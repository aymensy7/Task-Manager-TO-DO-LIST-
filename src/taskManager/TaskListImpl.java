package taskManager;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import java.util.List;


/**
 * a concrete class TaskListImpl that extends TaskList
 * this class should manage a list of tasks
 */
public class TaskListImpl extends TaskList {
    
	/**
	 * constructor for a new list of tasks
	 */
    public TaskListImpl() {
        tasks = new ArrayList<>();
    }

    @Override
    public void addTask(TaskImpl task) {
        tasks.add(task);
    }

    @Override
    public boolean deleteTask(String nameTask) {
    	 TaskImpl taskToRemove = null;
    	for (TaskImpl task : tasks) {
            if (task.getName().equals(nameTask)) {
                taskToRemove = task;
                break;
            }
    	}
         if(taskToRemove!=null)
         {
        tasks.remove(taskToRemove);
        return true;
         }
         else
         {
        	 return false;
         }
    }
    
    /**
     * method to remove 
     * all completed tasks
     * without repeated value
     * @return tasksNames list of removed tasks 
     */
    public ArrayList<String> deletealltask(){
    	List<TaskImpl> tasksToRemove = new ArrayList<>();
    	ArrayList<String> tasksNames=new ArrayList<>();
    	

    	for (TaskImpl task : tasks) {
    	    if (task.isCompleted().equals("Yes") && task.getRepeat().isEmpty()) {
    	    	tasksNames.add(task.getName());
    	        tasksToRemove.add(task);
    	        
    	        
    	    }
    	}

    	// Remove the tasks after iterating
    	tasks.removeAll(tasksToRemove);
    	return tasksNames;
    	
    }

    @Override
    public ArrayList<TaskImpl> getAllTasks() {
        return tasks;
    }
    /**
     * method for return tasks 
     * just for reading
     * @return tasks
     */
    public List<TaskImpl> getAllTasksForReading() {
        return Collections.unmodifiableList(tasks);
    }
    
    /**
     * this function make a search in all the list
     * make this search with a keyword
     * if any name,description,comment,priority and categories of a task include this keyword 
     * we add this task to a new list
     * and return this list
     * @param  keyword which is being searched
     * @return ArrayList result
     */
    public ArrayList<TaskImpl> searchTasks(String keyword) {
    	ArrayList<TaskImpl> result = new ArrayList<>();
        for (TaskImpl task : tasks) {
            if (task.getName().contains(keyword) ||  task.getDescription().contains(keyword) ||   task.getComment().contains(keyword) 
               || task.getPriority().contains(keyword) || task.getCategories().contains(keyword) || task.getDueDate().contains(keyword)
               || task.getStartDate().contains(keyword) || task.getRepeat().contains(keyword)) {
                result.add(task);
            }
        }
        return result;
    }
    
    @Override
    /**
     * method for edit a task
     * make a research by the currentName,
     * and if it found will be edited
     * @param currentName the name of the task who will be edit
     * @param newName for the task
     * @param newDescription for the task
     * @param newDueDate for the task
     * @return true if the task is edited ,false otherwise
     */
    public boolean editTask(String currentName, String newName, String newDescription,String newStartDate, String newDueDate,String newPriority,String newCategories ,String newComment,String newRepeat) {
        for (TaskImpl task : tasks) {
            if (task.getName().equals(currentName)) {
                // Update the fields if new values are provided
            	 if (newName != null && !newName.isEmpty()) {
                     task.setName(newName);
                 }
                 if (newDescription != null && !newDescription.isEmpty()) {
                     task.setDescription(newDescription);
                 }
                 if (newStartDate != null && !newStartDate.isEmpty()) {
                     task.setStartDate(newStartDate);
                 }
                 if (newDueDate != null && !newDueDate.isEmpty()) {
                     task.setDueDate(newDueDate);
                 }
                 if (newPriority != null && !newPriority.isEmpty()) {
                     task.setPriority(newPriority);;
                 }
                 if (newCategories != null && !newCategories.isEmpty()) {
                     task.setCategories(newCategories);
                 }
                 if (newComment != null && !newComment.isEmpty()) {
                     task.setComment(newComment);
                 }
                 if (newRepeat != null && !newRepeat.isEmpty()) {
                     task.setRepeat(newRepeat);
                 }
                 return true;
            }
        }
        return false;
    }
    /**
     *  method for research of a task by his name
     *  we used it in edit task (GUI)
     * @param nameTask of the  searched task 
     * @return true if task found ,or false otherwise 
     */
     public boolean searchOneTask(String nameTask) {
    	 for (TaskImpl task : tasks) {
    		 if(task.getName().equals(nameTask)) {
    			 return true;
    		 }
    	 }
    	 return false;
     }
    /**
     * Sort a list tasks by name alphabetically.
     * @return tasks sorted tasks
     */
    public List<TaskImpl> sortByName() {
        Collections.sort(tasks, Comparator.comparing(TaskImpl::getName));
        return tasks;
    }

    /**
     * Sort tasks by due date (earliest first).
     * @return tasks sorted tasks
     */
    public List<TaskImpl> sortByDueDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Collections.sort(tasks, (t1, t2) -> {
            try {
                Date date1 = dateFormat.parse(t1.getDueDate());
                Date date2 = dateFormat.parse(t2.getDueDate());
                return date1.compareTo(date2);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        });
        return tasks;
    }

    /**
     * Sort tasks by  status.
     *  incomplete tasks appear first
     * @return tasks sorted tasks
     */
    public ArrayList<TaskImpl> sortByStatus() {
        // Define the ranking as a list of strings
        List<String> statusRanking = Arrays.asList("No", "In Progress", "Yes");

        // Use Collections.sort with a comparator
        Collections.sort(tasks, new Comparator<TaskImpl>() {
            @Override
            public int compare(TaskImpl task1, TaskImpl task2) {
                int rank1 = statusRanking.indexOf(task1.isCompleted());
                int rank2 = statusRanking.indexOf(task2.isCompleted());
                return Integer.compare(rank1, rank2);
            }
        });

        return tasks;
    }

    
    /**
     * Sort tasks by  priority
     * with this order high middle and low
     * @param tasks will be ordered
     * @return tasks ordered 
     */
    public static ArrayList<TaskImpl> sortTasksByPriority(ArrayList<TaskImpl> tasks) {
        // Define priority order
        List<String> priorityOrder = List.of("High", "Medium", "Low" ,""); 

        // Use Collections.sort with a custom comparator
        Collections.sort(tasks, Comparator.comparing(task -> priorityOrder.indexOf(task.getPriority()))); 

        return tasks;
    }
    
   
    /**
     * setter of task list
     * @param task for set
     */
    public void setTasks(ArrayList<TaskImpl> task) {
    	this.tasks=task;
    }
    

 

   
}
