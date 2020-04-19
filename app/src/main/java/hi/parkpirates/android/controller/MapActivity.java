package hi.parkpirates.android.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import hi.parkpirates.android.R;
import hi.parkpirates.android.model.GameInterface;
import hi.parkpirates.android.model.Response;
import hi.parkpirates.android.model.Result;
import hi.parkpirates.android.model.TreasurePin;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static hi.parkpirates.android.R.string.mapbox_access_token;


public class MapActivity extends AppCompatActivity implements GameInterface.PinCallback, OnMapReadyCallback, PermissionsListener {

	private GameInterface model = null;
	private ArrayList<Button> pinButtons = new ArrayList<>();
	private Button user, treasure, map, bury, logout;
	private MapView mapView;
	private MapboxMap mapboxMap;
	private PermissionsManager permissionsManager;


	// NOTE: Lifecycle methods overridden here for informational
	//	purposes only at present -- wanted to have a look at how/when
	//	the various methods were being used, and what circumstances
	//	would trigger them.
	@Override
	protected void onStart() {
		super.onStart();
		mapView.onStart();
		System.out.println("MAP: OnStart(..)");
	}

	@Override
	protected void onStop() {
		super.onStop();
		mapView.onStop();
		System.out.println("MAP: OnStop(..)");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
		System.out.println("MAP: OnDestroy(..)");
	}

	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
		System.out.println("MAP: OnPause(..)");
	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
		System.out.println("MAP: OnResume(..)");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}

	public void onDestroyView() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Mapbox.getInstance(this, "pk.eyJ1Ijoic3RqYXJuYSIsImEiOiJjazkxa3hkemwwMHhpM2xwNnhibzVqZXl2In0.EXS7da6cFAXgkkYcQkxwBw");
		setContentView(R.layout.activity_map);
		mapView = findViewById(R.id.mapView);
		mapView.onCreate(savedInstanceState);
		mapView.getMapAsync(new OnMapReadyCallback() {
			@Override
			public void onMapReady(@NonNull MapboxMap mapboxMap) {
				mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
					@Override
					public void onStyleLoaded(@NonNull Style style) {

// Map is set up and the style has loaded. Now you can add data or make other map adjustments.


					}
				});
			}
		});
		user = (Button)findViewById(R.id.layout_map_button_userInfo);
		treasure = (Button)findViewById(R.id.layout_map_button_treasureInfo);
		map = (Button)findViewById(R.id.layout_map_button_map);
		bury = (Button)findViewById(R.id.layout_map_button_bury);
		logout = (Button)findViewById(R.id.layout_map_button_login);

		model = getIntent().getParcelableExtra(getString(R.string.intent_key_game_interface));
		if (model == null) {
			System.err.println("MAP: Error -- failed to receive game interface.");
		} else {
			model.setContext(this);
			// Testing out dynamic generation of view elements.
			model.getActiveTreasures(this);
		}


		mapView.getMapAsync(new OnMapReadyCallback() {
			FrameLayout frameLayout = findViewById(R.id.layout_map_frame);
			@Override
			public void onMapReady(MapboxMap mapboxMap) {
				MapActivity.this.mapboxMap = mapboxMap;
			}
		});


		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = LoginActivity.prepare(MapActivity.this, model);
				finish();
				startActivity(i);
			}
		});

		user.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = UserInfoActivity.prepare(MapActivity.this, model);
				finish();
				startActivity(i);
			}
		});

		treasure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = TreasureInfoActivity.prepare(MapActivity.this, model);
				finish();
				startActivity(i);
			}
		});

		map.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = LoginActivity.prepare(MapActivity.this, model);
				finish();
				startActivity(i);
			}
		});

		bury.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = BuryActivity.prepare(MapActivity.this, model);
				finish();
				startActivity(i);
			}
		});
	}

	@Override
	public void onMapReady(@NonNull final MapboxMap mapboxMap) {
		MapActivity.this.mapboxMap = mapboxMap;

		mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/cjerxnqt3cgvp2rmyuxbeqme7"),
				new Style.OnStyleLoaded() {
					@Override
					public void onStyleLoaded(@NonNull Style style) {
						enableLocationComponent(style);
					}
				});
	}

	@SuppressWarnings( {"MissingPermission"})
	private void enableLocationComponent(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
		if (PermissionsManager.areLocationPermissionsGranted(this)) {

// Get an instance of the component
			LocationComponent locationComponent = mapboxMap.getLocationComponent();

// Activate with options
			locationComponent.activateLocationComponent(
					LocationComponentActivationOptions.builder(this, loadedMapStyle).build());

// Enable to make component visible
			locationComponent.setLocationComponentEnabled(true);

// Set the component's camera mode
			locationComponent.setCameraMode(CameraMode.TRACKING);

// Set the component's render mode
			locationComponent.setRenderMode(RenderMode.COMPASS);
		} else {
			permissionsManager = new PermissionsManager(this);
			permissionsManager.requestLocationPermissions(this);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	@Override
	public void onExplanationNeeded(List<String> permissionsToExplain) {
		Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onPermissionResult(boolean granted) {
		if (granted) {
			mapboxMap.getStyle(new Style.OnStyleLoaded() {
				@Override
				public void onStyleLoaded(@NonNull Style style) {
					enableLocationComponent(style);
				}
			});
		} else {
			Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
			finish();
		}
	}

	@Override
	public void postPins(Response<List<TreasurePin>> outcome) {
		if (outcome.code == Result.SUCCESS && outcome.body != null) {
			displayPins(outcome.body);
		}
	}

	private void displayPins(List<TreasurePin> pins) {
		LinearLayout linkLayout = findViewById(R.id.layout_map_container);
		for (TreasurePin p : pins) {
			Button next = new Button(this);
			String text = "Treasure[" + Integer.toString(p.treasureId) + "]: " + Integer.toString(p.status);
			next.setText(text);
			linkLayout.addView(next);
		}
	}


	// prepare(..) static method provides a mechanism for other activities to
	//	create an intent which can start up this activity.  This involves parcelling
	//	the game interface into the bundle.  Similar methods exist for all Park-
	//	Pirates activities, as they generally all require a valid instance of
	//	the game state.
	public static Intent prepare(Context c, GameInterface game) {
		Intent i = new Intent(c, MapActivity.class);
		i.putExtra(c.getString(R.string.intent_key_game_interface), game);
		return i;
	}
}
