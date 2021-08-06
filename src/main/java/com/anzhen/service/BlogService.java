package com.anzhen.service;

import com.anzhen.entity.Blog;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Classname : BlogService
 * @Date : 2021.4.29 10:11
 * @Created : anzhen
 * @Description :
 * @目的:   接口定义规范
 */

public interface BlogService {

    // 查询全部
    List<Blog> queryAll();

    // 分页查询
    Map<String, Object> queryAll(int currentPage, int pageSize);

    // 查询博客需要传入id
    Map<String, Object> getBlogByContext(int id);

    // 添加博客
    void insertBlog(Blog blog);

    void updateBlogContent(Blog blog);

    void updateBlog(Blog blog);

    // 模糊查询数据 进行查询
    Map<String, Object> queryLike(String title);
    // 转码
//    void  使用

    Map<String, Object> queryCategory();

    String queryMdContent(int id);

    Blog queryBlogInfo(int id);

    // 解析文件接口
    String analysisFile(MultipartFile file);
}