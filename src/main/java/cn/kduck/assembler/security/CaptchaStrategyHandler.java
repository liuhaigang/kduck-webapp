package cn.kduck.assembler.security;

import cn.kduck.security.exception.AuthenticationFailureException;
import cn.kduck.security.filter.AuthenticationFailureStrategyFilter.AuthenticationFailureStrategyHandler;
import cn.kduck.security.filter.AuthenticationFailureStrategyFilter.PreAuthenticationToken;
import cn.kduck.security.listener.AuthenticationFailListener.AuthenticationFailRecord;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * 出现验证码策略
 */
public class CaptchaStrategyHandler implements AuthenticationFailureStrategyHandler {

    @Autowired
    private LoginConfig loginConfig;

    @Override
    public boolean supports(PreAuthenticationToken authentication, HttpServletRequest httpRequest) {
        if(!loginConfig.getCaptchaEnabled()) return false;
        AuthenticationFailRecord failRecord = authentication.getFailRecord();
        return failRecord.getFailTotalNum() >= loginConfig.getCaptchaThreshold();
    }

    @Override
    public boolean authenticate(PreAuthenticationToken authentication,HttpServletRequest httpRequest) {
//        CacheHelper.get(authentication.getName());
//        String captchaId = CacheHelper.get(httpRequest.getParameter("captchaId"),String.class);
        String captcha = httpRequest.getParameter("captcha");
        //TODO 仅用于session模式
        if(captcha == null || !CaptchaUtil.ver(captcha,httpRequest)){
            throw new AuthenticationFailureException("captchaRequired","验证码输入不正确");
        }
        return false;
    }

}
