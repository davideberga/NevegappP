package itisegato.com.nevegapptemp.Fragments;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.CopyrightOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.infowindow.InfoWindow;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import itisegato.com.nevegapptemp.GeneralClasses.GeneraArrayPunti;
import itisegato.com.nevegapptemp.GeneralClasses.MyInfoWindow;
import itisegato.com.nevegapptemp.GeneralClasses.MyOnClickMarkerListener;
import itisegato.com.nevegapptemp.GeneralClasses.Punto;
import itisegato.com.nevegapptemp.R;
import itisegato.com.nevegapptemp.Utilities.GpxParser;

/**
 * Created by Davide on 17/01/2018.
 * Classe che gestisce il fragment della mappa
 */

public class MapFragment extends Fragment {

    protected MapView map = null;
    protected IMapController controllore = null;
    protected GeoPoint currentLocation = null;
    protected GeoPoint centerOfPath = null;
    protected static int puntoAttuale = -1;
    protected TextView textPuntoSelezionato;
    protected ArrayList<Marker> listaMarker;
    private MyLocationNewOverlay myLocationNewOverlay;
    private CopyrightOverlay mCopyrightOverlay;
    private GpsMyLocationProvider provider;
    protected FloatingActionButton fabPosition;
    protected Button btnIndietro;
    protected Button btnAvanti;
    protected FloatingActionButton fabPath;

    private static final String SAVED_ZOOM_LEVEL = "zoom";
    private static final String SAVED_CENTER = "center";
    private static final long UPDATE_LOCATION_TIME = 1000;
    private static final double NORTH = 46.1846;
    private static final double EAST = 12.3837;
    private static final double SOUTH = 46.0113;
    private static final double WEST = 12.1825;

    /**
     * Metodo che inizializza la mappa
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_fragment, container, false);

        final Bundle state = savedInstanceState;
        puntoAttuale=-1;
        textPuntoSelezionato = v.findViewById(R.id.textViewPuntoAttuale);

        provider = new GpsMyLocationProvider(getActivity().getBaseContext());
        provider.addLocationSource(LocationManager.GPS_PROVIDER);
        provider.setLocationUpdateMinTime(UPDATE_LOCATION_TIME);

        BoundingBox box = new BoundingBox(NORTH, EAST, SOUTH, WEST);
        map = v.findViewById(R.id.mapView);
        map.setScrollableAreaLimitDouble(box);
        fabPosition = v.findViewById(R.id.fabPosition);
        btnIndietro = v.findViewById(R.id.bottoneIndietro);
        btnAvanti = v.findViewById(R.id.bottoneAvanti);
        fabPath = v.findViewById(R.id.fabPath);

        controllore = map.getController();

        if(savedInstanceState!=null) {
            if (savedInstanceState.getInt(SAVED_CENTER) != 0)
                controllore.setZoom(savedInstanceState.getInt(SAVED_CENTER));
            else
                controllore.setZoom(14);
            if (savedInstanceState.get(SAVED_CENTER) != null)
                controllore.setCenter((GeoPoint) savedInstanceState.get(SAVED_CENTER));
            else
                controllore.setCenter(new GeoPoint(Double.parseDouble("46.0854235"), Double.parseDouble("12.3049762")));
        }

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                map.setBuiltInZoomControls(false);
                map.setMultiTouchControls(true);
                map.setTileSource(TileSourceFactory.MAPNIK);
                map.setUseDataConnection(true);
                map.setVisibility(View.VISIBLE);
                map.setMinZoomLevel(14);
                mCopyrightOverlay = new CopyrightOverlay(getActivity());

                map.getOverlays().add(mCopyrightOverlay);

                myLocationNewOverlay = new MyLocationNewOverlay(provider,map);
                myLocationNewOverlay.enableMyLocation();
                myLocationNewOverlay.setDrawAccuracyEnabled(true);
                map.getOverlays().add(myLocationNewOverlay);

                ScaleBarOverlay mScaleBarOverlay = new ScaleBarOverlay(map);
                mScaleBarOverlay.enableScaleBar();
                map.getOverlays().add(mScaleBarOverlay);

                Polyline line = new Polyline();
                line.setColor(getResources().getColor(R.color.coloreAzzurroIcona));
                line.setWidth(4.5f);
                List<GeoPoint> pts = new ArrayList<>();
                List<Location> puntiDelPercorso = GpxParser.decodeGPX("trkpt",getContext());

                for(Location l : puntiDelPercorso){
                    pts.add(new GeoPoint(l.getLatitude(), l.getLongitude()));
                }

                line.setPoints(pts);
                line.setVisible(true);
                line.setGeodesic(true);
                map.getOverlays().add(line);

                Marker infopoint = new Marker(map);
                infopoint.setInfoWindow(new MyInfoWindow(R.layout.bonuspack_bubble, map, GeneraArrayPunti.getPUNTI()[0], getContext()));
                infopoint.setIcon(getResources().getDrawable(R.drawable.marker_punto_blu));
                infopoint.setPosition(new GeoPoint(46.092686, 12.281283));
                infopoint.setInfoWindowAnchor(3f,0.25f);
                map.getOverlays().add(infopoint);

                final List<Location> wayPoints = GpxParser.decodeGPX("wpt",getContext());
                listaMarker = new ArrayList<>();
                for(int i=0; i<wayPoints.size();i++){
                    Marker punto = new Marker(map);
                    listaMarker.add(punto);
                    punto.setPosition(new GeoPoint(wayPoints.get(i).getLatitude(),wayPoints.get(i).getLongitude()));
                    if(i<13) {
                        punto.setOnMarkerClickListener(new MyOnClickMarkerListener(GeneraArrayPunti.getPUNTI()[i+1],textPuntoSelezionato));
                        punto.setRelatedObject(GeneraArrayPunti.getPUNTI()[i+1]);
                    }
                    punto.setInfoWindow(new MyInfoWindow(R.layout.bonuspack_bubble, map, (Punto)punto.getRelatedObject(), getContext()));
                    punto.setInfoWindowAnchor(3f,0.25f);
                    punto.setIcon(getResources().getDrawable(R.drawable.marker_punto));
                    map.getOverlays().add(punto);
                }


                controllore.setZoom(14);
                controllore.setCenter(new GeoPoint(wayPoints.get(6)));

                //********   CLICK LISTENERS   ***********//

                textPuntoSelezionato.setText("...");
                btnIndietro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myLocationNewOverlay.disableFollowLocation();
                        if(puntoAttuale==0){
                            puntoAttuale--;
                        }
                        if(puntoAttuale!=-1){
                            puntoAttuale--;
                            textPuntoSelezionato.setText("Punto " + (puntoAttuale+1));
                            listaMarker.get(puntoAttuale).showInfoWindow();
                            controllore.animateTo(listaMarker.get(puntoAttuale).getPosition());
                        }
                        if(puntoAttuale==-1){
                            textPuntoSelezionato.setText("...");
                            InfoWindow.closeAllInfoWindowsOn(map);
                        }
                    }
                });

                btnAvanti.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myLocationNewOverlay.disableFollowLocation();
                        if(puntoAttuale!=11){
                            puntoAttuale++;
                            textPuntoSelezionato.setText("Punto " + (puntoAttuale+1));
                            listaMarker.get(puntoAttuale).showInfoWindow();
                            controllore.animateTo(listaMarker.get(puntoAttuale).getPosition());
                        }
                        else{
                            puntoAttuale=-1;
                            textPuntoSelezionato.setText("...");
                            InfoWindow.closeAllInfoWindowsOn(map);
                        }
                    }
                });


                fabPosition.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(myLocationNewOverlay.getMyLocation()!=null) {
                            controllore.animateTo(myLocationNewOverlay.getMyLocation());
                            myLocationNewOverlay.enableFollowLocation();
                        }
                        else
                            Toast.makeText(getContext(),getResources().getText(R.string.pozizione_ancora_nulla),Toast.LENGTH_SHORT).show();
                    }
                });

                fabPath.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myLocationNewOverlay.disableFollowLocation();
                        controllore.setZoom(14);
                        controllore.animateTo(new GeoPoint(wayPoints.get(6)));

                    }
                });
            }
        });


        return v;
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SAVED_ZOOM_LEVEL, map.getZoomLevel());
        outState.putSerializable(SAVED_CENTER, (Serializable) map.getMapCenter());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy(){
        map.onDetach();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        map.onDetach();
        super.onDetach();
    }

    public static void setPuntoAttuale(int nuovoPuntoAttuale) {
        puntoAttuale = nuovoPuntoAttuale;
    }

}
