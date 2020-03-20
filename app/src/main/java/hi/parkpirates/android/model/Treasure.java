package hi.parkpirates.android.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

// TODO: (dff 18/03/2020) Short description.
public class Treasure implements Parcelable, Serializable {
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

	private final ArrayList<GpsLocation> 	locPath;
	private final ArrayList<Claim> 			claims;

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

	// **** Parcelable ****
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(index);
		dest.writeString(name);
		dest.writeInt(value);
		dest.writeParcelable(locFinal, 0);
		dest.writeString(code);
		dest.writeString(auxInfo);
		dest.writeInt(multiFind ? 1 : 0);
		dest.writeLong(endTime.getTime());
		dest.writeInt(complete ? 1 : 0);
		dest.writeInt(ownerId);
		dest.writeInt(timesFound);
		dest.writeParcelableArray(locPath.toArray(new GpsLocation[0]), 0);
		dest.writeParcelableArray(claims.toArray(new Claim[0]), 0);
	}

	private Treasure(Parcel src) {
		this.index = src.readInt();
		this.name = src.readString();
		this.value = src.readInt();
		this.locFinal = src.readParcelable(GpsLocation.class.getClassLoader());
		this.code = src.readString();
		this.auxInfo = src.readString();
		this.multiFind = (src.readInt() > 0);
		this.endTime = new Date(src.readLong());
		this.complete = (src.readInt() > 0);
		this.ownerId = src.readInt();
		this.timesFound = src.readInt();

		this.locPath = new ArrayList<>();
		this.claims = new ArrayList<>();

		locPath.addAll(Arrays.asList(
				(GpsLocation[])src.readParcelableArray(GpsLocation.class.getClassLoader())));
		claims.addAll(Arrays.asList(
				(Claim[])src.readParcelableArray(Claim.class.getClassLoader())));
	}

	public static final Parcelable.Creator<Treasure> CREATOR =
			new Parcelable.Creator<Treasure>() {
				@Override
				public Treasure createFromParcel(Parcel source) {
					return new Treasure(source);
				}

				@Override
				public Treasure[] newArray(int size) {
					return new Treasure[size];
				}
			};
	// **** END Parcelable ****
}
