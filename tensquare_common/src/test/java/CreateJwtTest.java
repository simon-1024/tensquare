import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class CreateJwtTest {
    public static void main(String[] args) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("1234")              //ID
                .setSubject("simon")        //用户名
                .setIssuedAt(new Date())   //设置签发时间
                .signWith(SignatureAlgorithm.HS256, "baby");   //头部信息：编码方式，盐
        System.out.println(jwtBuilder.compact());
    }
}
