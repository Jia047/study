package com.jia.number.judge;

public class IsNum{

    public static void main(String[] args){
        char [] str = {'1', '2', 'e'};
        System.out.println(isNumeric(str));
    }

    public static boolean isNumeric(char[] str) {
        if(str == null || "".equals(str)){
            return false;
        }
        int i = 0;

        if(str[i] == '+' || str[i] == '-'){
            i++;
        }
        boolean hasDot = false;
        boolean hasE = false;
        boolean hasNum = false;
        boolean hasMinus = false;
        while(i < str.length){
            if(isNum(str[i])){
                hasNum = true;
            } else if(str[i] == 'e' || str[i] == 'E'){
                if(!hasNum || hasE || i == str.length - 1){
                    return false;
                }
                hasE = true;
            } else if(str[i] == '.'){
                if(!hasNum || hasE || hasDot){
                    return false;
                }
                hasDot = true;
            } else if(str[i] == '-'){
                if(!hasNum || !hasE || hasMinus || i == str.length - 1){
                    return false;
                }
                if(str[i-1] != 'e' || str[i-1] != 'E'){
                    return false;
                }
                hasMinus = true;
            }
            i++;
        }

        return true;
    }

    private static boolean isNum(char c){
        return c >= '0' && c <= '9';
    }
}
