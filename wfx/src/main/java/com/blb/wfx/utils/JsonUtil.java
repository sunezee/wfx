package com.blb.wfx.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class JsonUtil {

    private static final ObjectMapper mapper=new ObjectMapper();
    private static final ObjectMapper localMapper=new ObjectMapper();

    /**
     * 对象转为Json字符串
     * @param object
     * @return
     * @throws IOException
     */
    public static String encode(Object object) throws IOException{
        StringWriter out=new StringWriter();
        encode(object,out,mapper);
        return out.toString();
    }


    /**
     * 字符串转为对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T decode(String json, Class<T> clazz) throws IOException {
        JsonParser parser=null;
        T object=null;
        try{
            parser=mapper.getFactory().createParser(json);
            object=parser.readValueAs(clazz);
        }finally {
            if(parser!=null){
                try{
                    parser.close();
                }catch (IOException e){
                }
            }
        }
        return object;
    }

    public static  void encode(Object object, Writer out,ObjectMapper mapper) throws IOException {
        JsonGenerator json=null;
        try{
            json=mapper.getFactory().createGenerator(out);
            json.writeObject(object);
            json.flush();
        }finally {
            if(json!=null){
                try{
                   json.close();
                }catch (IOException e){
                }
            }
        }

    }

}
