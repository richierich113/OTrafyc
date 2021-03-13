package com.otrafyc.android.traffic.otrafycapp;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.appolica.interactiveinfowindow.InfoWindow;
import com.appolica.interactiveinfowindow.fragment.MapInfoWindowFragment;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.datatransport.BuildConfig;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.maps.model.SquareCap;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.otrafyc.android.traffic.otrafycapp.Helper.CustomInfoWindow;
import com.otrafyc.android.traffic.otrafycapp.MyCommon_Package.MyCommon;
import com.otrafyc.android.traffic.otrafycapp.Remote_Package.IGoogleAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*GoogleMap.OnMarkerClickListener*/
public class MapsActivity extends AppCompatActivity implements RoutingListener, OnMapReadyCallback, LocationListener, NavigationView.OnNavigationItemSelectedListener {


    private static final String REQUESTING_LOCATION_UPDATES_KEY = "";
    //private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    private static int DELAY_TIME = 30000;
    private GoogleMap mMap;

    LatLng currentLocation;


    private static final int MY_PERMISSION_REQUEST_CODE = 7000;
    private static final int PLAY_SERVICE_RES_REQUEST = 7001;

    //location
    private LocationRequest locationRequest;
    private GoogleApiClient mGoogleApiClient;

    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationProviderClient;


    private Location mLastLocation;

    private static int UPDATE_INTERVAL = 3000;
    private static int FASTEST_INTERVAL = 1000;
    private static int DISPLACEMENT = 1;

    private Marker mUserMarker;


    Animation bottomAnim, bottomAnimReverse, blinkAnim, buttonBounceAnim, blinkAnim2;
    boolean mBlinkingCardView = false;
    //boolean isMemoryAddIconBlinking = false;

    boolean requestingLocationUpdates = false;


    Marker poiMarker;
    Marker destinationMarker;
    private Marker memoryMarker;
    Button displayOnOffButton;
    Button trafficLegendButton;


    // String memoryTitleText, memoryDescriptionText;


    private SwitchMaterial locationSwitch;

    // Places fragment
    AutocompleteSupportFragment places;

    SupportMapFragment mapFragment;

    //country list selection

    ArrayList<String> countryListArray;
    ListView countryList;

    Dialog countryCodeSearchDialog, navigationDialog, tapNavigationDialog, exitAppDialog, networkCheckErrorDialog, locationSettingDialog, memoryMarkerDialog, howToUseDialog, mapTypeDialog;

    EditText dialogEditText;


    ImageView countriesSelectImg, helpMessageImg, memoryAddIconImg, searchPlace;
    MaterialCardView navigateCardView, tapNavigateCardView, trafficLegend_cardView, autocompleteCardView;
    MaterialCardView lovelyCardView, happyCardView, amazingCardView, sadCardView, coolCardView, angryCardView, painCardView, awkwardCardView, confuseCardView, cryingCardView, highWeedCardView;
    MaterialCardView kissCardView, laughCardView, pamperedCardView, scaredCardView, shockCardView, sleepingCardView, teasingCardView;
    MaterialCardView shareMemoryCardView, bottomCardview, mapTypeCardView, defaultMapCardView, darkMapCardView, satelliteMapCardView;


    Toolbar toolbar1;


    LinearLayout memoryIconsCardViewHeadLayout;
    HorizontalScrollView memoryIconScrollView;


    TextView distanceTextView, distanceShowText;

    TextInputLayout memoryTitle, memoryDescription;
    TextInputEditText memoryTitle_editText2, memoryDescription_editText2;

    LottieAnimationView locationSettingOnOffLottie, howToUseIconLottie;


    DrawerLayout drawer;
    NavigationView navigationView;
    private RelativeLayout wholeScreen;
    String jobId = "1";


    //    for navigation menu animation
    static final float END_SCALE = 0.7f;


    //car animation
    private List<LatLng> polyLineList;

    private float v;
    private double lat, lng;
    private Handler handler;
    LatLng currentPosition, startPosition, endPosition, destinationLatLng, currentLatLong, poiLatLng;
    private int index, next;

    String mDestination, mDestinationLat, mDestinationLong, poiLat, poiLong;

    String destinationLoc, destinationLong, destinationLat, destinationName;
    String currentLocationLat, currentLocationLong;
    private PolylineOptions polylineOptions, blackPolylineOptions;
    private Polyline blackPolyline, greyPolyline;
    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.colorPrimary, R.color.colorAccent, R.color.rippleEffectColor, R.color.colorPrimaryDark, R.color.primary_dark_material_light};

    private static String TAG = "info";

    private IGoogleAPI mService;

    //for editActivity data sharing
    private static final int EDIT_REQUEST = 1;


    //for  app update notification
    private static final int REQ_CODE_VERSION_UPDATE = 530;
    AppUpdateManager appUpdateManager;
    private InstallStateUpdatedListener installStateUpdatedListener;




   /* private Runnable drawPathRunnable = new Runnable() {
        @Override
        public void run() {
            if (index < polyLineList.size() - 1) {
                index++;
                next = index + 1;
            }

            *//*if (index < polyLineList.size() - 1) {
                startPosition = polyLineList.get(index);
                endPosition = polyLineList.get(next);

            }*//*


        }
    };*/

    // private MenuItem supportActionBar;

    //it was private float getBearing

    /* public float getBearing(LatLng startPosition, LatLng endPosition) {
         double lat = Math.abs(startPosition.latitude - endPosition.latitude);
         double lng = Math.abs(startPosition.longitude - endPosition.longitude);
         if (startPosition.latitude < endPosition.latitude && startPosition.longitude < endPosition.longitude)
             return (float) (Math.toDegrees(Math.atan(lat / lng)));
         else if (startPosition.latitude >= endPosition.latitude && startPosition.longitude < endPosition.longitude)
             return (float) ((90 - Math.toDegrees(Math.atan(lat / lng))) + 90);

         else if (startPosition.latitude >= endPosition.latitude && startPosition.longitude >= endPosition.longitude)
             return (float) ((Math.toDegrees(Math.atan(lat / lng))) + 180);
         else if (startPosition.latitude < endPosition.latitude && startPosition.longitude >= endPosition.longitude)
             return (float) ((90 - Math.toDegrees(Math.atan(lat / lng))) + 270);

         return -1;

     }

 */
   /* @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/typographica_regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());*/

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_maps);

        // lock screen orientation to portrait ...works when placed after setContentView
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mLastLocation = locationResult.getLastLocation();
            }
        };
        //for app update
        checkForAppUpdate();

// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        //interactive fra


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);// making toolbar ur actionBar


        //  dialogs init
        navigationDialog = new Dialog(this);
        tapNavigationDialog = new Dialog(this);
        exitAppDialog = new Dialog(this);
        networkCheckErrorDialog = new Dialog(this);
        locationSettingDialog = new Dialog(this);
        memoryMarkerDialog = new Dialog(this);
        howToUseDialog = new Dialog(this);
        //mapTypeDialog = new Dialog(this);

        displayOnOffButton = findViewById(R.id.displayOnOffButton);

        displayOnOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayOnOffButton.startAnimation(buttonBounceAnim);
            }
        });


        //FIREBASE ANALYTICS INIT
        // Obtain the FirebaseAnalytics instance.
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        //initiate array list
        countryListArray = new ArrayList<>();

        //add array items
        //A
        countryListArray.add("AF");
        countryListArray.add("AX");
        countryListArray.add("AL");
        countryListArray.add("DZ");
        countryListArray.add("AS");
        countryListArray.add("AD");
        countryListArray.add("AO");
        countryListArray.add("AI");
        countryListArray.add("AQ");
        countryListArray.add("AG");
        countryListArray.add("AR");
        countryListArray.add("AM");
        countryListArray.add("AW");
        countryListArray.add("AU");
        countryListArray.add("AT");
        countryListArray.add("AZ");
        //B
        countryListArray.add("BS");
        countryListArray.add("BH");
        countryListArray.add("BD");
        countryListArray.add("BB");
        countryListArray.add("BY");
        countryListArray.add("BE");
        countryListArray.add("BZ");
        countryListArray.add("BJ");
        countryListArray.add("BM");
        countryListArray.add("BT");
        countryListArray.add("BO");
        countryListArray.add("BA");
        countryListArray.add("BW");
        countryListArray.add("BV");
        countryListArray.add("BR");
        countryListArray.add("VG");
        countryListArray.add("IO");
        countryListArray.add("BN");
        countryListArray.add("BG");
        countryListArray.add("BF");
        countryListArray.add("BI");
        //C
        countryListArray.add("KH");
        countryListArray.add("CM");
        countryListArray.add("CA");
        countryListArray.add("CV");
        countryListArray.add("KY");
        countryListArray.add("CF");
        countryListArray.add("TD");
        countryListArray.add("CL");
        countryListArray.add("CN");
        countryListArray.add("HK");
        countryListArray.add("MO");
        countryListArray.add("CX");
        countryListArray.add("CC");
        countryListArray.add("CO");
        countryListArray.add("KM");
        countryListArray.add("CG");
        countryListArray.add("CD");
        countryListArray.add("CK");
        countryListArray.add("CR");
        countryListArray.add("CI");
        countryListArray.add("HR");
        countryListArray.add("CU");
        countryListArray.add("CY");
        countryListArray.add("CZ");
        //D
        countryListArray.add("DK");
        countryListArray.add("DJ");
        countryListArray.add("DM");
        countryListArray.add("DO");
        //E
        countryListArray.add("EC");
        countryListArray.add("EG");
        countryListArray.add("SV");
        countryListArray.add("GQ");
        countryListArray.add("ER");
        countryListArray.add("EE");
        countryListArray.add("ET");
        //F
        countryListArray.add("FK");
        countryListArray.add("FO");
        countryListArray.add("FJ");
        countryListArray.add("FI");
        countryListArray.add("FR");
        countryListArray.add("GF");
        countryListArray.add("PF");
        countryListArray.add("TF");
        //G
        countryListArray.add("GA");
        countryListArray.add("GM");
        countryListArray.add("GE");
        countryListArray.add("DE");
        countryListArray.add("GH");
        countryListArray.add("GI");
        countryListArray.add("GR");
        countryListArray.add("GL");
        countryListArray.add("GD");
        countryListArray.add("GP");
        countryListArray.add("GU");
        countryListArray.add("GT");
        countryListArray.add("GG");
        countryListArray.add("GN");
        countryListArray.add("GW");
        countryListArray.add("GY");
        //H
        countryListArray.add("HT");
        countryListArray.add("HM");
        countryListArray.add("VA");
        countryListArray.add("HN");
        countryListArray.add("HU");
        //I
        countryListArray.add("IS");
        countryListArray.add("IN");
        countryListArray.add("ID");
        countryListArray.add("IR");
        countryListArray.add("IQ");
        countryListArray.add("IE");
        countryListArray.add("IM");
        countryListArray.add("IL");
        countryListArray.add("IT");
        //J
        countryListArray.add("JM");
        countryListArray.add("JP");
        countryListArray.add("JE");
        countryListArray.add("JO");
        //K
        countryListArray.add("KZ");
        countryListArray.add("KE");
        countryListArray.add("KI");
        countryListArray.add("KP");
        countryListArray.add("KR");
        countryListArray.add("KW");
        countryListArray.add("KG");
        //L
        countryListArray.add("LA");
        countryListArray.add("LV");
        countryListArray.add("LB");
        countryListArray.add("LS");
        countryListArray.add("LR");
        countryListArray.add("LY");
        countryListArray.add("LI");
        countryListArray.add("LT");
        countryListArray.add("LU");
        //M
        countryListArray.add("MK");
        countryListArray.add("MG");
        countryListArray.add("MW");
        countryListArray.add("MY");
        countryListArray.add("MV");
        countryListArray.add("ML");
        countryListArray.add("MT");
        countryListArray.add("MH");
        countryListArray.add("MQ");
        countryListArray.add("MR");
        countryListArray.add("MU");
        countryListArray.add("YT");
        countryListArray.add("MX");
        countryListArray.add("FM");
        countryListArray.add("MD");
        countryListArray.add("MC");
        countryListArray.add("MN");
        countryListArray.add("ME");
        countryListArray.add("MS");
        countryListArray.add("MA");
        countryListArray.add("MZ");
        countryListArray.add("MM");
        //N
        countryListArray.add("NA");
        countryListArray.add("NR");
        countryListArray.add("NP");
        countryListArray.add("NL");
        countryListArray.add("AN");
        countryListArray.add("NC");
        countryListArray.add("NZ");
        countryListArray.add("NI");
        countryListArray.add("NE");
        countryListArray.add("NG");
        countryListArray.add("NU");
        countryListArray.add("NF");
        countryListArray.add("MP");
        countryListArray.add("NO");
        //O
        countryListArray.add("OM");
        //P
        countryListArray.add("PK");
        countryListArray.add("PW");
        countryListArray.add("PS");
        countryListArray.add("PA");
        countryListArray.add("PG");
        countryListArray.add("PY");
        countryListArray.add("PE");
        countryListArray.add("PH");
        countryListArray.add("PN");
        countryListArray.add("PL");
        countryListArray.add("PT");
        countryListArray.add("PR");
        //Q
        countryListArray.add("QA");
        //R
        countryListArray.add("RE");
        countryListArray.add("RO");
        countryListArray.add("RU");
        countryListArray.add("RW");
        //S
        countryListArray.add("BL");
        countryListArray.add("SH");
        countryListArray.add("KN");
        countryListArray.add("LC");
        countryListArray.add("MF");
        countryListArray.add("PM");
        countryListArray.add("VC");
        countryListArray.add("WS");
        countryListArray.add("SM");
        countryListArray.add("ST");
        countryListArray.add("SA");
        countryListArray.add("SN");
        countryListArray.add("RS");
        countryListArray.add("SC");
        countryListArray.add("SL");
        countryListArray.add("SG");
        countryListArray.add("SK");
        countryListArray.add("SI");
        countryListArray.add("SB");
        countryListArray.add("SO");
        countryListArray.add("ZA");
        countryListArray.add("GS");
        countryListArray.add("SS");
        countryListArray.add("ES");
        countryListArray.add("LK");
        countryListArray.add("SD");
        countryListArray.add("SR");
        countryListArray.add("SJ");
        countryListArray.add("SZ");
        countryListArray.add("SE");
        countryListArray.add("CH");
        countryListArray.add("SY");
        //T
        countryListArray.add("TW");
        countryListArray.add("TJ");
        countryListArray.add("TZ");
        countryListArray.add("TH");
        countryListArray.add("TL");
        countryListArray.add("TG");
        countryListArray.add("TK");
        countryListArray.add("TO");
        countryListArray.add("TT");
        countryListArray.add("TN");
        countryListArray.add("TR");
        countryListArray.add("TM");
        countryListArray.add("TC");
        countryListArray.add("TV");
        //U
        countryListArray.add("UG");
        countryListArray.add("UA");
        countryListArray.add("AE");
        countryListArray.add("GB");
        countryListArray.add("US");
        countryListArray.add("UM");
        countryListArray.add("UY");
        countryListArray.add("UZ");
        //V
        countryListArray.add("VU");
        countryListArray.add("VE");
        countryListArray.add("VN");
        countryListArray.add("VI");
        //W
        countryListArray.add("WF");
        countryListArray.add("EH");
        //X - Z
        countryListArray.add("YE");
        countryListArray.add("ZM");
        countryListArray.add("ZW");


        //init  locationSettingOnOffLottie
        locationSettingOnOffLottie = findViewById(R.id.animation_view);
        howToUseIconLottie = findViewById(R.id.how_to_use_icon_lottie);
        howToUseIconLottie.setRepeatCount(200);
        howToUseIconLottie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHowToUseDialog();
            }
        });


        bottomAnimReverse = AnimationUtils.loadAnimation(this, R.anim.bottom_animation_reverse);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        blinkAnim = AnimationUtils.loadAnimation(this, R.anim.blink_animation);
        blinkAnim2 = AnimationUtils.loadAnimation(this, R.anim.blink_animation_2);
        buttonBounceAnim = AnimationUtils.loadAnimation(this, R.anim.bounce_button_anim);


        // copyLocationImg init
        memoryAddIconImg = findViewById(R.id.copy_locationImg);
        memoryAddIconImg.startAnimation(blinkAnim2);


        memoryAddIconImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //copyLocationImg.startAnimation(buttonBounceAnim);
                memoryAddIconImg.clearAnimation();

                Toast.makeText(MapsActivity.this, "Location coordinates are copied to clipboard for your use.", Toast.LENGTH_LONG).show();


                final double latitude = mLastLocation.getLatitude();
                final double longitude = mLastLocation.getLongitude();
                currentLatLong = new LatLng(latitude, longitude);
                String currentLat = String.valueOf(latitude);
                String currentLong = String.valueOf(longitude);
                String currentLocCoordinates = currentLat + "," + currentLong;

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(null, currentLocCoordinates);
                if (clipboard == null) return;
                clipboard.setPrimaryClip(clip);

                ShowAddMomentSnackBar();

            }
        });


        // countries selection
        countriesSelectImg = findViewById(R.id.countries_select);
        // speechToTextImg = findViewById(R.id.speech);


        /*speechToTextImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                speechToTextImg.startAnimation(buttonBounceAnim);
                //Show speech dialog
                Intent speechToTextIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speechToTextIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speechToTextIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                speechToTextIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi say something");

                //start intent
                try {

                    startActivityForResult(speechToTextIntent, REQUEST_CODE_SPEECH_INPUT);

                }catch (Exception e) {

                    Toast.makeText(MapsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });*/

        countriesSelectImg.setOnClickListener(v -> {


            countriesSelectImg.startAnimation(buttonBounceAnim);
            //initiate dialog
            countryCodeSearchDialog = new Dialog(MapsActivity.this);
            Objects.requireNonNull(countryCodeSearchDialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimationSlideAndDisappear;

            //set custom dialog view
            countryCodeSearchDialog.setContentView(R.layout.country_dialog);
            //set custom height and width (650, 800)
            //countryCodeSearchDialog.getWindow().setLayout(650, 800);
            countryCodeSearchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //show dialog
            countryCodeSearchDialog.show();
            countryCodeSearchDialog.setCancelable(true);
            countryCodeSearchDialog.setCanceledOnTouchOutside(false);


            //initiate and assign variable in countryCodeSearch dialog
            dialogEditText = countryCodeSearchDialog.findViewById(R.id.dialog_edit_text);
            countryList = countryCodeSearchDialog.findViewById(R.id.country_list_view);
            helpMessageImg = countryCodeSearchDialog.findViewById(R.id.help_img);


            helpMessageImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MapsActivity.this, "Choose your country code to return address search queries from only that country for faster results", Toast.LENGTH_LONG).show();

                }
            });

            dialogEditText.setSingleLine();
            //initiate array list
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MapsActivity.this, android.R.layout.simple_list_item_1, countryListArray);
            // set arrayAdapter
            countryList.setAdapter(arrayAdapter);

            dialogEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                    // filter the list
                    arrayAdapter.getFilter().filter(charSequence);

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            countryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // what to do on item click
                    places.setCountries(arrayAdapter.getItem(position));

                    countryCodeSearchDialog.dismiss();
                    Snackbar.make(Objects.requireNonNull(mapFragment.getView()), "Place search queries limited to: " + arrayAdapter.getItem(position), Snackbar.LENGTH_LONG).show();

                }
            });


        });


        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        wholeScreen = findViewById(R.id.mapScreenLayout);


        navigationView.setCheckedItem(R.id.nav_map);
        navigationView.bringToFront();


        bottomCardview = findViewById(R.id.bottomCardview);

        // mapTypeCardView = findViewById(R.id.mapTypeCardView);

       /* mapTypeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapTypeCardView.startAnimation(buttonBounceAnim);
                showMapTypeDialog();
            }
        });
*/

        // initiate share memory card view
        shareMemoryCardView = findViewById(R.id.share_memoryCard);
        shareMemoryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //takeScreenShot(getWindow().getDecorView().getRootView(), "result");

                //shareMemoryCardView.setVisibility(View.GONE);


                //START...REMOVE BLUE DOT BEFORE CAPTURE
               /* if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled(false);*/
                //END

                captureScreen();


            }

        });


        //initialize autocompleteCardView for search
        autocompleteCardView = findViewById(R.id.autocompleteCardView);


        searchPlace = (ImageView) findViewById(R.id.searchPlace);
        searchPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (autocompleteCardView.getVisibility() == View.VISIBLE) {
                    autocompleteCardView.setVisibility(View.GONE);
                } else {
                    autocompleteCardView.setVisibility(View.VISIBLE);
                }

            }
        });


        // initialize traffic legend cardView
        trafficLegend_cardView = findViewById(R.id.trafficLegend_cardView);
        trafficLegendButton = findViewById(R.id.trafficLegend_button);


        // distanceTextView = findViewById(R.id.distanceText);
        // final LinearLayout bottomSheet = findViewById(R.id.bottomSheet);
        // initialize navigateCardView
        navigateCardView = findViewById(R.id.navigateCardView);
        tapNavigateCardView = findViewById(R.id.tapNavigateCardView);

        //final CardView bottomSheetLayout = findViewById(R.id.bottomSheetLayout);
        // set OnClick Listener for  navigateCardView and perform navigation task
        navigateCardView.setOnClickListener(v -> {

            showNavigationDialog();


            /*Uri navIntentUri = Uri.parse("google.navigation:q=" + destinationLat + "," + destinationLong);
            Intent navigationIntent = new Intent(Intent.ACTION_VIEW, navIntentUri);
*/
            //for checking if more than 1 navigation app is available fr task and den choose one...remove setPackage fr this
            //STARTS

           /* PackageManager packageManager = getPackageManager();
            List<ResolveInfo> activities = packageManager.queryIntentActivities(navigationIntent, PackageManager.MATCH_ALL);
            boolean isIntentSafe = activities.size() > 1;

            if (isIntentSafe) {

                Intent chooser = Intent.createChooser(navigationIntent, "Navigate using...");
                startActivity(chooser);


            } else if (navigationIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(navigationIntent);

            }*/
            //ENDS


            // or use code below for checking if at least a map application is installed on the device

            //if u dnt want to set google maps as default, remove setPackage
            //navigationIntent.setPackage("com.google.android.apps.maps");

            //checking if at least a map application is installed on the device so it opens it automatically for the navigation
            // else the app crashes
            //if more than one map application is installed, user is asked which to use for the action
            //just using if else statements also works perfectly without try catch
            /*try {
                if (navigationIntent.resolveActivity(getPackageManager()) != null) {

                    navigationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(navigationIntent);

                }
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), "Google map is not installed on your device... \nPlease Install Google maps to handle task", Toast.LENGTH_SHORT).show();
                Uri gmmIntentUri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                Intent googleMapsIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                googleMapsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(googleMapsIntent);

            }*/





           /* BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MapsActivity.this, R.style.BottomSheetDialogTheme);
            View navigateBottomSheetView = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.bottomsheet_navigate, (LinearLayout) findViewById(R.id.bottomSheet));

            navigateBottomSheetView.findViewById(R.id.bottomSheetNavigateCardView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri navIntentUri = Uri.parse("google.navigation:q=" + destinationLat + "," + destinationLong);
                    Intent navigationIntent = new Intent(Intent.ACTION_VIEW, navIntentUri);
                    navigationIntent.setPackage("com.google.android.apps.maps");
                    //checking if at least a map application is installed on the device so it opens it automatically for the navigation
                    // else the app crashes
                    //if more than one map application is installed, user is asked which to use for the action


                    if (navigationIntent.resolveActivity(getPackageManager()) != null) {

                        navigationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(navigationIntent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Google map is not installed on your device... \nPlease Install Google maps to handle task", Toast.LENGTH_SHORT).show();
                        Uri gmmIntentUri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                        Intent googleMapsIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                        googleMapsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(googleMapsIntent);

                    }


                }
            });*/

        });


        tapNavigateCardView.setOnClickListener(v -> {

            showTapNavigationDialog();


            // navigation intent
            /*Uri navIntentUri = Uri.parse("google.navigation:q=" + mDestinationLat + "," + mDestinationLong); //google.navigation:q=
            Intent tapNavigationIntent = new Intent(Intent.ACTION_VIEW, navIntentUri);
            tapNavigationIntent.setPackage("com.google.android.apps.maps");
            //checking if at least a map application is installed on the device so it opens it automatically for the navigation
            // else the app crashes
            //if more than one map application is installed, user is asked which to use for the action
            //just using if else statements also works perfectly without try catch
            try {
                if (tapNavigationIntent.resolveActivity(getPackageManager()) != null) {

                    tapNavigationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Intent.createChooser(tapNavigationIntent, "Navigate using..."));
                    //startActivity(tapNavigationIntent);

                }
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), "Google map is not installed on your device... \nPlease Install Google maps to handle task", Toast.LENGTH_SHORT).show();
                Uri gmmIntentUri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                Intent googleMapsIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                googleMapsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(googleMapsIntent);

            }*/







           /* BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MapsActivity.this, R.style.BottomSheetDialogTheme);
            View navigateBottomSheetView = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.bottomsheet_navigate, (LinearLayout) findViewById(R.id.bottomSheet));

            navigateBottomSheetView.findViewById(R.id.bottomSheetNavigateCardView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri navIntentUri = Uri.parse("google.navigation:q=" + destinationLat + "," + destinationLong);
                    Intent navigationIntent = new Intent(Intent.ACTION_VIEW, navIntentUri);
                    navigationIntent.setPackage("com.google.android.apps.maps");
                    //checking if at least a map application is installed on the device so it opens it automatically for the navigation
                    // else the app crashes
                    //if more than one map application is installed, user is asked which to use for the action


                    if (navigationIntent.resolveActivity(getPackageManager()) != null) {

                        navigationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(navigationIntent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Google map is not installed on your device... \nPlease Install Google maps to handle task", Toast.LENGTH_SHORT).show();
                        Uri gmmIntentUri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                        Intent googleMapsIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                        googleMapsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(googleMapsIntent);

                    }


                }
            });*/


        });


        //  MaterialCardView bottomSheetNavigateCardView = findViewById(R.id.bottomSheetNavigateCardView);
        /*bottomSheetNavigateCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri navIntentUri = Uri.parse("google.navigation:q=" + destinationLat + "," + destinationLong);
                Intent navigationIntent = new Intent(Intent.ACTION_VIEW, navIntentUri);
                navigationIntent.setPackage("com.google.android.apps.maps");
                //checking if at least a map application is installed on the device so it opens it automatically for the navigation
                // else the app crashes
                //if more than one map application is installed, user is asked which to use for the action


                if (navigationIntent.resolveActivity(getPackageManager()) != null) {

                    navigationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(navigationIntent);

                } else {
                    Toast.makeText(getApplicationContext(), "Google map is not installed on your device... \nPlease Install Google maps to handle task", Toast.LENGTH_SHORT).show();
                    Uri gmmIntentUri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                    Intent googleMapsIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                    googleMapsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(googleMapsIntent);

                }
            }
        });
*/

        // floating button
        final FloatingActionButton shareLocationFloatingActionButton = (FloatingActionButton) findViewById(R.id.shareLocationFab);
        final FloatingActionButton trafficFloatingActionButton = (FloatingActionButton) findViewById(R.id.traffficFab);
        //FloatingActionButton darkLightMapfloatingActionButton = (FloatingActionButton) findViewById(R.id.dark_light_MapFab);


        //both codes work but i reduced it by a line of code
        shareLocationFloatingActionButton.setOnClickListener(v -> {

            shareLocationFloatingActionButton.startAnimation(buttonBounceAnim);

            currentPosition = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

            currentLocationLat = String.valueOf(mLastLocation.getLatitude());
            currentLocationLong = String.valueOf(mLastLocation.getLongitude());


            Intent shareLocationIntent = new Intent(Intent.ACTION_SEND);
            shareLocationIntent.setType("text/plain");
            String messageBody = "Hi there!\nClick the link below to find my location" + "\n" + "http://maps.google.com/?q=" + currentLocationLat + "," + currentLocationLong;
            String messageSubject = "My Current Location";
            shareLocationIntent.putExtra(Intent.EXTRA_TEXT, messageBody);
            shareLocationIntent.putExtra(Intent.EXTRA_SUBJECT, messageSubject);
            startActivity(Intent.createChooser(shareLocationIntent, "Share Location with friend via..."));

        });


        trafficFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                trafficFloatingActionButton.startAnimation(buttonBounceAnim);
                // set traffic Overlay when visible or not
                if (mMap.isTrafficEnabled()) {
                    mMap.setTrafficEnabled(false);
                    Toast.makeText(MapsActivity.this, "Traffic Disabled", Toast.LENGTH_SHORT).show();
                } else {
                    mMap.setTrafficEnabled(true);

                    Toast.makeText(MapsActivity.this, "Traffic Enabled", Toast.LENGTH_SHORT).show();
                }

                // Toast.makeText(DriverMapActivity.this, "You clicked traffic", Toast.LENGTH_SHORT).show();

                //hide or show traffic legend
                if (trafficLegend_cardView.getVisibility() == View.VISIBLE) {
                    trafficLegend_cardView.setVisibility(View.GONE);
                    trafficLegend_cardView.setAnimation(bottomAnimReverse);

                } else {

                    trafficLegend_cardView.setVisibility(View.VISIBLE);
                    trafficLegend_cardView.setAnimation(bottomAnim);
                }


            }
        });


        //used when activity has a default action bar

        /*getSupportActionBar().setTitle("bYEEP app");
        getSupportActionBar().setSubtitle("beep to your convenience");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // this line sets default back icon
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_icon);  // use this line to customize ur back icon


         */


        // create and toggle ur drawer menu icon with rotating animation
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);


        //  navigate to current location
        final MaterialCardView locationImageLayout = findViewById(R.id.locationImageLayout);
        locationImageLayout.setOnClickListener(v -> {
            locationImageLayout.startAnimation(buttonBounceAnim);

            if (!isLocationEnabled(this)) {

                showLocationSettingDialog();
            } else {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                fusedLocationProviderClient.getLastLocation()
                        .addOnFailureListener(e -> Toast.makeText(MapsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show())
                        .addOnSuccessListener(location -> {
                            LatLng userLoc = new LatLng(location.getLatitude(), location.getLongitude());
                            //double offset = 0.0016;
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLoc.latitude, userLoc.longitude), 17f));


                        });

            }


        });


        //location_switch

        locationSwitch = findViewById(R.id.location_switch);


        locationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    //add multiple markers for predefined pickup/destination locations when online

                    startLocationUpdates();
                    displayLocation();

                    Snackbar.make(Objects.requireNonNull(mapFragment.getView()), " You are Online... On the Road? Drive safely", Snackbar.LENGTH_LONG).show();

                    displayOnOffButton.setText(R.string.you_are_onRoad);

                } else {
                    stopLocationUpdates();
                    if (mUserMarker != null) {
                        mUserMarker.remove();
                    }

                    if (poiMarker != null) {
                        poiMarker.remove();
                    }


                    //erasePolylines();

                    if (navigateCardView.getVisibility() == View.VISIBLE) {
                        navigateCardView.setVisibility(View.GONE);
                        if (mBlinkingCardView) {
                            navigateCardView.clearAnimation();
                            mBlinkingCardView = false;
                        }
                    }

                    if (tapNavigateCardView.getVisibility() == View.VISIBLE) {
                        tapNavigateCardView.setVisibility(View.GONE);
                        if (mBlinkingCardView) {
                            tapNavigateCardView.clearAnimation();
                            mBlinkingCardView = false;
                        }
                    }

                    if (destinationMarker != null) {
                        destinationMarker.remove();

                    }

                    //remove polyline if it drawn on map...
                    if (greyPolyline != null) {
                        greyPolyline.remove();
                    }

                    // mMap.clear clears all markers and polylines on the map...not needed
                    //mMap.clear();


                    //remove animation for route
                   /* mMap.clear();
                    if (handler != null) {
                        handler.removeCallbacks(drawPathRunnable);
                    }*/


                    Snackbar.make(Objects.requireNonNull(mapFragment.getView()), "You are Offline...off the Road? Stay safe", Snackbar.LENGTH_LONG).show();


                    displayOnOffButton.setText(R.string.you_are_offRoad);
                    displayOnOffButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bottom_card_button_design, null));
                    displayOnOffButton.setTextColor(getResources().getColor(R.color.quantum_white_text));

                }


            }

        });

        displayOnOffButton = (Button) findViewById(R.id.displayOnOffButton);

        // polylines when using  getRouteToDestination();
        polylines = new ArrayList<>();

//         polylines when using  getDirection();
        polyLineList = new ArrayList<>();

        //distanceShowText = findViewById(R.id.distanceShowText);


        //String apiKey = getString(R.string.api_key);
        // String apiKey = getResources().getString(R.string.google_maps_key);

        String apiKey = "AIzaSyD6ftXPVAisZGl-Uev8oK4JWJIFBmvym8o";  //AIzaSyD6ftXPVAisZGl-Uev8oK4JWJIFBmvym8o....AIzaSyBEVeqt-U8d1XoWPg-fMeeHhfXliAn4d74
        if (!Places.isInitialized()) {
            // initialize sdk
            Places.initialize(getApplicationContext(), apiKey);
        }


        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);


       /* typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .setTypeFilter(3)
                .build();

        */


        // Initialize the AutocompleteSupportFragment.
        places = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.OtrafycAutocomplete_fragment);


        //setting bounds to a location
        //places.setTypeFilter(TypeFilter.ADDRESS);
        //places.setLocationBias(RectangularBounds.newInstance(new LatLng(6.666667, -1.616667), new LatLng(6.666667, -1.616667)));

        // specify countries to show results


        // places.setCountries("GH");
        // Specify the types of places data to return.
        if (places != null) {
            //just added Place.Field.ADDRESS
            places.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG));
            places.setHint("Where to?");
            /*places.getSharedElementEnterTransition();
            places.getSharedElementReturnTransition();*/
        }
        // Set up a PlaceSelectionListener to handle the response
        if (places != null) {
            places.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NonNull Place place) {
                    if (locationSwitch.isChecked()) {

                        //destinationLoc = place.getName().toString();

                        //  using destinationLong and destinationLat  works fine by adding
                        // Place.Field.LAT_LNG in the places.setPlaceFields
                        destinationLat = String.valueOf(Objects.requireNonNull(place.getLatLng()).latitude);
                        destinationLong = String.valueOf(place.getLatLng().longitude);

                        destinationName = String.valueOf(place.getName());

                        destinationLatLng = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                        //destinationLoc = destinationLoc.replace("", "");

                        //using mMap.clear(); clears the map of any marker or routes(polylines)...
                        // mMap.clear();

                        //remove polyline if it drawn on map...
                        if (greyPolyline != null) {
                            greyPolyline.remove();
                        }
                        for (Polyline line : polylines) {
                            line.remove();
                        }

                        // Log.d("TAG", destinationLoc);

                        //  remove destinationMarker and add it to new selected place
                        if (destinationMarker != null) {
                            destinationMarker.remove();
                        }

                        if (poiMarker != null) {
                            poiMarker.remove();
                        }

                        Log.i("TAG", "Place: " + place.getName() + "," + place.getId());
                        destinationMarker = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).icon(bitmapDescriptorFromVector(MapsActivity.this, R.drawable.ic_user_destination_marker)).title(place.getName()).snippet("Destination")); //snippet(destinationLat + "," + destinationLong)
                        destinationMarker.showInfoWindow();
                        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15.0f));
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                .target(place.getLatLng())
                                .zoom(16f)
                                .tilt(60f)
                                .build()));


                        getDirection();

                        if (tapNavigateCardView.getVisibility() == View.VISIBLE) {
                            tapNavigateCardView.setVisibility(View.GONE);
                            if (mBlinkingCardView) {

                                tapNavigateCardView.clearAnimation();
                                mBlinkingCardView = false;
                            }
                        }


                        navigateCardView.setVisibility(View.VISIBLE);
                        if (!mBlinkingCardView) {
                            navigateCardView.startAnimation(blinkAnim);
                            mBlinkingCardView = true;
                        }

                        //openBottomSheet();


                        //

                        currentPosition = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                        Location startLocation = new Location("");
                        startLocation.setLatitude(mLastLocation.getLatitude());
                        startLocation.setLongitude(mLastLocation.getLongitude());

                        Location destinationLocation = new Location("");
                        destinationLocation.setLatitude(place.getLatLng().latitude);
                        destinationLocation.setLongitude(place.getLatLng().longitude);

                        float distance = startLocation.distanceTo(destinationLocation);
                        DecimalFormat DecimalPlace = new DecimalFormat("#0.00");

                        // displayOnOffButton.setBackgroundColor(getResources().getColor(R.color.quantum_lightblue700));
                        //displayOnOffButton.setTextSize(12);
                        displayOnOffButton.setText(String.format("~ %s km to %s", DecimalPlace.format(distance * 0.001), destinationName));
                        // displayOnOffButton.setBackground(getResources().getDrawable(R.drawable.distance_button_backgrnd));
                        // displayOnOffButton.setTextColor(getResources().getColor(R.color.quantum_lightblue700));


                        //set visibilities
                       /* displayOnOffButton.setVisibility(View.INVISIBLE);
                        distanceShowText.setVisibility(View.VISIBLE);
                        distanceShowText.setText(String.format("%s km away from \n%s", DecimalPlace.format(distance * 0.001), destinationName));
    */

                        //getRouteToDestination();

                    } else {
                        // locationSwitch.setChecked(false);
                        Toast.makeText(MapsActivity.this, "Please you are Offline... offRoad? Stay safe", Toast.LENGTH_SHORT).show();
                        //distanceShowText.setVisibility(View.INVISIBLE);
                        // displayOnOffButton.setVisibility(View.VISIBLE);


                        //displayOnOffButton.setBackground(getResources().getDrawable(R.drawable.bottom_card_button_design));
                        displayOnOffButton.setText(R.string.you_are_offRoad);
                        // displayOnOffButton.setTextSize(14);
                        //  displayOnOffButton.setTextColor(getResources().getColor(R.color.quantum_white_text));
                        // displayOnOffButton.setBackgroundColor(getResources().getColor(R.color.smokyBlack));


                    }


                }

                @Override
                public void onError(@NonNull Status status) {
                    // Toast.makeText(MapsActivity.this, "" + status.toString(), Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "An error occurred: " + status);
                }
            });
        }





      /*  Gobtn = (Button) findViewById(R.id.btnGo);

        editPlace = (EditText) findViewById(R.id.placeEdit);

        Gobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destination = editPlace.getText().toString();
                destination = destination.replace(" ", "+");  // replace destination address with + to search data
                Log.d("RICHIE", destination);

                getDirection();


            }
        });    */


        setUpLocation();

        mService = MyCommon.getGoogleAPI();

        animateNavigationDrawer();


        // call verifyStoragePermission on activity start
        verifyStoragePermission(this);

        updateValuesFromBundle(savedInstanceState);


        mapFragment.setRetainInstance(true);


        /*final MapInfoWindowFragment mapInfoWindowFragment =
                (MapInfoWindowFragment) getSupportFragmentManager().findFragmentById(R.id.interactive_infoWindowMap);

        final InfoWindow infoWindow = new InfoWindow(poiMarker, markerSpec, mapFragment);
        // Shows the InfoWindow or hides it if it is already opened.
        Objects.requireNonNull(mapInfoWindowFragment).infoWindowManager().toggle(infoWindow, true);
*/
    }

    /* private void showMapTypeDialog() {
         mapTypeDialog.setContentView(R.layout.map_type_select_dialog);
         Objects.requireNonNull(mapTypeDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
         //appear and disappear anim
         mapTypeDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationHorizontalSlide;


         defaultMapCardView = mapTypeDialog.findViewById(R.id.default_map_cardview);
         darkMapCardView = mapTypeDialog.findViewById(R.id.dark_map_cardview);
         satelliteMapCardView = mapTypeDialog.findViewById(R.id.satellite_map_cardview);

         defaultMapCardView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 //mMap.setMapType(GoogleMap.MAP_TYPE_NONE);

                 if (mMap.getMapType()==GoogleMap.MAP_TYPE_SATELLITE){
                     mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MapsActivity.this, R.raw.new_day_map));
                 }

                 try {
                     boolean isSuccess = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MapsActivity.this, R.raw.new_day_map));

                     if (!isSuccess) {
                         Toast.makeText(MapsActivity.this, "Failed to load map...Check your network connection !",  Toast.LENGTH_SHORT).show();
                         Log.e("ERROR", "Failed to load map");

                     }

                 } catch (Resources.NotFoundException e) {
                     e.printStackTrace();
                 }
                     defaultMapCardView.setStrokeColor(getResources().getColor(R.color.newBlue1));

                     darkMapCardView.setStrokeColor(getResources().getColor(R.color.white));
                     satelliteMapCardView.setStrokeColor(getResources().getColor(R.color.white));


             }
         });

         darkMapCardView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
               //  mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                 if (mMap.getMapType()==GoogleMap.MAP_TYPE_SATELLITE){
                     mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MapsActivity.this, R.raw.new_night_map_style));
                 }

                 try {
                     boolean isSuccess = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(MapsActivity.this, R.raw.new_night_map_style));

                     if (!isSuccess) {
                         Toast.makeText(MapsActivity.this, "Failed to load map...Check your network connection !",  Toast.LENGTH_SHORT).show();
                         Log.e("ERROR", "Failed to load map");

                     }

                 } catch (Resources.NotFoundException e) {
                     e.printStackTrace();
                 }
                 darkMapCardView.setStrokeColor(getResources().getColor(R.color.newBlue1));

                 satelliteMapCardView.setStrokeColor(getResources().getColor(R.color.white));
                 defaultMapCardView.setStrokeColor(getResources().getColor(R.color.white));

             }
         });

         satelliteMapCardView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                 mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                 satelliteMapCardView.setStrokeColor(getResources().getColor(R.color.newBlue1));

                 defaultMapCardView.setStrokeColor(getResources().getColor(R.color.white));
                 darkMapCardView.setStrokeColor(getResources().getColor(R.color.white));
             }
         });


         mapTypeDialog.show();
         mapTypeDialog.setCancelable(true);
         mapTypeDialog.setCanceledOnTouchOutside(true);



     }
 */
    private void showHowToUseDialog() {
        howToUseDialog.setContentView(R.layout.how_to_use_dialog_layout);
        Objects.requireNonNull(howToUseDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //appear and disappear anim
        howToUseDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationVerticalSlide;

        howToUseDialog.show();
        howToUseDialog.setCancelable(true);
        howToUseDialog.setCanceledOnTouchOutside(true);

        howToUseIconLottie.cancelAnimation();

    }


    public void captureScreen() {


        GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {

            Bitmap bitmap;

            @Override
            public void onSnapshotReady(Bitmap snapshot) {
                try {
                    bitmap = snapshot;
                    getWindow().getDecorView().findViewById(android.R.id.content).setDrawingCacheEnabled(true);
                    Bitmap backBitmap = getWindow().getDecorView().findViewById(android.R.id.content).getDrawingCache();
                    Bitmap bmOverlay = Bitmap.createBitmap(backBitmap.getWidth(), backBitmap.getHeight(), backBitmap.getConfig());
                    Canvas canvas = new Canvas(bmOverlay);
                    canvas.drawBitmap(snapshot, new Matrix(), null);
                    canvas.drawBitmap(backBitmap, 0, 20000, null);

                    FileOutputStream fout = null;


                    //init current date time setter
                    Calendar calendar = Calendar.getInstance();
                    String timeFormat = "dd-MM-yy hh:mm:ss a";
                    //START...use this to set local time  and format
                    //SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
                    //END
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);
                    String dateTime = simpleDateFormat.format(calendar.getTime());


                    //START...Use this to show filePath to show default generated numbered image name with .jpeg (eg.  153546225653.jpeg
                    // String filePath = System.currentTimeMillis() + ".jpeg";

                    //END


                    //START...Use this to show custom filePath with image name OTrafyc Screenshot-02-12-20 10:12:57 PM.jpeg (eg.  OTrafyc Screenshot-02-12-20 10:12:57 PM.jpeg)
                    //comment this if String filePath = System.currentTimeMillis() + ".jpeg" is used
                    String fileName = "OTrafyc_Screenshot";
                    String filePath = fileName + "-" + dateTime + ".jpeg";
                    //END


                    try {
                        fout = openFileOutput(filePath, MODE_WORLD_READABLE);

                        int qualityOfImage = 100;
                        // Write the string to the file
                        bmOverlay.compress(Bitmap.CompressFormat.JPEG, qualityOfImage, fout);
                        fout.flush();
                        fout.close();

                        //START...SHOW BLUE DOT AFTER CAPTURE
                       /* if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        mMap.setMyLocationEnabled(true);*/
                        //END


                    } catch (FileNotFoundException e) {
                        Toast.makeText(MapsActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        // TODO Auto-generated catch block
                        Log.d("ImageCapture", "FileNotFoundException");
                        Log.d("ImageCapture", Objects.requireNonNull(e.getMessage()));
                        filePath = "";
                    } catch (IOException e) {
                        Toast.makeText(MapsActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        // TODO Auto-generated catch block
                        Log.d("ImageCapture", "IOException");
                        Log.d("ImageCapture", e.getMessage());
                        filePath = "";
                    }

                    openShareImageDialog(filePath);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        mMap.snapshot(callback);


    }

    public void openShareImageDialog(String filePath) {

        File file = this.getFileStreamPath(filePath);


        if (!filePath.equals("")) {
            final ContentValues values = new ContentValues(2);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DATA, file.getPath());
            final Uri contentUriFile = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);


            //String memoryTitleText = memoryTitle.getEditText().getText().toString().trim();

            //START... using  getText same time of both  strings of TextInputLayout and TextInputEditText causes app to crash since either should do it but not both
            //String memoryTitleText2 = memoryTitle_editText2.getText().toString().trim();
            //String memoryDescription2 = memoryDescription_editText2.getText().toString().trim();
            //  String memoryDescriptionText = memoryDescription.getEditText().getText().toString().trim();


            String memoryTitleText = memoryTitle.getEditText().getText().toString().trim();
            String memoryDescriptionText = memoryDescription.getEditText().getText().toString().trim();


            final Intent momentShareIntent = new Intent(android.content.Intent.ACTION_SEND);
            momentShareIntent.setType("image/jpeg"); //jpeg
            momentShareIntent.putExtra(Intent.EXTRA_SUBJECT, memoryTitleText);
            momentShareIntent.putExtra(Intent.EXTRA_TEXT, memoryDescriptionText);
            momentShareIntent.putExtra(android.content.Intent.EXTRA_STREAM, contentUriFile);
            startActivity(Intent.createChooser(momentShareIntent, "Share moment via . . ."));
        } else {
            //This is a custom class I use to show dialogs...simply replace this with whatever you want to show an error message, Toast, etc.
            // DialogUtilities.showOkDialogWithText(this, R.string.shareImageFailed);

            Toast.makeText(getBaseContext(), "Error!", Toast.LENGTH_SHORT).show();
        }

    }

    //START
    //this takes screenshot of wholeScreen and share it very great but map is blank since with Google api v2,
    // GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() should be used

   /* protected File takeScreenShot(View view, String fileName) {


        //init current date time setter
        Calendar calendar = Calendar.getInstance();

        String timeFormat = "dd-MM-yy  hh:mm:ss a";


        //START...use this to set local time  and format
        //SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
        //END
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);
        String dateTime = simpleDateFormat.format(calendar.getTime());

        try {
            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/Screenshots";
            File fileDir = new File(dirPath);
            if (!fileDir.exists()) {
                boolean mkdir = fileDir.mkdir();
            }

            String path = dirPath+"/"+fileName+"-"+dateTime+".jpeg";


            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(path);

            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);

            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            fileDir.setReadable(true, false);



            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imageFile));
            sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            sharingIntent.setType("image/png");
            String shareBody = "In Tweecher,  My highest score with screen shot";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "My Tweecher score");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);


            startActivity(Intent.createChooser(sharingIntent, "Share via"));

            return  imageFile;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }*/

    // END


    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSION_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermission(Activity activity) {

        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSION_STORAGE, REQUEST_EXTERNAL_STORAGE);


        }

    }









   /* private void shareIt() {
        String imagePath = Environment.getExternalStorageDirectory().toString() + "/Screenshots";
        Uri uri = Uri.fromFile(getExternalFilesDir(imagePath));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/png");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "gdghdhg");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "hfjyfyfd");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }*/


    /*public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }
*/

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void ShowAddMomentSnackBar() {
        int DelayTime = 10000;
        Snackbar snackbar = Snackbar.make(drawer, "Would you like to add this moment you're having to share with friends?", Snackbar.LENGTH_INDEFINITE).setDuration(DelayTime);
        snackbar.setAction("YES", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAddMemoryMarkerDialog();

            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.memoryIconsYellow));
        snackbar.setBackgroundTint(getResources().getColor(R.color.blue1));
        snackbar.show();

    }

    private void showAddMemoryMarkerDialog() {

        memoryMarkerDialog.setContentView(R.layout.moment_marker_dialog_layout);
        Objects.requireNonNull(memoryMarkerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //appear and disappear anim
        memoryMarkerDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationVerticalSlide;


        memoryTitle = memoryMarkerDialog.findViewById(R.id.memoryTitle_editText);
        memoryDescription = memoryMarkerDialog.findViewById(R.id.memoryDescription_editText);

        memoryTitle_editText2 = memoryMarkerDialog.findViewById(R.id.memoryTitle_editText2);
        memoryDescription_editText2 = memoryMarkerDialog.findViewById(R.id.memoryDescription_editText2);


        final Button addMemoryButton = memoryMarkerDialog.findViewById(R.id.addMemory_button);

        addMemoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addMemoryMarker();
            }
        });


        //nit header layout and scroll view
        memoryIconsCardViewHeadLayout = memoryMarkerDialog.findViewById(R.id.memoryIcons_cardView_headLayout);
        memoryIconScrollView = memoryMarkerDialog.findViewById(R.id.memoryIcons_scrollView);


        // init the 18 memory mood card views
        lovelyCardView = memoryMarkerDialog.findViewById(R.id.lovely_cardView);
        happyCardView = memoryMarkerDialog.findViewById(R.id.happy_cardView);
        amazingCardView = memoryMarkerDialog.findViewById(R.id.amazing_cardView);
        sadCardView = memoryMarkerDialog.findViewById(R.id.sad_cardView);
        coolCardView = memoryMarkerDialog.findViewById(R.id.cool_cardView);
        angryCardView = memoryMarkerDialog.findViewById(R.id.angry_cardView);
        painCardView = memoryMarkerDialog.findViewById(R.id.pain_cardView);
        awkwardCardView = memoryMarkerDialog.findViewById(R.id.awkward_cardView);


        kissCardView = memoryMarkerDialog.findViewById(R.id.kiss_cardView);
        laughCardView = memoryMarkerDialog.findViewById(R.id.laugh_cardView);
        teasingCardView = memoryMarkerDialog.findViewById(R.id.teasing_cardView);
        pamperedCardView = memoryMarkerDialog.findViewById(R.id.pampered_cardView);
        cryingCardView = memoryMarkerDialog.findViewById(R.id.crying_cardView1);
        highWeedCardView = memoryMarkerDialog.findViewById(R.id.highweed_cardView);
        scaredCardView = memoryMarkerDialog.findViewById(R.id.scared_cardView);
        shockCardView = memoryMarkerDialog.findViewById(R.id.shock_cardView);
        sleepingCardView = memoryMarkerDialog.findViewById(R.id.sleeping_cardView);
        confuseCardView = memoryMarkerDialog.findViewById(R.id.confused_cardView);


        //this setOnLongClickListener also works aside setOnClickListener but setOnLongClickListener will increase user time on checking
        /*lovelyCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                lovelyCardView.setChecked(!lovelyCardView.isChecked());
                return true;
            }

        });
*/

        // using setOnClickListener for
        //isChecked() is a default boolean class with value = false
        lovelyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!lovelyCardView.isChecked()) {
                    lovelyCardView.setChecked(true);
                    lovelyCardView.setCardElevation(30);


                    happyCardView.setChecked(false);
                    happyCardView.setCardElevation(5);
                    amazingCardView.setChecked(false);
                    amazingCardView.setCardElevation(5);
                    sadCardView.setChecked(false);
                    sadCardView.setCardElevation(5);
                    coolCardView.setChecked(false);
                    coolCardView.setCardElevation(5);
                    angryCardView.setChecked(false);
                    angryCardView.setCardElevation(5);
                    painCardView.setChecked(false);
                    painCardView.setCardElevation(5);
                    awkwardCardView.setChecked(false);
                    awkwardCardView.setCardElevation(5);


                    kissCardView.setChecked(false);
                    kissCardView.setCardElevation(5);
                    laughCardView.setChecked(false);
                    laughCardView.setCardElevation(5);
                    teasingCardView.setChecked(false);
                    teasingCardView.setCardElevation(5);
                    sleepingCardView.setChecked(false);
                    sleepingCardView.setCardElevation(5);
                    pamperedCardView.setChecked(false);
                    pamperedCardView.setCardElevation(5);
                    scaredCardView.setChecked(false);
                    scaredCardView.setCardElevation(5);
                    shockCardView.setChecked(false);
                    shockCardView.setCardElevation(5);
                    cryingCardView.setChecked(false);
                    cryingCardView.setCardElevation(5);
                    highWeedCardView.setChecked(false);
                    highWeedCardView.setCardElevation(5);
                    confuseCardView.setChecked(false);
                    confuseCardView.setCardElevation(5);


                } else {
                    lovelyCardView.setChecked(false);
                    lovelyCardView.setCardElevation(5);
                }
            }
        });

        happyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!happyCardView.isChecked()) {
                    happyCardView.setChecked(true);
                    happyCardView.setCardElevation(30);

                    lovelyCardView.setChecked(false);
                    lovelyCardView.setCardElevation(5);
                    amazingCardView.setChecked(false);
                    amazingCardView.setCardElevation(5);
                    sadCardView.setChecked(false);
                    sadCardView.setCardElevation(5);
                    coolCardView.setChecked(false);
                    coolCardView.setCardElevation(5);
                    angryCardView.setChecked(false);
                    angryCardView.setCardElevation(5);
                    painCardView.setChecked(false);
                    painCardView.setCardElevation(5);
                    awkwardCardView.setChecked(false);
                    awkwardCardView.setCardElevation(5);

                    kissCardView.setChecked(false);
                    kissCardView.setCardElevation(5);
                    laughCardView.setChecked(false);
                    laughCardView.setCardElevation(5);
                    teasingCardView.setChecked(false);
                    teasingCardView.setCardElevation(5);
                    sleepingCardView.setChecked(false);
                    sleepingCardView.setCardElevation(5);
                    pamperedCardView.setChecked(false);
                    pamperedCardView.setCardElevation(5);
                    scaredCardView.setChecked(false);
                    scaredCardView.setCardElevation(5);
                    shockCardView.setChecked(false);
                    shockCardView.setCardElevation(5);
                    cryingCardView.setChecked(false);
                    cryingCardView.setCardElevation(5);
                    highWeedCardView.setChecked(false);
                    highWeedCardView.setCardElevation(5);
                    confuseCardView.setChecked(false);
                    confuseCardView.setCardElevation(5);


                } else {
                    happyCardView.setChecked(false);
                    happyCardView.setCardElevation(5);
                }
            }
        });

        amazingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!amazingCardView.isChecked()) {
                    amazingCardView.setChecked(true);
                    amazingCardView.setCardElevation(30);

                    lovelyCardView.setChecked(false);
                    lovelyCardView.setCardElevation(5);
                    happyCardView.setChecked(false);
                    happyCardView.setCardElevation(5);
                    sadCardView.setChecked(false);
                    sadCardView.setCardElevation(5);
                    coolCardView.setChecked(false);
                    coolCardView.setCardElevation(5);
                    angryCardView.setChecked(false);
                    angryCardView.setCardElevation(5);
                    painCardView.setChecked(false);
                    painCardView.setCardElevation(5);
                    awkwardCardView.setChecked(false);
                    awkwardCardView.setCardElevation(5);
                    kissCardView.setChecked(false);
                    kissCardView.setCardElevation(5);
                    laughCardView.setChecked(false);
                    laughCardView.setCardElevation(5);
                    teasingCardView.setChecked(false);
                    teasingCardView.setCardElevation(5);
                    sleepingCardView.setChecked(false);
                    sleepingCardView.setCardElevation(5);
                    pamperedCardView.setChecked(false);
                    pamperedCardView.setCardElevation(5);
                    scaredCardView.setChecked(false);
                    scaredCardView.setCardElevation(5);
                    shockCardView.setChecked(false);
                    shockCardView.setCardElevation(5);
                    cryingCardView.setChecked(false);
                    cryingCardView.setCardElevation(5);
                    highWeedCardView.setChecked(false);
                    highWeedCardView.setCardElevation(5);
                    confuseCardView.setChecked(false);
                    confuseCardView.setCardElevation(5);


                } else {
                    amazingCardView.setChecked(false);
                    amazingCardView.setCardElevation(5);
                }
            }
        });

        sadCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sadCardView.isChecked()) {
                    sadCardView.setChecked(true);
                    sadCardView.setCardElevation(30);

                    lovelyCardView.setChecked(false);
                    lovelyCardView.setCardElevation(5);
                    happyCardView.setChecked(false);
                    happyCardView.setCardElevation(5);
                    amazingCardView.setChecked(false);
                    amazingCardView.setCardElevation(5);
                    coolCardView.setChecked(false);
                    coolCardView.setCardElevation(5);
                    angryCardView.setChecked(false);
                    angryCardView.setCardElevation(5);
                    painCardView.setChecked(false);
                    painCardView.setCardElevation(5);
                    awkwardCardView.setChecked(false);
                    awkwardCardView.setCardElevation(5);

                    kissCardView.setChecked(false);
                    kissCardView.setCardElevation(5);
                    laughCardView.setChecked(false);
                    laughCardView.setCardElevation(5);
                    teasingCardView.setChecked(false);
                    teasingCardView.setCardElevation(5);
                    sleepingCardView.setChecked(false);
                    sleepingCardView.setCardElevation(5);
                    pamperedCardView.setChecked(false);
                    pamperedCardView.setCardElevation(5);
                    scaredCardView.setChecked(false);
                    scaredCardView.setCardElevation(5);
                    shockCardView.setChecked(false);
                    shockCardView.setCardElevation(5);
                    cryingCardView.setChecked(false);
                    cryingCardView.setCardElevation(5);
                    highWeedCardView.setChecked(false);
                    highWeedCardView.setCardElevation(5);
                    confuseCardView.setChecked(false);
                    confuseCardView.setCardElevation(5);


                } else {
                    sadCardView.setChecked(false);
                    sadCardView.setCardElevation(5);
                }
            }
        });

        coolCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!coolCardView.isChecked()) {
                    coolCardView.setChecked(true);
                    coolCardView.setCardElevation(30);

                    lovelyCardView.setChecked(false);
                    lovelyCardView.setCardElevation(5);
                    happyCardView.setChecked(false);
                    happyCardView.setCardElevation(5);
                    amazingCardView.setChecked(false);
                    amazingCardView.setCardElevation(5);
                    sadCardView.setChecked(false);
                    sadCardView.setCardElevation(5);
                    angryCardView.setChecked(false);
                    angryCardView.setCardElevation(5);
                    painCardView.setChecked(false);
                    painCardView.setCardElevation(5);
                    awkwardCardView.setChecked(false);
                    awkwardCardView.setCardElevation(5);
                    kissCardView.setChecked(false);
                    kissCardView.setCardElevation(5);
                    laughCardView.setChecked(false);
                    laughCardView.setCardElevation(5);
                    teasingCardView.setChecked(false);
                    teasingCardView.setCardElevation(5);
                    sleepingCardView.setChecked(false);
                    sleepingCardView.setCardElevation(5);
                    pamperedCardView.setChecked(false);
                    pamperedCardView.setCardElevation(5);
                    scaredCardView.setChecked(false);
                    scaredCardView.setCardElevation(5);
                    shockCardView.setChecked(false);
                    shockCardView.setCardElevation(5);
                    cryingCardView.setChecked(false);
                    cryingCardView.setCardElevation(5);
                    highWeedCardView.setChecked(false);
                    highWeedCardView.setCardElevation(5);
                    confuseCardView.setChecked(false);
                    confuseCardView.setCardElevation(5);


                } else {
                    coolCardView.setChecked(false);
                    coolCardView.setCardElevation(5);
                }
            }
        });


        angryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!angryCardView.isChecked()) {
                    angryCardView.setChecked(true);
                    angryCardView.setCardElevation(30);

                    lovelyCardView.setChecked(false);
                    lovelyCardView.setCardElevation(5);
                    happyCardView.setChecked(false);
                    happyCardView.setCardElevation(5);
                    amazingCardView.setChecked(false);
                    amazingCardView.setCardElevation(5);
                    sadCardView.setChecked(false);
                    sadCardView.setCardElevation(5);
                    coolCardView.setChecked(false);
                    coolCardView.setCardElevation(5);
                    painCardView.setChecked(false);
                    painCardView.setCardElevation(5);
                    awkwardCardView.setChecked(false);
                    awkwardCardView.setCardElevation(5);
                    kissCardView.setChecked(false);
                    kissCardView.setCardElevation(5);
                    laughCardView.setChecked(false);
                    laughCardView.setCardElevation(5);
                    teasingCardView.setChecked(false);
                    teasingCardView.setCardElevation(5);
                    sleepingCardView.setChecked(false);
                    sleepingCardView.setCardElevation(5);
                    pamperedCardView.setChecked(false);
                    pamperedCardView.setCardElevation(5);
                    scaredCardView.setChecked(false);
                    scaredCardView.setCardElevation(5);
                    shockCardView.setChecked(false);
                    shockCardView.setCardElevation(5);
                    cryingCardView.setChecked(false);
                    cryingCardView.setCardElevation(5);
                    highWeedCardView.setChecked(false);
                    highWeedCardView.setCardElevation(5);
                    confuseCardView.setChecked(false);
                    confuseCardView.setCardElevation(5);

                } else {
                    angryCardView.setChecked(false);
                    angryCardView.setCardElevation(5);

                }
            }
        });

        painCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!painCardView.isChecked()) {
                    painCardView.setChecked(true);
                    painCardView.setCardElevation(30);


                    happyCardView.setChecked(false);
                    happyCardView.setCardElevation(5);
                    amazingCardView.setChecked(false);
                    amazingCardView.setCardElevation(5);
                    sadCardView.setChecked(false);
                    sadCardView.setCardElevation(5);
                    coolCardView.setChecked(false);
                    coolCardView.setCardElevation(5);
                    angryCardView.setChecked(false);
                    angryCardView.setCardElevation(5);
                    lovelyCardView.setChecked(false);
                    lovelyCardView.setCardElevation(5);
                    awkwardCardView.setChecked(false);
                    awkwardCardView.setCardElevation(5);
                    kissCardView.setChecked(false);
                    kissCardView.setCardElevation(5);
                    laughCardView.setChecked(false);
                    laughCardView.setCardElevation(5);
                    teasingCardView.setChecked(false);
                    teasingCardView.setCardElevation(5);
                    sleepingCardView.setChecked(false);
                    sleepingCardView.setCardElevation(5);
                    pamperedCardView.setChecked(false);
                    pamperedCardView.setCardElevation(5);
                    scaredCardView.setChecked(false);
                    scaredCardView.setCardElevation(5);
                    shockCardView.setChecked(false);
                    shockCardView.setCardElevation(5);
                    cryingCardView.setChecked(false);
                    cryingCardView.setCardElevation(5);
                    highWeedCardView.setChecked(false);
                    highWeedCardView.setCardElevation(5);
                    confuseCardView.setChecked(false);
                    confuseCardView.setCardElevation(5);


                } else {
                    painCardView.setChecked(false);
                    painCardView.setCardElevation(5);
                }
            }
        });
        awkwardCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!awkwardCardView.isChecked()) {
                    awkwardCardView.setChecked(true);
                    awkwardCardView.setCardElevation(30);


                    happyCardView.setChecked(false);
                    happyCardView.setCardElevation(5);
                    amazingCardView.setChecked(false);
                    amazingCardView.setCardElevation(5);
                    sadCardView.setChecked(false);
                    sadCardView.setCardElevation(5);
                    coolCardView.setChecked(false);
                    coolCardView.setCardElevation(5);
                    angryCardView.setChecked(false);
                    angryCardView.setCardElevation(5);
                    lovelyCardView.setChecked(false);
                    lovelyCardView.setCardElevation(5);
                    painCardView.setChecked(false);
                    painCardView.setCardElevation(5);
                    kissCardView.setChecked(false);
                    kissCardView.setCardElevation(5);
                    cryingCardView.setChecked(false);
                    cryingCardView.setCardElevation(5);
                    confuseCardView.setChecked(false);
                    confuseCardView.setCardElevation(5);
                    teasingCardView.setChecked(false);
                    teasingCardView.setCardElevation(5);
                    pamperedCardView.setChecked(false);
                    pamperedCardView.setCardElevation(5);
                    scaredCardView.setChecked(false);
                    scaredCardView.setCardElevation(5);
                    laughCardView.setChecked(false);
                    laughCardView.setCardElevation(5);
                    shockCardView.setChecked(false);
                    shockCardView.setCardElevation(5);
                    highWeedCardView.setChecked(false);
                    highWeedCardView.setCardElevation(5);
                    sleepingCardView.setChecked(false);
                    sleepingCardView.setCardElevation(5);


                } else {
                    awkwardCardView.setChecked(false);
                    awkwardCardView.setCardElevation(5);
                }
            }
        });


        kissCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!kissCardView.isChecked()) {
                    kissCardView.setChecked(true);
                    kissCardView.setCardElevation(30);


                    happyCardView.setChecked(false);
                    happyCardView.setCardElevation(5);
                    amazingCardView.setChecked(false);
                    amazingCardView.setCardElevation(5);
                    sadCardView.setChecked(false);
                    sadCardView.setCardElevation(5);
                    coolCardView.setChecked(false);
                    coolCardView.setCardElevation(5);
                    angryCardView.setChecked(false);
                    angryCardView.setCardElevation(5);
                    lovelyCardView.setChecked(false);
                    lovelyCardView.setCardElevation(5);
                    painCardView.setChecked(false);
                    painCardView.setCardElevation(5);
                    laughCardView.setChecked(false);
                    laughCardView.setCardElevation(5);
                    teasingCardView.setChecked(false);
                    teasingCardView.setCardElevation(5);
                    sleepingCardView.setChecked(false);
                    sleepingCardView.setCardElevation(5);
                    pamperedCardView.setChecked(false);
                    pamperedCardView.setCardElevation(5);
                    scaredCardView.setChecked(false);
                    scaredCardView.setCardElevation(5);
                    shockCardView.setChecked(false);
                    shockCardView.setCardElevation(5);
                    cryingCardView.setChecked(false);
                    cryingCardView.setCardElevation(5);
                    highWeedCardView.setChecked(false);
                    highWeedCardView.setCardElevation(5);
                    confuseCardView.setChecked(false);
                    confuseCardView.setCardElevation(5);
                    awkwardCardView.setChecked(false);
                    awkwardCardView.setCardElevation(5);


                } else {
                    kissCardView.setChecked(false);
                    kissCardView.setCardElevation(5);
                }
            }
        });

        laughCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!laughCardView.isChecked()) {
                    laughCardView.setChecked(true);
                    laughCardView.setCardElevation(30);


                    happyCardView.setChecked(false);
                    happyCardView.setCardElevation(5);
                    amazingCardView.setChecked(false);
                    amazingCardView.setCardElevation(5);
                    sadCardView.setChecked(false);
                    sadCardView.setCardElevation(5);
                    coolCardView.setChecked(false);
                    coolCardView.setCardElevation(5);
                    angryCardView.setChecked(false);
                    angryCardView.setCardElevation(5);
                    lovelyCardView.setChecked(false);
                    lovelyCardView.setCardElevation(5);
                    painCardView.setChecked(false);
                    painCardView.setCardElevation(5);
                    kissCardView.setChecked(false);
                    kissCardView.setCardElevation(5);
                    teasingCardView.setChecked(false);
                    teasingCardView.setCardElevation(5);
                    sleepingCardView.setChecked(false);
                    sleepingCardView.setCardElevation(5);
                    pamperedCardView.setChecked(false);
                    pamperedCardView.setCardElevation(5);
                    scaredCardView.setChecked(false);
                    scaredCardView.setCardElevation(5);
                    shockCardView.setChecked(false);
                    shockCardView.setCardElevation(5);
                    cryingCardView.setChecked(false);
                    cryingCardView.setCardElevation(5);
                    highWeedCardView.setChecked(false);
                    highWeedCardView.setCardElevation(5);
                    confuseCardView.setChecked(false);
                    confuseCardView.setCardElevation(5);
                    awkwardCardView.setChecked(false);
                    awkwardCardView.setCardElevation(5);


                } else {
                    laughCardView.setChecked(false);
                    laughCardView.setCardElevation(5);
                }
            }
        });

        cryingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cryingCardView.isChecked()) {
                    cryingCardView.setChecked(true);
                    cryingCardView.setCardElevation(30);


                    happyCardView.setChecked(false);
                    happyCardView.setCardElevation(5);
                    amazingCardView.setChecked(false);
                    amazingCardView.setCardElevation(5);
                    sadCardView.setChecked(false);
                    sadCardView.setCardElevation(5);
                    coolCardView.setChecked(false);
                    coolCardView.setCardElevation(5);
                    angryCardView.setChecked(false);
                    angryCardView.setCardElevation(5);
                    lovelyCardView.setChecked(false);
                    lovelyCardView.setCardElevation(5);
                    painCardView.setChecked(false);
                    painCardView.setCardElevation(5);
                    kissCardView.setChecked(false);
                    kissCardView.setCardElevation(5);
                    teasingCardView.setChecked(false);
                    teasingCardView.setCardElevation(5);
                    sleepingCardView.setChecked(false);
                    sleepingCardView.setCardElevation(5);
                    pamperedCardView.setChecked(false);
                    pamperedCardView.setCardElevation(5);
                    scaredCardView.setChecked(false);
                    scaredCardView.setCardElevation(5);
                    shockCardView.setChecked(false);
                    shockCardView.setCardElevation(5);
                    laughCardView.setChecked(false);
                    laughCardView.setCardElevation(5);
                    highWeedCardView.setChecked(false);
                    highWeedCardView.setCardElevation(5);
                    confuseCardView.setChecked(false);
                    confuseCardView.setCardElevation(5);
                    awkwardCardView.setChecked(false);
                    awkwardCardView.setCardElevation(5);


                } else {
                    cryingCardView.setChecked(false);
                    cryingCardView.setCardElevation(5);
                }
            }
        });

        teasingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!teasingCardView.isChecked()) {
                    teasingCardView.setChecked(true);
                    teasingCardView.setCardElevation(30);


                    happyCardView.setChecked(false);
                    happyCardView.setCardElevation(5);
                    amazingCardView.setChecked(false);
                    amazingCardView.setCardElevation(5);
                    sadCardView.setChecked(false);
                    sadCardView.setCardElevation(5);
                    coolCardView.setChecked(false);
                    coolCardView.setCardElevation(5);
                    angryCardView.setChecked(false);
                    angryCardView.setCardElevation(5);
                    lovelyCardView.setChecked(false);
                    lovelyCardView.setCardElevation(5);
                    painCardView.setChecked(false);
                    painCardView.setCardElevation(5);
                    kissCardView.setChecked(false);
                    kissCardView.setCardElevation(5);
                    cryingCardView.setChecked(false);
                    cryingCardView.setCardElevation(5);
                    sleepingCardView.setChecked(false);
                    sleepingCardView.setCardElevation(5);
                    pamperedCardView.setChecked(false);
                    pamperedCardView.setCardElevation(5);
                    scaredCardView.setChecked(false);
                    scaredCardView.setCardElevation(5);
                    shockCardView.setChecked(false);
                    shockCardView.setCardElevation(5);
                    laughCardView.setChecked(false);
                    laughCardView.setCardElevation(5);
                    highWeedCardView.setChecked(false);
                    highWeedCardView.setCardElevation(5);
                    confuseCardView.setChecked(false);
                    confuseCardView.setCardElevation(5);
                    awkwardCardView.setChecked(false);
                    awkwardCardView.setCardElevation(5);


                } else {
                    teasingCardView.setChecked(false);
                    teasingCardView.setCardElevation(5);
                }
            }
        });


        pamperedCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pamperedCardView.isChecked()) {
                    pamperedCardView.setChecked(true);
                    pamperedCardView.setCardElevation(30);


                    happyCardView.setChecked(false);
                    happyCardView.setCardElevation(5);
                    amazingCardView.setChecked(false);
                    amazingCardView.setCardElevation(5);
                    sadCardView.setChecked(false);
                    sadCardView.setCardElevation(5);
                    coolCardView.setChecked(false);
                    coolCardView.setCardElevation(5);
                    angryCardView.setChecked(false);
                    angryCardView.setCardElevation(5);
                    lovelyCardView.setChecked(false);
                    lovelyCardView.setCardElevation(5);
                    painCardView.setChecked(false);
                    painCardView.setCardElevation(5);
                    kissCardView.setChecked(false);
                    kissCardView.setCardElevation(5);
                    cryingCardView.setChecked(false);
                    cryingCardView.setCardElevation(5);
                    sleepingCardView.setChecked(false);
                    sleepingCardView.setCardElevation(5);
                    teasingCardView.setChecked(false);
                    teasingCardView.setCardElevation(5);
                    scaredCardView.setChecked(false);
                    scaredCardView.setCardElevation(5);
                    shockCardView.setChecked(false);
                    shockCardView.setCardElevation(5);
                    laughCardView.setChecked(false);
                    laughCardView.setCardElevation(5);
                    highWeedCardView.setChecked(false);
                    highWeedCardView.setCardElevation(5);
                    confuseCardView.setChecked(false);
                    confuseCardView.setCardElevation(5);
                    awkwardCardView.setChecked(false);
                    awkwardCardView.setCardElevation(5);


                } else {
                    pamperedCardView.setChecked(false);
                    pamperedCardView.setCardElevation(5);
                }
            }
        });

        scaredCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!scaredCardView.isChecked()) {
                    scaredCardView.setChecked(true);
                    scaredCardView.setCardElevation(30);


                    happyCardView.setChecked(false);
                    happyCardView.setCardElevation(5);
                    amazingCardView.setChecked(false);
                    amazingCardView.setCardElevation(5);
                    sadCardView.setChecked(false);
                    sadCardView.setCardElevation(5);
                    coolCardView.setChecked(false);
                    coolCardView.setCardElevation(5);
                    angryCardView.setChecked(false);
                    angryCardView.setCardElevation(5);
                    lovelyCardView.setChecked(false);
                    lovelyCardView.setCardElevation(5);
                    painCardView.setChecked(false);
                    painCardView.setCardElevation(5);
                    kissCardView.setChecked(false);
                    kissCardView.setCardElevation(5);
                    cryingCardView.setChecked(false);
                    cryingCardView.setCardElevation(5);
                    sleepingCardView.setChecked(false);
                    sleepingCardView.setCardElevation(5);
                    teasingCardView.setChecked(false);
                    teasingCardView.setCardElevation(5);
                    pamperedCardView.setChecked(false);
                    pamperedCardView.setCardElevation(5);
                    shockCardView.setChecked(false);
                    shockCardView.setCardElevation(5);
                    laughCardView.setChecked(false);
                    laughCardView.setCardElevation(5);
                    highWeedCardView.setChecked(false);
                    highWeedCardView.setCardElevation(5);
                    confuseCardView.setChecked(false);
                    confuseCardView.setCardElevation(5);
                    awkwardCardView.setChecked(false);
                    awkwardCardView.setCardElevation(5);


                } else {
                    scaredCardView.setChecked(false);
                    scaredCardView.setCardElevation(5);
                }
            }
        });
        shockCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!shockCardView.isChecked()) {
                    shockCardView.setChecked(true);
                    shockCardView.setCardElevation(30);


                    happyCardView.setChecked(false);
                    happyCardView.setCardElevation(5);
                    amazingCardView.setChecked(false);
                    amazingCardView.setCardElevation(5);
                    sadCardView.setChecked(false);
                    sadCardView.setCardElevation(5);
                    coolCardView.setChecked(false);
                    coolCardView.setCardElevation(5);
                    angryCardView.setChecked(false);
                    angryCardView.setCardElevation(5);
                    lovelyCardView.setChecked(false);
                    lovelyCardView.setCardElevation(5);
                    painCardView.setChecked(false);
                    painCardView.setCardElevation(5);
                    kissCardView.setChecked(false);
                    kissCardView.setCardElevation(5);
                    cryingCardView.setChecked(false);
                    cryingCardView.setCardElevation(5);
                    sleepingCardView.setChecked(false);
                    sleepingCardView.setCardElevation(5);
                    teasingCardView.setChecked(false);
                    teasingCardView.setCardElevation(5);
                    pamperedCardView.setChecked(false);
                    pamperedCardView.setCardElevation(5);
                    scaredCardView.setChecked(false);
                    scaredCardView.setCardElevation(5);
                    laughCardView.setChecked(false);
                    laughCardView.setCardElevation(5);
                    highWeedCardView.setChecked(false);
                    highWeedCardView.setCardElevation(5);
                    confuseCardView.setChecked(false);
                    confuseCardView.setCardElevation(5);
                    awkwardCardView.setChecked(false);
                    awkwardCardView.setCardElevation(5);


                } else {
                    shockCardView.setChecked(false);
                    shockCardView.setCardElevation(5);
                }
            }
        });

        highWeedCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!highWeedCardView.isChecked()) {
                    highWeedCardView.setChecked(true);
                    highWeedCardView.setCardElevation(30);


                    happyCardView.setChecked(false);
                    happyCardView.setCardElevation(5);
                    amazingCardView.setChecked(false);
                    amazingCardView.setCardElevation(5);
                    sadCardView.setChecked(false);
                    sadCardView.setCardElevation(5);
                    coolCardView.setChecked(false);
                    coolCardView.setCardElevation(5);
                    angryCardView.setChecked(false);
                    angryCardView.setCardElevation(5);
                    lovelyCardView.setChecked(false);
                    lovelyCardView.setCardElevation(5);
                    painCardView.setChecked(false);
                    painCardView.setCardElevation(5);
                    kissCardView.setChecked(false);
                    kissCardView.setCardElevation(5);
                    cryingCardView.setChecked(false);
                    cryingCardView.setCardElevation(5);
                    sleepingCardView.setChecked(false);
                    sleepingCardView.setCardElevation(5);
                    teasingCardView.setChecked(false);
                    teasingCardView.setCardElevation(5);
                    pamperedCardView.setChecked(false);
                    pamperedCardView.setCardElevation(5);
                    scaredCardView.setChecked(false);
                    scaredCardView.setCardElevation(5);
                    laughCardView.setChecked(false);
                    laughCardView.setCardElevation(5);
                    shockCardView.setChecked(false);
                    shockCardView.setCardElevation(5);
                    confuseCardView.setChecked(false);
                    confuseCardView.setCardElevation(5);
                    awkwardCardView.setChecked(false);
                    awkwardCardView.setCardElevation(5);


                } else {
                    highWeedCardView.setChecked(false);
                    highWeedCardView.setCardElevation(5);
                }
            }
        });

        confuseCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!confuseCardView.isChecked()) {
                    confuseCardView.setChecked(true);
                    confuseCardView.setCardElevation(30);


                    happyCardView.setChecked(false);
                    happyCardView.setCardElevation(5);
                    amazingCardView.setChecked(false);
                    amazingCardView.setCardElevation(5);
                    sadCardView.setChecked(false);
                    sadCardView.setCardElevation(5);
                    coolCardView.setChecked(false);
                    coolCardView.setCardElevation(5);
                    angryCardView.setChecked(false);
                    angryCardView.setCardElevation(5);
                    lovelyCardView.setChecked(false);
                    lovelyCardView.setCardElevation(5);
                    painCardView.setChecked(false);
                    painCardView.setCardElevation(5);
                    kissCardView.setChecked(false);
                    kissCardView.setCardElevation(5);
                    cryingCardView.setChecked(false);
                    cryingCardView.setCardElevation(5);
                    sleepingCardView.setChecked(false);
                    sleepingCardView.setCardElevation(5);
                    teasingCardView.setChecked(false);
                    teasingCardView.setCardElevation(5);
                    pamperedCardView.setChecked(false);
                    pamperedCardView.setCardElevation(5);
                    scaredCardView.setChecked(false);
                    scaredCardView.setCardElevation(5);
                    laughCardView.setChecked(false);
                    laughCardView.setCardElevation(5);
                    shockCardView.setChecked(false);
                    shockCardView.setCardElevation(5);
                    highWeedCardView.setChecked(false);
                    highWeedCardView.setCardElevation(5);
                    awkwardCardView.setChecked(false);
                    awkwardCardView.setCardElevation(5);


                } else {
                    confuseCardView.setChecked(false);
                    confuseCardView.setCardElevation(5);
                }
            }
        });

        sleepingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sleepingCardView.isChecked()) {
                    sleepingCardView.setChecked(true);
                    sleepingCardView.setCardElevation(30);


                    happyCardView.setChecked(false);
                    happyCardView.setCardElevation(5);
                    amazingCardView.setChecked(false);
                    amazingCardView.setCardElevation(5);
                    sadCardView.setChecked(false);
                    sadCardView.setCardElevation(5);
                    coolCardView.setChecked(false);
                    coolCardView.setCardElevation(5);
                    angryCardView.setChecked(false);
                    angryCardView.setCardElevation(5);
                    lovelyCardView.setChecked(false);
                    lovelyCardView.setCardElevation(5);
                    painCardView.setChecked(false);
                    painCardView.setCardElevation(5);
                    kissCardView.setChecked(false);
                    kissCardView.setCardElevation(5);
                    cryingCardView.setChecked(false);
                    cryingCardView.setCardElevation(5);
                    confuseCardView.setChecked(false);
                    confuseCardView.setCardElevation(5);
                    teasingCardView.setChecked(false);
                    teasingCardView.setCardElevation(5);
                    pamperedCardView.setChecked(false);
                    pamperedCardView.setCardElevation(5);
                    scaredCardView.setChecked(false);
                    scaredCardView.setCardElevation(5);
                    laughCardView.setChecked(false);
                    laughCardView.setCardElevation(5);
                    shockCardView.setChecked(false);
                    shockCardView.setCardElevation(5);
                    highWeedCardView.setChecked(false);
                    highWeedCardView.setCardElevation(5);
                    awkwardCardView.setChecked(false);
                    awkwardCardView.setCardElevation(5);


                } else {
                    sleepingCardView.setChecked(false);
                    sleepingCardView.setCardElevation(5);
                }
            }
        });


        memoryMarkerDialog.show();
        memoryMarkerDialog.setCancelable(true);
        memoryMarkerDialog.setCanceledOnTouchOutside(true);


    }

    private void addMemoryMarker() {


        //START..if fields are empty, add default text to fields


        //START
        //check the boolean validations
        // the "||" checks the first validation before it checks the rest so user wont knw the other fields that needs correction before proceeding
        // the "|" checks all validations same time so user can knw all fields that needs correction
       /* if (!validateMemoryDialogTitle() | !validateMemoryDialogDescription()) {

            return;

        }*/
        //END


        //START ...all these are removed from this method cos not all are used here and the ones needed here  were declared accessible in the parent class such as the currentLatLong

             /*final double latitude = mLastLocation.getLatitude();
        final double longitude = mLastLocation.getLongitude();
             currentLatLong = new LatLng(latitude, longitude);

        String currentLat = String.valueOf(latitude);
        String currentLong = String.valueOf(longitude);
        String currentLocCoordinates = currentLat + "," + currentLong;
        */

        //END


        //init current date time setter
        Calendar calendar = Calendar.getInstance();

        //START...the "a" adds the AM or PM
            /*String  dateTimeFormat = "dd-MM-yy hh:mm:ss a";
            String  dateFormat = "dd-MMM-yy";*/
        //END
        String timeFormat = "dd-MMM-yy   hh:mm:ss a";


        //START...use this to set local time  and format
        /*SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();*/
        //END
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);

        String dateTime = simpleDateFormat.format(calendar.getTime());


        String memoryTitleText = memoryTitle.getEditText().getText().toString().trim();

        //START... using  getText same time of both  strings of TextInputLayout and TextInputEditText causes app to crash since either should do it but not both
        //String memoryTitleText2 = memoryTitle_editText2.getText().toString().trim();
        //String memoryDescription2 = memoryDescription_editText2.getText().toString().trim();
        String memoryDescriptionText = memoryDescription.getEditText().getText().toString().trim();


        //START.. use these if u dnt want to show error if fields are empty with no validation boolean error checking

        if (memoryTitleText.isEmpty() & !memoryDescriptionText.isEmpty()) {
            String noTitleText = "(no title)";
            //memoryTitle_editText2.getText();

            if (lovelyCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_love)).title("(" + dateTime + ")\n" + noTitleText).snippet(memoryDescriptionText));
                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (happyCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_happy)).title("(" + dateTime + ")\n" + noTitleText).snippet(memoryDescriptionText).zIndex(2f));
                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (amazingCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_selfie)).title("(" + dateTime + ")\n" + noTitleText).snippet(memoryDescriptionText).zIndex(2f));
                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (coolCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_cool)).title("(" + dateTime + ")\n" + noTitleText).snippet(memoryDescriptionText).zIndex(2f));
                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (sadCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_sad)).title("(" + dateTime + ")\n" + noTitleText).snippet(memoryDescriptionText).zIndex(2f));
                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (angryCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_angry1)).title("(" + dateTime + ")\n" + noTitleText).snippet(memoryDescriptionText).zIndex(2f));
                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (painCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_pain)).title("(" + dateTime + ")\n" + noTitleText).snippet(memoryDescriptionText).zIndex(2f));
                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (awkwardCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_awkward)).title("(" + dateTime + ")\n" + noTitleText).snippet(memoryDescriptionText).zIndex(2f));
                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (kissCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_kiss)).title("(" + dateTime + ")\n" + noTitleText).snippet(memoryDescriptionText).zIndex(2f));
                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (teasingCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_teasing)).title("(" + dateTime + ")\n" + noTitleText).snippet(memoryDescriptionText).zIndex(2f));
                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (laughCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_laugh)).title("(" + dateTime + ")\n" + noTitleText).snippet(memoryDescriptionText).zIndex(2f));
                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (cryingCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_crying)).title("(" + dateTime + ")\n" + noTitleText).snippet(memoryDescriptionText).zIndex(2f));
                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (scaredCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_scared)).title("(" + dateTime + ")\n" + noTitleText).snippet(memoryDescriptionText).zIndex(2f));
                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (sleepingCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_sleeping)).title("(" + dateTime + ")\n" + noTitleText).snippet(memoryDescriptionText).zIndex(2f));
                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (shockCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_shock)).title("(" + dateTime + ")\n" + noTitleText).snippet(memoryDescriptionText).zIndex(2f));
                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (pamperedCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_pampered)).title("(" + dateTime + ")\n" + noTitleText).snippet(memoryDescriptionText).zIndex(2f));
                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (confuseCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_confused)).title("(" + dateTime + ")\n" + noTitleText).snippet(memoryDescriptionText).zIndex(2f));
                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (highWeedCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_highweed)).title("(" + dateTime + ")\n" + noTitleText).snippet(memoryDescriptionText).zIndex(2f));
                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else {
                memoryIconsCardViewHeadLayout.setBackgroundColor(getResources().getColor(R.color.quantum_googred200));
                memoryIconScrollView.setBackgroundColor(getResources().getColor(R.color.quantum_googred200));
                Toast.makeText(this, "Select an icon to proceed", Toast.LENGTH_LONG).show();

                memoryMarkerDialog.show();

            }

            /*if (!lovelyCardView.isChecked() || !happyCardView.isChecked() || !amazingCardView.isChecked() || !coolCardView.isChecked() || !sadCardView.isChecked() || !angryCardView.isChecked()) {


                Toast toast = Toast.makeText(this, "Select an icon to proceed", Toast.LENGTH_LONG);

                toast.show();
                memoryIconsCardViewHeadLayout.setBackgroundColor(getResources().getColor(R.color.quantum_googred200));
                memoryIconScrollView.setBackgroundColor(getResources().getColor(R.color.quantum_googred200));


                memoryMarkerDialog.show();
            }
*/


        } else if (memoryDescriptionText.isEmpty() & !memoryTitleText.isEmpty()) {
            String noDescriptionText = "(no moment description)";
            //memoryDescription_editText2.setText(noDescriptionText);

            if (lovelyCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_love)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(noDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (happyCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_happy)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(noDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (amazingCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_selfie)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(noDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (coolCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_cool)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(noDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (sadCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_sad)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(noDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (angryCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_angry1)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(noDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (painCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_pain)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(noDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (awkwardCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_awkward)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(noDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (kissCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_kiss)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(noDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (laughCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_laugh)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(noDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (teasingCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_teasing)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(noDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (cryingCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_crying)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(noDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (shockCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_shock)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(noDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (scaredCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_scared)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(noDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (highWeedCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_highweed)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(noDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (pamperedCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_pampered)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(noDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (confuseCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_confused)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(noDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (sleepingCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_sleeping)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(noDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else {
                memoryIconsCardViewHeadLayout.setBackgroundColor(getResources().getColor(R.color.quantum_googred200));
                memoryIconScrollView.setBackgroundColor(getResources().getColor(R.color.quantum_googred200));
                Toast.makeText(this, "Select an icon to proceed", Toast.LENGTH_LONG).show();

                memoryMarkerDialog.show();


            }
            /*if (!lovelyCardView.isChecked() || !happyCardView.isChecked() || !amazingCardView.isChecked() || !coolCardView.isChecked() || !sadCardView.isChecked() || !angryCardView.isChecked()) {

                Toast.makeText(this, "Select an icon to proceed", Toast.LENGTH_LONG).show();

                memoryIconsCardViewHeadLayout.setBackgroundColor(getResources().getColor(R.color.quantum_googred200));
                memoryIconScrollView.setBackgroundColor(getResources().getColor(R.color.quantum_googred200));

                memoryMarkerDialog.show();

            }*/


        } else if (memoryTitleText.isEmpty() & memoryDescriptionText.isEmpty()) {
            String noTitleText2 = "(no title)";
            //memoryTitle_editText2.setText(noTitleText2);
            String noDescriptionText2 = "(no moment description)";
            //memoryDescription_editText2.setText(noDescriptionText2);


            if (lovelyCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_love)).title("(" + dateTime + ")\n" + noTitleText2).snippet(noDescriptionText2).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (happyCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_happy)).title("(" + dateTime + ")\n" + noTitleText2).snippet(noDescriptionText2).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (amazingCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_selfie)).title("(" + dateTime + ")\n" + noTitleText2).snippet(noDescriptionText2).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (coolCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_cool)).title("(" + dateTime + ")\n" + noTitleText2).snippet(noDescriptionText2).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (sadCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_sad)).title("(" + dateTime + ")\n" + noTitleText2).snippet(noDescriptionText2).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();
                shareMemoryCardView.setVisibility(View.VISIBLE);
            } else if (angryCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_angry1)).title("(" + dateTime + ")\n" + noTitleText2).snippet(noDescriptionText2).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (painCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_pain)).title("(" + dateTime + ")\n" + noTitleText2).snippet(noDescriptionText2).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (awkwardCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_awkward)).title("(" + dateTime + ")\n" + noTitleText2).snippet(noDescriptionText2).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (kissCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_kiss)).title("(" + dateTime + ")\n" + noTitleText2).snippet(noDescriptionText2).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (laughCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_laugh)).title("(" + dateTime + ")\n" + noTitleText2).snippet(noDescriptionText2).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (teasingCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_teasing)).title("(" + dateTime + ")\n" + noTitleText2).snippet(noDescriptionText2).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (cryingCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_crying)).title("(" + dateTime + ")\n" + noTitleText2).snippet(noDescriptionText2).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (shockCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_shock)).title("(" + dateTime + ")\n" + noTitleText2).snippet(noDescriptionText2).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (scaredCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_scared)).title("(" + dateTime + ")\n" + noTitleText2).snippet(noDescriptionText2).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (highWeedCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_highweed)).title("(" + dateTime + ")\n" + noTitleText2).snippet(noDescriptionText2).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (pamperedCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_pampered)).title("(" + dateTime + ")\n" + noTitleText2).snippet(noDescriptionText2).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (confuseCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_confused)).title("(" + dateTime + ")\n" + noTitleText2).snippet(noDescriptionText2).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (sleepingCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_sleeping)).title("(" + dateTime + ")\n" + noTitleText2).snippet(noDescriptionText2).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else {
                memoryIconsCardViewHeadLayout.setBackgroundColor(getResources().getColor(R.color.quantum_googred200));
                memoryIconScrollView.setBackgroundColor(getResources().getColor(R.color.quantum_googred200));
                Toast.makeText(this, "Select an icon to proceed", Toast.LENGTH_LONG).show();

                memoryMarkerDialog.show();

            }

            /*if (!lovelyCardView.isChecked() || !happyCardView.isChecked() || !amazingCardView.isChecked() || !coolCardView.isChecked() || !sadCardView.isChecked() || !angryCardView.isChecked()) {

                Toast.makeText(this, "Select an icon to proceed", Toast.LENGTH_LONG).show();

                memoryIconsCardViewHeadLayout.setBackgroundColor(getResources().getColor(R.color.quantum_googred200));
                memoryIconScrollView.setBackgroundColor(getResources().getColor(R.color.quantum_googred200));
                memoryMarkerDialog.show();
            }*/

        }

        //thus when both are not empty
        else {

            if (lovelyCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_love)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(memoryDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (happyCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_happy)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(memoryDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (amazingCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_selfie)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(memoryDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (coolCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_cool)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(memoryDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (sadCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_sad)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(memoryDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (angryCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_angry1)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(memoryDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (painCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_pain)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(memoryDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (awkwardCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_awkward)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(memoryDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (kissCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_kiss)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(memoryDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (laughCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_laugh)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(memoryDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (teasingCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_teasing)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(memoryDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (cryingCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_crying)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(memoryDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (shockCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_shock)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(memoryDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (scaredCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_scared)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(memoryDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (highWeedCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_highweed)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(memoryDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (pamperedCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_pampered)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(memoryDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (confuseCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_confused)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(memoryDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else if (sleepingCardView.isChecked()) {
                memoryMarker = mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(bitmapDescriptorFromVector(this, R.drawable.ic_sleeping)).title("(" + dateTime + ")\n" + memoryTitleText).snippet(memoryDescriptionText).zIndex(2f));

                memoryMarker.showInfoWindow();
                memoryMarkerDialog.dismiss();

                shareMemoryCardView.setVisibility(View.VISIBLE);

            } else {
                memoryIconsCardViewHeadLayout.setBackgroundColor(getResources().getColor(R.color.quantum_googred200));
                memoryIconScrollView.setBackgroundColor(getResources().getColor(R.color.quantum_googred200));
                Toast.makeText(this, "Select an icon to proceed", Toast.LENGTH_LONG).show();

                memoryMarkerDialog.show();


            }


            /*if (!lovelyCardView.isChecked() || !happyCardView.isChecked() || !amazingCardView.isChecked() || !coolCardView.isChecked() || !sadCardView.isChecked() || !angryCardView.isChecked()) {


                memoryIconsCardViewHeadLayout.setBackgroundColor(getResources().getColor(R.color.quantum_googred200));
                memoryIconScrollView.setBackgroundColor(getResources().getColor(R.color.quantum_googred200));
            }*/


        }

        //END


        //PREVIOUS CODE...START

        /*if (memoryMarker != null) {

            shareMemoryCardView.setVisibility(View.VISIBLE);

        } else {
            shareMemoryCardView.setVisibility(View.GONE);
        }
        */

        //END


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (shareMemoryCardView.getVisibility() == View.VISIBLE) {
                    shareMemoryCardView.setVisibility(View.GONE);
                }
            }
        }, DELAY_TIME);

       /* if (memoryMarker != null) {

           // shareMemoryCardView.setVisibility(View.VISIBLE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    shareMemoryCardView.setVisibility(View.VISIBLE);
                }
            }, DELAY_TIME);
        } else {
            shareMemoryCardView.setVisibility(View.GONE);
        }*/


    }


    //START ...used to validate  MemoryDialog Title edit text
   /* private boolean validateMemoryDialogTitle() {
        String memoryTitleText = memoryTitle.getEditText().getText().toString().trim();

        if (memoryTitleText.isEmpty()) {
            memoryTitle.setError("Title cannot be empty");
            memoryTitle.setErrorEnabled(true);
            return false;

       *//* } else if (memoryTitleText.length() < 2) {
            memoryTitle.setError("Title is too short!");

            return false;*//*

        } else {
            memoryTitle.setError(null);
            memoryTitle.setErrorEnabled(false);
            return true;
        }


    }*/
    //END


    //START ...used to validate  MemoryDialog Description edit text
   /* private boolean validateMemoryDialogDescription() {
        String memoryDescriptionText = memoryDescription.getEditText().getText().toString().trim();

        if (memoryDescriptionText.isEmpty()) {
            memoryDescription.setError("Memory description cannot be empty");
            return false;

        *//*} else if (memoryDescriptionText.length() < 2) {
            memoryDescription.setError("Memory description is too short!");

            return false;*//*

        } else if (memoryDescriptionText.length() > 1000) {
            memoryDescription.setError("Memory description is too long!");

            return false;

        } else {
            memoryDescription.setError(null);
            memoryDescription.setErrorEnabled(false);
            return true;
        }


    }
*/
    //END

    private void showNavigationDialog() {

        navigationDialog.setContentView(R.layout.navigation_dialog_layout);
        Objects.requireNonNull(navigationDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        navigationDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationSlideAndDisappear;


        // init image views in dialog layout
        ImageView closeImage1 = navigationDialog.findViewById(R.id.img1_close);
        ImageView wazeImage1 = navigationDialog.findViewById(R.id.waze1_img);
        ImageView googleMapsImage1 = navigationDialog.findViewById(R.id.googleMaps1_img);


        closeImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationDialog.dismiss();
            }
        });

        wazeImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri navIntentUri = Uri.parse("https://waze.com/ul?q=" + destinationLat + "," + destinationLong + "&navigate=yes&zoom=17"); //google.navigation:q=
                Intent NavigateWithWazeIntent = new Intent(Intent.ACTION_VIEW, navIntentUri);
                NavigateWithWazeIntent.setPackage("com.waze");
                //checking if at least a map application is installed on the device so it opens it automatically for the navigation
                // else the app crashes
                //if more than one map application is installed, user is asked which to use for the action
                //just using if else statements also works perfectly without try catch
                try {


                    startActivity(NavigateWithWazeIntent);


                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Waze is not installed on your device... \nPlease Install Waze to handle task", Toast.LENGTH_SHORT).show();
                    Uri gmmIntentUri = Uri.parse("https://play.google.com/store/apps/details?id=com.waze");
                    Intent WazeIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                    WazeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(WazeIntent);

                }
                navigationDialog.dismiss();
            }


        });
        googleMapsImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri navIntentUri = Uri.parse("google.navigation:q=" + destinationLat + "," + destinationLong); //google.navigation:q=
                Intent tapNavigationIntent = new Intent(Intent.ACTION_VIEW, navIntentUri);
                tapNavigationIntent.setPackage("com.google.android.apps.maps");
                //checking if at least a map application is installed on the device so it opens it automatically for the navigation
                // else the app crashes
                //if more than one map application is installed, user is asked which to use for the action
                //just using if else statements also works perfectly without try catch
                try {

                    startActivity(tapNavigationIntent);


                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Google map is not installed on your device... \nPlease Install Google maps to handle task", Toast.LENGTH_SHORT).show();
                    Uri gmmIntentUri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                    Intent googleMapsIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                    googleMapsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(googleMapsIntent);

                }
                navigationDialog.dismiss();

            }

        });


        navigationDialog.show();
        navigationDialog.setCancelable(true);
        navigationDialog.setCanceledOnTouchOutside(false);


    }


    private void showTapNavigationDialog() {
        // tapNavigationDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        //tapNavigationDialog.getWindow().setWindowAnimations(R.style.DialogAnimation);

        tapNavigationDialog.setContentView(R.layout.tap_navigation_dialog_layout);
        tapNavigationDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tapNavigationDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationSlideAndDisappear;

        // init image views in dialog layout
        ImageView closeImage = tapNavigationDialog.findViewById(R.id.img_close);
        ImageView wazeImage = tapNavigationDialog.findViewById(R.id.waze_img);
        ImageView googleMapsImage = tapNavigationDialog.findViewById(R.id.googleMaps_img);


        closeImage.setOnClickListener(v -> tapNavigationDialog.dismiss());

        wazeImage.setOnClickListener(v -> {
            Uri navIntentUri = Uri.parse("https://waze.com/ul?q=" + mDestinationLat + "," + mDestinationLong + "&navigate=yes&zoom=17"); //google.navigation:q=
            Intent TapNavigateWithWazeIntent = new Intent(Intent.ACTION_VIEW, navIntentUri);
            TapNavigateWithWazeIntent.setPackage("com.waze");

           /* Uri navIntentUri = Uri.parse("https://facebook.com/"); //google.navigation:q=
            Intent TapNavigateWithWazeIntent = new Intent(Intent.ACTION_VIEW, navIntentUri);
            TapNavigateWithWazeIntent.setPackage("com.facebook.katana");

*/
            //checking if at least a map application is installed on the device so it opens it automatically for the navigation
            // else the app crashes
            //if more than one map application is installed, user is asked which to use for the action
            //just using if else statements also works perfectly without try catch
            try {
              /*  if (TapNavigateWithWazeIntent.resolveActivity(getPackageManager()) != null) {

                    TapNavigateWithWazeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
                startActivity(TapNavigateWithWazeIntent);

                //}
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Waze is not installed on your device... \nPlease Install Waze to handle task", Toast.LENGTH_SHORT).show();
                Uri wazeIntentUri = Uri.parse("https://play.google.com/store/apps/details?id=com.waze");
                Intent wazeIntent = new Intent(Intent.ACTION_VIEW, wazeIntentUri);

                wazeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(wazeIntent);


            }
            tapNavigationDialog.dismiss();

        });
        googleMapsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri navIntentUri = Uri.parse("google.navigation:q=" + mDestinationLat + "," + mDestinationLong); //google.navigation:q=
                Intent tapNavigationIntent = new Intent(Intent.ACTION_VIEW, navIntentUri);
                tapNavigationIntent.setPackage("com.google.android.apps.maps");
                //checking if at least a map application is installed on the device so it opens it automatically for the navigation
                // else the app crashes
                //if more than one map application is installed, user is asked which to use for the action
                //just using if else statements also works perfectly without try catch
                try {

                    startActivity(tapNavigationIntent);


                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MapsActivity.this, "Google map is not installed on your device... \nPlease Install Google maps to handle task", Toast.LENGTH_SHORT).show();
                    Uri gmmIntentUri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                    Intent googleMapsIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                    googleMapsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(googleMapsIntent);

                }
                tapNavigationDialog.dismiss();
            }
        });


        tapNavigationDialog.show();
        tapNavigationDialog.setCancelable(true);
        tapNavigationDialog.setCanceledOnTouchOutside(false);


    }

   /* public void openBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getApplicationContext(), R.style.BottomSheetDialogTheme);
        View navigateBottomSheetView = LayoutInflater.from(MapsActivity.this)
                .inflate(R.layout.bottomsheet_navigate, (CardView) findViewById(R.id.bottomSheetLayout));

        navigateBottomSheetView.findViewById(R.id.bottomSheetNavigateCardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri navIntentUri = Uri.parse("google.navigation:q=" + destinationLat + "," + destinationLong);
                Intent navigationIntent = new Intent(Intent.ACTION_VIEW, navIntentUri);
                navigationIntent.setPackage("com.google.android.apps.maps");
                //checking if at least a map application is installed on the device so it opens it automatically for the navigation
                // else the app crashes
                //if more than one map application is installed, user is asked which to use for the action


                if (navigationIntent.resolveActivity(getPackageManager()) != null) {

                    navigationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(navigationIntent);

                } else {
                    Toast.makeText(getApplicationContext(), "Google map is not installed on your device... \nPlease Install Google maps to handle task", Toast.LENGTH_SHORT).show();
                    Uri gmmIntentUri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                    Intent googleMapsIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                    googleMapsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(googleMapsIntent);

                }


            }
        });

    }*/
    // method to show navigateBottomSheet


    private void getRouteToDestination() {
        currentPosition = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(false)
                .waypoints(currentPosition, destinationLatLng)
                .build();
        routing.execute();

    }

    private void animateNavigationDrawer() {

        drawer.setScrimColor(getResources().getColor(R.color.blue4));


        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Scale the view based on slideOffset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float OffsetScale = 1 - diffScaledOffset;
                wholeScreen.setScaleX(OffsetScale);
                wholeScreen.setScaleY(OffsetScale);


//                Translate view to account for the scale width

                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = wholeScreen.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                wholeScreen.setTranslationX(xTranslation);


            }
        });

    }


    public void btnLegend(View view) {
        ExpandableRelativeLayout trafficLegendExpandableLayout = (ExpandableRelativeLayout) findViewById(R.id.trafficLegend_expLayout);

        trafficLegendExpandableLayout.toggle();

        trafficLegendExpandableLayout.expand();

        trafficLegendExpandableLayout.collapse();


        // trafficLegendExpandableLayout.initLayout();

    }


    public void setSupportActionBar(Toolbar toolbar) {
        //setSupportActionBar(toolbar);

    }


    // test api key with this
    //https://maps.googleapis.com/maps/api/directions/json?mode=driving&transit_routing_preference=less_driving&origin=6.686979,%20-1.577775&destination=adum+road&key=AIzaSyCwMe6cI9XR8PUQUDojS4n9lngIpe-G-Ww

    public void getDirection() {
        /*final double latitude = mLastLocation.getLatitude();
        final double longitude = mLastLocation.getLongitude();*/
        //LatLng currentPosition = new LatLng(latitude, longitude);

        currentPosition = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                .target(currentPosition)
                .zoom(20f)
                .tilt(70f)
                .build()));


        String requestApi = null;

        try {
            requestApi = "https://maps.googleapis.com/maps/api/directions/json?" +
                    "mode=driving&" +
                    "transit_routing_preference=less_driving&" +
                    "origin=" + currentPosition.latitude + "," + currentPosition.longitude + "&" +
                    "destination=" + destinationLat + "," + destinationLong + "&" +
                    "key=" + getResources().getString(R.string.google_direction_api);

            Log.d(TAG, requestApi);  // print url for debug
            mService.getPath(requestApi)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());

                                JSONArray jsonArray = jsonObject.getJSONArray("routes");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject route = jsonArray.getJSONObject(i);
                                    JSONObject poly = route.getJSONObject("overview_polyline");
                                    String polyline = poly.getString("points");
                                    polyLineList = decodePoly(polyline);

                                }
                                //Adjust Bounds
                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                for (LatLng latLng : polyLineList)
                                    builder.include(latLng);
                                LatLngBounds bounds = builder.build();
                                CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 2);
                                mMap.animateCamera(mCameraUpdate);

                                polylineOptions = new PolylineOptions();
                                polylineOptions.color(getResources().getColor(R.color.quantum_lightblue600));
                                polylineOptions.width(15);
                                polylineOptions.zIndex(10f);

                                polylineOptions.startCap(new RoundCap());
                                polylineOptions.endCap(new RoundCap());
                                polylineOptions.jointType(JointType.ROUND);
                                polylineOptions.addAll(polyLineList);
                                greyPolyline = mMap.addPolyline(polylineOptions);


                                blackPolylineOptions = new PolylineOptions();
                                blackPolylineOptions.color(Color.BLACK);
                                blackPolylineOptions.width(10);
                                blackPolylineOptions.startCap(new SquareCap());
                                blackPolylineOptions.endCap(new SquareCap());
                                blackPolylineOptions.jointType(JointType.ROUND);
                                blackPolyline = mMap.addPolyline(blackPolylineOptions);

                                //destinationMarker1 = mMap.addMarker(new MarkerOptions().position(polyLineList.get(polyLineList.size() - 1)).title("Destination").icon(BitmapDescriptorFactory.fromResource(R.drawable.client)));

                                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))).setPosition(polyLineList.get(polyLineList.size() - 1));


//                                Animation
                                ValueAnimator polyLineAnimator = ValueAnimator.ofInt(0, 100);
                                polyLineAnimator.setDuration(2000);
                                polyLineAnimator.setInterpolator(new LinearInterpolator());
                                polyLineAnimator.addUpdateListener(valueAnimator -> {
                                    List<LatLng> points = greyPolyline.getPoints();
                                    int percentValue = (int) valueAnimator.getAnimatedValue();
                                    int size = points.size();
                                    int newPoints = (int) (size * (percentValue / 100.0f));
                                    List<LatLng> p = points.subList(0, newPoints);
                                    greyPolyline.setPoints(p);


                                });

                                //Add Animation of car for route
                                polyLineAnimator.start();

                               /* mUserMarker = mMap.addMarker(new MarkerOptions().position(currentPosition).flat(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
                                handler = new Handler();
                                index = -1;
                                next = 1;
                                handler.postDelayed(drawPathRunnable, 3000);
*/
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(MapsActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getOnMapTapDirection() {
        /*final double latitude = mLastLocation.getLatitude();
        final double longitude = mLastLocation.getLongitude();*/
        //LatLng currentPosition = new LatLng(latitude, longitude);


        currentPosition = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                .target(currentPosition)
                .zoom(20f)
                .tilt(70f)
                .build()));

        String requestApi = null;

        try {
            requestApi = "https://maps.googleapis.com/maps/api/directions/json?" +
                    "mode=driving&" +
                    "transit_routing_preference=less_driving&" +
                    "origin=" + currentPosition.latitude + "," + currentPosition.longitude + "&" +
                    "destination=" + mDestinationLat + "," + mDestinationLong + "&" +
                    "key=" + getResources().getString(R.string.google_direction_api);

            Log.d(TAG, requestApi);  // print url for debug
            mService.getPath(requestApi)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());

                                JSONArray jsonArray = jsonObject.getJSONArray("routes");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject route = jsonArray.getJSONObject(i);
                                    JSONObject poly = route.getJSONObject("overview_polyline");
                                    String polyline = poly.getString("points");
                                    polyLineList = decodePoly(polyline);

                                }
                                //Adjust Bounds
                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                for (LatLng latLng : polyLineList)
                                    builder.include(latLng);
                                LatLngBounds bounds = builder.build();
                                CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 2);
                                mMap.animateCamera(mCameraUpdate);

                                polylineOptions = new PolylineOptions();
                                polylineOptions.color(getResources().getColor(R.color.quantum_lightblue600)); //quantum_lightblue600
                                polylineOptions.width(15);
                                polylineOptions.zIndex(10f);
                                polylineOptions.startCap(new RoundCap());
                                polylineOptions.endCap(new RoundCap());
                                polylineOptions.jointType(JointType.ROUND);
                                polylineOptions.addAll(polyLineList);
                                greyPolyline = mMap.addPolyline(polylineOptions);


                                blackPolylineOptions = new PolylineOptions();
                                blackPolylineOptions.color(getResources().getColor(R.color.smokyBlack));
                                blackPolylineOptions.width(10);
                                blackPolylineOptions.startCap(new SquareCap());
                                blackPolylineOptions.endCap(new SquareCap());
                                blackPolylineOptions.jointType(JointType.ROUND);
                                blackPolyline = mMap.addPolyline(blackPolylineOptions);

                                //destinationMarker1 = mMap.addMarker(new MarkerOptions().position(polyLineList.get(polyLineList.size() - 1)).title("Destination").icon(BitmapDescriptorFactory.fromResource(R.drawable.client)));

                                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))).setPosition(polyLineList.get(polyLineList.size() - 1));


//                                Animation
                                ValueAnimator polyLineAnimator = ValueAnimator.ofInt(0, 100);
                                polyLineAnimator.setDuration(2000);
                                polyLineAnimator.setInterpolator(new LinearInterpolator());
                                polyLineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                        List<LatLng> points = greyPolyline.getPoints();
                                        int percentValue = (int) valueAnimator.getAnimatedValue();
                                        int size = points.size();
                                        int newPoints = (int) (size * (percentValue / 100.0f));
                                        List<LatLng> p = points.subList(0, newPoints);
                                        blackPolyline.setPoints(p);


                                    }


                                });

                                //Add Animation of car for route
                                polyLineAnimator.start();

                               /* mUserMarker = mMap.addMarker(new MarkerOptions().position(currentPosition).flat(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
                                handler = new Handler();
                                index = -1;
                                next = 1;
                                handler.postDelayed(drawPathRunnable, 3000);
*/
                            } catch (Exception e) {
                                e.printStackTrace();

                            }


                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            // Toast.makeText(MapsActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(MapsActivity.this, "Please check your internet connection \nYou need internet access to get directions", Toast.LENGTH_SHORT).show();


                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //decodePoly class code gotten fron online from
    // https://github.com/bashantad/Tourfit-android/blob/master/src/com/example/tourfit/DirectionsJSONParser.java

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (checkPlayServices()) {
                        // buildGoogleApiClient();
                        createLocationRequest();
                        if (locationSwitch.isChecked()) {
                            displayLocation();
                        }

                    }


                }
            }
        }
    }

    // check if location request is not granted then you request permission to access location else if granted then you display location
    private void setUpLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //Request runtime permission
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            }, MY_PERMISSION_REQUEST_CODE);

        } else {
            if (checkPlayServices()) {
                //buildGoogleApiClient();
                createLocationRequest();
                if (locationSwitch.isChecked()) {
                    displayLocation();
                }
            }

        }


    }


    // GravityCompat.START is for when drawer is drawn from start (left side)
    //GravityCompat.END would be for when drawer is drawn from end (right side)
    @Override
    public void onBackPressed() {
        //drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            navigationView.setCheckedItem(R.id.nav_map);


        } else {

            if (poiMarker != null) {
                poiMarker.remove();
            }

            if (navigateCardView.getVisibility() == View.VISIBLE) {
                navigateCardView.setVisibility(View.GONE);
                if (mBlinkingCardView) {

                    navigateCardView.clearAnimation();
                    mBlinkingCardView = false;
                }

                if (destinationMarker != null) {
                    destinationMarker.remove();
                }


                //remove polyline if it drawn on map...
                if (greyPolyline != null) {
                    greyPolyline.remove();
                }
                //mMap.clear();
                displayOnOffButton.setText(R.string.you_are_online);

            } else {
                if (tapNavigateCardView.getVisibility() == View.VISIBLE) {
                    tapNavigateCardView.setVisibility(View.GONE);
                    if (mBlinkingCardView) {

                        tapNavigateCardView.clearAnimation();
                        mBlinkingCardView = false;
                    }

                    if (destinationMarker != null) {
                        destinationMarker.remove();
                    }


                    //use this instead of mMap.clear() to only remove polyline if it drawn on map...
                    if (greyPolyline != null) {
                        greyPolyline.remove();
                    }
                    //mMap.clear();

                    if (locationSwitch.isChecked()) {
                        displayOnOffButton.setText(R.string.you_are_onRoad);
                    } else {
                        displayOnOffButton.setText(R.string.you_are_offRoad);
                    }


                } else {
                    showExitDialog();
                }
            }


        }


    }


    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        //new locationRequest
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        //PRIORITY_HIGH_ACCURACY consumes more phone battery power
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(DISPLACEMENT);


    }


   /* private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }*/

    // check if Google Play Services are installed on user's device
    private boolean checkPlayServices() {
       /* int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICE_RES_REQUEST).show();
            } else {
                Toast.makeText(this, "Please your device does not Support Google Play Services", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;

        }

        return true;*/

        GoogleApiAvailability gApi = GoogleApiAvailability.getInstance();
        int resultCode = gApi.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (gApi.isUserResolvableError(resultCode)) {
                gApi.getErrorDialog(this, resultCode, PLAY_SERVICE_RES_REQUEST).show();
            } else {
                Toast.makeText(this, "Please your device does not Support Google Play Services", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;

    }


    //remove Location with removeLocationUpdates when offline without locationRequest

    private void stopLocationUpdates() {


        //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);

       /* String driverId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference driverAvailableRef = FirebaseDatabase.getInstance().getReference("driversAvailable");
        GeoFire geoFire = new GeoFire(driverAvailableRef);
        geoFire.removeLocation(driverId);  */


    }

    private void startLocationUpdates() {
// this adds the blue dot to represent ur current updated location ..u can set it to false if u dnt want it

        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }*/
//        add blue dot to location
        /* mMap.setMyLocationEnabled(true);*/


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //Request runtime permission
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            }, MY_PERMISSION_REQUEST_CODE);
        }
        mMap.setMyLocationEnabled(true);
        //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

    }


    // rotate marker
    //remember to change int to float
    //it was private
    //GoogleMap mMap
    private void rotateMarker(final Marker mUserMarker, final float toRotation) {


        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final float startRotation = mUserMarker.getRotation();
        final long duration = 1500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                float rot = t * toRotation + (1 - t) * startRotation;
                mUserMarker.setRotation(-rot > 180 ? rot / 2 : rot);


                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }


            }
        });


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (!isInternetConnected(this)) {

            showCheckInternetDialog();
        }

        if (!isLocationEnabled(this)) {
            showLocationSettingDialog();
        }

//        set light_map_style as the default start map
        try {
            boolean isSuccess = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.new_day_map));

            if (!isSuccess) {
                Toast.makeText(this, "Failed to load map...Check your network connection !", Toast.LENGTH_SHORT).show();
                Log.e("ERROR", "Failed to load map");

            }

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);


        // mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(false);

        // left, top, right, bottom ...75 pushes compass below search fragment...200 pushes Google tag on map to display above bottom cardview
        mMap.setPadding(13, 75, 0, 200);

        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        // mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().isCompassEnabled();
        mMap.getUiSettings().setCompassEnabled(true);


        mMap.getUiSettings().setMyLocationButtonEnabled(false); //this set as false removes the setMyLocationButtonEnabled button from map even if mMap.setMyLocationEnabled is set to true (adds the blue dot to represent current updated location)

        mMap.setInfoWindowAdapter(new CustomInfoWindow(this));  //this makes the custom info window display as designed

       /* mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View myView;
                if (marker == destinationMarker) {

                    myView = LayoutInflater.from(MapsActivity.this).inflate(R.layout.custom_marker_info_window_2, null);
                    TextView infoTitle = ((TextView) myView.findViewById(R.id.txtMarkerInfoWindowTitle));
                    infoTitle.setText(marker.getTitle());


                    TextView infoSnippet = ((TextView) myView.findViewById(R.id.txtInfoWindowSnippet));
                    infoSnippet.setText(marker.getSnippet());


                    Button goButton = ((Button) myView.findViewById(R.id.go_dest_butt));
                    Button shareDestButt = ((Button) myView.findViewById(R.id.share_dest_butt));

                    goButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Toast.makeText(getApplicationContext(), "i clicked go button", Toast.LENGTH_SHORT).show();

                        }
                    });

                    shareDestButt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "i clicked go button", Toast.LENGTH_SHORT).show();

                        }
                    });


                    return myView;
                } else {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    myView = inflater.inflate(R.layout.custom_marker_info_window, null);
                    TextView infoTitle = ((TextView) myView.findViewById(R.id.txtMarkerInfoWindowTitle));
                    infoTitle.setText(marker.getTitle());


                    TextView infoSnippet = ((TextView) myView.findViewById(R.id.txtInfoWindowSnippet));
                    infoSnippet.setText(marker.getSnippet());

                }

                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        }); */
        mMap.getUiSettings().setZoomGesturesEnabled(true);  //allows you to zoom in and out manually without a button
        mMap.getUiSettings().setMapToolbarEnabled(false);  // HIDE "Navigation" and "GPS Pointer" buttons on marker click


        // Set a listener for marker click.
        //mMap.setOnMarkerClickListener(this);


        //Map long click event
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                if (locationSwitch.isChecked()) {
                    if (destinationMarker != null) {
                        destinationMarker.remove();
                    }

                    //remove polyline if it drawn on map...
                    if (greyPolyline != null) {
                        greyPolyline.remove();
                    }

                    if (poiMarker != null) {
                        poiMarker.remove();
                    }

                    //mMap.clear();

                    if (navigateCardView.getVisibility() == View.VISIBLE) {
                        navigateCardView.setVisibility(View.GONE);
                        if (mBlinkingCardView) {

                            navigateCardView.clearAnimation();
                            mBlinkingCardView = false;
                        }
                    }

                    tapNavigateCardView.setVisibility(View.VISIBLE);
                    if (!mBlinkingCardView) {
                        tapNavigateCardView.startAnimation(blinkAnim);
                        mBlinkingCardView = true;
                    }

                    //  mDestination = String.valueOf(latLng);

                    LatLng mTapDestinationLatLng = new LatLng(latLng.latitude, latLng.longitude);
                    mDestination = String.valueOf(mTapDestinationLatLng);
                    mDestinationLat = String.valueOf(mTapDestinationLatLng.latitude);
                    mDestinationLong = String.valueOf(mTapDestinationLatLng.longitude);

                    //getting address when map is clicked
                    /*List<Address> addresses = new ArrayList<>();
                    try {
                        addresses = geocoder.getFromLocation(mTapDestinationLatLng.latitude, mTapDestinationLatLng.longitude, 1);
                    }catch (IOException e) {
                        e.printStackTrace();
                    }

                    android.location.Address address = addresses.get(0);
                    if (address != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            stringBuilder.append(address.getAddressLine(i) + "\n");
                        }
                        destinationMarker = mMap.addMarker(new MarkerOptions().position(mTapDestinationLatLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title(stringBuilder.toString()).snippet(mDestinationLat + "," + mDestinationLong).flat(false));

                        destinationMarker.showInfoWindow();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
                        getOnMapTapDirection();

                    }
*/
                    destinationMarker = mMap.addMarker(new MarkerOptions().position(mTapDestinationLatLng).icon(bitmapDescriptorFromVector(MapsActivity.this, R.drawable.ic_user_destination_marker)).title("Destination").flat(false)); //.snippet(mDestinationLat + "," + mDestinationLong)

                    destinationMarker.showInfoWindow();


                    //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
                    mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                            .target(mTapDestinationLatLng)
                            .zoom(16f)
                            .tilt(70f)
                            .build()));
                    getOnMapTapDirection();

                    //set distance to destination
                    Location startLocation = new Location("");
                    startLocation.setLatitude(currentPosition.latitude);
                    startLocation.setLongitude(currentPosition.longitude);

                    Location TapdestinationLocation = new Location("");
                    TapdestinationLocation.setLatitude(latLng.latitude);
                    TapdestinationLocation.setLongitude(latLng.longitude);


                    //float lineDistance = polyLineList.size() - 1;

                    float distance = startLocation.distanceTo(TapdestinationLocation);
                    DecimalFormat DecimalPlace = new DecimalFormat("#0.00");

                    displayOnOffButton.setText(String.format("~ %s km to %s", DecimalPlace.format(distance * 0.001), "Destination"));

                } else {

                    if (!locationSwitch.isChecked()) {

                        if (destinationMarker != null) {
                            destinationMarker.remove();
                        }

                        if (poiMarker != null) {
                            poiMarker.remove();
                        }

                        //remove polyline if it drawn on map...
                        if (greyPolyline != null) {
                            greyPolyline.remove();
                        }
                        //mMap.clear();

                        if (navigateCardView.getVisibility() == View.VISIBLE) {
                            navigateCardView.setVisibility(View.GONE);
                            if (mBlinkingCardView) {
                                navigateCardView.clearAnimation();
                                mBlinkingCardView = false;
                            }
                        }

                        tapNavigateCardView.setVisibility(View.VISIBLE);
                        if (!mBlinkingCardView) {
                            tapNavigateCardView.startAnimation(blinkAnim);
                            mBlinkingCardView = true;
                        }
                        //  mDestination = String.valueOf(latLng);

                        LatLng mTapDestinationLatLng = new LatLng(latLng.latitude, latLng.longitude);
                        mDestination = String.valueOf(mTapDestinationLatLng);
                        mDestinationLat = String.valueOf(mTapDestinationLatLng.latitude);
                        mDestinationLong = String.valueOf(mTapDestinationLatLng.longitude);

                        //getting address when map is clicked
                    /*List<Address> addresses = new ArrayList<>();
                    try {
                        addresses = geocoder.getFromLocation(mTapDestinationLatLng.latitude, mTapDestinationLatLng.longitude, 1);
                    }catch (IOException e) {
                        e.printStackTrace();
                    }

                    android.location.Address address = addresses.get(0);
                    if (address != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            stringBuilder.append(address.getAddressLine(i) + "\n");
                        }
                        destinationMarker = mMap.addMarker(new MarkerOptions().position(mTapDestinationLatLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).title(stringBuilder.toString()).snippet(mDestinationLat + "," + mDestinationLong).flat(false));

                        destinationMarker.showInfoWindow();
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
                        getOnMapTapDirection();

                    }
*/

                        destinationMarker = mMap.addMarker(new MarkerOptions().position(mTapDestinationLatLng).icon(bitmapDescriptorFromVector(MapsActivity.this, R.drawable.ic_user_destination_marker)).title("Destination").snippet(mDestinationLat + "," + mDestinationLong).flat(false));

                        destinationMarker.showInfoWindow();
                        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                .target(mTapDestinationLatLng)
                                .zoom(16f)
                                .tilt(60f)
                                .build()));
                        getOnMapTapDirection();

                        //set distance to destination
                        Location startLocation = new Location("");
                        startLocation.setLatitude(currentPosition.latitude);
                        startLocation.setLongitude(currentPosition.longitude);

                        Location TapdestinationLocation = new Location("");
                        TapdestinationLocation.setLatitude(latLng.latitude);
                        TapdestinationLocation.setLongitude(latLng.longitude);

                        float distance = startLocation.distanceTo(TapdestinationLocation);
                        DecimalFormat DecimalPlace = new DecimalFormat("#0.00");

                        displayOnOffButton.setText(String.format("~ %s km to %s", DecimalPlace.format(distance * 0.001), "Destination"));

                        Toast.makeText(MapsActivity.this, "Please you are Offline", Toast.LENGTH_SHORT).show();


                        // displayOnOffButton.setText(R.string.youareoffline);
                    }


                }

            }


        });


        setPoiClick(mMap);
    }

    private void showLocationSettingDialog() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            locationSettingDialog.setContentView(R.layout.location_setting_layout);
            Objects.requireNonNull(locationSettingDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            locationSettingDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationScale;


            Button noThanksButton = locationSettingDialog.findViewById(R.id.noThanksImg);
            Button turnOnButton = locationSettingDialog.findViewById(R.id.connectImg);

            noThanksButton.setOnClickListener(v -> locationSettingDialog.dismiss());

            turnOnButton.setOnClickListener(v -> {

                locationSettingDialog.dismiss();
                Intent openLocationSettingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(openLocationSettingsIntent);

            });

            locationSettingDialog.show();
            locationSettingDialog.setCancelable(false);
            locationSettingDialog.setCanceledOnTouchOutside(false);


        } else {
            showSimpleLocationSettingDialog();
        }


    }

    private void showSimpleLocationSettingDialog() {
        AlertDialog.Builder LocationSettingDialog = new AlertDialog.Builder(MapsActivity.this);

        LocationSettingDialog.setMessage(R.string.turn_on_your_device_s_location_access)
                .setCancelable(false)
                .setPositiveButton("TURN ON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                });

        LocationSettingDialog.setNegativeButton("NO THANKS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        LocationSettingDialog.show();
    }


    private boolean isInternetConnected(MapsActivity mapsActivity) {

        ConnectivityManager connectivityManager = (ConnectivityManager) mapsActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConnect = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnect = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConnect != null && wifiConnect.isConnected()) || (mobileConnect != null && mobileConnect.isConnected())) {

            return true;

        } else {
            return false;
        }


    }

    public static Boolean isLocationEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
// This is new method provided in API 28
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            return locationManager != null && locationManager.isLocationEnabled();
        } else {
// This is Deprecated in API 28
            int mode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE,
                    Settings.Secure.LOCATION_MODE_OFF);
            return (mode != Settings.Secure.LOCATION_MODE_OFF);

        }
    }


    private void showCheckInternetDialog() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            networkCheckErrorDialog.setContentView(R.layout.internet_check_layout);
            Objects.requireNonNull(networkCheckErrorDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            networkCheckErrorDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationScale;


            Button noThanksButton = networkCheckErrorDialog.findViewById(R.id.noThanksImg);
            Button connectButton = networkCheckErrorDialog.findViewById(R.id.connectImg);

            noThanksButton.setOnClickListener(v -> networkCheckErrorDialog.dismiss());

            connectButton.setOnClickListener(v -> {

                networkCheckErrorDialog.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    startActivity(new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY));
                } else {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }

            });

            networkCheckErrorDialog.show();
            networkCheckErrorDialog.setCancelable(false);
            networkCheckErrorDialog.setCanceledOnTouchOutside(false);


        } else {
            showSimpleCheckInternetDialog();
        }


    }

    private void showSimpleCheckInternetDialog() {
        AlertDialog.Builder checkConnectionDialog = new AlertDialog.Builder(MapsActivity.this);

        checkConnectionDialog.setMessage("Internet is required for full map functionality")
                .setCancelable(false)
                .setPositiveButton("CONNECT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                });

        checkConnectionDialog.setNegativeButton("NO THANKS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        checkConnectionDialog.show();

    }


   /* @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        startLocationUpdates();

    }*/
    // display location

    public float getBearing(LatLng begin, LatLng end) {
        /*begin = new LatLng(begin.latitude, begin.longitude);
        end = new LatLng(end.latitude, end.longitude);*/
        double dLon = (end.longitude - begin.longitude);
        double x = Math.sin(Math.toRadians(dLon)) * Math.cos(Math.toRadians(end.latitude));
        double y = Math.cos(Math.toRadians(begin.latitude)) * Math.sin(Math.toRadians(end.latitude))
                - Math.sin(Math.toRadians(begin.latitude)) * Math.cos(Math.toRadians(end.latitude)) * Math.cos(Math.toRadians(dLon));
        double bearing = Math.toDegrees((Math.atan2(x, y)));
        return (float) bearing;
    }

    public void updateCameraBearing(GoogleMap mMap, float bearing) {
        if (mMap == null) return;
        CameraPosition camPos = CameraPosition
                .builder(
                        mMap.getCameraPosition() // current Camera
                )
                .target(camera)
                .zoom(16f)
                .tilt(50f)
                .bearing(bearing)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));
    }

    LatLng camera;

    private void displayLocation() {
// if location request permission is not granted by user then return (ie. no location is shown and subsequent methods to be executed after location permission is granted dnt start at all)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            }, MY_PERMISSION_REQUEST_CODE);
        }

        // mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                mLastLocation = locationResult.getLastLocation();

                if (mLastLocation != null) {

                    if (locationSwitch.isChecked()) {
                        //get the double or more accurate values ( ie. more decimal points) of variables, latitude and longitude
                        // of driver's location

                        final double latitude = mLastLocation.getLatitude();
                        final double longitude = mLastLocation.getLongitude();


                        //add Marker to current location
                        //if marker is already on the map for
                        // location, remove it and set marker to updated location
                        if (mUserMarker != null) {
                            mUserMarker.remove();
                        }
                        currentLocation = new LatLng(latitude, longitude);
                        if (mUserMarker != null) {
                            mUserMarker.remove();

                        }


                        //user loc marker
                        mUserMarker = mMap.addMarker(new MarkerOptions().position(currentLocation).title("My Location").icon(bitmapDescriptorFromVector(MapsActivity.this, R.drawable.ic_user_location_marker)).flat(false).zIndex(1f)); //.anchor(0.5f, 0.5f)

                        //setting center to lower with a latitude offset of 0.0026
                        double offset = 0.0026;
                        camera = new LatLng(mUserMarker.getPosition().latitude + offset, mUserMarker.getPosition().longitude);

                        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(camera, 16f));


                        //mUserMarker.setRotation(0.5f);


                        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15.5f));


//setLatLngBoundsForCameraTarget keeps camera at a pivot of currentLocation and only allow rotation about the currentLocation marker
/*mMap.setLatLngBoundsForCameraTarget(LatLngBounds.builder()
        .include(currentLocation)
        .build());*/

                        /*float bearing = 0;
                        CameraPosition camPos = CameraPosition
                                .builder(mMap.getCameraPosition())
                                .target(camera)
                                .zoom(16f)
                                .tilt(50f)
                                .bearing(bearing)
                                .build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(camPos));*/


                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                .target(camera)
                                .zoom(16f)
                                .tilt(50f)
                                .bearing(0.0f)
                                .build()));


                        //move camera to this location
                        //move center of map below
                /*LatLng mapCenter = mMap.getCameraPosition().target;
                Projection projection = mMap.getProjection();
                Point centerPoint = projection.toScreenLocation(mapCenter);

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int displayHeight = displayMetrics.heightPixels;


                centerPoint.y = centerPoint.y - (int) (displayHeight / 5.0);  // move center down for approx 22%


                LatLng newCenterPoint = projection.fromScreenLocation(centerPoint);

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newCenterPoint, 16f)); */

                        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
                            }, MY_PERMISSION_REQUEST_CODE);
                        }
                        mMap.setMyLocationEnabled(true);
                    } else {

                        final double latitude = mLastLocation.getLatitude();
                        final double longitude = mLastLocation.getLongitude();
                        currentLocation = new LatLng(latitude, longitude);

                        mMap.setMyLocationEnabled(true);
                        double offset = 0.0026;
                        LatLng camera = new LatLng(currentLocation.latitude + offset, currentLocation.longitude);

                        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(camera, 16f));

                    }

                } else {
                    Toast.makeText(MapsActivity.this, "Difficult getting your precise location", Toast.LENGTH_SHORT).show();
                    Log.d("ERROR!!", "cannot get your location");

                }

            }
        };


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        //mMap.setMyLocationEnabled(true);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

    }




    /*@Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();

    }*/

  /*  @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }*/


    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        displayLocation();
        updateCameraBearing(mMap, location.getBearing());


    }





    /*@Override
    public boolean onMarkerClick(Marker marker) {

// Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);

            //if number of marker clicks is more than 1 (! = 1) ..u can set the function if so
           *//* if (clickCount != 1 ){

            }

            *//*
            Toast.makeText(this, marker.getTitle() + " has been clicked " + clickCount + " times.", Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }
*/

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.options_menu, menu);
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;


    }

    */

    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.optionsMenuFAQ){

            startActivity(new Intent(this, FaqActivity.class));
            return false;

        }else
            if (id == R.id.optionsMenuSettings) {

                Toast.makeText(DriverMapActivity.this, "You clicked Settings", Toast.LENGTH_SHORT).show();



            }
        else


        if (id == R.id.optionsMenuShare){

            Toast.makeText(getApplicationContext(), "You clicked Share", Toast.LENGTH_SHORT).show();



        }else


        //this is for changing light map to dark map style
        if (id == R.id.optionsMenuDarkMap){

            boolean darkMap = false;
            if(darkMap){
                darkMap = true;

            }

            Toast.makeText(DriverMapActivity.this, "You clicked Dark Map", Toast.LENGTH_SHORT).show();


        }else

        if (id == R.id.optionsMenuSearchPlace){

            Toast.makeText(DriverMapActivity.this, "You clicked Search", Toast.LENGTH_SHORT).show();


        }



       /* switch (item.getItemId()){

            case R.id.optionsMenuFAQ:
                Intent intent = new Intent (this, FaqActivity.class);
                this.startActivity(intent);

                break;


            case R.id.optionsMenuSettings:

                Toast.makeText(DriverMapActivity.this, "You clicked Settings", Toast.LENGTH_SHORT).show();
                break;


            case R.id.optionsMenuShare:
                Toast.makeText(DriverMapActivity.this, "You clicked Share", Toast.LENGTH_SHORT).show();


                break;

            case R.id.optionsMenuSearchPlace:
                Toast.makeText(DriverMapActivity.this, "You clicked Search", Toast.LENGTH_SHORT).show();

                break;

            case R.id.optionsMenuDarkMap:
                Toast.makeText(DriverMapActivity.this, "You clicked Dark Map", Toast.LENGTH_SHORT).show();


                break;



        }



       // return onOptionsItemSelected(item);


        return onOptionsItemSelected(item);



    }

     */


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {


            case R.id.nav_map:
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Profile()).commit();


                break;


            case R.id.nav_inviteFriends:
                //startActivity(new Intent(DriverMapActivity.this, InviteFriendsActivity.class));


                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Hi there! Are you going somewhere today? \nI use OTrafyc as my guide for my daily commutes, add and share my moments on social platforms \nCommute with fun! \nDownload it for free on Google Play Store " + "\n\n https://play.google.com/store/apps/details?id=";
                String shareSub = "Download OTrafyc for GPS Navigation, Live Road Traffic, Fastest Routes,Turn by Turn Directions, Add and Share Moments!!!";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody + BuildConfig.APPLICATION_ID);
                startActivity(Intent.createChooser(sharingIntent, "Invite a friend via..."));

                break;


            case R.id.nav_rateUs:
                //startActivity(new Intent(DriverMapActivity.this, RateUsActivity.class));

                //you can  replace getPackageName() with OTrafyc package name as a string (ie. "com.otrafyc.android.traffic.otrafycapp")
                try {
                    Intent rateUsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName()));
                    startActivity(rateUsIntent);

                } catch (ActivityNotFoundException e) {

                    Intent playStore = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                    startActivity(playStore);

                }


                break;

            case R.id.nav_ContactUs:
                Intent ContactUsIntent = new Intent(MapsActivity.this, ContactUsActivity.class);

                // startActivity(new Intent(MapsActivity.this, ContactUsActivity.class));


                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(navigationView, "menu_to_Activities_transition");


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MapsActivity.this, pairs);


                    startActivity(ContactUsIntent, options.toBundle());
                } else {

                    startActivity(ContactUsIntent);

                }

                break;


            case R.id.nav_HowToUse:
                Intent HowToUseUsIntent = new Intent(MapsActivity.this, HowToUseActivity.class);

                // startActivity(new Intent(MapsActivity.this, ContactUsActivity.class));


                Pair[] pairs4 = new Pair[1];
                pairs4[0] = new Pair<View, String>(navigationView, "menu_to_Activities_transition");


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MapsActivity.this, pairs4);


                    startActivity(HowToUseUsIntent, options.toBundle());
                } else {

                    startActivity(HowToUseUsIntent);

                }

                break;

            case R.id.nav_Settings:
                Intent SettingsIntent = new Intent(MapsActivity.this, SettingsActivity.class);

                // startActivity(new Intent(MapsActivity.this, ContactUsActivity.class));
                //set transition animation
                Pair[] pairs2 = new Pair[1];
                pairs2[0] = new Pair<View, String>(navigationView, "menu_to_Activities_transition");


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MapsActivity.this, pairs2);


                    startActivity(SettingsIntent, options.toBundle());
                } else {

                    startActivity(SettingsIntent);

                }

                break;


            case R.id.nav_exit:

                showExitDialog();


                break;

            case R.id.nav_privacy_policy:
                Intent PrivacyIntent = new Intent(MapsActivity.this, PrivacyPolicyActivity.class);

                // startActivity(new Intent(MapsActivity.this, ContactUsActivity.class));

                Pair[] pairs1 = new Pair[1];
                pairs1[0] = new Pair<View, String>(navigationView, "menu_to_Activities_transition");


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MapsActivity.this, pairs1);


                    startActivity(PrivacyIntent, options.toBundle());
                } else {

                    startActivity(PrivacyIntent);

                }


                break;


            case R.id.nav_terms:
                Intent TermsIntent = new Intent(MapsActivity.this, TermsActivity.class);

                // startActivity(new Intent(MapsActivity.this, ContactUsActivity.class));

                Pair[] pairs3 = new Pair[1];
                pairs3[0] = new Pair<View, String>(navigationView, "menu_to_Activities_transition");


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MapsActivity.this, pairs3);


                    startActivity(TermsIntent, options.toBundle());
                } else {

                    startActivity(TermsIntent);

                }

                break;


        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        navigationView.setCheckedItem(R.id.nav_map);
        return true;
    }


    public void showExitDialog() {

        exitAppDialog.setContentView(R.layout.exit_dialog_layout);
        Objects.requireNonNull(exitAppDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exitAppDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationScale;


        final Button cancelButton = exitAppDialog.findViewById(R.id.cancelExitAppButton);
        final Button exitButton = exitAppDialog.findViewById(R.id.exitAppButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exitAppDialog.dismiss();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitAppDialog.dismiss();
                MapsActivity.super.onBackPressed();
            }
        });

        exitAppDialog.show();
        exitAppDialog.setCancelable(true);
        exitAppDialog.setCanceledOnTouchOutside(false);

        /*
        final AlertDialog.Builder exitDialog = new AlertDialog.Builder(this);

        // exitDialog.setMessage("Are you sure you want to exit app?");


        LayoutInflater inflater = LayoutInflater.from(this);
        final View exit_layout = inflater.inflate(R.layout.exit_dialog_layout, null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            exitDialog.setView(R.layout.exit_dialog_layout);

            exitDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    MapsActivity.super.onBackPressed();

                }
            });

            exitDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

        } else {
            super.onBackPressed();
        }


        exitDialog.show();
        */
    }


    boolean isMapStyleNotLight = false;

    public void changeMapStyle(View view) {

        FloatingActionButton darkLightMapfloatingActionButton = findViewById(R.id.dark_light_MapFab);

        darkLightMapfloatingActionButton.startAnimation(buttonBounceAnim);

        switch (view.getId()) {
            case R.id.dark_light_MapFab: {
                if (isMapStyleNotLight) {
                    mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.new_day_map));
                    isMapStyleNotLight = false;
                    Toast.makeText(this, "You set Day Map", Toast.LENGTH_SHORT).show();


                    // change background color and text color of legend button when map is light
                    //make trafficLegend_cardView and trafficLegendButton global variables to get access to them in this method

                    trafficLegendButton.setBackgroundColor(getResources().getColor(R.color.smokyBlack));
                    trafficLegendButton.setTextColor(getResources().getColor(R.color.quantum_white_text));

                    autocompleteCardView.setCardBackgroundColor(getResources().getColor(R.color.quantum_white_text));
                    autocompleteCardView.setAlpha(0.85f);

                    Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
                    TextView toolbarTitle = (TextView) findViewById(R.id.toolbalTitle);

                    toolbarTitle.setBackgroundColor(getResources().getColor(R.color.blue1));
                    toolbar1.setBackgroundColor(getResources().getColor(R.color.blue1));
                    toolbar1.setBackground(ContextCompat.getDrawable(this, R.drawable.search_place_onclick_ripple_backgrnd));
                    //toolbar1.setTitleTextAppearance(this, R.style.ToolbarTitleBlueBackgroundStyle);

                    howToUseIconLottie.setBackground(ContextCompat.getDrawable(this, R.drawable.search_place_onclick_ripple_backgrnd));
                    searchPlace.setBackground(ContextCompat.getDrawable(this, R.drawable.search_place_onclick_ripple_backgrnd));

                    bottomCardview.setBackgroundColor(getResources().getColor(R.color.blue12));


                } else {

                    mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.new_night_map_style));
                    isMapStyleNotLight = true;
                    Toast.makeText(MapsActivity.this, "You set Night Map", Toast.LENGTH_SHORT).show();

                    // change background color and text color of legend button when map is dark
                    //make trafficLegend_cardView and trafficLegendButton global variables to get access to them in this method

                    trafficLegendButton.setBackgroundColor(getResources().getColor(R.color.quantum_white_text));
                    trafficLegendButton.setTextColor(getResources().getColor(R.color.smokyBlack));

                    autocompleteCardView.setCardBackgroundColor(getResources().getColor(R.color.darkBlueBlackColor));
                    autocompleteCardView.setAlpha(0.85f);

                    Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
                    TextView toolbarTitle = (TextView) findViewById(R.id.toolbalTitle);
                    toolbarTitle.setBackgroundColor(getResources().getColor(R.color.darkBlueBlackColor));
                    toolbar1.setBackgroundColor(getResources().getColor(R.color.darkBlueBlackColor));
                    toolbar1.setBackground(ContextCompat.getDrawable(this, R.drawable.dark_ripple_background));
                    //toolbar1.setTitleTextAppearance(this, R.style.ToolbarTitleDarkBackgroundStyle);


                    howToUseIconLottie.setBackground(ContextCompat.getDrawable(this, R.drawable.dark_ripple_background2));
                    searchPlace.setBackground(ContextCompat.getDrawable(this, R.drawable.dark_ripple_background2));


                    bottomCardview.setBackgroundColor(getResources().getColor(R.color.darkBlueBlackColor));


                }


            }
        }

    }

    @Override
    public void onRoutingFailure(RouteException e) {
        if (e != null) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {

        if (polylines.size() > 0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();
        //add route(s) to the map.
        for (int i = 0; i < route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = i % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + i * 3);
            polyOptions.addAll(route.get(i).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polylines.add(polyline);

            Toast.makeText(getApplicationContext(), "Route " + (i + 1) + ": distance - " + route.get(i).getDistanceValue() + ": duration - " + route.get(i).getDurationValue(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRoutingCancelled() {

    }

    private void erasePolylines() {

        for (Polyline line : polylines) {
            line.remove();
        }
        polylines.clear();
    }

    public void whereTo(View view) {
        Toast.makeText(MapsActivity.this, "where to?", Toast.LENGTH_SHORT).show();
    }

    public void showHelpMessage(View view) {

        Toast.makeText(MapsActivity.this, "Choose your country code to return address search queries from only that country for faster results", Toast.LENGTH_LONG).show();


    }

    @Override
    protected void onPause() {
        mapFragment.onPause();
        super.onPause();
        stopLocationUpdates();


    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    protected void onResume() {
        super.onResume();


        mapFragment.onResume();
        checkNewAppVersionState();
        //check newo
        if (requestingLocationUpdates) {
            startLocationUpdates();
        }
        displayLocation();
    }


    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case REQ_CODE_VERSION_UPDATE:
                if (resultCode != RESULT_OK) { //RESULT_OK / RESULT_CANCELED / RESULT_IN_APP_UPDATE_FAILED
                    Toast.makeText(this, "Update flow failed! Result code: " + resultCode, Toast.LENGTH_SHORT).show();
                    // If the update is cancelled or fails,
                    // you can request to start the update again.
                    unregisterInstallStateUpdListener();
                }
                break;

            case (EDIT_REQUEST): {
                if (resultCode == Activity.RESULT_OK) {
                    MarkerOptions markerOptions = data.getParcelableExtra("marker");
                    mMap.addMarker(markerOptions);
                }
                break;


            }
            /*case REQUEST_CODE_SPEECH_INPUT: {
                if (requestCode == RESULT_OK && null != data) {
                    //Get text array from speech intent
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    // set speech to autocomplete fragment
                    places.setText(result.get(0));
                }
                break;


            }*/
        }
    }


    @Override
    protected void onStop() {
        mapFragment.onStop();
        super.onStop();

    }

    @Override
    public void onDestroy() {

        unregisterInstallStateUpdListener();
        mapFragment.onDestroy();

        super.onDestroy();
    }

    private void checkForAppUpdate() {
        // Creates instance of the appUpdateManager
        appUpdateManager = AppUpdateManagerFactory.create(this);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Create a listener to track request state updates.
        installStateUpdatedListener = new InstallStateUpdatedListener() {
            @Override
            public void onStateUpdate(InstallState installState) {
                // Show module progress, log state, or install the update.
                if (installState.installStatus() == InstallStatus.DOWNLOADED)
                    // After the update is downloaded, show a notification
                    // and request user confirmation to restart the app using popupSnackbarForCompleteUpdateAndUnregister() method .
                    popupMessageForCompleteUpdateAndUnregister();
            }
        };

        // Checks that the platform will allow the specified type of update.
        //lambda expression is used here with appUpdateInfo -> instead of new onSuccessListener<appUpdateInfo>
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                    // Request the update.
                    if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {

                        // Before starting an update, register a listener for updates.
                        appUpdateManager.registerListener(installStateUpdatedListener);
                        // Start an update.
                        MapsActivity.this.startAppUpdateFlexible(appUpdateInfo);
                    } /*
                else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    // Start an update.
                    MapsActivity.this.startAppUpdateImmediate(appUpdateInfo);
                }
                */
                }
            }
        });
    }


    /*private void startAppUpdateImmediate(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    MapsActivity.REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }*/

    private void startAppUpdateFlexible(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.FLEXIBLE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    MapsActivity.REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
            unregisterInstallStateUpdListener();
        }
    }

    /**
     * Displays the snackbar notification and call to action for app restart.
     * Needed only for Flexible app update
     */
    private void popupMessageForCompleteUpdateAndUnregister() {
        Snackbar snackbar = Snackbar.make(drawer, getString(R.string.update_downloaded), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.restart, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUpdateManager.completeUpdate();
            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.blue1));

        snackbar.show();

        unregisterInstallStateUpdListener();
    }

    /**
     * Checks that the update is not stalled during 'onResume()'.
     * However, you should execute this check at all app entry points.
     */


    private void checkNewAppVersionState() {
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                //FLEXIBLE:
                // If the update is downloaded but not installed,
                // notify the user to complete the update.
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    MapsActivity.this.popupMessageForCompleteUpdateAndUnregister();
                }

                //IMMEDIATE:
            /*
            if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                // If an in-app update is already running, resume the update.
                startAppUpdateImmediate(appUpdateInfo);
            }
            */
            }
        });

    }

    // Needed only for FLEXIBLE update
    private void unregisterInstallStateUpdListener() {
        if (appUpdateManager != null && installStateUpdatedListener != null)
            appUpdateManager.unregisterListener(installStateUpdatedListener);
    }


    // check new
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        // ...
        super.onSaveInstanceState(outState);

        outState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY,
                requestingLocationUpdates);
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return;
        }

        // Update the value of requestingLocationUpdates from the Bundle.
        if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
            requestingLocationUpdates = savedInstanceState.getBoolean(
                    REQUESTING_LOCATION_UPDATES_KEY);
        }
        if (memoryTitle != null) {
            memoryMarker.setPosition(currentLatLong);
        }

        // ...

        // Update UI to match restored state
        //updateUI();

    }

    private void setPoiClick(final GoogleMap map) {
        map.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
            @Override
            public void onPoiClick(PointOfInterest poi) {

                poiLatLng = new LatLng(poi.latLng.latitude, poi.latLng.longitude);
                poiLat = String.valueOf(poiLatLng.latitude);
                poiLong = String.valueOf(poiLatLng.longitude);
                String poiName =  poi.name;

                if (poiMarker != null) {
                    poiMarker.remove();
                }

                if (destinationMarker != null) {
                    destinationMarker.remove();
                }

                if (greyPolyline != null) {
                    greyPolyline.remove();
                }
                poiMarker = mMap.addMarker(new MarkerOptions().position(poiLatLng).icon(bitmapDescriptorFromVector(MapsActivity.this, R.drawable.ic_user_destination_marker)).title(poi.name).flat(false));
                poiMarker.showInfoWindow();

                if (tapNavigateCardView.getVisibility() == View.VISIBLE) {
                    tapNavigateCardView.setVisibility(View.GONE);
                    if (mBlinkingCardView) {

                        tapNavigateCardView.clearAnimation();
                        mBlinkingCardView = false;
                    }
                }

                if (navigateCardView.getVisibility() == View.VISIBLE) {
                    navigateCardView.setVisibility(View.GONE);
                    if (mBlinkingCardView) {

                        navigateCardView.clearAnimation();
                        mBlinkingCardView = false;
                    }
                }
                getPoiDirections();

                Location startLocation = new Location("");
                startLocation.setLatitude(currentPosition.latitude);
                startLocation.setLongitude(currentPosition.longitude);

                double poiLatD = Double.parseDouble(poiLat);
                double poiLongD = Double.parseDouble(poiLong);
                Location poiLocation = new Location("");
                poiLocation.setLatitude(poiLatD);
                poiLocation.setLongitude(poiLongD);


                float lineDistance = polyLineList.size() - 1;

                float distance = startLocation.distanceTo(poiLocation);
                DecimalFormat DecimalPlace = new DecimalFormat("#0.00");

                displayOnOffButton.setText(String.format("~ %s km to %s", DecimalPlace.format(distance * 0.001), poiName));


            }

        });


    }

    private void getPoiDirections() {

        // poiLatLng = new LatLng(poi.latLng.latitude, poi.latLng.longitude);
        poiLat = String.valueOf(poiLatLng.latitude);
        poiLong = String.valueOf(poiLatLng.longitude);


        currentPosition = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                .target(currentPosition)
                .zoom(20f)
                .tilt(70f)
                .build()));


        String requestApi = null;

        try {
            requestApi = "https://maps.googleapis.com/maps/api/directions/json?" +
                    "mode=driving&" +
                    "transit_routing_preference=less_driving&" +
                    "origin=" + currentPosition.latitude + "," + currentPosition.longitude + "&" +
                    "destination=" + poiLat + "," + poiLong + "&" +
                    "key=" + getResources().getString(R.string.google_direction_api);

            Log.d(TAG, requestApi);  // print url for debug
            mService.getPath(requestApi)
                    .enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());

                                JSONArray jsonArray = jsonObject.getJSONArray("routes");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject route = jsonArray.getJSONObject(i);
                                    JSONObject poly = route.getJSONObject("overview_polyline");
                                    String polyline = poly.getString("points");
                                    polyLineList = decodePoly(polyline);

                                }
                                //Adjust Bounds
                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                for (LatLng latLng : polyLineList)
                                    builder.include(latLng);
                                LatLngBounds bounds = builder.build();
                                CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 2);
                                mMap.animateCamera(mCameraUpdate);

                                polylineOptions = new PolylineOptions();
                                polylineOptions.color(getResources().getColor(R.color.quantum_lightblue600));
                                polylineOptions.width(15);
                                polylineOptions.zIndex(10f);

                                polylineOptions.startCap(new RoundCap());
                                polylineOptions.endCap(new RoundCap());
                                polylineOptions.jointType(JointType.ROUND);
                                polylineOptions.addAll(polyLineList);
                                greyPolyline = mMap.addPolyline(polylineOptions);


                                blackPolylineOptions = new PolylineOptions();
                                blackPolylineOptions.color(Color.BLACK);
                                blackPolylineOptions.width(10);
                                blackPolylineOptions.startCap(new SquareCap());
                                blackPolylineOptions.endCap(new SquareCap());
                                blackPolylineOptions.jointType(JointType.ROUND);
                                blackPolyline = mMap.addPolyline(blackPolylineOptions);

                                //destinationMarker1 = mMap.addMarker(new MarkerOptions().position(polyLineList.get(polyLineList.size() - 1)).title("Destination").icon(BitmapDescriptorFactory.fromResource(R.drawable.client)));

                                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))).setPosition(polyLineList.get(polyLineList.size() - 1));


//                                Animation
                                ValueAnimator polyLineAnimator = ValueAnimator.ofInt(0, 100);
                                polyLineAnimator.setDuration(2000);
                                polyLineAnimator.setInterpolator(new LinearInterpolator());
                                polyLineAnimator.addUpdateListener(valueAnimator -> {
                                    List<LatLng> points = greyPolyline.getPoints();
                                    int percentValue = (int) valueAnimator.getAnimatedValue();
                                    int size = points.size();
                                    int newPoints = (int) (size * (percentValue / 100.0f));
                                    List<LatLng> p = points.subList(0, newPoints);
                                    greyPolyline.setPoints(p);


                                });

                                //Add Animation of car for route
                                polyLineAnimator.start();

                               /* mUserMarker = mMap.addMarker(new MarkerOptions().position(currentPosition).flat(true).icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
                                handler = new Handler();
                                index = -1;
                                next = 1;
                                handler.postDelayed(drawPathRunnable, 3000);
*/
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(MapsActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

