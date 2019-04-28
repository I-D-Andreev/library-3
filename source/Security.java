import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

public class Security {
    private static final SecureRandom secureRandom = new SecureRandom();

    private static final int NUMBER_OF_ITERATIONS = 20000;
    private static final int LENGTH_OF_KEY = 512;
    private static final String ENCODING_ALGORITHM = "PBKDF2WithHmacSHA512";

    public static String generateSalt() {
        // generates a random length between 40-59
        int length = secureRandom.nextInt(20) + 40;

        byte[] saltBytes = new byte[length];
        secureRandom.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    public static String generatePassword(String plainTextPassword, String salt) {
        // convert the data to the needed format
        char[] passwordChar = plainTextPassword.toCharArray();
        byte[] saltBytes = salt.getBytes();

        // specify how we are going to encode the password
        PBEKeySpec keySpec = new PBEKeySpec(passwordChar, saltBytes, NUMBER_OF_ITERATIONS, LENGTH_OF_KEY);

        // delete all info in the passwordChar array
        Arrays.fill(passwordChar, Character.MIN_VALUE);

        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ENCODING_ALGORITHM);
            byte[] encodedPassword = secretKeyFactory.generateSecret(keySpec).getEncoded();
            return Base64.getEncoder().encodeToString(encodedPassword);
        } catch (NoSuchAlgorithmException noAlg) {
            System.out.println("Algorithm name for generating passwords is wrong");
            noAlg.printStackTrace();
        } catch (InvalidKeySpecException invKey) {
            System.out.println("Error parsing the secret key");
            invKey.printStackTrace();
        } finally {
            keySpec.clearPassword();
        }

        // shut compiler
        return null;
    }


}
