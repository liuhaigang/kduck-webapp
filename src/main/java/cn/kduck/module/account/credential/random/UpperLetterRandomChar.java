package cn.kduck.module.account.credential.random;

import cn.kduck.module.account.credential.AbstractRandomChar;
import cn.kduck.module.account.credential.RandomChar;

/**
 * @author LiuHG
 */
public class UpperLetterRandomChar extends AbstractRandomChar implements RandomChar {

    private final char[] UPPER_LETTER_CHARS= new char[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};


    @Override
    protected char[] resourceChars() {
        return UPPER_LETTER_CHARS;
    }
}
