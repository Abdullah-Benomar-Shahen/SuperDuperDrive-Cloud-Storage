package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private Logger logger = LoggerFactory.getLogger(CredentialController.class);

    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("/credential")
    public String submitCredential(@ModelAttribute("credential") Credential credential, Authentication authentication, Model model) {
        String usernameOfCurrentUser = (String) authentication.getPrincipal();
        Integer idOfCurrentUser = this.userService.getUserID(usernameOfCurrentUser);
        Boolean isSuccess = this.credentialService.insertOrUpdateCredential(credential, idOfCurrentUser);

        if (isSuccess)
            logger.info("INFO: Credential submitted successfully!");
        else
            logger.error("ERROR: Error while submitting Credential");

        return "redirect:/result?isSuccess=" + isSuccess;
    }

    @GetMapping("/credential")
    public String deletionCredential(@ModelAttribute("credential") Credential credential, @RequestParam(required = false, name = "credentialid") Integer credentialId, Authentication authentication, Model model) {
        Boolean isSuccess = this.credentialService.deleteCredential(credentialId);

        if (isSuccess)
        logger.info("INFO: Credential deleted successfully!");
        else
        logger.error("ERROR: Error while deleting Credential");

        return "redirect:/result?isSuccess=" + isSuccess;
    }
}
