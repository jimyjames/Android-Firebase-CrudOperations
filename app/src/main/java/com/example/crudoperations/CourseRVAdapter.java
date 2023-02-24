package com.example.crudoperations;



import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import android.text.TextUtils;
import java.util.ArrayList;

public class CourseRVAdapter extends RecyclerView.Adapter<CourseRVAdapter.ViewHolder> {
    // creating variables for our list, context, interface and position.
    private ArrayList<RVModal> RVModalArrayList;
    private Context context;
    private CourseClickInterface courseClickInterface;
    int lastPos = -1;

    // creating a constructor.
    public CourseRVAdapter(ArrayList<RVModal> RVModalArrayList, Context context, CourseClickInterface courseClickInterface) {
        this.RVModalArrayList = RVModalArrayList;
        this.context = context;
        this.courseClickInterface = courseClickInterface;
    }

    @NonNull
    @Override
    public CourseRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.course_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // setting data to our recycler view item on below line.
        RVModal courseRVModal = RVModalArrayList.get(position);
        if (!TextUtils.isEmpty(courseRVModal.getCourseImg())) {
            Picasso.get().load(courseRVModal.getCourseImg()).into(holder.courseIV);
        }
        holder.courseTV.setText(courseRVModal.getCourseName());
        holder.coursePriceTV.setText("Ksh. " + courseRVModal.getCoursePrice());
        if (!TextUtils.isEmpty(courseRVModal.getCourseImg())) {
            Picasso.get().load(courseRVModal.getCourseImg()).into(holder.courseIV);
        }
        Picasso.get().load(courseRVModal.getCourseImg()).into(holder.courseIV);
        // adding animation to recycler view item on below line.
        setAnimation(holder.itemView, position);
        holder.courseIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseClickInterface.onCourseClick(position);
            }
        });
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            // on below line we are setting animation.
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return RVModalArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variable for our image view and text view on below line.
        private ImageView courseIV;
        private TextView courseTV, coursePriceTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing all our variables on below line.
            courseIV = itemView.findViewById(R.id.idIVCourse);
            courseTV = itemView.findViewById(R.id.idTVCOurseName);
            coursePriceTV = itemView.findViewById(R.id.idTVCoursePrice);
        }
    }

    // creating a interface for on click
    public interface CourseClickInterface {
        void onCourseClick(int position);
    }
}