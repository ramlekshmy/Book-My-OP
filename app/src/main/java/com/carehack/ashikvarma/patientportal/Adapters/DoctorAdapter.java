package com.carehack.ashikvarma.patientportal.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carehack.ashikvarma.patientportal.Activities.DashboardActivity;
import com.carehack.ashikvarma.patientportal.Fragments.AppointmentFragment;
import com.carehack.ashikvarma.patientportal.Models.Doctor;
import com.carehack.ashikvarma.patientportal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.carehack.ashikvarma.patientportal.Utilities.Constants.DOCTORS;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

    private ArrayList<Doctor> mDoctors;
    private Context mContext;

    public DoctorAdapter(Context context, ArrayList<Doctor> names) {
        this.mContext = context;
        this.mDoctors = names;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.name.setText(mDoctors.get(position).getName());
        Doctor doctor = mDoctors.get(position);

        String temp = doctor.getPlace();
        int resID = this.mContext.getResources().getIdentifier(temp, "drawable", mContext.getPackageName());
        Picasso.with(mContext).load(resID).into(holder.image);

        // Picasso.with(mContext).load(mDoctors.get(position).getPlace()).into(holder.image);
//        Picasso.with(mContext).load(this.mContext.getResources().getIdentifier(mDoctors.get(position).getPlace()
//                , "drawable", mContext.getPackageName())).error(R.drawable.img1).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(DOCTORS, mDoctors.get(holder.getAdapterPosition()).getName());
                ((DashboardActivity) mContext).startFragmentNavigation(new AppointmentFragment(), bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDoctors.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.image)
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}