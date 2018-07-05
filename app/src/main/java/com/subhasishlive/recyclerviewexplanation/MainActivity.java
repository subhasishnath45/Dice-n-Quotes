package com.subhasishlive.recyclerviewexplanation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Adapter.MyAdapter;
import Model.ListItem;
import Util.Constants;
import Util.Prefs;

/**
 * Created by SubhasishNath on 6/11/2018.
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "getting length" ;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<ListItem> listItems;// listitems are movies...
    private RequestQueue queue;// to use volleys
    public int intValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // we are adding our queue and passing our current context....
            queue = Volley.newRequestQueue(MainActivity.this);

        // TODO: A FAB button would be added to call an alert dialog for search...


        // now we will set up our recyclerview and setup the adapter.
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // creating an instance of Prefs class, calling parameterized constructor
        // passing our mainActivity as parameter...
        //Prefs prefs = new Prefs(MainActivity.this);
        // calling getSearch() method
        //String search = prefs.getSearch();

        // ArrayLists are capable of holding any type of lists
        listItems = new ArrayList<>();

        // listItems is set to the returning value of getMovies(), which is a List<ListItem>
        // so listItems holds all the movies returned by the search parameter.
        listItems = getMovies();
        Intent mIntent = getIntent();
        intValue = mIntent.getIntExtra("addedRandomNo",0);
        adapter = new MyAdapter(MainActivity.this,listItems);// creating a new adapter for our listItems.
        recyclerView.setAdapter(adapter);//setting our adapter for our recyclerView.
        adapter.notifyDataSetChanged(); // Notify any registered observers that the data set has changed.
    }
    @Override
    public void onBackPressed() {

        // We implements here our logic
        createDialog();
    }
    private void createDialog() {

        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);



        alertDlg.setMessage("Are you sure you want to exit?");

        alertDlg.setCancelable(false); // We avoid that the dialong can be cancelled, forcing the user to choose one of the options



        alertDlg.setPositiveButton("Yes", new DialogInterface.OnClickListener() {



                    public void onClick(DialogInterface dialog, int id) {

                        MainActivity.super.onBackPressed();

                    }

                }

        );

        alertDlg.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {

                // We do nothing

            }

        });

        alertDlg.create().show();
    }
    // creating a method to get movies...
    public List<ListItem> getMovies(){
        listItems.clear(); // first we're clearing the movie list....


        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, Constants.base_url + "wp/v2/AllQuotes?_embed&per_page=100",new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // TODO:response holds the whole JSON object from the URL
                try{
                    Log.d(TAG, response.toString() + "Size: "+response.length());
                    // instantiated a new JSON array.
                    // Pass the name of the JSON array as parameter.
                    // Search is name of the JSON array from the JSON file
                    //JSONArray moviesArray = response.getJSONArray("Search");
                    // Now I'm iterating through the array by a for loop
                    // every index-element in that array contains a JSON object.
                    Random randomNumberGenerator = new Random();
                    // This gives a random integer between 2 (inclusive) and 88 (exclusive), one of 65,66,...,78,79
                    int number1 = randomNumberGenerator.nextInt(22 - 2) + 2;
                    for(int i=number1 - 2;i< number1 + intValue;i++){
                        // instanciating JSONObject variable to pick a Index item(which is a JSON object) from the JSONArray.
                        //JSONObject movieObj =  moviesArray.getJSONObject(i);
                        ListItem movie = new ListItem();// creating ListItem object.
                        Log.d(TAG,"Object at " + i+ response.get(i));
                        // getting the JSONObject at i-th position of the JSONArray
                        JSONObject obj = response.getJSONObject(i);
                        // fetching id of each JSONObject from JSONArray.
                        movie.setId(obj.getInt("id"));

                        // title itself is an object so first I'm retrieving that
                        JSONObject titleObj=obj.getJSONObject("title");
                        // using title object I'm retrieving the string in it.
                        movie.setTitle(titleObj.getString("rendered"));

                        // content itself is an object so first I'm retriving that.
                        JSONObject contentObj = obj.getJSONObject("content");
                        // setting up the content
                        movie.setContent(contentObj.getString("rendered"));

                        // getting image object and setting up image.
                        JSONObject imgObj = obj.getJSONObject("better_featured_image");
                        movie.setPostImg(imgObj.getString("source_url"));


                        // getting excerpt object and setting up excerpt.
                        JSONObject excerptObj = obj.getJSONObject("excerpt");
                        movie.setPostExcerpt(excerptObj.getString("rendered"));


                        // adding the newly created movie to listItems list...
                        listItems.add(movie);
                    }
                    adapter.notifyDataSetChanged();

                }catch(JSONException e){
                    e.printStackTrace(); // throwing an exception
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error

            }
        });

        queue.add(getRequest);
        return listItems;

    }
}
