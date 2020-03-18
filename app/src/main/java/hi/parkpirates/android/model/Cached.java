package hi.parkpirates.android.model;

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
public class Cached<T> {
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
}
