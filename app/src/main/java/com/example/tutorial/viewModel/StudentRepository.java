package com.example.tutorial.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.tutorial.model.Student;
import com.example.tutorial.model.database.StudentDao;
import com.example.tutorial.model.network.ApiInterface;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class StudentRepository {
    private static final String TAG = "StudentRepository";
    private ApiInterface apiInterface;
    private StudentDao studentDao;

    public StudentRepository(ApiInterface apiInterface, StudentDao studentDao) {
        this.apiInterface = apiInterface;
        this.studentDao = studentDao;
    }

    public Completable getDataFromServer() {
        return apiInterface.getStudents().doOnSuccess(students -> studentDao.saveStudents(students))
                .doOnError(throwable -> {
                    Log.i(TAG, "error in repository: " + throwable.getMessage());
                })
                .ignoreElement();
    }

    public LiveData<List<Student>> getDataFromDatabase() {
        return studentDao.getStudents();
    }

    public Single<Student> addStudent(String fName, String lName, String course, int score){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("first_name",fName);
        jsonObject.addProperty("last_name",lName);
        jsonObject.addProperty("course",course);
        jsonObject.addProperty("score",score);
        return apiInterface.addStudent(jsonObject).doOnSuccess(student->studentDao.addStudent(student));
    }

}
