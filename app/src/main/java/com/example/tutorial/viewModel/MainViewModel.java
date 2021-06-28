package com.example.tutorial.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tutorial.model.Student;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private StudentRepository repository;
    private Disposable disposable;
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> progressStatus = new MutableLiveData<>();

    public MainViewModel(StudentRepository repository) {
        this.repository = repository;

        progressStatus.setValue(true);

        repository
                .getDataFromServer()
                .subscribeOn(Schedulers.io())
                .doFinally(() -> progressStatus.postValue(false))
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onComplete() {
                        progressStatus.postValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        error.postValue("UnKnow Error");
                        Log.i(TAG, "error in main view model: "+e.getMessage());
                    }
                });
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<List<Student>> getStudents() {
        return repository.getDataFromDatabase();
    }

    public LiveData<Boolean> getProgressStatus() {
        return progressStatus;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) disposable.dispose();
    }
}
