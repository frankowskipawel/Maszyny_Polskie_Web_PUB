package pl.maszyny_polskie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.maszyny_polskie.entity.Device;
import pl.maszyny_polskie.entity.DeviceNote;

import java.util.List;

@Repository
public interface DeviceNoteRepository extends JpaRepository<DeviceNote, Integer> {

    List<DeviceNote> findByDevice(Device device);
}
