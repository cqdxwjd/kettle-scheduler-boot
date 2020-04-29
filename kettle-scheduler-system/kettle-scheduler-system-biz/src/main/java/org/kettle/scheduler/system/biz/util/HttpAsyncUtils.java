package org.kettle.scheduler.system.biz.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
public class HttpAsyncUtils {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static CloseableHttpAsyncClient httpAsyncClient = null;

    static {
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
            PoolingNHttpClientConnectionManager pool = new PoolingNHttpClientConnectionManager(ioReactor);
            pool.setMaxTotal(200);              //设置最多连接数
            pool.setDefaultMaxPerRoute(20);     //设置每个host最多20个连接数
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(3000)         //设置请求响应超时时间
                    .setConnectTimeout(3000)        //设置请求连接超时时间
                    .build();
            httpAsyncClient = HttpAsyncClients.custom()
                    .setConnectionManager(pool)                //设置连接池
                    .setDefaultRequestConfig(requestConfig)    //设置请求配置
                    .build();
        } catch (IOReactorException e) {
            e.printStackTrace();
        }
        httpAsyncClient.start();                                //启动异步连接
    }

    public static Future<HttpResponse> post(String url, Object param) {
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        if (param != null) {
            Map<String, Object> map = OBJECT_MAPPER.convertValue(param, Map.class);  //通过jackson转换参数
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() != null) {
                    NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                    paramList.add(pair);
                }
            }
        }

        final HttpPost post = new HttpPost(url);        //创建POSt请求
        HttpEntity entity = new UrlEncodedFormEntity(paramList, Charset.forName("utf-8"));
        post.setEntity(entity);                         //设置请求参数
        //发送请求并返回future
        return httpAsyncClient.execute(post, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse response) {
                System.out.println("执行接口completed:"+post.getRequestLine() + "->" + response.getStatusLine());
            }

            @Override
            public void failed(Exception ex) {
                System.out.println("执行接口failed"+post.getRequestLine() + "->" + ex);
            }

            @Override
            public void cancelled() {
                System.out.println("执行接口cancelled:"+post.getRequestLine() + " cancelled");
            }
        });
    }
    public static void get(String url) {
        final HttpGet get = new HttpGet(url);        //创建get请求
        //发送请求并返回future
        httpAsyncClient.execute(get,null);
    }

}
