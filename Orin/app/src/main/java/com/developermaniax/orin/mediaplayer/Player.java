package com.developermaniax.orin.mediaplayer;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

import com.developermaniax.orin.R;
import com.developermaniax.orin.model.SongListModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Ketan on 12/02/2016.
 */
public class Player extends MediaPlayer implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener,  MediaPlayer.OnPreparedListener{

    static MediaPlayer mPlayer;
    static Context mContext;
    private static Utilities utils;
    private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds
    private int currentSongIndex = 0;
    private boolean isShuffle = false;
    private boolean isRepeat = false;
    private static Handler mHandler = new Handler();

    public static int isPlaying = 0;
    private static ArrayList<SongListModel> mSongsModel;
    /*Stat Media Player Using Notification Service

    String songName;
// assign the song name to songName
PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(getApplicationContext(), MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
Notification notification = new Notification();
notification.tickerText = text;
notification.icon = R.drawable.play0;
notification.flags |= Notification.FLAG_ONGOING_EVENT;
notification.setLatestEventInfo(getApplicationContext(), "MusicPlayerSample",
                "Playing: " + songName, pi);
startForeground(NOTIFICATION_ID, notification);
     */

    static {
        mPlayer  = new MediaPlayer();
    }

    public Player(Context mContext , ArrayList<SongListModel> mSongsModel) {
        this.mContext = mContext;
        this.mSongsModel = mSongsModel;
        utils = new Utilities();
        mPlayer.setOnCompletionListener(this);

    }

    public static void PauseMusic(){
        if(mPlayer.isPlaying()){
            mPlayer.pause();
        }
    }
    private static void stopMusic(){
        if(mPlayer.isPlaying()){
            mPlayer.stop();
        }
    }

    private static void PlayMusic(){
        if(mPlayer.isPlaying()){
            if(mPlayer!=null){
                mPlayer.pause();
                // Changing button image to play button
                isPlaying  = 2;
            }
        }else{
            // Resume song
            if(mPlayer!=null){
                mPlayer.start();
                // Changing button image to pause button
                isPlaying  = 1;

            }
        }
    }
    public static int getBufferProgress(){
        int progress = 0;

        return 0;
    }

    private void forward(){
        int currentPosition = mPlayer.getCurrentPosition();
        // check if seekForward time is lesser than song duration
        if(currentPosition + seekForwardTime <= mPlayer.getDuration()){
            // forward song
            mPlayer.seekTo(currentPosition + seekForwardTime);
        }else{
            // forward to end position
            mPlayer.seekTo(mPlayer.getDuration());
        }
    }

    private void BackWard(){
        int currentPosition = mPlayer.getCurrentPosition();
        // check if seekBackward time is greater than 0 sec
        if(currentPosition - seekBackwardTime >= 0){
            // forward song
            mPlayer.seekTo(currentPosition - seekBackwardTime);
        }else{
            // backward to starting position
            mPlayer.seekTo(0);
        }
    }

    private void setRepeat(){
        if(isRepeat){
            isRepeat = false;
            Toast.makeText(mContext, "Repeat is OFF", Toast.LENGTH_SHORT).show();
          //  btnRepeat.setImageResource(R.drawable.btn_repeat);
        }else{
            // make repeat to true
            isRepeat = true;
            Toast.makeText(mContext, "Repeat is ON", Toast.LENGTH_SHORT).show();
            // make shuffle to false
            isShuffle = false;
//            btnRepeat.setImageResource(R.drawable.btn_repeat_focused);
//            btnShuffle.setImageResource(R.drawable.btn_shuffle);
        }
    }
    private void Previous(){
        if(currentSongIndex > 0){
           // playSong(currentSongIndex - 1);
            currentSongIndex = currentSongIndex - 1;
        }else{
            // play last song
          //  playSong(songsList.size() - 1);
            currentSongIndex = mSongsModel.size() - 1;
        }
    }
    private void Next(){
        if(currentSongIndex < (mSongsModel.size() - 1)){
            //playSong(currentSongIndex + 1);
            currentSongIndex = currentSongIndex + 1;
        }else{
            // play first song
          // playSong(0);
            currentSongIndex = 0;
        }
    }

    private void Suffle(){
        if(isShuffle){
            isShuffle = false;
            Toast.makeText(mContext, "Shuffle is OFF", Toast.LENGTH_SHORT).show();
            //btnShuffle.setImageResource(R.drawable.btn_shuffle);
        }else{
            // make repeat to true
            isShuffle= true;
            Toast.makeText(mContext, "Shuffle is ON", Toast.LENGTH_SHORT).show();
            // make shuffle to false
            isRepeat = false;
//            btnShuffle.setImageResource(R.drawable.btn_shuffle_focused);
//            btnRepeat.setImageResource(R.drawable.btn_repeat);
        }
    }

    @Override
    public void setOnPreparedListener(OnPreparedListener listener) {
        super.setOnPreparedListener(this);

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    class MusicPlayerSeeking extends AsyncTask<Void,Void,Void>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prepareAsync();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                mPlayer.prepare();
            } catch (IOException e) {
                Toast.makeText(mContext,"Sound File Not Exists!",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mPlayer.start();
        }
    }
    public static void PlayMusic(String songUrl) {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(songUrl);
        } catch (IllegalArgumentException e) {
            Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mPlayer.prepare();
        } catch (IllegalStateException e) {
            Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        }
        mPlayer.start();
        Toast.makeText(mContext, "Song Started!!", Toast.LENGTH_SHORT).show();
    }
    public static void  playSong(int songIndex){
        // Play song
        try {
            mPlayer.reset();
            mPlayer.setDataSource(mSongsModel.get(songIndex).getPreviewUrl());
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mPlayer.prepare();
            } catch (IllegalStateException e) {
                Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
            }
            mPlayer.start();
            // Displaying Song title
            isPlaying = 1;
            Toast.makeText(mContext, "Song Started!!", Toast.LENGTH_LONG).show();
            //btnPlay.setImageResource(R.drawable.btn_pause);

            // set Progress bar values

            // Updating progress bar
            updateProgressBar();
        }  catch (IllegalArgumentException e) {
            Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            Toast.makeText(mContext, "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update timer on seekbar
     * */
    public static void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    /**
     * Background Runnable thread
     * */
    private static Runnable mUpdateTimeTask = new Runnable() {
        public void run() {

            long totalDuration = mPlayer.getDuration();
            long currentDuration = mPlayer.getCurrentPosition();

            // Displaying Total Duration time
            //songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
//            songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);

            //songProgressBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };

    /**
     *
     * */
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {

    }

    /**
     * When user starts moving the progress handler
     * */
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // remove message Handler from updating progress bar
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    /**
     * When user stops moving the progress hanlder
     * */
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mPlayer.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        mPlayer.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();
    }

    /**
     * On Song Playing completed
     * if repeat is ON play same song again
     * if shuffle is ON play random song
     * */
    @Override
    public void onCompletion(MediaPlayer arg0) {

        // check for repeat is ON or OFF
        if(isRepeat){
            // repeat is on play same song again
            playSong(currentSongIndex);
        } else if(isShuffle){
            // shuffle is on - play a random song
            Random rand = new Random();
            currentSongIndex = rand.nextInt((mSongsModel.size() - 1) - 0 + 1) + 0;
            playSong(currentSongIndex);
        } else{
            // no repeat or shuffle ON - play next song
            if(currentSongIndex < (mSongsModel.size() - 1)){
                playSong(currentSongIndex + 1);
                currentSongIndex = currentSongIndex + 1;
            }else{
                // play first song
                playSong(0);
                currentSongIndex = 0;
            }
        }
    }

    public void onDestroy(){
        mPlayer.release();
    }

    public static MediaPlayer getmPlayer() {
        return mPlayer;
    }

    public static void setmPlayer(MediaPlayer mPlayer) {
        Player.mPlayer = mPlayer;
    }

    public static Context getmContext() {
        return mContext;
    }

    public static void setmContext(Context mContext) {
        Player.mContext = mContext;
    }

    public static Utilities getUtils() {
        return utils;
    }

    public static void setUtils(Utilities utils) {
        Player.utils = utils;
    }

    public int getSeekForwardTime() {
        return seekForwardTime;
    }

    public void setSeekForwardTime(int seekForwardTime) {
        this.seekForwardTime = seekForwardTime;
    }

    public int getSeekBackwardTime() {
        return seekBackwardTime;
    }

    public void setSeekBackwardTime(int seekBackwardTime) {
        this.seekBackwardTime = seekBackwardTime;
    }

    public int getCurrentSongIndex() {
        return currentSongIndex;
    }

    public void setCurrentSongIndex(int currentSongIndex) {
        this.currentSongIndex = currentSongIndex;
    }

    public boolean isShuffle() {
        return isShuffle;
    }

    public void setIsShuffle(boolean isShuffle) {
        this.isShuffle = isShuffle;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }

    public static Handler getmHandler() {
        return mHandler;
    }

    public static void setmHandler(Handler mHandler) {
        Player.mHandler = mHandler;
    }

    public static int getIsPlaying() {
        return isPlaying;
    }

    public static void setIsPlaying(int isPlaying) {
        Player.isPlaying = isPlaying;
    }

    public static ArrayList<SongListModel> getmSongsModel() {
        return mSongsModel;
    }

    public static void setmSongsModel(ArrayList<SongListModel> mSongsModel) {
        Player.mSongsModel = mSongsModel;
    }

    public static Runnable getmUpdateTimeTask() {
        return mUpdateTimeTask;
    }

    public static void setmUpdateTimeTask(Runnable mUpdateTimeTask) {
        Player.mUpdateTimeTask = mUpdateTimeTask;
    }
}
