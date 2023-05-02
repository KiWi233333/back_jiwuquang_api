package com.example.kiwi_community_mall_back.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {

    @Resource
    private JavaMailSender mailSender;
    @Resource
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;
    @Value("${emailTemplate}")
    private String emailTemplatePath;
    @Value("${emailCodeTemplate}")
    private String emailCodeTemplate;



    /**
     * 发送验证码文件
     * @param to 收件人
     * @param theme 主题
     * @param type 注册、修改和 登录
     * @param code 验证码
     * @throws MessagingException 发送失败
     */
    public void sendCodeMail(String to,String theme, String type, String code) throws MessagingException {
        MimeMessage mimeMessage =mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(theme);
        // 利用 Thymeleaf 模板构建 html 文本
        Context ctx = new Context();
        Map<String, String> map = new HashMap<>();
        map.put("title",theme);
        map.put("type",type);
        map.put("email",to);
        map.put("code",code);
        // 给模板的参数的上下文
        ctx.setVariable("EmailParams", map);
        // Thymeleaf的默认配置期望所有HTML文件都放在 **resources/templates ** 目录下，以.html扩展名结尾。
        String emailText = templateEngine.process(emailTemplatePath, ctx);
        // 传入模板
        mimeMessageHelper.setText(emailText, true);
        mailSender.send(mimeMessage);
    }

    /**
     * 发送普通文本邮件
     * @param to  收件人
     * @param title 标题
     * @param content 正文
     * @throws Exception 失败
     */
    public void sendTextMail(String to, String title, String content)throws Exception{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from); // 发件人
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
    }
}
