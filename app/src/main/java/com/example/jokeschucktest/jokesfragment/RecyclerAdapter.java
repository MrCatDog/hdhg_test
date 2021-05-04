package com.example.jokeschucktest.jokesfragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jokeschucktest.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.VH> {

    public final static String SAVE_DELIMITER = "\t";

    static class VH extends RecyclerView.ViewHolder {

        private final TextView jokeText;

        public VH(View itemView) {
            super(itemView);
            jokeText = itemView.findViewById(R.id.joke_text);
        }
    }

    private String[] dataArray = new String[0];

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.joke_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.jokeText.setText(dataArray[position]);
    }

    @Override
    public int getItemCount() {
        return dataArray.length;
    }

    public void setDataArray(String[] dataArray) {
        this.dataArray = dataArray;
    }

    public void restoreDataArray(String saved) {
        this.dataArray = saved.split(SAVE_DELIMITER);
    }

    public String getDataArrayForSave() {
        StringBuilder sb = new StringBuilder();
        for (String i : dataArray) {
            sb.append(i).append(SAVE_DELIMITER);
        }
        return sb.toString();
    }
}
