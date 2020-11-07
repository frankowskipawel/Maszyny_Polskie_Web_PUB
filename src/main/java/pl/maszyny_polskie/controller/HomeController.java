package pl.maszyny_polskie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.maszyny_polskie.repository.ServiceRepository;


@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    ServiceRepository serviceRepository;

    @GetMapping("/")
    public String home(){

        return "home";
    }

    @GetMapping("/privacy")
    public String privacy(){

        return "privacy";
    }
}
