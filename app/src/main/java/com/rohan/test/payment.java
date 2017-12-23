package com.rohan.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class payment extends WithMenuActivity {

    private EditText e_t_sid, e_t_name, e_t_amount;
    private TextView t_v_prev_paid_fees, t_v_current_paid_fees, t_v_total_paid_fees, t_v_pending_fees;
    private Button btn_ok;
    Student s;
    String sss;

    DatabaseReference databaseReference_student;


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
        setContentView(R.layout.activity_payment);

        e_t_sid = findViewById(R.id.s_id);
        e_t_name = findViewById(R.id.s_name);
        e_t_amount = findViewById(R.id.amount);


        btn_ok = findViewById(R.id.ok);

        t_v_prev_paid_fees = findViewById(R.id.prev_paid_fees);
        t_v_current_paid_fees = findViewById(R.id.current_paid_fees);
        t_v_total_paid_fees = findViewById(R.id.total_paid_fees);
        t_v_pending_fees = findViewById(R.id.pending_fees);

        Intent intent = getIntent();

       /* final String id = intent.getStringExtra("ID");
        final int prev_paid_fees = intent.getIntExtra("prev_fees", 0);*/
        Bundle bundle = intent.getBundleExtra("value");
        s = (Student) bundle.getSerializable("Student_obj");
        e_t_sid.setText(s.getS_id());
        e_t_name.setText(s.getName());
        e_t_sid.setEnabled(false);      //id number is already fetched .. no need to edit after this throughout whole
        e_t_name.setEnabled(false);


        sss = Integer.toString(s.getPaid_fees());

        t_v_prev_paid_fees.setText(sss);
        t_v_pending_fees.setText(Integer.toString((s.getFees() - s.getPaid_fees())));
        databaseReference_student = FirebaseDatabase.getInstance().getReference("student_table");
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paid_fees(s.getEntry_id(), s.getPaid_fees());
            }
        });
    }

    private void paid_fees(String id, int prev_paid_fees) {
        //to hide keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(btn_ok.getWindowToken(), 0);
        //

        Editable amount = e_t_amount.getText();
        if (TextUtils.isEmpty(amount)) {
            Toast.makeText(getApplicationContext(), "Please provide the amount first..!", Toast.LENGTH_SHORT).show();
            return;
        } else {
           /* String id = databaseReference_student.push().getKey();
            Toast.makeText(getApplicationContext(), "Key: " + id, Toast.LENGTH_SHORT).show();
           databaseReference_student.child("student_table").child(id);*/

            //get user name currently loggesd in ..!!$
            String user = FirebaseAuth.getInstance().getCurrentUser().getEmail();

            /*String uid = user.getUid();*/
            s.setFees_taken_by(user);
            databaseReference_student.child(id).child("fees_taken_by").setValue(user);


           /* Toast.makeText(getApplicationContext(), "Key: " + id, Toast.LENGTH_SHORT).show();*/
            int amount_f = Integer.parseInt(amount.toString());
            if (amount_f <= (s.getFees() - s.getPaid_fees())) {
                t_v_current_paid_fees.setText(Integer.toString(amount_f));

                int now_total = prev_paid_fees + amount_f;

                databaseReference_student.child(id).child("paid_fees").setValue(now_total);

                t_v_total_paid_fees.setText(Integer.toString(now_total));

                s.setPaid_fees(now_total);

                int pending_fees = s.getFees() - now_total;
                t_v_pending_fees.setText(Integer.toString(pending_fees));
            } else {
                Toast.makeText(getApplicationContext(), "OH, PLease Enter some less amount...!!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
