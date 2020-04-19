package hi.parkpirates.android.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import hi.parkpirates.android.R;
import hi.parkpirates.android.model.GameInterface;

public class BuryActivity extends AppCompatActivity {
	private GameInterface model = null;
	private Button user, treasure, map, bury, logout;

	// NOTE: Lifecycle methods overridden here for informational
	//	purposes only at present -- wanted to have a look at how/when
	//	the various methods were being used, and what circumstances
	//	would trigger them.
	@Override
	protected void onStart() {
		super.onStart();
		System.out.println("BURY: OnStart(..)");
	}

	@Override
	protected void onStop() {
		super.onStop();
		System.out.println("BURY: OnStop(..)");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("BURY: OnDestroy(..)");
	}

	@Override
	protected void onPause() {
		super.onPause();
		System.out.println("BURY: OnPause(..)");
	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("BURY: OnResume(..)");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bury);

		user = (Button)findViewById(R.id.layout_bury_button_userInfo);
		treasure = (Button)findViewById(R.id.layout_bury_button_treasureInfo);
		map = (Button)findViewById(R.id.layout_bury_button_map);
		bury = (Button)findViewById(R.id.layout_bury_button_bury);
		logout = (Button)findViewById(R.id.layout_bury_button_login);


		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = LoginActivity.prepare(BuryActivity.this, model);
				startActivity(i);
				finish();
			}
		});

		bury.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = BuryActivity.prepare(BuryActivity.this, model);
				startActivity(i);
				finish();
			}
		});

		map.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = MapActivity.prepare(BuryActivity.this, model);
				startActivity(i);
				finish();
			}
		});

		treasure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = TreasureInfoActivity.prepare(BuryActivity.this, model);
				startActivity(i);
				finish();
			}
		});


		user.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = UserInfoActivity.prepare(BuryActivity.this, model);
				startActivity(i);
				finish();
			}
		});
	}


	// prepare(..) static method provides a mechanism for other activities to
	//	create an intent which can start up this activity.  This involves parcelling
	//	the game interface into the bundle.  Similar methods exist for all Park-
	//	Pirates activities, as they generally all require a valid instance of
	//	the game state.
	public static Intent prepare(Context c, GameInterface game) {
		Intent i = new Intent(c, BuryActivity.class);
		i.putExtra(c.getString(R.string.intent_key_game_interface), game);
		return i;
	}
}
