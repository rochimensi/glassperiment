package com.example.glassperiment;

import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

public class HeadlineRenderer implements SurfaceHolder.Callback {
	
	private static final String TAG = HeadlineRenderer.class.getSimpleName();

    /** The duration, in milliseconds, of one frame. */
    private static final long FRAME_TIME_MILLIS = TimeUnit.SECONDS.toMillis(2);

	
    private SurfaceHolder mHolder;
    private boolean mTooSteep;
    private boolean mInterference;
    private RenderThread mRenderThread;
    private int mSurfaceWidth;
    private int mSurfaceHeight;

    public HeadlineRenderer(Context context) {
        //LayoutInflater inflater = LayoutInflater.from(context);

        //mLayout = (FrameLayout) inflater.inflate(R.layout.compass, null);
        //mLayout.setWillNotDraw(false);

        //mCompassView = (CompassView) mLayout.findViewById(R.id.compass);
        //mTipsContainer = (RelativeLayout) mLayout.findViewById(R.id.tips_container);
        //mTipsView = (TextView) mLayout.findViewById(R.id.tips_view);
    }
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mHolder = holder;
		
        mRenderThread = new RenderThread();
        mRenderThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mRenderThread.quit();
	}
	
	/**
     * Repaints the compass.
     */
    private synchronized void repaint() {
        Canvas canvas = null;

        try {
            canvas = mHolder.lockCanvas();
        } catch (RuntimeException e) {
            Log.d(TAG, "lockCanvas failed", e);
        }

        if (canvas != null) {
            //mLayout.draw(canvas);

            try {
                mHolder.unlockCanvasAndPost(canvas);
            } catch (RuntimeException e) {
                Log.d(TAG, "unlockCanvasAndPost failed", e);
            }
        }
    }
	

	/**
     * Redraws the compass in the background.
     */
    private class RenderThread extends Thread {
        private boolean mShouldRun;

        /**
         * Initializes the background rendering thread.
         */
        public RenderThread() {
            mShouldRun = true;
        }

        /**
         * Returns true if the rendering thread should continue to run.
         *
         * @return true if the rendering thread should continue to run
         */
        private synchronized boolean shouldRun() {
            return mShouldRun;
        }

        /**
         * Requests that the rendering thread exit at the next opportunity.
         */
        public synchronized void quit() {
            mShouldRun = false;
        }

        @Override
        public void run() {
            while (shouldRun()) {
            	Log.d(TAG, "Show a card...");
                long frameStart = SystemClock.elapsedRealtime();
                repaint();
                long frameLength = SystemClock.elapsedRealtime() - frameStart;

                long sleepTime = FRAME_TIME_MILLIS - frameLength;
                if (sleepTime > 0) {
                    SystemClock.sleep(sleepTime);
                }
            }
        }
    }
	
}
