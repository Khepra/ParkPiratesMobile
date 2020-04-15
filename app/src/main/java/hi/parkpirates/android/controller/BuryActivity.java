package hi.parkpirates.android.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import hi.parkpirates.android.R;
import hi.parkpirates.android.model.GameInterface;

public class BuryActivity extends AppCompatActivity {
	private GameInterface model = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bury);


		findViewById(R.id.layout_bury_button_login).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = LoginActivity.prepare(BuryActivity.this, model);
				startActivity(i);
				finish();
			}
		});

		findViewById(R.id.layout_bury_button_bury).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = BuryActivity.prepare(BuryActivity.this, model);
				startActivity(i);
				finish();
			}
		});

		findViewById(R.id.layout_bury_button_map).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = MapActivity.prepare(BuryActivity.this, model);
				startActivity(i);
				finish();
			}
		});

		findViewById(R.id.layout_bury_button_treasureInfo).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = TreasureInfoActivity.prepare(BuryActivity.this, model);
				startActivity(i);
				finish();
			}
		});

		findViewById(R.id.layout_bury_button_userInfo).setOnClickListener(new View.OnClickListener() {
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
