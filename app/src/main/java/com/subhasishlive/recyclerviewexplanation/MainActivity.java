package com.subhasishlive.recyclerviewexplanation;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "getting length" ;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<ListItem> listItems;// listitems are movies...
    private RequestQueue queue;// to use volleys
    private DrawerLayout mDrawerlayout;
    private ProgressBar progressbar;
    public int intValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.onlytoolbar);
        setSupportActionBar(toolbar);

        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // instantiating new drawerToggle
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,mDrawerlayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        mDrawerlayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        drawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        View hView =  navigationView.getHeaderView(0);

        String imageUri = "http://subhasishlive.com/wp-content/uploads/2018/07/profileApp.png";
        ImageView devImg = (ImageView) hView.findViewById(R.id.imageViewDev);
        Picasso.with(this).load(imageUri).into(devImg);
        CardView cardView = (CardView) findViewById(R.id.cardviewWrapper);
        //cardView.setCardElevation(0);
        //cardView.setPreventCornerOverlap(false); //it is very important
        // we are adding our queue and passing our current context....
            queue = Volley.newRequestQueue(MainActivity.this);

        progressbar = (ProgressBar) findViewById(R.id.progressbar);

        // now we will set up our recyclerview and setup the adapter.
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
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


        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, Constants.base_url + "wp/v2/AllQuotes?_embed&per_page=70",new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // TODO:response holds the whole JSON object from the URL
                try{
                    //Log.d(TAG, response.toString() + "Size: "+response.length());
                    // instantiated a new JSON array.
                    // Pass the name of the JSON array as parameter.
                    // Search is name of the JSON array from the JSON file
                    //JSONArray moviesArray = response.getJSONArray("Search");
                    // Now I'm iterating through the array by a for loop
                    // every index-element in that array contains a JSON object.
                    if(response.length()>0){
                        progressbar.setVisibility(ProgressBar.GONE);
                    }
                    Random randomNumberGenerator = new Random();
                    // This gives a random integer between 2 (inclusive) and 88 (exclusive), one of 65,66,...,78,79
                    int number1 = randomNumberGenerator.nextInt(58);// 0 to 22 any no will generate
                    for(int i=number1;i< number1 + intValue;i++){
                        // instanciating JSONObject variable to pick a Index item(which is a JSON object) from the JSONArray.
                        //JSONObject movieObj =  moviesArray.getJSONObject(i);
                        ListItem movie = new ListItem();// creating ListItem object.
                        //Log.d(TAG,"Object at " + i+ response.get(i));
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.website: {
                //do somthing
                Uri uri = Uri.parse( "http://subhasishlive.com" );
                startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
                break;
            }
            case R.id.blog: {
                //do somthing
                Uri uri = Uri.parse( "http://subhasishlive.com/blog" );
                startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
                break;
            }
            case R.id.disclaimer: {
                //do somthing
                Intent i = new Intent(MainActivity.this, Disclaimer.class);
                startActivity(i);
                break;
            }
        }
        //close navigation drawer
        hideDrawer();
        return true;
    }
    private void hideDrawer(){
        mDrawerlayout.closeDrawer(GravityCompat.START);
    }
}
