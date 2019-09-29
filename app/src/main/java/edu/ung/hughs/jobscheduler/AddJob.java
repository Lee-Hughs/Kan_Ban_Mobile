package edu.ung.hughs.jobscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class AddJob extends AppCompatActivity {

    private static int personID;
    private static int boardID;

    private EditText jobNameInput;
    private EditText jobDescInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        personID = intent.getIntExtra("personID", 0);
        boardID = intent.getIntExtra("boardID", 0);

        jobNameInput = findViewById(R.id.NameField);
        jobDescInput = findViewById(R.id.DescField);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: do more extensive validation of form information
                if(jobDescInput.getText().toString().length() < 1 || jobNameInput.getText().toString().length() < 1)
                {
                    Snackbar.make(view, "Please fill out a name and description", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                DAO dao = new DAO();
                if(dao.addJob(boardID, dao.getStatusByBoard(boardID).get(0), jobNameInput.getText().toString(), jobDescInput.getText().toString(), personID))
                    Snackbar.make(view, "Job Added!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                else
                    Snackbar.make(view, "There was a problem adding that job", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                Intent intent = new Intent(AddJob.this, BoardViewActivity.class);
                intent.putExtra("personID", personID);
                intent.putExtra("boardID", boardID);
                startActivity(intent);
            }
        });
    }
}
