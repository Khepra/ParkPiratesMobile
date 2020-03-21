package hi.parkpirates.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
	Cached<T>{..} class represents an instance of a cached data object of type
	 T.  It contains some indication of the time at which the data was sourced
	 as well as a flag which can be set indicating that the cached object
	 has definitely expired and needs to be refreshed.

	Note that the policy of how long to maintain cached objects, where to source
	 the data from, etc. does not belong in this object.  Cached<?> is just a
	 generic wrapper around a data object of any type which holds some
	 relevant information about the state of the cached data.  Policy decisions
	 are made by users of the Cached<?> class.
	See MobileGame{..} class for caching and refresh policy.
 */
public class Cached<T extends Parcelable> implements Parcelable, Serializable {
	private static final long serialVersionUID = 1L;

	private T 				obj;
	private Date			origin;
	private boolean			force;

	public Cached(T target) {
		this.obj = target;
		this.origin = new Date();
		this.force = false;
	}

	// expired(..) method checks whether our cached object has expired.  This
	//	method returns true if the time at which the cached object was stored
	//	is more than timeToLive minutes in the past, or if the 'force' flag
	//	has been set, and false otherwise.
	public boolean expired(int timeToLive) {
		if (timeToLive <= 0 || force) {
			return true;
		}

		Calendar c = new GregorianCalendar();
		c.setTime(origin);
		c.add(GregorianCalendar.MINUTE, timeToLive);
		Date end = c.getTime();

		return (new Date()).after(end);
	}

	// refresh(..) method replaces the cache target with a new instance of the
	//	target type, and resets the origin time.  Observe that this also clears
	//	the 'force' flag.
	public void refresh(T freshTarget) {
		obj = freshTarget;
		origin = new Date();
		force = false;
	}

	// invalidate(..) method sets the force flag for this object.
	public void invalidate() {
		force = true;
	}

	// data(..) method is an accessor to the cached object.
	public T data() {
		return obj;
	}

	@Override
	public String toString() {
		return "Cached " + obj.getClass().getName() + " @" + origin.toString() +
				"\n  " + obj.toString();
	}

	// **** Parcelable ****
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(obj.getClass().getName());
		dest.writeParcelable(obj, 0);
		dest.writeLong(origin.getTime());
		dest.writeInt(force ? 1 : 0);
	}

	private Cached(Parcel src) {
		final String type = src.readString();
		try {
			this.obj = src.readParcelable(Class.forName(type).getClassLoader());
		} catch (ClassNotFoundException e) {
			// TODO: (dff 19/03/2020) This exception should probably bubble up..
			System.out.println("Fatal error reading Cached<?> target from parcel.");
			System.out.println(e.getMessage());
		}
		this.origin = new Date(src.readLong());
		this.force = (src.readInt() > 0);
	}

	public static final Parcelable.Creator<Cached> CREATOR =
			new Parcelable.Creator<Cached>() {
				@Override
				public Cached createFromParcel(Parcel source) {
					return new Cached(source);
				}

				@Override
				public Cached[] newArray(int size) {
					return new Cached[size];
				}
			};
	// **** END Parcelable ****
}
