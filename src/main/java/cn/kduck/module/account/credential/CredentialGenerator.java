package cn.kduck.module.account.credential;

/**
 * 凭证生成器
 * @author LiuHG
 */
public interface CredentialGenerator {

    /* 数值与CredentialGeneratorImpl中静态代码块的保持一致 */
    int NUMBER = 1;
    int LOWER_LETTER = 2;
    int UPPER_LETTER = 4;
    int SPECIAL = 8;

    //TODO 密码长度，复杂度（包含的字符类型），前缀、后缀
    String generate(int length,int strength);

    boolean isValid(String str,int strength);

}
