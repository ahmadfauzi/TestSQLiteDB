package com.example.ahmadfauzi.testsqlitedb.dashboard_foodtest;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.ahmadfauzi.testsqlitedb.R;
import com.example.ahmadfauzi.testsqlitedb.model.DatabaseConnector;
import com.example.ahmadfauzi.testsqlitedb.model.FoodTest;

public class DetailFTActivity extends ActionBarActivity {
    boolean isUpdate = false;
    private int _idFT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ft);

        getSupportActionBar().setTitle("Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            FTDetailFragment fragment = new FTDetailFragment();
            Bundle bundle = getIntent().getBundleExtra("packetFromDashboard");

            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.detailFTContainer, fragment).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_ft, menu);
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
            case R.id.action_settings:
                break;
            case R.id.home:
                finish();
                break;
            case R.id.action_save:
                saveFT();
                finish();
                break;
            case R.id.action_delete:
                deleteFT();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveFT() {
        FoodTest foodTest = new FoodTest();

        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        foodTest.setNameFT(editTextName.getText().toString());

        EditText editTextReagent = (EditText) findViewById(R.id.editTextReagent);
        foodTest.setReagentFT(editTextReagent.getText().toString());

        EditText editTextResult = (EditText) findViewById(R.id.editTextResult);
        foodTest.setResultFT(editTextResult.getText().toString());

        foodTest.setIdFT(_idFT);

        DatabaseConnector databaseConnector = new DatabaseConnector(this);

        long statusInsert = -1;
        statusInsert = databaseConnector.insertFT(foodTest);
    }

    private void deleteFT() {

    }
}
