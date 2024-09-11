package com.SmartBridge.HouseRent.controller;

import com.SmartBridge.HouseRent.models.ApplicationModel;
import com.SmartBridge.HouseRent.models.PropertiesModel;
import com.SmartBridge.HouseRent.models.UserModel;
import com.SmartBridge.HouseRent.service.ApplicationService;
import com.SmartBridge.HouseRent.service.PropertyService;
import com.SmartBridge.HouseRent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Controller
public class RouteController {

    @Autowired
    UserService userService;

    @Autowired
    PropertyService propertyService;

    @Autowired
    ApplicationService applicationService;

    // Landing Page
    @RequestMapping("/")
    public ModelAndView landingPage(){
        return new ModelAndView("landing");
    }

    // Login Page
    @RequestMapping("/login")
    public ModelAndView loginPage(){
        return new ModelAndView("login");
    }

    // Handling Login Action
    @PostMapping("/login-user")
    public UserModel loginUser(@ModelAttribute UserModel userData, Model model) {
        try {
            UserModel user = userService.findByEmail(userData.getEmail());
            if (user != null && user.getPassword().equals(userData.getPassword())) {
                return user;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    // Registration Page
    @RequestMapping("/register")
    public ModelAndView registerPage(){
        return new ModelAndView("register");
    }

    // Handling User Registration
    @PostMapping("/register-user")
    public UserModel registerUser(@ModelAttribute UserModel userData, Model model) {
        try {
            return userService.saveUser(userData);
        } catch (Exception e) {
            return null;
        }
    }

    // User's Home Page
    @RequestMapping("/user/{id}")
    public ModelAndView userHomePage(@PathVariable("id") String id){
        List<PropertiesModel> propertiesList = propertyService.findAll();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("userId", id);
        modelMap.addAttribute("propertiesList", propertiesList);
        return new ModelAndView("user/home", modelMap);
    }

    // Booking a Property by User
    @PostMapping("/book-property")
    public ModelAndView bookUserProperty(@ModelAttribute ApplicationModel applicationData, Model model){
        Optional<UserModel> user = userService.findById(applicationData.getApplicantId());
        Optional<PropertiesModel> property = propertyService.findById(applicationData.getPropertyId());

        if(user.isPresent() && property.isPresent()){
            UserModel userData = user.get();
            PropertiesModel propertyData = property.get();
            applicationData.setOwnerId(propertyData.getOwnerId());
            applicationData.setApplicantName(userData.getUsername());
            applicationData.setApplicantEmail(userData.getEmail());
            applicationData.setStatus("Pending");

            List<String> applicantsList = propertyData.getApplicantsList();
            applicantsList.add(userData.get_id());
            propertyData.setApplicantsList(applicantsList);

            applicationService.saveApplication(applicationData);
            propertyService.saveProperty(propertyData);
        }

        return new ModelAndView("redirect:/user/" + user.get().get_id());
    }

    // View Property Details
    @RequestMapping("/property/{propertyId}")
    public ModelAndView viewPropertyDetails(@PathVariable("propertyId") String propertyId) {
        Optional<PropertiesModel> property = propertyService.findById(propertyId);
        ModelMap modelMap = new ModelMap();
        property.ifPresent(p -> modelMap.addAttribute("property", p));
        return new ModelAndView("property/details", modelMap);
    }

    // User's Applications
    @RequestMapping("/user/{id}/applications")
    public ModelAndView viewUserApplications(@PathVariable("id") String userId) {
        List<ApplicationModel> applications = applicationService.findAll().stream()
                .filter(application -> application.getApplicantId().equals(userId))
                .collect(Collectors.toList());

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("applications", applications);
        modelMap.addAttribute("userId", userId);
        return new ModelAndView("user/applications", modelMap);
    }

    // View all Properties for Admin
    @RequestMapping("/admin/properties")
    public ModelAndView adminViewProperties(){
        List<PropertiesModel> propertiesList = propertyService.findAll();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("propertiesList", propertiesList);
        return new ModelAndView("admin/properties", modelMap);
    }

    // Approve Application by Admin
    @PostMapping("/admin/application/approve")
    public ModelAndView approveApplication(@RequestParam("applicationId") String applicationId) {
        Optional<ApplicationModel> application = applicationService.findById(applicationId);
        application.ifPresent(app -> {
            app.setStatus("Approved");
            applicationService.saveApplication(app);
        });
        return new ModelAndView("redirect:/admin/applications");
    }

    // View All Applications for Admin
    @RequestMapping("/admin/applications")
    public ModelAndView viewAllApplications() {
        List<ApplicationModel> applications = applicationService.findAll();
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("applications", applications);
        return new ModelAndView("admin/applications", modelMap);
    }

    // Reject Application by Admin
    @PostMapping("/admin/application/reject")
    public ModelAndView rejectApplication(@RequestParam("applicationId") String applicationId) {
        Optional<ApplicationModel> application = applicationService.findById(applicationId);
        application.ifPresent(app -> {
            app.setStatus("Rejected");
            applicationService.saveApplication(app);
        });
        return new ModelAndView("redirect:/admin/applications");
    }

    // View Admin Dashboard
    @RequestMapping("/admin/dashboard")
    public ModelAndView adminDashboard() {
        return new ModelAndView("admin/dashboard");
    }
}
