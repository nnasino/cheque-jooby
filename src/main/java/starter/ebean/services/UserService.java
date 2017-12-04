package starter.ebean.services;

import starter.ebean.dtos.UserDTO;
import starter.ebean.models.User;

import java.util.List;

/**
 * Service for handling manipulation of all {@link starter.ebean.models.User} objects
 */
public interface UserService {
    /**
     * Saves the specified {@link starter.ebean.models.User} object to the database and returns
     * the db id
     *
     * @param user the User object to be saved
     * @return the db id of the saved User
     */
    Long addUser(UserDTO user, User addedBy);

    /**
     * Finds the {@link starter.ebean.models.User} record with the specified db id
     *
     * @param id the db id of the required User
     * @return the {@link User} object if found or null if not found
     */
    User findUserById(Long id);

    /**
     * Returns a {@link List} of all the {@link starter.ebean.models.User}s in the database
     *
     * @return List of Cheques
     */
    List<User> findPage(int page, int pageSize);

    User findUserByUsername(String username);
}
