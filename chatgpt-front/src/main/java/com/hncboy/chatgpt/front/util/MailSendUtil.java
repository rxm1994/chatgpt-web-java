package com.hncboy.chatgpt.front.util;

import cn.hutool.extra.mail.MailAccount;
import com.hncboy.chatgpt.base.util.ObjectMapperUtil;
import com.sun.mail.smtp.SMTPTransport;
import lombok.extern.slf4j.Slf4j;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @author andyrenxiaomeng
 * @description
 * @Date 2023/5/10
 */
@Slf4j
public class MailSendUtil {


  public static String send(MailAccount mailAccount, String to, String subject, String content, boolean isHtml, File... files) {
    Properties props = new Properties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.host", mailAccount.getHost());
    props.put("mail.smtp.port", mailAccount.getPort());
    props.put("mail.smtp.starttls.enable", mailAccount.isStarttlsEnable());
    props.put("mail.smtp.auth", mailAccount.isAuth());
    /* Return path for bounces and complaints. Pick an address you own */
    props.put("mail.smtp.from", mailAccount.getFrom());
    props.put("mail.smtp.connectiontimeout", "10000");
    props.put("mail.smtp.timeout", "10000");
    props.put("mail.smtp.writetimeout", "10000");
    Session session = Session.getDefaultInstance(props);
    try {
      SMTPTransport transport = (SMTPTransport) session.getTransport();
      transport.connect(mailAccount.getUser(), mailAccount.getPass());

      MimeMessage msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress(mailAccount.getFrom()));
      msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
      msg.setSubject(subject);
      msg.setContent(content, "text/html; charset=utf-8");
      msg.saveChanges();

      transport.sendMessage(msg, msg.getAllRecipients());
      /* Log your SES message Id, AWS Support might need it */
      String serverResponse = transport.getLastServerResponse();
      String prefix = "250 Ok ";
      if (serverResponse.startsWith(prefix)) {
        return serverResponse.substring(prefix.length());
      }
    } catch (Exception e) {
      log.error("send mail failed ,mailAccount:{}", ObjectMapperUtil.toJson(mailAccount), e);
    }
    return "";
  }

}
