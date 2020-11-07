package pl.maszyny_polskie.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.maszyny_polskie.entity.Log;


@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {

    Page<Log> findAll(Pageable pageable);
}
