package com.studis.nife.k_now;


import android.content.Context;
import android.content.SharedPreferences;

public class MemoryManager {
    public String load(String speicherort, String speicherstelle, Context context) {
       SharedPreferences pref = context.getSharedPreferences(speicherort, 0);
       return pref.getString(speicherstelle, "");


    }

    public void save(String speicherort,String speicherstelle, String text, Context context) {
        SharedPreferences pref = context.getSharedPreferences(speicherort, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(speicherstelle, text);
        editor.commit();
    }
}
