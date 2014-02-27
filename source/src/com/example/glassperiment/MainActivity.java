package com.example.glassperiment;

import android.os.IBinder;
import android.app.Service;
import android.content.Intent;

public class MainActivity extends Service {
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	public int onStartCommand(Intent intent, int flags, int startId) {
		Intent i = new Intent(this, Glasscard.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
