package pl.maszyny_polskie.service;

import org.springframework.security.core.userdetails.UserDetails;
import pl.maszyny_polskie.entity.User;
import java.util.List;

public interface UserService {

    UserDetails loadUserByUsername(String email);
    User findUserByUserName(String email);
    User saveWithEncryptPassword(User user);
    List<User> findAll();
    void delete(String email);
    User findById(int id);
    void delete(int id);
    User save(User user);
}
