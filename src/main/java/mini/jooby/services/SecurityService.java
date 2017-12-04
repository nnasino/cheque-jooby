package mini.jooby.services;

public interface SecurityService {


    String createPassword(String clearString);

    boolean checkPassword(String candidate, String encryptedPassword);
}
