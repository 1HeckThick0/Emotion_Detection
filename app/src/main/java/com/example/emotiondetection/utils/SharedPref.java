package com.example.emotiondetection.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.emotiondetection.models.UserSession;
import com.google.gson.Gson;

public class SharedPref {

    private static final String PREF_NAME_USER = "UserPreferences";
    private static final String KEY_IS_AUTHENTICATED = "isAuthenticated";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME_USER, Context.MODE_PRIVATE);
    }

    public static void saveUserSession(Context context, UserSession userModel) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        Gson gson = new Gson();
        String userJson = gson.toJson(userModel);
        editor.putString("user", userJson);
        editor.apply();
    }

    public static UserSession getUserSession(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        String userJson = sharedPreferences.getString("user", "");

        if (!userJson.isEmpty()) {
            Gson gson = new Gson();
            return gson.fromJson(userJson, UserSession.class);
        } else {
            // Return a default UserModel or handle the absence of data as needed
            return null;
        }
    }

    public static void setIsAuthenticated(Context context, boolean isAuthenticated) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(KEY_IS_AUTHENTICATED, isAuthenticated);
        editor.apply();
    }

    // Method to retrieve isAuthenticated from SharedPreferences
    public static boolean getIsAuthenticated(Context context) {
        return getSharedPreferences(context).getBoolean(KEY_IS_AUTHENTICATED, false);
    }

    public static void clearAllPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
