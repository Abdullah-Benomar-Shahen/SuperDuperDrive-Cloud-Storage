package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    // SELECTS
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    // INSERTS
    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES (#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int addUser(User user);

    // DELETE
    @Delete("DELETE FROM USERS WHERE id = #{id}")
    void deleteUser(int id);
}
