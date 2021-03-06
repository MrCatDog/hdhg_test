package com.example.jokeschucktest.jokesfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jokeschucktest.MainActivity;
import com.example.jokeschucktest.R;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;

public class JokesFragment extends Fragment {

    public final static int TITLE = R.string.jokes_title;
    public final static String JOKES_SAVE_TAG = "FUNNYASHELL";

    private final RecyclerAdapter recyclerAdapter = new RecyclerAdapter();
    private final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

    private RecyclerView jokesList;
    private Button jokeReloadBtn;
    private EditText jokeAmount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.joke_fragment, container, false);

        jokesList = layout.findViewById(R.id.jokes_list);
        jokeReloadBtn = layout.findViewById(R.id.jokes_load_btn);
        jokeAmount = layout.findViewById(R.id.jokes_amount);

        getActivity().setTitle(TITLE);

        jokeReloadBtn.setOnClickListener(new JokesOnClickListener(new WeakReference<>((MainActivity) getActivity()), this));

        jokesList.setLayoutManager(linearLayoutManager);
        jokesList.setAdapter(recyclerAdapter);

        if (savedInstanceState != null) {
            recyclerAdapter.setDataArray(savedInstanceState.getStringArray(JOKES_SAVE_TAG));
        }

        return layout;
    }

    public String getJokeAmountText() {
        return jokeAmount.getText().toString();
    }

    public void setRecyclerAdapterData(String[] data) {
        recyclerAdapter.setDataArray(data);
        jokesList.post(recyclerAdapter::notifyDataSetChanged);
    }

    public void stopInput() {
        jokeAmount.onEditorAction(EditorInfo.IME_ACTION_DONE);
    }

    public void showSnack(int text) {
        Snackbar.make(jokeAmount, getString(text), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray(JOKES_SAVE_TAG, recyclerAdapter.getDataArray());
    }
}
