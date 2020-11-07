package pl.maszyny_polskie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.maszyny_polskie.entity.Category;
import pl.maszyny_polskie.entity.Customer;
import pl.maszyny_polskie.entity.Device;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

    Device findByName(String name);
    List<Device> findByCustomer(Customer customer);
    List<Device>  findByCategory(Category category);
}
