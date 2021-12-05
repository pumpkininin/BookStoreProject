package sad.fit2021.bookstoreproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        setDefaultFailureUrl("/login.html?error=true");

        super.onAuthenticationFailure(request, response, exception);

        String errorMessage = "Invalid Credentials";

        if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
            errorMessage = "Your account is disabled please check your mail and click on the confirmation link";
        } else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
            errorMessage = "Your registration token has expired. Please register again.";
        }

        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
    }
}