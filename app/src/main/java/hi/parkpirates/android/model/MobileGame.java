package hi.parkpirates.android.model;

import java.util.ArrayList;
import java.util.List;

// TODO: (dff 18/03/2020) Short description.
public class MobileGame implements GameInterface {
	private List<Cached<Treasure>> trsCache;
	private List<Cached<TreasurePin>> pinCache;
	private Cached<User> usrCache;
	private RemoteData server;

	public MobileGame() {
		this.trsCache = new ArrayList<>();
		this.pinCache = new ArrayList<>();
		this.usrCache = null;
		this.server = null;
	}

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
