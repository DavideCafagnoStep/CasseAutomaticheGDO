package it.step.casseAutomatiche.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC {
	
	private static Connection cn;

	
	public static Connection getConnection() {
		if(cn == null) {
			startConnection();
		}
		return cn;
	}


	private static void startConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			cn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/casse_automatiche_DB", "postgres", "admin");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void closePsAndRs(PreparedStatement ps, ResultSet rs) {
		try {
			if(rs!= null)
				rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(ps!= null)
			ps.closeOnCompletion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
