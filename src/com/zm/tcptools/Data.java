package com.zm.tcptools;

import java.util.ArrayList;

/**
 * Created by zhangmin on 2014/12/25.
 */

public class Data {
    private DataType dataType;
    private String dataName;
    private String dataValue;
    private ArrayList<Data[]> dataList;

    public ArrayList<Data[]> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<Data[]> dataList) {
        this.dataList = dataList;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }
    public Data(DataType dataType, String dataName, String dataValue)throws IllegalArgumentException
    {
        if(dataName.equals(""))
            throw new IllegalArgumentException("名字不要为空哦");
        if(dataValue.equals(""))
            throw new IllegalArgumentException(dataName+"： 数据为空哦");
        switch (dataType)
        {
            case ONEBYTE:
                short b = Short.parseShort(dataValue);
                if(b<Byte.MIN_VALUE || b>(Byte.MAX_VALUE -Byte.MIN_VALUE))
                    throw new IllegalArgumentException(dataName+"： 请输入正确的1个字节的数据");
                break;
            case TWOBYTES:
                int s = Integer.parseInt(dataValue);
                if(s<Short.MIN_VALUE || s>(Short.MAX_VALUE -Short.MIN_VALUE))
                    throw new IllegalArgumentException(dataName+"： 请输入正确的2个字节的数据");
                break;
            case FOURBYTES:
                long ii = Long.parseLong(dataValue);
                if(ii<Integer.MIN_VALUE || ii>((long)Integer.MAX_VALUE -(long)Integer.MIN_VALUE))
                    throw new IllegalArgumentException(dataName+"： 请输入正确的4个字节的数据");
                break;
            case IP:
                if(!dataValue.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}"))
                    throw new IllegalArgumentException(dataName+"： 请输入正确的ip地址");
                break;
            case EIGHTBYTES:
                if(!dataValue.matches("[0-9]{1,}"))
                    throw new IllegalArgumentException(dataName+"： 请输入正确的8个字节的数据");
                break;
            case STRING:
                break;
            case HEXSTRING:
                String tmp = dataValue.toUpperCase();
                for(int i =0; i<tmp.length(); i++)
                {
                    char c = tmp.charAt(i);
                    if(ByteUtils.charToByte(c) == -1)
                        throw new IllegalArgumentException(dataName+"： 请输入正确的16进制数据");
                }
                break;
        }
        this.dataType = dataType;
        this.dataName = dataName;
        this.dataValue = dataValue;
        this.dataList = null;
    }
    public Data(DataType dataType, String dataName, ArrayList<Data[]> arrayList)
    {
        this.dataType = dataType;
        this.dataName = dataName;
        this.dataValue = "";
        this.dataList = arrayList;
    }
    public Data(DataType dataType, String dataName)throws IllegalArgumentException
    {
        if(dataName.equals(""))
            throw new IllegalArgumentException("名字不要为空哦");
        this.dataType = dataType;
        this.dataName = dataName;
        this.dataValue = "";
        this.dataList = null;
    }
}
