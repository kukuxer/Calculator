
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.Color;
import java.awt.event.MouseEvent;

public class GraphicalCalculator extends JFrame {
    private JTextField textField;
    private double num1 = 0, num2 = 0, result = 0;
    private char operator = ' ';

    public GraphicalCalculator() {
        setTitle("Sheniator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

        textField = new JTextField();
        textField.setEditable(false);
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textField.setFont(new Font("Arial", Font.PLAIN, 48));
        textField.setPreferredSize(new Dimension(300, 50));
        add(textField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBackground(Color.BLACK);

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "c", "!", "√", "^"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
            button.setBackground(Color.BLACK);
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            button.setFont(new Font("Arial", Font.PLAIN, 40));
            button.setFocusPainted(false);
            button.setFocusable(false);
            addHoverEffect(button);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }

    private void addHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.BLACK);
            }
        });
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String buttonText = source.getText();

            if (buttonText.matches("[0-9]")) {
                textField.setText(textField.getText() + buttonText);
            } else if (buttonText.matches("[+\\-\\*/]")) {
                if (!textField.getText().isEmpty()) {
                    num1 = Double.parseDouble(textField.getText());
                    operator = buttonText.charAt(0);
                    textField.setText("");
                }
            } else if (buttonText.equals("!")) {
                if (!textField.getText().isEmpty()) {
                    num1 = Double.parseDouble(textField.getText());
                    result = 1;
                    for (int i = 1; i <= num1; i++) {
                        result *= i;
                    }
                    textField.setText(String.valueOf(result));
                } else {
                    textField.setText("Please enter a number before factorial (!)");
                }
            } else if (buttonText.equals("√")) {
                if (!textField.getText().isEmpty()) {
                    num1 = Double.parseDouble(textField.getText());
                    result = Math.sqrt(num1);
                    textField.setText(String.valueOf(result));
                } else {
                    textField.setText("Please enter a number before square root (√)");
                }
            } else if ("^".equals(buttonText)) {
                if (!textField.getText().isEmpty()) {
                    num1 = Double.parseDouble(textField.getText());
                    operator = buttonText.charAt(0);
                    textField.setText("");
                }
            } else if ("c".equals(buttonText)) {
                textField.setText("");
            } else if (buttonText.equals(".")) {
                if (!textField.getText().contains(".")) {
                    textField.setText(textField.getText() + buttonText);
                }
            } else if (buttonText.equals("=")) {
                if (!textField.getText().isEmpty()) {
                    num2 = Double.parseDouble(textField.getText());

                    switch (operator) {
                        case '^':
                            result = Math.pow(num1, num2);
                            break;
                        case '+':
                            result = num1 + num2;
                            break;
                        case '-':
                            result = num1 - num2;
                            break;
                        case '*':
                            result = num1 * num2;
                            break;
                        case '/':
                            if (num2 != 0) {
                                result = num1 / num2;
                            } else {
                                textField.setText("Boom!");
                                return;
                            }
                            break;
                        default:
                            // Handle unsupported operations or buttons
                            textField.setText("Nope");
                            return;
                    }

                    textField.setText(String.valueOf(result));
                }
            }

                    SwingWorker<Void, Color> colorChanger = new SwingWorker<Void, Color>() {
                        @Override
                        protected Void doInBackground() {
                            for (float hue = 0; hue <= 1.0; hue += 0.01) {
                                // Convert HSB to RGB
                                Color color = Color.getHSBColor(hue, 1.0f, 1.0f);
                                publish(color);

                                // Sleep to slow down the color change
                                try {
                                    Thread.sleep(25);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            publish(Color.BLACK);

                            return null;
                        }

                        @Override
                        protected void process(java.util.List<Color> chunks) {
                            // Update the button color
                            source.setBackground(chunks.get(chunks.size() - 1));
                        }
                    };

                    // Execute the SwingWorker
                    colorChanger.execute();
            }
        }
}
