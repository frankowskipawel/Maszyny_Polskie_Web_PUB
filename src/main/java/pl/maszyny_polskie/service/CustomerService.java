package pl.maszyny_polskie.service;

import org.springframework.stereotype.Service;
import pl.maszyny_polskie.entity.Customer;

import java.util.List;

@Service
public interface CustomerService {

    List<Customer> findAll();
    Customer findById(int id);
    Customer save(Customer customer);
    Customer findByShortName(String shortName);
    void delete(Customer customer);


}
