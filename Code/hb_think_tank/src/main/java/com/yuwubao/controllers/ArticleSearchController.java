package com.yuwubao.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuwubao.entities.ArticleEntity;
import com.yuwubao.services.ArticleService;
import com.yuwubao.util.Const;
import com.yuwubao.util.RestApiResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyu on 2017/11/2.
 */
@RestController
@RequestMapping
@CrossOrigin
public class ArticleSearchController {

    private final static Logger logger = LoggerFactory.getLogger(ArticleSearchController.class);

    @Autowired
    private TransportClient client;

    @Autowired
    private ArticleService articleService;

    /**
     *  站内搜索
     * @param query 查询内容
     * @param pageIndex  第几页
     * @param pageSize  每页几条
     *
     */
    @GetMapping("/query/zk/article")
    public RestApiResponse<List<ArticleEntity>> query(@RequestParam(defaultValue = "", required = false) String query,
                                     @RequestParam(defaultValue = "0", required = false) int pageIndex,
                                     @RequestParam(defaultValue = "10", required = false) int pageSize){
        RestApiResponse<List<ArticleEntity>> result = new RestApiResponse<List<ArticleEntity>>();
        List<ArticleEntity> list = new  ArrayList<ArticleEntity>();
        try {
            if (StringUtils.isEmpty(query)) {
                List<Map<String, Object>> articleEntityList =  articleService.getAll(pageIndex, pageSize);
                ModelMapper mapper = new ModelMapper();
                for (Map<String,Object> map: articleEntityList ) {
                    ArticleEntity articleEntity = mapper.map(map, ArticleEntity.class);
                    list.add(articleEntity);
                }
                result.successResponse(Const.SUCCESS, list, "获取列表成功");
                return result;
            }
            // 构建布尔查询
            QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(query);
            queryBuilder.analyzer("ik_smart");
            queryBuilder.field("title").field("author").field("content");

            HighlightBuilder hiBuilder = new HighlightBuilder();
            hiBuilder.preTags("<span style=\"color:red\">");
            hiBuilder.postTags("</span>");
            hiBuilder.field("title").field("author").field("content");

            SearchRequestBuilder builder = this.client.prepareSearch("zk").highlighter(hiBuilder)
                    .setTypes("article")
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(queryBuilder)
                    .setExplain(true)
                    .setFrom(pageIndex)
                    .setSize(pageSize);

            SearchResponse response = builder.get();

            ObjectMapper mapper = new ObjectMapper();
            for(SearchHit hit:response.getHits()){
                ArticleEntity articleEntity = mapper.readValue(hit.getSourceAsString(), ArticleEntity.class);
                Map<String, HighlightField> Hresult = hit.highlightFields();
                //从设定的高亮域中取得指定域
                HighlightField titleField = Hresult.get("title");
                if (titleField != null) {
                    Text[] titleTexts = titleField.fragments();
                    String title = "";
                    for(Text text : titleTexts){
                        title += text;
                    }
                    articleEntity.setTitle(title);
                }
                HighlightField contentField = Hresult.get("content");
                if (contentField != null) {
                    Text[] contentTexts = contentField.fragments();
                    String content = "";
                    for (Text text: contentTexts){
                        content += text;
                    }
                    articleEntity.setContent(content);
                }
                list.add(articleEntity);
            }
            result.successResponse(Const.SUCCESS, list, "获取列表成功");
        } catch (Exception e) {
            logger.warn("站内搜索异常", e);
            result.failedApiResponse(Const.FAILED, "站内搜索异常");
        }

        return result;
    }

    /**
     *  创建索引
     * @return
     */
    @PostMapping("/es/addIndex")
    public RestApiResponse<Boolean> createIndex(@RequestParam(required = false, defaultValue = "zk") String index){
        RestApiResponse<Boolean> result = new RestApiResponse<>();
        try {
            CreateIndexResponse createIndexResponse = this.client.admin().indices().prepareCreate(index).get();
            boolean acknowledged = createIndexResponse.isAcknowledged();
            result.successResponse(Const.SUCCESS, acknowledged, "创建成功");
        } catch (Exception e) {
            logger.warn("创建失败", e);
            result.failedApiResponse(Const.FAILED, "创建失败");
        }
        return result;
    }

    /**
     * 给索引增加mapping。
     */
    @PostMapping("/es/addMapping")
    public RestApiResponse<Boolean> addMapping(@RequestParam(required = false, defaultValue = "zk") String index,
                                               @RequestParam(required = false, defaultValue = "article") String typeName) {
        RestApiResponse<Boolean> result = new RestApiResponse<Boolean>();
        try {
            // 使用XContentBuilder创建Mapping
            XContentBuilder builder =
                    XContentFactory.jsonBuilder()
                            .startObject()
                            .field("properties")
                            .startObject()
                            .field("title")
                            .startObject()
                            .field("analyzer","ik_max_word")
                            .field("type", "text")
                            .endObject()
                            .field("author")
                            .startObject()
                            .field("analyzer","ik_smart")
                            .field("type", "text")
                            .endObject()
                            .field("content")
                            .startObject()
                            .field("analyzer","ik_max_word")
                            .field("type", "text")
                            .endObject()
                            .endObject()
                            .endObject();
            System.out.println(builder.string());
            PutMappingRequest mappingRequest = Requests.putMappingRequest(index).source(builder).type(typeName);
            this.client.admin().indices().putMapping(mappingRequest).actionGet();
            result.successResponse(Const.SUCCESS, true, "创建mapping成功");
        } catch (Exception e) {
            logger.warn("创建失败", e);
            result.failedApiResponse(Const.FAILED, "创建失败");
        }
        return result;
    }

    /**
     * 删除索引
     *
     */
    @DeleteMapping("/es/deleteIndex")
    public RestApiResponse<Boolean> deleteIndex(@RequestParam(required = false, defaultValue = "zk") String index) {
        RestApiResponse<Boolean> result = new RestApiResponse<Boolean>();
        try {
            DeleteIndexResponse deleteIndexResponse =
                    this.client
                            .admin()
                            .indices()
                            .prepareDelete(index)
                            .get();
            boolean acknowledged = deleteIndexResponse.isAcknowledged();// true表示成功
            result.successResponse(Const.SUCCESS, acknowledged, "删除索引成功");
        } catch (Exception e) {
            logger.warn("删除索引异常", e);
            result.failedApiResponse(Const.FAILED, "删除索引异常");
        }
        return result;
    }
}
