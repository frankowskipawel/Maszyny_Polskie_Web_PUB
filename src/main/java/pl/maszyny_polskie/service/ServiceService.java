package pl.maszyny_polskie.service;


import org.springframework.stereotype.Service;
import pl.maszyny_polskie.entity.Device;


import java.util.List;

@Service
public interface ServiceService {

    List<pl.maszyny_polskie.entity.Service> findAll();
    pl.maszyny_polskie.entity.Service findById(int id);
    pl.maszyny_polskie.entity.Service save(pl.maszyny_polskie.entity.Service service);
    void delete(pl.maszyny_polskie.entity.Service service);
    List<pl.maszyny_polskie.entity.Service> findByDevice(Device device);
    pl.maszyny_polskie.entity.Service findLastServiceWithNumber(String year);
}
