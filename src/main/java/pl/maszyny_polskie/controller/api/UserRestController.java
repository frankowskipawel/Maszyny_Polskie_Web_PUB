package pl.maszyny_polskie.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.maszyny_polskie.dto.UserDto;
import pl.maszyny_polskie.entity.Log;
import pl.maszyny_polskie.entity.Role;
import pl.maszyny_polskie.entity.User;
import pl.maszyny_polskie.repository.RoleRepository;
import pl.maszyny_polskie.service.LogService;
import pl.maszyny_polskie.service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin(origins = "*")
@Api(description = "Api service")
public class UserRestController {

    @Autowired
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private LogService logService;

    @PostMapping("/auth")
    public String auth(@RequestHeader("email") String email,
                       @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = null;
        if (user != null) {
            isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        }
        if (user != null && isCorrectPassword && user.isActive() == true) {
            user.setPassword(null);
            return gson.toJson(user);
        } else {
            return gson.toJson(new User());
        }
    }

    @PostMapping("/findAll")
    public String findAll(@RequestHeader("email") String email,
                          @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = null;
        if (user != null) {
            isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        }
        if (user != null && isCorrectPassword && user.isActive() == true) {
            List<User> users = userService.findAll();
            users.stream().forEach(user1 -> user1.setPassword(null));
            return gson.toJson(users);
        } else {
            return null;
        }
    }

    @PostMapping("/findAllDto")
    public String findAllDto(@RequestHeader("email") String email,
                             @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = null;
        if (user != null) {
            isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        }
        if (user != null && isCorrectPassword && user.isActive() == true) {
            List<User> users = userService.findAll();
            users.stream().forEach(user1 -> user1.setPassword(null));
            List<UserDto> listDto = new ArrayList<>();
            for (User user1 : users) {
                String active = "";

                if (user1.isActive()) {
                    active = "TAK";
                } else {
                    active = "NIE";
                }
                String roles="";
                for (Role role : user1.getRoles()) {
                    roles = roles+role.getRole()+", ";
                }
                roles = roles.substring(0,roles.length()-2);

                UserDto userDto = UserDto.builder()
                        .id(user1.getId())
                        .userName(user1.getUserName())
                        .password(user1.getPassword())
                        .email(user1.getEmail())
                        .firstName(user1.getFirstName())
                        .lastName(user1.getLastName())
                        .country(user1.getCountry())
                        .active(active)
                        .roles(roles)
                        .build();
                listDto.add(userDto);
            }
            return gson.toJson(listDto);
        } else {
            return null;
        }
    }

    @PostMapping("/insert")
    public boolean insert(@RequestParam(value = "userName", required = false) String userName,
                          @RequestHeader("passwordUser") String passwordUser,
                          @RequestHeader("emailUser") String emailUser,
                          @RequestParam(value = "firstName", required = false) String firstName,
                          @RequestParam(value = "lastName", required = false) String lastName,
                          @RequestParam(value = "country", required = false) String country,
                          @RequestParam("active") Boolean active,
                          @RequestParam("role") String role,
                          @RequestHeader("email") String email,
                          @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword
                && user.getRoles().contains(roleRepository.findByRole("ADMIN"))
                && user.isActive() == true) {
            User newUser = new User();
            newUser.setUserName(userName);
            newUser.setPassword(passwordUser);
            newUser.setEmail(emailUser);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setCountry(country);
            newUser.setActive(active);
            newUser.setRoles(new HashSet<Role>() {
            });
            newUser.getRoles().add(roleRepository.findByRole(role));
            userService.saveWithEncryptPassword(newUser);
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + newUser.getEmail()));
            //--
            return true;
        }
        return false;
    }

    @PostMapping("/update")
    public boolean update(@RequestParam("id") Integer id,
                          @RequestParam(value = "userName", required = false) String userName,
                          @RequestHeader(value = "passwordUser", required = false) String passwordUser,
                          @RequestHeader("emailUser") String emailUser,
                          @RequestParam(value = "firstName", required = false) String firstName,
                          @RequestParam(value = "lastName", required = false) String lastName,
                          @RequestParam(value = "country", required = false) String country,
                          @RequestParam("active") Boolean active,
                          @RequestParam(value = "role", required = false) String role,
                          @RequestHeader("email") String email,
                          @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword
                && user.getRoles().contains(roleRepository.findByRole("ADMIN"))
                && user.isActive() == true) {
            User updatedUser = userService.findById(id);

            if (userName != null) {
                updatedUser.setUserName(userName);
            }
            if (firstName != null) {
                updatedUser.setFirstName(firstName);
            }
            if (lastName != null) {
                updatedUser.setLastName(lastName);
            }
            if (country != null) {
                updatedUser.setCountry(country);
            }
            updatedUser.setActive(active);
            if (role != null) {
                updatedUser.getRoles().clear();
                updatedUser.getRoles().add(roleRepository.findByRole(role));
            }
            if (!passwordUser.equals("")) {
                updatedUser.setPassword(passwordUser);
                userService.saveWithEncryptPassword(updatedUser);
                return true;
            }
            userService.save(updatedUser);
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + updatedUser.getEmail()));
            //--
            return true;
        }
        return false;
    }

    @PostMapping("/delete")
    public boolean delete(@RequestParam("id") Integer id,
                          @RequestHeader("email") String email,
                          @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword
                && user.getRoles().contains(roleRepository.findByRole("ADMIN"))
                && user.isActive() == true) {
            userService.delete(id);
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + "id="+id));
            //--
            return true;
        } else {
            return false;
        }
    }

    @PostMapping("/findById")
    public String findById(@RequestParam("id") Integer id,
                           @RequestHeader("email") String email,
                           @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword
                && user.getRoles().contains(roleRepository.findByRole("ADMIN"))
                && user.isActive() == true) {
            User user1 = userService.findById(id);
            return gson.toJson(user1);
        } else {
            return "";
        }
    }
}
