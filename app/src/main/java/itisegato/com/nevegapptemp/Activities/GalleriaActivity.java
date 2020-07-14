package itisegato.com.nevegapptemp.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import itisegato.com.nevegapptemp.Fragments.TematicheFragment;
import itisegato.com.nevegapptemp.R;
import itisegato.com.nevegapptemp.Utilities.Image;
import itisegato.com.nevegapptemp.Utilities.RecyclerViewAdapter;


/**
 *  La galleria visualizza una serie di immagini,scaricabili sul proprio dispositivo
 */
public class GalleriaActivity extends AppCompatActivity {

    private List<Image> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galleria);

        images = new ArrayList<>();

        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle("GALLERIA");


        images.add(new Image("1° Giochi della Gioventù anno 1970 (foto Zanfron)", R.drawable.foto_01));
        images.add(new Image("Aconito napello nei pressi dell'orto botanico",  R.drawable.foto_02));
        images.add(new Image("Albergo S. Martino 1961 (foto Zanfron)",  R.drawable.foto_03));
        images.add(new Image("Campanula morettiana",  R.drawable.foto_04));
        images.add(new Image("Dolina nei pressi dell'orto botanico", R.drawable.foto_05));
        images.add(new Image("Gentiana punctata",  R.drawable.foto_06));
        images.add(new Image("Genziana pannonica",  R.drawable.foto_07));
        images.add(new Image("Giglio di S. Giovanni",  R.drawable.foto_08));
        images.add(new Image("Gli autori, in cresta",  R.drawable.foto_09));
        images.add(new Image("Gli autori, in pausa contemplativa", R.drawable.foto_10));
        images.add(new Image("Ingresso del giardino botanico",  R.drawable.foto_11));
        images.add(new Image("Nigritella nigra",  R.drawable.foto_12));
        images.add(new Image("Ninfea comune",  R.drawable.foto_13));
        images.add(new Image("Panoramica Dolomiti",  R.drawable.foto_14));
        images.add(new Image("Raponzolo di roccia",  R.drawable.foto_15));
        images.add(new Image("Regina delle Alpi",  R.drawable.foto_16));
        images.add(new Image("Rif. Col Visentìn  anni 50 (foto Fausto Viola)",  R.drawable.foto_17));
        images.add(new Image("Rocce calcaree",  R.drawable.foto_18));
        images.add(new Image("Scarpetta della Madonna",  R.drawable.foto_19));
        images.add(new Image("Tracce lasciate da cinghiali",  R.drawable.foto_20));
        images.add(new Image("Vista su monte Serva e Schiara",  R.drawable.foto_21));

        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, images);
        myrv.setLayoutManager(new GridLayoutManager(this, 3));
        myrv.setAdapter(myAdapter);
    }
}
