package com.anzhen.service.impl;

import cn.hutool.core.util.StrUtil;
import com.anzhen.config.MyServiceConfig;
import com.anzhen.entity.Blog;
import com.anzhen.mapper.BlogMapper;
import com.anzhen.service.BlogService;
import com.anzhen.utils.MarkdownEntity;
import com.anzhen.utils.jwt.TokenUnavailable;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname : BlogServiceImpl
 * @Date : 2021.4.29 10:12
 * @Created : anzhen
 * @Description :
 * @目的:
 */

@Service
public class BlogServiceImpl implements BlogService {


    static StringBuffer mdContent = null;

    @Resource
    BlogMapper blogMapper;

    @Resource
    MyServiceConfig myServiceConfig;


    @Override
    // 查询全部数据
    public List<Blog> queryAll() {
        return  blogMapper.queryAll();
    }

    @Override
    // 分页查询
    public Map<String, Object> queryAll(int currentPage, int pageSize) {
        // 开始分页
        PageHelper.startPage(currentPage, pageSize);
        Page<Blog> blogByPage = blogMapper.getBlogByPage();
        List<Blog> result = blogByPage.getResult();
        int totalPage = (queryAll().size() + pageSize - 1) / pageSize;
        System.out.println("totalPage  -> " + totalPage);
        Map<String, Object> map = new HashMap<>();
        map.put("total",result.size());
        map.put("items",result);
        map.put("hasNextPage", currentPage >= totalPage? false : true);
        map.put("currPage", currentPage);
        return map;
    }

    @Override
    public Map<String, Object> getBlogByContext(int id) {
        // 传入数据进行查询
        Blog blogByContext = blogMapper.getBlogByContext(id);
        String str = null;
        try {
            //  配置映射路径
            str = MarkdownEntity.mdHtml(blogByContext.getMdContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("title", blogByContext.getTitle());
        map.put("crumbs", blogByContext.getCreateTime());
        map.put("summary", blogByContext.getSummary());
        map.put("content", str);
        return map;
    }


//    @Override
//    // 修改博客
//    public void updateBlogContent(int id, String md) {
//        String html = null;
//        // 将md 转换为html
//        try {
//            html = MarkdownEntity.mdHtml(md);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Blog blog = new Blog();
//        blog.setId(id);
//        blog.setMdContent(md);
//        blog.setHtmlContent(html);
//        int i = blogMapper.updateBlog(blog);
//        System.out.println( "update blog  is  ->> " + i);
//    }

    @Override
    public void updateBlogContent(Blog blog) {
        int i = blogMapper.updateBlogContent(blog);
        System.out.println( "修改是否成功 " + i);
    }

    @Override
    public void updateBlog(Blog blog) {
        int i = blogMapper.updateBlog(blog);
        System.out.println( "update blog  is  ->> " + i);
    }

    @Override
    public Map<String, Object> queryLike(String title) {
        List<Blog> blogs = blogMapper.queryLike(title);
        // 封装请求
        Map<String, Object> map = new HashMap<>();
        map.put("items", blogs);
        return map;
    }

    @Override
    public Map<String, Object> queryCategory() {
        List<String> titles = blogMapper.queryTitleStrings();
        String regEx = "[a-zA-Z0-9]";
        Pattern p =   Pattern.compile(regEx);
        HashSet<String> stringResult = new HashSet<>();
        for (String string : titles) {
            Matcher m   =   p.matcher(string);
            StringBuffer sb = new StringBuffer();
            int flag = 1;
            while (m.find()){
                if (flag == 1) {
                    String upperCase = m.group().toUpperCase();
                    sb.append(upperCase);
                    flag--;
                }else sb.append(m.group());
            }
            stringResult.add(sb.toString());
        }
        stringResult.remove("");
        HashMap<String, Object> result = new HashMap<>();
        result.put("titles", stringResult);
        return result;
    }

    @Override
    public String queryMdContent(int id) {
        String mdContent = blogMapper.queryMdContent(id);
        return mdContent;
    }

    @Override
    public Blog queryBlogInfo(int id) {
        Blog blog = blogMapper.queryBlogById(id);
        return blog;
    }

    @Override
    public String analysisFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
//        截取文件名 .后面的不是md的话   就抛出异常
        String pattern = ".*\\.md$";
        System.out.println(Pattern.matches(pattern, fileName));
        if ( !Pattern.matches(pattern, fileName)){
            throw new TokenUnavailable("文件名不匹配");
        }

        // 设置字符编码
        String charSet = "utf-8";


        mdContent = new StringBuffer();

        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(file.getInputStream());
            byte[] bytes = new byte[(int) file.getSize()];  // 防止读取错乱  直接一下读完
            int len = 0;
            while ((len = bufferedInputStream.read(bytes)) != -1){
                String str = StrUtil.str(bytes, charSet);
                mdContent.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mdContent.toString();
    }


    @Override
    // 插入博客  需要调用文件解析器
    public void insertBlog(Blog blog) {

        if (mdContent == null){
            throw  new TokenUnavailable("文件读取错误");
        }else{
            blog.setMdContent(mdContent.toString());
            blog.setViewsCount(20L);
            blog.setCommentsCount(30L);
            blogMapper.insertBlog(blog);
        }
    }


}
