package pl.maszyny_polskie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.maszyny_polskie.entity.Log;
import pl.maszyny_polskie.repository.LogRepository;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository logRepository;


    @Override
    public List<Log> findAll() {
        return logRepository.findAll();
    }

    @Override
    public Page<Log> findAll(Pageable pageable) {
        Page<Log> logs = logRepository.findAll(pageable);

        return logs;
    }

    @Override
    public Log findById(int id) {
        return logRepository.findById(id).get();
    }

    @Override
    public Log save(Log log) {
        return logRepository.save(log);
    }

    @Override
    public void delete(Log log) {
        logRepository.delete(log);
    }
}
