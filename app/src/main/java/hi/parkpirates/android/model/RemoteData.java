package hi.parkpirates.android.model;

import java.util.List;

/*
	RemoteData{..} provides a standard interface to the functionality
	 offered by the park pirates server.  This is a loose mapping of the
	 RESTful interface of the server.

	See RemoteSource{..} class for implementation.

	Note that DummySource{..} is also provided so that this interface
	 can be used before the server side interface has been implemented.
 */
public interface RemoteData {
	Result createUser(String name, String email, String passPlain);
	Result modScore(int userId, int delta);
	Result deleteUser();
	Result logIn(String name, String passPlain);
	Result logOut();

	User getUser();
	List<TreasurePin> getPins();
	Treasure getTreasure(int treasureId);

	Result makeClaim(Claim c);
	Result abandonClaim(Claim c);
	Result completeClaim(Claim c);

	Result createTreasure(Treasure trs);
	Result deleteTreasure(int treasureId);

	// NOTE: The RemoteCallbacks interface is a first-pass list of callback
	//	functions, and will be refined and adjusted as it is integrated into the
	//	project.  Trying a few things out as I go with this one, so the list
	//	is a little incoherent right now, but consistency will be enforced on
	//	it in a refactor once I have a better sense of how all the pieces
	//	will ultimately fit together.
	interface RemoteCallbacks {
		// TODO: (dff 21/03/2020) Go over args/returns as I put these into use, adjust.
		void postRegister(boolean result, String msg);
		void postLogIn(boolean result, String msg);
		void postLogOut();

		void postUser(User info);
		void postTreasure(Treasure info);
		void postCollection(List<Treasure> info);
		void postPins(List<TreasurePin> info);

		void postClaim(Result result, Claim info);
		void postAbandon(boolean result);
		void postSubmit(boolean result);

		void postBury(boolean result, String msg);
		void postRemove(boolean result, String msg);
	}
}
