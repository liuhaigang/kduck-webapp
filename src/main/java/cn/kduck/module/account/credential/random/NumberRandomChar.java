package cn.kduck.module.account.credential.random;

import cn.kduck.module.account.credential.AbstractRandomChar;
import cn.kduck.module.account.credential.RandomChar;

/**
 * @author LiuHG
 */
public class NumberRandomChar extends AbstractRandomChar implements RandomChar {

    private final char[] NUMBER_CHARS = new char[]{'0','1','2','3','4','5','6','7','8','9'};

    @Override
    protected char[] resourceChars() {
        return NUMBER_CHARS;
    }
}
