package com.rohan.test;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends WithMenuActivity {

    private EditText s_id, name, mob, fees;
    private Spinner spinner;
    private Button b_submit;

    DatabaseReference student_db;

    private List<Student> studentList;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    //Exit When Back and Set no History

    //for differnt font
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        student_db = FirebaseDatabase.getInstance().getReference("student_table");

        s_id = findViewById(R.id.e_s_id);
        name = findViewById(R.id.e_name);
        mob = findViewById(R.id.e_mobile_no);
        fees = findViewById(R.id.e_fees);
        b_submit = findViewById(R.id.btn_submit);
        spinner = findViewById(R.id.pickup_points);


        b_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = s_id.getText().toString().trim();
                String nm = name.getText().toString().trim();
                String mo_no = mob.getText().toString().trim();
                int fees_pending = 0;
                try {
                    if (fees.getText().toString() != null)
                        fees_pending = Integer.parseInt(fees.getText().toString());
                } catch (NumberFormatException e) {
                    fees_pending = 0;
                }

                String p_point = spinner.getSelectedItem().toString();

                if (id.isEmpty() || nm.isEmpty() || mo_no.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter all the input fields correctly. ", Toast.LENGTH_SHORT).show();
                } else {
                    add_data(id, nm, mo_no, fees_pending, p_point);
                }

            }
        });


        //
        studentList = new ArrayList<>();
        student_db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                   /* Toast.makeText(getApplicationContext(),"getting the data...",Toast.LENGTH_SHORT).show();*/
                studentList.clear();
                for (DataSnapshot student_snapshot : dataSnapshot.getChildren()) {
                    Student student = student_snapshot.getValue(Student.class);
                    studentList.add(student);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void add_data(String s_id_ip, String name_ip, String mobile_no_ip, int fees_ip, String pickup_point_ip) {


        for (Student s : studentList) {
            if (s.getS_id().equals(s_id_ip)) {
                Toast.makeText(getApplicationContext(), "Student Already Exits...!!!", Toast.LENGTH_SHORT).show();
                return;
            }
        }


        String id = student_db.push().getKey();
        Student s1 = new Student(id, s_id_ip, name_ip, mobile_no_ip, fees_ip, pickup_point_ip);
        student_db.child(id).setValue(s1);
        Log.i("MainActivity", "id: " + s_id_ip + " mob. no.: " + mobile_no_ip + " fees: " + fees_ip + " pickup_point: " + pickup_point_ip + ".");

        Toast.makeText(getApplicationContext(), "Student added sucessfully.", Toast.LENGTH_SHORT).show();
        //make all edittext clear..(restart the activity)//
        s_id.setText("");
        name.setText("");
        mob.setText("");
        fees.setText("");
        spinner.setSelection(0);

    }
}






