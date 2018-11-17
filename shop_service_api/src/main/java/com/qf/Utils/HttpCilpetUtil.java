package com.qf.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpCilpetUtil {

    /**
     * httpclient工具方法 - 传递json字符串
     * http:
     * get传参, url?key=value
     * post传参， url?key=value, 请求体：key=value&key=value
     *
     * 传递json：
     * 请求体：json
     */
    public  static  String sendJonsPost(String url,String json){
        //1获得HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        //2使用Post请求
        HttpPost httpPost = new HttpPost(url);
        //设置请求头，请求体
        httpPost.setHeader(new BasicHeader("Content-Type","application/json"));
        httpPost.setEntity(new StringEntity(json,"utf-8"));


        try {
            //3发送POST请求
            CloseableHttpResponse response = httpClient.execute(httpPost);

            //获得响应体
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity);
            return s;

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(httpClient!=null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return null;
    }

    public static  String sendPostParamAndHeader(String url,
                                                 Map<String,String> params, Map<String,String> headers){
        //获取HttpClient对象
        CloseableHttpClient build = HttpClientBuilder.create().build();

        //设置请求为post请求
        HttpPost httpPost = new HttpPost(url);
        try {
            //设置请求头
            if(headers!=null){
                for(Map.Entry<String,String> header:headers.entrySet()){
                    System.out.println("header="+header.getKey()+":"+header.getValue());
                    httpPost.addHeader(header.getKey(),header.getValue());

                }

            }
            //设置请求体
            List<NameValuePair> paramList =new ArrayList<>();

            if(params != null){
                for(Map.Entry<String,String> param : params.entrySet()){
                    System.out.println("param"+param.getKey()+":"+param.getValue());
                    paramList.add(new BasicNameValuePair(param.getKey(),param.getValue()));

                }

            }
            //设置编码
            httpPost.setEntity(new UrlEncodedFormEntity(paramList,"utf-8"));
            //发送Post请求
            CloseableHttpResponse response = build.execute(httpPost);
            //获得响应体
            HttpEntity entity = response.getEntity();
            String str = EntityUtils.toString(entity);

            return str;

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }


        return null;
    }


}
