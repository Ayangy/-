package com.yuwubao.controllers;

import com.yuwubao.entities.ClientUserEntity;
import com.yuwubao.services.ClientUserService;
import com.yuwubao.util.Const;
import com.yuwubao.util.MD5;
import com.yuwubao.util.RestApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by yangyu on 2017/12/20.
 */
@RestController
@RequestMapping("/clientUser")
@Transactional
@CrossOrigin
public class ClientUserController {

    private final static Logger logger = LoggerFactory.getLogger(ClientUserController.class);

    @Value("${myEmailSMTPHost}")
    private String myEmailSMTPHost;

    @Value("${myEmailAccount}")
    private String myEmailAccount;

    @Value("${myEmailPwd}")
    private String myEmailPwd ;

    @Autowired
    private ClientUserService clientUserService;

    /**
     * 注册用户
     */
    @PostMapping("/registeredUser")
    public RestApiResponse<Boolean> registeredUser(@RequestBody ClientUserEntity clientUserEntity) {
        RestApiResponse<Boolean> result = new RestApiResponse<Boolean>();
        try {
            ClientUserEntity clientUser = clientUserService.findByEmail(clientUserEntity.getEmail());
            if (clientUser != null) {
                result.failedApiResponse(Const.FAILED, "已使用此邮箱注册");
                return result;
            }
            String uuid = UUID.randomUUID().toString().replace("-", "");
            clientUserEntity.setActivationCode(uuid);
            clientUserEntity.setPwd(MD5.md5(clientUserEntity.getPwd()));
            ClientUserEntity entity = clientUserService.add(clientUserEntity);
            if (entity != null) {
                //创建参数配置, 用于连接邮件服务器的参数配置
                Properties props = new Properties();
                props.setProperty("mail.transport.protocol", "smtp");
                props.setProperty("mail.smtp.host", myEmailSMTPHost);
                props.setProperty("mail.smtp.auth", "true");

                //配置ssl
                /*final String smtpPort = "465";
                props.setProperty("mail.smtp.port", smtpPort);
                props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.setProperty("mail.smtp.socketFactory.fallback", "false");
                props.setProperty("mail.smtp.socketFactory.port", smtpPort);*/

                //根据配置创建会话对象, 用于和邮件服务器交互
                Session session = Session.getInstance(props);
                session.setDebug(true);

                //创建邮件
                MimeMessage message = new MimeMessage(session);

                // From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
                message.setFrom(new InternetAddress(myEmailAccount, "湖北智库网", "UTF-8"));

                //To: 收件人（可以增加多个收件人、抄送、密送）
                message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(clientUserEntity.getEmail(), clientUserEntity.getName(), "UTF-8"));

                //邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
                message.setSubject("湖北智库网新用户激活", "UTF-8");

                //邮件正文
                message.setContent("您好：\n" +
                        "感谢您注册湖北智库网，点击或复制到浏览器打开下方链接，激活您的账号"+
                        "<a href='http://localhost:8080/clientUser/activateUser?id=" + clientUserEntity.getId() + "&ActivationCode=" +clientUserEntity.getActivationCode()+" _act='check_domail'>http://localhost:8080/clientUser/activateUser?id="+ clientUserEntity.getId() +"&ActivationCode=" +clientUserEntity.getActivationCode()+ "</a>",
                        "text/html;charset=UTF-8");

                //设置发件时间
                message.setSentDate(new Date());

                // 7. 保存设置
                message.saveChanges();

                //根据 Session 获取邮件传输对象
                Transport transport = session.getTransport();
                transport.connect(myEmailAccount, myEmailPwd);
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                result.successResponse(Const.SUCCESS, true, "注册成功");
                return result;
            }
            result.failedApiResponse(Const.FAILED, "注册失败");
            return result;
        } catch (Exception e) {
            logger.warn("注册异常", e);
            result.failedApiResponse(Const.FAILED, "注册异常");
        }
        return result;
    }

    /**
     * 激活账号
     * @param id  前端用户id
     * @param ActivationCode  激活码
     * @return
     */
    @RequestMapping("/activateUser")
    public RestApiResponse<Boolean> activateUser(@RequestParam int id,
                                                 @RequestParam String ActivationCode) {
        RestApiResponse<Boolean> result = new RestApiResponse<Boolean>();
        try {
            ClientUserEntity entity = clientUserService.findOne(id);
            if (entity != null) {
                if(entity.getStatus() == 1){
                    result.failedApiResponse(Const.FAILED, "账号已激活");
                    return result;
                }
                if(entity.getActivationCode().equals(ActivationCode)){
                    entity.setStatus(1);
                    clientUserService.add(entity);
                    result.successResponse(Const.SUCCESS, true, "激活成功");
                    return result;
                }
            }
            result.failedApiResponse(Const.FAILED, "激活失败");
        } catch (Exception e) {
            logger.warn("激活异常", e);
            result.failedApiResponse(Const.FAILED, "激活异常");
        }
        return result;
    }

}