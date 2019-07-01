package com.crm.core;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.crm.common.MessageException;
import com.crm.common.MessageException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * json传递日期格式化重写处理
* @ClassName: CustomJsonDateDeserializer
* @pack:com.by.crm.channel.core
* @Description:
* @author:zhoujingsong
* @date 2017年9月25日 下午2:03:05
*
 */
public class CustomJsonDateDeserializer extends JsonDeserializer<Date> {
	  
    @Override  
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
                String date = jp.getText();  
                
                try {  
                    return format.parse(date);  
                } catch (ParseException e) {  
                    throw new MessageException(e.getMessage());
                }  
    }  
  
}  