package hi.parkpirates.android.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import hi.parkpirates.android.R;
import hi.parkpirates.android.model.GameInterface;
import hi.parkpirates.android.model.Response;
import hi.parkpirates.android.model.Result;
import hi.parkpirates.android.model.TreasurePin;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements GameInterface.PinCallback {
	private GameInterface model = null;
	private ArrayList<Button> pinButtons = new ArrayList<>();
	private Button user;
	private Button treasure;
	private Button map;
	private Button bury;
	private Button logout;


	// NOTE: Lifecycle methods overridden here for informational
	//	purposes only at present -- wanted to have a look at how/when
	//	the various methods were being used, and what circumstances
	//	would trigger them.
	@Override
	protected void onStart() {
		super.onStart();
		System.out.println("MAP: OnStart(..)");
	}

	@Override
	protected void onStop() {
		super.onStop();
		System.out.println("MAP: OnStop(..)");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("MAP: OnDestroy(..)");
	}

	@Override
	protected void onPause() {
		super.onPause();
		System.out.println("MAP: OnPause(..)");
	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("MAP: OnResume(..)");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

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
				Intent i = MapActivity.prepare(MapActivity.this, model);
				finish();
				startActivity(i);
			}
		});
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
