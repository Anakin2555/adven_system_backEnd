package com.example.mybatisplus.interceptor;

import com.example.mybatisplus.mapper.TokenMapper;
import com.example.mybatisplus.model.domain.Token;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.Date;
import java.util.stream.Collectors;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    TokenMapper tokenMapper;

    private final Logger logger = LoggerFactory.getLogger( LoginInterceptor.class );


    //提供查询
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {}
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {}




    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {


        //显示接收到的请求

        logger.info(arg0.getRequestURI());
        logger.info(arg0.getHeader("token"));

        //此处为不需要登录的接口放行
        if (arg0.getRequestURI().contains("login")
                || arg0.getRequestURI().contains("findPassword")
                || arg0.getRequestURI().contains("getAllFeedbackByActivityId")
                || arg0.getRequestURI().contains("error")) {
            return true;
        }
        //权限路径拦截
        arg1.setContentType("text/html;charset=utf-8");
        ServletOutputStream resultWriter = arg1.getOutputStream();
        final String headerToken=arg0.getHeader("token");
        //判断请求信息
        if(null==headerToken||headerToken.trim().equals("")||headerToken.equals("")){
            resultWriter.write(arg0.getRequestURI().getBytes());
            resultWriter.write("你没有token,需要登录".getBytes());
            resultWriter.flush();
            resultWriter.close();
            return false;
        }

        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey("preRead").parseClaimsJws(headerToken).getBody();
        } catch (ExpiredJwtException e) {
            claims=e.getClaims();
        }



        //解析Token信息
        try {
            String tokenUserId=(String)claims.get("userId");
            long iTokenUserId = Long.parseLong(tokenUserId);
            //根据客户Token查找数据库Token
            Token myToken= tokenMapper.selectByUserId(iTokenUserId);

            //数据库没有Token记录
            if(null==myToken) {
                resultWriter.write("我没有你的token？,需要登录".getBytes());
                resultWriter.flush();
                resultWriter.close();
                return false;
            }
            //数据库Token与客户Token比较
            if( !headerToken.equals(myToken.getToken()) ){
                resultWriter.write("你的token修改过？,需要登录".getBytes());
                resultWriter.flush();
                resultWriter.close();
                return false;
            }
            //判断Token过期
            Date tokenDate= claims.getExpiration();
            int overTime=(int)(new Date().getTime()-tokenDate.getTime())/1000;
            if(overTime>60*60*24*3){
                resultWriter.write("你的token过期了？,需要登录".getBytes());
                resultWriter.flush();
                resultWriter.close();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            resultWriter.write("反正token不对,需要登录".getBytes());
            resultWriter.flush();
            resultWriter.close();
            return false;
        }
        //最后才放行
        return true;
    }

}
