package edu.ung.hughs.jobscheduler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class BoardColumnFragment extends Fragment{
    private int columnId;
    private String columnName;
    private int boardID;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public static BoardColumnFragment newInstance(int columnId, String columnName, int boardID)
    {
        BoardColumnFragment colFrag = new BoardColumnFragment();
        Bundle args = new Bundle();
        args.putInt("columnID", columnId);
        args.putString("columnName",columnName);
        args.putInt("boardID", boardID);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        RecyclerView rv = view.findViewById(R.id.list);
        layoutManager = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(layoutManager);
        DAO dao = new DAO();
        ArrayList<Job> jobs = dao.getJobListByBoard(boardID, columnName);
        rv.setAdapter(new MyItemRecyclerViewAdapter(jobs));
        return view;
    }
}
