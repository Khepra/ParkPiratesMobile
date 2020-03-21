package hi.parkpirates.android.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import hi.parkpirates.android.R;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
	MobileGame{..} class implements the GameInterface -- this class is the
	 heart of the Model; it maintains game data objects over their lifetimes,
	 caching them as necessary and pulling fresh versions from the remote
	 source where appropriate.

	This class defines the interface through which the activities associated with
	 this project shall interact with the game systems.
 */
public class MobileGame implements GameInterface {
	private ArrayList<Cached<Treasure>> trsCache;
	private ArrayList<Cached<TreasurePin>> pinCache;
	private Cached<User> usrCache;
	private RemoteData server;
	private Context context;

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
	public MobileGame(Context con)
			throws IOException, ClassNotFoundException
	{
		this.context = con;
		// TODO: (dff 19/03/2020) Strip debug logging.
		SharedPreferences pref = context.getSharedPreferences(
				context.getString(R.string.file_preferences_key), Context.MODE_PRIVATE);

		// Load cached data if it exists, otherwise create and prep the cache files.
		File pinCacheFile = new File(context.getCacheDir(),
				context.getString(R.string.file_cache_pins));
		if (pinCacheFile.exists()) {
			FileInputStream in = new FileInputStream(pinCacheFile);
			loadPinCache(in);
			in.close();
			System.out.println("Loaded pin cache.");
		} else {
			pinCacheFile.createNewFile();
			pinCache = new ArrayList<>();
			FileOutputStream out = new FileOutputStream(pinCacheFile);
			savePinCache(out);
			out.close();
			System.out.println("Pin cache not found -- creating.");
		}

		File trsCacheFile = new File(context.getCacheDir(),
				context.getString(R.string.file_cache_treasures));
		if (trsCacheFile.exists()) {
			FileInputStream in = new FileInputStream(trsCacheFile);
			loadTreasureCache(in);
			in.close();
			System.out.println("Loaded treasure cache.");
		} else {
			trsCacheFile.createNewFile();
			trsCache = new ArrayList<>();
			FileOutputStream out = new FileOutputStream(trsCacheFile);
			saveTreasureCache(out);
			out.close();
			System.out.println("Treasure cache not found -- creating.");
		}

		// TODO: (dff 19/03/2020) Check shared prefs for existing credentials.
	}

	// setContext(..) method provides a means to set the context associated
	//	with this MobileGame for instances which were created from a Parcel.
	public void setContext(Context con) {
		this.context = con;
	}

	// savePinCache(..) method serializes the list of cached TreasurePins to
	//	the specified output stream.
	private void savePinCache(OutputStream cache)
			throws IOException
	{
		ObjectOutputStream dest = new ObjectOutputStream(cache);
		dest.writeObject(pinCache);
		dest.close();
	}

	// loadPinCache(..) method loads a list of cached TreasurePins from the
	//	specified input stream.
	private void loadPinCache(InputStream cache)
			throws IOException, ClassNotFoundException
	{
		ObjectInputStream src = new ObjectInputStream(cache);
		pinCache = (ArrayList)src.readObject();
		src.close();
	}

	// saveTreasureCache(..) method serializes the list of cached Treasures to
	//	the specified output stream.
	private void saveTreasureCache(OutputStream cache)
			throws IOException
	{
		ObjectOutputStream dest = new ObjectOutputStream(cache);
		dest.writeObject(trsCache);
		dest.close();
	}

	// loadTreasureCache(..) method loads a list of cached Treasures from the
	//	specified input stream.
	private void loadTreasureCache(InputStream cache)
			throws IOException, ClassNotFoundException
	{
		ObjectInputStream src = new ObjectInputStream(cache);
		trsCache = (ArrayList)src.readObject();
		src.close();
	}

	// TODO: (dff 20/03/2020) Remove DBG_ methods eventually.
	public void DBG_makePins()
			throws IOException
	{
		if (pinCache.isEmpty()) {
			TreasurePin tp = new TreasurePin(2, GpsLocation.sample(), -1);
			Cached<TreasurePin> c = new Cached<>(tp);
			pinCache.add(c);
			tp = new TreasurePin(3, GpsLocation.sample(), -10);
			c = new Cached<>(tp);
			pinCache.add(c);
			tp = new TreasurePin(4, GpsLocation.sample(), -100);
			c = new Cached<>(tp);
			pinCache.add(c);

			File pinCacheFile = new File(context.getCacheDir(),
					context.getString(R.string.file_cache_pins));
			FileOutputStream out = new FileOutputStream(pinCacheFile);
			savePinCache(out);
			out.close();
		}
	}

	public void DBG_testRemote() {
		RemoteSource src = new RemoteSource(context.getString(R.string.url_base_local));
		src.DBG_testFetch("deadbeef");
	}

	public void DBG_printCache() {
		for (Cached<TreasurePin> c : pinCache) {
			System.out.println(c);
		}

		for (Cached<Treasure> c : trsCache) {
			System.out.println(c);
		}

		if (usrCache != null) {
			System.out.println(usrCache);
		}
	}

	// **** Parcelable ****
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO: (dff 19/03/2020) Verify whether the following array mistreatment works as expected.
		if (!trsCache.isEmpty()) {
			dest.writeInt(1);
			dest.writeParcelableArray(trsCache.toArray(new Cached[0]), 0);
		} else {
			dest.writeInt(0);
		}
		if (!pinCache.isEmpty()) {
			dest.writeInt(1);
			dest.writeParcelableArray(pinCache.toArray(new Cached[0]), 0);
		} else {
			dest.writeInt(0);
		}
		if (usrCache != null) {
			dest.writeInt(1);
			dest.writeParcelable(usrCache, 0);
		} else {
			dest.writeInt(0);
		}

		// TODO: (dff 19/03/2020) Does RemoteData need to be parcelled?
	}

	private MobileGame(Parcel src) {
		this.trsCache = new ArrayList<>();
		this.pinCache = new ArrayList<>();
		this.usrCache = null;
		this.server = null;

		if (src.readInt() > 0) {
			Parcelable[] arr = src.readParcelableArray(Cached.class.getClassLoader());
			for (Parcelable p : arr) {
				Cached<Treasure> c = (Cached<Treasure>)p;
				trsCache.add(c);
			}
		}

		if (src.readInt() > 0) {
			Parcelable[] arr = src.readParcelableArray(Cached.class.getClassLoader());
			for (Parcelable p : arr) {
				Cached<TreasurePin> c = (Cached<TreasurePin>)p;
				pinCache.add(c);
			}
		}

		if (src.readInt() > 0) {
			this.usrCache = src.readParcelable(Cached.class.getClassLoader());
		}
		
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
		// NOTE: This is a placeholder implementation, added for now
		//	to test out dynamic generation of view elements.  Will be
		//	overhauled in the future.
		// TODO: Check cached data age, refresh as necessary.
		ArrayList<TreasurePin> out = new ArrayList<>();
		for (Cached<TreasurePin> c : pinCache) {
			out.add(c.data());
		}
		return out;
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
