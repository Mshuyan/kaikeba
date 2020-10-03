package com.kkb.spring.utils;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

public class DocumentUtils {
    public static Document getDocument(InputStream inputStream){
        try {
            SAXReader reader = new SAXReader();
            return reader.read(inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    // 暗号：张助教找媳妇
}
