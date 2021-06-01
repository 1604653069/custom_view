package com.xlkj.custom_view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xlkj.custom_view.view.LetterSildeView;
import com.xlkj.custom_view.view.PercentView;
import com.xlkj.custom_view.R;

public class MainActivity extends AppCompatActivity implements LetterSildeView.OnLetterStrBackListener {
    private PercentView percentview;
    private LetterSildeView letterSildeView;
    private TextView showLetter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        percentview = this.findViewById(R.id.percentview);
        showLetter = this.findViewById(R.id.tv_show);
        percentview.starScan();
        letterSildeView = this.findViewById(R.id.letter_slide);
        letterSildeView.setOnLetterStrBackListener(this);
    }

    @Override
    public void onLetterTouchStrBack(String letter,float x,float y) {
        Log.e("TAG","letter->"+letter);
        showLetter.setVisibility(View.VISIBLE);
        showLetter.setText(letter);
        showLetter.setX(x);
        showLetter.setY(y);
    }

    @Override
    public void onTouchCanleListener() {
        Log.e("TAG","手势交互结束");
        showLetter.setVisibility(View.GONE);
    }
}