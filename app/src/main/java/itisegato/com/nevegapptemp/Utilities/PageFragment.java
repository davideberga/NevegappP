package itisegato.com.nevegapptemp.Utilities;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import itisegato.com.nevegapptemp.R;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PageFragment extends android.support.v4.app.Fragment{

    TextView title;
    ImageView image;
    boolean premuto;
    Button salva;
    View view;
    Bundle bundle;

    public PageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.activity_image, container, false);
        getContenuti();
        setContenuti();
        taptap();
        salva.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                salvaImmagini(bundle.getInt("image"), bundle.getString("title"));
            }
        });

        return view;
    }

    public void getContenuti(){
        title = (TextView) view.findViewById(R.id.myImageViewText_id);
        image = (ImageView) view.findViewById(R.id.image_id);
        salva = (Button) view.findViewById(R.id.button_save_id);
    }

    public void setContenuti(){
        bundle = getArguments();
        title.setText(bundle.getString("title"));
        Glide.with(this).load(bundle.getInt("image")).fitCenter().into(image);
        premuto = bundle.getBoolean("premuto");
    }

    public void taptap(){
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(image);
        photoViewAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float v, float v1) {
                if (premuto) {
                    Animation fade = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                    title.setVisibility(View.VISIBLE);
                    title.startAnimation(fade);
                    premuto = !premuto;
                } else {
                    Animation fade = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
                    title.startAnimation(fade);
                    title.setVisibility(View.INVISIBLE);
                    premuto = !premuto;
                }
            }
        });
        photoViewAttacher.update();
    }
    private void salvaImmagini(final int image,final String title){

        Toast toast = Toast.makeText(getContext(), "Salvataggio in corso...", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        Runnable r = new Runnable() {
            @Override
            public void run() {

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                Bitmap imageDaPassare = BitmapFactory.decodeResource(getResources(), image);
                File path = Environment.getExternalStorageDirectory();
                File dir = new File(path+"/NevegApp/");
                dir.mkdirs();
                File file = new File(dir, title+".png");
                OutputStream out = null;

                try{
                    out = new FileOutputStream(file);
                    imageDaPassare.compress(Bitmap.CompressFormat.PNG,100, out);
                    out.flush();
                    out.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thr = new Thread(r);
        thr.start();
    }
}
