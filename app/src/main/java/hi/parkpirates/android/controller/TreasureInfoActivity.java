package hi.parkpirates.android.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import hi.parkpirates.android.R;
import hi.parkpirates.android.model.GameInterface;
import hi.parkpirates.android.model.Treasure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TreasureInfoActivity extends AppCompatActivity {
	private GameInterface model = null;
	private Button user, treasure, map, bury, logout;
	private ListView leftList;
	private ListView rightList;

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
		this.displayListview();

		user = (Button)findViewById(R.id.layout_treasure_button_userInfo);
		treasure = (Button)findViewById(R.id.layout_treasure_button_treasureInfo);
		map = (Button)findViewById(R.id.layout_treasure_button_map);
		bury = (Button)findViewById(R.id.layout_treasure_button_bury);
		logout = (Button)findViewById(R.id.layout_treasure_button_login);

		//leftList = (ListView)findViewById(R.id.treasure_listview_left);
		//rightList = (ListView)findViewById(R.id.treasure_listview_right);

		model = getIntent().getParcelableExtra(getString(R.string.intent_key_game_interface));

		if (model == null) {
			System.err.println("TREASURE_INFO: Error -- failed to receive game interface.");
		}

		// onckilcklistener for logout, it leads to login page
		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = LoginActivity.prepare(TreasureInfoActivity.this, model);
				startActivity(i);
				finish();
			}
		});

		// onckilcklistener for bury
		bury.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = BuryActivity.prepare(TreasureInfoActivity.this, model);
				startActivity(i);
				finish();
			}
		});

		// onckilcklistener for map
		map.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = MapActivity.prepare(TreasureInfoActivity.this, model);
				startActivity(i);
				finish();
			}
		});

		// onckilcklistener for treasure info
		treasure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = TreasureInfoActivity.prepare(TreasureInfoActivity.this, model);
				startActivity(i);
				finish();
			}
		});

		// onckilcklistener for user info
		user.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = UserInfoActivity.prepare(TreasureInfoActivity.this, model);
				startActivity(i);
				finish();
			}
		});
	}

	private void displayListview() {
		String[] titleL = {"Blue dimond","Green dimond", "Red dimond", "Purple dimond", "Gold", "Blue on gold"};
		String[] titleR = {"Green on gold", "Red on gold", "Purple on gold", "Three dimonds", "Four dimonds", "Dimonds on gold"};
		//Integer[] image = {R.drawable.blue_dimond, R.drawable.green_dimond, R.drawable.red_dimond, R.drawable.purple_dimond, R.drawable.gold, R.drawable.gold_blue_dimond};
		ArrayList<Map<String,Object>> treasureL = new ArrayList<Map<String,Object>>();
		ArrayList<Map<String,Object>> treasureR = new ArrayList<Map<String,Object>>();
		int titlelen = titleL.length;
		for(int i = 0; i < titlelen; i++) {
			Map<String,Object> listMapL = new HashMap<String,Object>();
			Map<String,Object> listMapR = new HashMap<String,Object>();
			listMapL.put("title", titleL[i]);
		//	listMapL.put("image", image[i]);
			listMapR.put("title", titleR[i]);
			treasureL.add(listMapL);
			treasureR.add(listMapR);
		}

		SimpleAdapter simpleAdapterL = new SimpleAdapter(this, treasureL, android.R.layout.simple_list_item_2,
				new String[]{"title"}, new int[]{android.R.id.text1});
		SimpleAdapter simpleAdapterR = new SimpleAdapter(this, treasureR, android.R.layout.simple_list_item_2,
				new String[]{"title"}, new int[]{android.R.id.text1});

		leftList = (ListView)findViewById(R.id.treasure_listview_left);
		rightList = (ListView)findViewById(R.id.treasure_listview_right);
		leftList.setAdapter(simpleAdapterL);
		rightList.setAdapter(simpleAdapterR);
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
