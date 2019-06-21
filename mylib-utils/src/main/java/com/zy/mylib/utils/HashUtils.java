package com.zy.mylib.utils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author 许乐
 * @date 2017/3/2
 */
public class HashUtils{

    public static String getHash(File file) throws Exception {
        byte[] b = new byte[0];
        b = createChecksum(file);

        String result = "";
        for (int i = 0; i < b.length; i++) {
            //加0x100是因为有的b[i]的十六进制只有1位
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    public static byte[] createChecksum(File file) throws IOException, NoSuchAlgorithmException {
        InputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        //如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;
        do {
            //从文件读到buffer，最多装满buffer
            numRead = fis.read(buffer);
            if (numRead > 0) {
                //用读到的字节进行MD5的计算，第二个参数是偏移量
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);
        fis.close();
        return complete.digest();
    }

    /**
     * 用于获取一个String的md5值
     * @param str
     * @return
     */
    public static String getMd5(String str) throws Exception{
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bs = md5.digest(str.getBytes());

        StringBuilder sb = new StringBuilder(40);
        for(byte x:bs) {
            if((x & 0xff)>>4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }

}