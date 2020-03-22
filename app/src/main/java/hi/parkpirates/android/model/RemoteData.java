package hi.parkpirates.android.model;

import java.util.List;

/*
	RemoteData{..} provides a standard interface to the functionality
	 offered by the park pirates server.  This is a loose mapping of the
	 RESTful interface of the server.  Observe that the design of this
	 class was determined largely by the goal that any given action
	 that requires networking with the remote host shouldn't take more
	 than a single POST -> RESPONSE transaction, to minimize overhead.

	See RemoteSource{..} class for implementation.

	Note that DummySource{..} is also provided so that this interface
	 can be used before the server side interface has been implemented.
 */
public interface RemoteData {
	// All requests made to the remote data source will result in some
	//	form of Response<?> object, containing both a result code (success,
	//	failure, timeout, etc.) and a relevant data object which has been
	//	updated as a result of the action.

	// Sometimes a single request made to the remote data may affect
	//	both a treasure and a user -- in these cases the Response from
	//	the method contains an Update containing all the _relevant_ game
	//	data objects which have changed.
	class Update {
		public final Treasure treasure;
		public final User user;

		public Update(Treasure t, User u) {
			this.treasure = t;
			this.user = u;
		}
	}

	// Because all requests made to the remote host happen asynchronously,
	//	any client of this class needs to implement the associated callback
	//	methods.
	// NOTE: The callbacks for _every_ remote action provided by this class
	//	are kept in a single CallBacks class -- the reason for this is due
	//	to the architecture of this system; the only client of this interface
	//	is the MobileGame{..} class which needs to implement all of these
	//	callbacks; thus there was no specific reason to break them up into
	//	separate callback interfaces.
	// NOTE: Each specific action of RemoteData will invoke a single one
	//	of these callback methods.  See the corresponding method for
	//	documentation.
	interface Callbacks {
		void updateLogIn(Response<User> outcome);
		void updateVerify(Response<User> outcome);
		void updateRegister(Response<Void> outcome);
		void updatePins(Response<List<TreasurePin>> outcome);
		void updateLogOut(Response<Void> outcome);
		void updateDelete(Response<Void> outcome);
		void updateUserTreasures(Response<List<Treasure>> outcome);
		void updateRemove(Response<Void> outcome);
		void updateSpecificTreasure(Response<Treasure> outcome);
		void updateClaim(Response<Update> outcome);
		void updateAbandon(Response<Update> outcome);
		void updateCapture(Response<Update> outcome);
		void updateBury(Response<Treasure> outcome);
	}

	// cancelPending(..) method shall cancel all asynchronous tasks that have
	//	are currently running.  This method shall be used anywhere where
	//	completion of an asynchronous task will take place after the RemoteData
	//	object has been invalidated (f.ex. when switching between activities).
	void cancelPending();

	// logIn(..) method attempts to log in with the credentials supplied.  This
	//	method shall be used when the user is not logged in (nor was logged in
	//	when the previous session, if any, terminated).
	void logIn(String name, String passPlain, Callbacks cb);

	// verify(..) method submits a user name and a session token, which
	//	the remote host will verify.  If the verification is successful, then
	//	the current state of that User will be returned.  This method shall
	//	be used when the game is started, but the user was logged in when
	//	a previous session terminated.
	// NOTE: Currently login tokens do not expire -- they are only invalidated
	//	if the user logs in on another device, or explicitly logs out.
	void verify(String name, String token, Callbacks cb);

	// register(..) method is used to register a new user with the remote host.
	//	The callback for this method receives a pure result response; there is
	//	no data explicitly part of this action -- the user of the program
	//	is not logged in by this action, and must log in separately.
	void register(String name, String email, String passPlain, Callbacks cb);

	// getPins(..) method retrieves the set of all treasure pins known to
	//	the system.
	void getPins(Callbacks cb);

	// logOut(..) method logs out the current user's session.
	void logOut(Callbacks cb);

	// delete(..) method deletes the logged in user's account.  Note
	//	that this also invalidates their login token and associated data
	//	so be sure to respond appropriately if this method succeeds.
	void delete(Callbacks cb);

	// getUserTreasures(..) method fetches a list of all the treasures
	//	that have been hidden by the logged in user.  This can be used
	//	when displaying user information -- it can be used to generate
	//	a list display of treasures owned by the user.
	void getUserTreasures(Callbacks cb);

	// removeTreasure(..) method removes a single treasure (owned by the
	//	logged in user) from the game.  Note that some policy decisions
	//	still remain to be made about this action.
	/* TODO: (dff 22/03/2020) What happens to other users' claims on a
	    treasure when the treasure is removed?  Are the claims simply
	    invalidated?  It may be difficult to ensure that this action
	    propagates to other users' devices in a timely fashion.  Another
	    alternative is that the treasure is not deleted, but simply marked
	    as complete -- all further attempts to claim the treasure or
	    capture it by submitting the secret code will fail.
	    A third alternative is to simply not allow a treasure to be
	    removed if there are currently any claims on it.
	 */
	void removeTreasure(int treasureId, Callbacks cb);

	// getSpecificTreasure(..) method fetches the treaure data associated
	//	with the provided treasure index.
	void getSpecificTreasure(int treasureId, Callbacks cb);

	// claim(..) method lays a claim by the logged in user upon the specified
	//	treasure.
	void claim(int treasureId, Callbacks cb);

	// abandon(..) method abandons the logged in user's current claim.
	void abandon(Callbacks cb);

	// capture(..) method is used to signal that the treasure has successfully
	//	been found (correct secret code submitted by user for a treasure upon
	//	which they have an active claim).  Note that this method assumes
	//	the system has _already_ validated the capture, ensuring that the
	//	claim is valid and the code correct.
	void capture(Callbacks cb);

	// bury(..) method registers a new treasure with the system under the
	//	ownership of the logged in user.
	void bury(Treasure treasure, Callbacks cb);

}
