package com.example.glassperiment;

import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.LiveCard.PublishMode;
import com.google.android.glass.timeline.TimelineManager;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class HeadlineService extends Service {

	// Tag used to identify the LiveCard in debugging logs.
	private static final String LIVE_CARD_TAG = "glassperiment";

	private HeadlineRenderer mRenderer;
	private TimelineManager mTimelineManager;
	
	// Cached instance of the LiveCard created by the publishCard() method.
	private LiveCard mLiveCard;

	@Override
	public void onCreate() {
		super.onCreate();

		mTimelineManager = TimelineManager.from(this);
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		if (mLiveCard == null) {
			mLiveCard = mTimelineManager.createLiveCard(LIVE_CARD_TAG);
			mRenderer = new HeadlineRenderer(this);
			
			mLiveCard.setDirectRenderingEnabled(true).getSurfaceHolder().addCallback(mRenderer);

			Intent headlineCardIntent = new Intent(this, MainActivity.class);
			headlineCardIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

			mLiveCard.setAction(PendingIntent.getActivity(this, 0, headlineCardIntent, 0));
			mLiveCard.publish(PublishMode.REVEAL);

			startActivity(headlineCardIntent);
		}

		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		if (mLiveCard != null && mLiveCard.isPublished()) {
			mLiveCard.unpublish();
			mLiveCard.getSurfaceHolder().removeCallback(mRenderer);
			mLiveCard = null;
		}

		super.onDestroy();
	}

}
