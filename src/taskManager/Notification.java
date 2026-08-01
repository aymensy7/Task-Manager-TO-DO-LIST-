package taskManager;

/**
 * class Notification
 * for manage one notification
 */
public  class Notification {
    private String taskName;
    private String type;
    private String startDate;
    private String dueDate;

    /**
     * constructor for Notification
     * @param taskName of task
     * @param type of notification
     * @param startDate of task
     * @param dueDate of task
     */
    public Notification(String taskName, String type, String startDate, String dueDate ) {
        this.taskName = taskName;
        this.type = type;
        this.startDate=startDate;
        this.dueDate = dueDate;
        
    }
    
    /**
     * getter for task name
     * @return taskName of this task
     */
    public String getTaskName() {
        return taskName;
    }
    
    /**
     * getter of type of notification
     * @return type
     */
    public String getType() {
        return type;
    }
    
    /**
     * getter of due date
     * @return dueDate of this task
     */
    public String getDueDate() {
        return dueDate;
    }
    
    /**
     * getter of start date
     * @return startDate of this task
     */
    public String getStartDate() {
        return startDate;
    }
    
    /**
     * setter of start date
     * @param startDate of this task
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    /**
     * setter of due date
     * @param dueDate of this task
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
