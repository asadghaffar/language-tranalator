package com.virtual.insurancebenefits;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtil
{

    static public final class Prefs
    {
        public static SharedPreferences get(Context context)
        {

            return context.getSharedPreferences("_dreamf_pref", 0);
        }
    }


    static public String getString(Context context, String key)
    {
        SharedPreferences settings = Prefs.get(context);
        return settings.getString(key, "");
    }

    static public String getString(Context context, String key, String defaultString)
    {
        SharedPreferences settings = Prefs.get(context);
        return settings.getString(key, defaultString);
    }

    static public synchronized void putString(Context context, String key, String value)
    {
        SharedPreferences settings = Prefs.get(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    static public synchronized void putBool(Context context, String key, boolean value)
    {
        SharedPreferences settings = Prefs.get(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    static public boolean getBool(Context context, String key, boolean defaultValue)
    {
        SharedPreferences settings = Prefs.get(context);
        return settings.getBoolean(key, defaultValue);
    }



    static public void clearPreferences(Context context)
    {
        SharedPreferences settings = Prefs.get(context);
        settings.edit()
                .clear()
                .apply();
    }

}