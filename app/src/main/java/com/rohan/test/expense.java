package com.rohan.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class expense extends WithMenuActivity {

    private EditText e_t_email, e_t_amount, e_t_desc;
    private Button b_pay;
    private ListView expense_list_view;
    private List<User> expenseList;
    private String user_logged_in;

    DatabaseReference databaseReference_expance;

    //for differnt font
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    //
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Display.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expence);

        e_t_email = findViewById(R.id.email);
        e_t_amount = findViewById(R.id.amount_of_expence);
        e_t_desc = findViewById(R.id.desc);
        b_pay = findViewById(R.id.pay);

        expense_list_view = findViewById(R.id.expence_details);
        expenseList = new ArrayList<>();

        databaseReference_expance = FirebaseDatabase.getInstance().getReference("expanse_table");

        user_logged_in = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        e_t_email.setText(user_logged_in);
        e_t_email.setEnabled(false);

        b_pay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(e_t_amount.getText().toString()))
                    add_expense();
                else
                    Toast.makeText(getApplicationContext(), "Please, Enter the amount first...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void add_expense() {
        //to hide keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(b_pay.getWindowToken(), 0);
        //
        String u_id = databaseReference_expance.push().getKey();

        String amount_paid = e_t_amount.getText().toString();
        String desc = e_t_desc.getText().toString().trim();
        User user = new User(user_logged_in, Integer.parseInt(amount_paid), desc);
        databaseReference_expance.child(u_id).setValue(user);
        Toast.makeText(getApplicationContext(), "Details added", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference_expance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                expenseList.clear();
                for (DataSnapshot expanseSnapshot : dataSnapshot.getChildren()) {
                    User user = expanseSnapshot.getValue(User.class);
                    expenseList.add(user);
                }
                expense_struct adapter = new expense_struct(expense.this, expenseList);
                expense_list_view.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
