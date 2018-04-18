package com.example.delle6330.assignment1;

/**
 * Created by Ksenia on 4/16/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.delle6330.assignment1.Mydatabase;
import com.example.delle6330.assignment1.R;

import java.util.ArrayList;

public class Custom extends BaseAdapter {

    Context context;
    ArrayList<Patient> list;
    public Custom(Context context,ArrayList<Patient> list){
        this.context=context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final LayoutInflater inflater = LayoutInflater.from(context);
        final Mydatabase db = new Mydatabase(context);
        view = inflater.inflate(R.layout.layout,null);

        TextView id, doctorChoice, name, address, bDay, phoneNumber, hcNumber, desc, q1, q2;
        id = (TextView) view.findViewById(R.id.textView);
        doctorChoice = (TextView) view.findViewById(R.id.textView2);
        name = (TextView) view.findViewById(R.id.textView3);
        address = (TextView) view.findViewById(R.id.textView4);
        bDay = (TextView) view.findViewById(R.id.textView5);
        phoneNumber = (TextView) view.findViewById(R.id.textView6);
        hcNumber = (TextView) view.findViewById(R.id.textView7);
        desc = (TextView) view.findViewById(R.id.textView8);
        q1 = (TextView) view.findViewById(R.id.textView9);
        q2 = (TextView) view.findViewById(R.id.textView10);

        final Patient patient = list.get(i);

        id.setText(patient.getId()+"");
        doctorChoice.setText(patient.getDoctorChoice());
        name.setText(patient.getName());
        address.setText(patient.getAddress());
        bDay.setText(patient.getBirthday());
        phoneNumber.setText(patient.getPhoneNumber());
        hcNumber.setText(patient.getSsNumber());
        desc.setText(patient.getVisitReason());
        q1.setText(patient.getAddQuestion1());
        q2.setText(patient.getAddQuestion2());


        Button btnXoa = (Button) view.findViewById(R.id.btnXoa);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.DeletePatient(patient.id);
                notifyDataSetChanged();
                list.remove(i);
            }
        });

        Button btnsua = (Button) view.findViewById(R.id.btnSua);
        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Update.class);
                intent.putExtra("ID",patient.id);
                intent.putExtra("doctor_choice", patient.doctorChoice);
                intent.putExtra("name",patient.name);
                intent.putExtra("address",patient.address);
                intent.putExtra("birthday", patient.birthday);
                intent.putExtra("phone_number", patient.phoneNumber);
                intent.putExtra("health_card_number", patient.ssNumber);
                intent.putExtra("description", patient.visitReason);
                intent.putExtra("addQuestion1", patient.addQuestion1);
                intent.putExtra("addQuestion2", patient.addQuestion2);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
