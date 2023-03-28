package com.example.expensetracker;

import static com.example.expensetracker.R.id.tvListAmount;
import static com.example.expensetracker.R.id.tvListName;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ArrayListCustomAdapter extends ArrayAdapter<DetailList> {
    public ArrayListCustomAdapter(Context context, ArrayList<DetailList> detailsArrayList) {
        super(context, R.layout.list_layout, detailsArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // capturing the view and checking if it is null
        //check if the existing view is being reused, otherwise inflate the view
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, parent,false);
        }
        //Get the object located at this position in the list
        DetailList detailList = getItem(position);
        TextView tvListName = (TextView) listView.findViewById(R.id.tvListName);
        TextView tvListAmount = (TextView) listView.findViewById(R.id.tvListAmount);

        tvListName.setText(detailList.getPersonName());
        tvListAmount.setText(String.valueOf(detailList.getAmount()));
        return listView;
    }


}
