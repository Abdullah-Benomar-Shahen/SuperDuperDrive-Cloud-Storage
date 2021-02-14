package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface NoteMapper {

    // SELECTS
    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getNotesByUserId(Integer userid);

    // INSERTS
    @Insert("INSERT INTO NOTES(notetitle, notedescription, userid) VALUES (#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Note note);

    // DELETE
    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    void delete(Integer noteid);

    // UPDATES
    @Update("UPDATE notes SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid}")
    int update(String notetitle, String notedescription, Integer noteid);
}
