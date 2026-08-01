package taskManager;

import java.sql.*;
/**
  * class UserDB
  * handle operations of users
  * in database
 */
public class UserDB {
   
	
	/**
	 * method for register user
	 * in data base
	 * @param username for register
	 * @param password for register
	 * @return true or false
	 */
    public boolean registerUser(String username, String password) {
        String checkUserQuery = "SELECT COUNT(*) FROM user WHERE username = ?";
        String insertUserQuery = "INSERT INTO user (username, password) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(checkUserQuery);
             PreparedStatement insertStatement = connection.prepareStatement(insertUserQuery)) {

            // Check if the username already exists
            checkStatement.setString(1, username);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return false; // Username already exists
            }

            // If not, insert the new user
            insertStatement.setString(1, username);
            insertStatement.setString(2, password);
            insertStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle other SQL exceptions
        }
    }
    
    /**
     * method for check user
     * exist in data base or no
     * @param username of checking user
     * @param password of checking user
     * @return true or false
     */
    public boolean validateUser(String username, String password) {
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Returns true if a record is found

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * method for get user id
     * from database
     * @param username of user id
     * @return userId of user
     */
    public int getUserId(String username) {
        String query = "SELECT id FROM user WHERE LOWER(username) = LOWER(?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Trim the username to avoid errors with extra spaces
            statement.setString(1, username.trim());

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Check if a result exists
            if (resultSet.next()) {
                int userId = resultSet.getInt("id");
                return userId;
            } else {
              //  System.out.println("No user found with username: " + username);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Return -1 if user not found or an error occurs
    }

    

}
