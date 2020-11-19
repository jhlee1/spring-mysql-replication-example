package lee.joohan.springmysqlreplicationexample.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Joohan Lee on 2020/07/07
 */

@Getter
@NoArgsConstructor
@Entity(name = "customers")
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String username;
  private String email;

  @Builder
  public Customer(String username, String email) {
    this.username = username;
    this.email = email;
  }
}
