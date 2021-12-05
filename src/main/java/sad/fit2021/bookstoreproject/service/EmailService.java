package sad.fit2021.bookstoreproject.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.sql.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import sad.fit2021.bookstoreproject.model.entity.User;
import sad.fit2021.bookstoreproject.model.entity.VerificationToken;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@NoArgsConstructor
@Getter
@Setter
public class EmailService {
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private JavaMailSender javaMailSender;

    private String appUrl;

    public void sendVerificationEmail(User user, String purpose) throws MessagingException {
        VerificationToken verificationToken = verificationTokenService.getTokenByUser(user);

        //check if user has token
        if (verificationToken != null) {
            String token = verificationToken.getToken();
            Context context = new Context();
            context.setVariable("title", "Veriy your email address!");
            context.setVariable("link", this.appUrl + "/"+ purpose +"?token=" + token);
            //create html template and pass variables to it

            String body = templateEngine.process("verification", context);

            //send verification email
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mailHelper = new MimeMessageHelper(message, true);
            mailHelper.setTo(user.getEmail());
            mailHelper.setSubject("Email address verification");
            mailHelper.setText(body, true);
            javaMailSender.send(message);


        }
    }

}
