package edu.cmu.mobileapp.picocale.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import java.util.List;

import edu.cmu.mobileapp.picocale.listener.GalleryItemClickListener;
import edu.cmu.mobileapp.picocale.service.DeviceImageServiceImpl;
import edu.cmu.mobileapp.picocale.service.ImageService;
import edu.cmu.mobileapp.picocale.view.adapter.ImageGridAdapter;

/**
 * Created by srikrishnan_suresh on 07/26/2015.
 */
public class DeviceImageFetcher extends AsyncTask<String, Void, List<String>> {
    private Activity activity;
    private GridView gridView;
    private ProgressDialog progress;
    private ImageService imageService = new DeviceImageServiceImpl();

    public DeviceImageFetcher(Activity activity, GridView gridView) {
        this.activity = activity;
        this.gridView = gridView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = new ProgressDialog(activity);
        progress.setMessage("Loading...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }

    @Override
    protected void onPostExecute(List<String> locationList) {
        super.onPostExecute(locationList);
        progress.hide();
        gridView.setAdapter(new ImageGridAdapter(activity, locationList));
        gridView.setOnItemClickListener(new GalleryItemClickListener(activity, locationList));
    }

    @Override
    protected List<String> doInBackground(String... params) {
        String location = params[0];
        return imageService.getImageList(activity, location);
    }
}