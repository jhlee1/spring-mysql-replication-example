package lee.joohan.springmysqlreplicationexample.controller;

import java.util.List;
import javax.validation.Valid;
import lee.joohan.springmysqlreplicationexample.domain.Customer;
import lee.joohan.springmysqlreplicationexample.dto.request.CreateCustomerRequest;
import lee.joohan.springmysqlreplicationexample.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Joohan Lee on 2020/07/07
 */

@RequiredArgsConstructor
@RequestMapping("/customers")
@RestController
public class CustomerController {
  private final CustomerService customerService;

  @GetMapping
  public ResponseEntity<List<Customer>> getAll() {
    return ResponseEntity.ok(customerService.getAll());
  }

  @PostMapping
  public ResponseEntity<Customer> create(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
    return ResponseEntity.ok(customerService.create(createCustomerRequest.getUsername(), createCustomerRequest.getEmail()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable long id) {
    customerService.delete(id);

    return ResponseEntity.ok().build();
  }
}
