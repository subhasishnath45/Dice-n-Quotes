package com.subhasishlive.recyclerviewexplanation;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PrivateKey;

import Model.ListItem;
import Util.Constants;

public class DetailsActivity extends AppCompatActivity {
    private ListItem movie;
    private CollapsingToolbarLayout quoteDetailTitle;
    private ImageView quoteDetailImage;
    private TextView quoteData;
    private TextView quoteBy;
//    private TextView actors;
//    private TextView category;
//    private TextView rating;
//    private TextView writers;
//    private TextView plot;
//    private TextView boxOffice;
//    private TextView runtime;

    private RequestQueue queue;
    // Based on the movieId we will get the details of the movies.
    private long movieId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //*****must instantiate our queue*****
        queue = Volley.newRequestQueue(this);

        // intent.putExtra("movie",movie); the movie was used as name in MyAdapter.java...
        movie = (ListItem) getIntent().getSerializableExtra("movie");
        movieId = movie.getId();// fetched and stored the movie id.

        // setting toolbar text
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetails);
        toolbar.setTitle("Details Toolbar");

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.shareFabBtn);
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareURL();
            }
        });

        // setting CollapsingToolbar text
        //CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        //collapsingToolbarLayout.setTitle("Collapsing Toolbar");

        // called two private methods
        setUpUI();
        getMovieDetails(movieId);
    }
    private void shareURL() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, quoteData.getText());
        startActivity(Intent.createChooser(shareIntent, "Share This Quote :)"));
    }
    private void setUpUI() {

            quoteDetailTitle = findViewById(R.id.collapsingToolbar);
            quoteDetailImage = (ImageView) findViewById(R.id.detailsImg);
            quoteData = (TextView) findViewById(R.id.details_Quote_data);
            quoteBy = (TextView) findViewById(R.id.quote_by);
//        director = (TextView) findViewById(R.id.directedByDet);
//        category = (TextView) findViewById(R.id.movieCatIDDet);
//        rating = (TextView) findViewById(R.id.movieRatingIDDet);
//        writers = (TextView) findViewById(R.id.writersDet);
//        plot = (TextView) findViewById(R.id.plotDet);
//        boxOffice = (TextView) findViewById(R.id.boxOfficeDet);
//        runtime = (TextView) findViewById(R.id.runtimeDet);
//        actors = (TextView) findViewById(R.id.actorsDet);
    }

    private void getMovieDetails(Long id) {
        // JsonObjectRequest and JsonArrayRequest (both subclasses of JsonRequest).
        // Specify a URL and get a JSON object or array (respectively) in response.


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.base_url + "wp/v2/AllQuotes/" + id ,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try{
                    // Checking if our response object has "Ratings" key available...
                    if(response.has("id")){// TODO: might cause problem
                        // retrieving the whole Rating array, which has 2 objects...
//                        JSONArray ratings = response.getJSONArray("Ratings");
//                        String source = null;
//                        String value = null;

//                        if (ratings.length() > 0) {
//                            // retrieving all objects from inside ratings array...
//                            JSONObject mRatings = ratings.getJSONObject(ratings.length() - 1);
//                            source = mRatings.getString("Source");// retriving the object with "Source" key
//                            value = mRatings.getString("Value");// retriving the object with "Value" key
//                            rating.setText(source + " : " + value);
//
//                        }else {
//                            rating.setText("Ratings: N/A");
//                        }

                        // first getting the title object, from inside response JSONObject.
                        // the getting the "rendered" string and setting it as title of quoteTitle.
                        JSONObject detailstitleObj =response.getJSONObject("title");
                        quoteDetailTitle.setTitle(detailstitleObj.getString("rendered"));

                        // getting the img object and then setting up that image into ImageView.
                        JSONObject detailsimageObj = response.getJSONObject("better_featured_image");
                        Picasso.with(getApplicationContext())
                                .load(detailsimageObj.getString("source_url"))
                                .into(quoteDetailImage);

                        JSONObject detailsContentObj = response.getJSONObject("content");
                        String quoteDetailsString = detailsContentObj.getString("rendered");
                        String quoteDetailsStringTagExcluded = quoteDetailsString.replaceAll("\\<.*?\\>", "");
                        quoteData.setText("" + quoteDetailsStringTagExcluded);

                        JSONObject detailQuoteByObj = response.getJSONObject("excerpt");
                        String detailQuoteByString = detailQuoteByObj.getString("rendered");
                        String detailQuoteByStringTagExcluded = detailQuoteByString.replaceAll("\\<.*?\\>", "");
                        quoteBy.setText("By: " + detailQuoteByStringTagExcluded);
//                        writers.setText("Writers: " + response.getString("Writer"));
//                        plot.setText("Plot: " + response.getString("Plot"));
//                        runtime.setText("Runtime: " + response.getString("Runtime"));
//                        actors.setText("Actors: " + response.getString("Actors"));
//
//                        boxOffice.setText("Box Office: " + response.getString("BoxOffice"));

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                VolleyLog.d("Error: ",error.getMessage());
            }
        });
        queue.add(jsonObjectRequest);
    }

}
