package Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.subhasishlive.recyclerviewexplanation.DetailsActivity;
import com.subhasishlive.recyclerviewexplanation.R;

import java.util.List;
import java.util.ArrayList;

import Model.ListItem;

/**
 * Created by SubhasishNath on 6/11/2018.
 */
// This is Adapter sub class...
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    // created a list, that is capable of holding our ListItem objects
    private List<ListItem> listitems;
    public MyAdapter(Context context, List listitem){
        this.context = context;
        this.listitems = listitem;

    }

    /*
    * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to
    * represent an item.This new ViewHolder should be constructed with a new View that can represent the items of the given type.
    * You can either create a new View manually or inflate it from an XML layout file.
    *
    * onCreateViewHolder creates necessery Viewholders
    * */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // onCreateViewHolder is Called when RecyclerView needs a new RecyclerView.ViewHolder
        // of the given type to represent an item.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);
        return new ViewHolder(view,this.context);
    }
    /*
    * binding ViewHolders to data from the model layer
    * */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // onBindViewHolder is Called by RecyclerView to display the data at the specified position.
        ListItem item = listitems.get(position);//get item from a specific position.
//        holder.name.setText(item.getName());
//        holder.description.setText(item.getDescription());
//        holder.rating.setText(item.getRating());
        String posterLink = item.getPostImg();

        holder.title.setText(item.getTitle());
        holder.year.setText("Tap to know more...");

        // using picasso image loading library to load images efficiently.
        Picasso.with(context).load(posterLink).placeholder(R.drawable.demoimg).into(holder.poster);

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    // This is ViewHolder sub class...
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * Think of this ViewHolder class as if our main activity class.
         * where we set up all our views of a single item of recyclerview.
         * ViewHolder does one thing: It holds on to a View.
         **/
//        public TextView name;
//        public TextView description;
//        public TextView rating;
        public TextView title;
        public ImageView poster;
        public TextView year;
        //public TextView type;

        public ViewHolder(View itemView,Context ctx){
            super(itemView);//holds a reference to the entire View
//            name = (TextView) itemView.findViewById(R.id.title);
//            description = (TextView) itemView.findViewById(R.id.description);
//            rating = (TextView) itemView.findViewById(R.id.rating);
            context = ctx;
            title = (TextView) itemView.findViewById(R.id.movieTitleID);
            poster = (ImageView) itemView.findViewById(R.id.movieImageID);
            year = (TextView) itemView.findViewById(R.id.movieReleaseID);
            //type = (TextView) itemView.findViewById(R.id.movieCatID);

            //itemView.setOnClickListener(this);// this is used as current context
            // Here itemView stands for a single view in the recyclerView.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context,"Item tapped !!!",Toast.LENGTH_SHORT).show();
                    // getAdapterPosition returns the Adapter position of the item represented by this ViewHolder.
                    int position = getAdapterPosition();
                    ListItem movie = listitems.get(position);
                    // tapping on a single item, we will go to DetailsActivity.java file...
                    Intent intent = new Intent(context, DetailsActivity.class);
                    // now we're able to get the whole movie object
                    // this name will be used in DetailsActivity.java while retrieving tha details
                    intent.putExtra("movie",movie);
                    context.startActivity(intent);
                }
            });
        }
        /*
        * this onClick() method will be implemented when the user will tap on any of
        * the recyclerView item.
        */
        @Override
        public void onClick(View v) {
            // first we need to get the position of the row, that was tapped.
//            int position = getAdapterPosition();
//            // then fetching the ListItem object of that position
//            ListItem item = listitems.get(position);
//            // then calling getName() method to return
//            // the name of the list item in that position is shown in the toast message...
//            //Toast.makeText(context,item.getName(),Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(context, DetailsActivity.class);
////            intent.putExtra("name",item.getName());
////            intent.putExtra("description",item.getDescription());
////            intent.putExtra("rating",item.getRating());
//
//            context.startActivity(intent);
        }
    }
}
