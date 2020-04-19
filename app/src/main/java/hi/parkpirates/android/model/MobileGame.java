package hi.parkpirates.android.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import hi.parkpirates.android.R;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
	MobileGame{..} class implements the GameInterface -- this class is the
	 heart of the Model; it maintains game data objects over their lifetimes,
	 caching them as necessary and pulling fresh versions from the remote
	 source where appropriate.

	This class defines the interface through which the activities associated with
	 this project shall interact with the game systems.
 */
public class MobileGame implements GameInterface, RemoteData.Callbacks {
	private ArrayList<Cached<Treasure>> trsCache;
	private ArrayList<Cached<TreasurePin>> pinCache;
	private Cached<User> usrCache;
	private RemoteData server;
	private Context context;

	// We need to track some of the callback objects passed into the methods
	//	of this class.
	private PinCallback pendingPinCallback = null;

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

		// TODO: (dff 22/03/2020) Replace DummySource with RemoteSource.
		this.server = new DummySource();
	}

	// setContext(..) method provides a means to set the context associated
	//	with this MobileGame for instances which were created from a Parcel.
	@Override
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

	// **** GameInterface ****

	@Override
	public void logIn(String name, String passPlain, LogInCallback cb) {
		// TODO: (dff 22/03/2020) Implement.
		// gsa: Was trying to figure out how to implement the function.
		// It is just a raw implement and not tested.
		if(name.isEmpty() || passPlain.isEmpty()) {
			cb.postLogIn(new Response(Result.FAIL_INVALID_INPUT));
		} else {
			cb.postLogIn(new Response(Result.SUCCESS));

		}
	}

	@Override
	public void verify(String name, String token, VerifyCallback cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void register(String name, String email, String passPlain, RegisterCallback cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void getActiveTreasures(PinCallback cb) {
		// TODO: (dff 22/03/2020) Replace proof-of-concept implementation.
		ArrayList<TreasurePin> out = new ArrayList<>();

		// Check whether our cached pin data is all fresh.  Note that if one
		//	or more pins are stale, then we will refresh the entire list.
		boolean fresh = true;
		for (Cached<TreasurePin> c : pinCache) {
			// TODO: (dff 22/03/2020) Replace '5' with static constant.
			if (c.expired(5)) {
				fresh = false;
				break;
			} else {
				out.add(c.data());
			}
		}

		if (fresh) {
			// Cached pins are all valid, send them back!
			cb.postPins(new Response<List<TreasurePin>>(Result.SUCCESS, out));
		} else {
			// Otherwise get a fresh set from the remote host.
			pendingPinCallback = cb;
			server.getPins(this);
		}
	}

	@Override
	public void logOut(LogOutCallback cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void delete(DeleteCallback cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void getUserTreasures(UserTreasureCallback cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void remove(int treasureId, RemoveCallback cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void getSpecificTreasure(int treasureId, TreasureCallback cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void claim(int treasureId, ClaimCallback cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void abandon(AbandonCallback cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void submit(String code, SubmitCallback cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void bury(Treasure treasure, BuryCallback cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	// **** END GameInterface ****

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
			// NOTE: pin segment works as expected.
		} else {
			dest.writeInt(0);
		}
		if (usrCache != null) {
			dest.writeInt(1);
			dest.writeParcelable(usrCache, 0);
		} else {
			dest.writeInt(0);
		}

		// TODO: (dff 22/03/2020) Does RemoteData need to be parcelled?
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

		// TODO: (dff 22/03/2020) Replace DummySource with RemoteSource, un-parcel
		//  RemoteSource instead of creating fresh.
		this.server = new DummySource();
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

	// **** RemoteData.Callbacks interface ****

	@Override
	public void updateLogIn(Response<User> outcome) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void updateVerify(Response<User> outcome) {
		// TODO: (dff 22/03/2020) Implement.
		// (gsa) tried to implement this function by using implement from (dff) in updatePins,
		// it is not guaranteed that it works, has not been tested.
		if (outcome.code == Result.SUCCESS && outcome.body != null) {
			// Update the local cache file.
			File usrCacheFile = new File(context.getCacheDir(),
					context.getString(R.string.file_cache_users));
			try {
				FileOutputStream out = new FileOutputStream(usrCacheFile);
				savePinCache(out);
				out.close();
			} catch (IOException e) {
				System.err.println("Error: Failed to save user cache in MobileGame::updateLogin(..).");
				System.err.println(e.getMessage());
			}
		}

	}

	@Override
	public void updateRegister(Response<Void> outcome) {
		// TODO: (dff 22/03/2020) Implement.

	}

	@Override
	public void updatePins(Response<List<TreasurePin>> outcome) {
		if (outcome.code == Result.SUCCESS && outcome.body != null) {
			// Generate a new set of Cached<?> wrappings.
			pinCache.clear();
			for (TreasurePin p : outcome.body) {
				pinCache.add(new Cached<TreasurePin>(p));
			}

			// Update the local cache file.
			File pinCacheFile = new File(context.getCacheDir(),
					context.getString(R.string.file_cache_pins));
			try {
				FileOutputStream out = new FileOutputStream(pinCacheFile);
				savePinCache(out);
				out.close();
			} catch (IOException e) {
				System.err.println("Error: Failed to save pin cache in MobileGame::updatePins(..).");
				System.err.println(e.getMessage());
			}

			// If a pending callback exists, trigger it and reset.
			if (pendingPinCallback != null) {
				pendingPinCallback.postPins(outcome);
				pendingPinCallback = null;
			}
		} else {
			// TODO: (dff 22/03/2020) Determine policy on what to do when requests time out.
			System.err.println("Failure in MobileGame::updatePins(..).");
		}
	}

	@Override
	public void updateLogOut(Response<Void> outcome) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void updateDelete(Response<Void> outcome) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void updateUserTreasures(Response<List<Treasure>> outcome) {
		// TODO: (dff 22/03/2020) Implement.
		// (gsa) tried to implement this function by using implement from (dff) in updatePins,
		// it is not guaranteed that it works, has not been tested.
		if (outcome.code == Result.SUCCESS && outcome.body != null) {
			// Generate a new set of Cached<?> wrappings.
			trsCache.clear();
			for (Treasure t : outcome.body) {
				trsCache.add(new Cached<Treasure>(t));
			}

			// Update the local cache file.
			File trsCacheFile = new File(context.getCacheDir(),
					context.getString(R.string.file_cache_treasures));
			try {
				FileOutputStream out = new FileOutputStream(trsCacheFile);
				savePinCache(out);
				out.close();
			} catch (IOException e) {
				System.err.println("Error: Failed to save pin cache in MobileGame::updateUserTreasure(..).");
				System.err.println(e.getMessage());
			}
		}
	}

	@Override
	public void updateRemove(Response<Void> outcome) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void updateSpecificTreasure(Response<Treasure> outcome) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void updateClaim(Response<RemoteData.Update> outcome) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void updateAbandon(Response<RemoteData.Update> outcome) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void updateCapture(Response<RemoteData.Update> outcome) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void updateBury(Response<Treasure> outcome) {
		// TODO: (dff 22/03/2020) Implement.
	}

	// **** END RemoteData.Callbacks interface ****
}
