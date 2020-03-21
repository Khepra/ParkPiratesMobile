package hi.parkpirates.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

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
public class TreasurePin implements Parcelable, Serializable {
	private static final long serialVersionUID = 1L;

	public final int			treasureId;
	public final GpsLocation	location;
	public final int			status;

	public TreasurePin(int id, GpsLocation loc, int stat) {
		treasureId = id;
		location = loc;
		status = stat;
	}

	@Override
	public String toString() {
		return Integer.toString(treasureId) + location.toString() + Integer.toString(status);
	}

	// **** Parcelable ****
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(treasureId);
		dest.writeParcelable(location, 0);
		dest.writeInt(status);
	}

	private TreasurePin(Parcel src) {
		this.treasureId = src.readInt();
		this.location = src.readParcelable(GpsLocation.class.getClassLoader());
		this.status = src.readInt();
	}

	public static final Parcelable.Creator<TreasurePin> CREATOR =
			new Parcelable.Creator<TreasurePin>() {
				@Override
				public TreasurePin createFromParcel(Parcel source) {
					return new TreasurePin(source);
				}

				@Override
				public TreasurePin[] newArray(int size) {
					return new TreasurePin[size];
				}
			};
	// **** END Parcelable ****
}
