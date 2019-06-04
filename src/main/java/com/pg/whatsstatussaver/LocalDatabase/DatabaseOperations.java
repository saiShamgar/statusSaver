package com.pg.whatsstatussaver.LocalDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.media.Image;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface DatabaseOperations {

    @Query("SELECT * FROM ImagesUrlTable")
    List<ImagesUrlTable> getAll();

    @Query("SELECT * FROM ImagesUrlTable WHERE" + " url= :url")
    boolean checkUrl(String url);

    @Query("SELECT * FROM ImagesUrlTable WHERE" + " video= :url")
    boolean checkUrlVideo(String url);

    @Insert(onConflict = IGNORE)
    long insert(ImagesUrlTable task);

    @Insert(onConflict = IGNORE)
    long insertVideo(ImagesUrlTable task);

    @Query("DELETE  FROM ImagesUrlTable WHERE" + " url= :task")
    int delete(String task);

    @Query("DELETE  FROM ImagesUrlTable WHERE" + " video= :task")
    int deleteVideo(String task);

}
