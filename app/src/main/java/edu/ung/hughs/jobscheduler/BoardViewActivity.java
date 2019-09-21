package edu.ung.hughs.jobscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class BoardViewActivity extends AppCompatActivity {

    private static int personID;
    private static int boardID;

    private SmartFragmentStatePagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_view);
        Intent intent = getIntent();
        personID = intent.getIntExtra("personID", 0);
        boardID = intent.getIntExtra("boardID", 0);
        ViewPager vpPager = findViewById(R.id.vpPager);
        DAO dao = new DAO();
        ArrayList<String> columns = dao.getStatusByBoard(boardID);
        Log.e("ran938", "boardID: " + boardID + "\nCols:" + columns.toString());
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), columns);
        vpPager.setAdapter(adapterViewPager);







        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BoardViewActivity.this, AddJob.class);
                intent.putExtra("personID", personID);
                intent.putExtra("boardID", boardID);
                startActivity(intent);
            }
        });
    }

    public static class MyPagerAdapter extends SmartFragmentStatePagerAdapter {

        private static int NUM_ITEMS;
        private static ArrayList<String> cols = new ArrayList<>();


        public MyPagerAdapter(FragmentManager fragmentManager, ArrayList<String> columns)
        {
            super(fragmentManager);
            cols = columns;
            NUM_ITEMS = cols.size();
            Log.e("inside:", cols.toString());
        }
        @Override
        public int getCount()
        {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position)
        {
            Log.e("BoardViewActivity getItem: ", ("args: " + position + "," + cols.get(position) + "," + boardID));
            return BoardColumnFragment.newInstance(position, cols.get(position), boardID, personID);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            Log.e("PageTitle",cols.get(position));
            return cols.get(position);
        }
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(BoardViewActivity.this, BoardListActivity.class);
        intent.putExtra("boardID", boardID);
        intent.putExtra("personID", personID);
        startActivity(intent);
    }
}