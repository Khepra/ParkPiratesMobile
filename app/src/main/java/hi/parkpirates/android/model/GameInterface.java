package hi.parkpirates.android.model;

import android.os.Parcelable;

import java.util.List;

/*
	GameInterface{..} provides an interface that can be used by the system
	 controllers to interact with the game model.  See specific implementation
	 in MobileGame{..} class.
 */
public interface GameInterface extends Parcelable {
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
}
