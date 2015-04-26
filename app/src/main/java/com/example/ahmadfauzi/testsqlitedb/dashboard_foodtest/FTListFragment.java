package com.example.ahmadfauzi.testsqlitedb.dashboard_foodtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.ahmadfauzi.testsqlitedb.R;
import com.example.ahmadfauzi.testsqlitedb.model.FoodTest;

import java.util.ArrayList;

/**
 * Created by 5111100057 on 4/25/2015.
 */
public class FTListFragment extends ListFragment{
    private ArrayList<FoodTest> foodtestList;

    private Callbacks mCallback;

    public FTListFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        foodtestList = (ArrayList<FoodTest>) b.getSerializable("FTList");
        Log.d("FTListFragment","Data received : " + foodtestList.size());

        FTArrayAdapter adapter = new FTArrayAdapter(getActivity(), R.layout.ft_list_item, foodtestList);
        setListAdapter(adapter);
    }

    public interface Callbacks{
        public void onItemSelected(FoodTest foodTest);
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);

        this.mCallback = (Callbacks) activity;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        FoodTest foodTest = foodtestList.get(position);
        mCallback.onItemSelected(foodTest);
    }
}
