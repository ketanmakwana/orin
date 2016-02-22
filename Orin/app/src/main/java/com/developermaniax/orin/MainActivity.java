package com.developermaniax.orin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.developermaniax.orin.Net.InternetConnection;
import com.developermaniax.orin.component.FontView;
import com.developermaniax.orin.mediaplayer.Player;
import com.developermaniax.orin.model.SongListModel;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView btnBack;
    ImageView btnSearch;
    ImageView btnBackward;
    ImageView btnForward;
    ListView lstSongs;
    ImageView btnPause;

    TextView txtTimer;
    SongsListAdapter adapter;

    ArrayList<SongListModel> mSongsModel;
    Player player;
    MediaPlayer mPlayer;

    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBack = (ImageView) findViewById(R.id.txtBack);
        btnBackward = (ImageView) findViewById(R.id.txtStepBackward);
        btnForward = (ImageView) findViewById(R.id.txtStepForward);
        btnSearch = (ImageView) findViewById(R.id.txtSearch);
        lstSongs = (ListView) findViewById(R.id.lstSongs);
        btnPause = (ImageView) findViewById(R.id.imgPause);
        txtTimer = (TextView) findViewById(R.id.txtTimer);

        CircularProgressBar circularProgressBar = (CircularProgressBar) findViewById(R.id.seekBar);
        circularProgressBar.setColor(ContextCompat.getColor(this, R.color.colorBlack));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGray));
        circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.progressBarWidth));
        circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.backgroundProgressBarWidth));
        int animationDuration = 2500; // 2500ms = 2,5s
        circularProgressBar.setProgressWithAnimation(65, animationDuration);


        if (new InternetConnection().isConnectingToInternet(this)) {
            new GetDataFromWeb().execute(new String[]{InternetConnection.songs_url});
        } else {
            //please check connection.
        }

        btnBackward.setOnClickListener(this);
        btnForward.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnPause.setOnClickListener(this);

    }

    private void PlayMusic(int index) {
        new Player(this, mSongsModel).playSong(index);
        if(Player.getIsPlaying() == 1){
            btnPause.setImageResource(R.drawable.pause);
        }else if(Player.getIsPlaying() == 2){
            btnPause.setImageResource(R.drawable.play);
        }else{
            btnPause.setImageResource(R.drawable.play);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    protected void getData(String urls) {
        mSongsModel = new ArrayList<>();
        int TimeOut = 25000;
        HttpURLConnection c = null;
        String JSON = null;

        try {

            URL u = new URL(urls);
            c = (HttpURLConnection) u.openConnection();
            c.setReadTimeout(30000);
            c.setConnectTimeout(30000);
            c.setRequestMethod("POST");
            c.setDoInput(true);
            c.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder();
            builder.appendQueryParameter("accesskey", getString(R.string.accesskey));
            String query = builder.build().getEncodedQuery();
            OutputStream os = c.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            c.connect();

            disableConnectionReuseIfNecessary();
            int status = c.getResponseCode();
            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    JSON = sb.toString();
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE,
                            null, ex);
                }
            }
        }

        try {
            JSONObject jObject = new JSONObject(JSON);
            JSONArray itemArray = jObject.getJSONArray("data");
            for (int i = 0; i < itemArray.length(); i++) {
                JSONObject jItemObject = itemArray.getJSONObject(i);
                JSONObject jSongData = jItemObject.getJSONObject("song_data");
                String id = jSongData.getString("id");
                String previewUrl = jSongData.getString("preview_url");
                String imageUrl = jSongData.getString("image_url");
                String duration = jSongData.getString("duration");
                String artistName = jSongData.getString("artistName");
                String genrename = jSongData.getString("genrename");
                String SongName = jSongData.getString("post_title");
                Log.e("SongUrl", previewUrl);
                mSongsModel.add(new SongListModel(id, previewUrl, "http://www.spiritoforin.com/wp-content/uploads/" + imageUrl, duration, artistName, SongName, genrename));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Error!", e.toString());
        }
    }

    public void disableConnectionReuseIfNecessary() {
        // Work around pre-Froyo bugs in HTTP connection reuse.
        if (Integer.parseInt(Build.VERSION.SDK) < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");

        }
    }

    ProgressDialog progressDialog;

    @Override
    public void onClick(View target) {
        if (target == btnPause) {

        }
        if(target == btnBack){
            Intent intent = new Intent(MainActivity.this,Home.class);
            startActivity(intent);
        }
    }

    class GetDataFromWeb extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait while processing...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            getData(params[0]);
            progressDialog.dismiss();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mSongsModel != null && mSongsModel.size() > 0) {
                adapter = new SongsListAdapter(MainActivity.this, R.layout.list_songs_items, mSongsModel);
                lstSongs.setAdapter(adapter);
            }
            // set whole data
        }
    }

    class SongsListAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<SongListModel> mSongList;
        int resourceId;

        public SongsListAdapter(Context mContext, int resourceId, ArrayList<SongListModel> mSongList) {
            this.mContext = mContext;
            this.resourceId = resourceId;
            this.mSongList = mSongList;
        }

        @Override
        public int getCount() {
            return mSongList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_songs_items, parent, false);
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.linearSong);
            ImageView imgUserPhoto = (ImageView) view.findViewById(R.id.imgArtistPhoto);
            TextView txtSongName = (TextView) view.findViewById(R.id.txtSongName);
            TextView txtSongDescription = (TextView) view.findViewById(R.id.txtSongDescription);
            FontView txtMore = (FontView) view.findViewById(R.id.txtMore);
            ImageView txtAdd = (ImageView) view.findViewById(R.id.txtAdd);
            if (mSongList != null) {
                Picasso.with(mContext).load(mSongList.get(position).getImageUrl()).into(imgUserPhoto);
                txtSongName.setText(mSongList.get(position).getSongName());
                txtSongDescription.setText(mSongList.get(position).getArtistName());
            }
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSongList.size() > 0 && mSongList != null) {
                        txtTimer.setText(mSongsModel.get(position).getDuration() + " / " + mSongsModel.get(position).getDuration());
                        PlayMusic(position);
                   }
                }
            });

            return view;
        }
    }
}
