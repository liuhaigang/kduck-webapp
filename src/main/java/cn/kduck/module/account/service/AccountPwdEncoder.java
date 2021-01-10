package cn.kduck.module.account.service;

/**
 * LiuHG
 */
public interface AccountPwdEncoder {

    String encode(CharSequence rawPassword);

    boolean matches(CharSequence rawPassword, String encodedPassword);
}
