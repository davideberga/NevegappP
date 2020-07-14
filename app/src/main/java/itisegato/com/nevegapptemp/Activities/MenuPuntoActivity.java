package itisegato.com.nevegapptemp.Activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import itisegato.com.nevegapptemp.Fragments.OnePageFragment;
import itisegato.com.nevegapptemp.GeneralClasses.GeneraArrayPunti;
import itisegato.com.nevegapptemp.GeneralClasses.Punto;
import itisegato.com.nevegapptemp.R;
import itisegato.com.nevegapptemp.GeneralClasses.Sezione;

/**
 * Activity per la visualizzazione di tutte le info di un punto
 */
public class MenuPuntoActivity extends AppCompatActivity {

    protected Punto puntoVisualizzato;
    protected ArrayList<Sezione> sezioni;
    protected ViewPager visualizzatorePagine;
    protected AdattatorePagine adattatore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        puntoVisualizzato = GeneraArrayPunti.getPUNTI()[getIntent().getIntExtra(OnePageFragment.SIMBOLO_PUNTO, 0)];
        sezioni = puntoVisualizzato.sezioni();


        setContentView(R.layout.activity_menu_punto);

        ImageView immagine = findViewById(R.id.immagineMenuPunto);
        Glide.with(this)
                .load(puntoVisualizzato.getIdImmagine())
                .centerCrop()
                .into(immagine);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.freccia_indietro);
        toolbar.setClickable(true);
        toolbar.setTitle(puntoVisualizzato.getNomePunto());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuPuntoActivity.super.onBackPressed();
            }
        });

        visualizzatorePagine = findViewById(R.id.viewPagerMenuPunto);
        adattatore = new AdattatorePagine(getSupportFragmentManager());
        visualizzatorePagine.setAdapter(adattatore);

        TabLayout mTabLayout = findViewById(R.id.tabLayoutArgomenti);
        visualizzatorePagine.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(visualizzatorePagine));

        for (Sezione s: sezioni) {
            mTabLayout.addTab(mTabLayout.newTab().setText(s.getNomeSezione()));
        }

    }

    /**
     *  Queste due classi permettono la visulizzazione 'con tab' delle diverse sezioni di un punto
     */

    public static class FragmentSezionePunto extends Fragment {

        private CharSequence testoSezione;
        private String nome;

        public FragmentSezionePunto() {
        }

        public static Fragment newInstance(CharSequence testo, String nome){
            FragmentSezionePunto fr = new FragmentSezionePunto();
            fr.testoSezione=testo;
            fr.nome = nome;
            return fr;
        }

        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View composizione =
                    inflater.inflate(R.layout.fragment_menu_sezione,container,false);
            TextView testo = composizione.findViewById(R.id.testoScrollabile);
            testo.setText(testoSezione);
            return composizione;
        }
    }

    public class AdattatorePagine extends FragmentStatePagerAdapter {

        public AdattatorePagine(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentSezionePunto.newInstance(sezioni.get(position).getContenutoSezione(), sezioni.get(position).getNomeSezione());
        }

        @Override
        public int getCount() {
            return sezioni.size();
        }
    }

}
