package com.htt.demo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;

public class Demo1 {
    @Test
    public void test() throws IOException {
        //用来存放结果
        String result;
        HttpGet get=new HttpGet("http://www.baidu.com");
        //用来执行get方法
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(get);
        String s = EntityUtils.toString(response.getEntity(),"UTF-8");
        System.out.println(s);
    }
	@Test
    public void test1(){
        System.out.println("测试git代码提交");
    }
}
