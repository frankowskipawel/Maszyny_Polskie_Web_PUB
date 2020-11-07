package pl.maszyny_polskie.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.maszyny_polskie.entity.File;
import pl.maszyny_polskie.entity.Log;
import pl.maszyny_polskie.entity.Service;
import pl.maszyny_polskie.entity.User;
import pl.maszyny_polskie.repository.RoleRepository;
import pl.maszyny_polskie.service.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api/service")
@Api(description = "Api service")
public class ServiceRestController {

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
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
            return gson.toJson(serviceService.findAll());
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
        if (user != null && isCorrectPassword) {
            Service service = serviceService.findById(id);
            return gson.toJson(serviceService.findById(id));

        } else {
            return "";
        }
    }

    @PostMapping("/findByDevice")
    public String findByDevice(@RequestParam("id") Integer idDevice,
                               @RequestHeader("email") String email,
                               @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive()==true) {
            List<Service> list = serviceService.findByDevice(deviceService.findById(idDevice));
            List<File> fileList= new ArrayList<>();
            for (Service service : list) {
                System.out.println(fileService.findByService(service));
                fileList = fileService.findByService(service);
                List <String> fileListString = new ArrayList<>();
                for (File file : fileList) {
                    fileListString.add(file.getFilename());
                }
                service.setFiles(fileListString);
            }
            return gson.toJson(list);
        } else {
            return "";
        }
    }


    @PostMapping("/insert")
    public String insert(@RequestParam("addNextNumber") Boolean addNextNumber,
                         @RequestParam("serviceDate") String serviceDate,
                         @RequestParam("customerId") Integer customerId,
                         @RequestParam("deviceId") Integer deviceId,
                         @RequestParam(value = "type", required = false) String type,
                         @RequestParam(value = "paymentType", required = false) String paymentType,
                         @RequestParam(value = "faultDescription", required = false) String faultDescription,
                         @RequestParam(value = "inspectionReport", required = false) String inspectionReport,
                         @RequestParam(value = "rangeOfWorks", required = false) String rangeOfWorks,
                         @RequestParam(value = "materialUsed", required = false) String materialUsed,
                         @RequestParam(value = "comment", required = false) String comment,
                         @RequestParam(value = "startDate", required = false) String startDate,
                         @RequestParam(value = "startTime", required = false) String startTime,
                         @RequestParam(value = "endDate", required = false) String endDate,
                         @RequestParam(value = "endTime", required = false) String endTime,
                         @RequestParam(value = "workingTime", required = false) String workingTime,
                         @RequestParam(value = "driveDistance", required = false) String driveDistance,
                         @RequestParam(value = "daysAtHotel", required = false) String daysAtHotel,
                         @RequestParam(value = "user", required = false) String user,
                         @RequestParam(value = "state", required = false) String state,
                         @RequestParam(value = "gpsLocation", required = false) String gpsLocation,
                         @RequestHeader("email") String email,
                         @RequestHeader("password") String password
    ) {

        Integer yearOfService = Integer.parseInt(serviceDate.substring(0, 4));

        Service service = null;
        User usr = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, usr.getPassword());
        if (usr != null && isCorrectPassword && usr.isActive()==true) {
            service = Service.builder()
                    .year(yearOfService + "")
                    .date(Date.valueOf(serviceDate))
                    .customer(customerService.findById(customerId))
                    .device(deviceService.findById(deviceId))
                    .type(type)
                    .paymentType(paymentType)
                    .deviceInspectionReport(inspectionReport)
                    .rangeOfWorks(rangeOfWorks)
                    .materialsUsed(materialUsed)
                    .faultDescription(faultDescription)
                    .comments(comment)
                    .startTime(startTime)
                    .endTime(endTime)
                    .workingTime(workingTime)
                    .driveDistance(driveDistance)
                    .daysAtHotel(daysAtHotel)
                    .user(user)
                    .state(state)
                    .gpsLocation(gpsLocation)
                    .build();

            if (startDate != null) {
                service.setStartDate(Date.valueOf(startDate));
            }
            if (endDate != null) {
                service.setEndDate(Date.valueOf(endDate));
            }

            serviceService.save(service);
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new java.util.Date().toString(),
                    usr.getEmail() + "\n" + usr.getFirstName() + " " + usr.getLastName(),
                    methodName + "(); " + service));
            //--
        }
        return gson.toJson(service);
    }

    @PostMapping("/update")
    public String update(@RequestParam("id") Integer id,
                         @RequestParam("addNextNumber") Boolean addNextNumber,
                         @RequestParam("serviceDate") String date,
                         @RequestParam("customerId") Integer customerId,
                         @RequestParam("deviceId") Integer deviceId,
                         @RequestParam(value = "number", required = false) String number,
                         @RequestParam(value = "type", required = false) String type,
                         @RequestParam(value = "paymentType", required = false) String paymentType,
                         @RequestParam(value = "faultDescription", required = false) String faultDescription,
                         @RequestParam(value = "inspectionReport", required = false) String inspectionReport,
                         @RequestParam(value = "rangeOfWorks", required = false) String rangeOfWorks,
                         @RequestParam(value = "materialUsed", required = false) String materialUsed,
                         @RequestParam(value = "comment", required = false) String comment,
                         @RequestParam(value = "startDate", required = false) String startDate,
                         @RequestParam(value = "startTime", required = false) String startTime,
                         @RequestParam(value = "endDate", required = false) String endDate,
                         @RequestParam(value = "endTime", required = false) String endTime,
                         @RequestParam(value = "workingTime", required = false) String workingTime,
                         @RequestParam(value = "driveDistance", required = false) String driveDistance,
                         @RequestParam(value = "daysAtHotel", required = false) String daysAtHotel,
                         @RequestParam(value = "user", required = false) String user,
                         @RequestParam(value = "state", required = false) String state,
                         @RequestParam(value = "gpsLocation", required = false) String gpsLocation,
                         @RequestHeader("email") String email,
                         @RequestHeader("password") String password
    ) {
        int nextNumber = 0;
        Integer yearOfService = Integer.parseInt(date.substring(0, 4));
        if (addNextNumber == true ) {
            Service service = serviceService.findLastServiceWithNumber(yearOfService + "");
            if (service == null) {
                nextNumber = 1;
            } else {
                Integer lastNumber = service.getNumber();
                nextNumber = lastNumber + 1;

            }
        }
        Service service = null;
        User usr = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, usr.getPassword());
        if (usr != null
                && isCorrectPassword
                && usr.isActive()==true) {
            service = Service.builder()
                    .id(id)
                    .year(yearOfService + "")
                    .date(Date.valueOf(date))
                    .customer(customerService.findById(customerId))
                    .device(deviceService.findById(deviceId))
                    .type(type)
                    .paymentType(paymentType)
                    .deviceInspectionReport(inspectionReport)
                    .rangeOfWorks(rangeOfWorks)
                    .materialsUsed(materialUsed)
                    .faultDescription(faultDescription)
                    .comments(comment)
                    .startTime(startTime)
                    .endTime(endTime)
                    .workingTime(workingTime)
                    .driveDistance(driveDistance)
                    .daysAtHotel(daysAtHotel)
                    .user(user)
                    .state(state)
                    .gpsLocation(gpsLocation)
                    .build();
            if (addNextNumber == true) {
                service.setNumber(nextNumber);
            } else if (!number.equals("0")){
                service.setNumber(Integer.parseInt(number));
            }

            if (startDate != null) {
                service.setStartDate(Date.valueOf(startDate));
            }
            if (endDate != null) {
                service.setEndDate(Date.valueOf(endDate));
            }
            serviceService.save(service);
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new java.util.Date().toString(),
                    usr.getEmail() + "\n" + usr.getFirstName() + " " + usr.getLastName(),
                    methodName + "(); " + service));
            //--
        }
        return gson.toJson(service);
    }

    @PostMapping("/delete")
    public void delete(@RequestParam("id") Integer id,
                       @RequestHeader("email") String email,
                       @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null
                && isCorrectPassword && user.getRoles().contains(roleRepository.findByRole("ADMIN"))
                && user.isActive()==true) {
            serviceService.delete(serviceService.findById(id));
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new java.util.Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + "serviceId="+id));
            //--
        }
    }
}
