package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    // SELECTS
    @Select("SELECT * FROM FILES WHERE fileid = #{fileid}")
    File getFileById(Integer fileid);

    @Select("SELECT * FROM FILES")
    List<File> getAllFiles();

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getFilesByUserID(Integer userid);

    @Select("SELECT * FROM FILES WHERE filename = #{filename} AND userid = #{userid}")
    List<File> getFilesByFilenameAndUserID(String filename, Integer userid);

    // INSERTS
    @Insert("INSERT INTO FILES(filename, contenttype, filesize, filedata, userid) VALUES (#{filename}, #{contenttype}, #{filesize}, #{filedata}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    int insert(File file);

    // DELETE
    @Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
    void delete(Integer fileid);
}
