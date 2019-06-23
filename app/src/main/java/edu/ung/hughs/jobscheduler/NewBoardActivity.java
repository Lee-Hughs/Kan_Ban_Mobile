package edu.ung.hughs.jobscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class NewBoardActivity extends AppCompatActivity {

    private EditText nameField, descField;
    private String name, desc;
    private int personID;
    private ArrayList<String> statusList;
    private static int totalStatus = 0;
    private Button addStatus, removeStatus;
    private LinearLayout containerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_board);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameField = findViewById(R.id.groupName);
        descField = findViewById(R.id.description);
        addStatus = findViewById(R.id.addStatusButton);
        removeStatus = findViewById(R.id.removeStatusButton);
        containerLayout = (LinearLayout) findViewById(R.id.linLayout);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameField.getText().toString();
                desc = descField.getText().toString();
                personID = getIntent().getIntExtra("personID", 0);
                populatStatusList();
                if(statusList.size() < 1)
                    return; //todo:make a toast saying it requires status
                DAO dao = new DAO();
                dao.addBoard(name, desc, personID, statusList);
                Intent intent = new Intent(NewBoardActivity.this, BoardListActivity.class);
                intent.putExtra("personID", personID);
                startActivity(intent);
            }
        });

        addStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalStatus > 20)
                    return;
                totalStatus++;
                EditText editText = new EditText(getParent().getApplicationContext());
                containerLayout.addView(editText);
                editText.setGravity(Gravity.CENTER);
                editText.setId(totalStatus);
            }
        });

        removeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalStatus < 1)
                    return;
                containerLayout.removeView(findViewById(totalStatus));
                totalStatus--;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void populatStatusList()
    {
        statusList.clear();
        for(int i = 1; i < totalStatus; i++)
        {
            EditText s = findViewById(totalStatus);
            statusList.add(s.getText().toString());
        }
    }
}
