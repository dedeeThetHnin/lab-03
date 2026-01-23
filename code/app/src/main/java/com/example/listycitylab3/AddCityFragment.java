package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import androidx.fragment.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


import androidx.annotation.NonNull;

public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener{
        void addCity(City city);
        void editCity(City city);
    }

    private AddCityDialogListener listener;
    private City city;

    public static AddCityFragment newInstance(City city){
        AddCityFragment fragment = new AddCityFragment();
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        if(context instanceof AddCityDialogListener){
            listener = (AddCityDialogListener) context;
        }else{
            throw new RuntimeException(context + " must implement AddCityDialogListerner");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState){
        View view= LayoutInflater
                .from(getContext())
                .inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        if (getArguments() != null){
            city = (City) getArguments().getSerializable("city");
            editCityName.setText(city.getName());
            editProvinceName.setText(city.getProvince());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Add/edit city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (dialog, which)->{

                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();

                    if (city == null){
                        listener.addCity(new City(cityName, provinceName));
                    } else{
                        city.setName(cityName);
                        city.setProvince(provinceName);
                        listener.editCity(city);
                    }
                })
                .create();
    }
}
