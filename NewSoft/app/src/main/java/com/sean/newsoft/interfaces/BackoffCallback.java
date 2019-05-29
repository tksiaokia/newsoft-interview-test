package com.sean.newsoft.interfaces;

import android.os.Handler;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;

public abstract class BackoffCallback<T> implements Callback<T> {
    private static final int RETRY_COUNT = 3;
    /**
     * Base retry delay for exponential backoff, in Milliseconds
     */
    private static final double RETRY_DELAY = 300;
    private int retryCount = 0;

    @Override
    public void onFailure(final Call<T> call, Throwable t) {
        retryCount++;
        if (retryCount <= RETRY_COUNT) {
            int expDelay = (int) (RETRY_DELAY * Math.pow(2, Math.max(0, retryCount - 1)));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    retry(call);
                }
            }, expDelay);
        } else {
            onFailedAfterRetry(t);
        }
    }

    private void retry(Call<T> call) {
        call.clone().enqueue(this);
    }

    public abstract void onFailedAfterRetry(Throwable t);
}