package pl.maszyny_polskie.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.maszyny_polskie.entity.DeviceNote;
import pl.maszyny_polskie.entity.Log;
import pl.maszyny_polskie.entity.User;
import pl.maszyny_polskie.repository.RoleRepository;
import pl.maszyny_polskie.service.DeviceNoteService;
import pl.maszyny_polskie.service.DeviceService;
import pl.maszyny_polskie.service.LogService;
import pl.maszyny_polskie.service.UserService;

import java.util.Date;


@RestController
@RequestMapping("/api/deviceNote")
@Api(description = "Api deviceNote")
public class DeviceNoteRestController {

    @Autowired
    private DeviceNoteService deviceNoteService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    @PostMapping("/findAll")
    public String findAll(@RequestHeader("email") String email,
                          @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive()==true) {
            return gson.toJson(deviceNoteService.findAll());
        } else {
            return "";
        }
    }

    @PostMapping("/findById")
    public String findById(@RequestParam("id") Integer id,
                           @RequestHeader("email") String email,
                           @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive()==true) {
            return gson.toJson(deviceNoteService.findById(id));
        } else {
            return "";
        }
    }

    @PostMapping("/insert")
    public void insert(@RequestParam("deviceId") Integer deviceId,
                       @RequestParam("text") String text,
                       @RequestHeader("email") String email,
                       @RequestHeader("password") String password
    ) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive()==true) {
            DeviceNote deviceNote = DeviceNote.builder()
                    .device(deviceService.findById(deviceId))
                    .text(text)
                    .build();
            deviceNoteService.save(deviceNote);
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + deviceNote));
            //--
        }

    }

    @PostMapping("/update")
    public void update(@RequestParam("id") Integer id,
                       @RequestParam(value = "deviceId", required = false) Integer deviceId,
                       @RequestParam(value = "text", required = false) String text,
                       @RequestHeader("email") String email,
                       @RequestHeader("password") String password
    ) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null
                && isCorrectPassword
                && user.getRoles().contains(roleRepository.findByRole("ADMIN"))
                && user.isActive()==true) {
            DeviceNote deviceNote = deviceNoteService.findById(id);
            if (deviceId != null) {
                deviceNote.setDevice(deviceService.findById(deviceId));
            }
            if (text != null) {
                deviceNote.setText(text);
            }
            deviceNoteService.save(deviceNote);
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + deviceNote));
            //--
        }
    }

    @PostMapping("/delete")
    public void delete(@RequestParam("id") Integer id,
                       @RequestHeader("email") String email,
                       @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword
                && user.getRoles().contains(roleRepository.findByRole("ADMIN"))
                && user.isActive()==true) {

            deviceNoteService.delete(deviceNoteService.findById(id));
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + "deviceNoteId="+id));
            //--
        }
    }

    @PostMapping("/findByIdDevice")
    public String findAllByIdDevice(@RequestParam("id") Integer id,
                                    @RequestHeader("email") String email,
                                    @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive()==true) {
            return gson.toJson(deviceNoteService.findByDevice(deviceService.findById(id)));
        } else {
            return "";
        }
    }
}
