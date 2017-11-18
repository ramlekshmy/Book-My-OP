package com.carehack.ashikvarma.patientportal.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carehack.ashikvarma.patientportal.Adapters.DepartmentsAdapter;
import com.carehack.ashikvarma.patientportal.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.carehack.ashikvarma.patientportal.Utilities.Constants.DEPARTMENTS;

public class DepartmentFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ArrayList<String> mDepartments = new ArrayList<>();

    public DepartmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_departmentlist, container, false);
        ButterKnife.bind(this, view);
        getActivity().setTitle("Make Your Appointment");

        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {

        DatabaseReference refOffers = FirebaseDatabase.getInstance().getReference().child(DEPARTMENTS);
        refOffers.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot department : dataSnapshot.getChildren()) {
                    if (department.getValue() != null) {

                        mDepartments.add(department.getValue().toString());
                    }
                }
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                recyclerView.setAdapter(new DepartmentsAdapter(getActivity(), mDepartments)); // set the Adapter to RecyclerView
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}