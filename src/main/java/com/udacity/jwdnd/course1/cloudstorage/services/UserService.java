package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    /**
     * check if username available for new registrations.
     *
     * @param username the username as String
     * @return boolean
     */
    public boolean isUsernameAvailable(String username){
        return userMapper.getUserByUsername(username) == null;
    }

    /**
     * Hash the provided Password and create new User.
     *
     * @param user the user
     * @return ID of the the created user
     */
    public int createUser(User user){
        // Security
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

        // Persisting the new User in the DB
        User newUser = new User(
                null,
                user.getUsername(),
                encodedSalt,
                hashedPassword,
                user.getFirstname(),
                user.getLastname()
        );
        return userMapper.addUser(newUser);

    }

    /**
     * Retrieves user by username from the database
     *
     * @param username the username
     * @return the user
     */
    public User getUser(String username){
        return userMapper.getUserByUsername(username);
    }

    public Integer getUserID(String username){
        return userMapper.getUserIDByUsername(username);
    }
}
