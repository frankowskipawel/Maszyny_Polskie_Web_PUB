package pl.maszyny_polskie.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.maszyny_polskie.entity.File;
import pl.maszyny_polskie.entity.Log;
import pl.maszyny_polskie.entity.User;
import pl.maszyny_polskie.repository.RoleRepository;
import pl.maszyny_polskie.service.*;


@RestController
@RequestMapping("/api/file")
@Api(description = "Api file")
public class FileRestController {

    @Autowired
    private FileService fileService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private ServiceService serviceService;
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
        if (user != null && isCorrectPassword && user.isActive() == true) {
            return gson.toJson(fileService.findAll());
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
        if (user != null && isCorrectPassword && user.isActive() == true) {
            return gson.toJson(fileService.findById(id));
        } else {
            return "";
        }
    }

    @PostMapping("/insert")
    public String insert(@RequestParam(value = "idDevice", required = false) Integer idDevice,
                         @RequestParam(value = "idService", required = false) Integer idService,
                         @RequestParam(value = "date", required = false) String date,
                         @RequestParam(value = "type", required = false) String type,
                         @RequestParam(value = "description", required = false) String description,
                         @RequestParam(value = "filename", required = false) String filename,
                         @RequestHeader("email") String email,
                         @RequestHeader("password") String password
    ) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive() == true) {
            File file = File.builder()
                    .date(date)
                    .type(type)
                    .description(description)
                    .filename(filename)
                    .build();
            if (idDevice != null) {
                file.setDevice(deviceService.findById(idDevice));
            }
            if (idService != null) {
                file.setService(serviceService.findById(idService));
            }
            File fileWithId = fileService.save(file);
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new java.util.Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + file));
            //--
            return gson.toJson(fileWithId);
        }
        return "";
    }

    @PostMapping("/update")
    public void update(@RequestParam("id") Integer id,
                       @RequestParam(value = "idDevice", required = false) Integer idDevice,
                       @RequestParam(value = "idService", required = false) Integer idService,
                       @RequestParam(value = "date", required = false) String date,
                       @RequestParam(value = "type", required = false) String type,
                       @RequestParam(value = "description", required = false) String description,
                       @RequestParam(value = "filename", required = false) String filename,
                       @RequestHeader("email") String email,
                       @RequestHeader("password") String password
    ) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null
                && isCorrectPassword
                && user.isActive() == true) {
            File file = fileService.findById(id);
            if (idDevice != null) {
                file.setDevice(deviceService.findById(idDevice));
            }
            if (idService != null) {
                file.setService(serviceService.findById(idService));
            }
            if (date != null) {
                file.setDate(date);
            }
            if (type != null) {
                file.setType(type);
            }
            if (description != null) {
                file.setDescription(description);
            }
            if (filename != null) {
                file.setFilename(filename);
            }
            fileService.save(file);
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new java.util.Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + file));
            //--
        }
    }

    @PostMapping("/delete")
    public void delete(@RequestParam("id") int id,
                       @RequestHeader("email") String email,
                       @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null
                && isCorrectPassword
                && user.getRoles().contains(roleRepository.findByRole("ADMIN"))
                && user.isActive() == true) {
            fileService.delete(fileService.findById(id));
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new java.util.Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + "fileId="+id));
            //--
        }
    }

    @PostMapping("/findByDevice")
    public String findByDevice(@RequestParam("idDevice") Integer id,
                               @RequestHeader("email") String email,
                               @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive() == true) {
            return gson.toJson(fileService.findByDevice(deviceService.findById(id)));
        } else {
            return "";
        }
    }

    @PostMapping("/findByService")
    public String findByService(@RequestParam("idService") Integer id,
                                @RequestHeader("email") String email,
                                @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive() == true) {
            return gson.toJson(fileService.findByService(serviceService.findById(id)));
        } else {
            return "";
        }
    }

    @PostMapping("/findByDeviceAndType")
    public String findByDeviceAndType(@RequestParam("idDevice") Integer id,
                                      @RequestParam("type") String type,
                                      @RequestHeader("email") String email,
                                      @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive() == true) {
            return gson.toJson(fileService.findByDeviceAndType(deviceService.findById(id), type));
        } else {
            return "";
        }
    }
}
