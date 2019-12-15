package edu.ung.hughs.jobscheduler;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class JobViewActivity extends AppCompatActivity {

    private static String jobName;
    private static String jobDesc;
    private static String jobStatus;
    private static String jobCreatedBy;
    private static int jobID;

    private int personID;
    private int boardID;

    private TextView nameView;
    private TextView descView;
    private TextView statusView;
    private TextView createdByView;

    private FloatingActionButton remove;
    private FloatingActionButton edit;
    private FloatingActionButton promote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_view);
        Intent intent = getIntent();

        //get info for on back pressed
        personID = intent.getIntExtra("personID", 0);
        boardID = intent.getIntExtra("boardID", 0);

        //Get Job details from intent
        //todo: why the fuck did i do this, this should just be retrieved by DAO, why are you passing so god damn much information through intents
        jobName = intent.getStringExtra("jobName");
        jobDesc = intent.getStringExtra("jobDesc");
        jobStatus = intent.getStringExtra("jobStatus");
        jobCreatedBy = intent.getStringExtra("jobCreatedBy");
        jobID = intent.getIntExtra("jobID", 0);

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

        remove = findViewById(R.id.removeJobButton);
        edit = findViewById(R.id.editJobButton);
        promote = findViewById(R.id.promoteJobButton);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(jobID != 0)
                {
                    DAO dao = new DAO();
                    if(dao.removeJob(jobID))
                    {
                        Snackbar.make(view, "Job Removed!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();//toast and exit;
                        Intent intent = new Intent(JobViewActivity.this, BoardViewActivity.class);
                        intent.putExtra("personID", personID);
                        intent.putExtra("boardID", boardID);
                        startActivity(intent);
                    }
                    else
                        Snackbar.make(view, "Error Removing Job", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                }
                else
                    Snackbar.make(view, "Error Removing Job", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JobViewActivity.this, JobEdit.class);
                intent.putExtra("boardID", boardID);
                intent.putExtra("jobName", jobName);
                intent.putExtra("createdBy", jobCreatedBy);
                intent.putExtra("status", jobStatus);
                intent.putExtra("jobDesc", jobDesc);
                intent.putExtra("personID", personID);
                intent.putExtra("jobID", jobID);
                startActivity(intent);
            }
        });

        promote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
