package com.anzhen.mapper;

import com.anzhen.entity.Blog;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @Classname : BlogMapper
 * @Date : 2021.4.29 9:27
 * @Created : anzhen
 * @Description :
 * @目的:
 */

public interface BlogMapper {

    // 查询全部数据进行测试
    List<Blog> queryAll();

    // 分页查询
    Page<Blog> getBlogByPage();

    // 查询博客需要传入id
    Blog getBlogByContext(int id);

    int insertBlog(Blog blog);

    // 修改博客
    int updateBlog(Blog blog);

    // 修改博客
    int updateBlogContent(Blog blog);

    // 模糊查询博客
    Page<Blog> queryLike(String name);

    // 查询分类名词
    List<String> queryTitleStrings();

    String queryMdContent(int id);

    Blog queryBlogById(int id);
}
