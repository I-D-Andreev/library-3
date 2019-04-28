import java.security.SecureRandom;
import java.util.Base64;

public class Security {
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateSalt(){
        // generates a random length between 30-39
        int length = secureRandom.nextInt(10) + 30;

        byte[] saltBytes = new byte[length];
        secureRandom.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }


}
