package starter.ebean.services.implementations;

import com.google.inject.Inject;
import io.ebean.EbeanServer;
import starter.ebean.models.User;
import starter.ebean.models.query.QUser;
import starter.ebean.services.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Inject
    private EbeanServer ebean;

    @Override
    public Long addUser(User user) {
        ebean.insert(user);
        System.out.println(user.toString());
        return user.getId();
    }

    @Override
    public User findUserById(Long id){
        return ebean.find(User.class, id);
    }

    @Override
    public List<User> findAll(){
        return new QUser().findList();
    }
}
