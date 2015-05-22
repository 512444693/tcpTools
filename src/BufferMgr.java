import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by zhangmin on 2014/12/25.
 */
public class BufferMgr {
    private byte[] buffer = null;
    int position = 0;
    public BufferMgr(){
        buffer = new byte[0];
    }
    public byte[] getBuffer(){
        return this.buffer;
    }
    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
        position = 0;
    }
    public void put(Data data) throws UnknownHostException,IllegalArgumentException {
        switch (data.getDataType())
        {
            case ONEBYTE:
                //byte b = Byte.parseByte(data.getDataValue()); 只能转换-127-128
                byte b =(byte) Short.parseShort(data.getDataValue());
                buffer = ByteUtils.bytesMerger(buffer, b);
                break;
            case TWOBYTES:
                short s = (short) Integer.parseInt(data.getDataValue());
                buffer = ByteUtils.bytesMerger(buffer,ByteUtils.short2Byte(s));
                break;
            case FOURBYTES:
                int i = (int) Long.parseLong(data.getDataValue());
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

    /**
     * 加入数据的长度
     */
    public void encode(){
        byte[] head = ByteUtils.subBytes(buffer,0,8);
        int len = buffer.length-8;
        byte[] tail = ByteUtils.subBytes(buffer,8,buffer.length-8);
        buffer = ByteUtils.bytesMerger(head,ByteUtils.int2Bytes(len));
        buffer = ByteUtils.bytesMerger(buffer,tail);
    }
    public void decode(){
        byte[] head = ByteUtils.subBytes(buffer,0,8);
        int len = ByteUtils.bytes2Int(ByteUtils.subBytes(buffer,8,4));
        if( len != buffer.length-12 )
            throw new IllegalStateException("解码失败，长度不一致");
        byte[] tail = ByteUtils.subBytes(buffer,12,buffer.length-12);
        buffer = ByteUtils.bytesMerger(head,tail);
    }
    public void get(Data data) throws UnknownHostException {
        switch (data.getDataType())
        {
            case ONEBYTE:
                if((buffer.length - position) < 1)
                    throw new IllegalStateException(data.getDataName()+" 解码失败");
                data.setDataValue(buffer[position]+"");
                position++;
                break;
            case TWOBYTES:
                if((buffer.length - position) < 2)
                    throw new IllegalStateException(data.getDataName()+" 解码失败");
                data.setDataValue(ByteUtils.bytes2Short(ByteUtils.subBytes(buffer, position, 2))+"");
                position+=2;
                break;
            case FOURBYTES:
                if((buffer.length - position) < 4)
                    throw new IllegalStateException(data.getDataName()+" 解码失败");
                data.setDataValue(ByteUtils.bytes2Int(ByteUtils.subBytes(buffer, position, 4))+"");
                position+=4;
                break;
            case IP:
                if((buffer.length - position) < 4)
                    throw new IllegalStateException(data.getDataName()+" 解码失败");
                data.setDataValue(ByteUtils.bytes2Ip(ByteUtils.subBytes(buffer, position, 4)));
                position+=4;
                break;
            case EIGHTBYTES:
                if((buffer.length - position) < 8)
                    throw new IllegalStateException(data.getDataName()+" 解码失败");
                data.setDataValue(ByteUtils.bytes2Long(ByteUtils.subBytes(buffer, position, 8))+"");
                position+=8;
                break;
            case STRING:
                if((buffer.length - position) < 4)
                    throw new IllegalStateException(data.getDataName()+" 解码失败");
                int len = ByteUtils.bytes2Int(ByteUtils.subBytes(buffer, position, 4));
                position+=4;
                if((buffer.length - position) < len)
                    throw new IllegalStateException(data.getDataName()+" 解码失败");
                data.setDataValue(new String(ByteUtils.subBytes(buffer,position,len)));
                position+=len;
                break;
            case HEXSTRING:
                if((buffer.length - position) < 4)
                    throw new IllegalStateException(data.getDataName()+" 解码失败");
                int len2 = ByteUtils.bytes2Int(ByteUtils.subBytes(buffer, position, 4));
                position+=4;
                if((buffer.length - position) < len2)
                    throw new IllegalStateException(data.getDataName()+" 解码失败");
                data.setDataValue(ByteUtils.bytes2Hex(ByteUtils.subBytes(buffer,position,len2)).toString());
                position+=len2;
                break;
            case ARRAY:

                break;
        }
    }
}
