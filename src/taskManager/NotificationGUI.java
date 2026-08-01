package taskManager;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
 * class Notification GUI
 * to show notifications of tasks
 */
public class NotificationGUI extends JFrame {
    
	private static final long serialVersionUID = 1L;
	/**
	 *  notification manager
	 */
	private NotificationManager notificationManager;
  
	/**
	 * constructor of NotificationGUI
	 * @param notificationManager  for GUI
	 */
    public NotificationGUI(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
        setTitle("Notifications");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create table model with column names
        String[] columnNames = {"Task Name", "Notification Type", "Start Date", "Due Date"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable notificationTable = new JTable(tableModel);
        notificationTable.setFillsViewportHeight(true);
        notificationTable.setRowHeight(30);
        notificationTable.getTableHeader().setReorderingAllowed(false);
        
        // Style the table header
        notificationTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        notificationTable.getTableHeader().setBackground(new Color(72, 209, 204));
        notificationTable.getTableHeader().setForeground(Color.black);

        // Style the table rows
        notificationTable.setFont(new Font("Arial", Font.PLAIN, 14));
        notificationTable.setSelectionBackground(new Color(173, 216, 230));
        notificationTable.setSelectionForeground(Color.BLACK);

        // Populate table with notifications
        updateTableModel(tableModel);

        // Scroll pane for table
        JScrollPane scrollPane = new JScrollPane(notificationTable);

       
      

        // Layout the frame
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    
    }

    /**
     * method for update table of notifications after
     * call all methods for adding notifications
     * and check the notification is not already exist in the table
     * @param tableModel of tasks
     */
    private void updateTableModel(DefaultTableModel tableModel) {
    	 // Clear existing rows
        tableModel.setRowCount(0);
        
        // Fetch notifications from the manager
        notificationManager.overdueTaskAlerts();
        notificationManager.taskStartReminders();
        notificationManager.dueDateReminders();
        notificationManager.taskCompletionNotifications();
        notificationManager.weeklySummaries();
        notificationManager.recurringTaskReminders();

        // Check if notifications are fetched
        ArrayList<Notification> notifications = (ArrayList<Notification>) notificationManager.getNotifications();
        if (notifications == null || notifications.isEmpty()) {
        	JOptionPane.showMessageDialog(null, "No notifications to display.");
        } else {
            
            // Add notifications to table, avoiding duplicates
            for (Notification notif : notifications) {
                // Check if the notification already exists in the table
                boolean exists = false;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String existingTaskName = (String) tableModel.getValueAt(i, 0);
                    String existingType = (String) tableModel.getValueAt(i, 1);
                    String existingStartDate = (String) tableModel.getValueAt(i, 2);
                    String existingDueDate = (String) tableModel.getValueAt(i, 3);

                    // Only match when the entire combination (taskName + type + startDate + dueDate) is the same
                    if (existingTaskName.equals(notif.getTaskName()) &&
                        existingType.equals(notif.getType()) &&
                        existingStartDate.equals(notif.getStartDate()) &&
                        existingDueDate.equals(notif.getDueDate())) {
                        exists = true;
                        break;
                    }
                }

                // Only add the notification if it doesn't already exist
                if (!exists) {
                    Object[] rowData = {notif.getTaskName(), notif.getType(), notif.getStartDate(), notif.getDueDate()};
                    tableModel.addRow(rowData);
                }
            }
        }
    }
}

