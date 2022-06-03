package com.openclassrooms.reunion.ui.reunion_list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.reunion.R;
import com.openclassrooms.reunion.di.DI;
import com.openclassrooms.reunion.events.DeleteReunionEvent;
import com.openclassrooms.reunion.model.Reunion;
import com.openclassrooms.reunion.service.ReunionApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class ReunionFragment extends Fragment {

    private ReunionApiService mApiService;
    private List<Reunion> mReunion;
    private RecyclerView mRecyclerView;


    /**
     * Create and return a new instance
     * @return @{@link ReunionFragment}
     */
    public static ReunionFragment newInstance() {
        ReunionFragment fragment = new ReunionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getReunionApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reunion_list, container, false);
        Context context = view.getContext();
        mRecyclerView = view.findViewById(R.id.list_reunion);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        return view;
    }

    /**
     * Init the List of Reunion
     */
    private void initList() {
        mReunion = mApiService.getReunion();
        mRecyclerView.setAdapter(new MyReunionRecyclerViewAdapter(mReunion));
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired if the user clicks on a delete button
     * @param event
     */
    @Subscribe
    public void onDeleteReunion(DeleteReunionEvent event) {
        mApiService.deleteReunion(event.reunion);
        initList();
    }
}
