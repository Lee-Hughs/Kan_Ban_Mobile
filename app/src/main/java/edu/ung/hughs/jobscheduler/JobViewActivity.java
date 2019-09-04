package edu.ung.hughs.jobscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JobViewActivity extends AppCompatActivity {

    private static String jobName;
    private static String jobDesc;
    private static String jobStatus;
    private static String jobCreatedBy;

    private int personID;
    private int boardID;

    private TextView nameView;
    private TextView descView;
    private TextView statusView;
    private TextView createdByView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_view);
        Intent intent = getIntent();

        //get info for on back pressed
        personID = intent.getIntExtra("personID", 0);
        boardID = intent.getIntExtra("boardID", 0);

        //Get Job details from intent
        jobName = intent.getStringExtra("jobName");
        jobDesc = intent.getStringExtra("jobDesc");
        jobStatus = intent.getStringExtra("jobStatus");
        jobCreatedBy = intent.getStringExtra("jobCreatedBy");

        //Assign textviews to their id's in layout file
        nameView = findViewById(R.id.NameField);
        descView = findViewById(R.id.DescField);
        statusView = findViewById(R.id.statusField);
        createdByView = findViewById(R.id.createdByField);

        //Set the textviews' value to the values pulled from the intent
        nameView.setText(jobName);
        descView.setText(jobDesc);
        statusView.setText(jobStatus);
        createdByView.setText(jobCreatedBy);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(JobViewActivity.this, BoardViewActivity.class);
        intent.putExtra("personID", personID);
        intent.putExtra("boardID", boardID);
        startActivity(intent);
    }
}
