package hi.parkpirates.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/*
	The Claim{..} class represents a single user's claim upon a particular treasure.
 */
public class Claim implements Parcelable, Serializable {
	private static final long serialVersionUID = 1L;

	public final int userId;
	public final int treasureId;
	private final Date start;
	private boolean complete;
	private boolean expired;

	// This form of the constructor is provided to create NEW claims ab-initio.
	//	This shall be done where the system must create a brand new claim on a
	//	particular treasure in response to user input.
	public Claim(int user, int treasure) {
		this.userId = user;
		this.treasureId = treasure;
		this.start = new Date();
		this.complete = false;
		this.expired = false;
	}

	// This form of the constructor is provided for when a pre-existing claim
	//	fetched from a remote source must be instantiated as a Java object.  This
	//	will typically be used when the system acquires information on claims from
	//	the remote source, and NOT in response to the user's actions.
	public Claim(int user, int treasure, Date start, boolean complete, boolean expired) {
		this.userId = user;
		this.treasureId = treasure;
		this.start = start;
		this.complete = complete;
		this.expired = expired;
	}

	// TODO: (dff 18/03/2020) Some sort of expiry or completion check?

	// **** Parcelable ****
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(userId);
		dest.writeInt(treasureId);
		dest.writeLong(start.getTime());
		dest.writeInt(complete ? 1 : 0);
		dest.writeInt(expired ? 1 : 0);
	}

	private Claim(Parcel src) {
		this.userId = src.readInt();
		this.treasureId = src.readInt();
		this.start = new Date(src.readLong());
		this.complete = (src.readInt() > 0);
		this.expired = (src.readInt() > 0);
	}

	public static final Parcelable.Creator<Claim> CREATOR =
			new Parcelable.Creator<Claim>() {
				@Override
				public Claim createFromParcel(Parcel source) {
					return new Claim(source);
				}

				@Override
				public Claim[] newArray(int size) {
					return new Claim[size];
				}
			};
	// **** END Parcelable ****
}
