package com.yuwubao.services.impl;

import com.yuwubao.entities.ArticleEntity;
import com.yuwubao.services.ArticleService;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * Created by yangyu on 2017/10/27.
 */
@Service
public class ArticleSearchService {



    @Autowired
    private TransportClient client;

    @Autowired
    private ArticleService articleService;

    /**
     * 创建一个文档
     */
    public Boolean createDoc(ArticleEntity articleEntity) throws Exception {
        // 使用XContentBuilder创建一个doc source
        String id = String.valueOf(articleEntity.getId());
        XContentBuilder builder =
                XContentFactory.jsonBuilder()
                        .startObject()
                        .field("title", articleEntity.getTitle())
                        .field("author", articleEntity.getAuthor())
                        .field("content", articleEntity.getContent())
                        .endObject();

        IndexResponse indexResponse = this.client
                .prepareIndex()
                .setIndex("zk")
                .setType("article")
                .setId(id) // 如果没有设置id，则ES会自动生成一个id
                .setSource(builder.string())
                .get();
        return true;
    }


    /**
     * 更新文档
     *
     */
    public Boolean update(String id,ArticleEntity articleEntity)throws Exception{
        String article = get(id);
        if (article == null) {
            return false;
        }
        UpdateRequest update = new UpdateRequest("zk","article",id);
        String title = articleEntity.getTitle();
        String author = articleEntity.getAuthor();
        String content = articleEntity.getContent();

        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject();
        if(!StringUtils.isEmpty(title)){
            builder.field("title",title);
        }
        if(!StringUtils.isEmpty(author)){
            builder.field("author", author);
        }
        if(!StringUtils.isEmpty(content)){
            builder.field("content", content);
        }
        builder.endObject();
        update.doc(builder);
        UpdateResponse result = this.client.update(update).get();
        return true;
    }

    /**
     * 根据ID查询一条数据记录。
     * @param id 要查询数据的ID。
     * @return 返回查询出来的记录对象的json字符串。
     */
    public String get(String id) {
        GetResponse getResponse = this.client
                .prepareGet()   // 准备进行get操作，此时还有真正地执行get操作。（与直接get的区别）
                .setIndex("zk")  // 要查询的
                .setType("article")
                .setId(id)
                .get();
        return getResponse.getSourceAsString();
    }

    /**
     *  删除文档
     *
     */
    public Boolean delete(String id){
        DeleteResponse result = this.client.prepareDelete("zk", "article", id).get();
        return true;
    }



}
