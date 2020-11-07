package pl.maszyny_polskie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maszyny_polskie.entity.Customer;
import pl.maszyny_polskie.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public List<Customer> findAll() {
        return customerRepository.findAllByOrderByShortNameAsc();
    }

    @Override
    public Customer findById(int id) {
        return customerRepository.findById(id).get();
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer findByShortName(String shortName) {
        return customerRepository.findByShortName(shortName);
    }

    @Override
    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }
}
