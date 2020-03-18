package hi.parkpirates.android.model;

import java.util.List;

/*
	GameInterface{..} provides an interface that can be used by the system
	 controllers to interact with the game model.  See specific implementation
	 in MobileGame{..} class.
 */
public interface GameInterface {
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
}
