package cn.kduck.assembler.filter.interceptor;

import cn.kduck.assembler.security.LoginConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.kduck.module.account.service.Account;
import cn.kduck.module.account.service.AccountService;
import cn.kduck.security.principal.AuthUser;
import cn.kduck.security.principal.filter.AuthenticatedUserFilter.AuthUserContext;
import cn.kduck.security.principal.filter.FilterInterceptor;
import cn.kduck.core.utils.RequestUtils;
import cn.kduck.core.web.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ChangePasswordFilterInterceptor implements FilterInterceptor {

    private ObjectMapper om = new ObjectMapper();

    @Autowired
    private AccountService accountService;

    @Autowired
    private LoginConfig loginConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response) {
        AuthUser authUser = AuthUserContext.getAuthUser();
        if(authUser == null){
            return true;
        }

        if(loginConfig.getForceChangeFirstLogin() &&
                authUser.getDetailsItem("changePasswordRequired") == null){
            Account account = accountService.getAccountByName(authUser.getUsername());

            boolean needChangePwd = account.getChangePasswordDate() == null;

            authUser.setDetailsItem("changePasswordRequired", needChangePwd);

            if(needChangePwd){
                //FIXME 固定的/account/changePassword，对修改密码的接口请求不做拦截
//                固定的/account/credential/valid，对密码强度校验请求不做拦截
                if(request.getRequestURI().startsWith("/account/changePassword") ||
                        request.getRequestURI().startsWith("/account/credential/valid")){
                    return true;
                }

                if(RequestUtils.isAjax(request)){
                    JsonObject jsonObject = new JsonObject(null,-4 , "需要强制修改密码");
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    try {
                        om.writeValue(response.getOutputStream(),jsonObject);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    //TODO redirect
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response) {

    }
}
