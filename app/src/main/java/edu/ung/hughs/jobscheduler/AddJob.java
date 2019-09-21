package edu.ung.hughs.jobscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AddJob extends AppCompatActivity {

    private static int personID;
    private static int boardID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        personID = intent.getIntExtra("personID", 0);
        personID = intent.getIntExtra("boardID", 0);
        

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: make this call DAO object to add job to board
                Snackbar.make(view, "Replace with DAO call", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
