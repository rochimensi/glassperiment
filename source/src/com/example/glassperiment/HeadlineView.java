package com.example.glassperiment;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class HeadlineView extends FrameLayout {
	
	/**
     * Interface to listen for changes on the view layout.
     */
    public interface ChangeListener {
        /** Notified of a change in the view. */
        public void onChange();
    }
    
    private final int mWhiteColor;
    private final TextView mDescriptionView;
    private ChangeListener mChangeListener;
    
	public HeadlineView(Context context) {
		super(context);

        LayoutInflater.from(context).inflate(R.layout.headline, this);

        mDescriptionView = (TextView) findViewById(R.id.description);
        mWhiteColor = context.getResources().getColor(R.color.white);
	}
	
	/**
     * Set a {@link ChangeListener}.
     */
    public void setListener(ChangeListener listener) {
        mChangeListener = listener;
    }
	
	/**
     * Updates the displayed text with the provided values.
     * 
     * @param description the description of the headline
     */
	public void updateText(String description){
    	mDescriptionView.setText(description);
    	mDescriptionView.setTextColor(mWhiteColor);
        
        if (mChangeListener != null) {
            mChangeListener.onChange();
        }
	}
    
}