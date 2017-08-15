import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestPassword {
	public static void main(String[] args) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String password = encoder.encodePassword("password", null);
		System.out.println(password);
		
		BCryptPasswordEncoder der = new BCryptPasswordEncoder();
		String encode = der.encode("password");
		System.out.println(encode);
	}

}
