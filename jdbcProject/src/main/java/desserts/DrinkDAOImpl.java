package desserts;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DrinkDAOImpl implements DrinkDAO {
	Connection conn = null;
	final static String SELECT_ALL_SQL = "SELECT id, name, cost FROM drinks;";
	final static String INSERT_SQL = "INSERT INTO drinks (name, cost) VALUES (?, ?);";
	final static String UPDATE_SQL = "UPDATE drinks SET name = ?, cost = ? WHERE id = ?;";
	final static String DELETE_SQL = "DELETE FROM drinks WHERE id = ?;";
	final static String FIND_SQL = "SELECT * FROM drinks WHERE id = ?;";

	public DrinkDAOImpl() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/desserts?createDatabaseIfNotExist=true",
					"root", "root");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found");
		} catch (SQLException e) {
			System.out.println("SQL exception");
			e.printStackTrace();
		}
	}

	@Override
	public List<DrinkDTO> getAll() {
		List<DrinkDTO> drinks = new ArrayList<>();
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL);
			while (rs.next()) {
				long id = rs.getLong("id");
				String name = rs.getString("name");
				int cost = rs.getInt("cost");
				drinks.add(new DrinkDTO(id, name, cost));
			}
		} catch (SQLException e) {
			System.out.println("unable to run query");
			e.printStackTrace();
		}
		return drinks;
	}

	public DrinkDTO productInfo(int id) {
		try (PreparedStatement preparedStatement = conn.prepareStatement(FIND_SQL, id)) {
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				DrinkDTO product = new DrinkDTO((long) rs.getLong("id"), rs.getString("name"), rs.getInt("cost"));
				return product;

			}
		} catch (SQLException e) {
			System.out.println("unable to run query");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public DrinkDTO create(DrinkDTO drink) {
		try (PreparedStatement preparedStatement = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, drink.getName());
			preparedStatement.setInt(2, (drink.getCost()));
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Unable to create record");
			}
			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					drink.setId(generatedKeys.getLong(1));
					return drink;
				} else {
					throw new SQLException("Creating drink failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			System.out.println("unable to run query");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void remove(Long id) {
		try (PreparedStatement preparedStatement = conn.prepareStatement(DELETE_SQL)) {
			preparedStatement.setInt(1, id.intValue());
			preparedStatement.execute();
		} catch (SQLException e) {
			System.out.println("Unable to run query");
			System.out.println("SQL State: " + e.getSQLState());
			System.out.println("Error Code: " + e.getErrorCode());
			e.printStackTrace();
		}
	}

	@Override
	public void remove(DrinkDTO drink) {
		try (PreparedStatement preparedStatement = conn.prepareStatement(DELETE_SQL)) {
			preparedStatement.setInt(1, drink.getId().intValue());
			preparedStatement.execute();
		} catch (SQLException e) {
			System.out.println("Unable to run query");
			System.out.println("SQL State: " + e.getSQLState());
			System.out.println("Error Code: " + e.getErrorCode());
			e.printStackTrace();
		}
	}

	@Override
	public DrinkDTO update(DrinkDTO drink) {
		if (drink.getId() > Integer.MAX_VALUE) {
			throw new RuntimeException("ID too large");
		}
		try (PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_SQL)) {
			preparedStatement.setString(1, drink.getName());
			preparedStatement.setInt(2, drink.getCost());
			preparedStatement.setInt(3, drink.getId().intValue());
			int changes = preparedStatement.executeUpdate();
			if (changes > 0) {
				return drink;
			}
		} catch (SQLException e) {
			System.out.println("Unable to run query");
			System.out.println("SQL State: " + e.getSQLState());
			System.out.println("Error Code: " + e.getErrorCode());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int count() {
		return 0;
	}

}
