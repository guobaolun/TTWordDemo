package com.english.storm.glide.progress;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.target.Target;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProgressManager {

    private static Map<Object, WeakReference<OnProgressListener>> mapLeners = Collections.synchronizedMap(new HashMap<Object, WeakReference<OnProgressListener>>());

    private static OkHttpClient okHttpClient;

    private static Handler handler = new Handler(Looper.getMainLooper());

    private ProgressManager() {
    }


    static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(@NonNull Chain chain) throws IOException {
                            Request request = chain.request();
                            Response response = chain.proceed(request);
                            return response.newBuilder().body(new ProgressResponseBody(request.url().toString(), response.body(), LISTENER)).build();
                        }
                    })
                    .build();
        }
        return okHttpClient;
    }


    private static final OnProgressListener LISTENER = new OnProgressListener() {
        @Override
        public void onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
        }

        @Override
        public void onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
        }

        @Override
        public void onProgress(final String imageUrl, final long bytesRead, final long totalBytes, final boolean isDone, final GlideException exception) {
            if (mapLeners == null || mapLeners.size() == 0) {
                return;
            }


            handler.post(new Runnable() {
                @Override
                public void run() {
                    WeakReference<OnProgressListener> weakReference = mapLeners.get(decoderURL(imageUrl));
                    if (weakReference == null) {
                        return;
                    }

                    OnProgressListener progressListener = weakReference.get();
                    if (progressListener != null) {
                        progressListener.onProgress(imageUrl, bytesRead, totalBytes, isDone, exception);
                    } else {
                        mapLeners.remove(imageUrl);
                    }
                }
            });
        }
    };

    public static void addProgressListener(Object url,OnProgressListener progressListener) {
        if (progressListener == null) {
            return;
        }

        mapLeners.put(url, (new WeakReference<>(progressListener)));
    }

    public static void removeProgressListener(Object url) {
        mapLeners.remove(url);
    }


    private final static String ENCODE = "UTF-8";

    /**
     * URL 解码
     */
    private static String decoderURL(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
