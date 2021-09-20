package com.sti.research.personalsafetyalert.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.sti.research.personalsafetyalert.model.Message;

import java.util.List;

@Dao
public interface MessageDao {

    @Insert
    void insertMessage(Message message);

    @Insert
    void insertMessages(List<Message> messages);

    @Query("UPDATE Messages SET timestamp = :timestamp WHERE id = :id")
    void updateMessage(int id, String timestamp);

    @Query("DELETE FROM Messages")
    void deleteMessages();

    @Query("SELECT * FROM Messages ORDER BY id DESC")
    LiveData<List<Message>> selectMessages();

}
