package hi.parkpirates.android.model;

// TODO: (dff 18/03/2020) Short description.
public class User {
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
}
