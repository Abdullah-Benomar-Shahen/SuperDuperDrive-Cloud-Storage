package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.CustomErrors;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@ControllerAdvice
@RequestMapping("/files")
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, RedirectAttributes redirectAttributes)  {

        String usernameOfCurrentUser = (String) authentication.getPrincipal();
        Integer idOfCurrentUser = this.userService.getUserID(usernameOfCurrentUser);

        // check if file is empty
        if (fileUpload.isEmpty()) {
            logger.error("ERROR: File is empty!");
            redirectAttributes.addFlashAttribute("errorAlertMessage", CustomErrors.UI_ERROR_FILE_IS_EMPTY);
            redirectAttributes.addFlashAttribute("errorMsgActive", true);
            redirectAttributes.addFlashAttribute("successMsgActive", false);
            return "redirect:/result";
        }  else if(!this.fileService.isFileNameAvailableForUser(idOfCurrentUser, fileUpload.getOriginalFilename())) {
            logger.error("ERROR: A file with same name already exists!");
            redirectAttributes.addFlashAttribute("errorAlertMessage", CustomErrors.UI_ERROR_FILE_ALREADY_EXISTS);
            redirectAttributes.addFlashAttribute("errorMsgActive", true);
            redirectAttributes.addFlashAttribute("successMsgActive", false);
            return "redirect:/result";
        } else {
            try {
                this.fileService.saveFile(fileUpload, usernameOfCurrentUser);
                redirectAttributes.addFlashAttribute("successAlertMessage", "File was saved successfully.");
                redirectAttributes.addFlashAttribute("errorMsgActive", false);
                redirectAttributes.addFlashAttribute("successMsgActive", true);
            } catch (IOException e) {
                logger.error("ERROR: Error while saving file!");
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("errorAlertMessage", CustomErrors.UI_ERROR_GENERAL);
                redirectAttributes.addFlashAttribute("errorMsgActive", true);
                redirectAttributes.addFlashAttribute("successMsgActive", false);
            }
            return "redirect:/result";
        }
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleFileSizeExceededError(RedirectAttributes redirectAttributes){
        logger.error("ERROR: File size exceeded the limit of 5MB");
        redirectAttributes.addFlashAttribute("errorAlertMessage", CustomErrors.UI_ERROR_FILE_IS_LARGE);
        redirectAttributes.addFlashAttribute("errorMsgActive", true);
        redirectAttributes.addFlashAttribute("successMsgActive", false);
        return "redirect:/result";
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam(required = false, name = "fileid") Integer fileId, RedirectAttributes redirectAttributes) {
        Boolean isSuccess = this.fileService.deleteFile(fileId);

        if (isSuccess){
            logger.info("INFO: File deleted successfully!");
            redirectAttributes.addFlashAttribute("successAlertMessage", "File deleted successfully!");
            redirectAttributes.addFlashAttribute("errorMsgActive", false);
            redirectAttributes.addFlashAttribute("successMsgActive", true);
        }
        else {
            logger.error("ERROR: Error while deleting file");
            redirectAttributes.addFlashAttribute("errorAlertMessage", CustomErrors.UI_ERROR_FILE_DELETION_FAILED);
            redirectAttributes.addFlashAttribute("errorMsgActive", true);
            redirectAttributes.addFlashAttribute("successMsgActive", false);
        }

        return "redirect:/result";
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam(required = false, name = "fileid") Integer fileId) {

        File file = this.fileService.getFileByFileId(fileId);

        String fileName = file.getFilename();
        String contentType = file.getContenttype();
        byte[] fileData = file.getFiledata();

        InputStream inputStream = new ByteArrayInputStream(fileData);
        InputStreamResource resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }
}
