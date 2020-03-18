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
}
