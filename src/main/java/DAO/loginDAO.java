package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import Entity.usersEntity;

public class loginDAO {
private DataSource datasource;
	
	public loginDAO(DataSource datasource) {
		this.datasource = datasource;
	}
	
	public usersEntity Login(String email, String pass) throws SQLException, ClassNotFoundException{
		String sql = "select * from Users where email = ? and password_hash = ?";
		try (Connection connection = datasource.getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setString(1, email);
			statement.setString(2, pass);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				return new usersEntity(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
			}
		} catch (Exception e) {
		}
		return null;
	}
}
