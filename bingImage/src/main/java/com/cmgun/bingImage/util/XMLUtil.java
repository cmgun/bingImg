package com.cmgun.bingImage.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.List;

/**
 * Created by cmgun on 2017/4/2.
 */
public class XMLUtil {

    public static JSONArray parseXML(String xml) {
        try {
            JSONArray images = new JSONArray();
            Document doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();
            List<Element> nodes = root.elements("image");
            for (Element node : nodes) {
                JSONObject image = new JSONObject();
                String fileName = node.element("startdate").getStringValue();
                String url = node.element("url").getStringValue();
                image.put("fileName", fileName);
                image.put("url", url);
                images.add(image);
            }
            return images;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}