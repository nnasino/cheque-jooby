package starter.ebean;

import org.jooby.Jooby;
import org.jooby.ebean.Ebeanby;
import org.jooby.json.Jackson;

import io.ebean.EbeanServer;
import io.ebean.config.ServerConfig;
import starter.ebean.query.QPet;

public class App extends Jooby {

  {
    use(new Jackson());

    /**
     * Configure Ebean:
     */
    use(new Ebeanby().doWith((final ServerConfig ebean) -> {
      /** These can be done in .conf file too: */
      ebean.setDisableClasspathSearch(false);
      ebean.setDdlGenerate(true);
      ebean.setDdlRun(true);
    }));

    /**
     * Insert some data on startup:
     */
    onStart(() -> {
      EbeanServer ebean = require(EbeanServer.class);
      ebean.insert(new Pet("Lala"));
      ebean.insert(new Pet("Mandy"));
      ebean.insert(new Pet("Fufy"));
    });

    /**
     * Find all via query-beans:
     */
    get("/pets", req -> {
      return new QPet().findList();
    });

    /**
     * Find by id via ebean:
     */
    get("/pets/{id:\\d+}", req -> {
      int id = req.param("id").intValue();
      EbeanServer ebean = require(EbeanServer.class);
      return ebean.find(Pet.class, id);
    });

    /**
     * Find by name via query-beans:
     */
    get("/pets/:name", req -> {
      EbeanServer ebean = require(EbeanServer.class);
      String name = req.param("name").value();
      return new QPet(ebean).name.ilike(name)
          .findList();
    });
  }

  public static void main(final String[] args) {
    run(App::new, args);
  }

}
