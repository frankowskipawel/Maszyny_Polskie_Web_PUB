package pl.maszyny_polskie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.maszyny_polskie.entity.Device;

import java.util.List;

@Service
public interface ServiceRepository extends JpaRepository<pl.maszyny_polskie.entity.Service, Integer> {

    List<pl.maszyny_polskie.entity.Service> findByDevice(Device device);
    pl.maszyny_polskie.entity.Service findFirstByNumberIsNotAndYearEqualsOrderByNumberDesc(int zero, String Year);
}