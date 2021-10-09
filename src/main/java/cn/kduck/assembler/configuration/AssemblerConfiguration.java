package cn.kduck.assembler.configuration;

import cn.kduck.assembler.KduckAssemblerProperties;
import cn.kduck.assembler.security.CaptchaStrategyHandler;
import cn.kduck.assembler.security.LockStrategyHandler;
import cn.kduck.flow.client.BpmServiceFactory;
import cn.kduck.security.configuration.HttpSecurityConfigurer;
import cn.kduck.security.filter.AuthenticationFailureStrategyFilter.AuthenticationFailureStrategyHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

@Configuration
@EnableConfigurationProperties(KduckAssemblerProperties.class)
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class AssemblerConfiguration {

    @Bean
    public AuthenticationFailureStrategyHandler captchaPreAuthentication(){
        return new CaptchaStrategyHandler();
    }

    @Bean
    public AuthenticationFailureStrategyHandler lockPreAuthentication(){
        return new LockStrategyHandler();
    }

    @Bean
    public BpmServiceFactory bpmServiceFactory(@Value("${kduck.flow.base-url}") String baseUrl){
        BpmServiceFactory bpmServiceFactory = BpmServiceFactory.getInstance(baseUrl);
        return bpmServiceFactory;
    }

    @Bean
    public HttpSecurityConfigurer assemblerSecurityConfigurer(){
        return new HttpSecurityConfigurer(){
            @Override
            public void configure(HttpSecurity http) throws Exception {}

            @Override
            public void configure(WebSecurity web) throws Exception {
                web.ignoring().antMatchers("/captcha");
            }
        };
    }

}
