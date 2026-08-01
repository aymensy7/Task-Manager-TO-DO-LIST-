package taskManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * class NotificationManager
 * for manage a notifications
 */
public class NotificationManager {

	  private List<Notification> notifications;
	  private TaskListImpl tasks;
 
	    /**
	     * constructor for NotificationManager
	     * @param tasks list
	     */
	    public NotificationManager(TaskListImpl tasks) {
	        notifications = new ArrayList<>();
	        this.tasks=tasks;
	        
	    }
        
	   /**
	     * method too add a notification to the list
	     * @param taskName of this task
	     * @param type of this notification
	     * @param dueDateTime of this task
	     * @param status of this task
	    */
	    public void addNotification(String taskName, String type, String dueDateTime, String status) {
	    	
	        notifications.add(new Notification(taskName, type, dueDateTime, status));
	    }
	    
	    /**
	      * method for searching tasks that 
	      * have a notification 
	      * of dueDateReminders
	     */
	    public void dueDateReminders(){
	    	for(TaskImpl task : tasks.getAllTasksForReading()) {
	    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-M-d");
	    		LocalDate currentDate = LocalDate.now();
	    		if (!task.getDueDate().equals("")) {
	    		LocalDate dateTask= LocalDate.parse(task.getDueDate(),formatter);
	    		
	    	         if(ChronoUnit.DAYS.between(dateTask, currentDate)>=1 && task.isCompleted().equals("No")) {
	    	        	 Notification net=new Notification(task.getName(),"Due Date Reminder",task.getStartDate(),task.getDueDate());
	    	             if(!notifications.contains(net)) {
	    	                 notifications.add(net);     
	    	             }
	    	         }	
	    	}
	    		}
	   
	    	
	    }
	    
	    
	    /**
	      * method for searching tasks that 
	      * have a notification 
	      * of overdueTaskAlerts
	     */
	    public void overdueTaskAlerts(){
	    	for(TaskImpl task : tasks.getAllTasksForReading()) {
	    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-M-d");
	    		LocalDate currentDate = LocalDate.now();
	    		if (!task.getDueDate().equals("")) {
	    		LocalDate dateTask= LocalDate.parse(task.getDueDate(),formatter);
	    	         if(ChronoUnit.DAYS.between(dateTask,currentDate)>=0 && task.isCompleted().equals("No")) {
	    	        	 Notification net=new Notification(task.getName(),"Overdue Task Alerts",task.getStartDate(),task.getDueDate());
	    	             if(!notifications.contains(net)) {
	    	                 notifications.add(net);     
	    	             }   	        	 
	    	         }	
	    	}
	    		}
	    	
	    }
	    
	    /**
	      * method for searching tasks that 
	      * have a notification 
	      * of taskStartReminders
	     */
	    public void taskStartReminders(){
	    	
	    	for(TaskImpl task : tasks.getAllTasksForReading()) {
	    		if (!task.getStartDate().equals("")) {
	    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-M-d");
	    		LocalDate currentDate = LocalDate.now();
	    		if(!task.getStartDate().equals("")) {
	    		LocalDate dateTask= LocalDate.parse(task.getStartDate(),formatter);
	    	         if(ChronoUnit.DAYS.between(dateTask,currentDate)>=0) {
	    	        	 Notification net=new Notification(task.getName(),"Task Start Reminders",task.getStartDate(),task.getDueDate());
	    	             if(!notifications.contains(net)) {
	    	                 notifications.add(net);     
	    	             }	        	 
	    	         }	
	    	}
	    	}
	    		}
	    	
	    }
	    /**
	      * method for searching tasks that 
	      * have a notification 
	      * of taskCompletionNotifications
	     */
	      public void taskCompletionNotifications() {
	    	  for(TaskImpl task : tasks.getAllTasksForReading()) {
	    		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-M-d");
		    		LocalDate currentDate = LocalDate.now();
		    		if(!task.getCompletDate().equals("")) {
		    		LocalDate dateTask= LocalDate.parse(task.getCompletDate(),formatter);
		    	         if(ChronoUnit.DAYS.between(dateTask,currentDate)==0) {
		    	        	 Notification net=new Notification(task.getName(),"Task Completed",task.getStartDate(),task.getDueDate());
		    	             if(!notifications.contains(net)) {
		    	                 notifications.add(net);     
		    	             }	        	 
		    	         }	
		    	}
	    	  }
	    	  
	      }
	      
	      /**
		    * method for searching tasks that 
	        * have a notification 
	        * of weeklySummaries
		   */
		    public void weeklySummaries(){
		    	
		    	for(TaskImpl task : tasks.getAllTasksForReading()) {
		    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-M-d");
		    		LocalDate currentDate = LocalDate.now();
		    		if(!task.getStartDate().equals("")) {
		    		LocalDate dateTask= LocalDate.parse(task.getStartDate(),formatter);
		    	         if(dateTask.isEqual(currentDate.plusDays(1)) || dateTask.isAfter(currentDate.plusDays(1))
		                        && dateTask.isBefore(currentDate.plusWeeks(1))) {
		    	        	 Notification net=new Notification(task.getName(),"Task Coming Next Week",task.getStartDate(),task.getDueDate());
		    	             if(!notifications.contains(net)) {
		    	                 notifications.add(net);     
		    	             }	        	 
		    	         }	
		    	}
		    	}
		    	
		    }
		    
		    /**
		      * method for searching tasks that 
	          * have a notification 
	          * of recurringTaskReminders
		    */
		    public void recurringTaskReminders() {
		    	for(TaskImpl task : tasks.getAllTasksForReading()) {
		    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-M-d");
		    		LocalDate currentDate = LocalDate.now();
		    		String date =currentDate.format(formatter);
		    		if(!task.getStartDate().equals("") && !task.getRepeat().equals("")) {
		    		
		    	         if(task.getRepeat().equals("daily") && task.getStartDate().equals(date) && !task.getCompletDate().equals("Yes")) {
		    	        	 Notification net=new Notification(task.getName(),"Task Repeated Daily",task.getStartDate(),task.getDueDate());
		    	             if(!notifications.contains(net)) {
		    	                 notifications.add(net);     
		    	             }	        	 
		    	         }
		    	         else if(task.getRepeat().equals("weekly") && task.getStartDate().equals(date) && !task.getCompletDate().equals("Yes") ){
		    	        	 Notification net=new Notification(task.getName(),"Task Repeated Weekly",task.getStartDate(),task.getDueDate());
		    	             if(!notifications.contains(net)) {
		    	                 notifications.add(net);     
		    	             }	        	 
		    	         }	
		    	         else if(task.getRepeat().equals("monthly") && task.getStartDate().equals(date) && !task.getCompletDate().equals("Yes")) {
		    	        	 Notification net=new Notification(task.getName(),"Task Repeated Monthly",task.getStartDate(),task.getDueDate());
		    	             if(!notifications.contains(net)) {
		    	                 notifications.add(net);     
		    	             }	        	 
		    	         }	
		    	      }
		    	}
		    }

        /**
         * function for return a list of notifications
         * @return notifications list
         */
	    public List<Notification> getNotifications() {
	    
	        return notifications;
	    }

}
