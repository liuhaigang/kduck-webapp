package cn.kduck.assembler.security;

import org.springframework.boot.actuate.security.AuthenticationAuditListener;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class LoginAuthenticationAuditListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        System.out.println("登录成功日志-=-=-=-=-=->" + event.getSource());
    }
}
