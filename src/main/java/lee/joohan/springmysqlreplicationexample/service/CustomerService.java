package lee.joohan.springmysqlreplicationexample.service;

import java.util.List;
import lee.joohan.springmysqlreplicationexample.domain.Customer;
import lee.joohan.springmysqlreplicationexample.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Joohan Lee on 2020/07/07
 */

@RequiredArgsConstructor
@Service
public class CustomerService {
  private final CustomerRepository customerRepository;

  @Transactional(readOnly = true)
  public List<Customer> getAll() {
    return customerRepository.findAll();
  }

  @Transactional
  public Customer create(String username, String email) {
    Customer customer = Customer.builder()
        .username(username)
        .email(email)
        .build();

    return customerRepository.save(customer);
  }

  @Transactional
  public void delete(long id) {
    customerRepository.deleteById(id);
  }
}
