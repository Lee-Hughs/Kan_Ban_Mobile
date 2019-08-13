package edu.ung.hughs.jobscheduler;

import android.app.Activity;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BoardListActivity extends AppCompatActivity {

    private int personID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_list);
        Intent intent = getIntent();
        personID = intent.getIntExtra("personID", 0);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        try {
            getSupportActionBar().setTitle("My Boards");
        }
        catch(NullPointerException e)
        {
            Log.e("Null pointer error: ", e.getMessage());
        }
        populateListView();

    }

    private void populateListView()
    {               //This may or may not work, who fucking knows
        try {
            DAO dao = new DAO();
            ArrayAdapter<Board> adapter = new ArrayAdapter<Board>(this, android.R.layout.simple_list_item_1 ,android.R.id.text1, dao.getBoardList(personID));
            ListView listView = findViewById(R.id.boardList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int boardID = ((Board) parent.getAdapter().getItem(position)).getBoardID();
                    Intent intent = new Intent(BoardListActivity.this, BoardViewActivity.class);
                    intent.putExtra("boardID",boardID);
                    intent.putExtra("personID", personID);
                    startActivity(intent);
                }
            });
        }
        catch(Exception e)
        {
            Log.e("Exception:", e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_boards_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.new_board) {
            Intent intent = new Intent(BoardListActivity.this, NewBoardActivity.class);
            intent.putExtra("personID", personID);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
