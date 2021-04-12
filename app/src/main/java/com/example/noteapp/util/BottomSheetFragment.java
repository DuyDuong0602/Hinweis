package com.example.noteapp.util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.noteapp.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;



public class BottomSheetFragment extends BottomSheetDialogFragment {
    FrameLayout colorPicker1, colorPicker2, colorPicker3, colorPicker4;
    ImageView colorCheck1, colorCheck2, colorCheck3, colorCheck4;

    String selectedColor;

    public static BottomSheetFragment newInstance() {
        Bundle bundle = new Bundle();
        BottomSheetFragment bsf = new BottomSheetFragment();
        bsf.setArguments(bundle);
        return bsf;
    }
    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_note_bottom_sheet, null);
        dialog.setContentView(view);

        CoordinatorLayout.LayoutParams param = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = param.getBehavior();
        if(behavior instanceof BottomSheetBehavior) {
             ((BottomSheetBehavior) behavior).addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    String state = "";
                    switch(newState) {
                        case BottomSheetBehavior.STATE_DRAGGING: {
                            state = "DRAGGING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_SETTLING: {
                            state = "SETTLING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_EXPANDED: {
                            state = "EXPANDED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_COLLAPSED: {
                            state = "COLLAPSED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            state = "HIDDEN";
                            dismiss();
                            ((BottomSheetBehavior) behavior).setState(BottomSheetBehavior.STATE_COLLAPSED);

                        }
                    }
                }


                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                }
            });
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        selectedColor = Integer.toHexString(getResources().getColor(R.color.light_blue));
        colorPicker1 = getView().findViewById(R.id.colorPicker1);
        colorPicker2 = getView().findViewById(R.id.colorPicker2);
        colorPicker3 = getView().findViewById(R.id.colorPicker3);
        colorPicker4 = getView().findViewById(R.id.colorPicker4);
        colorCheck1 = getView().findViewById(R.id.colorCheck1);
        colorCheck2 = getView().findViewById(R.id.colorCheck2);
        colorCheck3 = getView().findViewById(R.id.colorCheck3);
        colorCheck4 = getView().findViewById(R.id.colorCheck4);
        setOnClickListener();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_bottom_sheet, container, false);
    }

    private void setOnClickListener() {
        colorPicker1.setOnClickListener(v -> {
            colorCheck1.setImageResource(R.drawable.ic_check_small);
            colorCheck2.setImageResource(0);
            colorCheck3.setImageResource(0);
            colorCheck4.setImageResource(0);
            selectedColor = Integer.toHexString(getResources().getColor(R.color.light_yellow));
            Intent pickColor1 = new Intent("bottom_sheet_action");
            pickColor1.putExtra("selectedColor", selectedColor);
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(pickColor1);
            dismiss();
        });

        colorPicker2.setOnClickListener(v -> {
            colorCheck1.setImageResource(0);
            colorCheck2.setImageResource(R.drawable.ic_check_small);
            colorCheck3.setImageResource(0);
            colorCheck4.setImageResource(0);
            selectedColor = Integer.toHexString(ContextCompat.getColor(getContext(), R.color.purple));
            Intent pickColor2 = new Intent("bottom_sheet_action");
            pickColor2.putExtra("selectedColor", selectedColor);
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(pickColor2);
            dismiss();
        });

        colorPicker3.setOnClickListener(v -> {
            colorCheck1.setImageResource(0);
            colorCheck2.setImageResource(0);
            colorCheck3.setImageResource(R.drawable.ic_check_small);
            colorCheck4.setImageResource(0);
            selectedColor = Integer.toHexString(ContextCompat.getColor(getContext(), R.color.light_green));
            Intent pickColor3 = new Intent("bottom_sheet_action");
            pickColor3.putExtra("selectedColor", selectedColor);
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(pickColor3);
            dismiss();
        });

        colorPicker4.setOnClickListener(v -> {
            colorCheck1.setImageResource(0);
            colorCheck2.setImageResource(0);
            colorCheck3.setImageResource(0);
            colorCheck4.setImageResource(R.drawable.ic_check_small);
            selectedColor = Integer.toHexString(ContextCompat.getColor(getContext(), R.color.blue_brown));
            Intent pickColor4 = new Intent("bottom_sheet_action");
            pickColor4.putExtra("selectedColor", selectedColor);
            LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(pickColor4);
            dismiss();
        });

    }
}
