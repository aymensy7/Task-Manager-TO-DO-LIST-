package taskManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * class StatisticsGUI of to_do_list
 */
public class StatisticsGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    /**
     *  list of tasks
     */
    private TaskListImpl tasks;

    /**
     *  constructor for StatisticsGUI
     * @param tasks list
     */
    public StatisticsGUI(TaskListImpl tasks) {
        this.tasks = tasks;
        setTitle("Statistics");

        // Set a fixed size for the window that fits the content
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the window on the screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a panel for showing statistics with BoxLayout
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding for the panel

        // Total tasks label
        JLabel totalTasksLabel = new JLabel("Total Tasks: " + getTotalTasks());
        totalTasksLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalTasksLabel.setForeground(Color.DARK_GRAY);

        JLabel tasksCompletedTodayLabel = new JLabel("Tasks Completed Today: " + getCompletedTodayTasks());
        tasksCompletedTodayLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tasksCompletedTodayLabel.setForeground(Color.DARK_GRAY);

        // Category-wise task distribution table
        String[] columnNames = {"Category", "Number of Tasks"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            // Make the table non-editable
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable categoryTable = new JTable(tableModel);
        categoryTable.setFont(new Font("Arial", Font.PLAIN, 12));
        categoryTable.setSelectionBackground(new Color(173, 216, 230));
        categoryTable.setSelectionForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(categoryTable);

        // Set font for the header
        categoryTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        categoryTable.getTableHeader().setBackground(new Color(72, 209, 204));
        categoryTable.getTableHeader().setForeground(Color.black);

        // Add components to the panel using BoxLayout
        statsPanel.add(totalTasksLabel);
        statsPanel.add(Box.createVerticalStrut(10)); // Space between labels
        statsPanel.add(tasksCompletedTodayLabel);
        statsPanel.add(Box.createVerticalStrut(10)); // Space between labels
        statsPanel.add(scrollPane);

        // Layout for the frame
        setLayout(new BorderLayout());
        add(statsPanel, BorderLayout.CENTER);

        // Fill category table
        updateCategoryTable(tableModel);
    }

    // Get the total number of tasks
    private int getTotalTasks() {
        return tasks.getAllTasksForReading().size();
    }

    // Get the number of tasks completed today
    private int getCompletedTodayTasks() {
        int count = 0;
        LocalDate today = LocalDate.now();
        for (TaskImpl task : tasks.getAllTasksForReading()) {
            if (task.isCompleted() != null && task.isCompleted().equals("Yes") && task.getCompletDate().equals(today.toString())) {
                count++;
            }
        }
        return count;
    }

    /**
     *  Update the table with category and the number of tasks in each category
     * @param tableModel table of categories
     */
    private void updateCategoryTable(DefaultTableModel tableModel) {
        Map<String, Integer> categoryCount = new HashMap<>();

        // Iterate through all tasks and group by category
        for (TaskImpl task : tasks.getAllTasksForReading()) {
            String category = task.getCategories();
            
            // Replace empty category with "No Category"
            if (category == null || category.trim().isEmpty()) {
                category = "No Category";
            }
            
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }

        // Add rows to the table model for each category
        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            tableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }
    }

    /**
     * main of class
     * @param args for main
     */
    public static void main(String[] args) {

    }
}
