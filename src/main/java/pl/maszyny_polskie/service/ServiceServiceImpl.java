package pl.maszyny_polskie.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.maszyny_polskie.entity.Device;
import pl.maszyny_polskie.entity.Service;
import pl.maszyny_polskie.repository.ServiceRepository;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService{

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public Service findById(int id) {
        return serviceRepository.findById(id).get();
    }

    @Override
    public Service save(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public void delete(Service service) {
        serviceRepository.delete(service);
    }

    @Override
    public List<Service> findByDevice(Device device) {
        return serviceRepository.findByDevice(device);
    }

    @Override
    public Service findLastServiceWithNumber(String year) {
        return serviceRepository.findFirstByNumberIsNotAndYearEqualsOrderByNumberDesc(0,year);
    }
}
