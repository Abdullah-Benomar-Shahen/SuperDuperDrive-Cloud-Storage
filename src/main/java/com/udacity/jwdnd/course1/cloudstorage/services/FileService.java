package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }


    /**
     * heck if Filename available for current user.
     *
     * @param userid   the userid
     * @param filename the filename
     * @return the boolean
     */
    public Boolean isFileNameAvailableForUser(Integer userid, String filename) {
        return this.fileMapper.getFilesByFilenameAndUserID(filename, userid).isEmpty();
    }

    public List<File> getAllUserFiles(Integer userID) {
        return this.fileMapper.getFilesByUserID(userID);
    }

    public Boolean saveFile(MultipartFile file, String username) throws IOException {

        User user = this.userMapper.getUserByUsername(username);
        Integer userId = user.getUserid();

        byte[] fileData = file.getBytes();
        String contentType = file.getContentType();
        String fileSize = String.valueOf(file.getSize());
        String fileName = file.getOriginalFilename();

        this.fileMapper.insert(new File(null, fileName, contentType, fileSize, userId, fileData));

        return true;
    }

    public Boolean deleteFile(Integer fileId) {
        this.fileMapper.delete(fileId);
        return true;
    }

    public File getFileByFileId(Integer fileId) {
        return this.fileMapper.getFileById(fileId);
    }
}
