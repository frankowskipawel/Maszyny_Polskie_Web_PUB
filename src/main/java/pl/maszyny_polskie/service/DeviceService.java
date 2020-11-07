package pl.maszyny_polskie.service;

import org.springframework.stereotype.Service;
import pl.maszyny_polskie.entity.Category;
import pl.maszyny_polskie.entity.Customer;
import pl.maszyny_polskie.entity.Device;

import java.util.List;

@Service
public interface DeviceService {

    List<Device> findAll();
    Device findById(int id);
    Device save(Device device);
    Device findByName(String name);
    void delete(Device device);
    List<Device> findByCustomer(Customer customer);
    List<Device>  findByCategory(Category category);
}
