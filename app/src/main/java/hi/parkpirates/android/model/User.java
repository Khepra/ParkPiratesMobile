package hi.parkpirates.android.model;

import android.os.Parcel;
import android.os.Parcelable;

// TODO: (dff 18/03/2020) Short description.
public class User implements Parcelable {
	public final int index;
	public final String name;
	public final String email;
	public final String passHash;
	public final String passSalt;
	public final int score;

	public Claim claim;
	public String token;

	public User(int index, String name, String email, String passHash, String passSalt, int score) {
		this.index = index;
		this.name = name;
		this.email = email;
		this.passHash = passHash;
		this.passSalt = passSalt;
		this.score = score;

		this.claim = null;
		this.token = "";
	}


	// TODO: (dff 18/03/2020) Define claim interaction.

	// TODO: (dff 18/03/2020) Define how tokens are handled.

	// **** Parcelable ****
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(index);
		dest.writeString(name);
		dest.writeString(email);
		dest.writeString(passHash);
		dest.writeString(passSalt);
		dest.writeInt(score);
		dest.writeParcelable(claim, 0);
		dest.writeString(token);
	}

	private User(Parcel src) {
		this.index = src.readInt();
		this.name = src.readString();
		this.email = src.readString();
		this.passHash = src.readString();
		this.passSalt = src.readString();
		this.score = src.readInt();
		this.claim = src.readParcelable(Claim.class.getClassLoader());
		this.token = src.readString();
	}

	public static final Parcelable.Creator<User> CREATOR =
			new Parcelable.Creator<User>() {
				@Override
				public User createFromParcel(Parcel source) {
					return new User(source);
				}

				@Override
				public User[] newArray(int size) {
					return new User[size];
				}
			};
	// **** END Parcelable ****
}
