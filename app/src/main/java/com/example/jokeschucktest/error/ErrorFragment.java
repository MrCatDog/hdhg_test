package com.example.jokeschucktest.error;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.jokeschucktest.R;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorFragment extends Fragment {
    final Exception err;

    public ErrorFragment(Exception err) {
        this.err = err;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        this.err.printStackTrace(pw);

        View errContainer = inflater.inflate(R.layout.error_fragment, container, false);

        TextView baseInfo = errContainer.findViewById(R.id.error_base_info);
        baseInfo.setText(err.getLocalizedMessage());

        TextView extendInfo = errContainer.findViewById(R.id.more_about_error);
        extendInfo.setText(sw.toString());
        extendInfo.setVisibility(View.GONE);

        Button moreBut = errContainer.findViewById(R.id.more_about_error_but);
        moreBut.setOnClickListener((View v) -> {
            if (extendInfo.getVisibility() == View.GONE)
                extendInfo.setVisibility(View.VISIBLE);
            else
                extendInfo.setVisibility(View.GONE);
        });

        return errContainer;
    }
}