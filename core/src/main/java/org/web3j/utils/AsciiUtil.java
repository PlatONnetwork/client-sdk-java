package org.web3j.utils;

public class AsciiUtil {
    public static int toAscii(String character){
        byte[] bytestr = character.getBytes();
        int sum = 0;
        for(int i=0;i<bytestr.length;i++){
            sum += bytestr[i];
        }
        return sum;
    }
}
