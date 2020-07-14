package itisegato.com.nevegapptemp.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import itisegato.com.nevegapptemp.R;
import itisegato.com.nevegapptemp.Utilities.DepthPageTranformer;
import itisegato.com.nevegapptemp.Utilities.Image;
import itisegato.com.nevegapptemp.Utilities.SwipeAdapter;

/**
 *  Classe utilizzata dalla galleria, un fragment che rappresenta un'immagine
 */
public class ImageActivity extends FragmentActivity {

    private ViewPager viewPager;
    private List<Image> images;
    private int pos;
    private  boolean premuto;
    private SwipeAdapter swipeAdapter;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        riceviImmagini();

        inviaImmagini();

        mContext = this;
    }
    private void riceviImmagini(){
        Intent intent = getIntent();
        images = intent.getParcelableArrayListExtra("ListImages");
        pos = intent.getIntExtra("pos", -1);
        premuto = intent.getBooleanExtra("premuto", false);
    }

    private void inviaImmagini(){
        viewPager = (ViewPager) findViewById(R.id.view_pager_id);
        swipeAdapter = new SwipeAdapter(getSupportFragmentManager(), images, premuto, mContext);
        viewPager.setPageTransformer(true, new DepthPageTranformer(), View.LAYER_TYPE_NONE);
        viewPager.setAdapter(swipeAdapter);
        viewPager.setCurrentItem(pos);
    }

}
