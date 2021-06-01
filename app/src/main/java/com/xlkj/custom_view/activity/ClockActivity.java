package com.xlkj.custom_view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.shouzhong.scanner.ScannerView;
import com.xlkj.custom_view.R;
import com.xlkj.custom_view.view.RadarView;

public class ClockActivity extends AppCompatActivity {
    private RadarView radarView;
    private ScannerView scannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

    }

}