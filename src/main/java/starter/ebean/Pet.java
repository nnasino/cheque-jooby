package starter.ebean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Pet {

  @Id
  @GeneratedValue
  private int id;

  private String name;

  public Pet(final String name) {
    this.name = name;
  }

  public Pet() {
  }

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

}
