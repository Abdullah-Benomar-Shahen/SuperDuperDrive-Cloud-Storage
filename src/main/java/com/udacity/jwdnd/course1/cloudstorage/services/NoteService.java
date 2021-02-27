package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    /**
     * Check if Note Title available for current user.
     *
     * @param userid   the userid
     * @param noteTitle Note Title
     * @return the boolean
     */
    public Boolean isNoteTitleAvailable(Integer userid, String noteTitle) {
        return this.noteMapper.getNotesByNoteTitleAndUserID(noteTitle, userid).isEmpty();
    }

    /**
     * Gets all notes from Database by username.
     *
     * @param userID the user id
     * @return the all user notes
     */
    public List<Note> getAllUserNotes(Integer userID) {
        return this.noteMapper.getNotesByUserId(userID);
    }

    /**
     * Insert or update note.
     *
     * @param userID the user id
     * @param note   the note
     * @return the boolean
     */
    public Boolean insertOrUpdateNoteByUserID(Integer userID, Note note) {

        Integer noteId = note.getNoteid();

        if (noteId == null || noteId.toString().equals("")) {
            note.setUserid(userID);
            this.noteMapper.insert(note);
        } else {
            this.noteMapper.update(note.getNotetitle(), note.getNotedescription(), note.getNoteid());
        }

        return true;
    }

    /**
     * Delete note from Database.
     *
     * @param noteId   the note id
     * @return the boolean
     */
    public Boolean deleteNote(Integer noteId) {
        this.noteMapper.delete(noteId);
        return true;
    }
}
