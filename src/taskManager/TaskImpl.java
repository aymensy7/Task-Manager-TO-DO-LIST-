package taskManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * an class TaskImpl that implement Task
 * this class represent a specific task
 */
public class TaskImpl implements Task {
    private String name="";
    private String description="";
    private String dueDate="";
    private String Completed;
    private String comment="";
    private String priority="";
    private String categories="";
    private String startDate="";
    private String completeDate="";
    private String repeat="";

    /**
     * constructor for a new task
     * @param name of task
     * @param description of task
     * @param dueDate of task
     * @param startDate of the task
	 * @param priority of the task
	 * @param categories of the task
	 * @param comment of the task
	 * @param repeat of the task
     */
    public  TaskImpl(String name, String description, String startDate, String dueDate ,String priority,String categories ,String comment,String repeat) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.Completed = "No";
        this.comment = comment;
        if(!priority.equals("No Priority")) {
        	this.priority=priority;
        	
        }
        this.categories=categories;
        this.startDate=startDate;
        if(!repeat.equals("No Repetition")) {
        this.repeat=repeat;
        }
       
    }
    /**
     * a empty constructor
     */
    public TaskImpl() {};
    
    @Override
    public TaskImpl createTask(String name, String description, String startDate, String dueDate ,String priority,String categories ,String comment,String repeat) {
    	return new TaskImpl(name,description,startDate,dueDate,priority,categories,comment,repeat);
    	
    }

    
    /**
     * setter of name
     * @param  name of the task
     */
   public void setName(String name)
   {
	   this.name=name;
   }
    /**
     * getter of name
     * @return name of the task 
     */
   public String getName() 
   {
	   return this.name;
   }
    
    /**
     * setter of description
     * @param  description of the task
     */
   public void setDescription(String description)
   {
	   this.description=description;
   }
    /**
     * getter of description
     * @return description of the task
     */
   public String getDescription()
   {
	   return this.description;
   }
    
    /**
     * setter of dueDate
     * and make changes if task 
     * have a repeat value
     * @param  dueDate of the task
     */
   public void setDueDate(String dueDate)
   {
	   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-M-d");
	   if (!this.startDate.equals("")) {
       LocalDate localDate = LocalDate.parse(this.startDate, formatter);
	   
		   if(this.repeat.equals("daily") ) {
			   LocalDate newDueDate = localDate.plusDays(1);
		        String newDate =newDueDate.format(formatter); 
		        this.dueDate=newDate;
		   }
		   else if(this.repeat.equals("weekly") ) {
			   LocalDate newDueDate = localDate.plusWeeks(1);
		        String newDate =newDueDate.format(formatter);
		        this.dueDate=newDate;
			   
		   }
		   else if(this.repeat.equals("monthly") ) {
			   LocalDate newDueDate = localDate.plusMonths(1);
		        String newDate =newDueDate.format(formatter);
		        this.dueDate=newDate;
			   
		   }
		   else {
		      this.dueDate=dueDate;
		   }}
	   else {
		   this.dueDate=dueDate;
	   }
   }
    /**
     * getter of dueDate
     * @return the dueDate of the task
     */
    public String getDueDate() {
    	return this.dueDate;
    }
    
    /**
     * setter of comment
     * @param comment added
     */
    public void setComment(String comment)
    {
    	this.comment=comment;
    }
    /**
     * getter of comment
     * @return the comment of task
     */
   public String getComment() 
    {
    	return this.comment;
    }
   
   @Override
   public String isCompleted() {
   	return this.Completed;
   }

	@Override
	public void markAsCompleted() {
		this.Completed="Yes";
		LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.completeDate = currentDate.format(formatter);

		
	}
	@Override
    public void markAsInProgress() {
		if(this.Completed.equals("In Progress")) {
			this.Completed="No";
		}
		else {
		this.Completed="In Progress";
		}
    	
    }
	
	
	/**
	 * setter of priority
	 * @param priority of the task
	 */
   public void setPriority(String priority)
   {   
	   if(priority.equals("No Priority")){
		   this.priority="";
	   }
	   else {
	   this.priority=priority;
	   }
   }
   /**
    * getter of priority
    * @return priority of the task
    */
   public String getPriority() 
   {
	   return this.priority;
   }
   
   /**
    * setter of Categories
    * @param categories of the task
    */
   public void setCategories(String categories)
   {
	   this.categories=categories;
   }
   /**
    * getter of Categories
    * @return categories of the task
    */
   public String getCategories() 
   {
	   return this.categories;
   }
   
   /**
    * setter of startDate
    * and make changes if task 
    * have a repeat value
    * @param  startDate of the task
    */
  public void setStartDate(String startDate)
  {    
	  LocalDate today = LocalDate.now();
	  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-M-d");
      String dateNow = today.format(formatter);
	   if(this.repeat.equals("daily") && !this.startDate.equals(dateNow)) {
		   this.startDate=dateNow;
		   
	   }
	   else if(this.repeat.equals("weekly") && this.dueDate.equals(dateNow)) {
		   this.startDate=dateNow;
		   
	   }
	   else if(this.repeat.equals("monthly") && this.dueDate.equals(dateNow)) {
		   this.startDate=dateNow;
		   
	   }
	   else {
	      this.startDate=startDate;
	   }
  }
   /**
    * getter of startDate
    * @return the startDate of the task
    */
   public String getStartDate() {
   	return this.startDate;
   }
   
   /**
    * setter of status
    * @param status of the task
    */
   public void setCompleted(String status) {
	   LocalDate today = LocalDate.now();
	   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-M-d");
	    String dateNow = today.format(formatter);
	    if (!this.startDate.equals("")) {
	    LocalDate startdate=LocalDate.parse(this.startDate,formatter);
	    
        if(!this.completeDate.equals("") && !this.repeat.equals("")) {
        	LocalDate completedate=LocalDate.parse(this.completeDate,formatter);
	    if(this.repeat.equals("daily") && !this.completeDate.equals(dateNow)) {
	    	
	        this.Completed="No";
	        this.completeDate="";
	    } 
	    else if(this.repeat.equals("weekly") && completedate.isBefore(startdate)) {
	    	
	    	this.Completed="No";
	    	this.completeDate="";
	    } 
        else if(this.repeat.equals("monthly") && completedate.isBefore(startdate)) {
	    	
	    	this.Completed="No";
	    	this.completeDate="";
	    }
	   
	    else {
	    	this.Completed=status;
	    }
        }
        else {
	    	this.Completed=status;
	    }}
	    else {
	    	this.Completed=status;
	    }
   }
   
   /**
    * setter of date completed task
    * @param completeDate of the task
    */
   public void setCompletDate(String completeDate) {
	   this.completeDate=completeDate;
   }
   
   /**
    * getter of dateCompleted
    * @return completeDate
    */
   public String getCompletDate() {
	   return this.completeDate;
   }
   
   /**
    * setter of repeat
    * @param repeat of the task
    */
   public void setRepeat(String repeat) {
	   if(repeat.equals("No Repetition")) {
		   this.repeat="";
	   }
	   else {
	   this.repeat=repeat;
	   }
   }
   
   /**
    * getter of repeat
    * @return repeat of the task
    */
   public String getRepeat() {
	   return this.repeat;
   }
   
   
   
   
   
    
}
