package com.example.tutorial.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tutorial.model.Student;

import java.util.List;

@Dao
public interface StudentDao {

    @Query("SELECT * FROM Student ORDER BY id DESC")
    LiveData<List<Student>> getStudents();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveStudents(List<Student> students);

    @Insert
    void addStudent(Student student);

}
