package com.carehack.ashikvarma.patientportal.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carehack.ashikvarma.patientportal.Adapters.CustomAdapterForMyAppointments;
import com.carehack.ashikvarma.patientportal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAppointments extends Fragment {
    View view;
    //    TextView txt2;
//    public String name;
//    public String img_url;
//    int indexing=0;

    public ArrayList<String> doctorname_listing=new ArrayList<String>();

    public ArrayList<String> date_listing=new ArrayList<String>();
    public ArrayList<String> concerns_listing=new ArrayList<String>();
    public ArrayList<String> patientname_listing=new ArrayList<String>();

    public CardView cv;
    public String appointment_date;
    public FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    public  FirebaseUser user=firebaseAuth.getCurrentUser();
    //    public String year;
//    public ListView lv;
//    private ArrayAdapter<String> arr;
//    public TextView txt;
    DatabaseReference rootRef= FirebaseDatabase.getInstance().getReference();
    //    public ArrayList<String> offers=new ArrayList<>();
//
//    private RecyclerView mRecyclerView;
//    private RecyclerView.Adapter mAdapter;
//    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList Appointment_Concerns = new ArrayList<>();
    ArrayList Appointment_Doctor= new ArrayList<>();
    ArrayList Appointment_Patient_Name= new ArrayList<>();
    ArrayList Appointment_Date= new ArrayList<>();

    public MyAppointments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        view=inflater.inflate(R.layout.fragment_appointment, container, false);

        DatabaseReference refOffers=FirebaseDatabase.getInstance().getReference().child("Appointments");

        refOffers.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                HashSet<OfferList> set=new HashSet<OfferList>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    String concerns=(((DataSnapshot) i.next()).getValue()).toString();
                    String date = (((DataSnapshot) i.next()).getValue()).toString();
                    String doctor=(((DataSnapshot) i.next()).getValue()).toString();
                    String name=(((DataSnapshot) i.next()).getValue()).toString();
                    String phone=(((DataSnapshot) i.next()).getValue()).toString();
                    if(user.getPhoneNumber().equals(phone))
                    {
                        patientname_listing.add(name);
                        doctorname_listing.add(doctor);
                        date_listing.add(date);

                        concerns_listing.add(concerns);

                    }

                }

                for( String arr:patientname_listing)
                {

                    Appointment_Patient_Name.add(arr);
                    patientname_listing.clear();
                }
                for( String arr1:doctorname_listing)
                {

                    Appointment_Doctor.add(arr1);
                    doctorname_listing.clear();
                }
                for( String arr2:date_listing)
                {

                    Appointment_Date.add(arr2);
                    date_listing.clear();
                }
                for( String arr3:concerns_listing)
                {

                    Appointment_Concerns.add(arr3);
                    concerns_listing.clear();
                }

                RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);

                //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
                CustomAdapterForMyAppointments customAdapterForMyAppointments = new CustomAdapterForMyAppointments(getActivity(),Appointment_Patient_Name,Appointment_Date,Appointment_Concerns,Appointment_Doctor);
                recyclerView.setAdapter(customAdapterForMyAppointments); // set the Adapter to RecyclerView


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




        getActivity().setTitle("My Appointments");





        return view;
    }


}
