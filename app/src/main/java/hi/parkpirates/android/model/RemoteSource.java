package hi.parkpirates.android.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/*
	RemoteSource{..} class implements the remote data interface.  This class
	 is used to access the remote Park-Pirates server and interact with it.

	NOTE: Currently rather skeletal.  Awaiting formalization of REST interface
	 on server to fully implement.  Recommend using DummySource{..} instead in
	 the interim.

	Typically the RemoteSource will make contact by targeting a particular
	 URL, with user name and session token as POST parameters, as well as
	 any pertinent input.  The response from the server shall take the form
	 of a JSON structure, which RemoteData will take appropriate action on
	 (usually this will involve parsing the JSON into game data objects,
	 and then firing them back up through the given callback).
 */
// TODO: (dff 22/03/2020) Make RemoteSource parcelable.
public class RemoteSource implements RemoteData {
	private User credential;
	private String host;

	// TESTING CONSTRUCTOR
	// TODO: (dff 21/03/2020) Replace with proper-use constructor.
	public RemoteSource(String host) {
		credential = new User(-1, "Coyote", "none", "n/a", "n/a", -1);
		this.host = host;
	}

	// buildAuthParams(..) method constructs a string from the user's
	//	credentials which is suitable to use in a POST request to the
	//	Park-Pirates server.
	private String buildAuthParams() {
		return "user=" + credential.name + "&auth=" + credential.token;
	}

	// TODO: (dff 21/03/2020) Remove DBG_ methods.
	public void DBG_testFetch(String param) {
		// TODO: (dff 21/03/2020) Investigate the static field leak possibility.
		@SuppressLint("StaticFieldLeak") final FetchTask task = new FetchTask() {
			@Override
			protected void onPostExecute(String s) {
				System.out.println("Fetch result: " + s);
			}
		};

		String target = host + "debug/";
		String body = buildAuthParams() + "&aux=" + param;
		task.execute(target, body);
	}

	// Fetch task requires TWO String parameters:
	//	[0] - target URL, f.ex. "localhost:8080/rest/login/"
	//	[1] - POST parameters condensed into a single string.
	// Fetch task RETURNS a JSON string (may be null?).

	// NOTE: Where the FetchTask is used, an anonymous version should be
	//	created which overrides the onPostExecute(..) method, such that
	//	something may be done with the results.
	private static class FetchTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onCancelled() {
			// Thread has been cancelled -- do nothing with the result of
			//	the remote query.
		}

		@Override
		protected String doInBackground(String... args) {
			String target = args[0];
			String params = args[1];

			byte[] parBytes = params.getBytes(StandardCharsets.UTF_8);
			int parLength = parBytes.length;

			URL url = null;
			HttpURLConnection conn = null;
			String result = null;

			try {
				// TODO: (dff 21/03/2020) Swap to https when we move off LOCAL to REMOTE.
				url = new URL(target);
				conn = (HttpURLConnection)url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("charset", "utf-8");
				conn.setRequestProperty("Content-Length", Integer.toString(parLength));
				conn.setUseCaches(false);
				// TODO: (dff 21/03/2020) Find a good timeout value, catch SocketException explicitly
				conn.setConnectTimeout(3000);

				try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
					out.write(parBytes);
				}

				try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
					StringBuilder stash = new StringBuilder();
					String line = "";
					do {
						stash.append(line);
						line = in.readLine();
					} while (line != null);
					result = stash.toString();
				}
			} catch (MalformedURLException e) {
				System.err.println("MalformedURLException with '" + target + "'");
			} catch (IOException e) {
				System.err.println("IOException in fetch(..)");
				System.err.println(e.getMessage());
			} finally {
				if (conn != null) {
					conn.disconnect();
				}
			}

			return result;
		}
	}

	@Override
	public void cancelPending() {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void logIn(String name, String passPlain, Callbacks cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void verify(String name, String token, Callbacks cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void register(String name, String email, String passPlain, Callbacks cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void getPins(Callbacks cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void logOut(Callbacks cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void delete(Callbacks cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void getUserTreasures(Callbacks cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void removeTreasure(int treasureId, Callbacks cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void getSpecificTreasure(int treasureId, Callbacks cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void claim(int treasureId, Callbacks cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void abandon(Callbacks cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void capture(Callbacks cb) {
		// TODO: (dff 22/03/2020) Implement.
	}

	@Override
	public void bury(Treasure treasure, Callbacks cb) {
		// TODO: (dff 22/03/2020) Implement.
	}
}
