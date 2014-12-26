package com.zm.test;

import java.lang.reflect.Array;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.util.ArrayList;

/**
 * Created by zhangmin on 2014/12/25.
 */
public class BufferMgr {
    private byte[] buffer = null;
    public BufferMgr(){
        buffer = new byte[0];
    }
    public byte[] getBuffer(){
        return this.buffer;
    }
    public void put(Data data) throws UnknownHostException,IllegalArgumentException {
        switch (data.getDataType())
        {
            case ONEBYTE:
                //byte b = Byte.parseByte(data.getDataValue()); 只能转换-127-128
                byte b =(byte) Integer.parseInt(data.getDataValue());
                buffer = ByteUtils.bytesMerger(buffer,b);
                break;
            case TWOBYTES:
                short s = Short.parseShort(data.getDataValue());
                buffer = ByteUtils.bytesMerger(buffer,ByteUtils.short2Byte(s));
                break;
            case FOURBYTES:
                int i = Integer.parseInt(data.getDataValue());
                buffer = ByteUtils.bytesMerger(buffer,ByteUtils.int2Bytes(i));
                break;
            case IP:
                String ip = data.getDataValue();
                buffer = ByteUtils.bytesMerger(buffer,ByteUtils.ip2Bytes(ip));
                break;
            case EIGHTBYTES:
                long l = Long.parseLong(data.getDataValue());
                buffer = ByteUtils.bytesMerger(buffer,ByteUtils.long2Bytes(l));
                break;
            case STRING:
                String str = data.getDataValue();
                buffer = ByteUtils.bytesMerger(buffer,ByteUtils.int2Bytes(str.length()));
                buffer = ByteUtils.bytesMerger(buffer,str.getBytes());
                break;
            case HEXSTRING:
                String hex = data.getDataValue();
                byte[] temp = ByteUtils.hex2Bytes(hex);
                buffer = ByteUtils.bytesMerger(buffer,ByteUtils.int2Bytes(temp.length));
                buffer = ByteUtils.bytesMerger(buffer,temp);
                break;
            case ARRAY:
                ArrayList<Data[]> arrayList = data.getDataList();
                buffer = ByteUtils.bytesMerger(buffer,ByteUtils.int2Bytes(arrayList.size()));
                for(int j=0; j<arrayList.size(); j++)
                    for(int m=0; m<arrayList.get(j).length; m++)
                    {
                        put(arrayList.get(j)[m]);
                        System.out.println(arrayList.get(j)[m].getDataValue().equals("")?"":
                                arrayList.get(j)[m].getDataName()+" value is "+arrayList.get(j)[m].getDataValue());
                    }
                break;
        }
    }
}
