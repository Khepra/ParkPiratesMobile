package hi.parkpirates.android.model;

/*
	Result enumeration contains a complete list of the possible results from
	 calls to GameInterface methods.  These can be checked by callers, and
	 then responded to appropriately.
 */
public enum Result {
	DEPRECATED,
	REDUNDANT,
	SUCCESS,
	FAIL_DEFAULT,
	FAIL_TIMEOUT,
	FAIL_INVALID_CRED,
	FAIL_INVALID_INPUT,
	FAIL_TRS_CLAIMED,
	FAIL_TRS_UNCLAIMED,
	FAIL_TRS_SELF,
	FAIL_TRS_CODE
}
