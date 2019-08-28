package edu.ung.hughs.jobscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

public class BoardViewActivity extends AppCompatActivity {

    private int personID;
    private int boardID;

    private SmartFragmentStatePagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_view);
        ViewPager vpPager = findViewById(R.id.vpPager);
        DAO dao = new DAO();
        ArrayList<String> columns = dao.getStatusByBoard(boardID);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), columns.size());
        vpPager.setAdapter(adapterViewPager);

        Intent intent = getIntent();

        personID = intent.getIntExtra("personID", 0);
        boardID = intent.getIntExtra("boardID", 0);




        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: make this go to add job activity
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public static class MyPagerAdapter extends SmartFragmentStatePagerAdapter {

        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager)
        {
            super(fragmentManager);
        }
        public MyPagerAdapter(FragmentManager fragmentManager, int tabs)
        {
            super(fragmentManager);
            NUM_ITEMS = tabs;
        }
        @Override
        public int getCount()
        {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position)
        {
            return BoardColumnFragment.newInstance(position, "test");
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return "page" + position;
        }
    }
}