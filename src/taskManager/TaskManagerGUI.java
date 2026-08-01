package taskManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * class TaskManagerGUI for to-do-list
 * 
 */
public class TaskManagerGUI extends JFrame {
   
	/**
	 *  serial version
	 */
    private static final long serialVersionUID = 1L;
    /**
     *  list of task
     */
    private TaskListImpl taskList;
    /**
     *  table of task
     */
    private JTable taskTable;
    /**
     * table model
     */
    private DefaultTableModel tableModel;
    /**
     *  user name of user
     */
    private String username;
    /**
     *  task data base
     */
    private TaskDB taskDAO;
    /**
     *  useerId of user
     */
    private int userId;
    /**
     *  notification manager
     */
    private NotificationManager notificationManager;
    
    /**
     * constructor TaskManager
     * @param username of the user
     */
    public TaskManagerGUI(String username) {
        taskList = new TaskListImpl();
        taskDAO = new TaskDB();
        this.username = username;

        // Get user ID from UserDAO
        UserDB userDAO = new UserDB();
        this.userId = userDAO.getUserId(username); 

        loadTasksFromDatabase();
        initializeGUI();
        
        updateTaskTable();
        notificationManager = new NotificationManager(taskList);
    }
    
    // load task from data base
    private void loadTasksFromDatabase() {
        taskList.setTasks(taskDAO.loadTasks(userId));
    }
    
    
    @SuppressWarnings({ "serial", "static-access" })
    private void initializeGUI() {
        // Set frame properties
        setTitle("Task Manager");
        setSize(1075, 718);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        

         
        // Apply Nimbus Look and Feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        // Add window listener to handle close events
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(
                        TaskManagerGUI.this,
                        "Do you want to save your tasks before exiting?",
                        "Save Tasks",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (option == JOptionPane.YES_OPTION) {
                    saveTasksToDatabase();
                    dispose();
                } else if (option == JOptionPane.NO_OPTION) {
                    dispose();
                }
            }
        });

        // Top panel with logo and search bar
        JPanel topPanel = new JPanel();
        topPanel.setBounds(0, 0, 1061, 83);
        topPanel.setLayout(null);

        // Logo
        JLabel logoLabel = new JLabel("TO-DO LIST", JLabel.CENTER);
        logoLabel.setBounds(96, 10, 786, 31);
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(logoLabel);

        // Search bar
        JPanel searchPanel = new JPanel();
        searchPanel.setBounds(624, 29, 437, 54);
        JLabel searchLabel = new JLabel("Search: ");
        searchLabel.setBounds(24, 21, 45, 22);
        JTextField searchField = new JTextField(20);
        searchField.setBounds(79, 21, 166, 23);
        searchPanel.setLayout(null);

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        topPanel.add(searchPanel);

        JButton searchButton = new JButton("Go");
        searchButton.setBounds(255, 19, 45, 26);
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            ArrayList<TaskImpl> results = taskList.searchTasks(keyword);
            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No tasks found with the keyword.");
            } else {
                updateTaskTable(results);
            }
        });
        searchPanel.add(searchButton);

        // Save button
        JButton saveButton = new JButton("Save ");
        saveButton.setToolTipText("Save Tasks ");
        saveButton.setBounds(310, 19, 99, 26);
        saveButton.addActionListener(e -> saveTasksToDatabase());
        searchPanel.add(saveButton);
        getContentPane().setLayout(null);
        getContentPane().add(topPanel);
        
        JButton notification = new JButton("Notification");
        notification.setBounds(23, 41, 85, 31);
        topPanel.add(notification);
        
        JButton statistics = new JButton("Statistics");
        statistics.setBounds(119, 41, 85, 31);
        topPanel.add(statistics);
        
        notification.addActionListener(e -> {
        	NotificationGUI notifications = new NotificationGUI(notificationManager);
            notifications.setVisible(true);
       });
     
     
        statistics.addActionListener(e -> {
        	 StatisticsGUI statisticsGUI = new StatisticsGUI(taskList);
             statisticsGUI.setVisible(true);
        });

        // Task table panel
        tableModel = new DefaultTableModel(
                new String[]{"Name", "Description", "Start Date", "Due Date", "Completed", "Comment", "Priority", "Categories","Repeat"}, 0);
        taskTable = new JTable(tableModel);
        taskTable.setFillsViewportHeight(true);
        taskTable.setRowHeight(30);
        taskTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        taskTable.setShowGrid(true);
        taskTable.setGridColor(Color.LIGHT_GRAY);

        // Alternate row colors
        taskTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 240));
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.setBounds(90, 82, 971, 544);
        getContentPane().add(scrollPane);
        
        JButton add = new JButton("Add");
        add.setToolTipText("Add Task");
        add.setBackground(new Color(128, 255, 128));
        add.setBounds(5, 144, 80, 39);
        getContentPane().add(add);
        
        JButton remove = new JButton("Remove");
        remove.setToolTipText("Remove Task");
        remove.setBackground(new Color(255, 0, 0));
        remove.setBounds(5, 276, 80, 39);
        getContentPane().add(remove);
        
        JButton edit = new JButton("Edit");
        edit.setToolTipText("Edit Task");
        edit.setBackground(new Color(255, 255, 128));
        edit.setBounds(5, 210, 80, 39);
        getContentPane().add(edit);
        
        JButton markasCompleted = new JButton("✅");
        markasCompleted.setToolTipText("Mark As completed");
        markasCompleted.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        markasCompleted.setBackground(new Color(128, 255, 0));
        markasCompleted.setBounds(5, 342, 80, 39);
        getContentPane().add(markasCompleted);
        
        JButton returnbtn = new JButton("Retrun");
        returnbtn.setToolTipText("Return to list");
        returnbtn.setBounds(5, 474, 80, 39);
        getContentPane().add(returnbtn);
                                
       JButton sortByDueDate = new JButton("DueDate");
        sortByDueDate.addActionListener(new ActionListener() {
          	public void actionPerformed(ActionEvent e) {
               	}
               });
        /**
         * Event listener for sorting tasks by due date.
         */
        sortByDueDate.addActionListener(e -> {
            taskList.sortByDueDate();
            updateTaskTable();
        });
          sortByDueDate.setBounds(322, 636, 85, 35);
          getContentPane().add(sortByDueDate);
          
          JButton sortByName = new JButton("Name");
          sortByName.setBounds(417, 636, 85, 35);
          getContentPane().add(sortByName);
          
          JButton sortByStatus = new JButton("Status");
          sortByStatus.setBounds(518, 636, 85, 35);
          getContentPane().add(sortByStatus);
          
          JButton sortByPriority = new JButton("Priority");
          sortByPriority.setBounds(613, 636, 85, 35);
          getContentPane().add(sortByPriority);
          
          JButton  markasInProgress = new JButton("Progress");
          markasInProgress.setBackground(new Color(0, 128, 255));
          markasInProgress.setToolTipText("Mark Task In Progress");
          markasInProgress.setBounds(5, 408, 80, 39);
          getContentPane().add( markasInProgress);
          
          JLabel lblNewLabel = new JLabel("Sorting By :");
          lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
          lblNewLabel.setBounds(216, 636, 96, 35);
          getContentPane().add(lblNewLabel);
          // Mark task as completed action
          markasInProgress.addActionListener(e -> {
              String taskName = JOptionPane.showInputDialog("Enter Task Name to In Progress (or cancel in progress):");
              for (TaskImpl task : taskList.getAllTasks()) {
                  if (task.getName().equals(taskName)) {
                      task.markAsInProgress();
                      updateTaskTable();
                      break;
                  }
              }
          });
          
          /**
           * Event listener for sorting tasks by Priority
           */
          sortByPriority.addActionListener(e -> {
              taskList.sortTasksByPriority(taskList.getAllTasks());
              updateTaskTable();
          });
          /**
           * Event listener for sorting tasks by Name.
           */
          sortByName.addActionListener(e -> {
              taskList.sortByName();
;              updateTaskTable();
          });
          /**
           * Event listener for sorting tasks by Status.
           */
          sortByStatus.addActionListener(e -> {
              taskList.sortByStatus();
              updateTaskTable();
          });
        
     // Add task action
          add.addActionListener(e -> {
        	    String name = JOptionPane.showInputDialog("Enter Task Name:");
        	    String description = JOptionPane.showInputDialog("Enter Task Description:");
        	    String startDate = JOptionPane.showInputDialog("Enter Start Date (yyyy-MM-dd):");
        	    String dueDate = JOptionPane.showInputDialog("Enter Due Date (yyyy-MM-dd):");

        	    // Priority selection using JComboBox
        	    String[] priorityOptions = {"Low", "Medium", "High", "No Priority"};
        	    JComboBox<String> priorityComboBox = new JComboBox<>(priorityOptions);
        	    
			    JOptionPane.showOptionDialog(null, priorityComboBox, "Select Task Priority", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        	    String priority = (String) priorityComboBox.getSelectedItem();

        	    String categories = JOptionPane.showInputDialog("Enter Task Categories:");
        	    String comment = JOptionPane.showInputDialog("Enter Task Comment:");
        	 // Task repetition selection using JComboBox
        	    String[] repetitionOptions = {"Daily", "Weekly", "Monthly", "No Repetition"};
        	    JComboBox<String> repetitionComboBox = new JComboBox<>(repetitionOptions);

        	    JOptionPane.showOptionDialog(null, repetitionComboBox, "Select Task Repetition", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        	    String repeat = (String) repetitionComboBox.getSelectedItem();

        	    TaskImpl task = new TaskImpl();
        	    task = task.createTask(name, description, startDate, dueDate, priority, categories, comment, repeat);
        	    taskList.addTask(task);
        	    updateTaskTable();
        	});

          
     // Remove task action
        remove.addActionListener(e -> {
            String taskName = JOptionPane.showInputDialog("Enter Task Name to Remove(enter 'All' for remove all completed tasks):");
           if(!(taskName==null)) {
            if(!taskName.equals("All")){
            if (taskList.deleteTask(taskName)) {
                JOptionPane.showMessageDialog(null, "Task removed.");
                taskDAO.deleteTask(taskName, userId);
                updateTaskTable();
            } else {
                JOptionPane.showMessageDialog(null, "Task not found.");
            }
            }
            else {
            	ArrayList<String> names=new ArrayList<>();
            	names=taskList.deletealltask();
            	if (!names.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All completed task are removed.");
                    for(String name :names) {
                    taskDAO.deleteTask(name, userId);
                    }
                    updateTaskTable();
                } else {
                    JOptionPane.showMessageDialog(null, "No completed task to remove.");
                }
            	
            	
            	
            }}
           else {
        	   JOptionPane.showMessageDialog(null, "Cannot be empty");
           }
         });

     // Edit task action
        edit.addActionListener(e -> {
            String taskName = JOptionPane.showInputDialog("Enter Task Name to Edit:");
            if (taskName == null || taskName.trim().isEmpty()) return;
            if (!taskList.searchOneTask(taskName)) {
                JOptionPane.showMessageDialog(this, "Task not found.");
            } else {
                // Get new task details
                String newName = JOptionPane.showInputDialog("Enter new name (leave blank to keep current):");
                String newDescription = JOptionPane.showInputDialog("Enter new description (leave blank to keep current):");
                String newStartDate = JOptionPane.showInputDialog("Enter new Start Date (yyyy-MM-dd, leave blank to keep current):");
                String newDueDate = JOptionPane.showInputDialog("Enter new due date (yyyy-MM-dd, leave blank to keep current):");
                // Priority selection using JComboBox
        	    String[] priorityOptions = {"Low", "Medium", "High", "No Priority"};
        	    JComboBox<String> priorityComboBox = new JComboBox<>(priorityOptions);
        	    JOptionPane.showOptionDialog(null, priorityComboBox, "Select Task Priority", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
        	    String newPriority = (String) priorityComboBox.getSelectedItem();

                String newCategories = JOptionPane.showInputDialog("Enter new Categories (leave blank to keep current):");
                String newComment = JOptionPane.showInputDialog("Enter new Comment (leave blank to keep current):");
                // Task repetition selection using JComboBox
                String[] repetitionOptions = {"daily", "weekly", "monthly", "No Repetition"};
                JComboBox<String> repetitionComboBox = new JComboBox<>(repetitionOptions);

                JOptionPane.showOptionDialog(null, repetitionComboBox, "Select Task Repetition", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
                String newRepeat = (String) repetitionComboBox.getSelectedItem();

                boolean isEdited = taskList.editTask(taskName, newName, newDescription, newStartDate, newDueDate, newPriority, newCategories, newComment,newRepeat);
                if (isEdited) {
                    updateTaskTable();
                    JOptionPane.showMessageDialog(this, "Task updated successfully.");
                }
            }
        });
        // Mark task as completed action
        markasCompleted.addActionListener(e -> {
            String taskName = JOptionPane.showInputDialog("Enter Task Name to Mark as Completed:");
            for (TaskImpl task : taskList.getAllTasks()) {
                if (task.getName().equals(taskName)) {
                    task.markAsCompleted();
                    updateTaskTable();
                    break;
                }
            }
        });
        
        returnbtn.addActionListener(e -> updateTaskTable());
        setVisible(true);
    }
    
    
    
    // method for save tasks in database
    private void saveTasksToDatabase() {
        for (TaskImpl task : taskList. getAllTasksForReading()) {
            boolean success = taskDAO.saveTask(task, userId);
            if (!success) {
                JOptionPane.showMessageDialog(this, "Error saving task: " + task.getName());
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Tasks saved successfully.");
    }
    
    // method for update task table
    private void updateTaskTable() {
        tableModel.setRowCount(0); // Clear the table
        for (TaskImpl task : taskList. getAllTasksForReading()) {
            tableModel.addRow(new Object[]{
                    task.getName(),
                    task.getDescription(),
                    task.getStartDate(),
                    task.getDueDate(),
                    task.isCompleted(),
                    task.getComment(),
                    task.getPriority(),
                    task.getCategories(),
                    task.getRepeat()
            });
        }
    }
    
    // method for update task table
    private void updateTaskTable(List<TaskImpl> tasks) {
        tableModel.setRowCount(0); // Clear the table
        for (TaskImpl task : tasks) {
            tableModel.addRow(new Object[]{
                    task.getName(),
                    task.getDescription(),
                    task.getStartDate(),
                    task.getDueDate(),
                    task.isCompleted(),
                    task.getComment(),
                    task.getPriority(),
                    task.getCategories(),
                    task.getRepeat()
            });
        }
    }
    
    /**
     *  main for TaskManagerGUI
     * @param args for main
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String username = "yourUsername"; // Replace with actual username
            new TaskManagerGUI(username); // Pass username to the constructor
        });
    }
} 