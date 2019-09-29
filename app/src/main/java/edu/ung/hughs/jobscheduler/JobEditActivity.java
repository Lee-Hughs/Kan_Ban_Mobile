package edu.ung.hughs.jobscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class JobEditActivity extends AppCompatActivity {

    private static int jobID;
    private static String jobName;
    private static String jobDesc;
    private static String jobStatus;
    private static String jobCreatedBy;

    private int personID;
    private int boardID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //todo: change this to new layout, exact same as job_view but with EditText instead of TextView
        setContentView(R.layout.activity_job_view);
        Intent intent = getIntent();

        jobID = intent.getIntExtra("jobID", 0);
        jobName = intent.getStringExtra("jobName");
        jobDesc = intent.getStringExtra("jobDesc");
        jobStatus = intent.getStringExtra("jobStatus");
        jobCreatedBy = intent.getStringExtra("jobCreatedBy");

        personID = intent.getIntExtra("personID", 0);
        boardID = intent.getIntExtra("boardID", 0);


    }
}
