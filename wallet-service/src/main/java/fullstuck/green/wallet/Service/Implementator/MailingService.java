package fullstuck.green.wallet.Service.Implementator;

import fullstuck.green.wallet.Service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class MailingService implements EmailService {
    private final JavaMailSender emailSender;
    private final static String subject = "Password Reset - GreenPay";

    @Override
    public void simpleResetPassword(String to, String url) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply.admin@greenwallet.org");
        message.setTo(to);
        message.setSubject(subject);
        message.setText("Dear " + to +
        " you have requested a password reset for your account at GreenPay.\n\n" +
        "If this was an accident or you have never requested such action, please ignore this message.\n\n"
                + "Here's your password reset token for " + to + ". Please note that the link will only available for 1 hours.\n\n"
        + url + "\n\n" + "Best regards, \nGreenPay Team"
        );
        emailSender.send(message);
    }

    @Override
    public void upgradedResetPassword(String to, String url) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            message.setFrom(new InternetAddress("noreply.admin@greenwallet.org"));
            message.setRecipients(MimeMessage.RecipientType.TO, to);
            message.setSubject(subject);
            message.setText("Dear " + to +
                    " you have requested a password reset for your account at GreenPay.\n\n" +
                    "If this was an accident or you have never requested such action, please ignore this message.\n\n"
                    + "Here's your password reset token for " + to + ". Please note that the link will only available for 1 hours.\n\n"
                    + url + "\n\n" + "Best regards, \nGreenPay Team"
            );
            // https://mailtrap.io/blog/spring-send-email/
            String htmlContent = "<H2>TESTING H2 PARAGRAF<H2>";
            message.setContent(htmlContent, "text/html; charset=utf-8");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        emailSender.send(message);
    }
}