package com.example.coursecrashapp;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditCourseActivity extends AppCompatActivity {

    private TextInputEditText courseNameEdt, coursePriceEdt, courseSuitedForEdt, courseImgEdt, courseLinkEdt, courseDescEdt;
    private Button updateCourseBtn, deleteCourseBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String courseID;
    private CourseRVModal courseRVModal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        courseNameEdt = (TextInputEditText) findViewById(R.id.idETCourseName);
        coursePriceEdt= (TextInputEditText) findViewById(R.id.idETCoursePrice);
        courseSuitedForEdt = (TextInputEditText) findViewById(R.id.idETCourseSuitedFor);
        courseImgEdt = (TextInputEditText) findViewById(R.id.idETCourseImageLink);
        courseLinkEdt = (TextInputEditText) findViewById(R.id.idETCourseLink);
        courseDescEdt = (TextInputEditText) findViewById(R.id.idETCourseDescription);
        updateCourseBtn = (Button) findViewById(R.id.idBtnUpdateCourse);
        deleteCourseBtn = (Button) findViewById(R.id.idBtnDeleteCourse);
        loadingPB = (ProgressBar) findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();

        courseRVModal = getIntent().getParcelableExtra("course");
        if(courseRVModal != null){
            courseNameEdt.setText(courseRVModal.getCourseName());
            coursePriceEdt.setText(courseRVModal.getCoursePrice());
            courseSuitedForEdt.setText(courseRVModal.getCourseSuitedFor());
            courseImgEdt.setText(courseRVModal.getCourseImg());
            courseLinkEdt.setText(courseRVModal.getCourseLink());
            courseDescEdt.setText(courseRVModal.getCourseDescription());
            courseID = courseRVModal.getCourseID();
        }
        databaseReference = firebaseDatabase.getReference("Courses").child(courseID);
        updateCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String courseName = courseNameEdt.getText().toString();
                String coursePrice = coursePriceEdt.getText().toString();
                String courseSuitedFor = courseSuitedForEdt.getText().toString();
                String courseImg = courseImgEdt.getText().toString();
                String courseLink = courseLinkEdt.getText().toString();
                String courseDesc = courseDescEdt.getText().toString();

                Map<String,Object> map = new HashMap<>();
            map.put("courseName", courseName);
            map.put("courseDescription",courseDesc);
            map.put("coursePrice", coursePrice);
            map.put("courseSuitedFor", courseSuitedFor);
            map.put("courseImg", courseImg);
            map.put("courseLink",courseLink);
            map.put("courseID", courseID);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                   loadingPB.setVisibility(View.GONE);
                   databaseReference.updateChildren(map);
                    Toast.makeText(EditCourseActivity.this, "Course Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditCourseActivity.this,MainActivity.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(EditCourseActivity.this, "Fail to update course...", Toast.LENGTH_SHORT).show();
                }
            });

            }
        });

        deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCourse();
            }
        });
    }
    private void deleteCourse(){
        databaseReference.removeValue();
        Toast.makeText(this, "Course Deleted Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditCourseActivity.this, MainActivity.class));

    }
}