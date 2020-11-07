package pl.maszyny_polskie.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.maszyny_polskie.entity.Customer;
import pl.maszyny_polskie.entity.Log;
import pl.maszyny_polskie.entity.User;
import pl.maszyny_polskie.repository.RoleRepository;
import pl.maszyny_polskie.service.CustomerService;
import pl.maszyny_polskie.service.LogService;
import pl.maszyny_polskie.service.UserService;

import java.util.Date;


@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:3000/")
@Api(description = "Api customer")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    @PostMapping("/findAll")
    public String findAll(
            @RequestHeader(value = "email", required = false) String email,
            @RequestHeader(value = "password", required = false) String password
    ) {

        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive() == true) {


            return gson.toJson(customerService.findAll());
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
        if (user != null && isCorrectPassword && user.isActive() == true) {
            return gson.toJson(customerService.findById(id));
        } else {
            return "";
        }
    }

    @PostMapping("/insert")
    public void insert(@RequestParam("shortName") String shortName,
                       @RequestParam("fullName") String fullName,
                       @RequestParam("street") String street,
                       @RequestParam("zipCode") String zipCode,
                       @RequestParam("city") String city,
                       @RequestParam("nip") String nip,
                       @RequestParam("regon") String regon,
                       @RequestParam("phone") String phone,
                       @RequestParam("emailCustomer") String emailCustomer,
                       @RequestHeader("email") String email,
                       @RequestHeader("password") String password
    ) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive() == true) {
            Customer customer = Customer.builder()
                    .shortName(shortName)
                    .fullName(fullName)
                    .streetAddress(street)
                    .zipCode(zipCode)
                    .city(city)
                    .nip(nip)
                    .regon(regon)
                    .phoneNumber(phone)
                    .email(emailCustomer)
                    .build();
            customerService.save(customer);
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + customer));
            //--
        }
    }

    @PostMapping("/update")
    public void update(@RequestParam("id") Integer id,
                       @RequestParam(value = "shortName", required = false) String shortName,
                       @RequestParam(value = "fullName", required = false) String fullName,
                       @RequestParam(value = "street", required = false) String street,
                       @RequestParam(value = "zipCode", required = false) String zipCode,
                       @RequestParam(value = "city", required = false) String city,
                       @RequestParam(value = "nip", required = false) String nip,
                       @RequestParam(value = "regon", required = false) String regon,
                       @RequestParam(value = "phone", required = false) String phone,
                       @RequestParam(value = "emailCustomer", required = false) String emailCustomer,
                       @RequestHeader("email") String email,
                       @RequestHeader("password") String password
    ) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword
                && user.getRoles().contains(roleRepository.findByRole("ADMIN"))
                && user.isActive() == true) {
            Customer customer = customerService.findById(id);
            if (shortName != null) {
                customer.setShortName(shortName);
            }
            if (fullName != null) {
                customer.setFullName(fullName);
            }
            if (street != null) {
                customer.setStreetAddress(street);
            }
            if (zipCode != null) {
                customer.setZipCode(zipCode);
            }
            if (city != null) {
                customer.setCity(city);
            }
            if (nip != null) {
                customer.setNip(nip);
            }
            if (regon != null) {
                customer.setRegon(regon);
            }
            if (phone != null) {
                customer.setPhoneNumber(phone);
            }
            if (emailCustomer != null) {
                customer.setEmail(emailCustomer);
            }

            customerService.save(customer);
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + customer));
            //--
        }
    }

    @PostMapping("/findByShortName")
    public String findByShortName(@RequestParam("shortName") String shortName,
                                  @RequestHeader("email") String email,
                                  @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive() == true) {
            return gson.toJson(customerService.findByShortName(shortName));
        }
        return "";
    }


    @PostMapping("/delete")
    public void deleteByName(@RequestParam("id") int id,
                             @RequestHeader("email") String email,
                             @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword
                && user.getRoles().contains(roleRepository.findByRole("ADMIN"))
                && user.isActive() == true) {
            Customer customer = customerService.findById(id);
            customerService.delete(customer);
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + customer));
            //--
        }
    }
}
