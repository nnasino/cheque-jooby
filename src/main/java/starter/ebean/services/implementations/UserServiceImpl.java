package starter.ebean.services.implementations;

import com.google.inject.Inject;
import io.ebean.EbeanServer;
import starter.ebean.models.User;
import starter.ebean.services.UserService;

public class UserServiceImpl implements UserService {
    @Inject
    private EbeanServer ebean;
    @Override
    public Long addUser(User user) {
        ebean.insert(user);
        System.out.println(user.toString());
        return user.getId();
    }
}
