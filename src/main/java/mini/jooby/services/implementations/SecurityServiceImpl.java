package mini.jooby.services.implementations;

import com.google.inject.Inject;
import mini.jooby.models.User;
import mini.jooby.services.SecurityService;
import mini.jooby.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.credentials.authenticator.AbstractUsernamePasswordAuthenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.profile.CommonProfile;

public class SecurityServiceImpl extends AbstractUsernamePasswordAuthenticator implements SecurityService {
    public static final String ROLE = "role";

    @Inject
    private UserService userService;

    /**
     * Create an encrypted password from a clear string.
     *
     * @param clearString the clear string
     * @return an encrypted password of the clear string
     */
    @Override
    public String createPassword(String clearString) {
        if (clearString == null) {
            throw new IllegalArgumentException("Password is null");
        }
        return BCrypt.hashpw(clearString, BCrypt.gensalt());
    }

    /**
     * Checks if the the encrypted password matches the plain textpassword
     *
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

    @Override
    public void validate(UsernamePasswordCredentials credentials, WebContext context) throws HttpAction {
        User user = userService.findUserByUsername(credentials.getUsername());

        if (user == null) {
            context.setResponseStatus(403);
            throw new CredentialsException("Invalid username/password");
        }

        if (!checkPassword(credentials.getPassword(), user.getPasswordHash())) {
            context.setResponseStatus(403);
            throw new CredentialsException("Invalid username/password");
        }

        final CommonProfile profile = new CommonProfile();
        profile.setId(user.getId());
        profile.addAttribute(Pac4jConstants.USERNAME, user.getUsername());
        profile.addAttribute(ROLE, user.getRole());
        credentials.setUserProfile(profile);
    }
}
