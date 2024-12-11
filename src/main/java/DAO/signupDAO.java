package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import Entity.usersEntity;

public class signupDAO {
	private DataSource datasource;
	
	public signupDAO(DataSource datasource) {
		this.datasource = datasource;
	}
	
	public boolean CreateAccount(usersEntity acc) throws ClassNotFoundException {
		String sql = "INSERT INTO Users (username, password_hash, email) VALUES (?, ?, ?);";
		try (Connection conn = datasource.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	            
            stmt.setString(1, acc.getUsername());
            stmt.setString(2, acc.getPassword_hash());
            stmt.setString(3, acc.getEmail());
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	}
	
}
