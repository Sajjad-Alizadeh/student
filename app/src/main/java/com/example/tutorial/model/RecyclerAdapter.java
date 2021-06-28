package com.example.tutorial.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorial.R;

import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<Student> students;

    public RecyclerAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_recycler_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, int position) {
        Student student = students.get(position);
        holder.fullName.setText(student.getFirstName()+" " + student.getLastName());
        holder.courseName.setText(student.getCourse());
        holder.firstCharacter.setText(student.getFirstName().substring(0,1));
        holder.score.setText(Integer.toString(student.getScore()));

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView firstCharacter;
        private TextView fullName;
        private TextView courseName;
        private TextView score;


        public MyViewHolder(View itemView) {
            super(itemView);
            firstCharacter = itemView.findViewById(R.id.tv_first_character);
            fullName = itemView.findViewById(R.id.tv_full_name);
            courseName = itemView.findViewById(R.id.tv_course);
            score = itemView.findViewById(R.id.tv_score);

        }
    }
}
