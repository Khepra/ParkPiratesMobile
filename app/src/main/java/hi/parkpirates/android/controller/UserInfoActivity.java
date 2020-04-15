package hi.parkpirates.android.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import hi.parkpirates.android.R;
import hi.parkpirates.android.model.GameInterface;

import java.util.ArrayList;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity {
	private GameInterface model = null;
	private ArrayList<Button> treasures = new ArrayList<>();
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
		System.out.println("USER_INFO: OnStart(..)");
	}

	@Override
	protected void onStop() {
		super.onStop();
		System.out.println("USER_INFO: OnStop(..)");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("USER_INFO: OnDestroy(..)");
	}

	@Override
	protected void onPause() {
		super.onPause();
		System.out.println("USER_INFO: OnPause(..)");
	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("USER_INFO: OnResume(..)");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);

		user = (Button)findViewById(R.id.layout_user_button_userInfo);
		treasure = (Button)findViewById(R.id.layout_user_button_treasureInfo);
		map = (Button)findViewById(R.id.layout_user_button_map);
		bury = (Button)findViewById(R.id.layout_user_button_bury);
		logout = (Button)findViewById(R.id.layout_user_button_login);

		// Observe that this method should not cold-start, as it is not
		//	a launchpad activity.  Thus we require that it receive a valid
		//	GameInterface parcel when created.
		model = getIntent().getParcelableExtra(getString(R.string.intent_key_game_interface));
		if (model == null) {
			System.out.println("USER_INFO: No game interface found with intent.");
		} else {
			System.out.println("USER_INFO: Unpacked game interface.");
		}

		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = LoginActivity.prepare(UserInfoActivity.this, model);
				startActivity(i);
				finish();
			}
		});

		bury.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = BuryActivity.prepare(UserInfoActivity.this, model);
				startActivity(i);
				finish();
			}
		});

		map.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = MapActivity.prepare(UserInfoActivity.this, model);
				startActivity(i);
				finish();
			}
		});

		treasure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = TreasureInfoActivity.prepare(UserInfoActivity.this, model);
				startActivity(i);
				finish();
			}
		});

		user.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = UserInfoActivity.prepare(UserInfoActivity.this, model);
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
		Intent i = new Intent(c, UserInfoActivity.class);
		i.putExtra(c.getString(R.string.intent_key_game_interface), game);
		return i;
	}
}
