package itisegato.com.nevegapptemp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import itisegato.com.nevegapptemp.Activities.MenuPuntoActivity;
import itisegato.com.nevegapptemp.GeneralClasses.Punto;
import itisegato.com.nevegapptemp.R;

/**
 * Punto stilizzato
 */

public class OnePageFragment extends Fragment{

    private Punto puntoVisualizzato;
    public static final String SIMBOLO_PUNTO = "punto";
    private Intent i;

    private View.OnClickListener listenerButton = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            i.putExtra(SIMBOLO_PUNTO, puntoVisualizzato.getNumeroPunto());
            startActivity(i);
        }
    };

    public OnePageFragment() {

    }

    public static OnePageFragment newInstance(Punto p) {
        OnePageFragment pagina = new OnePageFragment();
        pagina.puntoVisualizzato=p;
        return pagina;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        i= new Intent(this.getContext(), MenuPuntoActivity.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup composizione = (ViewGroup) inflater.inflate(
                R.layout.layout_single_fragment, container, false);
        ConstraintLayout laPiccolaFinestra;
        if(puntoVisualizzato.getNumeroPunto()!=0) {
            laPiccolaFinestra = composizione.findViewById(R.id.piccolaFinestra);
            ConstraintLayout finestra = composizione.findViewById(R.id.layoutFinestra);
            laPiccolaFinestra.setBackground(getResources().getDrawable(R.drawable.sfondo_rettangolo));
            laPiccolaFinestra.setOnClickListener(listenerButton);
            ImageView immagine = composizione.findViewById(R.id.immaginePunto);

            ((TextView) composizione.findViewById(R.id.descrizionePunto)).setText(puntoVisualizzato.getDescrizione());
            TextView titolo = composizione.findViewById(R.id.titoloPunto);
            titolo.setText(puntoVisualizzato.getNomePunto());
            ((TextView) composizione.findViewById(R.id.altitudinePunto)).setText("ALTITUDINE: " + (int) puntoVisualizzato.getAltitudine() + " s.l.m.");

            Glide.with(this)
                    .load(puntoVisualizzato.getIdImmagine())
                    .into(immagine);
            ((TextView) composizione.findViewById(R.id.descrizionePunto)).setText(puntoVisualizzato.getDescrizione());
            ((TextView) composizione.findViewById(R.id.numeroPuntoTitolo)).setText("Punto " + puntoVisualizzato.getNumeroPunto());
        }
        else {
            laPiccolaFinestra = composizione.findViewById(R.id.piccolaFinestra);
            laPiccolaFinestra.setOnClickListener(listenerButton);
            ImageView immagine = composizione.findViewById(R.id.immaginePunto);
            Glide.with(this)
                    .load(puntoVisualizzato.getIdImmagine())
                    .into(immagine);
            ((TextView) composizione.findViewById(R.id.numeroPuntoTitolo)).setText("Infopoint");
            TextView titolo = composizione.findViewById(R.id.titoloPunto);
            titolo.setText(puntoVisualizzato.getNomePunto());
            titolo.setTextSize(28f);
            ((TextView) composizione.findViewById(R.id.altitudinePunto)).setText("ALTITUDINE: " + (int) puntoVisualizzato.getAltitudine() + " s.l.m.");
        }
        return composizione;
    }
}
