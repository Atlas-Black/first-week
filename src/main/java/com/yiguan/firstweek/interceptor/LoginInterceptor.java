package com.yiguan.firstweek.interceptor;

import com.yiguan.firstweek.context.UserContext;
import com.yiguan.firstweek.exception.BusinessException;
import com.yiguan.firstweek.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

//Spring MVC拦截器，在Controller执行之前，先拦截请求做检查
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    //进入Controller之前，先执行这个
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //取请求头里的Token
        String token = request.getHeader("Authorization");

        if (token == null || token.isBlank()) {
            throw new BusinessException("未登录");
        }

        try {
            //把JWT解析出来，拿到里面的payload信息
            Claims claims = JwtUtils.parseToken(token);
            //取出userId转成Long
            Long userId = Long.valueOf(claims.get("userId").toString());
            //当前请求的用户 id，已经被保存到 ThreadLocal 里了
            UserContext.setUserId(userId);
        } catch (Exception e) {
            throw new BusinessException("登录失效，请重新登录");
        }

        return true;
    }

    //请求处理结束后，把当前线程里的userId清掉
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.remove();
    }
}