package com.htt.cookie;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class post {
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
    public void testWithCookie() throws Exception {
        String result;//存响应结果
        String uri=bundle.getString("post.test");
        String testUrl=this.url+uri;
        //用来执行post方法
        DefaultHttpClient client = new DefaultHttpClient();
        //声明一个方法，post方法
        HttpPost post=new HttpPost(testUrl);
        //添加参数
        JSONObject json = new JSONObject();
        json.put("name", "rose");
        json.put("age", "26");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(json.toString(),"UTF-8");
        post.setEntity(entity);
        //设置请求头信息
        post.setHeader("Content-Type","application/html");
        //设置cookie信息
        client.setCookieStore(this.cookie);
        HttpResponse execute = client.execute(post);
        //获取响应状态码
//        int statusCode = execute.getStatusLine().getStatusCode();
//        System.out.println(statusCode);
        result = EntityUtils.toString(execute.getEntity(), "UTF-8");
        System.out.println(result);
        //将返回的响应结果转化为json对象
        JSONObject jsonObject = new JSONObject(result);
        //获取结果
        String message = (String) jsonObject.get("message");
        //判断结果:期望结果，实际结果
        Assert.assertEquals("success",message);

    }
}
