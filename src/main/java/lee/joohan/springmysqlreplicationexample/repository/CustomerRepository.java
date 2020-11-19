package lee.joohan.springmysqlreplicationexample.repository;

import lee.joohan.springmysqlreplicationexample.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Joohan Lee on 2020/07/07
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
