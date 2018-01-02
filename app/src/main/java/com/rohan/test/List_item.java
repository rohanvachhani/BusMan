package com.rohan.test;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Rohan on 12/17/2017.
 */

public class List_item extends ArrayAdapter<Student> {
    private Activity context;
    private List<Student> studentList;
    private ArrayList<Student> arraylist;
   /* private Button b_up;*/

    public List_item(Activity context, List<Student> studentList) {
        super(context, R.layout.list_item_structure, studentList);
        this.context = context;
        this.studentList = studentList;
        this.arraylist = new ArrayList<Student>();
        this.arraylist.addAll(studentList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_item_structure, null, true);

        TextView t_v_name = listViewItem.findViewById(R.id.s_name);
        TextView t_v_id = listViewItem.findViewById(R.id.s_id);
        TextView t_v_mob = listViewItem.findViewById(R.id.s_mob);
        TextView t_v_p_point = listViewItem.findViewById(R.id.s_p_point);
        TextView t_v_fees = listViewItem.findViewById(R.id.s_fees);
        TextView t_v_fees_taken_by = listViewItem.findViewById(R.id.s_fees_taken_by);
        TextView t_v_paid_fees = listViewItem.findViewById(R.id.s_paid_fees);


        final Student student = studentList.get(position);
        t_v_name.setText("Name: " + student.getName());
        t_v_id.setText("Id No.: " + student.getS_id());
        t_v_mob.setText("Mobile No.: " + student.getMobile_no());
        t_v_p_point.setText("Pickup Point: " + student.getPickup_point());
        t_v_fees.setText("Fees: " + Integer.toString(student.getFees()));
        t_v_paid_fees.setText("Paid Fees: " + Integer.toString(student.getPaid_fees()));
        t_v_fees_taken_by.setText("Fees Taken By: " + student.getFees_taken_by());

        return listViewItem;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        studentList.clear();
        if (charText.length() == 0) {
            studentList.addAll(arraylist);
        } else {
            for (Student wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText) || wp.getPickup_point().toLowerCase(Locale.getDefault()).contains(charText) || wp.getS_id().contains(charText)) {
                    studentList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filter_button() {
        studentList.clear();
        for (Student sp : arraylist) {
            if ((sp.getFees() - sp.getPaid_fees()) != 0) {
                studentList.add(sp);
            }
        }
        notifyDataSetChanged();
    }


}

