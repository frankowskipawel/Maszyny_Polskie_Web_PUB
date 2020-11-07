package pl.maszyny_polskie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.maszyny_polskie.entity.Device;
import pl.maszyny_polskie.entity.File;
import pl.maszyny_polskie.entity.Service;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {

    List<File> findByDevice(Device device);
    List<File> findByService(Service service);
    List<File> findByDeviceAndType(Device device, String type);
}
