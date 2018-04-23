package com.example.android.miwok;

/**
 * Created by ARINZE on 2016-08-15.
 */
public class Word {

    /**
     * Default translation for the word also known as the states of the word class
     * Makes the states private, so other activities would not modify it
     */
    private String mDefaultTranslation;

    /**
     * Miwok translation for the word also one state of the word class
     */
    private String mMiwokTranslation;


    //Images for the app
    /**
     * set the mImageResourceId variable to start at the NO_IMAGE_PROVIDED state by default
     */
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    /**
     * CREATED A CONSTANT variable named NO_IMAGE_PROVIDED hence the capital letters and set it to -1
     * because the value for the constant, VISIBLE image is 0
     * INVISIBLE is 4
     * GONE is 8
     */
    private static final int NO_IMAGE_PROVIDED = -1;


    private int mAudioResourceId;



    /**
     *      * Create a new Word object. and constructors
     *      *
     *      * @param defaultTranslation is the word in a language that the user is already familiar with
     *      *                           (such as English)
     *      * @param miwokTranslation is the word in the Miwok language
     *
     *      @param imageResourceId is the refers to the particular image in the drawable folder
     *
     *      New object created below, name name variable is word, with input parameters as the states created above
     *
     */
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }



    // new word constructor for the word object to enable the phrases activity call its inputs without calling an image resource id
    /** defaultTranslation is the default word for the app
     *
     * @param defaultTranslation is the default word translation of the word
     * @param miwokTranslation is the miwok translation of the word
     */
    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }


    /**
     *      Get method for getting the default translation of the word.
     *      Other activities can call this get method, without modify the ststes themselves
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }



    /**
     *      * Get the Miwok translation of the word.
     *
     */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }


    //Get the images from the state of the word class
    public int getImage() {return mImageResourceId;}

    /** return true if the mImageResourceId is NOT EQUAL to the NO_IMAGE_PROVIDED value ( -1)
     * return false if the mImageResourceId ie equal to -1, meaning the word has no image
     * We want to return an image if an image is required, because the phrases activity has no image
     * != means not equal to
     */
    public boolean hasImage(){
    return mImageResourceId != NO_IMAGE_PROVIDED;
    }



    public int getAudioResourceId() {return mAudioResourceId;}
}
