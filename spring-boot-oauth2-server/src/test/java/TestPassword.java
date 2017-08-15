import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class TestPassword {
	public static void main(String[] args) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String password = encoder.encodePassword("user", null);
		System.out.println(password);
	}

}
