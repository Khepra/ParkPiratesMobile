package hi.parkpirates.android.model;

/*
	GpsLocation{..} class represents a single location in earth GPS coordinates.
	 Objects of this type are immutable, and should be constructed as
	 necessary.  To take a measurement of the device's coordinates, use
	 the static sample(..) method.

	Comparison operators may be added later; it might be nice to be able to
	 Easily test whether the GpsLocation lies within an area defined in some
	 fashion.
 */
public class GpsLocation {
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
}
