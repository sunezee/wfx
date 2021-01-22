package com.blb.wfx.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * 插入数据的工具类
 */
public class PublicUtil {

    public static void main(String[] args) {
        String md5 = encryption("md5", "123456", "jack520007", 5);
        System.out.println(md5);
    }


    /**
     * 字符串分割成数组
     * @param s 要分割的字符串
     * @param p 分割的标志符
     * @return
     */
    public static String[] stringToArray(String s,String p){
        String [] a=s.split(p);
        return  a;
    }




    /**
     * 生成指定位数的随机数
     * @param length 自定义随机数的长度
     * @param start  自定义随机数的首位数字
     * @return
     */
    public static String getRandom(int length,String start){
        String val = start;
        if(start.isEmpty()){
            val="1";
        }
        Random random = new Random();
        for (int i = 0; i < length-1; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }

    /**
     * 随机获取1到3位之间的随机数
     * @return
     */
    public static String getOneToThreeRandom(){
        Random random = new Random();
        int a=random.nextInt(3)+1;
        String val="";
        for (int i = 0; i < a; i++) {
            val += String.valueOf(random.nextInt(9)+1);
        }
        return  val;
    }

    /**
     * 获取固定位数随机数,且首位在1-9之间随机变动
     * @param length
     * @return
     */
    public static String getRondomChangeFirst(int length){
        Random random = new Random();
        int a=random.nextInt(9)+1;
        String val = String.valueOf(a);
        for (int i = 0; i < length-1; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return  val;

    }

    /**
     * 给密码加密加盐
     * @param methods 加密方法
     * @param password 输入密码
     * @param salt 输入盐
     * @param num 加密次数
     * @return
     */
    public static String encryption(String methods,String password,String salt,int num){
        SimpleHash simpleHash=new SimpleHash(methods,password, ByteSource.Util.bytes(salt),num);
        return  simpleHash.toString();
    }

    /**
     * 时间戳转字符串时间
     * @param timestamp
     * @param flag 为真获取年月日时分秒，否则只获取年月日
     * @return
     */
    public static String timestamp2String(Timestamp timestamp,boolean flag) {
        if(flag){
            String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp.getTime());  // 获取年月日时分秒
            return datetime;
        }else{
            String date = new SimpleDateFormat("yyyy-MM-dd").format(timestamp.getTime());  // 获取只有年月日的时间
            return date;
        }
    }

    /**
     *字符串时间转换换成timestamp
     * @param time 2018-05-06 10:30:40
     * @return
     */
    public static Timestamp string2timestamp(String time) {
        Timestamp timestamp =Timestamp.valueOf(time);
        return timestamp;
    }
}
