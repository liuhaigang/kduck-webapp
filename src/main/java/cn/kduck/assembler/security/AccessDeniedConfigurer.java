package cn.kduck.assembler.security;

import cn.kduck.security.configuration.HttpSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

@Component
public class AccessDeniedConfigurer implements HttpSecurityConfigurer {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().accessDeniedHandler(new AccessDeniedMessageHandler());
    }

}
