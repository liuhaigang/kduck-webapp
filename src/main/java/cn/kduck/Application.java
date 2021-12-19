package cn.kduck;

import cn.kduck.module.account.service.AccountPwdEncoder;
import cn.kduck.module.account.service.AccountService;
import cn.kduck.security.UserExtInfo;
import cn.kduck.webapp.login.UserExtInfoImpl;
import cn.kduck.webapp.security.CustomRoleAccessVoter;
import cn.kduck.webapp.security.UserDetailsServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
//@EnableRabbit
public class Application extends SpringBootServletInitializer {

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

//    @Bean
//    public AccountPwdEncoder accountPwdEncoder(PasswordEncoder passwordEncoder){
//        return new AccountPwdEncoder(){
//            @Override
//            public String encode(CharSequence rawPassword) {
//                return passwordEncoder.encode(rawPassword);
//            }
//
//            @Override
//            public boolean matches(CharSequence rawPassword, String encodedPassword) {
//                return passwordEncoder.matches(rawPassword,encodedPassword);
//            }
//        };
//    }

//    @Bean
//    public CustomRoleAccessVoter roleAccessVoter(AccountService accountService){
//        return new CustomRoleAccessVoter(accountService);
//    }

    @Bean
    public UserExtInfo userExtInfo(){
        return new UserExtInfoImpl();
    }


    public static void main(String[] args) throws Exception {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(address.getHostAddress());

        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket allDocket() {
        return new Docket(DocumentationType.SWAGGER_2)//(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.ant("/**/*"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build()
                .useDefaultResponseMessages(false);
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("K-Duck Demo")
                //创建人
                .contact(new Contact("LiuHG", "http://www.kduck.cn", ""))
                //版本号
                .version("1.0")
                //描述
                .description("API 描述")
                .build();
    }

}

