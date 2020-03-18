package hi.parkpirates.android.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// TODO: (dff 18/03/2020) Short description.
public class Treasure {
	public final int 			index;
	public final String 		name;
	public final int 			value;
	public final GpsLocation 	locFinal;
	public final String 		code;
	public final String 		auxInfo;
	public final boolean 		multiFind;
	public final Date 			endTime;
	public final boolean 		complete;
	public final int 			ownerId;
	public final int 			timesFound;

	private final List<GpsLocation> 	locPath;
	private final List<Claim> 			claims;

	public Treasure(int id, String name, int worth, GpsLocation loc, String code, String auxInfo,
					boolean multi, Date end, boolean complete, int owner, int found) {
		this.index = id;
		this.name = name;
		this.value = worth;
		this.locFinal = loc;
		this.code = code;
		this.auxInfo = auxInfo;
		this.multiFind = multi;
		this.endTime = end;
		this.complete = complete;
		this.ownerId = owner;
		this.timesFound = found;

		this.locPath = new ArrayList<>();
		this.claims = new ArrayList<>();
	}

	// TODO: (dff 18/03/2020) Add mechanism for system to guide user along path.

	// addToPath(..) method appends a single GPS location to the tail of the path
	//	to the treasure.
	public void addToPath(GpsLocation next) {
		locPath.add(next);
	}

	// TODO: (dff 18/03/2020) Define claim-treasure interaction.
}
