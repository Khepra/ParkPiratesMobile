package hi.parkpirates.android.model;

/*
	Response<?>{..} class bundles a data object with a result code.  Response
	 objects will typically be passed into callback methods resulting from
	 asynchronous tasks.
 */
public class Response<T> {
	public final T body;
	public final Result code;

	// For 'failed' responses, no object need be supplied, and
	//	the body is set to null.
	public Response(Result code) {
		this.body = null;
		this.code = code;
	}

	public Response(Result code, T body) {
		this.body = body;
		this.code = code;
	}
}
