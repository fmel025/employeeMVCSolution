package com.example.taller01.controllers;

import com.example.taller01.models.dtos.LoginDTO;
import com.example.taller01.models.entities.Employee;
import com.example.taller01.services.DateValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class EmployeeController {

    private static final List<Employee> employees = new ArrayList<>();

    // The date forma is yyyy-MM-dd
    // First employee is common user, the second one is admin
    // the third one is unactive, and the last one its registration date is not valid
    static {
        employees.add(new Employee("AB123456", "Fernando","Melendez","2023-01-28", "employee", "asd12512", true));
        employees.add(new Employee("CD123456", "Cristopher","Calderon","2023-02-01", "admin", "asd12512", true));
        employees.add(new Employee("EF123456", "Marcos","Granillo","2023-01-28", "admin", "asd12512", false));
        employees.add(new Employee("GH123456", "Samuel","Ortiz","2023-04-28", "admin", "asd12512", true));
    }
    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @PostMapping("/make-login")
    public String login(@ModelAttribute LoginDTO userData, RedirectAttributes attributes){
        for (Employee employee: employees) {

            // If the employee code is not the same just continue to the next val
            if(!employee.getEmployeeCode().equals(userData.getEmployeeCode())) continue;

            // Here the employee code is valid, but checks if the password is invalid
            if(!employee.getPassword().equals(userData.getPassword())){
                attributes.addFlashAttribute("error","has ingresado credenciales invalidas");
                return "redirect:/error-page";
            }

            // Here the user code and password were valid
            // If the user is not active just return the login page again
            if(!employee.isActive()){
                return "login";
            }

            // So, at last we check if the user can access by the registrationDate
            if(!DateValidator.canAccessSystem(employee.getHiringDate())){
                attributes.addFlashAttribute("error", "aun no has empezado a laborar");
                return "redirect:/error-page";
            }

            attributes.addFlashAttribute("employee", employee);
            return "redirect:/";
        }

        attributes.addFlashAttribute("error","no se ha encontrado un usuario con las credenciales ingresadas");
        return "redirect:/error-page";
    }

    // Something funny is that is some html is named exact as the route
    // It will render the html instead of redirecting to that route
    @RequestMapping(path = "/error-page", method = RequestMethod.GET)
    public String getErrorPage(Model model){
        return "error";
    }

    @GetMapping("/")
    public String getHomePage(Model model){
        Employee employeeData = (Employee) model.getAttribute("employee");

        String name = employeeData.getName();
        String lastName = employeeData.getLastName();

        model.addAttribute("name", name);
        model.addAttribute("lastName", lastName);

        String time = Calendar.getInstance().getTime().toString();
        model.addAttribute("time",time);

        if(employeeData.getRol() != "employee") {
            model.addAttribute("employees", employees);
            return "home-admin";
        } else {
            return "home";
        }
    }

}
