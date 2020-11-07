package pl.maszyny_polskie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maszyny_polskie.entity.Category;
import pl.maszyny_polskie.entity.Customer;
import pl.maszyny_polskie.entity.Device;
import pl.maszyny_polskie.repository.DeviceRepository;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService{

    @Autowired
    DeviceRepository deviceRepository;


    @Override
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    @Override
    public Device findById(int id) {
        return deviceRepository.findById(id).get();
    }

    @Override
    public Device save(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public Device findByName(String name) {
        return deviceRepository.findByName(name);
    }

    @Override
    public void delete(Device device) {
        deviceRepository.delete(device);
    }

    @Override
    public List<Device> findByCustomer(Customer customer) {
        return deviceRepository.findByCustomer(customer);
    }

    @Override
    public List<Device> findByCategory(Category category) {
        return deviceRepository.findByCategory(category);
    }
}
