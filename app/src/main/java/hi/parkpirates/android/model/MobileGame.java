package hi.parkpirates.android.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import hi.parkpirates.android.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: (dff 18/03/2020) Short description.
public class MobileGame implements GameInterface, Parcelable {
	private List<Cached<Treasure>> trsCache;
	private List<Cached<TreasurePin>> pinCache;
	private Cached<User> usrCache;
	private RemoteData server;

	// NOTE: This constructor is temporary, should not be used.
	// TODO: (dff 19/03/2020) Remove.
	public MobileGame() {
		this.trsCache = new ArrayList<>();
		this.pinCache = new ArrayList<>();
		this.usrCache = null;
		this.server = null;
	}

	// This constructor is intended to be used by activities that are
	//	in a cold-start -- there is no existing Parcel from which to construct
	//	the MobileGame instance.  This method will regenerate the game
	//	state from the cache files if they exist, or as a blank slate
	//	if they do not.
	public MobileGame(Context context)
			throws IOException
	{
		// TODO: (dff 19/03/2020) Strip debug logging.
		SharedPreferences pref = context.getSharedPreferences(
				context.getString(R.string.file_preferences_key), Context.MODE_PRIVATE);

		// Load cached data if it exists, otherwise create and prep the cache files.
		File pinCacheFile = new File(context.getCacheDir(),
				context.getString(R.string.file_cache_pins));
		if (pinCacheFile.exists()) {
			loadPinsFromCache(pinCacheFile);
			System.out.println("Loaded pin cache.");
		} else {
			pinCacheFile.createNewFile();
			System.out.println("Pin cache not found -- creating.");
		}

		File trsCacheFile = new File(context.getCacheDir(),
				context.getString(R.string.file_cache_treasures));
		if (trsCacheFile.exists()) {
			loadTreasuresFromCache(trsCacheFile);
			System.out.println("Loaded treasure cache.");
		} else {
			trsCacheFile.createNewFile();
			System.out.println("Treasure cache not found -- creating.");
		}

		// TODO: (dff 19/03/2020) Check shared prefs for existing credentials.
	}

	private void loadPinsFromCache(File cache) {
		// TODO: (dff 19/03/2020) Implement.
	}

	private void loadTreasuresFromCache(File cache) {
		// TODO: (dff 19/03/2020) Implement.
	}

	// **** Parcelable ****
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO: (dff 19/03/2020) Verify whether the following array mistreatment works as expected.
		dest.writeParcelableArray(trsCache.toArray(new Cached[0]), 0);
		dest.writeParcelableArray(pinCache.toArray(new Cached[0]), 0);
		dest.writeParcelable(usrCache, 0);

		// TODO: (dff 19/03/2020) Does RemoteData need to be parcelled?
	}

	private MobileGame(Parcel src) {
		// TODO: (dff 19/03/2020) Verify that this nasty code works as it should.
		this.trsCache = new ArrayList<>(Arrays.asList((Cached<Treasure>[])src.readParcelableArray(Cached.class.getClassLoader())));
		this.pinCache = new ArrayList<>(Arrays.asList((Cached<TreasurePin>[])src.readParcelableArray(Cached.class.getClassLoader())));
		this.usrCache = src.readParcelable(Cached.class.getClassLoader());
		this.server = null;
		
		// TODO: (dff 19/03/2020) Unify user & treasure claim reference.
	}

	public static final Parcelable.Creator<MobileGame> CREATOR =
			new Parcelable.Creator<MobileGame>() {
				@Override
				public MobileGame createFromParcel(Parcel source) {
					return new MobileGame(source);
				}

				@Override
				public MobileGame[] newArray(int size) {
					return new MobileGame[size];
				}
			};

	// **** END Parcelable ****

	@Override
	public Result registerUser(String name, String email, String passPlain) {
		return Result.FAIL_DEFAULT;
	}

	@Override
	public Result logIn(String name, String passPlain) {
		return Result.FAIL_DEFAULT;
	}

	@Override
	public Result logOut() {
		return Result.FAIL_DEFAULT;
	}

	@Override
	public Result deleteUser() {
		return Result.FAIL_DEFAULT;
	}

	@Override
	public List<TreasurePin> getActiveTreasures() {
		return null;
	}

	@Override
	public List<Treasure> getUserTreasures() {
		return null;
	}

	@Override
	public Treasure getSpecificTreasure(int index) {
		return null;
	}

	@Override
	public Result buryTreasure(Treasure newTrs) {
		return Result.FAIL_DEFAULT;
	}

	@Override
	public Result claimTreasure(int index) {
		return Result.FAIL_DEFAULT;
	}

	@Override
	public Result submitCode(String code) {
		return Result.FAIL_DEFAULT;
	}

	@Override
	public Result abandonClaim() {
		return Result.FAIL_DEFAULT;
	}
}
