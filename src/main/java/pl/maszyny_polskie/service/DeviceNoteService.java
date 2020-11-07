package pl.maszyny_polskie.service;

import org.springframework.stereotype.Service;
import pl.maszyny_polskie.entity.Device;
import pl.maszyny_polskie.entity.DeviceNote;

import java.util.List;

@Service
public interface DeviceNoteService {
    List<DeviceNote> findAll();
    DeviceNote findById(int id);
    DeviceNote save(DeviceNote deviceNote);
    void delete(DeviceNote deviceNote);
    List<DeviceNote> findByDevice(Device device);

}
