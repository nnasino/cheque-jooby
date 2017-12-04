package mini.jooby;

import com.google.inject.Binder;
import com.typesafe.config.Config;
import mini.jooby.services.*;
import mini.jooby.services.implementations.*;
import org.jooby.Env;
import org.jooby.Jooby;

public class AppModule implements Jooby.Module {

    public void configure(Env env, Config conf, Binder binder) {
        binder.bind(BranchService.class).to(BranchServiceImpl.class);
        binder.bind(ChequeService.class).to(ChequeServiceImpl.class);
        binder.bind(CustomerService.class).to(CustomerServiceImpl.class);
        binder.bind(LoanService.class).to(LoanServiceImpl.class);
        binder.bind(SecurityService.class).to(SecurityServiceImpl.class);
        binder.bind(UserService.class).to(UserServiceImpl.class);
    }

}
