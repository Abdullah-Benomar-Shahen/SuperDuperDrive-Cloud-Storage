package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String getHomepage(){
        // TODO: Check if User is Authenticated
        return "Home";
    }


    @GetMapping("/login")
    public String getLoginPage(@ModelAttribute("user") User user, @RequestParam(required = false, name = "error") Boolean error, @RequestParam(required = false, name = "loggedOut") Boolean loggedOut, Model model){
        if(user != null)
            logger.debug("Received user info from Login Form: ".concat(user.toString()));

        Boolean errorOccurred = error != null && error;
        Boolean isLoggedOut = loggedOut != null && loggedOut;

        model.addAttribute("errorOccurred", errorOccurred);
        model.addAttribute("isLoggedOut", isLoggedOut);
        model.addAttribute("toLogin", true);
        model.addAttribute("loggedInSuccessfully", false);

        return "Login";
    }

    @GetMapping("/signup")
    public String signupForm(@ModelAttribute("user") User user, Model model) {

        if(user != null)
            logger.debug("Received user info from Signup Form: ".concat(user.toString()));

        model.addAttribute("toSignUp", true);
        model.addAttribute("signupDoneSuccessfully", false);
        model.addAttribute("errorOccurred", false);

        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute("user") User user, Model model){

        logger.debug("Received user info from get Signup Form: ".concat(user.toString()));

        if (!userService.isUsernameAvailable(user.getUsername())){
            model.addAttribute("toSignUp", true);
            model.addAttribute("signupDoneSuccessfully", false);
            model.addAttribute("errorOccurred", true);
        } else {
            model.addAttribute("toSignUp", false);
            model.addAttribute("signupDoneSuccessfully", true);
            model.addAttribute("errorOccurred", false);
            userService.createUser(user);
        }

        return "Signup";
    }

    @GetMapping("/logout")
    public String logout(@ModelAttribute("user") User user, Model model){
        return getLoginPage(user, false, true, model);
    }

    @GetMapping("/result")
    public String showResult(Authentication authentication, @RequestParam(required = false, name = "isSuccess") Boolean isSuccess, @RequestParam(required = false, name = "errorType") Integer errorType, Model model) {
        model.addAttribute("isSuccess", isSuccess);
        model.addAttribute("errorType", errorType);
        return "result";
    }

}
