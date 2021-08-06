package com.anzhen;

import com.anzhen.config.MyServiceConfig;
import com.anzhen.entity.Blog;
import com.anzhen.mapper.BlogMapper;
import com.anzhen.mapper.PermissionMapper;
import com.anzhen.mapper.UserMapper;
import com.anzhen.security.utils.TokenService;
import com.anzhen.service.BlogService;
import com.anzhen.utils.MarkdownEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@SpringBootTest
class AnZhenBlogApplicationTests {

    @Resource
    MyServiceConfig myServiceConfig;

    @Resource
    BlogMapper blogMapper;

    @Resource
    BlogService blogService;

    @Test
    void contextLoads() throws Exception {
        System.out.println(" 开始替换");
        System.out.println(myServiceConfig.getUrl());
        String path = "D:\\Files\\NodeBook\\ReLearnNote\\springboot.md";
        String s = MarkdownEntity.readMd(path, "http://192.168.1.9:9999/assets/");
        Blog blog = new Blog();
        blog.setId(4);
        blog.setMdContent(s);
        blogMapper.updateBlogContent(blog);
        System.out.println(s);
    }


    @Test
    void testQuery(){
        System.out.println(myServiceConfig.getUrl());
        System.out.println(blogService.queryCategory());
    }

    @Test
    void testToken(){
        int[] arr = {4, 6, 8,1, 3,10,-5,9,12,7,3,0};
        System.out.println(FindMaxSum(arr, 3));
    }



    int testNumber(int n){
        int number = 0;
        for (int i = 1; i <= n; i++) number += i;
        return number;
    }


    HashMap<String, Integer> FindMaxSum(int[] arr, int n){
        ArrayList<HashMap<String, Integer>> hashMaps = new ArrayList<>();
        for (int i = 0; i < arr.length - n; i++) {
            int maxSum  = 0;
            HashMap<String, Integer> stringObjectHashMap = new HashMap<>();
            stringObjectHashMap.put("起始位置", i);
            stringObjectHashMap.put("结束位置", i + n);
            for (int j = i; j < i + n; j++){
                System.out.println(arr[j]);
                maxSum += arr[j];
            }
            stringObjectHashMap.put("总数", maxSum);
            hashMaps.add(stringObjectHashMap);
        }
        // 求max
        int index = 0;  // 最大值坐标
        for (int i = 0; i < hashMaps.size() - 1; i++) {
            int max = Integer.max(hashMaps.get(i).get("总数"), hashMaps.get(i + 1).get("总数"));
            index = i;
            System.out.println("最大值" + max  + "坐标为" + i);
        }
        return  hashMaps.get(index);
    }


    @Test
    void testBate(){
        Map<String, Object> stringObjectMap =
                blogService.queryAll(1, 1);

        System.out.println(stringObjectMap);
    }

    @Test
    void testPattern(){
        String str = "mongo.md";
        String pattern = ".*\\.md$";
        System.out.println(Pattern.matches(pattern, str));

    }

    @Resource
    UserMapper mapper;
    @Test
    void test2(){
        System.out.println(Arrays.toString(mapper.selectUserPermissionById(1).get("permissionsNameList").toString().split(",")));
        System.out.println();
    }

    @Resource
    BCryptPasswordEncoder passwordEncoder;
    @Test
    void testCode(){
        System.out.println(passwordEncoder.encode("zianger"));
    }



    @Resource
    PermissionMapper permissionMapper;
    @Test
    void testPermission(){
        System.out.println(permissionMapper.queryAll());
    }


    @Resource
    TokenService tokenService;
    @Test
    void test02(){
        System.out.println(tokenService.deleteToken("login_token_key:30be23fd0c6b44f0b321561641a76bda"));
    }

}
