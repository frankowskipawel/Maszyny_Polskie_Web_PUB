package pl.maszyny_polskie.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.maszyny_polskie.entity.Category;
import pl.maszyny_polskie.entity.Log;
import pl.maszyny_polskie.entity.User;
import pl.maszyny_polskie.repository.RoleRepository;
import pl.maszyny_polskie.service.CategoryService;
import pl.maszyny_polskie.service.LogService;
import pl.maszyny_polskie.service.UserService;


import java.util.Date;


@RestController
@RequestMapping("/api/category")
@Api(description = "Api category")
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LogService logService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();


    @PostMapping("/findAll")
    public String findAll(@RequestHeader("email") String email,
                          @RequestHeader("password") String password
    ) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive() == true) {


            return gson.toJson(categoryService.findAll());
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
            return gson.toJson(categoryService.findById(id));
        } else {
            return "";
        }
    }

    @PostMapping("/findByName")
    public String findByName(@RequestParam("name") String name,
                             @RequestHeader("email") String email,
                             @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive() == true) {
            return gson.toJson(categoryService.findByName(name));
        } else {
            return "";
        }
    }

    @PostMapping("/insert")
    public void insert(@RequestParam(value = "name") String name,
                       @RequestParam(value = "serviceReviewOperationsList", required = false) String serviceReviewOperationsList,
                       @RequestParam(value = "operatorTrainingList", required = false) String operatorTrainingList,
                       @RequestHeader("email") String email,
                       @RequestHeader("password") String password
    ) {

        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive() == true) {
            Category category = Category.builder()
                    .name(name)
                    .serviceReviewOperationsList(serviceReviewOperationsList)
                    .operatorTrainingList(operatorTrainingList)
                    .build();
            System.out.println(">>><<<<>>>><<<<+++++" + name);
            System.out.println(">>><<<<>>>><<<<+++++" + category);
            categoryService.save(category);


            // log
            String methodName = new Object() {
            }
                    .getClass()
                    .getEnclosingMethod()
                    .getName();

            logService.save(new Log(0,
                    new Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + category.toString()));
            //--
        }
    }


    @PostMapping("/update")
    public void update(@RequestParam("id") int id,
                       @RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "serviceReviewOperationsList", required = false) String serviceReviewOperationsList,
                       @RequestParam(value = "operatorTrainingList", required = false) String operatorTrainingList,
                       @RequestHeader("email") String email,
                       @RequestHeader("password") String password

    ) {

        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword
                && user.getRoles().contains(roleRepository.findByRole("ADMIN"))
                && user.isActive() == true) {
            Category category = categoryService.findById(id);
            if (name != null) {
                category.setName(name);
            }
            if (serviceReviewOperationsList != null) {
                category.setServiceReviewOperationsList(serviceReviewOperationsList);
            }
            if (operatorTrainingList != null) {
                category.setOperatorTrainingList(operatorTrainingList);
            }
            categoryService.save(category);
            // log
            String methodName = new Object() {
            }
                    .getClass()
                    .getEnclosingMethod()
                    .getName();

            logService.save(new Log(0,
                    new Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + category.toString()));
            //--
        }

    }

    @PostMapping("/delete/{id}")
    public void delete(@PathVariable int id,
                       @RequestHeader("email") String email,
                       @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword
                && user.getRoles().contains(roleRepository.findByRole("ADMIN"))
                && user.isActive()==true) {
            categoryService.delete(categoryService.findById(id));
            // log
            String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
            logService.save(new Log(0,
                    new Date().toString(),
                    user.getEmail() + "\n" + user.getFirstName() + " " + user.getLastName(),
                    methodName + "(); " + "categoryId="+id));
            //--
        }
    }

    @PostMapping("/deleteByName")
    public void deleteByName(@RequestParam("name") String name,
                             @RequestHeader("email") String email,
                             @RequestHeader("password") String password) {
        User user = userService.findUserByUserName(email);
        Boolean isCorrectPassword = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (user != null && isCorrectPassword && user.isActive()==true) {
            Category category = categoryService.findByName(name);
            categoryService.delete(category);
        }
    }
}
