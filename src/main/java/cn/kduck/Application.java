package cn.kduck;

import cn.kduck.module.account.service.AccountPwdEncoder;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.UUID;

@SpringBootApplication
//@ComponentScan({"com.goldgov.kduck","com.goldgov.demo"})
public class Application extends SpringBootServletInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public AccountPwdEncoder accountPwdEncoder(){
        return new AccountPwdEncoder(){
            @Override
            public String encode(CharSequence rawPassword) {
                return passwordEncoder.encode(rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return passwordEncoder.matches(rawPassword,encodedPassword);
            }
        };
    }

    @Bean
    public CustomRoleAccessVoter roleAccessVoter(){
        return new CustomRoleAccessVoter();
    }

    @Bean
    public UserExtInfo userExtInfo(){
        return new UserExtInfoImpl();
    }


    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);

        /**
         * CREATE TABLE `demo` (
         *   `demo_id` varchar(50) DEFAULT NULL,
         *   `demo_name` varchar(255) DEFAULT NULL,
         *   `demo_age` int(11) DEFAULT NULL,
         *   `demo_date` datetime DEFAULT NULL
         * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
         */
//        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/kduck?useSSL=false&nullCatalogMeansCurrent=true&serverTimezone=Asia/Shanghai", "liuhg", "gang317");

//        PreparedStatement preparedStatement = connection.prepareStatement("insert into demo (demo_id,demo_name,demo_age,demo_date)values(?,?,?,?)");
//        PreparedStatement preparedStatement = connection.prepareStatement("update demo set demo_name=? where demo_id=?");

//        for (int i = 0; i < 10; i++) {
//            preparedStatement.setString(1, "id_"+i);
//            preparedStatement.setString(2,"text_"+i);
//            preparedStatement.setInt(3,i+1);
//            preparedStatement.setDate(4,new java.sql.Date(System.currentTimeMillis()));


//            preparedStatement.setString(1,"modify_"+i);
//            preparedStatement.setString(2, "id_"+i);
//            preparedStatement.addBatch();
//        }

//        preparedStatement.executeBatch();
//        preparedStatement.close();
//        connection.close();

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
                .contact(new Contact("LiuHG", "http://www.software4j.com", ""))
                //版本号
                .version("1.0")
                //描述
                .description("API 描述")
                .build();
    }

}

