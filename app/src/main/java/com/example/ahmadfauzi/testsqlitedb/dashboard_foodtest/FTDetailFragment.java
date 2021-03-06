package com.example.ahmadfauzi.testsqlitedb.dashboard_foodtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ahmadfauzi.testsqlitedb.R;
import com.example.ahmadfauzi.testsqlitedb.model.FoodTest;
import com.example.ahmadfauzi.testsqlitedb.model.MySQLiteHelper;

/**
 * Created by 5111100057 on 4/25/2015.
 */
public class FTDetailFragment extends Fragment{
    FoodTest foodTest;
    EditText editTextName, editTextReagent, editTextResult;
    ImageView ivFoodTest;
    Button buttonTakePhoto;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        //bundle.containsKey(MySQLiteHelper.TABLE_NAME_FT);
        if(bundle != null ){
            foodTest = new FoodTest(bundle);
        }
        //Log.d("FTDetailFragment", "ID = " + String.valueOf(bundle.containsKey(MySQLiteHelper.TABLE_NAME_FT)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.detail_ft_fragment, container, false);

        editTextName = (EditText) view.findViewById(R.id.editTextName);
        editTextReagent = (EditText) view.findViewById(R.id.editTextReagent);
        editTextResult = (EditText) view.findViewById(R.id.editTextResult);
        ivFoodTest = (ImageView) view.findViewById(R.id.ivFoodTest);

        if(foodTest != null){
            editTextName.setText(foodTest.getNameFT());
            editTextReagent.setText(foodTest.getReagentFT());
            editTextResult.setText(foodTest.getResultFT());

            if(foodTest.getPhotoFT() == null){
                ivFoodTest.setImageResource(R.drawable.ft_photo);
            }else {
                Bitmap photoFT = BitmapFactory.decodeFile(foodTest.getPhotoFT());
                ivFoodTest.setImageBitmap(photoFT);
            }
        }
        return view;
    }
}
