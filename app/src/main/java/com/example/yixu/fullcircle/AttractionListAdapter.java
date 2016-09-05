package com.example.yixu.fullcircle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AttractionListAdapter extends ArrayAdapter<String> {
    public AttractionListAdapter(Context context, String [] AttractionList) {
        super(context, R.layout.custom_row_layout_for_listing,AttractionList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater hotelRowInflater = LayoutInflater.from(getContext());

        View hotelListingView = hotelRowInflater.inflate(R.layout.custom_row_layout_for_listing, parent, false);

        String attraction = getItem(position);

        ImageView hotelIcon = (ImageView)hotelListingView.findViewById(R.id.hotelIconImgView);

        hotelIcon.setImageResource(R.drawable.hotel_icon);

        TextView hotelName = (TextView) hotelListingView.findViewById(R.id.hotelName);

        hotelName.setText(attraction);

        return hotelListingView;

    }

    @Override
    public void remove(String object) {
        super.remove(object);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
