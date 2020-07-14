package itisegato.com.nevegapptemp.Utilities;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import itisegato.com.nevegapptemp.Activities.ImageActivity;
import itisegato.com.nevegapptemp.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    protected List<Image> mData ;

    public RecyclerViewAdapter(Context mContext, List<Image> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_images,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tv_image_title.setText(mData.get(position).getTitle());
        //holder.image_thumbnail.setImageResource(mData.get(position).getThumbnail());
        Glide.with(mContext).load(mData.get(position).getThumbnail()).into(holder.image_thumbnail);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ImageActivity.class);
                intent.putParcelableArrayListExtra("ListImages", (ArrayList<? extends Parcelable>) mData);
                intent.putExtra("pos", position);
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_image_title;
        ImageView image_thumbnail;
        CardView cardView ;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_image_title = (TextView) itemView.findViewById(R.id.image_title_id) ;
            image_thumbnail = (ImageView) itemView.findViewById(R.id.image_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }
}