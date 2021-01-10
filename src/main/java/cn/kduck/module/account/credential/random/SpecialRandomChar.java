package cn.kduck.module.account.credential.random;

import cn.kduck.module.account.credential.AbstractRandomChar;
import cn.kduck.module.account.credential.RandomChar;

/**
 * @author LiuHG
 */
public class SpecialRandomChar extends AbstractRandomChar implements RandomChar {


    private final char[] SPECIAL_CHARS = new char[]{'~','!','@','#','$','%','^','&','*','(',')','_','+','=','{','}','[',']','>','<','/','?'};

    @Override
    protected char[] resourceChars() {
        return SPECIAL_CHARS;
    }
}
