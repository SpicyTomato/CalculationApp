package com.spicytomato.summary_test;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import java.util.Random;

import javax.crypto.KeyAgreement;

public class MyViewModel extends AndroidViewModel {
    private SavedStateHandle handle;
    private static String KEY_HIGH_SCORE = "key_high_score";
    private static String KEY_FIRST_NUMBER = "key_first_number";
    private static String KEY_OPERATE = "key_operate";
    private static String KEY_SECOND_NUMBER = "key_second_number";
    private static String KEY_ANSWER = "key_answer";
    private static String KEY_SHP_SAVED_DATA = "key_shp_saved_data";
    private static String KEY_CURRENT_DATA = "key_current_data";
    boolean win_flag = false;

    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);

        //要保存下来的是最高纪录 保存到SharedPreferences
        if(!handle.contains(KEY_HIGH_SCORE)){
            SharedPreferences sharedPreferences = getApplication().getSharedPreferences(KEY_SHP_SAVED_DATA, Context.MODE_PRIVATE);
            handle.set(KEY_HIGH_SCORE,sharedPreferences.getInt(KEY_HIGH_SCORE,0));
            handle.set(KEY_FIRST_NUMBER,0);
            handle.set(KEY_OPERATE,"+");
            handle.set(KEY_SECOND_NUMBER,0);
            handle.set(KEY_ANSWER,0);
            handle.set(KEY_CURRENT_DATA,0);

        }

        this.handle = handle;
    }

    public MutableLiveData<Integer> getFirstNumber(){
        return handle.getLiveData(KEY_FIRST_NUMBER);
    }

    public MutableLiveData<String> getOperate(){
        return handle.getLiveData(KEY_OPERATE);
    }

    public MutableLiveData<Integer> getSecondNumber(){
        return handle.getLiveData(KEY_SECOND_NUMBER);
    }

    public MutableLiveData<Integer> getAnswer(){
        return handle.getLiveData(KEY_ANSWER);
    }

    public MutableLiveData<Integer> getHighScore(){
        return handle.getLiveData(KEY_HIGH_SCORE);
    }

    public MutableLiveData<Integer> getCurrentScore(){
        return handle.getLiveData(KEY_CURRENT_DATA);
    }

    public void generateEquation(){
        int level = 20;
        Random random = new Random();

        int firstNumber = random.nextInt(level);
        int secondNumber = random.nextInt(level);
        int operateSign = random.nextInt(2);

        if(operateSign % 2 == 0){
            getOperate().setValue("+");
            getFirstNumber().setValue(firstNumber);
            getSecondNumber().setValue(secondNumber);
            getAnswer().setValue(firstNumber + secondNumber);

        }else{
            getOperate().setValue("-");
            getFirstNumber().setValue(firstNumber);
            getSecondNumber().setValue(secondNumber);
            getAnswer().setValue(firstNumber - secondNumber);
        }
    }

    public void save(){
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(KEY_HIGH_SCORE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_HIGH_SCORE,getHighScore().getValue());
        editor.apply();

    }

    public void answerCorrect(){
        getCurrentScore().setValue(getCurrentScore().getValue() + 1);
        if(getCurrentScore().getValue() > getHighScore().getValue()){
            getHighScore().setValue(getCurrentScore().getValue());
            win_flag = true;
        }
        generateEquation();
    }

}
