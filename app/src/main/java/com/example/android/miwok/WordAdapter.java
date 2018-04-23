package com.example.android.miwok;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by ARINZE on 2016-08-15.
 */

/*
+* {@link AndroidFlavorAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
+* based on a data source, which is a list of {@link AndroidFlavor} objects.
+* */
// we extends the new array adapter class from the word apter class to show the new customer class wordadapter inherits behaviour from the arraydapter class.
public class WordAdapter extends ArrayAdapter<Word> {


    /** Resource ID for the background color for this list of words */
    private int mColorResourceId;



    /**
     +     * This custom constructor (it doesn't mirror a superclass constructor).
     +     * The context is used to inflate the layout file, and the list is the data we want
     +     * to populate into the lists.
     +     *
     +     * @param context        The current context. Used to inflate the layout file.
     +     * @param Word A List of word objects to display in a list
     +     * @param colorResourceId is the resource ID for the background color for this list of words
     +     */


    public WordAdapter(Activity context, ArrayList<Word> words, int colorResourceId) {

        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.

        super(context, 0, words);
        mColorResourceId = colorResourceId;
    }


    /**
     +     * Provides a view for an AdapterView (ListView, GridView, etc.)
     +     *
     +     * @param position The position in the list of data that should be displayed in the
     +     *                 list item view.
     +     * @param convertView The recycled view to populate.
     +     * @param parent The parent ViewGroup that is used for inflation.
     +     * @return The View for the position in the AdapterView.
     +     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view (manually create a view to be reused)
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate( R.layout.list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        Word currentWord = getItem(position);


        // Find the TextView in the list_item.xml layout with the ID default_text_view
        TextView defaultWordTextView = (TextView) listItemView.findViewById(R.id.default_text_view);

        // Get the defaultTranslation from the current word object and
        // set this text on the defaultWord TextView
        defaultWordTextView.setText(currentWord.getDefaultTranslation());


        // Find the TextView in the list_item.xml layout with the ID miwok_text_view
        TextView miwokNameTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);

        // Get the miwokTranslation from the current Word object and
        // set this text on the miwokName TextView
        miwokNameTextView.setText(currentWord.getMiwokTranslation());



        // Find the imageView in the list_item.xml layout with the ID image_equivalent
        ImageView imageEquivalent = (ImageView) listItemView.findViewById(R.id.image_equivalent);

            //Use the hasImage() boolean method from the word.java class to create an if else state for whether there is an image or not
        if (currentWord.hasImage()) {
            // If yes set imageView to the image resource id specified in the current word
            imageEquivalent.setImageResource(currentWord.getImage());
            //Make sure the image is visible
            imageEquivalent.setVisibility(View.VISIBLE);
        }
        else{
            //Otherwise hide the imageView (set visibility to GONE)
            imageEquivalent.setVisibility(View.GONE);
        }



        // Set the theme color for the list item
        //find the id text_container from the linear layout of the list_item xml
        View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);




        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
