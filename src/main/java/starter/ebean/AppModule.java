package starter.ebean;

import com.google.inject.Binder;
import com.typesafe.config.Config;
import org.jooby.Env;
import org.jooby.Jooby;
import starter.ebean.services.BranchService;
import starter.ebean.services.ChequeService;
import starter.ebean.services.UserService;
import starter.ebean.services.implementations.BranchServiceImpl;
import starter.ebean.services.implementations.ChequeServiceImpl;
import starter.ebean.services.implementations.UserServiceImpl;

public class AppModule implements Jooby.Module {

    public void configure(Env env, Config conf, Binder binder) {
        binder.bind(BranchService.class).to(BranchServiceImpl.class);
        binder.bind(ChequeService.class).to(ChequeServiceImpl.class);
        binder.bind(UserService.class).to(UserServiceImpl.class);
    }

}
