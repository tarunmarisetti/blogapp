package security;


import com.SpringBoot.blogApp.security.JWTService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JWTServiceTests {
    JWTService jwtService=new JWTService();
    @Test
    void canCreateJWTFromUserId(){
        var jwt=jwtService.createJWT(1001L);
        Assertions.assertNotNull(jwt);
    }

}
