package com.htt.demo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;

public class Demo2 { 
   @Test
    public void test2(){
        System.out.println("测试新增文件，git代码提交");
    }
   @Test
    public void test3(){
        System.out.println("测试从远程仓库拉文件");
    }

    @Test
    public void test4(){
        System.out.println("使用idea提交代码");
    }

    @Test
    public void test5(){
        System.out.println("branch1分支提交的代码");
    }
}
