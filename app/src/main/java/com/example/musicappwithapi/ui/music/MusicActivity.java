package com.example.musicappwithapi.ui.music;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicappwithapi.R;
import com.example.musicappwithapi.data.db.model.Track;
import com.example.musicappwithapi.data.network.ApiClient;
import com.example.musicappwithapi.data.network.ApiInterface;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MusicActivity extends AppCompatActivity {

    public static final String API_KEY = "336c7a92c13b4970be0773e0b2cf5c67";
    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MediaPlayer mediaPlayer;
    @BindView(R.id.text_current_time)
    TextView txt_current_time;
    @BindView(R.id.text_duration)
    TextView txt_duration;
    @BindView(R.id.text_song_title)
    TextView txt_song_title;
    @BindView(R.id.text_song_author)
    TextView txt_song_author;
    @BindView(R.id.image_play)
    ImageView btn_play;
    @BindView(R.id.songProgressBar)
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.button_suffer)
    void onClickBtnSuffer() {
        Toast.makeText(this, "onClickBtnSuffer", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button_previous)
    void onClickBtnPrevious() {
        String url = "https://data00.chiasenhac.com/downloads/1833/0/1832664-be718709/128/Mot Nha - Da LAB.mp3";
        mediaPlayer(url);
        txt_song_title.setText("Một nhà");
        txt_song_author.setText("Da LAB");
    }

    @OnClick(R.id.image_play)
    void onClickBtnPlay() {
        mediaPlayer("https://data25.chiasenhac.com/download2/2119/0/2118057-1a95e408/128/Hoa%20Hai%20Duong%20-%20Jack.mp3");
        txt_song_title.setText("Hoa Hải Đường");
        txt_song_author.setText("Jack");
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            btn_play.setImageResource(R.drawable.ic_pause);
        } else {
            mediaPlayer.start();
            btn_play.setImageResource(R.drawable.ic_play);
        }
    }

    @OnClick(R.id.button_next)
    void onClickBtnNext() {
        mediaPlayer("https://data.chiasenhac.com/down2/1398/0/1397587-7381eabe/128/Dem Tan - Wowy.mp3");
        txt_song_title.setText("Đêm Tàn");
        txt_song_author.setText("Wowy");
        Toast.makeText(this, "onClickBtnNext", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button_list_music)
    void onClickBtnList() {
        Toast.makeText(this, "onClickBtnList", Toast.LENGTH_SHORT).show();
    }
    @Deprecated
    public void demo() {
        compositeDisposable.add(apiInterface.getTrack()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Track>() {
                    @Override
                    public void accept(Track track) throws Exception {
                        //  Toast.makeText(MusicActivity.this, "" + track.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    public void mediaPlayer(String url) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mRunnable.run();
            }
        });
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            Toast.makeText(this, "Error" + e, Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("DefaultLocale")
    private String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        long hours = millis / (1000 * 60 * 60);
        long minutes = (millis % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = ((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000;

        buf.append(String.format("%02d", hours))
                .append(":")
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));
        return buf.toString();
    }

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null) {
                //set max value
                int mDuration = mediaPlayer.getDuration();
                seekBar.setMax(mDuration);
                //update total time text view
                txt_duration.setText(getTimeString(mDuration));
                //set progress to current position
                int mCurrentPosition = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(mCurrentPosition);
                //update current time text view
                txt_current_time.setText(getTimeString(mCurrentPosition));
                //handle drag on seekbar
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (mediaPlayer != null && fromUser) {
                            mediaPlayer.seekTo(progress);
                        }
                    }
                });
            }
            //repeat above code every second
            mHandler.postDelayed(this, 10);
        }
    };
}
