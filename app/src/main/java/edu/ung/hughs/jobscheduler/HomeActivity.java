package edu.ung.hughs.jobscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {

    //initiate view elements
    TextView titleText;
    Button myJobsBtn;
    Button myBoardsBtn;
    Button myProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.home_view);

        //assigning view elements
        titleText = (TextView) findViewById(R.id.TitleText);

        myJobsBtn = (Button) findViewById(R.id.myJobsButton);
        myBoardsBtn = (Button) findViewById(R.id.myBoardsButton);
        myProfileBtn = (Button) findViewById(R.id.myProfileButton);

        final int personID = intent.getIntExtra("personID", 0);
        titleText.setText("ID: " + personID);


        myJobsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo:create jobs activity
            }
        });

        myBoardsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBoards = new Intent(HomeActivity.this, BoardListActivity.class);
                intentBoards.putExtra("personID", personID);
                startActivity(intentBoards);
            }
        });

        myProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo:create profile Activity
            }
        });



    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(HomeActivity.this, LogInActivity.class);
        startActivity(intent);
    }
}
