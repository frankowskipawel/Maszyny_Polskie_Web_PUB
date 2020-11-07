package pl.maszyny_polskie.service;

import org.springframework.stereotype.Service;
import pl.maszyny_polskie.entity.Device;
import pl.maszyny_polskie.entity.File;

import java.util.List;

@Service
public interface FileService {

    List<File> findAll();
    File findById(int id);
    File save(File file);
    void delete(File file);
    List<File> findByDevice(Device device);
    List<File> findByService(pl.maszyny_polskie.entity.Service service);
    void flush();
    List<File> findByDeviceAndType(Device device, String type);
}
