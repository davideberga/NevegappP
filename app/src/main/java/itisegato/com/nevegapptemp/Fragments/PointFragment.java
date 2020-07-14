package itisegato.com.nevegapptemp.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import itisegato.com.nevegapptemp.GeneralClasses.GeneraArrayPunti;
import itisegato.com.nevegapptemp.R;
import itisegato.com.nevegapptemp.Utilities.ZoomOutPageTransformer;

/**
 * Fragment punto per la sezione 'Punti' della Main Activity
 * Created by Davide on 19/01/2018.
 */

public class PointFragment extends Fragment {

    public static final int N_PAGINE = GeneraArrayPunti.getPUNTI().length;

    public PointFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.point_fragment,container,false);
        ViewPager paginatore = v.findViewById(R.id.paginatore);
        paginatore.setPageTransformer(true, new ZoomOutPageTransformer());
        Adattatore adattatorePagine = new Adattatore(getChildFragmentManager());
        paginatore.setAdapter(adattatorePagine);
        paginatore.setCurrentItem(0);
        ImageView img = v.findViewById(R.id.sfondoPunti);

        Glide.with(this)
                .load(R.drawable.sfondo_punti)
                .centerCrop()
                .into(img);

        return v;
    }

    private class Adattatore extends FragmentPagerAdapter {

        public Adattatore(FragmentManager manager){
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return OnePageFragment.newInstance(GeneraArrayPunti.getPUNTI()[position]);
        }

        @Override
        public int getCount() { return N_PAGINE;}
    }

    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation animation = super.onCreateAnimation(transit, enter, nextAnim);

        if (animation != null) {
            getView().setLayerType(View.LAYER_TYPE_HARDWARE, null);

            animation.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationEnd(Animation animation) {
                    getView().setLayerType(View.LAYER_TYPE_NONE, null);
                }
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        return animation;
    }
}
