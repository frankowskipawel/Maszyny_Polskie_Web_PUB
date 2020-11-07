package pl.maszyny_polskie.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.maszyny_polskie.entity.Log;

import java.util.List;

@Service
public interface LogService {

    List<Log> findAll();
    Page<Log> findAll(Pageable pageable);
    Log findById(int id);
    Log save(Log log);
    void delete(Log log);
}
