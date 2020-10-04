package com.example.musicappwithapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.musicappwithapi.ui.music.MusicActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //59d7e6b5b81357f4b29d9a4d1f5088bc
    }

    @OnClick(R.id.button_music)
        void onClickBtnMusic(){
        Toast.makeText(this, "Play Music Together", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MusicActivity.class);
        startActivity(intent);
    }
}