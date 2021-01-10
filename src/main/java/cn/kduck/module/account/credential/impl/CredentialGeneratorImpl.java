package cn.kduck.module.account.credential.impl;

import cn.kduck.module.account.credential.random.LowerLetterRandomChar;
import cn.kduck.module.account.credential.random.NumberRandomChar;
import cn.kduck.module.account.credential.random.SpecialRandomChar;
import cn.kduck.module.account.credential.CredentialGenerator;
import cn.kduck.module.account.credential.RandomChar;
import cn.kduck.module.account.credential.random.UpperLetterRandomChar;

import java.util.*;

/**
 *
 * @author LiuHG
 */
public class CredentialGeneratorImpl implements CredentialGenerator {

    private static Map<Integer,RandomChar> randomCharMap = new HashMap<>();

    private static final Random RANDOM = new Random();

    static {
        /** 数值与CredentialGenerator中常量定义的保持一致 **/
        randomCharMap.put(1,new NumberRandomChar());
        randomCharMap.put(2,new LowerLetterRandomChar());
        randomCharMap.put(4,new UpperLetterRandomChar());
        randomCharMap.put(8,new SpecialRandomChar());
    }

    /**
     *
     * 提示：
     * 如果未来有必须以某字符开头，可以考虑将第一个字符直接用对应的RandomChar创建，然后随机创建"长度-1"的剩余字符；如果未来有前缀
     * 及后缀要求，暂时由调用者自行在调用外部实现。
     *
     * @author LiMK
     * @param length
     * @param strength
     * @return
     */
    @Override
    public String generate(int length,int strength) {
        List<RandomChar> charTypeList = new ArrayList<>(randomCharMap.size());

        Iterator<Integer> keyIterator = randomCharMap.keySet().iterator();
        while(keyIterator.hasNext()){
            Integer indexValue = keyIterator.next();
            if((strength & indexValue)> 0){
                charTypeList.add(randomCharMap.get(indexValue));
            }
        }

        if(charTypeList.size() > length){
            throw new IllegalArgumentException("随机值的长度不满足复杂度长度：随机强度类型种类（"+charTypeList.size()+"） < 密码长度（"+length+"）");
        }

        Collections.shuffle(charTypeList);

        int randomLen;
        StringBuilder builder = new StringBuilder();
        for (int i = charTypeList.size() - 1; i >= 0; i--) {
            if (i == 0) {
                randomLen = length - builder.length();
            } else {
                randomLen = RANDOM.nextInt(length - builder.length() - i) + 1;
            }
            for (int charNum = 0; charNum < randomLen; charNum++) {
                builder.append(charTypeList.get(i).getChar());
            }
        }
        char[] chars = builder.toString().toCharArray();
        shuffle(chars, RANDOM);


        return builder.toString();
    }

    private void shuffle(char[] arr, Random rnd) {
        int size = arr.length;
        for (int i = size; i > 1; i--)
            swap(arr, i - 1, rnd.nextInt(i));

    }

    private void swap(char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    @Override
    public boolean isValid(String str, int strength) {
        Iterator<Integer> keyIterator = randomCharMap.keySet().iterator();
        while(keyIterator.hasNext()){
            Integer indexValue = keyIterator.next();
            if((strength & indexValue)> 0){
                RandomChar randomChar = randomCharMap.get(indexValue);
                if(!randomChar.isType(str)){
                    return false;
                }
            }
        }
        return true;
    }

}
