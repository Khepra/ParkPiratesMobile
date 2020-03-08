package hi.parkpirates.android;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void startGame(View v) {
        	Log.i("ImageButton", "clicked");
        	Intent intent = new Intent(this, StartGame.class);
        	startActivity(intent);
        	finish();
    }
}
