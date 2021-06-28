package com.example.tutorial.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private StudentRepository repository;

    public MyViewModelFactory(StudentRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class))
        return (T) new MainViewModel(repository);
        else if (modelClass.isAssignableFrom(AddStudentViewModel.class))
            return (T) new AddStudentViewModel(repository);
        else
            throw new IllegalArgumentException("Class in MyViewModelFactory not found");
    }
}
