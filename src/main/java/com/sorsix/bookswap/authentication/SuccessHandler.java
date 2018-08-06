package com.sorsix.bookswap.authentication;

import com.sorsix.bookswap.authentication.users.domain.User;
import com.sorsix.bookswap.authentication.users.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;

@Component
public class SuccessHandler implements AuthenticationSuccessHandler{

    @Autowired
    private UsersService usersService;

    private static Logger logger = LoggerFactory.getLogger(SuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Authentication userAuthentication = ((OAuth2Authentication)authentication).getUserAuthentication();
        LinkedHashMap<String, Object> details = (LinkedHashMap<String, Object>) userAuthentication.getDetails();
        String requestUrl = request.getRequestURL().toString();

        String loginSource = requestUrl.endsWith("facebook")?"f":"g";
        String id =  loginSource + String.valueOf(details.get("id"));
        String name = String.valueOf(details.get("name"));
        String email =  String.valueOf(details.get("email"));

        User user = new User(id, name, email);
        request.getSession().setAttribute("user", user);

        //this.usersService.setLoginSource(loginSource);
        this.usersService.saveUser(id, name, email);

        response.sendRedirect("/");
    }

}

