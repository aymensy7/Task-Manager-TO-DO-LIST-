package taskManager;

/**
 * an interface task 
 * that defines the necessary methods for managing a task 
 */

public interface Task {
	/**
	 * method to create a new task
	 * @param name of the task
	 * @param description of the task
	 * @param dueDate of the task
	 * @param startDate of the task
	 * @param priority of the task
	 * @param categories of the task
	 * @param comment of the task
	 * @param repeat of the task
	 * @return Task created
	 */
	 public TaskImpl createTask(String name, String description, String startDate, String dueDate ,String priority,String categories ,String comment,String repeat);
	 
	  /**
	     * method to  mark task as completed 
	     */
	    void markAsCompleted();
	    
	    /**
	     * method to  mark task as on working
	     */
	    void markAsInProgress();

	    
	    /**
	     * method to return status of task
	     * @return the status of the task
	     */
	    public String isCompleted();
	

    
}