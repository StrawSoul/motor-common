package com.motor.common.table.utils;


/**
 * @author zlj
 */
public class ColumnUtils {

    private final static String SPLIT_FALG = "_";
    private final static char DOT = '.';
    /**
     * 转换驼峰
     *
     * @param str 要转换的字符串(只能转换以_分割的字符串)
     * @return String
     */
    public static String convertHump(String str) {
        if (str == null) {str = new String();}
        StringBuffer stringBuffer = new StringBuffer();
        String[] words = str.split(SPLIT_FALG);
        if (words.length == 1) {return str.toLowerCase();}
        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase();
            if (i == 0) {
                stringBuffer.append(word);
                continue;
            }
            stringBuffer.append(word.substring(0, 1).toUpperCase() + word.substring(1));
        }
        return stringBuffer.toString();
    }


    /**
     *  驼峰转下划线
     * @param para
     * @return
     */
    public static String convertHumpToUnderline(String para){
        StringBuilder sb=new StringBuilder(para);
        if (!para.contains(SPLIT_FALG) ) {
            int tmp = 0;
            for(int i=0;i<para.length();i++){
                if(i>0){
                    if(Character.isLowerCase(para.charAt(i)) && Character.isUpperCase(para.charAt(i-1))){
                        if(i>2 && DOT == para.charAt(i-2)){
                            continue;
                        }
                        sb.insert((i -1) +tmp, SPLIT_FALG);
                        tmp++;
                        i++;
                    }else if(Character.isUpperCase(para.charAt(i)) && Character.isLowerCase(para.charAt(i-1))){
                        sb.insert(i  +tmp, SPLIT_FALG);
                        tmp++;
                        i++;
                    }
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String para = "HELLOWorldTODAYHappyPointType.CreateUserId.id";
        String s = convertHumpToUnderline(para);
        System.out.println(s);
    }
}
