package hi.parkpirates.android.model;

/*
	TreasurePin{..} class contains the minimum of information required
	 to display upon the world map an indicator to the user that a treasure
	 is 'here' and possibly available.

	'status' member indicates what state the treasure the pin refers to is
	 in, and may take the following values:
		0 -> Treasure is active and unclaimed.
		1 -> Treasure is active but claimed by someone else.
		2 -> Treasure is active but claimed by the user.
		3 -> Treasure was hidden by the user, is active and unclaimed.
		4 -> Treasure was hidden by the user, is active and claimed by someone else.
		5 -> Treasure was hidden by the user, is inactive (completed).

	// TODO: (dff 18/03/2020) Provide static constants to represent these values.

	This class is provided to reduce load upon the server; When a user views the map
	 they do not require the complete information of all active treasures to display
	 the map view, only the locations and statuses thereof.

	Objects of this type are immutable.
 */
public class TreasurePin {
	public final int			treasureId;
	public final GpsLocation	location;
	public final int			status;

	public TreasurePin(int id, GpsLocation loc, int stat) {
		treasureId = id;
		location = loc;
		status = stat;
	}
}
