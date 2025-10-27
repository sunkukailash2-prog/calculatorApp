import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdvancedCalculator extends JFrame implements ActionListener {

    private JTextField display;
    private String operator = "";
    private double firstNumber = 0;
    private boolean isOperatorClicked = false;

    // Buttons
    private final String[] buttonLabels = {
            "C", "←", "±", "√",
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "%", "+"
    };

    private JButton[] buttons = new JButton[buttonLabels.length];
    private JButton equalsButton;

    public AdvancedCalculator() {
        setTitle("Advanced Calculator");
        setSize(400, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Display
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 30));
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBackground(Color.DARK_GRAY);

        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 22));
            buttons[i].setForeground(Color.WHITE);

            // Color coding
            if (buttonLabels[i].matches("[/*\\-+√%]")) {
                buttons[i].setBackground(Color.ORANGE.darker());
            } else if (buttonLabels[i].matches("[C←±]")) {
                buttons[i].setBackground(Color.RED.darker());
            } else {
                buttons[i].setBackground(Color.GRAY.darker());
            }

            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }

        // Equals button
        equalsButton = new JButton("=");
        equalsButton.setFont(new Font("Arial", Font.BOLD, 22));
        equalsButton.setBackground(Color.GREEN.darker());
        equalsButton.setForeground(Color.WHITE);
        equalsButton.addActionListener(this);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.CENTER);
        southPanel.add(equalsButton, BorderLayout.SOUTH);

        add(southPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        try {
            if (command.matches("[0-9]")) {
                if (isOperatorClicked) {
                    display.setText("");
                    isOperatorClicked = false;
                }
                display.setText(display.getText() + command);
            } else if (command.equals(".")) {
                if (!display.getText().contains(".")) {
                    display.setText(display.getText() + ".");
                }
            } else if (command.equals("C")) {
                display.setText("");
                firstNumber = 0;
                operator = "";
            } else if (command.equals("←")) {
                String text = display.getText();
                if (!text.isEmpty()) {
                    display.setText(text.substring(0, text.length() - 1));
                }
            } else if (command.equals("±")) {
                if (!display.getText().isEmpty()) {
                    double value = Double.parseDouble(display.getText());
                    display.setText(String.valueOf(-value));
                }
            } else if (command.equals("√")) {
                if (!display.getText().isEmpty()) {
                    double value = Double.parseDouble(display.getText());
                    display.setText(String.valueOf(Math.sqrt(value)));
                }
            } else if (command.equals("%")) {
                if (!display.getText().isEmpty()) {
                    double value = Double.parseDouble(display.getText());
                    display.setText(String.valueOf(value / 100));
                }
            } else if (command.matches("[+\\-*/]")) {
                if (!display.getText().isEmpty()) {
                    firstNumber = Double.parseDouble(display.getText());
                    operator = command;
                    isOperatorClicked = true;
                }
            } else if (command.equals("=")) {
                if (!display.getText().isEmpty() && !operator.isEmpty()) {
                    double secondNumber = Double.parseDouble(display.getText());
                    double result = calculate(firstNumber, secondNumber, operator);
                    display.setText(String.valueOf(result));
                    operator = "";
                }
            }
        } catch (Exception ex) {
            display.setText("Error");
        }
    }

    private double calculate(double a, double b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": 
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
            default: return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdvancedCalculator::new);
    }
}
