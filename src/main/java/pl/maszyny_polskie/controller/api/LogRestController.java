package pl.maszyny_polskie.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.maszyny_polskie.entity.Log;
import pl.maszyny_polskie.entity.User;
import pl.maszyny_polskie.service.LogService;
import pl.maszyny_polskie.service.UserService;

import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping("/api/log")
@Api(description = "Api log")
public class LogRestController {

    @Autowired
    private LogService logService;
    @Autowired
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    @PostMapping("/findAll")
    public String findAll(@RequestHeader("email") String email,
                          @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive()==true) {
            Pageable pageable = PageRequest.of(0, 100, Sort.Direction.DESC, "id");
            String data = gson.toJson(logService.findAll(pageable).toList());
            return data;
        } else {
            return "";
        }
    }

    @PostMapping("/findById")
    public String findById(@RequestParam("id") int id,
                           @RequestHeader("email") String email,
                           @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive()==true) {
            return gson.toJson(logService.findById(id));
        } else {
            return "";
        }
    }

    @PostMapping("/insert")
    public void insert(@RequestParam(value = "user", required = false) String user,
                       @RequestParam(value = "val", required = false) String val,
                       @RequestHeader("email") String email,
                       @RequestHeader("password") String password
    ) {
        User usr = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, usr.getPassword());
        if (usr != null && isCorrectPassword && usr.isActive()==true) {
            Date today = new Date();
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-dd-MM");
            Log log = Log.builder()
                    .date(DATE_FORMAT.format(today))
                    .user(user)
                    .value(val)
                    .build();
            logService.save(log);
        }
    }

    @PostMapping("/delete")
    public void delete(@RequestParam("id") int id,
                       @RequestHeader("email") String email,
                       @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive()==true) {
            logService.delete(logService.findById(id));
        }
    }
}
