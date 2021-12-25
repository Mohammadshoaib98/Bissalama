package com.example.regester.models_viewholders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.regester.R;

public class MyViewHolder_Seat extends RecyclerView.ViewHolder {

    TextView m_tv_seatNumber;
    CheckBox m_chb_seatState;

    public MyViewHolder_Seat(@NonNull View itemView) {
        super(itemView);
        m_tv_seatNumber = itemView.findViewById(R.id.tv_seatNumber);
        m_chb_seatState = itemView.findViewById(R.id.chb_seatState);
    }

    public void setSeatNumber(String m_tv_seatNumber) {
        this.m_tv_seatNumber.setText(m_tv_seatNumber);
    }

    public void setSeatChecked(Boolean seatCheck) {
        this.m_chb_seatState.setChecked(seatCheck);
    }

    public void setSeatEnabled(Boolean seatEnable) {
        this.m_chb_seatState.setEnabled(seatEnable);
    }

}
