package com.example.tutorial.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tutorial.ApiInterfaceProvider;
import com.example.tutorial.R;
import com.example.tutorial.model.database.AppDatabase;
import com.example.tutorial.viewModel.AddStudentViewModel;
import com.example.tutorial.viewModel.MyViewModelFactory;
import com.example.tutorial.viewModel.StudentRepository;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddStudentActivity extends AppCompatActivity {

    private EditText firstEdt;
    private EditText lastEdt;
    private EditText courseEdt;
    private EditText scoreEdt;
    private ExtendedFloatingActionButton saveFab;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        setupViews();

        AddStudentViewModel addStudentViewModel = new ViewModelProvider(this, new MyViewModelFactory(new StudentRepository(ApiInterfaceProvider.getInstance(), AppDatabase.getAppDatabase(getApplicationContext()).getDao()))).get(AddStudentViewModel.class);

        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudentViewModel.addStudent(firstEdt.getText().toString(),lastEdt.getText().toString(),courseEdt.getText().toString(),Integer.parseInt(scoreEdt.getText().toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe( Disposable d) {
                        disposable =d;
                    }

                    @Override
                    public void onComplete() {
                        finish();
                    }

                    @Override
                    public void onError( Throwable e) {

                    }
                });
            }
        });


    }

    private void setupViews() {
        saveFab = findViewById(R.id.fab_save);
        firstEdt = findViewById(R.id.edt_firstname);
        lastEdt = findViewById(R.id.edt_lasttname);
        courseEdt = findViewById(R.id.edt_course);
        scoreEdt = findViewById(R.id.edt_score);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}