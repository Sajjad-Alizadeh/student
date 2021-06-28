package com.example.tutorial.viewModel;

import androidx.lifecycle.ViewModel;

import io.reactivex.Completable;

public class AddStudentViewModel extends ViewModel {

    private StudentRepository studentRepository;

    public AddStudentViewModel(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Completable addStudent(String fName, String lName, String course, int score) {
        return studentRepository.addStudent(fName, lName, course, score).ignoreElement();
    }
}
