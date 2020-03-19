package hi.parkpirates.android.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import hi.parkpirates.android.R;
import hi.parkpirates.android.model.GameInterface;
import hi.parkpirates.android.model.MobileGame;

import java.io.IOException;

// NOTE: MainActivity is the login/splash screen activity.
public class LoginActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// TODO: (dff 19/03/2020) Strip debug logging.
		System.out.println("Message 2?");

		try {
			GameInterface g = new MobileGame(this);
		} catch (IOException e) {
			System.out.println("IOException from MobileGame(..)");
			System.out.println(e.getMessage());
		}
	}
}
