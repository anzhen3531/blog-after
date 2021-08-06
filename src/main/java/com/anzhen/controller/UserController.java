package com.anzhen.controller;

import com.anzhen.entity.User;
import com.anzhen.service.UserService;
import com.anzhen.utils.result.Result;
import com.anzhen.utils.result.ResultCodeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Classname : UserController
 * @Date : 2021.4.27 9:24
 * @Created : anzhen
 * @Description :
 * @目的:  剩下统一结果回显   jwt + token
 */

@RestController
@RequestMapping("/user")
@Api(description = "Blog系统用户接口")
@CrossOrigin  // 跨域
public class UserController {


    @Resource
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    UserService  userService;


    @GetMapping("/getUser/{id}")
    @ApiOperation("查询用户接口")
    public Result<User> getUser(@PathVariable("id") int id){
        User user = userService.queryUserById(id);
        if (user == null){
            return Result.error(ResultCodeEnum.UNKNOWN_REASON);
        }
        return Result.success(user);
    }

//    @PostMapping("/login")
//    @ApiOperation("用户登录接口")
//    public Result getUser(@RequestBody UserDto user){
//        User userByname = userService.queryUserByName(user.getName());
//        if (userByname == null){
//            throw new TokenUnavailable("没有登录");
//        }
//
//        // 密码编辑
//        if (!bCryptPasswordEncoder.matches( user.getPassword() , userByname.getPassword())){
//            throw  new TokenUnavailable("密码错误");
//        }
//        // 签发token
//        Map<String, Object> result = new HashMap<>();
//        result.put("user", userByname);
//        //result.put("tokne", JwtUtils.createToken(userByname.getId(), userByname.getName(), userByname.getPassword()));
//        return Result.success(result);
//    }

    // 修改  post
    @PostMapping("/updateUser")
    @ApiOperation("添加用户接口")
    public Result updateUser(@RequestBody  User user){
        int i = userService.updateUser(user);
        System.out.println();
        if (i  <= 0){
            return Result.error(ResultCodeEnum.UNKNOWN_REASON);
        }
        return Result.success("添加成功");
    }


    @GetMapping("/deleteUser/{id}")
    @ApiOperation("删除用户接口")
    public Result deleteUser(@PathVariable("id") int id){
        int i = userService.deleteUser(id);
        if (i <= 0){
            return Result.error(ResultCodeEnum.UNKNOWN_REASON);
        }
        return Result.success("删除成功");
    }


    @GetMapping("/outLogin")
    @ApiOperation("退出用户接口")
//     TODO  本来应该从请求体中获取token 但是现在后台还没有写
    public Result outLogin(){
        // 正常逻辑  应该是校验 是否是正确的token 然后进行登录 或者是退出操作
        // 清除缓存中的token
        return Result.success();
    }

}
