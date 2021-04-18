package com.hackathon.unpack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ClinicAdapter extends ArrayAdapter<Clinic> {
    public ClinicAdapter(Context context, ArrayList<Clinic> users) {
        super(context, 0, users);
    }
    private static class ViewHolder {
        TextView name;
        TextView location;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Clinic clinic = getItem(position);
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.cliniclines, parent, false);

            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.location = (TextView) convertView.findViewById(R.id.location);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(clinic.name);
        viewHolder.location.setText(clinic.location);

        return convertView;
    }
}
