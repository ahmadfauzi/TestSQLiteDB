package com.example.ahmadfauzi.testsqlitedb.dashboard_foodtest;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewFragment;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmadfauzi.testsqlitedb.R;
import com.example.ahmadfauzi.testsqlitedb.model.FoodTest;

import java.util.List;

/**
 * Created by 5111100057 on 4/25/2015.
 */
public class FTArrayAdapter extends ArrayAdapter<FoodTest> {
    private Context context;
    private List<FoodTest> foodtestList;

    public FTArrayAdapter(Context context, int resource, List<FoodTest> objects){
        super(context, resource, objects);
        this.context = context;
        this.foodtestList = objects;
    }

    @Override
    public View getView(int position, View ConvertView, ViewGroup parent){
        FoodTest foodTest = foodtestList.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ft_list_item, null);

        ImageView image = (ImageView) view.findViewById(R.id.list_item_photo);
        if(foodTest.getPhotoFT() == null){
            image.setImageResource(R.drawable.ft_photo);
        }else{
            image.setImageBitmap(BitmapFactory.decodeFile(foodTest.getPhotoFT()));
        }

        TextView idLabel = (TextView) view.findViewById(R.id.list_item_id);
        idLabel.setText(String.valueOf(foodTest.getIdFT()));
        Log.d("FTArrayAdapter", "ID = " + String.valueOf(foodTest.getIdFT()));

        TextView nameLabel = (TextView) view.findViewById(R.id.list_item_name);
        if(!foodTest.getNameFT().isEmpty()){
            //nameLabel.setText("Food Name : -");
            nameLabel.setText("Food Name : " + foodTest.getNameFT());
        }

        TextView reagentLabel = (TextView) view.findViewById(R.id.list_item_reagent);
        if(!foodTest.getReagentFT().isEmpty()){
            //reagentLabel.setText("Reagent : -");
            reagentLabel.setText("Reagent : " + foodTest.getReagentFT());
        }

        TextView resultLabel = (TextView) view.findViewById(R.id.list_item_result);
        if(!foodTest.getResultFT().isEmpty()){
            //resultLabel.setText("Result : - ");
            resultLabel.setText("Result : " + foodTest.getResultFT());
        }
        return view;
    }
}
