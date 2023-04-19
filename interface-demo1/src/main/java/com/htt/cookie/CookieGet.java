package com.htt.cookie;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CookieGet {
    private String url;
    private ResourceBundle bundle;
    //用来存cookie
    CookieStore cookie;
    //读配置文件
    @BeforeTest
    public void beforeTest(){
        bundle=ResourceBundle.getBundle("application", Locale.CHINA);
        url=bundle.getString("test.url");
    }
    @Test
    public void getCookie() throws IOException {
        String result;
        String uri=bundle.getString("getCookie.uri");
        String testUrl=this.url+uri;
        HttpGet get=new HttpGet(testUrl);
        //用来执行get方法
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity(),"UTF-8");
        System.out.println(result);
        //获取cookie信息
        this.cookie = client.getCookieStore();
        List<Cookie> cookies = cookie.getCookies();
        for(Cookie cookie:cookies){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println(name+":"+value+";");
        }
    }
    @Test(dependsOnMethods = {"getCookie"})
    public void testWithCookie() throws IOException {
        String result;
        String uri=bundle.getString("withCookie.uri");
        String testUrl=this.url+uri;
        HttpGet get=new HttpGet(testUrl);
        //用来执行get方法
        DefaultHttpClient client = new DefaultHttpClient();
        //设置cookie信息
        client.setCookieStore(this.cookie);
        HttpResponse execute = client.execute(get);
        //获取响应状态码
        int statusCode = execute.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        if(statusCode==200){
            result = EntityUtils.toString(execute.getEntity(),"UTF-8");
            System.out.println(result);
        }
    }
}
