package com.zva2340.collegescheduler.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.zva2340.collegescheduler.R;

public class SortingArrayAdapter extends ArrayAdapter<String> {

    public SortingArrayAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        if (position == 3) {
            view.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.assignmentColor));
        } else if (position == 4){
            view.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.examColor));
        } else {
            view.setBackgroundColor(getContext().getResources().getColor(android.R.color.white));
        }
        return view;
    }
}
