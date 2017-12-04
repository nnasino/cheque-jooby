package starter.ebean.services.implementations;

import org.mindrot.jbcrypt.BCrypt;
import starter.ebean.services.SecurityService;

public class SecurityServiceImpl implements SecurityService {
    /**
     * Create an encrypted password from a clear string.
     *
     * @param clearString the clear string
     * @return an encrypted password of the clear string
     */
    @Override
    public String createPassword(String clearString){
        if (clearString == null) {
            throw new IllegalArgumentException("Password is null");
        }
        return BCrypt.hashpw(clearString, BCrypt.gensalt());
    }

    /**
     * @param candidate         the clear text
     * @param encryptedPassword the encrypted password string to check.
     * @return true if the candidate matches, false otherwise.
     */
    @Override
    public boolean checkPassword(String candidate, String encryptedPassword) {
        if (candidate == null) {
            return false;
        }
        if (encryptedPassword == null) {
            return false;
        }
        return BCrypt.checkpw(candidate, encryptedPassword);
    }
}
