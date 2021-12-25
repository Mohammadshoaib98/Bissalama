package com.example.regester.constants;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.regester.R;

public class MyViewHolderSeatMangement extends RecyclerView.ViewHolder {
    TextView seatNumber;
    CheckBox seatState;


    Button confirm, unconfirm, call;

    public MyViewHolderSeatMangement(@NonNull View itemView) {
        super(itemView);
        seatNumber = itemView.findViewById(R.id._seat_number);
        seatState = itemView.findViewById(R.id.ch_seat_status);
        confirm = itemView.findViewById(R.id.btn_confirm);
        unconfirm = itemView.findViewById(R.id.btn_un_confirm);
        call = itemView.findViewById(R.id.btn_call);


    }
    public void setSeatNumber (String seatNumber){
        this.seatNumber.setText(seatNumber);
    }

    public void setSeatState (CheckBox seatState){
        this.seatState = seatState;
    }
}
