package hi.parkpirates.android.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
	DummySource{..} provides a 'dummy' implementation of the RemoteData
	 interface.  This is intended to be used during testing, and while
	 the RESTful interface of the server has not yet been fully defined.
	 This class will _not_ take part in the final product.
 */
public class DummySource implements RemoteData {
	// Here we have some static, stand-in data to be returned by
	//	the dummy's methods.
	private User usr = null;
	private Treasure[] trs = new Treasure[3];
	private TreasurePin[] pin = new TreasurePin[3];

	@Override
	public Result createUser(String name, String email, String passPlain) {
		return Result.FAIL_DEFAULT;
	}

	@Override
	public Result modScore(int userId, int delta) {
		return Result.FAIL_DEFAULT;
	}

	@Override
	public Result deleteUser() {
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
	public User getUser() {
		return usr;
	}

	@Override
	public List<TreasurePin> getPins() {
		return new ArrayList<>(Arrays.asList(pin));
	}

	@Override
	public Treasure getTreasure(int treasureId) {
		for (Treasure t : trs) {
			if (t.index == treasureId) {
				return t;
			}
		}
		return null;
	}

	@Override
	public Result makeClaim(Claim c) {
		return Result.FAIL_DEFAULT;
	}

	@Override
	public Result abandonClaim(Claim c) {
		return Result.FAIL_DEFAULT;
	}

	@Override
	public Result completeClaim(Claim c) {
		return Result.FAIL_DEFAULT;
	}

	@Override
	public Result createTreasure(Treasure trs) {
		return Result.FAIL_DEFAULT;
	}

	@Override
	public Result deleteTreasure(int treasureId) {
		return Result.FAIL_DEFAULT;
	}
}
