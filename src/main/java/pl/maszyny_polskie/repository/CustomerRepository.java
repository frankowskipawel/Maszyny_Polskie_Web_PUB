package pl.maszyny_polskie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.maszyny_polskie.entity.Customer;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByShortName(String shortName);
    List<Customer> findAllByOrderByShortNameAsc();

}
