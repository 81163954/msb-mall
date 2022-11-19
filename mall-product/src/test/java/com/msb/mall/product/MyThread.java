package com.msb.mall.product;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyThread implements Runnable{
    public static int i = 3798;
    @Override
    public void run() {
        StringBuffer append = new StringBuffer();
        StringBuffer url;
        StringBuffer urlStart =new StringBuffer("http://hntaste.top/sem/send_edit_code/mobile/1871092");


        for (i = 3798; i < 10000; i++) {
            url = new StringBuffer().append(urlStart);
            StringBuffer append1 = url.append(appendResult(i));
            System.out.println(append1);
            System.out.println(sendRequest(append1.toString(),"GET"));
        }
    }
    public static StringBuffer appendResult(int i){
        StringBuffer stringBuffer = new StringBuffer();
        for (int j = 0; j < (4-LengthNum(i)); j++) {
            stringBuffer.append("0");
        }
        return stringBuffer.append(i);
    }

    public static int LengthNum(int num) {
        int count=0; //计数
        while(num>=1) {
            num/=10;
            count++;
        }
        return count;
    }

    public static String sendRequest(String urlParam,String requestType) {

        HttpURLConnection con = null;

        BufferedReader buffer = null;
        StringBuffer resultBuffer = null;

        try {
            URL url = new URL(urlParam);
            //得到连接对象
            con = (HttpURLConnection) url.openConnection();
            //设置请求类型
            con.setRequestMethod(requestType);
            //设置请求需要返回的数据类型和字符集类型
            con.setRequestProperty("Content-Type", "application/json;charset=GBK");
            //允许写出
            con.setDoOutput(true);
            //允许读入
            con.setDoInput(true);
            //不使用缓存
            con.setUseCaches(false);
            //得到响应码
            int responseCode = con.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                //得到响应流
                InputStream inputStream = con.getInputStream();
                //将响应流转换成字符串
                resultBuffer = new StringBuffer();
                String line;
                buffer = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
                while ((line = buffer.readLine()) != null) {
                    resultBuffer.append(line);
                }
                return resultBuffer.toString();
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
