package com.carehack.ashikvarma.patientportal.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carehack.ashikvarma.patientportal.Adapters.DoctorAdapter;
import com.carehack.ashikvarma.patientportal.Models.Doctor;
import com.carehack.ashikvarma.patientportal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.carehack.ashikvarma.patientportal.Utilities.Constants.DEPARTMENTS;
import static com.carehack.ashikvarma.patientportal.Utilities.Constants.DOCTORS;

public class DoctorFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ArrayList<Doctor> mDoctors = new ArrayList<>();


    public DoctorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
        ButterKnife.bind(this, view);
        final String department = getArguments().getString(DEPARTMENTS);

        setupRecyclerView(department);
        getActivity().setTitle("Make Your Appointment");
        return view;
    }

    private void setupRecyclerView(final String department) {

        DatabaseReference refOffers = FirebaseDatabase.getInstance().getReference().child(DOCTORS);
        refOffers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot name : dataSnapshot.getChildren()) {
                    if (name.getValue() != null) {
                        Doctor doctor = name.getValue(Doctor.class);
                        if(doctor!=null && doctor.getDepartment().equals(department)){
                            mDoctors.add(doctor);
                        }
                    }
                }
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                recyclerView.setAdapter(new DoctorAdapter(getActivity(), mDoctors));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        refOffers.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                for (DataSnapshot name : dataSnapshot.getChildren()) {
//                    if (name.getValue() != null) {
//                        Doctor doctor = name.getValue(Doctor.class);
//                        if(doctor!=null && doctor.getDepartment().equals(department)){
//                            mDoctors.add(doctor);
//                        }
//                    }
//                }
//                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//                recyclerView.setAdapter(new DoctorAdapter(getActivity(), mDoctors)); // set the Adapter to RecyclerView
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
    }
}