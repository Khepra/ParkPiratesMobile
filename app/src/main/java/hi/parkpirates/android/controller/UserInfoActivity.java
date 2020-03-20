package hi.parkpirates.android.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import hi.parkpirates.android.R;
import hi.parkpirates.android.model.GameInterface;

public class UserInfoActivity extends AppCompatActivity {
	private GameInterface model = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);

		// Observe that this method should not cold-start, as it is not
		//	a launchpad activity.  Thus we require that it receive a valid
		//	GameInterface parcel when created.
		model = getIntent().getParcelableExtra(getString(R.string.intent_key_game_interface));
		if (model == null) {
			System.out.println("USER_INFO: No game interface found with intent.");
		} else {
			System.out.println("USER_INFO: Unpacked game interface.");
		}

		// NOTE: Sloppy business below.
		findViewById(R.id.move_login).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = LoginActivity.prepare(UserInfoActivity.this, model);
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
