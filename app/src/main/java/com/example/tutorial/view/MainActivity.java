package com.example.tutorial.view;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tutorial.ApiInterfaceProvider;
import com.example.tutorial.R;
import com.example.tutorial.model.RecyclerAdapter;

import com.example.tutorial.model.Student;
import com.example.tutorial.model.database.AppDatabase;
import com.example.tutorial.viewModel.MainViewModel;
import com.example.tutorial.viewModel.MyViewModelFactory;
import com.example.tutorial.viewModel.StudentRepository;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private ExtendedFloatingActionButton fab;
    private ProgressBar progressBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress);

        setupActionBar();

        MainViewModel mainViewModel = new ViewModelProvider(this, new MyViewModelFactory(new StudentRepository(ApiInterfaceProvider.getInstance(),
                AppDatabase.getAppDatabase(getApplicationContext()).getDao())))
                .get(MainViewModel.class);

        mainViewModel.getProgressStatus().observe(this, aBoolean-> progressBar.setVisibility(aBoolean?View.VISIBLE:View.GONE));

        mainViewModel.getStudents().observe(this, students-> setupRecycler(students));

        mainViewModel.getError().observe(this,error-> Toast.makeText(this, error, Toast.LENGTH_SHORT).show());

        fab = (ExtendedFloatingActionButton) findViewById(R.id.fab_add_student);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddStudentActivity.class));
            }
        });
    }

    private void setupActionBar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    private void setupRecycler(List<Student> students) {
        recyclerView = findViewById(R.id.my_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerAdapter(MainActivity.this, students));
    }


}