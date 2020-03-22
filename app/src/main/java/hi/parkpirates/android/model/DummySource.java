package hi.parkpirates.android.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/*
	DummySource{..} provides a 'dummy' implementation of the RemoteData
	 interface.  This is intended to be used during testing, and while
	 the RESTful interface of the server has not yet been fully defined.
	 This class will _not_ take part in the final product.

	Observe that the DummySource does not persist data between sessions
	 currently; it is started in a fixed, near-blank slate mode each
	 time a fresh run of the system is started.
 */
public class DummySource implements RemoteData {
	// Here we have some static, stand-in data to be returned by
	//	the dummy's methods.
	private User usr = null;
	private Treasure[] trs = new Treasure[3];
	private TreasurePin[] pin = new TreasurePin[3];
	private boolean timeout = false;

	// Simple constructor initializes our near-blank slate.
	public DummySource() {
		usr = new User(1, "demo", "none", "pass", "n/a", 0);
		usr.token = "someToken";

		trs[0] = new Treasure(1, "Treasure A", 10, GpsLocation.sample(),
				"codeA", "n/a", false, new Date(),
				false, 1, 0);
		trs[1] = new Treasure(2, "Treasure B", 20, GpsLocation.sample(),
				"codeB", "n/a", false, new Date(),
				false, 0, 0);
		trs[2] = new Treasure(3, "Treasure C", 50, GpsLocation.sample(),
				"codeC", "n/a", false, new Date(),
				false, 0, 0);

		pin[0] = new TreasurePin(1, trs[0].locFinal, 0);
		pin[1] = new TreasurePin(2, trs[1].locFinal, 0);
		pin[2] = new TreasurePin(3, trs[2].locFinal, 0);
	}

	// If we wish to test how the system responds to actions timing out,
	//	then set timeout to true.  This will cause all actions provided
	//	by DummySource to time out.  Note that it is not quite comparable
	//	to a true timeout though, because it happens instantaneously.  This
	//	is provided mostly just to be able to check the code-path for
	//	when actions timeout in a basic but not comprehensive fashion.
	public void setTimeout(boolean t) {
		timeout = t;
	}

	@Override
	public void cancelPending() {
		// Do nothing -- dummy class does not implement asynchronous tasks.
	}

	@Override
	public void logIn(String name, String passPlain, Callbacks cb) {
		if (timeout) {
			cb.updateLogIn(new Response<User>(Result.FAIL_TIMEOUT));
		}

		if (name.equals(usr.name) && passPlain.equals(usr.passHash)) {
			cb.updateLogIn(new Response<User>(Result.SUCCESS, usr));
		} else {
			cb.updateLogIn(new Response<User>(Result.FAIL_INVALID_INPUT));
		}
	}

	@Override
	public void verify(String name, String token, Callbacks cb) {
		if (timeout) {
			cb.updateVerify(new Response<User>(Result.FAIL_TIMEOUT));
		}

		if (name.equals(usr.name) && token.equals(usr.token)) {
			cb.updateVerify(new Response<User>(Result.SUCCESS, usr));
		} else {
			cb.updateVerify(new Response<User>(Result.FAIL_INVALID_INPUT));
		}
	}

	@Override
	public void register(String name, String email, String passPlain, Callbacks cb) {
		if (timeout) {
			cb.updateRegister(new Response<Void>(Result.FAIL_TIMEOUT));
		}

		// Currently does nothing, dummy only allows a single predefined user.
		// TODO: (dff 22/03/2020) Change dummy to contain user list, allow
		//  creation of new user.
		cb.updateRegister(new Response<Void>(Result.FAIL_DEFAULT));
	}

	@Override
	public void getPins(Callbacks cb) {
		if (timeout) {
			cb.updatePins(new Response<List<TreasurePin>>(Result.FAIL_TIMEOUT));
		}

		ArrayList<TreasurePin> pinList = new ArrayList<>();
		Collections.addAll(pinList, pin);

		cb.updatePins(new Response<List<TreasurePin>>(Result.SUCCESS, pinList));
	}

	@Override
	public void logOut(Callbacks cb) {
		if (timeout) {
			cb.updateLogOut(new Response<Void>(Result.FAIL_TIMEOUT));
		}

		// Currently does nothing.
		cb.updateLogOut(new Response<Void>(Result.FAIL_DEFAULT));
	}

	@Override
	public void delete(Callbacks cb) {
		if (timeout) {
			cb.updateDelete(new Response<Void>(Result.FAIL_TIMEOUT));
		}

		// Currently does nothing.
		cb.updateDelete(new Response<Void>(Result.FAIL_DEFAULT));
	}

	@Override
	public void getUserTreasures(Callbacks cb) {
		if (timeout) {
			cb.updateUserTreasures(new Response<List<Treasure>>(Result.FAIL_TIMEOUT));
		}

		ArrayList<Treasure> trsList = new ArrayList<>();
		for (Treasure t : trs) {
			if (t.ownerId == usr.index) {
				trsList.add(t);
			}
		}

		cb.updateUserTreasures(new Response<List<Treasure>>(Result.SUCCESS, trsList));
	}

	@Override
	public void removeTreasure(int treasureId, Callbacks cb) {
		if (timeout) {
			cb.updateRemove(new Response<Void>(Result.FAIL_TIMEOUT));
		}

		// Currently does nothing.
		cb.updateRemove(new Response<Void>(Result.FAIL_DEFAULT));
	}

	@Override
	public void getSpecificTreasure(int treasureId, Callbacks cb) {
		if (timeout) {
			cb.updateSpecificTreasure(new Response<Treasure>(Result.FAIL_TIMEOUT));
		}

		// Currently does nothing.
		for (Treasure t : trs) {
			if (t.index == treasureId) {
				cb.updateSpecificTreasure(new Response<Treasure>(Result.SUCCESS, t));
				return;
			}
		}
		cb.updateSpecificTreasure(new Response<Treasure>(Result.FAIL_INVALID_INPUT));
	}

	@Override
	public void claim(int treasureId, Callbacks cb) {
		if (timeout) {
			cb.updateClaim(new Response<Update>(Result.FAIL_TIMEOUT));
		}

		// Currently does nothing.
		cb.updateClaim(new Response<Update>(Result.FAIL_DEFAULT));
	}

	@Override
	public void abandon(Callbacks cb) {
		if (timeout) {
			cb.updateAbandon(new Response<Update>(Result.FAIL_TIMEOUT));
		}

		// Currently does nothing.
		cb.updateAbandon(new Response<Update>(Result.FAIL_DEFAULT));
	}

	@Override
	public void capture(Callbacks cb) {
		if (timeout) {
			cb.updateCapture(new Response<Update>(Result.FAIL_TIMEOUT));
		}

		// Currently does nothing.
		cb.updateCapture(new Response<Update>(Result.FAIL_DEFAULT));
	}

	@Override
	public void bury(Treasure treasure, Callbacks cb) {
		if (timeout) {
			cb.updateBury(new Response<Treasure>(Result.FAIL_TIMEOUT));
		}

		// Currently does nothing.
		cb.updateBury(new Response<Treasure>(Result.FAIL_DEFAULT));
	}
}
