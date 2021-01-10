package cn.kduck.module.account.credential.random;

import cn.kduck.module.account.credential.AbstractRandomChar;
import cn.kduck.module.account.credential.RandomChar;

/**
 * @author LiuHG
 */
public class LowerLetterRandomChar extends AbstractRandomChar implements RandomChar {

    private final char[] LOWER_LETTER_CHARS=new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

    @Override
    protected char[] resourceChars() {
        return LOWER_LETTER_CHARS;
    }
}
