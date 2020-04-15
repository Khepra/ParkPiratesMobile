package hi.parkpirates.android.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import hi.parkpirates.android.R;
import hi.parkpirates.android.model.GameInterface;

public class TreasureInfoActivity extends AppCompatActivity {
	private GameInterface model = null;
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
		System.out.println("TREASURE_INFO: OnStart(..)");
	}

	@Override
	protected void onStop() {
		super.onStop();
		System.out.println("TREASURE_INFO: OnStop(..)");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("TREASURE_INFO: OnDestroy(..)");
	}

	@Override
	protected void onPause() {
		super.onPause();
		System.out.println("TREASURE_INFO: OnPause(..)");
	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("TREASURE_INFO: OnResume(..)");
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_treasure_info);

		user = (Button)findViewById(R.id.layout_treasure_button_userInfo);
		treasure = (Button)findViewById(R.id.layout_treasure_button_treasureInfo);
		map = (Button)findViewById(R.id.layout_treasure_button_map);
		bury = (Button)findViewById(R.id.layout_treasure_button_bury);
		logout = (Button)findViewById(R.id.layout_treasure_button_login);


		model = getIntent().getParcelableExtra(getString(R.string.intent_key_game_interface));
		if (model == null) {
			System.err.println("TREASURE_INFO: Error -- failed to receive game interface.");
		}


		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = LoginActivity.prepare(TreasureInfoActivity.this, model);
				startActivity(i);
				finish();
			}
		});

		bury.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = BuryActivity.prepare(TreasureInfoActivity.this, model);
				startActivity(i);
				finish();
			}
		});

		map.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = MapActivity.prepare(TreasureInfoActivity.this, model);
				startActivity(i);
				finish();
			}
		});

		treasure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = TreasureInfoActivity.prepare(TreasureInfoActivity.this, model);
				startActivity(i);
				finish();
			}
		});

		user.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = UserInfoActivity.prepare(TreasureInfoActivity.this, model);
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
		Intent i = new Intent(c, TreasureInfoActivity.class);
		i.putExtra(c.getString(R.string.intent_key_game_interface), game);
		return i;
	}
}
