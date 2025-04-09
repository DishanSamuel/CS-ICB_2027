import javax.swing.; import javax.swing.table.DefaultTableModel; import java.awt.; import java.awt.event.; import java.io.; import java.util.Vector;

public class ExpenseTracker extends JFrame { private JTextField amountField, categoryField; private DefaultTableModel tableModel; private JTable table; private JLabel totalLabel; private double total = 0;

public ExpenseTracker() {
    setTitle("Personal Expense Tracker");
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new GridLayout(3, 2, 10, 10));

    inputPanel.add(new JLabel("Amount:"));
    amountField = new JTextField();
    inputPanel.add(amountField);

    inputPanel.add(new JLabel("Category:"));
    categoryField = new JTextField();
    inputPanel.add(categoryField);

    JButton addButton = new JButton("Add Expense");
    inputPanel.add(addButton);

    JButton saveButton = new JButton("Save to File");
    inputPanel.add(saveButton);

    tableModel = new DefaultTableModel(new Object[]{"Amount", "Category"}, 0);
    table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);

    totalLabel = new JLabel("Total: Rs. 0.0");
    totalLabel.setFont(new Font("Arial", Font.BOLD, 14));

    add(inputPanel, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
    add(totalLabel, BorderLayout.SOUTH);

    addButton.addActionListener(e -> addExpense());
    saveButton.addActionListener(e -> saveToFile());
}

private void addExpense() {
    try {
        double amount = Double.parseDouble(amountField.getText());
        String category = categoryField.getText();
        if (category.isEmpty()) throw new Exception("Empty category");

        tableModel.addRow(new Object[]{amount, category});
        total += amount;
        totalLabel.setText("Total: Rs. " + total);

        amountField.setText("");
        categoryField.setText("");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid amount and category.");
    }
}

private void saveToFile() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("expenses.csv"))) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Vector<?> row = tableModel.getDataVector().elementAt(i);
            writer.write(row.get(0) + "," + row.get(1));
            writer.newLine();
        }
        writer.write("Total,Rs. " + total);
        JOptionPane.showMessageDialog(this, "Expenses saved to expenses.csv");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error saving file.");
    }
}

public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new ExpenseTracker().setVisible(true));
}

}

