package com.example.coursecrashapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
            }
        });
    }
}