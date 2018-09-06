package com.example.allseven64.kamus;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.allseven64.kamus.db.KamusHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PreLoadActivity extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_load);

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        new LoadData().execute();
    }

    private class LoadData extends AsyncTask <Void, Integer, Void>{
        final String TAG = LoadData.class.getSimpleName();
        KamusHelper kamusHelper;
        AppPreference appPreference;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute(){
            kamusHelper = new KamusHelper(PreLoadActivity.this);
            appPreference = new AppPreference(PreLoadActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            Boolean firstRun = appPreference.getFirstRun();

            if(firstRun){
                ArrayList<KamusModel> kamusInggris = preLoadRaw("Eng");
                ArrayList<KamusModel> kamusIndonesia = preLoadRaw("Ind");


                kamusHelper.open();
                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 100.0;
                Double progressDiff = (progressMaxInsert - progress) / kamusIndonesia.size()+kamusInggris.size();

                //IND
                kamusHelper.beginTransaction();
                try {
                    for (KamusModel model : kamusIndonesia){
                        kamusHelper.insertTransaction(model, "Ind");
                    }
                    kamusHelper.setTransactionSuccessful();
                }catch (Exception e){
                    //Jika gagal
                    Log.e(TAG,"doInBackground:Exception");
                }
                kamusHelper.endTransaction();
                progress += progressDiff;
                publishProgress((int)progress);

                //ENG
                kamusHelper.beginTransaction();
                try {
                    for (KamusModel model : kamusInggris){
                        kamusHelper.insertTransaction(model, "Eng");
                    }
                    kamusHelper.setTransactionSuccessful();
                }catch (Exception e){
                    //Jika gagal
                    Log.e(TAG,"doInBackground:Exception");
                }
                kamusHelper.endTransaction();
                progress += progressDiff;
                publishProgress((int)progress);

                kamusHelper.close();

                appPreference.setFirstRun(false);
                publishProgress((int)maxprogress);
            }
            else {
                try {
                    synchronized (this){
                        this.wait(2000);
                        publishProgress(50);
                        this.wait(2000);
                        publishProgress((int)maxprogress);
                    }
                }catch (Exception e){
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values){
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result){
            Intent i = new Intent(PreLoadActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<KamusModel> preLoadRaw(String lang_select){
        ArrayList<KamusModel> kamusModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        int raw_data;

        if (lang_select == "Eng"){
            raw_data = R.raw.english_indonesia;
        }else{
            raw_data = R.raw.indonesia_english;
        }

        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(raw_data);
            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;

            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                KamusModel kamusModel;
                kamusModel = new KamusModel(splitstr[0], splitstr[1]);
                kamusModels.add(kamusModel);
                count++;

            }while (line != null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  kamusModels;
    }
}
