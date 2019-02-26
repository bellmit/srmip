package com.kingtake.common.util;

/**
 * 序号生成规则，09后面变成0A，一直到0Z，然后就是10
 * 
 * @author admin
 * 
 */
public class CodeGenerateUtils {

    /// <summary>
    /// 增加节点号，规则：从1开始，如果到9则变为A，直到变为Z为止； 当为Z时，就给前一位的数字加1；
    /// </summary>
    /// <param name="str">当前节点的字符串</param>
    /// <param name="leng">当前节点的长度</param>
    /// <returns>返回处理之后的字符串</returns>
    public static String getLayNo(String str, int leng) {
        String strC = str.substring(leng - 1);
        String strS = str.substring(0, leng - 1);

        int ascI = 0;
        ascI = asc(strC);

        String chrI;
        if (ascI == 57) //ASCII对应值为9
        {
            ascI = ascI + 8; //ASCII对应值为A
            chrI = chr(ascI);
        } else if (ascI == 90) //ASCII对应值为Z
        {
            ascI = 48; //ASCII对应值为0
            strS = getLayNo(strS, --leng); //进入递归找前一位
            chrI = chr(ascI);
        } else {
            ++ascI;
            chrI = chr(ascI);
        }

        return strS + chrI;
    }

    /// <summary>
    /// 获取字符的ASCII码值
    /// </summary>
    /// <param name="character">当前要处理的字符</param>
    /// <returns>处理之后的ASCII码整型值</returns>
    public static int asc(String character) {
        char[] chars = character.toCharArray();
        return Integer.valueOf(chars[0]);
    }

    /// <summary>
    /// 将ASCII码值转换为其对应的字符
    /// </summary>
    /// <param name="asciiCode">当前所要转换的ASCII码值</param>
    /// <returns>返回转换之后的字符</returns>
    public static String chr(int asciiCode) {
        String str = "";
        if (asciiCode >= 0 && asciiCode <= 255) {
            char[] chars = Character.toChars(asciiCode);
            str = new String(chars);
        }
        return str;
    }

    public static void main(String args[]) {

    }
}
