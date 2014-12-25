package com.zm.test;


import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 网络序
 * Created by zhangmin on 2014/12/24.
 */
public class ByteUtils {

    /**0-65535
     *short(2个字节) 转换成字节数组，返回
     * @param num
     * @return
     */
    public static byte[] short2Byte(short num)
    {
        byte[] data = new byte[2];
        data[0] = (byte) (num >> 8 & 0xff);
        data[1] = (byte) (num &0xff);
        return data;
    }
    /**
     *字节数组转换成short，如果字节数组大小不是2或者为null，抛出“非法参数异常”
     * @param data
     * @return
     * @throws IllegalArgumentException
     */
    public static short bytes2Short(byte[] data)throws IllegalArgumentException
    {
        if(data.length != 2 || data == null)
            throw new IllegalArgumentException();
        short num = 0;
        return (short) ((data[0] & 0xff) << 8 | (data[1] & 0xff));
    }

    /**0-4294967295
     * int（4个字节）转换成字节数组，返回
     * @param num
     * @return
     */
    public static byte[] int2Bytes(int num)
    {
        byte[] data = new byte[4];
        data[0] = (byte) (num >> 24 & 0xff);
        data[1] = (byte) (num >> 16 & 0xff);
        data[2] = (byte) (num >> 8 & 0xff);
        data[3] = (byte) (num & 0xff);
        return data;
    }
    /**
     *字节数组转换成int，如果字节数组大小不是4或者为null，抛出“非法参数异常”
     * @param data
     * @return
     * @throws IllegalArgumentException
     */
    public static int bytes2Int(byte[] data)throws IllegalArgumentException
    {
        if(data.length != 4 || data == null)
            throw new IllegalArgumentException();
        int num = 0;
        return (data[0] & 0xff) << 24 |(data[1] & 0xff) << 16 |(data[2] & 0xff) << 8 | (data[3] & 0xff);
    }

    /**
     *long（8个字节）转换成字节数组，返回
     * @param l
     * @return
     */
    public static byte[] long2Bytes(long l){
        byte[] byteArray = new byte[8];
        for (int i=7; i>=0; i--){
            byteArray[i] = new Long(l & 0xff).byteValue();
            l >>= 8;
        }
        return byteArray;
    }
    /**
     *字节数组转换成long，如果字节数组大小不是8或者为null，抛出“非法参数异常”
     * @param b
     * @return
     * @throws IllegalArgumentException
     */
    public static long bytes2Long(byte[] b)throws IllegalArgumentException
    {
        if(b.length != 8 || b == null)
            throw new IllegalArgumentException();
        long iOutcome = 0;
        byte bLoop;
        for (int i = 0; i < 8; i++)
        {
            bLoop = b[i];
            iOutcome += ((long)(bLoop & 0x000000ff)) << (8 * (7-i));
        }
        return iOutcome;
    }
    /**
     * 把byte[]转换成十六进制字符串
     * @param data
     * @return
     */
    public static StringBuffer bytes2Hex(byte[] data) {
        StringBuffer buf = new StringBuffer(data.length * 2);
        for (int i = 0; i < data.length; i++) {
            if (((int) data[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) data[i] & 0xff, 16));
        }
        return buf;
    }
    /**
     * 把byte[]中从off开始的 length个字符转换成十六进制字符串
     * @param data
     * @param off
     * @param length
     * @return
     */
    public static StringBuffer bytes2Hex(byte[] data, int off, int length) {
        StringBuffer buf = new StringBuffer(data.length * 2);
        for (int i = off; i < length; i++) {
            if (((int) data[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) data[i] & 0xff, 16));
        }
        return buf;
    }

    /**
     * 把byte[]转换成十六进制字符串,加入空格和换行使易于观看
     * @param data
     * @return
     */
    public static StringBuffer bytes2HexGoodLook(byte[] data) {
        StringBuffer buf = new StringBuffer(data.length * 2);
        int len = 0;
        for (int i = 0; i < data.length; i++) {
            if (((int) data[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) data[i] & 0xff, 16));
            len++;
            buf.append(" ");
            if(len%8 ==0)
                buf.append("\t");
            if(len%16 ==0)
                buf.append("\r\n");
        }
        return buf;
    }
    /**
     *把16进制字符串转换成字节数组，如果字符串长度为奇数，则在前面加“0”
     * @param hexString
     * @return
     * @throws IllegalArgumentException
     */
    public static byte[] hex2Bytes(String hexString)throws IllegalArgumentException
    {
        if(null == hexString || hexString.equals(""))
            throw new IllegalArgumentException();
        hexString = hexString.toUpperCase();
        for(int i =0; i<hexString.length(); i++)
        {
            char c = hexString.charAt(i);
            if(charToByte(c) == -1)
                throw new IllegalArgumentException();
        }
        if(hexString.length()%2 != 0)
            hexString = "0" + hexString;
        int dataLength = hexString.length()/2;
        byte[] data = new byte[dataLength];

        char c = ' ';
        byte b = 0;
        for(int i=0; i<dataLength; i++)
        {
            c = hexString.charAt(i*2);
            b =(byte) (charToByte(c) << 4);
            c = hexString.charAt(i*2+1);
            b += charToByte(c);
            data[i] = b;
        }
        return data;
    }
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 把ip地址转换成字节数组
     * @param ipString
     * @return
     * @throws UnknownHostException
     * @throws IllegalArgumentException
     */
    public static byte[] ip2Bytes(String ipString) throws UnknownHostException,IllegalArgumentException {
        if(!ipString.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}"))
            throw new IllegalArgumentException();
        InetAddress ip = InetAddress.getByName(ipString);
        return ip.getAddress();
    }

    /**
     * 把字节数组转换成ip地址
     * @param data
     * @return
     * @throws UnknownHostException
     * @throws IllegalArgumentException
     */
    public static String bytes2Ip(byte[] data) throws UnknownHostException,IllegalArgumentException {
        if(data.length != 4 || data == null)
            throw new IllegalArgumentException();
        return InetAddress.getByAddress(data).getHostAddress();
    }

    /**
     * 截取字节数组的一部分
     * @param src
     * @param begin
     * @param count
     * @return
     */
    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i=begin; i<begin+count; i++) bs[i-begin] = src[i];
        return bs;
    }

    /**
     * 数组合并，参数中，第一个数组在前
     * @param arg1
     * @param arg2
     * @return
     */
    public static byte[] bytesMerger(byte[] arg1, byte[] arg2)
    {
        byte[] ret = new byte[arg1.length+arg2.length];
        int position = 0;
        for(int i=0; i<arg1.length; i++)
            ret[position++] = arg1[i];
        for(int i=0; i<arg2.length; i++)
            ret[position++] = arg2[i];
        return ret;
    }
    /**
     * 数组合并，参数中，第一个数组在前,第二个数组有参数
     * @param arg1
     * @param arg2
     * @return
     */
    public static byte[] bytesMerger(byte[] arg1, byte[] arg2, int offset, int length)
    {
        byte[] ret = new byte[arg1.length+length];
        int position = 0;
        for(int i=0; i<arg1.length; i++)
            ret[position++] = arg1[i];
        for(int i=offset; i<length; i++)
            ret[position++] = arg2[i];
        return ret;
    }
    /**
     * 数组与字节合并
     * @param arg1
     * @param arg2
     * @return
     */
    public static byte[] bytesMerger(byte[] arg1, byte arg2)
    {
        byte[] ret = new byte[arg1.length+1];
        int position = 0;
        for(int i=0; i<arg1.length; i++)
            ret[position++] = arg1[i];
        ret[position++] = arg2;
        return ret;
    }
    public static void main(String[] args)
    {
        /**
         * short test
         byte[] data = short2Byte((short) 0xff);
         System.out.println(bytes2Hex(data));
         System.out.println(bytes2Short(data));
         */

        /**
         * int test
         byte[] data = int2Byte(0xffff);
         System.out.println(bytes2Hex(data));
         System.out.println(bytes2Int(data));
         */

        /**
         * long test
         long l = System.currentTimeMillis() << 8 ;
         byte[] data = long2Bytes((long) 0x3fffffff);//int 转换成long时前面4个字节数字和int最高位一样
         System.out.println(bytes2Hex(data));
         System.out.println(bytes2Long(data));
         */
        /**
         byte[] data = hex2Bytes("0123456789abcdef");
         System.out.println(bytes2Hex(data));
         */
        /*
        try {
            byte[] data = ip2Bytes("192.168.2.23");
            System.out.println(bytes2Ip(data));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        */
        /**
         * 对象（自己定义），数组 是引用传递
         * 内置基本数据类型是值传递(包括基本对象)
         *
         */
        //System.out.println(bytes2HexGoodLook("zhangmin123圣诞快乐附近开健身房".getBytes()));
        /*
        byte[] dataRec = new byte[0];
        byte[] dataTemp = "zhangmin".getBytes();
        dataRec = ByteUtils.bytesMerger(dataRec,dataTemp,0,dataTemp.length);
        System.out.println(new String(dataRec));
        */
    }

}