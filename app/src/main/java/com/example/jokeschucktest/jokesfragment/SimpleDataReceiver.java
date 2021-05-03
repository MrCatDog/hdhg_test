package com.example.jokeschucktest.jokesfragment;

import com.example.jokeschucktest.error.ApiException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SimpleDataReceiver {

    public final static String BASE_URL = "http://api.icndb.com/jokes/random/";
    public final static String COUNT_URL = "http://api.icndb.com/jokes/count";
    public final static String ESCAPE_JS = "?escape=javascript"; //for correct form of quotes(non &quot;)
    public final static String ANSWER_TYPE_TAG = "type";
    public final static String ACCEPTABLE_ANSWER_TAG = "success";
    public final static String ANSWER_TAG = "value";
    public final static String JOKE_TEXT_TAG = "joke";

    private final OkHttpClient client = new OkHttpClient();

    public String[] receiveData(int count) throws IOException, JSONException, ApiException {
        Request request = new Request.Builder().url(BASE_URL + count + ESCAPE_JS).build();
        JSONObject answer = null;
        try (Response response = this.client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                answer = new JSONObject(response.body().string());
                JSONArray answerArr = answer.getJSONArray(ANSWER_TAG);
                String[] newDataArray = new String[count];
                for (int i = 0; i < count; i++) {
                    newDataArray[i] = answerArr.getJSONObject(i).getString(JOKE_TEXT_TAG);
                }
                return newDataArray;
            }
        } catch (JSONException ex) {
            addExceptionIfPossible(answer, ex);//ЕСЛИ API ВЕРНЁТ ОТЧЁТ ОБ ОШИБКЕ
        }
        return new String[0];
    }

    public int receiveCountOfAvailableJokes() throws IOException, JSONException, ApiException {
        Request request = new Request.Builder().url(COUNT_URL).build();
        JSONObject answer = null;
        try (Response response = this.client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                answer = new JSONObject(response.body().string());
                return answer.getInt(ANSWER_TAG);
            }
        } catch (JSONException ex) {
            addExceptionIfPossible(answer, ex);//ЕСЛИ API ВЕРНЁТ ОТЧЁТ ОБ ОШИБКЕ
            // (сначала сделал, потом понял что он почти никогда их не возвращает)
        }
        return 0;
    }

    private void addExceptionIfPossible(JSONObject answer, JSONException ex) throws JSONException, ApiException {
        if (answer != null && !answer.getString(ANSWER_TYPE_TAG).equals(ACCEPTABLE_ANSWER_TAG)) {
            throw new ApiException(answer.getString(ANSWER_TAG), ex);
        }
        throw ex;
    }
}

