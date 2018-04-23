package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    /*
    Listener gets triggered when the MediaPlayer has finished playing the audio files.
    So as to call the release method to save device memory
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener (){
        @Override
        public void onCompletion (MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {

                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                //after pause the audio file should resume from the beginning hence seekTo 0
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {

                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // We use the word custom class in the ArrayList instead of Strings data type
        // because we already created a word class in thw word.java file to enable us have up to two views in a list view
        //instead of individual strings
        final ArrayList<Word> words = new ArrayList<Word>();

        //we call the add method on the words object to add the default translation and miwok translation from teh word.java file (word custom class)
        words.add(new Word ("father","әpә",R.drawable.family_father,R.raw.family_father));
        words.add(new Word ("mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        words.add(new Word ("son","angsi",R.drawable.family_son,R.raw.family_son));
        words.add(new Word ("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Word ("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Word ("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Word ("older sister","teṭe",R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word ("younger sister","kolliti",R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word ("grand mother","ama",R.drawable.family_grandmother, R.raw. family_grandmother));
        words.add(new Word ("grand father","paapa",R.drawable.family_grandfather, R.raw.family_grandfather));


        // Create an {@link ArrayAdapter}, whose data source is a list of Strings. The
        // adapter knows how to create layouts for each item in the list, using the
        // simple_list_item_1.xml layout resource defined in the Android framework.
        // This list item layout contains a single {@link TextView}, which the adapter will set to
        // display a single word.

        //WordAdapter is a customer adapter i created that takes in two constructs this and the array list object known as word
        //we created this custom word adapter to enable us use multiple views (text views, image views etc) in our list item
        WordAdapter adapter =
                new WordAdapter (this, words,R.color.category_family);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of words.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);

                //Release media player if it currently exists because we are about to play a
                //different sound file
                releaseMediaPlayer();

                int request = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,

                        AudioManager.STREAM_MUSIC,
                        //AudioManager.AUDIOFOCUS_GAIN_TRANSIENT request audio focus for a short period of time);
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (request == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    // We have audio focus now.

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getAudioResourceId());

                    //start the audio file
                    mMediaPlayer.start();

                    //sets the mCompletiionListener that was established as a global variable so that
                    //we can stop and release the media player once the sound has finished
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                }

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
