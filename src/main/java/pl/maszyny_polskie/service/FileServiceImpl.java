package pl.maszyny_polskie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.maszyny_polskie.entity.Device;
import pl.maszyny_polskie.entity.File;
import pl.maszyny_polskie.repository.FileRepository;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public List<File> findAll() {
        return fileRepository.findAll();
    }

    @Override
    public File findById(int id) {
        return fileRepository.findById(id).get();
    }

    @Override
    public File save(File file) {
        return fileRepository.saveAndFlush(file);
    }

    @Override
    public void delete(File file) {
        fileRepository.delete(file);
    }

    @Override
    public List<File> findByDevice(Device device) {
        return fileRepository.findByDevice(device);
    }

    @Override
    public List<File> findByService(pl.maszyny_polskie.entity.Service service) {
        return fileRepository.findByService(service);
    }

    @Override
    public void flush() {
        fileRepository.flush();
    }

    @Override
    public List<File> findByDeviceAndType(Device device, String type) {
        return fileRepository.findByDeviceAndType(device, type);
    }
}
