package com.infusion.digimenu.framework;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by ali on 2015-05-31.
 * Reference: http://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView mImageView;

    public DownloadImageTask(ImageView imageView) {
        mImageView = imageView;
    }

    protected Bitmap doInBackground(String... urls) {
        Bitmap result = null;
        String url = "";

        try {
            url = urls[0];

            InputStream stream = new java.net.URL(url).openStream();
            result = BitmapFactory.decodeStream(stream);
        } catch (Exception e) {
            Log.e(DownloadImageTask.class.getName(), "Failed to download image from " + url, e);
            e.printStackTrace();
        }
        return result;
    }

    protected void onPostExecute(Bitmap result) {
        if (mImageView == null) {
            // no image view to apply bitmap against
            return;
        }

        mImageView.setImageBitmap(result);
    }
}
