package pl.maszyny_polskie.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.maszyny_polskie.entity.Device;
import pl.maszyny_polskie.entity.Log;
import pl.maszyny_polskie.entity.Part;
import pl.maszyny_polskie.entity.User;
import pl.maszyny_polskie.repository.RoleRepository;
import pl.maszyny_polskie.service.*;

import java.sql.Date;

@RestController
@RequestMapping("/api/device")
@Api(description = "Api device")
public class DeviceRestController {

    @Autowired
    DeviceService deviceService;
    @Autowired
    CustomerService customerService;
    @Autowired
    CategoryService categoryService;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PartService partService;
    @Autowired
    private LogService logService;
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    @PostMapping("/findAll")
    public String findAll(@RequestHeader("email") String email,
                          @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword) {
            return gson.toJson(deviceService.findAll());
        } else {
            return "";
        }
    }

    @PostMapping("/findById/{id}")
    public String findById(@PathVariable int id,
                           @RequestHeader("email") String email,
                           @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword) {
            return gson.toJson(deviceService.findById(id));
        } else {
            return "";
        }
    }

    @PostMapping("/insert")
    public void insert(@RequestParam("name") String name,
                       @RequestParam(value = "serialNumber", required = false) String serialNumber,
                       @RequestParam(value = "sourcePower", required = false) String sourcePower,
                       @RequestParam("customerId") Integer customerId,
                       @RequestParam("categoryId") Integer categoryId,
                       @RequestParam(value = "transferDate", required = false) String transferDate,
                       @RequestParam(value = "gpsLocation", required = false) String gpsLocation,
                       @RequestParam(value = "streetAddress", required = false) String streetAddress,
                       @RequestParam(value = "zipCode", required = false) String zipCode,
                       @RequestParam(value = "city", required = false) String city,
                       @RequestHeader("email") String email,
                       @RequestHeader("password") String password
    ) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword) {
            Device device = Device.builder()
                    .name(name)
                    .serialNumber(serialNumber)
                    .sourcePower(sourcePower)
                    .customer(customerService.findById(customerId))
                    .category(categoryService.findById(categoryId))
                    .transferDate(Date.valueOf(transferDate))
                    .gpsLocation(gpsLocation)
                    .streetAddress(streetAddress)
                    .zipCode(zipCode)
                    .city(city)
                    .build();
            deviceService.save(device);
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new java.util.Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + device));
            //--
        }
    }

    @PostMapping("/update")
    public void update(@RequestParam("id") Integer id,
                       @RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "serialNumber", required = false) String serialNumber,
                       @RequestParam(value = "sourcePower", required = false) String sourcePower,
                       @RequestParam(value = "customerId", required = false) Integer customerId,
                       @RequestParam(value = "categoryId", required = false) Integer categoryId,
                       @RequestParam(value = "transferDate", required = false) String transferDate,
                       @RequestParam(value = "gpsLocation", required = false) String gpsLocation,
                       @RequestParam(value = "streetAddress", required = false) String streetAddress,
                       @RequestParam(value = "zipCode", required = false) String zipCode,
                       @RequestParam(value = "city", required = false) String city,
                       @RequestHeader("email") String email,
                       @RequestHeader("password") String password
    ) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword
                && (user.getRoles().contains(roleRepository.findByRole("ADMIN"))
                || gpsLocation != null)
                && user.isActive() == true) {

            Device device = deviceService.findById(id);
            if (name != null) {
                device.setName(name);
            }
            if (serialNumber != null) {
                device.setSerialNumber(serialNumber);
            }
            if (sourcePower != null) {
                device.setSourcePower(sourcePower);
            }
            if (customerId != null) {
                device.setCustomer(customerService.findById(customerId));
            }
            if (categoryId != null) {
                device.setCategory(categoryService.findById(categoryId));
            }
            if (transferDate != null) {
                device.setTransferDate(Date.valueOf(transferDate));
            }
            if (gpsLocation != null) {
                device.setGpsLocation(gpsLocation);
            }
            if (streetAddress != null) {
                device.setStreetAddress(streetAddress);
            }
            if (zipCode != null) {
                device.setZipCode(zipCode);
            }
            if (city != null) {
                device.setCity(city);
            }
            deviceService.save(device);
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new java.util.Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + device));
            //--
        }
    }

    @PostMapping("/findByName")
    public String findByName(@RequestParam("name") String name,
                             @RequestHeader("email") String email,
                             @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive() == true) {
            return gson.toJson(deviceService.findByName(name));
        } else {
            return "";
        }

    }

    @PostMapping("/delete")
    public void delete(@RequestParam("id") Integer id,
                       @RequestHeader("email") String email,
                       @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null
                && isCorrectPassword
                && user.getRoles().contains(roleRepository.findByRole("ADMIN"))
                && user.isActive() == true) {
            deviceService.delete(deviceService.findById(id));
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new java.util.Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + "deviceId="+id));
            //--
        }
    }

    @PostMapping("findByCustomer")
    public String findByCustomer(@RequestParam("id") int id,
                                 @RequestHeader("email") String email,
                                 @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive() == true) {
            return gson.toJson(deviceService.findByCustomer(customerService.findById(id)));
        } else {
            return "";
        }
    }

    @PostMapping("findByCategory")//
    public String findByCategory(@RequestParam("id") Integer id,
                                 @RequestHeader("email") String email,
                                 @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive() == true) {
            return gson.toJson(deviceService.findByCategory(categoryService.findById(id)));
        } else {
            return "";
        }
    }

    @PostMapping("insertPart")
    public void insertPart(@RequestParam("idDevice") Integer idDevice,
                           @RequestParam("name") String partName,
                           @RequestParam(value = "serialNumber", required = false) String partSerialNumber,
                           @RequestParam(value = "producer", required = false) String partProducer,
                           @RequestParam(value = "decsription", required = false) String partDescription,
                           @RequestHeader("email") String email,
                           @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive() == true) {
            Part part = Part.builder()
                    .name(partName)
                    .serialNumber(partSerialNumber)
                    .producer(partProducer)
                    .decsription(partDescription)
                    .build();
            Device device = deviceService.findById(idDevice);
            device.getParts().add(part);
            deviceService.save(device);
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new java.util.Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + part));
            //--
        }
    }

    @PostMapping("updatePart")
    public void updatePart(@RequestParam("idPart") Integer idPart,
                           @RequestParam("name") String partName,
                           @RequestParam(value = "serialNumber", required = false) String partSerialNumber,
                           @RequestParam(value = "producer", required = false) String partProducer,
                           @RequestParam(value = "description", required = false) String partDescription,
                           @RequestHeader("email") String email,
                           @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive() == true) {
            Part part = partService.findById(idPart);
            part.setName(partName);
            part.setSerialNumber(partSerialNumber);
            part.setProducer(partProducer);
            part.setDecsription(partDescription);
            partService.update(part);
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new java.util.Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + part));
            //--
        }
    }

    @PostMapping("deletePart")
    public void deletePart(@RequestParam("idDevice") Integer idDevice,
                           @RequestParam("idPart") int idPart,
                           @RequestHeader("email") String email,
                           @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        Device device = null;
        Part partToDelete = null;
        if (user != null && isCorrectPassword && user.isActive() == true) {
            device = deviceService.findById(idDevice);
            partToDelete = device.getParts().stream().filter(part -> part.getId() == idPart).findFirst().get();
        }
        device.getParts().remove(partToDelete);
        deviceService.save(device);
        partService.delete(partToDelete);
        // log
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        logService.save(new Log(0,
                new java.util.Date().toString(),
                user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                methodName + "(); " + "idPart="+idPart));
        //--
    }
}
