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
import java.util.Date;
import java.util.List;

/**
 * Created by Rohan on 12/23/2017.
 */

public class expense_struct extends ArrayAdapter<User> {
    private Activity context;
    private List<User> userList;
    private ArrayList<User> arraylist;
    private Date date;
   /* private Button b_up;*/

    public expense_struct(Activity context, List<User> userList) {
        super(context, R.layout.expence_item, userList);
        this.context = context;
        this.userList = userList;
        this.arraylist = new ArrayList<User>();
        this.arraylist.addAll(userList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.expence_item, null, true);

        TextView t_v_email = listViewItem.findViewById(R.id.u_email);
        TextView t_v_amount = listViewItem.findViewById(R.id.amount_paid);
        TextView t_v_desc = listViewItem.findViewById(R.id.desc);
        TextView t_v_date = listViewItem.findViewById(R.id.date);

        final User user = userList.get(position);
        t_v_email.setText(user.getUser_email());
        t_v_amount.setText(Integer.toString(user.getPayment()));
        t_v_desc.setText(user.getDesc());
        t_v_date.setText(user.getDate());


        return listViewItem;
    }

}
