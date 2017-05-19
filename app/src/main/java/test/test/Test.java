package test.test;

import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by admin on 2017/5/18.
 */

public class Test {
    //1962a3b8d97d0decce730c2dceadc951
    //7f926f8d5002d3ad2382eff4b561a8fe
    public static void main(String  args[]){

//        String key = "0101010101010101";

//        String str = md5("发生发生大幅度上发电示范试点");
//        String str = md5(new File("F:/新建文本文档.txt"));
//        System.out.println(str);
////      decrypt(key,encrypt(key,pass));
//        File oriFile = new File("F:/新建文本文档.txt");
//        File entryFile = new File("F:/entry.txt");
//        File detryFile = new File("F:/detry.txt");
//        encrypt(key,oriFile,entryFile);
//        decrypt(key,entryFile,detryFile);

    }

    private static String md5(File file){
        FileInputStream fis = null;
        try {
            MessageDigest  digest = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[1024 * 4];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }

            return String.format("%32x", new BigInteger(1,digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
             try {
                 if(fis!=null)
                 fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static String md5(String string) {
        byte[] hash;
        StringBuilder hex = null;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
            hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10) hex.append("0");
                hex.append(Integer.toHexString(b & 0xFF));
            }
        } catch (Exception e) {

        }
        return hex.toString();
    }



    private static String decrypt(String password, String pass) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
            SecretKeySpec key = new SecretKeySpec(password.getBytes("UTF-8"), "AES");
            IvParameterSpec ivp = new IvParameterSpec(password.getBytes("UTF-8"));
            cipher.init(Cipher.DECRYPT_MODE, key, ivp);// 初始化
            return new String(cipher.doFinal(parseHexStr2Byte(pass)),"UTF-8");
        } catch (Exception e) {

        }
        return null;
    }

    private static String encrypt(String password,String origin){

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
            SecretKeySpec key = new SecretKeySpec(password.getBytes("UTF-8"), "AES");
            IvParameterSpec ivp = new IvParameterSpec(password.getBytes("UTF-8"));
            cipher.init(Cipher.ENCRYPT_MODE, key, ivp);// 初始化
            return  byte2hex(cipher.doFinal(origin.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String temp;
        for (int n = 0; n < b.length; n++) {
            temp = (Integer.toHexString(b[n] & 0XFF));
            if (temp.length() == 1) {
                hs = hs + "0" + temp;
            } else {
                hs = hs + temp;
            }
        }
        return hs.toUpperCase();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }




    public static boolean encrypt(String password, File inPutFile, File outPutFile) {
        return aesCipher(Cipher.ENCRYPT_MODE, password, inPutFile, outPutFile);
    }

    public static boolean decrypt(String password, File inPutFile, File outPutFile) {
        return aesCipher(Cipher.DECRYPT_MODE, password, inPutFile, outPutFile);
    }

    private static boolean aesCipher(int mode, String password, File inPutFile, File outPutFile) {
        try {
            FileInputStream fis = new FileInputStream(inPutFile);
            FileOutputStream fos = new FileOutputStream(outPutFile);
            SecretKeySpec key = new SecretKeySpec(password.getBytes("UTF-8"), "AES");
            IvParameterSpec ivp = new IvParameterSpec(password.getBytes("UTF-8"));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
            cipher.init(mode, key, ivp);// 初始化
            CipherInputStream cis = new CipherInputStream(fis, cipher);
            byte[] buffer = new byte[1024 * 10];
            int r;
            while ((r = cis.read(buffer)) > 0) {
                fos.write(buffer, 0, r);
            }
            fos.flush();
            cis.close();
            fis.close();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
