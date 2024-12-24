import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;

public class Calculator extends JFrame implements ActionListener {
    private final JTextField display;
    private double result;
    private String operator;
    private boolean startOfNumber;

    public Calculator() {
        result = 0;
        operator = "=";
        startOfNumber = true;

        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);


        display.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                if (Character.isDigit(ch) || ch == '.') {
                    if (startOfNumber) {
                        display.setText(String.valueOf(ch));
                        startOfNumber = false;
                    } else {
                        display.setText(display.getText() + ch);
                    }
                } else if (ch == '\n') {
                    double x = Double.parseDouble(display.getText());
                    calculate(x);
                    operator = "=";
                    startOfNumber = true;
                } else {
                    processOperator(String.valueOf(ch));
                }
            }
        });

        // Create buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4));
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(this);
            buttonPanel.add(button);
        }


        setLayout(new BorderLayout());
        add(display, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        pack();
        setTitle("Simple Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        processOperator(command);
    }

    private void processOperator(String command) {
        if ("0123456789.".contains(command)) {
            if (startOfNumber) {
                display.setText(command);
                startOfNumber = false;
            } else {
                display.setText(display.getText() + command);
            }
        } else {
            if (startOfNumber) {
                if (command.equals("-")) {
                    display.setText(command);
                    startOfNumber = false;
                } else {
                    operator = command;
                }
            } else {
                double x = Double.parseDouble(display.getText());
                calculate(x);
                operator = command;
                startOfNumber = true;
            }
        }
    }

    private void calculate(double n) {
        switch (operator) {
            case "+" -> result += n;
            case "-" -> result -= n;
            case "*" -> result *= n;
            case "/" -> result /= n;
            case "=" -> result = n;
        }
        display.setText("" + result);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
        });
    }
}
