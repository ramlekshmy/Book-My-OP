package com.carehack.ashikvarma.patientportal.Adapters;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carehack.ashikvarma.patientportal.R;

import java.util.ArrayList;

//import static com.example.ashikvarma.patientportal.R.drawable.img1;

public class CustomAdapterForMyAppointments extends RecyclerView.Adapter<CustomAdapterForMyAppointments.MyViewHolder> {

    ArrayList AppointmentDate;
    ArrayList AppointmentConcerns;
    ArrayList AppointmentDoctor;
    ArrayList PatientName;
    Resources resources;
    Context context;
    public Activity context1;
    public String s;

    public CustomAdapterForMyAppointments( Context context,ArrayList PatientName,ArrayList AppointmentDate,ArrayList AppointmentConcerns,ArrayList AppointmentDoctor) {

        this.context=context;
        this.AppointmentConcerns=AppointmentConcerns;
        this.AppointmentDate=AppointmentDate;
        this.AppointmentDoctor=AppointmentDoctor;
        this.PatientName=PatientName;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
// infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.myappointments, parent, false);
        context = parent.getContext();
// set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(CustomAdapterForMyAppointments.MyViewHolder holder, final int position) {
// set the data in items
        holder.Pname.setText(PatientName.get(position).toString());
        holder.Dname.setText(AppointmentDoctor.get(position).toString());
        holder.AppDate.setText(AppointmentDate.get(position).toString());
        holder.AppConcerns.setText(AppointmentConcerns.get(position).toString());




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
//                s = departmentNames.get(position).toString();
//
//
//                Fragment fragment = new DoctorFragment();
//
//                FragmentManager fragmentManager = ((DashboardActivity)mContext).getSupportFragmentManager();
//
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                Bundle bundle=new Bundle();
//                bundle.putString("mDepartment", s); //key and value
////set Fragmentclass Arguments
//                fragment.setArguments(bundle);
//
//
//
//                // this is basically mContext of the class
//                fragmentTransaction.replace(R.id.fragment_department_id, fragment);
////                fragmentTransaction.remove();
//                // fragmentTransaction.replace(R.id.fragment_department_id, fragment);
//                //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//
//                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return PatientName.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView Pname;
        TextView Dname;
        TextView AppDate;
        TextView AppConcerns;


        public MyViewHolder(View itemView) {
            super(itemView);

// get the reference of item view's
            Pname = (TextView)itemView.findViewById(R.id.Pname);
            Dname = (TextView)itemView.findViewById(R.id.Dname);
            AppDate = (TextView)itemView.findViewById(R.id.AppDate);
            AppConcerns = (TextView)itemView.findViewById(R.id.AppConcerns);


        }
    }
}