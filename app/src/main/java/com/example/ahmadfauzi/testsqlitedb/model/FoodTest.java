package com.example.ahmadfauzi.testsqlitedb.model;

import android.os.Bundle;

import java.io.Serializable;

/**
 * Created by 5111100057 on 4/22/2015.
 */
public class FoodTest implements Serializable {

    public int idFT;
    public String nameFT;
    public String reagentFT;
    public String resultFT;

    public FoodTest(){
        //this.idFT = 0;
        this.nameFT = "";
        this.reagentFT = "";
        this.resultFT = "";
    }

    public FoodTest(int id, String name, String reagent, String result){
        this.idFT = id;
        this.nameFT = name;
        this.reagentFT = reagent;
        this.resultFT = result;
    }

    public FoodTest(Bundle b){
        if(b != null){
            this.idFT = b.getInt(MySQLiteHelper.COLUMN_ID_TABLE_FT);
            this.nameFT = b.getString(MySQLiteHelper.COLUMN_NAME_TABLE_FT);
            this.reagentFT = b.getString(MySQLiteHelper.COLUMN_REAGENT_TABLE_FT);
            this.resultFT = b.getString(MySQLiteHelper.COLUMN_RESULT_TABLE_FT);
        }
    }

    public int getIdFT() {
        return idFT;
    }

    public void setIdFT(int idFT) {
        this.idFT = idFT;
    }

    public String getNameFT() {
        return nameFT;
    }

    public void setNameFT(String nameFT) {
        this.nameFT = nameFT;
    }

    public String getReagentFT() {
        return reagentFT;
    }

    public void setReagentFT(String reagentFT) {
        this.reagentFT = reagentFT;
    }

    public String getResultFT() {
        return resultFT;
    }

    public void setResultFT(String resultFT) {
        this.resultFT = resultFT;
    }

    //================================================================================================

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;

        return  true;
    }

    //================================================================================================

    public Bundle toBundle(){
        Bundle b = new Bundle();
        b.putInt(MySQLiteHelper.COLUMN_ID_TABLE_FT, this.idFT);
        b.putString(MySQLiteHelper.COLUMN_NAME_TABLE_FT, this.nameFT);
        b.putString(MySQLiteHelper.COLUMN_REAGENT_TABLE_FT, this.reagentFT);
        b.putString(MySQLiteHelper.COLUMN_RESULT_TABLE_FT, this.resultFT);
        return b;
    }

    @Override
    public String toString(){
        return "FoodTest{" +
                "idFT='" + idFT + '\'' +
                "nameFT='" + nameFT + '\'' +
                "reagentFT='" + reagentFT + '\'' +
                "resultFT='" + resultFT + '\'' +
                '}';
    }
}
