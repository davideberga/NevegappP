package itisegato.com.nevegapptemp.Utilities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class SwipeAdapter extends FragmentStatePagerAdapter {

    private List<Image> images;
    private boolean premuto;
    private Context mContext;

    public SwipeAdapter(FragmentManager fm, List<Image> images, boolean premuto, Context mContext) {
        super(fm);
        this.images = images;
        this.premuto = premuto;
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int pos) {
        Fragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", images.get(pos).getTitle());
        bundle.putInt("image", images.get(pos).getThumbnail());
        bundle.putBoolean("premuto", premuto);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return images.size();
    }
}
