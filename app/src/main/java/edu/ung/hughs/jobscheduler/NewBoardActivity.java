package edu.ung.hughs.jobscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class NewBoardActivity extends AppCompatActivity {

    private EditText nameField, descField;
    private String name, desc;
    private int personID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_board);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameField = findViewById(R.id.groupName);
        descField = findViewById(R.id.description);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameField.getText().toString();
                desc = descField.getText().toString();
                personID = getIntent().getIntExtra("personID", 0);
                DAO dao = new DAO();
                dao.addBoard(name, desc, personID);
                Intent intent = new Intent(NewBoardActivity.this, BoardListActivity.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
