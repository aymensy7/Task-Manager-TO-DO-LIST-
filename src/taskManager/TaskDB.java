package taskManager;

import java.sql.*;
import java.util.ArrayList;
/**
 * class TaskDB 
 * handle operations of tasks
 * in database
 */
public class TaskDB {
    
	/**
	 * function for save task in the database
	 * after check the user is exist
	 * and the task if exist (it will modify)
	 * and if task don't exist will be add a new task
	 * @param task who will save
	 * @param userId where task will save
	 * @return  true or false
	 */
	public boolean saveTask(TaskImpl task, int userId) {
	    // Check if the user_id exists in the user table
	    if (userId <= 0 || !isUserExist(userId)) {
	        System.out.println("Invalid User ID: " + userId);
	        return false;
	    }

	    // Check if the task already exists based on name and user_id
	    if (isTaskExist(task.getName(), userId)) {
	    	 String query1 = "UPDATE task set user_id=?, name=?, description=?, start_date=?, due_date=?, completed=?,priority=?, category=?,compelete_date=?,repeated=?,comment=?"
	    	 		+ "where name=? ";
	    	  try (Connection connection = DatabaseConnection.getConnection();
	    		         PreparedStatement statement = connection.prepareStatement(query1)) {

	    		        // Set parameters to the prepared statement
	    		        statement.setInt(1, userId); // user_id
	    		        statement.setString(2, task.getName()); // name
	    		        statement.setString(3, task.getDescription()); // description
	    		        statement.setString(4, task.getStartDate()); // start_date
	    		        statement.setString(5, task.getDueDate()); // due_date
	    		        statement.setString(6, task.isCompleted()); // completed
	    		        statement.setString(7, task.getPriority()); // priority
	    		        statement.setString(8, task.getCategories()); // category
	    		        statement.setString(9, task.getCompletDate());
	    		        statement.setString(10, task.getRepeat());
	    		        statement.setString(11, task.getComment());
	    		        statement.setString(12, task.getName());
	    		        

	    		        // Execute the update and return whether it was successful
	    		        return statement.executeUpdate() > 0;

	    		    } catch (SQLException e) {
	    		        e.printStackTrace();
	    		        return false; // Return false if an error occurs
	    		    }

	        
	    }

	    String query = "INSERT INTO task (user_id, name, description, start_date, due_date, completed,priority, category,compelete_date,repeated,comment) " +
	                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection connection = DatabaseConnection.getConnection();
	         PreparedStatement statement = connection.prepareStatement(query)) {

	        // Set parameters to the prepared statement
	        statement.setInt(1, userId); // user_id
	        statement.setString(2, task.getName()); // name
	        statement.setString(3, task.getDescription()); // description
	        statement.setString(4, task.getStartDate()); // start_date
	        statement.setString(5, task.getDueDate()); // due_date
	        statement.setString(6, task.isCompleted()); // completed
	        statement.setString(7, task.getPriority()); // priority
	        statement.setString(8, task.getCategories()); // category
	        statement.setString(9, task.getStartDate());
	        statement.setString(10, task.getRepeat());
	        statement.setString(11, task.getComment());

	        // Execute the update and return whether it was successful
	        return statement.executeUpdate() > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; // Return false if an error occurs
	    }
	}
  /**
   * method for check the task exist or no
   * @param taskName task checked
   * @param userId where task is
   * @return true or false
   */
	private boolean isTaskExist(String taskName, int userId) {
	    String query = "SELECT COUNT(*) FROM task WHERE name = ? AND user_id = ?";
	    try (Connection connection = DatabaseConnection.getConnection();
	         PreparedStatement statement = connection.prepareStatement(query)) {
	        statement.setString(1, taskName);  // Set task name
	        statement.setInt(2, userId);       // Set user_id
	        ResultSet resultSet = statement.executeQuery();
	        
	        // If the count is greater than 0, the task already exists for this user
	        if (resultSet.next() && resultSet.getInt(1) > 0) {
	            return true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
    
	/**
	 * method for check the user exist or no
	 * @param userId user checking
	 * @return true or false
	 */
	private boolean isUserExist(int userId) {
	    String query = "SELECT 1 FROM user WHERE id = ?";
	    try (Connection connection = DatabaseConnection.getConnection();
	         PreparedStatement statement = connection.prepareStatement(query)) {

	        statement.setInt(1, userId);
	        ResultSet resultSet = statement.executeQuery();
	        return resultSet.next(); // True if the user exists

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

   /**
    * method for load tasks from database to a list 
    * @param userId from where tasks load
    * @return tasks loaded
    */
    public ArrayList<TaskImpl> loadTasks(int userId) {
        ArrayList<TaskImpl> tasks = new ArrayList<>();
        String query = "SELECT * FROM task WHERE user_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId); // Load tasks only for the specific user
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                TaskImpl task = new TaskImpl();
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setCompletDate(resultSet.getString("compelete_date"));
                task.setRepeat(resultSet.getString("repeated"));
                task.setStartDate(resultSet.getString("start_Date"));
                task.setDueDate(resultSet.getString("due_date"));
                task.setDueDate(resultSet.getString("due_date"));
                task.setCompleted(resultSet.getString("completed"));
                task.setComment(resultSet.getString("comment"));
                task.setPriority(resultSet.getString("priority"));
                task.setCategories(resultSet.getString("category"));
                
              
		        
                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
    
    /**
     * method for delete task from database
     * @param taskName who will be delete
     * @param userId  where be delete
     */
    public void deleteTask(String taskName, int userId) {
        String query = "DELETE FROM task WHERE name = ? AND user_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, taskName);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
