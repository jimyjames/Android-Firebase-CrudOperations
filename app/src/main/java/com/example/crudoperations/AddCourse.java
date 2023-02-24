package com.example.crudoperations;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AddCourse extends AppCompatActivity {

    // creating variables for our button, edit text,
    // firebase database, database reference, progress bar.
    private Button addCourseBtn;
    private Button upload;
    private TextInputEditText courseNameEdt, courseDescEdt, coursePriceEdt, bestSuitedEdt, courseImgEdt, courseLinkEdt;
    FirebaseDatabase firebaseDatabase;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;

    private ImageView imgCourse;
    private StorageReference mStorageRef;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    private String courseID;
    private FirebaseFirestore firebaseFirestore;
    private ImageView courseImageView;

    private static final int REQUEST_CODE = 1;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        // initializing all our variables.
        addCourseBtn = findViewById(R.id.idBtnAddCourse);
//        upload = findViewById(R.id.Upload_img);
        courseNameEdt = findViewById(R.id.idEdtCourseName);
        courseDescEdt = findViewById(R.id.idEdtCourseDescription);
        coursePriceEdt = findViewById(R.id.idEdtCoursePrice);
        bestSuitedEdt = findViewById(R.id.idEdtSuitedFor);
        courseImgEdt = findViewById(R.id.idEdtCourseImageLink);
        courseLinkEdt = findViewById(R.id.idEdtCourseLink);
        loadingPB = findViewById(R.id.idPBLoading);
//        courseImageView = findViewById(R.id.idImgCourse);
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
//        upload = findViewById(R.id.Upload_img);
//        imgCourse = findViewById(R.id.idImgCourse);
        addCourseBtn = findViewById(R.id.idBtnAddCourse);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");


        // on below line creating our database reference.
        databaseReference = firebaseDatabase.getReference("Courses");
//        upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                openFileChooser();
//            }
//        });
        // adding click listener for our add course button.
        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
//                uploadFile();
                // getting data from our edit text.
                String courseName = courseNameEdt.getText().toString();
                String courseDesc = courseDescEdt.getText().toString();
                String coursePrice = coursePriceEdt.getText().toString();
                String bestSuited = bestSuitedEdt.getText().toString();
                String courseImg = courseImgEdt.getText().toString();
                String courseLink = courseLinkEdt.getText().toString();
                courseID = courseName;
                // on below line we are passing all data to our modal class.
                RVModal RVModal = new RVModal(courseID, courseName, courseDesc, coursePrice, bestSuited, courseImg, courseLink);
                // on below line we are calling a add value event
                // to pass data to firebase database.
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // on below line we are setting data in our firebase database.
                        databaseReference.child(courseID).setValue(RVModal);
                        // displaying a toast message.
                        Toast.makeText(AddCourse.this, "Course Added..", Toast.LENGTH_SHORT).show();
                        // starting a main activity.
                        startActivity(new Intent(AddCourse.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on below line.
                        Toast.makeText(AddCourse.this, "Fail to add Course..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
//        private void openFileChooser(){
//            Intent intent =new Intent();
//            intent.setType("image/*");
//            intent.setAction(intent.ACTION_GET_CONTENT);
//            startActivityForResult(intent,PICK_IMAGE_REQUEST);
//
//
//        }
//        protected void onActivity(int requestCode,int resultCode, Intent data) {
//            AddCourse.super.onActivityResult(requestCode, resultCode, data);
//            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//                mImageUri = data.getData();
//                Picasso.get().load(mImageUri).into(imgCourse);
//                }
//         }
//         private void uploadFile() {
//            if (mImageUri !=null){
//                StorageReference fileReference =mStorageRef.child(System.currentTimeMillis()
//                        + "." + getFileExtension(mImageUri));
//                fileReference.putFile(mImageUri) .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Toast.makeText(AddCourse.this, "Upload Successful", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(AddCourse.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }else {
//                Toast.makeText(AddCourse.this, "No File Selected", Toast.LENGTH_SHORT).show();
//            }
//         }
//         private String getFileExtension(Uri uri){
//             ContentResolver contentResolver=getContentResolver();
//             MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//             return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
//         }


    }
