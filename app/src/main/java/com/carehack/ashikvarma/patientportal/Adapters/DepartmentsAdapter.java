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
import com.carehack.ashikvarma.patientportal.Fragments.DoctorFragment;
import com.carehack.ashikvarma.patientportal.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.carehack.ashikvarma.patientportal.Utilities.Constants.DEPARTMENTS;


public class DepartmentsAdapter extends RecyclerView.Adapter<DepartmentsAdapter.ViewHolder> {

    private ArrayList mDepartments;
    private Context mContext;
    int[] imagelist = {R.drawable.neurology, R.drawable.bone, R.drawable.cardiology, R.drawable.dentist,
            R.drawable.ent, R.drawable.nephro};

    public DepartmentsAdapter(Context context, ArrayList departmentNames) {
        this.mContext = context;
        this.mDepartments = departmentNames;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.departmentnames, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.name.setText(mDepartments.get(position).toString());
        holder.imgDept.setImageResource(imagelist[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(DEPARTMENTS, mDepartments.get(holder.getAdapterPosition()).toString());
                ((DashboardActivity) mContext).startFragmentNavigation(new DoctorFragment(), bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDepartments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;
        ImageView imgDept;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imgDept = (ImageView) itemView.findViewById(R.id.imgDept);
        }

    }
}