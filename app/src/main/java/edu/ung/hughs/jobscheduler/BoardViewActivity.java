package edu.ung.hughs.jobscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class BoardViewActivity extends AppCompatActivity {

    private int boardID;
    private ListView todo,doing,done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_view);
        Intent intent = getIntent();
        boardID = intent.getIntExtra("boardID", 0);

        todo = findViewById(R.id.todo);
        doing = findViewById(R.id.doing);
        done = findViewById(R.id.done);

        populateListView(boardID, "todo", todo);
        populateListView(boardID, "doing", doing);
        populateListView(boardID, "done", done);

    }


    private void populateListView(int boardID, String status, ListView listView)
    {
        try {
            DAO dao = new DAO();
            ArrayAdapter<Job> adapter = new ArrayAdapter<Job>(this, android.R.layout.simple_list_item_1 ,android.R.id.text1, dao.getJobListByBoard(boardID, status));
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int jobID = ((Job) parent.getAdapter().getItem(position)).getJobID();
                    //todo:Create Job View activity and make the on item click point to it
                   // Intent intent = new Intent(BoardListActivity.this, BoardViewActivity.class);
                   // intent.putExtra("jobID",jobID);
                   // startActivity(intent);
                }
            });
        }
        catch(Exception e)
        {
            Log.e("Exception:", e.getMessage());
        }
    }
}
