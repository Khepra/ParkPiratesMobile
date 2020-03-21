package hi.parkpirates.android.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import hi.parkpirates.android.R;
import hi.parkpirates.android.model.GameInterface;
import hi.parkpirates.android.model.MobileGame;

import java.io.IOException;

// NOTE: LoginActivity is the login/splash screen activity.
public class LoginActivity extends AppCompatActivity {
	private GameInterface model = null;

	// NOTE: Lifecycle methods overridden here for informational
	//	purposes only at present -- wanted to have a look at how/when
	//	the various methods were being used, and what circumstances
	//	would trigger them.
	@Override
	protected void onStart() {
		super.onStart();
		System.out.println("LOGIN: OnStart(..)");
	}

	@Override
	protected void onStop() {
		super.onStop();
		System.out.println("LOGIN: OnStop(..)");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("LOGIN: OnDestroy(..)");
	}

	@Override
	protected void onPause() {
		super.onPause();
		System.out.println("LOGIN: OnPause(..)");
	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("LOGIN: OnResume(..)");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// TODO: (dff 19/03/2020) Strip debug logging.
		System.out.println("LOGIN: OnCreate(..)");

		// Check if a GameInterface was provided in the intent bundle.
		model = getIntent().getParcelableExtra(getString(R.string.intent_key_game_interface));

		if (model == null) {
			System.out.println("LOGIN: No game interface found with intent.");
			try {
				model = new MobileGame(this);

				// TODO: (dff 20/03/2020) Remove debug.
				((MobileGame)model).DBG_makePins();
			} catch (IOException e) {
				System.out.println("LOGIN: IOException from MobileGame(..)");
				System.out.println(e.getMessage());
			} catch (ClassNotFoundException e) {
				System.out.println("LOGIN: ClassNotFoundException from MobileGame(..)");
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("LOGIN: Unpacked game interface.");
			((MobileGame)model).setContext(this);
			// TODO: (dff 20/03/2020) Remove debug.
			((MobileGame)model).DBG_printCache();
			((MobileGame)model).DBG_testRemote();
		}

		// NOTE: Temporary action assignment, temporary buttons.
		findViewById(R.id.move_userInfo).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = UserInfoActivity.prepare(LoginActivity.this, model);
				finish();
				startActivity(i);
			}
		});

		findViewById(R.id.move_map).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = MapActivity.prepare(LoginActivity.this, model);
				finish();
				startActivity(i);
			}
		});
	}

	// prepare(..) static method provides a mechanism for other activities to
	//	create an intent which can start up this activity.  This involves parcelling
	//	the game interface into the bundle.  Similar methods exist for all Park-
	//	Pirates activities, as they generally all require a valid instance of
	//	the game state.
	public static Intent prepare(Context c, GameInterface game) {
		Intent i = new Intent(c, LoginActivity.class);
		i.putExtra(c.getString(R.string.intent_key_game_interface), game);
		return i;
	}
}
