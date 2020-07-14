package itisegato.com.nevegapptemp.Utilities;

import android.support.v4.view.ViewPager;
import android.view.View;

import itisegato.com.nevegapptemp.R;

/**
 * Created by Davide on 07/02/2018.
 */

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    public void transformPage(View view, float position) {
        view = view.findViewById(R.id.piccolaFinestra);
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position <= 1) {
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }

            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        }
    }
}
