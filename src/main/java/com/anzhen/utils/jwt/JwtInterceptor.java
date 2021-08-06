//package com.anzhen.utils.jwt;
//
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.lang.reflect.Method;
//
///**
// * @Classname : JwtInterceptor
// * @Date : 2021.5.11 15:35
// * @Created : anzhen
// * @Description :  springboot 的拦截器
// * @目的:   拦截那些 标记了JwtToken 注解的方法
// */
//
//public class JwtInterceptor implements HandlerInterceptor {
//
//    @Override
////    preHandle：在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        // 从请求参数中  获取token
//        String token = request.getHeader("token");
//
//        if (!(handler instanceof HandlerMethod)){  // 校验请求头是否有token
//            return  true;
//        }
//
//        // 强转为 handlerMethod
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//
//
//        // 得到method  对象   获取反射对象
//        Method method = handlerMethod.getMethod();
//
//        // 检查有没有需要用户权限的注解   检查有什么方法标记了这个注解
//        if (method.isAnnotationPresent(JwtToken.class)) {
//            // 校验是不是有这个注解
//            JwtToken jwtToken = method.getAnnotation(JwtToken.class);
//            //   通过注解属性 判断是不是需要认证
//            if (jwtToken.required()){
//                if (token == null){
//                    throw new TokenUnavailable("没有token  请登录获取");
//                }
//                // 获取token的信息
//                String audience = JwtUtils.getAudience(token);
//                // 检验token
//                JwtUtils.verifyToken(token);
//                System.out.println( "校验成功 签发 用户id -> " + audience);
//                return true;
//            }
//
//        }
//        return false;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//    }
//}
