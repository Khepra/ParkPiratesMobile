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
import hi.parkpirates.android.model.TreasurePin;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {
	private GameInterface model = null;
	private ArrayList<Button> pinButtons = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		model = getIntent().getParcelableExtra(getString(R.string.intent_key_game_interface));
		if (model == null) {
			System.err.println("MAP: Error -- failed to receive game interface.");
		} else {
			// Testing out dynamic generation of view elements.
			LinearLayout linkLayout = findViewById(R.id.layout_map_container);
			for (TreasurePin p : model.getActiveTreasures()) {
				Button next = new Button(this);
				String text = "Treasure[" + Integer.toString(p.treasureId) + "]: " + Integer.toString(p.status);
				next.setText(text);
				linkLayout.addView(next);
			}
		}

		findViewById(R.id.layout_map_button_login).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = LoginActivity.prepare(MapActivity.this, model);
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
		Intent i = new Intent(c, MapActivity.class);
		i.putExtra(c.getString(R.string.intent_key_game_interface), game);
		return i;
	}
}
