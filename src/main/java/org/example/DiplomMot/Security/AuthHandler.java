package org.example.DiplomMot.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import java.io.IOException;
import java.util.Set;

//класс-обработчик аутентификации, в зависимости от роли перенаправляет
//либо на страницу администратора, либо на страницу пользователя
@Configuration
public class AuthHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Authentication authentication) throws IOException, jakarta.servlet.ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin-profile");
        } else {
            response.sendRedirect("/user-profile");
        }
    }
}