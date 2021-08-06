package com.anzhen.utils;

import lombok.Data;
import org.pegdown.PegDownProcessor;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Classname : MarkdownUtil
 * @Date : 2021.5.1 8:02
 * @Created : anzhen
 * @Description :
 * @目的:  封装markdown 格式的文件
 */

@Data
public class MarkdownEntity {
    public static String md2Html(String path , @Nullable String imgaddr) throws IOException {
        String html = " ";
        FileReader r = new FileReader(path);
        char[] cbuf = new char[1024];
        while( r.read(cbuf) != -1){
            html += new String(cbuf);
        }

        PegDownProcessor pdp = new PegDownProcessor(Integer.MAX_VALUE);
        html = pdp.markdownToHtml(html);

        if(!StringUtils.isEmpty(imgaddr)){
            //将html中路径"assets/***" 变为 "http://localhost:4000/assets/"
            String newHtml = StringUtils.replace(html, "assets/", imgaddr);
            System.out.println("执行替换操作");
            return newHtml;
        }
        return html;
    }


    // md 转换为 html
    public static String mdHtml(String md) throws IOException {

        PegDownProcessor pdp = new PegDownProcessor(Integer.MAX_VALUE);
        String html = pdp.markdownToHtml(md);
        return html;
    }


    // md 转换为 html
    public static String mdHtml(String md,  @Nullable String imgaddr) throws IOException {

        PegDownProcessor pdp = new PegDownProcessor(Integer.MAX_VALUE);
        String html = pdp.markdownToHtml(md);
        if(!StringUtils.isEmpty(imgaddr)){
            //将html中路径"assets/***" 变为 "http://localhost:4000/assets/"
            String newHtml = StringUtils.replace(html, "assets/", imgaddr);
            System.out.println("执行替换操作");
            return newHtml;
        }
        return html;
    }

    public static String readMd(String path , @Nullable String imgaddr) throws IOException {
        StringBuffer md = new StringBuffer();
        String temp;
        BufferedReader r = new BufferedReader(new FileReader(path));
        while( (temp = r.readLine()) != null){
            md.append(temp + "\r\n");
        }

        if(!StringUtils.isEmpty(imgaddr)){
            String newMd = StringUtils.replace(md.toString(), "assets/", imgaddr);
            System.out.println("执行替换操作");
            System.out.println(newMd);
            return newMd;
        }
        return md.toString();
    }



}