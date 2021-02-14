package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NoteController {

    private Logger logger = LoggerFactory.getLogger(NoteController.class);

    private NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/note")
    public String submitNote(@ModelAttribute("note") Note note, Authentication authentication, Model model) {
        String usernameOfCurrentUser = (String) authentication.getPrincipal();
        Integer idOfCurrentUser = this.userService.getUserID(usernameOfCurrentUser);
        Boolean isSuccess = this.noteService.insertOrUpdateNoteByUserID(idOfCurrentUser, note);

        if (isSuccess)
            logger.info("INFO: Note submitted successfully!");
        else
            logger.error("ERROR: Error while submitting Note");

        return "redirect:/result?isSuccess=" + isSuccess;
    }

    @GetMapping("/note")
    public String deleteNote(@ModelAttribute("note") Note note, @RequestParam(required = false, name = "noteid") Integer noteId, Model model) {
        Boolean isSuccess = this.noteService.deleteNote(noteId);

        if (isSuccess)
            logger.info("INFO: Note deleted successfully!");
        else
            logger.error("ERROR: Error while deleting Note");

        return "redirect:/result?isSuccess=" + isSuccess;
    }
}
