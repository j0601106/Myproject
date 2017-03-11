package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lts.global.core.dao.LTSDaoFactory;

public class DBConnect_Login {

	/**
	 * @param args
	 */
	// String driverName="oracle.jdbc.driver.OracleDriver";
	// String username = "WEBDB";
	// String password = "1qaz2wsX";
	// String url="jdbc:oracle:thin:@192.168.231.3:1521:WEBDB";
	
	
//	String conn_driver = "jdbc:oracle:thin:@127.0.0.1:1521:MENG";
	
	String conn_class = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	//String conn_driver = "jdbc:sqlserver://10.6.200.118:1433;DatabaseName=TBD";
	String conn_driver = "jdbc:sqlserver://10.78.5.26:1433;DatabaseName=TBD";
	String conn_username = "tbduser";
	String conn_password = "1qaz2wsx";	
	Connection conn;
	Statement sm;
	String res = "N";
	
	/**
	 * @param args
	 * @throws SQLException SQLException
	 */
	public void ConnectionDB() throws SQLException {
		try {
			Class.forName(conn_class).newInstance();

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// conn =
		// DriverManager.getConnection("jdbc:oracle:thin:@10.6.200.117:1521:XE","F30A","f30a");
		// conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:MENG", "A17A", "a17a");
		//conn = DriverManager.getConnection(conn_driver, conn_username, conn_password);
		conn = LTSDaoFactory.getDaoFactory().createDao("ds_SSR_E77A").getConnection();
	}
	
	/**
	 * @param SQL SQL
	 * 
	 */
	public void countlogin(String SQL) {

		try {
			ConnectionDB();
			Statement stmt = conn.createStatement();
			stmt.executeQuery(SQL);
			stmt.close();

		} catch (SQLException e) {
			res = "計算登入次數錯誤";
		}
	}
	/**
	 * @param SQL SQL
	 * @throws SQLException SQL
	 * @return int int
	 */
	public int count(String SQL) throws SQLException {
		ResultSet rs = null;
		try {
			ConnectionDB();
			Statement stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e) {
			res = "帳號檢查錯誤";
		}
		int cnt = 0;
		while (rs.next()) {
			cnt = rs.getInt("cnt");
		}
		return cnt;
	}

	/**
	 * @param SQL SQL
	 * 
	 * @return String String
	 */
	public String CheckAccount(String SQL) {

		try {
			ConnectionDB();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				if (rs.getInt("cnt") > 0) {
					res = "Y";
				} else {
					res = "N";
				}
			}
			stmt.close();

		} catch (SQLException e) {
			res = "帳號檢查錯誤";
		}
		return res;
	}

	/**
	 * @param arg
	 * 
	 * 
	 */
	public void closeDB() {
		try {
			if (sm != null) {
				sm.close();
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	/**
	 * @param arg
	 * 
	 * @return Statement Statement
	 */
	public Statement getStatement() {
		return sm;
	}
	/**
	 * @param sm sm
	 * 
	 * 
	 */
	public void setStatement(Statement sm) {
		this.sm = sm;
	}
	/**
	 * @param args args
	 * 
	 * 
	 */
	public static void main(String[] args) {
		// DBConnect_Login connServer = new DBConnect_Login();
		// String
		// a=connServer.CheckAccount("select count(*) as cnt from ACCOUNT");
		// System.out.print(a);
	}

}
