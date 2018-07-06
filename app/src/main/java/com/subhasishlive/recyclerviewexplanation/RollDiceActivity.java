package com.subhasishlive.recyclerviewexplanation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Random;

public class RollDiceActivity extends AppCompatActivity {
    private Button rollBtn;
    private ImageView leftDice;
    private ImageView rightDice;
    //private TextView noOfQuotes;
    private Button viewQuotesBtn;
    private FloatingActionButton myFab;
    public int addedRandomNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roll_dice);

        isConnected(this);


        rollBtn = (Button) findViewById(R.id.rollBtn);
        leftDice = (ImageView) findViewById(R.id.image_leftdice);
        rightDice = (ImageView) findViewById(R.id.image_rightdice);
        //noOfQuotes = (TextView) findViewById(R.id.textNoOfQuotes);
        //viewQuotesBtn = (Button) findViewById(R.id.viewQuotesBtn);
        myFab = (FloatingActionButton)  findViewById(R.id.fabgo);
        myFab.setVisibility(View.GONE);

        final int[] diceArray = {R.drawable.dice1,
                R.drawable.dice2,
                R.drawable.dice3,
                R.drawable.dice4,
                R.drawable.dice5,
                R.drawable.dice6};
        rollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DiceMe","Hello there");

                Random randomNumberGenerator = new Random();
                int number1 = randomNumberGenerator.nextInt(6);
                int number2 = randomNumberGenerator.nextInt(6);

                //Log.d("DiceMe","The Random number is: "+ number1 );

                leftDice.setImageResource(diceArray[number1]);
                rightDice.setImageResource(diceArray[number2]);
                addedRandomNo = number1 + number2 + 2;
                Snackbar.make(v, Html.fromHtml("You can see " + "<font color=\"#ef5350\">"+ addedRandomNo  + " quotes."+"</font>"),Snackbar.LENGTH_SHORT).setAction("Action",null).show();
                //noOfQuotes.setText("You can see " + addedRandomNo + " quotes.");

                if(addedRandomNo > 1){
                    myFab.setVisibility(View.VISIBLE);
                }

            }
        });

        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RollDiceActivity.this,MainActivity.class);
                intent.putExtra("addedRandomNo", addedRandomNo);
                startActivity(intent);
                RollDiceActivity.this.finish();
            }
        });

        String imageUri = "http://subhasishlive.com/wp-content/uploads/2018/07/ic_splashlogo.png";
        ImageView logoDC = (ImageView) findViewById(R.id.image_logo);
        Picasso.with(this).load(imageUri).into(logoDC);




    }

    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected())) {
            return true;
        } else {
            showDialog();
            return false;
        }
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Connect to wifi or quit")
                .setCancelable(false)
                .setPositiveButton("Connect to WIFI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RollDiceActivity.this.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
