package cn.kduck.module.account.credential;

import java.util.Random;

/**
 * @author LiuHG
 */
public abstract class AbstractRandomChar implements RandomChar{

    private Random random = new Random();

    protected int getRandom(int maxNum){
        return random.nextInt(maxNum);
    }

    @Override
    public char getChar() {
        char[] chars = resourceChars();
        return chars[getRandom(chars.length)];
    }

    protected abstract char[] resourceChars();

    protected boolean exist(char[] chars,String text){
        char[] textChars = text.toCharArray();
        for (int i = 0;i < textChars.length;i++){
            for (int j = 0;j < chars.length;j++){
                if (textChars[i] == chars[j]){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isType(String c) {
        return exist(resourceChars(),c);
    }
}


