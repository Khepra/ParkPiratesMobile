package hi.parkpirates.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/*
	GpsLocation{..} class represents a single location in earth GPS coordinates.
	 Objects of this type are immutable, and should be constructed as
	 necessary.  To take a measurement of the device's coordinates, use
	 the static sample(..) method.

	Comparison operators may be added later; it might be nice to be able to
	 Easily test whether the GpsLocation lies within an area defined in some
	 fashion.
 */
public class GpsLocation implements Parcelable, Serializable {
	private static final long serialVersionUID = 1L;

	public final float		latitude;
	public final float		longitude;

	public GpsLocation(float lat, float lon) {
		this.latitude = lat;
		this.longitude = lon;
	}

	public static GpsLocation sample() {
		// TODO: (dff 18/03/2020) Connect to android location services.
		return new GpsLocation(0, 0);
	}

	@Override
	public String toString() {
		return "(" + Float.toString(latitude) + ", " + Float.toString(longitude) + ")";
	}

	// **** Parcelable ****
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeFloat(latitude);
		dest.writeFloat(longitude);
	}

	private GpsLocation(Parcel src) {
		this.latitude = src.readFloat();
		this.longitude = src.readFloat();
	}

	public static final Parcelable.Creator<GpsLocation> CREATOR =
			new Parcelable.Creator<GpsLocation>() {
				@Override
				public GpsLocation createFromParcel(Parcel source) {
					return new GpsLocation(source);
				}

				@Override
				public GpsLocation[] newArray(int size) {
					return new GpsLocation[size];
				}
			};
	// **** END Parcelable ****
}
