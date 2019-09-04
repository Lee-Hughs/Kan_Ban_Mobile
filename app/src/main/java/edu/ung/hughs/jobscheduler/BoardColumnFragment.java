package edu.ung.hughs.jobscheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class BoardColumnFragment extends Fragment implements MyItemRecyclerViewAdapter.OnJobListener {
    private int columnId;
    private String columnName;
    private int boardID;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Job> jobs;
    private int personID;

    public static BoardColumnFragment newInstance(int columnId, String columnName, int boardID, int personID)
    {
        BoardColumnFragment colFrag = new BoardColumnFragment();
        Bundle args = new Bundle();
        args.putInt("columnID", columnId);
        args.putString("columnName",columnName);
        args.putInt("boardID", boardID);
        args.putInt("personID", personID);
        colFrag.setArguments(args);
        Log.e("newInstance:" , args.toString());
        return colFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        columnId = getArguments().getInt("columnID");
        columnName = getArguments().getString("columnName");
        boardID = getArguments().getInt("boardID");
        personID = getArguments().getInt("personID");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        RecyclerView rv = view.findViewById(R.id.list);
        layoutManager = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(layoutManager);
        DAO dao = new DAO();
        jobs = dao.getJobListByBoard(boardID, columnName);
        rv.setAdapter(new MyItemRecyclerViewAdapter(jobs, this));
        return view;
    }

    @Override
    public void onJobClick(int position) {
        Toast.makeText(this.getContext(), "Number: " + Integer.toString(position), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this.getContext(), JobViewActivity.class);
        intent.putExtra("jobName",jobs.get(position).getName());
        intent.putExtra("jobDesc",jobs.get(position).getDesc());
        intent.putExtra("jobStatus",jobs.get(position).getStatus());
        intent.putExtra("jobCreatedBy", jobs.get(position).getCreatedBy());
        intent.putExtra("boardID", boardID);
        intent.putExtra("personID", personID);
        startActivity(intent);
        //Todo: go to new job activity
    }
}
