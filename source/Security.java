import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

/**
 * A class to deal with password encryption and security.
 * @author Ivan Andreev 
 */
public class Security {
    private static final SecureRandom secureRandom = new SecureRandom();

    private static final int NUMBER_OF_ITERATIONS = 20000;
    private static final int LENGTH_OF_KEY = 512;
    private static final String ENCODING_ALGORITHM = "PBKDF2WithHmacSHA512";

    /**
     * Generates password salt of random length.
     * @return The password salt.
     */
    public static String generateSalt() {
        // generates a random length between 40-59
        int length = secureRandom.nextInt(20) + 40;

        byte[] saltBytes = new byte[length];
        secureRandom.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    /**
     * Generates an encoded password.
     * @param plainTextPassword The password in plain text format.
     * @param salt The salt.
     * @return The encoded password.
     */
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

    /**
     * Checks whether a password is correct.
     * @param plainTextPassword The plain text password.
     * @param salt The salt.
     * @param encodedPassword The encoded password.
     * @return True if the plain text password equals the encoded password when encrypted, false otherwise.
     */
    public static boolean checkPassword(String plainTextPassword, String salt, String encodedPassword){
        String generatedPassword = Security.generatePassword(plainTextPassword, salt);
        return generatedPassword == null ? false : generatedPassword.equals(encodedPassword);
    }


    /**
     * Generates a random unencoded password.
     * @return A string containing the password.
     */
    public static String generateRandomUnencodedPassword(){
        // generate random bytes, then convert them to base64
        // and use that random string as password
        byte[] bytes = new byte[7];
        secureRandom.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }


    /**
     * Checks whether a password is strong enough. A strong password has at least 6 characters,
     * a letter, a number and a symbol.
     * @param password The plain text password.
     * @return True if password is strong enough, false - otherwise.
     */
    public static boolean checkPasswordStrength(String password){
        boolean atLeast6Characters = (password.length()>=6);
        boolean hasLetter = false;
        boolean hasNumber = false;
        boolean hasSymbol = false;

        for(char character : password.toCharArray()){
            if(Character.isDigit(character)){
                hasNumber = true;
            } else if(Character.isLetter(character)){
                hasLetter = true;
            } else {
                hasSymbol = true;
            }
       }

        return (atLeast6Characters && hasLetter && hasNumber && hasSymbol);
    }


}
