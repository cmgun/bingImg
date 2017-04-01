package com.cmgun.bingImage.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmgun.bingImage.util.XMLUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;

/**
 * Created by cmgun on 2017/4/2.
 */
public class MyServer {
    private static final String URL = "http://cn.bing.com/HPImageArchive.aspx?n=10&pid=hp";
    private static final String IMG_PERFIX = "http://cn.bing.com";
    private static final String IMG_SAVEPATH = "C:\\Users\\cmgun\\Pictures\\Saved Pictures\\";

    public static void main(String[] args) {
        byte[] responseBody = doGet(URL);
        if (responseBody != null) {
            JSONArray images = XMLUtil.parseXML(new String(responseBody));
            System.out.println(images.toJSONString());
            for (Object o : images) {
                JSONObject imageInfo = (JSONObject) o;
                downloadImage(IMG_SAVEPATH + imageInfo.getString("fileName") + ".jpg", IMG_PERFIX + imageInfo.getString("url"));
            }
        }
    }

    private static void downloadImage(String filePath, String urlPath) {
        try {
            byte[] data = doGet(urlPath);
            FileImageOutputStream fios = new FileImageOutputStream(new File(filePath));
            fios.write(data, 0, data.length);
            fios.close();
            System.out.println("download success! image save in" + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] doGet(String url) {
        HttpClient client = new HttpClient();
        HttpMethod getMethod = new GetMethod(url);
        int statusCode;
        byte[] responseBody = null;
        try {
            statusCode = client.executeMethod(getMethod);
            if (statusCode == HttpStatus.SC_OK) {
                responseBody = getMethod.getResponseBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            getMethod.releaseConnection();
        }
        return responseBody;
    }
}