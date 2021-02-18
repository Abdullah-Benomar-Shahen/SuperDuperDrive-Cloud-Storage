package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final UserService userService;
    private final FileService fileService;
    private final CredentialService credentialService;
    private final NoteService noteService;

    public HomeController(UserService userService, FileService fileService, CredentialService credentialService, NoteService noteService) {
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.noteService = noteService;
    }

    @GetMapping("/home")
    public String getHomepage(
            @ModelAttribute("credential") Credential credential,
            @ModelAttribute("note") Note note,
            @ModelAttribute("file") File file,
            Authentication authentication,
            Model model
    ){
        String usernameOfCurrentUser = (String) authentication.getPrincipal();
        Integer idOfCurrentUser = this.userService.getUserID(usernameOfCurrentUser);

        model.addAttribute("noteList", this.noteService.getAllUserNotes(idOfCurrentUser));
        model.addAttribute("credentialList", this.credentialService.getAllUserCredentials(idOfCurrentUser));
        model.addAttribute("fileList", this.fileService.getAllUserFiles(idOfCurrentUser));

        return "Home";
    }


    @GetMapping("/login")
    public String getLoginPage(@ModelAttribute("user") User user, @RequestParam(required = false, name = "error") Boolean error, @RequestParam(required = false, name = "loggedOut") Boolean loggedOut, Model model){

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

        model.addAttribute("toSignUp", true);
        model.addAttribute("signupDoneSuccessfully", false);
        model.addAttribute("errorOccurred", false);

        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute("user") User user, Model model){

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
