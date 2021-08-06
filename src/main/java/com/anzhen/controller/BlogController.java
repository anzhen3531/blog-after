package com.anzhen.controller;

import com.anzhen.entity.Blog;
import com.anzhen.service.BlogService;
import com.anzhen.utils.result.Result;
import com.anzhen.utils.result.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Classname : BlogController
 * @Date : 2021.4.29 10:19
 * @Created : anzhen
 * @Description :
 * @目的:
 */
@RestController
@Api(description = "博客项目接口")
@CrossOrigin
public class BlogController {

    @Resource
    BlogService blogService;


    @ApiOperation("分页查询")
    @GetMapping("/blog/queryAll")
    // 分页数据 从前台传入数据
    public Result<Blog> limitPage(Integer currentPage, Integer pageSize){
        System.out.println( "currentPage   => " + currentPage    +  "pageSize => " + pageSize);

        Map<String, Object> stringObjectMap = blogService.queryAll(currentPage, pageSize);
        return Result.success(stringObjectMap);
    }

    // 博客显示接口
    @GetMapping("/article/{id}")
    // 建立创建 数据库  传入参数进行查询
    public Result<Blog> markdownAndHtml(@PathVariable("id") int id){
        Map<String, Object> blogByContext = blogService.getBlogByContext(id);
        return Result.success(blogByContext);
    }


    // 模糊查询数据 通过标题名进行查询
    @GetMapping("/search/{title}")
    public Result<Map> queryLike(@PathVariable("title") String title){
        Map<String, Object> result = blogService.queryLike(title);
        return Result.success(result);
    }

    // 返回标题  传递给前台
    @GetMapping("/category")
    public Result queryCategory(){
        Map<String, Object> result = blogService.queryCategory();
        return Result.success(result);
    }

    // 查询md内容
    @GetMapping("/editBlog/{id}")
    public Result editBlog(@PathVariable("id") int id){
        String md = blogService.queryMdContent(id);
        return Result.success(md);
    }

    // 通过id查询博客
    @GetMapping("/queryBlogInfo/{id}")
    public Result queryBlogInfo(@PathVariable("id") int id){
        Blog blog = blogService.queryBlogInfo(id);
        return Result.success(blog);
    }



    // 修改博客基本信息
    @PreAuthorize("@pm.hasPermission('/updateBlog')")
    @PostMapping("/updateBlog/{id}")
    public Result updateBlog(@PathVariable("id") int id, @RequestBody Map<String, Blog> blog){
        System.out.println(" 进入修改 ");
        // 测试文件 上传  将长传的文件读取存入数据库 md 文件
        Blog blogInfo = blog.get("blog");
        System.out.println(blogInfo);
        blogInfo.setId(id);
        blogService.updateBlog(blogInfo);
        return Result.success();
    }


    @PostMapping("/updateBlogContent")
    @PreAuthorize("@pm.hasPermission('/updateBlogContent')")
    // 修改博客内容
    public Result updateContent(@RequestBody Blog blogInfo){
        blogService.updateBlogContent(blogInfo);
        return Result.success();
    }


    @PostMapping("/addBlogInfo")
    @PreAuthorize("@pm.hasPermission('/addBlogInfo')")
    // 添加博客  使用文件 添加  需要用到文件接收
    public Result addBlog(@RequestBody Blog blog){
        // 接收到添加数据
        System.out.println(blog);

        blogService.insertBlog(blog);
        return Result.success();
    }

    @PostMapping("/addBlogFile")
    @PreAuthorize("@pm.hasPermission('/addBlogFile')")
    public Result upload(@RequestBody  MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error(ResultCodeEnum.FILE_UPLOAD_ERROR, "上传失败，请选择文件");
        }
        blogService.analysisFile(file);
        return Result.success();
    }

}
