package com.mc.refillCard.util;

public class StringConvertHexUtils {

    /**
     * 十进制数转16进制
     * @param n
     * @return
     */
    public static String intToHex(int n) {
        //StringBuffer s = new StringBuffer();
        StringBuilder sb = new StringBuilder(8);
        String a="";
        char []b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        while(n != 0){
            sb = sb.append(b[n%16]);
            n = n/16;
        }
        if(n==0){
            sb.append("0");
        }
        if(n==1){
            sb.append("1");
        }

        a = sb.reverse().toString();
        int num=a.length();
        switch (num){
            case 1:
                a="00 0"+a;
                break;
            case 2:
                a="00 "+a;
                break;
            case 3:
                a="0"+a.substring(0,1)+" "+a.substring(1);
                break;
            case 4:
                a=a.substring(0,2)+" "+a.substring(2);
                break;
                default:break;
        }
        return a;
    }

    /**
     * byte转Hex
     */
    public static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if(hex.length() < 2) {
            hex = "0" + hex;
        }
        return hex;
    }
    /**
     * byte[]转Hex
     */
    public static String bytesToHex(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if(hex.length() < 2) {
                hex = "0" + hex;
            }
            sb.append(hex.toUpperCase()+" ");
        }

        return sb.toString();
    }

    /**
     * Hex转byte,hex只能含两个字符，如果是一个字符byte高位会是0
     */
    public static byte hexTobyte(String hex) {
        return (byte)Integer.parseInt(hex, 16);
    }
    /**
     * Hex转byte[]，两种情况，Hex长度为奇数最后一个字符会被舍去
     */
    public static byte[] hexTobytes(String hex) {
        if (hex.length() < 1) {
            return null;
        } else {
            byte[] result = new byte[hex.length() / 2];
            int j = 0;
            for(int i = 0; i < hex.length(); i+=2) {
                result[j++] = (byte)Integer.parseInt(hex.substring(i,i+2), 16);
            }
            return result;
        }
    }
}
