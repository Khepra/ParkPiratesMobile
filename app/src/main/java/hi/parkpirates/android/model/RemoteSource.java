package hi.parkpirates.android.model;

import java.util.List;

public class RemoteSource implements RemoteData {
	private User credential;

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
		return null;
	}

	@Override
	public List<TreasurePin> getPins() {
		return null;
	}

	@Override
	public Treasure getTreasure(int treasureId) {
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
		return null;
	}

	@Override
	public Result deleteTreasure(int treasureId) {
		return null;
	}
}
