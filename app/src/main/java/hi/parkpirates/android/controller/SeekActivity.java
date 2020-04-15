package hi.parkpirates.android.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import hi.parkpirates.android.R;

public class SeekActivity extends AppCompatActivity {


	// NOTE: Lifecycle methods overridden here for informational
	//	purposes only at present -- wanted to have a look at how/when
	//	the various methods were being used, and what circumstances
	//	would trigger them.
	@Override
	protected void onStart() {
		super.onStart();
		System.out.println("SEEK: OnStart(..)");
	}

	@Override
	protected void onStop() {
		super.onStop();
		System.out.println("SEEK: OnStop(..)");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("SEEK: OnDestroy(..)");
	}

	@Override
	protected void onPause() {
		super.onPause();
		System.out.println("SEEK: OnPause(..)");
	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("SEEK: OnResume(..)");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seek);
	}
}
