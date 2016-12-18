package security;

import org.mindrot.jbcrypt.BCrypt;

/**
 * A password encryptor that passes through to the string digester on the backend.
 */
public class Password {
    public static Boolean checkPassword(String message, String digest) {
        return BCrypt.checkpw(message, digest);
    }

    public static String encryptPassword(String plaintext) {
        return BCrypt.hashpw(plaintext, BCrypt.gensalt());
    }
}
