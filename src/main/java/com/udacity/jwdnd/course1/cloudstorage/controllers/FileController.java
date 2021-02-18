package com.udacity.jwdnd.course1.cloudstorage.controllers;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
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
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication)  {

        String usernameOfCurrentUser = (String) authentication.getPrincipal();
        Integer idOfCurrentUser = this.userService.getUserID(usernameOfCurrentUser);

        // check if file is empty
        if (fileUpload.isEmpty()) {
            logger.error("ERROR: File is empty!");
            return "redirect:/result?isSuccess=" + false + "&errorType=" + 1;
        }

        String fileName = fileUpload.getOriginalFilename();

        if(!this.fileService.isFileNameAvailableForUser(idOfCurrentUser, fileName)) {
            logger.error("ERROR: A file with same name already exists!");
            return "redirect:/result?isSuccess=" + false + "&errorType=" + 1;
        }

        try {
            this.fileService.saveFile(fileUpload, usernameOfCurrentUser);
        } catch (IOException e) {
            logger.error("ERROR: Error while saving file!");
            e.printStackTrace();
        }

        return "redirect:/result?isSuccess=" + true;
    }

    @GetMapping("/delete")
    public String deleteFile(@RequestParam(required = false, name = "fileid") Integer fileId) {
        Boolean isSuccess = this.fileService.deleteFile(fileId);

        if (isSuccess)
            logger.info("INFO: File deleted successfully!");
        else
            logger.error("ERROR: Error while deleting file");

        return "redirect:/result?isSuccess=" + isSuccess;
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
