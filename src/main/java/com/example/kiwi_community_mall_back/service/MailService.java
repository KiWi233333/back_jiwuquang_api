package com.example.kiwi_community_mall_back.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * 发送文本邮件
     *
     * @param to      收件人
     * @param title 标题
     * @param content 正文
     * @throws Exception
     */
    public void sendTextMail(String to, String title, String content)throws Exception{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail); // 发件人
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
    }
    public boolean sendCodeMail(String to, String title, String code)throws Exception{
        try {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(title);
            //第二个参数：格式是否为html
            helper.setText(getTemplate(code), true);
            mailSender.send(message);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }


    /**
     * 发送html文件
     *
     * @param to
     * @param title
     * @param content
     * @throws MessagingException
     */
    public void sendHtmlMail(String to, String title, String content) throws MessagingException {
        //注意这里使用的是MimeMessage
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(fromEmail);
        helper.setTo(to);
        helper.setSubject(title);
        //第二个参数：格式是否为html
        helper.setText(content, true);
        mailSender.send(message);
    }

    private String getTemplate(String code){
        return "<div>\n" +
                "    <includetail>\n" +
                "        <div style=\"font:Verdana normal 14px;color:#000;\">\n" +
                "            <div style=\"position:relative;\">\n" +
                "                <div class=\"eml-w eml-w-sys-layout\">\n" +
                "                    {{--页眉--}}\n" +
                "                    <div style=\"font-size: 0px;\">\n" +
                "                        {{--分割线--}}\n" +
                "                        <div class=\"eml-w-sys-line\">\n" +
                "                            <div class=\"eml-w-sys-line-left\"></div>\n" +
                "                            <div class=\"eml-w-sys-line-right\"></div>\n" +
                "                        </div>\n" +
                "                        {{--  LOGO --}}\n" +
                "                        <div class=\"eml-w-sys-logo\">\n" +
                "                            <img src=\"https://rescdn.qqmail.com/node/wwqy/qymng/style/images/sass/independent/welcome_eml_logo.png\" style=\"width: 34px; height: 24px;\" onerror=\"\">\n" +
                "                        </div>\n" +
                "                    </div>\n" +
                "                    {{--以下写正文--}}\n" +
                "                    <div class=\"eml-w-sys-content\">\n" +
                "                        {{--以下写正文--}}\n" +
                "                        <div class=\"dragArea gen-group-list\">\n" +
                "\n" +
                "                            {{-- 普通的文本  --}}\n" +
                "                            <div class=\"gen-item\">\n" +
                "                                <div class=\"eml-w-item-block\" style=\"padding: 0px;\">\n" +
                "                                    <div class=\"eml-w-phase-normal-16\">用户你好！你的验证码为：</div>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "\n" +
                "                            {{-- 普通的文本【自定义样式】  --}}\n" +
                "                            <div class=\"gen-item\">\n" +
                "                                {{-- padding:行间距 --}}\n" +
                "                                <div class=\"eml-w-item-block\" style=\"padding: 10px;\">\n" +
                "                                    <div class=\"eml-w-phase-normal-16\" style=\"color: red;text-align: center;font-weight:bold\"> "+code+ "</div>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "\n" +
                "                            {{-- 普通的文本【自定义样式】  --}}\n" +
                "                            <div class=\"gen-item\">\n" +
                "                                {{-- padding:行间距 --}}\n" +
                "                                <div class=\"eml-w-item-block\" style=\"padding: 0px;\">\n" +
                "                                    <div class=\"eml-w-phase-normal-16\" style=\"color: red;text-align: center;font-weight:bold;font-size: 20px\"> "+code+ "</div>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                    {{--  署名  --}}\n" +
                "                    <div class=\"eml-w-sys-footer\">腾讯企业邮团队</div>\n" +
                "                </div>\n" +
                "                {{--                <img src=\"//exmail.qq.com/qy_mng_logic/reportKV?type=NewFeatureNotify0731&amp;itemName=NewFeatureNotify0731\" style=\"width:1px;height:1px;display:none;\" onerror=\"\">--}}\n" +
                "            </div>\n" +
                "        </div><!--<![endif]-->\n" +
                "    </includetail>\n" +
                "</div>\n" +
                "\n" +
                "<style>\n" +
                "    .eml-w .eml-w-phase-normal-16 {\n" +
                "        color: #2b2b2b;\n" +
                "        font-size: 16px;\n" +
                "        line-height: 1.75\n" +
                "    }\n" +
                "\n" +
                "    .eml-w .eml-w-phase-bold-16 {\n" +
                "        font-size: 16px;\n" +
                "        color: #2b2b2b;\n" +
                "        font-weight: 500;\n" +
                "        line-height: 1.75\n" +
                "    }\n" +
                "\n" +
                "    .eml-w-title-level1 {\n" +
                "        font-size: 20px;\n" +
                "        font-weight: 500;\n" +
                "        padding: 15px 0\n" +
                "    }\n" +
                "\n" +
                "    .eml-w-title-level3 {\n" +
                "        font-size: 16px;\n" +
                "        font-weight: 500;\n" +
                "        padding-bottom: 10px\n" +
                "    }\n" +
                "\n" +
                "    .eml-w-title-level3.center {\n" +
                "        text-align: center\n" +
                "    }\n" +
                "\n" +
                "    .eml-w-phase-small-normal {\n" +
                "        font-size: 14px;\n" +
                "        color: #2b2b2b;\n" +
                "        line-height: 1.75\n" +
                "    }\n" +
                "\n" +
                "    .eml-w-picture-wrap {\n" +
                "        padding: 10px 0;\n" +
                "        width: 100%;\n" +
                "        overflow: hidden\n" +
                "    }\n" +
                "\n" +
                "    .eml-w-picture-full-img {\n" +
                "        display: block;\n" +
                "        width: auto;\n" +
                "        max-width: 100%;\n" +
                "        margin: 0 auto\n" +
                "    }\n" +
                "\n" +
                "    .eml-w-sys-layout {\n" +
                "        background: #fff;\n" +
                "        box-shadow: 0 2px 8px 0 rgba(0, 0, 0, .2);\n" +
                "        border-radius: 4px;\n" +
                "        margin: 50px auto;\n" +
                "        max-width: 700px;\n" +
                "        overflow: hidden\n" +
                "    }\n" +
                "\n" +
                "    .eml-w-sys-line-left {\n" +
                "        display: inline-block;\n" +
                "        width: 88%;\n" +
                "        background: #2984ef;\n" +
                "        height: 3px\n" +
                "    }\n" +
                "\n" +
                "    .eml-w-sys-line-right {\n" +
                "        display: inline-block;\n" +
                "        width: 11.5%;\n" +
                "        height: 3px;\n" +
                "        background: #8bd5ff;\n" +
                "        margin-left: 1px\n" +
                "    }\n" +
                "\n" +
                "    .eml-w-sys-logo {\n" +
                "        text-align: right\n" +
                "    }\n" +
                "\n" +
                "    .eml-w-sys-logo img {\n" +
                "        display: inline-block;\n" +
                "        margin: 30px 50px 0 0\n" +
                "    }\n" +
                "\n" +
                "    .eml-w-sys-content {\n" +
                "        position: relative;\n" +
                "        padding: 20px 50px 0;\n" +
                "        min-height: 216px;\n" +
                "        word-break: break-all\n" +
                "    }\n" +
                "\n" +
                "    .eml-w-sys-footer {\n" +
                "        font-weight: 500;\n" +
                "        font-size: 12px;\n" +
                "        color: #bebebe;\n" +
                "        letter-spacing: .5px;\n" +
                "        padding: 0 0 30px 50px;\n" +
                "        margin-top: 60px\n" +
                "    }\n" +
                "\n" +
                "    .eml-w {\n" +
                "        font-family: Helvetica Neue, Arial, PingFang SC, Hiragino Sans GB, STHeiti, Microsoft YaHei, sans-serif;\n" +
                "        -webkit-font-smoothing: antialiased;\n" +
                "        color: #2b2b2b;\n" +
                "        font-size: 14px;\n" +
                "        line-height: 1.75\n" +
                "    }\n" +
                "\n" +
                "    .eml-w a {\n" +
                "        text-decoration: none\n" +
                "    }\n" +
                "\n" +
                "    .eml-w a, .eml-w a:active {\n" +
                "        color: #186fd5\n" +
                "    }\n" +
                "\n" +
                "    .eml-w h1, .eml-w h2, .eml-w h3, .eml-w h4, .eml-w h5, .eml-w h6, .eml-w li, .eml-w p, .eml-w ul {\n" +
                "        margin: 0;\n" +
                "        padding: 0\n" +
                "    }\n" +
                "\n" +
                "    .eml-w-item-block {\n" +
                "        margin-bottom: 10px\n" +
                "    }\n" +
                "\n" +
                "    @media (max-width: 420px) {\n" +
                "        .eml-w-sys-layout {\n" +
                "            border-radius: none !important;\n" +
                "            box-shadow: none !important;\n" +
                "            margin: 0 !important\n" +
                "        }\n" +
                "\n" +
                "        .eml-w-sys-layout .eml-w-sys-line {\n" +
                "            display: none\n" +
                "        }\n" +
                "\n" +
                "        .eml-w-sys-layout .eml-w-sys-logo img {\n" +
                "            margin-right: 30px !important\n" +
                "        }\n" +
                "\n" +
                "        .eml-w-sys-layout .eml-w-sys-content {\n" +
                "            padding: 0 35px !important\n" +
                "        }\n" +
                "\n" +
                "        .eml-w-sys-layout .eml-w-sys-footer {\n" +
                "            padding-left: 30px !important\n" +
                "        }\n" +
                "    }\n" +
                "</style>";
    }
}
