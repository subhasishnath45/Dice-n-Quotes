package Util;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by SubhasishNath on 6/17/2018.
 */

public class Prefs {

//    Shared preferences are files on your
//    filesystem that you read and edit using
//    the SharedPreferences class.

    SharedPreferences sharedPreferences;

    public Prefs(Activity activity) {
        sharedPreferences = activity.getPreferences(activity.MODE_PRIVATE);

    }

    public void setSearch(String search) {
        sharedPreferences.edit().putString("search", search).commit();
    }

    public String getSearch() {
        return sharedPreferences.getString("search", "Batman");
    }
}
