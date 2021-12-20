package nazioni.italy.org;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

	private final static String DB_URL = "jdbc:mysql://localhost:3306/db_nations";
	private final static String DB_USER = "root";
	private final static String DB_PASSWORD = "2282";
	private final static String DB_QUERY = "select c.name, c.country_id, r.name as region, c2.name as continent \r\n"
            + "from countries c\r\n" + "join regions r ON r.region_id = c.region_id\r\n"
            + "join continents c2 on c2.continent_id = r.continent_id\r\n" + "group by c.name\r\n" + "order by c.name;";
	private final static String DB_QUERY2 = "select c.name, c.country_id, r.name as region, c2.name as continent \r\n"
            + "from countries c\r\n" 
            + "join regions r ON r.region_id = c.region_id\r\n"
            + "join continents c2 on c2.continent_id = r.continent_id\r\n" 
            + "where c.name like "
            + "?\r\n" + "group by c.name\r\n" 
            + "order by c.name;";
	
	public static void main(String[] args) throws SQLException {
		
		Scanner scanner = new Scanner(System.in);

		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(DB_QUERY);
			while (result.next()) {

				System.out.println("Country name: " + result.getString("c.name"));
				System.out.println("ID n. " + result.getInt("c.country_id"));
				System.out.println("Region name: " + result.getString("region") + ". ");
				System.out.println("Continent name: " + result.getString("continent") + ". ");

			}
			
			System.out.println("Inserisci l'elemento da cercare: ");
			String UserInput = scanner.next();
			String UserInputFinal = "%" + UserInput + "%";
			
			try (PreparedStatement preparedStatement = connection.prepareStatement(DB_QUERY2)) {
				
				preparedStatement.setString(1,UserInputFinal);
				ResultSet result2 = preparedStatement.executeQuery();
				
				while (result2.next()) {
					System.out.println("Country name: " + result2.getString("c.name"));
					System.out.println("ID n. " + result2.getInt("c.country_id"));
					System.out.println("Region name: " + result2.getString("region") + ". ");
					System.out.println("Continent name: " + result2.getString("continent") + ". ");

				}
			}

		} catch (SQLException message) {
			System.out.println("errore" + message.getMessage());
		}
     scanner.close();
	}
}
