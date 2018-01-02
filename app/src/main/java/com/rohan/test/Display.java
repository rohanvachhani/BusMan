package com.rohan.test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Display extends WithMenuActivity implements Serializable {

    private ListView l_v_student;
    private DatabaseReference databaseStudent;
    private List<Student> studentList;
    private ProgressBar progressBar;
    public EditText search;
    private List_item adapter;
    private Button btn_update, btn_delete;
    private ImageButton b_search;


    //for differnt font
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    //

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        b_search = findViewById(R.id.srch_p_point);

        l_v_student = findViewById(R.id.student_list);

        databaseStudent = FirebaseDatabase.getInstance().getReference("student_table");

        studentList = new ArrayList<>();

        search = findViewById(R.id.search);

        progressBar = findViewById(R.id.progressBar);

        /*adapter = new List_item(Display.this, studentList);*/

        b_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.filter_button();
            }
        });


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              /*  Display.this.adapter.getFilter().filter(s.toString());
                adapter.notifyDataSetChanged();*/
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = search.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);

            }
        });

        l_v_student.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student s = studentList.get(position);
                Intent intent = new Intent(getApplicationContext(), payment.class);
                /*intent.putExtra("Student_obj",s);*/
              /*  intent.putExtra("ID", s.getEntry_id());
                intent.putExtra("prev_fees",s.getPaid_fees());*/
                Bundle b = new Bundle();
                b.putSerializable("Student_obj", (Serializable) s);
                intent.putExtra("value", b);
                startActivity(intent);
            }
        });

        l_v_student.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Student s = studentList.get(position);

                show_update_dialog(s);
                return true;
            }
        });


    }

    private void show_update_dialog(final Student s) {
        AlertDialog.Builder dialog_builder = new AlertDialog.Builder(this);

        LayoutInflater layoutInflater = getLayoutInflater();
        View DialogView = layoutInflater.inflate(R.layout.dialog_structure, null);

        final EditText id = DialogView.findViewById(R.id.e_s_id);
        final EditText name = DialogView.findViewById(R.id.e_name);
        final EditText mob = DialogView.findViewById(R.id.e_mobile_no);
        final Spinner p_point = DialogView.findViewById(R.id.pickup_points);
        final EditText fees = DialogView.findViewById(R.id.e_fees);

        //set current values to all files at initial of dialog ... update after this .. this can be editable ..then click update button
        id.setText(s.getS_id());
        name.setText(s.getName());
        mob.setText(s.getMobile_no());
        p_point.setSelection(getIndex(p_point, s.getPickup_point()));
        fees.setText(Integer.toString(s.getFees()));


        dialog_builder.setView(DialogView);
        dialog_builder.setTitle("update");

        final AlertDialog alertDialog = dialog_builder.create();
        alertDialog.show();

        btn_update = DialogView.findViewById(R.id.b_update_up);
        btn_delete = DialogView.findViewById(R.id.b_delete_record);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s.setS_id(id.getText().toString());
                s.setName(name.getText().toString());
                s.setMobile_no(mob.getText().toString());
                s.setPickup_point(p_point.getSelectedItem().toString());
                s.setFees(Integer.valueOf(fees.getText().toString()));
                Toast.makeText(getApplicationContext(), " Updated", Toast.LENGTH_SHORT).show();

                databaseStudent.child(s.getEntry_id()).setValue(s);
                alertDialog.hide();

            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseStudent.child(s.getEntry_id()).removeValue();
                alertDialog.hide();
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
            databaseStudent.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                   /* Toast.makeText(getApplicationContext(),"getting the data...",Toast.LENGTH_SHORT).show();*/
                    studentList.clear();
                    for (DataSnapshot student_snapshot : dataSnapshot.getChildren()) {
                        Student student = student_snapshot.getValue(Student.class);
                        studentList.add(student);
                    }


                adapter = new List_item(Display.this, studentList);
                progressBar.setVisibility(View.GONE);
                l_v_student.setTextFilterEnabled(true);
                l_v_student.setAdapter(adapter);
                l_v_student.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private int getIndex(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }


}
