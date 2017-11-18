package com.carehack.ashikvarma.patientportal.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.carehack.ashikvarma.patientportal.R;

/**
 * Created by Bavithra on 11/15/2017.
 */

public class TipsFragment extends Fragment {
    CardView cardViewChild;
    CardView cardViewFitness;
    CardView cardViewHairloss;
    CardView cardViewChildDetails;
    CardView cardViewhairlossDetails;
    CardView cardViewFitnessDetails;
    ImageView imgChild;
    ImageView imgFItness;
    ImageView imghairloss;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tips_fragment, container, false);
        cardViewChild = (CardView) view.findViewById(R.id.cardViewchild);
        cardViewHairloss = (CardView) view.findViewById(R.id.cardViewhairloss);
        cardViewFitness = (CardView) view.findViewById(R.id.cardViewfitness);
        cardViewChildDetails = (CardView) view.findViewById(R.id.cardViewChildCareDetails);
        cardViewFitnessDetails = (CardView) view.findViewById(R.id.cardViewfitnessDetails);
        cardViewhairlossDetails = (CardView) view.findViewById(R.id.cardViewhaircareDetails);
        imgChild = (ImageView) view.findViewById(R.id.imgchild);
        imgFItness = (ImageView) view.findViewById(R.id.imgfitness);
        imghairloss = (ImageView) view.findViewById(R.id.imghair);
        cardViewChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardViewChildDetails.getVisibility() == View.VISIBLE) {
                    cardViewChildDetails.setVisibility(View.GONE);
                    imgChild.setRotation(0);
                } else {
                    cardViewChildDetails.setVisibility(View.VISIBLE);
                    imgChild.setRotation(90);

                }
            }
        });
        cardViewFitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardViewFitnessDetails.getVisibility() == View.VISIBLE) {
                    cardViewFitnessDetails.setVisibility(View.GONE);
                    imgFItness.setRotation(0);
                } else {
                    cardViewFitnessDetails.setVisibility(View.VISIBLE);
                    imgFItness.setRotation(90);

                }
            }
        });
        cardViewHairloss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardViewhairlossDetails.getVisibility() == View.VISIBLE) {
                    cardViewhairlossDetails.setVisibility(View.GONE);
                    imghairloss.setRotation(0);
                } else {
                    cardViewhairlossDetails.setVisibility(View.VISIBLE);
                    imghairloss.setRotation(90);

                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(getString(R.string.tips));
    }
}
