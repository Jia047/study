package com.jia.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author jia
 * @date 2019/12/7 10:05
 *
 **/
public class CharacterPinYinConvert {

    /**
     * 中文匹配的正则表达式
     */
    private static final String CHINESE = "[\\u4E00-\\u9FA5]";

    /**
     * 设置汉语拼音输出格式
     */
    private HanyuPinyinOutputFormat format = null;

    /**
     * 默认构造函数，指定拼音的输出格式
     */
    public CharacterPinYinConvert(){
        format = new HanyuPinyinOutputFormat();

        // 不设置音调
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        // 韵母 鱼（yu -> yv） 使用 v 来代替
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        // 返回的字母为小写字母
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    }

    /**
     * 文本转换成拼音
     * @param text 待转换文本
     */
    public String toPinyin(String text){
        StringBuilder pinYin = new StringBuilder();
        for(int i = 0; i < text.length(); i++){
            char wordChar = text.charAt(i);
            // 如果为汉字
            if(Character.toString(wordChar).matches(CHINESE)){
                try{
                    String[] allPinyin = PinyinHelper.toHanyuPinyinStringArray(wordChar, format);
                    // 取第一个拼音
                    String pinYin0 = allPinyin[0];
                    if(pinYin0 != null){
                        pinYin.append(pinYin0).append(" ");
                    }
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination){
//                    logger.info("拼音转换失败：" + wordChar);
                }
            }else if (((int) wordChar >= 65 && (int) wordChar <= 90)
                    || ((int) wordChar >= 97 && (int) wordChar <= 122))
            {
                pinYin.append(wordChar);
            }
        }

        return pinYin.toString();
    }

    public static void main(String[] args) {
        CharacterPinYinConvert convert = new CharacterPinYinConvert();
        System.out.println(convert.toPinyin("林锦佳"));;
    }
}
