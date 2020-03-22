package hi.parkpirates.android.model;

import android.content.Context;
import android.os.Parcelable;

import java.util.List;

/*
	GameInterface{..} provides an interface that can be used by the system
	 controllers to interact with the game model.  See specific implementation
	 in MobileGame{..} class.

	GameInterface provides a number of callback interfaces for its methods --
	 since all of these methods can involve communicating with a remote host,
	 they need to occur asynchronously and thus we require an associated
	 callback method to trigger on completion.

	Note that the interface provided here is quite similar to that of the
	 RemoteData type, but is expressed differently in terms of how other
	 types subscribe to different callbacks.  Since many different
	 activities will use the GameInterface, any particular activity need
	 only implement the callback interfaces that it requires for its own
	 functionality, without having to stub out the rest of them.
 */
public interface GameInterface extends Parcelable {
	// The game itself may sometimes need access to the surrounding
	//	context.  This is mainly to do with accessing the cache files
	//	in local storage; in general we should avoid tying model
	//	mechanics to controller concepts.
	void setContext(Context ctx);

	// logIn(..) method attempts to log the user in with the supplied
	//	credentials.  Observe that this method _only_ applies to when
	//	a log in is attempted from cold-start; if the user was logged in
	//	in a previous session, then verify(..) should be used instead.
	// This method cannot return any cached immediate data.
	void logIn(String name, String passPlain, LogInCallback cb);
	interface LogInCallback {
		void postLogIn(Response<Void> outcome);
	}

	// verify(..) method attempts to verify that the credentials recovered
	//	from a previous session are still valid.
	// This method cannot return any cached immediate data.
	void verify(String name, String token, VerifyCallback cb);
	interface VerifyCallback {
		void postVerify(Response<Void> outcome);
	}

	// register(..) method registers a new user with the system.  Observe
	//	that this method does NOT log the user in as this new user even if
	//	the registration succeeds; that must be done separately.
	// This method cannot return any cached immediate data.
	void register(String name, String email, String passPlain, RegisterCallback cb);
	interface RegisterCallback {
		void postRegister(Response<Void> outcome);
	}

	// getActiveTreasures(..) method gathers the list of treasure pins
	//	associated with all treasures currently active in the game.
	// If the CACHED list of treasure pins is still valid, this method
	//	will call it back immediately.
	void getActiveTreasures(PinCallback cb);
	interface PinCallback {
		void postPins(Response<List<TreasurePin>> outcome);
	}

	// logOut(..) method logs the current user out.
	// This method cannot return any cached immediate data.
	void logOut(LogOutCallback cb);
	interface LogOutCallback {
		void postLogOut(Response<Void> outcome);
	}

	// delete(..) method deletes the currently logged in user's account
	//	from the system.  Observe that there is no recovering or undoing
	//	this action!  Note also that if this action succeeds, it is implicit
	//	that the user is logged out of the now non-existent account.
	// This method cannot return any cached immediate data.
	void delete(DeleteCallback cb);
	interface DeleteCallback {
		void postDelete(Response<Void> outcome);
	}

	// getUserTreasures(..) method retrieves a list of all the treasures that
	//	are owned by the logged in user.
	// If the CACHED user data is still valid, then this method will call it
	//	back immediately.
	void getUserTreasures(UserTreasureCallback cb);
	interface UserTreasureCallback {
		void postUserTreasures(Response<List<Treasure>> outcome);
	}

	// remove(..) method removes or otherwise deactivates a single treasure
	//	owned by the logged in user.
	// This method cannot return any cached immediate data.
	void remove(int treasureId, RemoveCallback cb);
	interface RemoveCallback {
		void postRemove(Response<Void> outcome);
	}

	// getSpecificTreasure(..) method retrieves the information associated
	//	with the specified treasure.
	// If the CACHED treasure data is still valid, this method will call
	// it back immediately.
	void getSpecificTreasure(int treasureId, TreasureCallback cb);
	interface TreasureCallback {
		void postTreasure(Response<Treasure> outcome);
	}

	// claim(..) method will lay claim to the treasure specified.
	// This method cannot return any cached immediate data.
	void claim(int treasureId, ClaimCallback cb);
	interface ClaimCallback {
		void postClaim(Response<Void> outcome);
	}

	// abandon(..) method will abandon the user's existing claim.
	// This method cannot return any cached immediate data.
	void abandon(AbandonCallback cb);
	interface AbandonCallback {
		void postAbandon(Response<Void> outcome);
	}

	// submit(..) method takes a secret code submitted by the user
	//	and compares it to the code of the treasure that they have
	//	currently claimed.  If the code is correct, the user is
	//	credited with capturing the treasure, and his claim is
	//	concluded.
	// If the CACHED user, and claimed treasure, are both valid, and
	//	the code is INCORRECT this method may call back immediately with
	//	a failed outcome.
	void submit(String code, SubmitCallback cb);
	interface SubmitCallback {
		void postSubmit(Response<Void> outcome);
	}

	// bury(..) method attempts to create a new treasure (owned by the
	//	logged in user) from the information provided.  Note that this
	//	method may not succeed if invalid data is provided.
	// This method cannot return any cached immediate data.
	void bury(Treasure treasure, BuryCallback cb);
	interface BuryCallback {
		void postBury(Response<Void> outcome);
	}
	/*
	Result registerUser(String name, String email, String passPlain);
	Result logIn(String name, String passPlain);

	Result logOut();
	Result deleteUser();

	List<TreasurePin> getActiveTreasures();
	List<Treasure> getUserTreasures();
	Treasure getSpecificTreasure(int index);

	Result buryTreasure(Treasure newTrs);
	Result claimTreasure(int index);
	Result submitCode(String code);
	Result abandonClaim();

	// NOTE: WIP; not integrated yet.

	// A number of separate callback interfaces are defined here,
	//	so that a particular activity can choose to implement only
	//	the subset that is relevant to its functionality.

	// Generally, an activity will implement one or two of these in
	//	such a way that it can dynamically alter the UI/Control flow
	//	based on the results of interactions with the GameInterface.

	interface SystemCallbacks {
		void postRegister();
		void postLogIn();
		void postLogOut();
		void postDelete();
	}

	interface MapCallbacks {
		void postPins();
	}

	interface UserCallbacks {
		void postInfo();
		void postBury();
		void postRemove();
	}

	interface TreasureCallbacks {
		void postInfo();
		void postClaim();
		void postSubmit();
		void postAbandon();
	}

	 */
}
