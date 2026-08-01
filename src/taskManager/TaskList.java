package taskManager;

import java.util.ArrayList;
import java.util.List;
/**
 * abstract class TaskList
 * that define the necessary methods for managing a list of tasks
 */
public abstract class TaskList {
    protected  ArrayList<TaskImpl> tasks;
    /**
     * method  for add a new task to the list
     * @param task added
     */
    public abstract void addTask(TaskImpl task);
    /**
     * method  for delete  a new task to the list
     * @param name of the deleted task
     * @return true if task is deleted,false otherwise
     */
    public abstract boolean deleteTask(String name);
    
    /**
     * method for edit a task
     * @param currentName the name of the task who will be edit
     * @param newName for the task
     * @param newDescription for the task
     * @param newDueDate for the task
     * @param newStartDate for the task
	 * @param newPriority of the task
	 * @param newCategories of the task
	 * @param newComment of the task
	 * @param newRepeat of the task
     * @return true if the task is edited ,false otherwise
     */
    public abstract boolean editTask(String currentName, String newName, String newDescription,String newStartDate, String newDueDate,String newPriority,String newCategories ,String newComment,String newRepeat);
    /**
     * method  return the whole list of tasks
     * @return ArrayList of tasks
     */
    public abstract ArrayList<TaskImpl> getAllTasks();
}
