package com.example.mybatisplus.service.impl;

import com.example.mybatisplus.model.domain.Token;
import com.example.mybatisplus.mapper.TokenMapper;
import com.example.mybatisplus.model.domain.User;
import com.example.mybatisplus.service.TokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zyc&rgl
 * @since 2022-03-02
 */
@Service
public class TokenServiceImpl extends ServiceImpl<TokenMapper, Token> implements TokenService {

    @Autowired
    TokenMapper tokenMapper;

    @Override
    public Token getToken(User user) {

        Token token = tokenMapper.selectByUserId(user.getId());

        String tokenStr = "";
        Date date = new Date();
        Long nowTime =  (date.getTime() / 1000);

        tokenStr=createToken(user.getId(),date);
        if (null == token) {
            //第一次登陆
            token = new Token();
            token.setToken(tokenStr);
            token.setUserId(user.getId());
            token.setBuildTime(nowTime);

            tokenMapper.insert(token);


        } else {

            token.setToken(tokenStr);
            token.setBuildTime(nowTime);

            tokenMapper.updateById(token);
        }


        return token;


    }

    private String createToken(Long id, Date date) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        JwtBuilder jwtBuilder = Jwts.builder().setHeaderParam("typ","JWT")
                .setHeaderParam("alg", "HS256").setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + 1000 * 60 * 60))
                .claim("userId",String.valueOf(id))
                .setIssuer("zyc")
                .signWith(signatureAlgorithm,"preRead");
        String jwt=jwtBuilder.compact();
        return jwt;
    }
}
