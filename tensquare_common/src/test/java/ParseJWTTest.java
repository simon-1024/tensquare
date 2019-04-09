import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class ParseJWTTest {
    public static void main(String[] args) {
        String str = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMjM0Iiwic3ViIjoic2ltb24iLCJpYXQiOjE1NTQxOTIzNTYsImV4cCI6MTU1NDE5MjQxNiwicm9sZSI6ImFkbWluIiwiYWdlIjoyMH0.iB21wLgcNLIPNXPgG27krMFuuasACT4ePFZzcEjHd1Y";
        Claims claims = Jwts.parser().setSigningKey("baby").parseClaimsJws(str).getBody();
        System.out.println(claims);
        System.out.println("id : "+claims.getId());
        System.out.println("subject : "+claims.getSubject());
        System.out.println("IssuedAt : "+claims.getIssuedAt());
        System.out.println("role : "+claims.get("role"));
        System.out.println("age : "+claims.get("age"));
    }
}
