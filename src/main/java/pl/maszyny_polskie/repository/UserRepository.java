package pl.maszyny_polskie.repository;


import pl.maszyny_polskie.entity.Role;
import pl.maszyny_polskie.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.email =:email")
    User findUserByEmail(String email);
    List<User> findAll();
    Page<User> findAllByRoles(Pageable pageable, Role rol);
    List<User> findAllByRoles(Role role);
    void deleteById(int id);



}
