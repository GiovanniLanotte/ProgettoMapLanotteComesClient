package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.management.InstanceNotFoundException;

/**
 * Gestisce l'accesso al DB per la lettura dei dati di training
 * @author Map Tutor
 *
 */
public class DbAccess {

	private static final String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
	private static final String DBMS = "jdbc:mysql";
	private  final String SERVER = "localhost";
	private  int PORT = 3306;
	private  String DATABASE = "AprioriDB";
	private  String USER_ID = "AprioriUser";
	private  String PASSWORD = "apriori";

	private Connection conn;

	/**
	 * Inizializza una connessione al DB
	 */
	public  void initConnection() throws DatabaseConnectionException{
		String connectionString = DBMS+"://" + SERVER + ":" + PORT + "/" + DATABASE;
		try {
			
				Class.forName(DRIVER_CLASS_NAME).newInstance();
			} 
		catch (IllegalAccessException e) {
				
				e.printStackTrace();
				throw new DatabaseConnectionException(e.toString());
			}
		catch (InstantiationException e) {
					
					e.printStackTrace();
					throw new DatabaseConnectionException(e.toString());
			} 
		catch (ClassNotFoundException e) {
			System.out.println("Impossibile trovare il Driver: " + DRIVER_CLASS_NAME);
			throw new DatabaseConnectionException(e.toString());
		}
		
		try {
			conn = DriverManager.getConnection(connectionString, USER_ID, PASSWORD);
			
		} catch (SQLException e) {
			System.out.println("Impossibile connettersi al DB");
			e.printStackTrace();
			throw new DatabaseConnectionException(e.toString());
		}
		
	}
	public Connection getConnection(){
		return conn;
	}
	public void closeConnection() throws SQLException{
		conn.close();
	}

}
