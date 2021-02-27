package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.CustomErrors;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private final Logger logger = LoggerFactory.getLogger(NoteController.class);

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/note")
    public String submitNote(@ModelAttribute("note") Note note, Authentication authentication, Model model, RedirectAttributes redirectAttributes) {

        String usernameOfCurrentUser = (String) authentication.getPrincipal();
        Integer idOfCurrentUser = this.userService.getUserID(usernameOfCurrentUser);

        if(note.getNotedescription().trim().length() > 1000){
            logger.info("ERROR: Note Description exceeded the max. length of 1000 character");
            redirectAttributes.addFlashAttribute("errorAlertMessage", CustomErrors.UI_ERROR_LONG_NOTE_DESCRIPTION);
            redirectAttributes.addFlashAttribute("errorMsgActive", true);
            redirectAttributes.addFlashAttribute("successMsgActive", false);
            return "redirect:/result";
        } else if(!noteService.isNoteTitleAvailable(idOfCurrentUser, note.getNotetitle())){
            logger.info("ERROR: duplicate Note exists.");
            redirectAttributes.addFlashAttribute("errorAlertMessage", CustomErrors.UI_ERROR_DUPLICATE_NOTE);
            redirectAttributes.addFlashAttribute("errorMsgActive", true);
            redirectAttributes.addFlashAttribute("successMsgActive", false);
            return "redirect:/result";
        } else {
            Boolean isSuccess = this.noteService.insertOrUpdateNoteByUserID(idOfCurrentUser, note);
            if (isSuccess){
                logger.info("INFO: Note submitted successfully!");
                redirectAttributes.addFlashAttribute("successAlertMessage", "Note submitted successfully!");
                redirectAttributes.addFlashAttribute("errorMsgActive", false);
                redirectAttributes.addFlashAttribute("successMsgActive", true);
            }
            return "redirect:/result";
        }
    }

    @GetMapping("/note")
    public String deleteNote(@ModelAttribute("note") Note note, @RequestParam(required = false, name = "noteid") Integer noteId, Model model, RedirectAttributes redirectAttributes) {
        Boolean isSuccess = this.noteService.deleteNote(noteId);

        if (isSuccess){
            logger.info("INFO: Note submitted successfully!");
            redirectAttributes.addFlashAttribute("successAlertMessage", "Note deleted successfully!");
            redirectAttributes.addFlashAttribute("errorMsgActive", false);
            redirectAttributes.addFlashAttribute("successMsgActive", true);
        }
        else{
            logger.error("ERROR: Error while submitting Note");
            redirectAttributes.addFlashAttribute("errorAlertMessage", CustomErrors.UI_ERROR_NOTE_DELETION_FAILED);
            redirectAttributes.addFlashAttribute("errorMsgActive", true);
            redirectAttributes.addFlashAttribute("successMsgActive", false);
        }

        return "redirect:/result";
    }
}
