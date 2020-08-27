package com.motor.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerConverter;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: zlj
 * @date: 2019-09-26 下午8:31
 * @description:
 */
public class StringToDateConverter extends DozerConverter<String, Date> {

    public StringToDateConverter() {
        super(String.class, Date.class);
    }

    @Override
    public String convertFrom(Date source, String destination) {
        if(source == null){
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        destination = formatter.format(source);
        return destination;
    }

    @Override
    public Date convertTo(String source, Date destination) {
        if(StringUtils.isEmpty(source)){
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        destination = formatter.parse(source, pos);
        return destination;
    }

}
