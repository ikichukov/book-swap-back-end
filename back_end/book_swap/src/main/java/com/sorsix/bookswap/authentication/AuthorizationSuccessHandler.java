package com.sorsix.bookswap.authentication;

import com.sorsix.bookswap.authentication.users.domain.User;
import com.sorsix.bookswap.authentication.users.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;

@Component
public class AuthorizationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static String DEFAULT_TARGET_URL = "http://localhost:4200";

    @Autowired
    private UsersService usersService;

    private static Logger logger = LoggerFactory.getLogger(AuthorizationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        this.setDefaultTargetUrl(DEFAULT_TARGET_URL);
        super.onAuthenticationSuccess(request, response, authentication);

        User user = buildAndSaveAuthenticatedUser(authentication, request.getRequestURL().toString());
        request.getSession().setAttribute("user", user);
    }

    private User buildAndSaveAuthenticatedUser(Authentication authentication, String requestUrl){
        Authentication userAuthentication = ((OAuth2Authentication)authentication).getUserAuthentication();
        LinkedHashMap<String, Object> details = (LinkedHashMap<String, Object>) userAuthentication.getDetails();

        String loginSource = requestUrl.endsWith("facebook")?"f":"g";
        String id =  String.format("%s%s", loginSource, details.get("id"));
        String name = String.valueOf(details.get("name"));
        String email =  String.valueOf(details.get("email"));

        return this.usersService.saveUser(id, name, email);
    }

}

