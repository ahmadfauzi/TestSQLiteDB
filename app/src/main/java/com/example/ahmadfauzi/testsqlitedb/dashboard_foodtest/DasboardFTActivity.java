package com.example.ahmadfauzi.testsqlitedb.dashboard_foodtest;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmadfauzi.testsqlitedb.R;
import com.example.ahmadfauzi.testsqlitedb.model.DatabaseConnector;
import com.example.ahmadfauzi.testsqlitedb.model.FoodTest;

import java.util.ArrayList;
import java.util.TooManyListenersException;

public class DasboardFTActivity extends ActionBarActivity implements FTListFragment.Callbacks{

    TextView foodTestId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard_ft);
    }

    @Override
    public void onResume(){
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        Log.d("DasboardFTActivity", "Refresh List");

        DatabaseConnector databaseConnector = new DatabaseConnector(this);
        ArrayList<FoodTest> foodtestList = (ArrayList<FoodTest>) databaseConnector.getFTList();

        FTListFragment fragment = new FTListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("FTList", foodtestList);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.dashboardMainActivityContainer, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dasboard_ft, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_add:
                addNewFT();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addNewFT() {
        Bundle bundle = null;
        Intent intent = new Intent(this, DetailFTActivity.class);
        intent.putExtra("packetFromDashboard", bundle);
        //intent.putExtra("foodtest_Id",0);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(FoodTest foodTest){
        Bundle bundle = foodTest.toBundle();

        Intent detailIntent = new Intent(this, DetailFTActivity.class);
        detailIntent.putExtra("packetFromDashboard", bundle);
        detailIntent.putExtra("FT_Id", foodTest.getIdFT());
        startActivity(detailIntent);

        Toast.makeText(this, "DashboardFTActivity, ID = " + String.valueOf(foodTest.getIdFT()), Toast.LENGTH_SHORT).show();
        Log.d("DashboardMainActivity", String.valueOf(foodTest.getIdFT()));
    }
}
