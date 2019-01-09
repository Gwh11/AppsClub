package com.example.haoza.appsclub;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity的基类
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    public static class ActivityCollector{
        public static List<Activity> activities=new ArrayList<>();

        public static void addActivity(Activity activity){
            activities.add(activity);
        }
        public static void removeActivity(Activity activity){
            activities.remove(activity);
        }
    public static void finishAll(){
            for (Activity activity:activities){
                if(!activity.isFinishing()){
                    activity.finish();
                }
            }
            activities.clear();
        }
    }
}
