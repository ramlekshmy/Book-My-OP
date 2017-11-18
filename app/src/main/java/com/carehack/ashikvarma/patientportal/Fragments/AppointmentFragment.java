package com.carehack.ashikvarma.patientportal.Fragments;


import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.carehack.ashikvarma.patientportal.Activities.EnterAppointment;
import com.carehack.ashikvarma.patientportal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.carehack.ashikvarma.patientportal.Utilities.Constants.DOCTORS;


/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentFragment extends Fragment implements View.OnClickListener {
    public EditText patientname;
    public EditText patientdate;
    public EditText patientconcerns;
    public TextView doctorname;
    public Button submitbtn;
    public String PatientName;
    public String Doctorname;
    public String PatientConcerns;
    public String PatientDate;
    public Calendar myCalendar = Calendar.getInstance();
    public String doctorName;
    public FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public String fireuserph = firebaseAuth.getCurrentUser().getPhoneNumber();
    public EditText patientAge;
    public TextInputLayout inputLayoutName;
    public TextInputLayout inputLayoutage;
    public TextInputLayout inputLayoutDate;
    public TextInputLayout inputLayoutPurpose;
    public RadioGroup radioGroupGender;
    public RadioButton radiomale;
    public RadioButton radiofemale;
    View view;
    private DatePickerDialog DatePickerDialog;

    public AppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_book_now_, container, false);
        doctorName = getArguments().getString(DOCTORS);

        patientname = (EditText) view.findViewById(R.id.patientname);
        doctorname = (TextView) view.findViewById(R.id.doctorname);
        doctorname.setEnabled(false);
        doctorname.setText(doctorName);
//        if(doctorName==null )
//        {
//            doctorName=" ";
//
//        }
//        else
//        {
//            doctorname.setText(doctorName);
//        }

        patientdate = (EditText) view.findViewById(R.id.patientdate);
        patientAge = (EditText) view.findViewById(R.id.patientage);
        patientconcerns = (EditText) view.findViewById(R.id.patientconcerns);
        submitbtn = (Button) view.findViewById(R.id.submitbtn);
        inputLayoutName = (TextInputLayout) view.findViewById(R.id.input_layout_yourname);
        inputLayoutage = (TextInputLayout) view.findViewById(R.id.input_layout_yourage);
        inputLayoutDate = (TextInputLayout) view.findViewById(R.id.input_layout_date);
        inputLayoutPurpose = (TextInputLayout) view.findViewById(R.id.input_layout_purpose);
        submitbtn.setOnClickListener(this);
        radioGroupGender = (RadioGroup) view.findViewById(R.id.radiogrp);
        radiomale = (RadioButton) view.findViewById(R.id.radiomale);
        radiofemale = (RadioButton) view.findViewById(R.id.radiofemale);
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                String myFormat="yy-MM-dd";
//                SimpleDateFormat sdf=new SimpleDateFormat(myFormat, Locale.US);
                String myDate = year + "-" + (++monthOfYear) + "-" + dayOfMonth;
                patientdate.setText(myDate);

            }


        };
        patientdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)) {

                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        DatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                    }
                };

                DatePickerDialog.show();
            }
        });
        getActivity().setTitle("Make Your Appointment");


        return view;
    }
//    public void onClick(View view)
//    {
//        if(view==cv)
//        {
//            startActivity(new Intent(getActivity(),Appointment.class));
//        }

    // }

    public void onClick(View view) {
        System.out.println("best");
        inputLayoutName.setError(null);
        inputLayoutage.setError(null);
        inputLayoutDate.setError(null);
        inputLayoutDate.setError(null);

        if (view == submitbtn) {

            if (patientname.getText().toString().length() == 0)
                inputLayoutName.setError("Enter Patient Name");
            else if (patientAge.getText().toString().length() == 0)
                inputLayoutage.setError("Enter patient age");
            else if (patientdate.getText().toString().length() == 0)
                inputLayoutDate.setError("Enter Date");
            else if (patientconcerns.getText().toString().length() == 0)
                inputLayoutPurpose.setError("Enter purpose");
            else if (doctorname.getText().toString().length() == 0)
                doctorname.setError("Enter Doctor Name");
            else {

                DatabaseReference root1Ref = FirebaseDatabase.getInstance().getReference().child("Appointments");
                PatientName = patientname.getText().toString().trim();
                Doctorname = doctorname.getText().toString().trim();
                PatientDate = patientdate.getText().toString().trim();
                PatientConcerns = patientconcerns.getText().toString().trim();

                Map<String, Object> map = new HashMap<String, Object>();
                String temp_key = root1Ref.push().getKey();
                map.put(temp_key, "");
                root1Ref.updateChildren(map);

                DatabaseReference patientDetails = root1Ref.child(temp_key);
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("doctor", Doctorname);
                map1.put("phone", fireuserph);
                map1.put("name", PatientName);
                map1.put("date", PatientDate);
                map1.put("concerns", PatientConcerns);
                patientDetails.updateChildren(map1);

                new AlertDialog.Builder(getActivity())
                        .setTitle("Mark Appointment")
                        .setMessage("Do you want to mark your appointment on calendar?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Toast.makeText(getActivity(), "You clicked on YES", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), EnterAppointment.class);
                                intent.putExtra("appointment_date", PatientDate);
                                intent.putExtra("appointment_concerns", PatientConcerns);
                                intent.putExtra("doctorName", Doctorname);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(getActivity(), "You clicked on No", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        }
    }

}
