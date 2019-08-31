package edu.ung.hughs.jobscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JobViewActivity extends AppCompatActivity {

    private static String jobName;
    private static String jobDesc;

    private TextView nameView;
    private TextView descView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_view);
        Intent intent = getIntent();

        jobName = intent.getStringExtra("jobName");
        jobDesc = intent.getStringExtra("jobDesc");

        nameView = findViewById(R.id.NameField);
        descView = findViewById(R.id.DescField);

        nameView.setText(jobName);
        descView.setText(jobDesc);

    }
}
