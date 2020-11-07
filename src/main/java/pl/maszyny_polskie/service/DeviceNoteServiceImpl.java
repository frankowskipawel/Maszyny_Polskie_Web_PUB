package pl.maszyny_polskie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maszyny_polskie.entity.Device;
import pl.maszyny_polskie.entity.DeviceNote;
import pl.maszyny_polskie.repository.DeviceNoteRepository;

import java.util.List;

@Service
public class DeviceNoteServiceImpl implements DeviceNoteService {

    @Autowired
    DeviceNoteRepository deviceNoteRepository;

    @Override
    public List<DeviceNote> findAll() {
        return deviceNoteRepository.findAll();
    }

    @Override
    public DeviceNote findById(int id) {
        return deviceNoteRepository.findById(id).get();
    }

    @Override
    public DeviceNote save(DeviceNote deviceNote) {
        return deviceNoteRepository.save(deviceNote);
    }

    @Override
    public void delete(DeviceNote deviceNote) {
        deviceNoteRepository.delete(deviceNote);
    }

    @Override
    public List<DeviceNote> findByDevice(Device device) {
        return deviceNoteRepository.findByDevice(device);
    }
}
