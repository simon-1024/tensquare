import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class CreateJwtTest2 {
    public static void main(String[] args) {

        Long exp = System.currentTimeMillis() + 1000*60 ;
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("1234")              //ID
                .setSubject("simon")        //用户名
                .setIssuedAt(new Date())   //设置签发时间
                .setExpiration(new Date(exp))   //设置过期时间为一分钟
                .signWith(SignatureAlgorithm.HS256, "baby");   //头部信息：编码方式，盐
        System.out.println(jwtBuilder.compact());
    }
}
