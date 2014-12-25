package com.zm.test;

import java.util.ArrayList;

/**
 * Created by zhangmin on 2014/12/25.
 */
enum DataType{
    ONEBYTE,TWOBYTES,FOURBYTES,IP,EIGHTBYTES,STRING,HEXSTRING,ARRAY
}
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
    public Data(DataType dataType, String dataName, String dataValue)
    {
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
}
