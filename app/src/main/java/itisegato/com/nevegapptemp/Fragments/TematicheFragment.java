package itisegato.com.nevegapptemp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import itisegato.com.nevegapptemp.Activities.GalleriaActivity;
import itisegato.com.nevegapptemp.Activities.MenuSezioneActivity;
import itisegato.com.nevegapptemp.GeneralClasses.Punto;
import itisegato.com.nevegapptemp.R;

/**
 * Fragment tematiche per la sezione 'Temi' della Main Activity
 * Created by Davide on 15/03/2018.
 */

public class TematicheFragment extends Fragment {

    private View v;
    public static final String COLORE = "colore";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tematiche_fragment,container,false);
        setAllOnClickListener();
        return v;
    }



    private void setAllOnClickListener(){
        v.findViewById(R.id.cardViewStoria).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getBaseContext(), MenuSezioneActivity.class);
                i.putExtra(MenuSezioneActivity.SIMBOLO_SEZIONE, Punto.NOME_SEZIONE_STORIA);
                i.putExtra(COLORE, getResources().getColor(R.color.coloreStoria));
                startActivity(i);
            }
        });
        v.findViewById(R.id.cardViewFauna).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getBaseContext(), MenuSezioneActivity.class);
                i.putExtra(MenuSezioneActivity.SIMBOLO_SEZIONE, Punto.NOME_SEZIONE_FAUNA);
                i.putExtra(COLORE, getResources().getColor(R.color.coloreFauna));
                startActivity(i);
            }
        });
        v.findViewById(R.id.cardViewFlora).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getBaseContext(), MenuSezioneActivity.class);
                i.putExtra(MenuSezioneActivity.SIMBOLO_SEZIONE, Punto.NOME_SEZIONE_FLORA);
                i.putExtra(COLORE, getResources().getColor(R.color.coloreFlora));
                startActivity(i);
            }
        });
        v.findViewById(R.id.cardViewGeologia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getBaseContext(), MenuSezioneActivity.class);
                i.putExtra(MenuSezioneActivity.SIMBOLO_SEZIONE, Punto.NOME_SEZIONE_GEOLOGIA);
                i.putExtra(COLORE, getResources().getColor(R.color.coloreGeologia));
                startActivity(i);
            }
        });
        v.findViewById(R.id.cardViewTurismo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getBaseContext(), MenuSezioneActivity.class);
                i.putExtra(MenuSezioneActivity.SIMBOLO_SEZIONE, Punto.NOME_SEZIONE_TURISMO);
                i.putExtra(COLORE, getResources().getColor(R.color.coloreTurismo));
                startActivity(i);
            }
        });
        v.findViewById(R.id.cardViewVarie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getBaseContext(), MenuSezioneActivity.class);
                i.putExtra(MenuSezioneActivity.SIMBOLO_SEZIONE, Punto.NOME_SEZIONE_VARIE);
                i.putExtra(COLORE, getResources().getColor(R.color.coloreVarie));
                startActivity(i);
            }
        });
        v.findViewById(R.id.cardViewLinkEContatti).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getBaseContext(), MenuSezioneActivity.class);
                i.putExtra(MenuSezioneActivity.SIMBOLO_SEZIONE, Punto.NOME_SEZIONE_LINK_CONTATTI);
                i.putExtra(COLORE, getResources().getColor(R.color.coloreLinkEContatti));
                startActivity(i);
            }
        });
        v.findViewById(R.id.cardViewDescrizioni).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getBaseContext(), MenuSezioneActivity.class);
                i.putExtra(MenuSezioneActivity.SIMBOLO_SEZIONE, Punto.NOME_SEZIONE_DESCRIZIONE);
                i.putExtra(COLORE, getResources().getColor(R.color.coloreDescrizioni));
                startActivity(i);
            }
        });
        v.findViewById(R.id.cardViewGalleria).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getBaseContext(), GalleriaActivity.class);
                i.putExtra(COLORE, getResources().getColor(R.color.coloreGalleria));
                startActivity(i);
            }
        });
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