package Entity;

public class usersEntity {
	private int user_id;
	private String username;
	private String password_hash;
	private String email;
	private String role;
	private String created_at;
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword_hash() {
		return password_hash;
	}
	public void setPassword_hash(String password_hash) {
		this.password_hash = password_hash;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public usersEntity(int user_id, String username, String password_hash, String email, String role,
			String created_at) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password_hash = password_hash;
		this.email = email;
		this.role = role;
		this.created_at = created_at;
	}
	
}
