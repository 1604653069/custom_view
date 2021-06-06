package com.xlkj.custom_view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.xlkj.custom_view.R;

import java.util.ArrayList;
import java.util.List;

public class SlideActivity extends AppCompatActivity {
    private ListView menuListView;
    private List<String> menusTitles = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        menuListView = this.findViewById(R.id.menu_listview);
        menusTitles.add("标题1");
        menusTitles.add("标题2");
        menusTitles.add("标题3");
        menusTitles.add("标题4");
        menusTitles.add("标题5");
        menusTitles.add("标题6");
        menusTitles.add("标题6");
        menusTitles.add("标题6");
        menusTitles.add("标题6");
        menusTitles.add("标题6");
        menusTitles.add("标题6");
        menusTitles.add("标题6");
        menusTitles.add("标题6");
        menusTitles.add("标题6");
        menusTitles.add("标题6");
        menusTitles.add("标题6");
        menusTitles.add("标题6");
        menuListView.setAdapter(new MyAdapter());
    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return menusTitles.size();
        }

        @Override
        public Object getItem(int i) {
            return menusTitles.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(SlideActivity.this).inflate(R.layout.item_left_menu,null);
            return view;
        }
    }
}