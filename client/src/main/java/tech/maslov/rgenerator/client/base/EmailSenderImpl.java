package tech.maslov.rgenerator.client.base;

import com.rcore.domain.userPasswordRecover.port.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@RequiredArgsConstructor
public class EmailSenderImpl implements EmailSender {
    private final String javaMailUsername;
    private final String javaMailPassword;

    @Override
    public void send(String toEmail, String code) {
        try {
            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.yandex.ru");
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true"); //TLS

            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(javaMailUsername, javaMailPassword);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(javaMailUsername));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("RGenerator recovery password link");

            String msg = "Hi!<br/>Please go to link - https://rgenerator.maslov.tech/forgot/" + code;

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
