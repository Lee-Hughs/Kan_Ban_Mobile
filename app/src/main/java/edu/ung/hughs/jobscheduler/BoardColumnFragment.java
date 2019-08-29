package edu.ung.hughs.jobscheduler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BoardColumnFragment extends Fragment {
    private int columnId;
    private String columnName;

    public static BoardColumnFragment newInstance(int columnId, String columnName)
    {
        BoardColumnFragment colFrag = new BoardColumnFragment();
        Bundle args = new Bundle();
        args.putInt("columnID", columnId);
        args.putString("columnName",columnName);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        return view;
    }
}
