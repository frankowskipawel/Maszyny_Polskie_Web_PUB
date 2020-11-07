package pl.maszyny_polskie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.maszyny_polskie.entity.Part;


@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {


}
