package edu.ung.hughs.jobscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class JobEdit extends AppCompatActivity {

    private static int jobID;
    private static int personID;
    private static int boardID;

    private String jobName;
    private String jobDesc;
    private String status;

    private Spinner statusOptions;
    private EditText nameEdit;
    private EditText descEdit;

    private TextView createdByView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        jobID = intent.getIntExtra("jobID", 0);
        personID = intent.getIntExtra("personID", 0);
        boardID = intent.getIntExtra("boardID", 0);

        createdByView = findViewById(R.id.createdByField);
        createdByView.setText(intent.getStringExtra("createdBy"));

        nameEdit = findViewById(R.id.NameField);
        nameEdit.setText(intent.getStringExtra("jobName"));
        descEdit = findViewById(R.id.DescField);
        descEdit.setText(intent.getStringExtra("jobDesc"));
        statusOptions = findViewById(R.id.statusSpinner);
        DAO dao = new DAO();
        List<String> statusList = dao.getStatusByBoard(boardID);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusList);

        statusOptions.setAdapter(dataAdapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobName = nameEdit.getText().toString();
                jobDesc = descEdit.getText().toString();
                status = statusOptions.getSelectedItem().toString();
                DAO dao = new DAO();
                if(dao.updateJob(jobID, jobName, jobDesc, status))
                {
                    Snackbar.make(view, "Job Updated!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent intent = new Intent(JobEdit.this, JobViewActivity.class);
                    intent.putExtra("personID", personID);
                    intent.putExtra("jobName", jobName);
                    intent.putExtra("jobDesc",jobDesc);
                    intent.putExtra("jobStatus", status);
                    intent.putExtra("jobID", jobID);

                    startActivity(intent);
                }

            }
        });
    }

}
