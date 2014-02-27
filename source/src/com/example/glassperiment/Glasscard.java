package com.example.glassperiment;

import com.google.android.glass.app.Card;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Glasscard extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Card newCard = new Card(this);
		newCard.setText("Hello Making Sense!");
		newCard.setFootnote("Glassperimenting...");
		View card1View = newCard.toView();

		setContentView(card1View);
	}
	
}