package lee.joohan.springmysqlreplicationexample.dto.request;

import lombok.Getter;

/**
 * Created by Joohan Lee on 2020/07/07
 */

@Getter
public class CreateCustomerRequest {
  private String username;
  private String email;

}
