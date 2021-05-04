package com.example.jokeschucktest.jokesfragment;

import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;

import com.example.jokeschucktest.error.ErrorFragment;
import com.example.jokeschucktest.MainActivity;

import java.lang.ref.WeakReference;

public class JokesOnClickListener implements View.OnClickListener {

    private static class AsyncTaskRunner extends AsyncTask<Void, String, String[]> {

        public final static String WRONG_NUM_MSG = "Столько у нас нет, попробуйте другое число!";

        private static final SimpleDataReceiver dataReceiver = new SimpleDataReceiver();
        int amount;
        boolean isWrongNumber = false;
        private final WeakReference<JokesOnClickListener> justWorkGodDamn;

        AsyncTaskRunner(JokesOnClickListener context, int amount) {
            justWorkGodDamn = new WeakReference<>(context);
            this.amount = amount;
        }

        @Override
        protected void onPreExecute() {
            JokesOnClickListener context = justWorkGodDamn.get();
            context.jokesFragment.stopInput();
        }

        @Override
        protected String[] doInBackground(Void... voids) {
            try {
                if (amount < 1 || amount > dataReceiver.receiveCountOfAvailableJokes()) {
                    isWrongNumber = true;
                    this.cancel(false);
                    return new String[0];
                }
                return dataReceiver.receiveData(amount);
            } catch (Exception ex) {
                ErrorFragment errorFragment = new ErrorFragment(ex);
                final MainActivity activity = justWorkGodDamn.get().mainActivityWeakReference.get();
                activity.changeFragment(errorFragment);
            }
            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] result) {
            JokesOnClickListener context = justWorkGodDamn.get();
            context.jokesFragment.setRecyclerAdapterData(result);
            context.isWorking = false;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (isWrongNumber) {
                justWorkGodDamn.get().jokesFragment.showSnack(WRONG_NUM_MSG);
                isWrongNumber = false;
            }
        }
    }

    private AsyncTaskRunner onWork;
    private boolean isWorking = false;
    private final WeakReference<MainActivity> mainActivityWeakReference;
    private final JokesFragment jokesFragment;

    JokesOnClickListener(WeakReference<MainActivity> mainActivity, JokesFragment jokesFragment) {
        this.mainActivityWeakReference = mainActivity;
        this.jokesFragment = jokesFragment;
    }

    @Override
    public void onClick(View v) {
        String text = jokesFragment.getJokeAmountText();
        if (text.isEmpty()) {
            return;
        }
        if (isWorking) {
            onWork.cancel(false);
        }
        AsyncTaskRunner task = new AsyncTaskRunner(this, Integer.parseInt(text));
        onWork = task;
        task.execute();
        isWorking = true;
    }
}
