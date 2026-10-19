
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmGame {

    private JFrame frame;
    private JTextField numberInput;
    private JComboBox<String> speedChoice;
    private JLabel statusLabel;
    private JPanel numberPanel;
    private JTextField guessInput;
    private JButton nextButton;

    private int[] numbers;
    private List<String> steps;
    private int currentStep;
    private int animationDelay;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AlgorithmGame().createWindow());
    }

    private void createWindow() {
        frame = new JFrame("Sort It Out");
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Sort It Out", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        frame.add(title, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter numbers separated by commas:"));

        numberInput = new JTextField("5, 2, 8, 1", 18);
        inputPanel.add(numberInput);

        JButton startButton = new JButton("Start Sorting");
        inputPanel.add(startButton);

        mainPanel.add(inputPanel);

        JPanel settingsPanel = new JPanel();
        settingsPanel.add(new JLabel("Animation speed:"));

        String[] speeds = {"Slow", "Medium", "Fast"};
        speedChoice = new JComboBox<>(speeds);
        settingsPanel.add(speedChoice);

        mainPanel.add(settingsPanel);

        numberPanel = new JPanel();
        numberPanel.setBorder(BorderFactory.createTitledBorder("Array"));
        mainPanel.add(numberPanel);

        statusLabel = new JLabel(
                "Enter numbers and click Start Sorting.",
                SwingConstants.CENTER
        );
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(statusLabel);

        JPanel guessPanel = new JPanel();
        guessPanel.add(new JLabel("Your prediction:"));

        guessInput = new JTextField(15);
        guessInput.setToolTipText(
                "Example: 5 and 2"
        );
        guessPanel.add(guessInput);

        nextButton = new JButton("Check Guess");
        nextButton.setEnabled(false);
        guessPanel.add(nextButton);

        mainPanel.add(guessPanel);

        frame.add(mainPanel, BorderLayout.CENTER);

        startButton.addActionListener(e -> startGame());
        nextButton.addActionListener(e -> checkGuess());

        frame.setVisible(true);
    }

    private void startGame() {
        try {
            String input = numberInput.getText().trim();
            String[] parts = input.split(",");

            if (parts.length < 2) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Please enter at least two numbers."
                );
                return;
            }

            if (parts.length > 15) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Please enter no more than 15 numbers."
                );
                return;
            }

            numbers = new int[parts.length];

            for (int i = 0; i < parts.length; i++) {
                numbers[i] = Integer.parseInt(parts[i].trim());
            }

            String speed = (String) speedChoice.getSelectedItem();

            if ("Slow".equals(speed)) {
                animationDelay = 1800;
            } else if ("Fast".equals(speed)) {
                animationDelay = 500;
            } else {
                animationDelay = 1000;
            }

            steps = new ArrayList<>();
            currentStep = 0;

            createSortingSteps();

            nextButton.setEnabled(true);
            guessInput.setEnabled(true);

            displayNumbers(numbers, -1, -1);

            statusLabel.setText(
                    "Predict which two values Selection Sort will compare."
            );

            guessInput.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    frame,
                    "Invalid input! Enter whole numbers separated by commas."
            );
        }
    }

    private void createSortingSteps() {
        int[] copy = numbers.clone();

        for (int i = 0; i < copy.length - 1; i++) {

            int minimumIndex = i;

            for (int j = i + 1; j < copy.length; j++) {

                steps.add(
                        "COMPARE:" + i + ":" + minimumIndex + ":" + j
                );

                if (copy[j] < copy[minimumIndex]) {
                    minimumIndex = j;
                }
            }

            if (minimumIndex != i) {
                steps.add(
                        "SWAP:" + i + ":" + minimumIndex
                );

                int temporary = copy[i];
                copy[i] = copy[minimumIndex];
                copy[minimumIndex] = temporary;
            }
        }
    }

    private void checkGuess() {
        if (currentStep >= steps.size()) {
            showFinalResult();
            return;
        }

        String[] step = steps.get(currentStep).split(":");
        String prediction = guessInput.getText().trim();

        int firstIndex;
        int secondIndex;

        if (step[0].equals("COMPARE")) {
            firstIndex = Integer.parseInt(step[2]);
            secondIndex = Integer.parseInt(step[3]);
        } else {
            firstIndex = Integer.parseInt(step[1]);
            secondIndex = Integer.parseInt(step[2]);
        }

        int firstValue = numbers[firstIndex];
        int secondValue = numbers[secondIndex];

        String correctAnswer = firstValue + " and " + secondValue;

        if (prediction.equals(correctAnswer)
                || prediction.equals(secondValue + " and " + firstValue)) {

            statusLabel.setText("Correct prediction!");
        } else {
            statusLabel.setText(
                    "Not quite! The correct values are "
                    + correctAnswer + "."
            );
        }

        if (step[0].equals("COMPARE")) {
            displayNumbers(numbers, firstIndex, secondIndex);

            statusLabel.setText(
                    statusLabel.getText()
                    + " Comparing " + firstValue + " and " + secondValue + "."
            );
        } else {
            statusLabel.setText(
                    statusLabel.getText()
                    + " These values are being swapped."
            );

            displayNumbers(numbers, firstIndex, secondIndex);
        }

        currentStep++;

        if (currentStep < steps.size()) {
            nextButton.setText("Next Step");
            guessInput.setText("");
        } else {
            nextButton.setText("Show Result");
        }

        if (currentStep == steps.size()) {
            statusLabel.setText(
                    statusLabel.getText()
                    + " Sorting steps completed. Click Show Result."
            );
        }
    }

    private void displayNumbers(int[] values, int first, int second) {
        numberPanel.removeAll();

        for (int i = 0; i < values.length; i++) {
            JLabel number = new JLabel(
                    String.valueOf(values[i]),
                    SwingConstants.CENTER
            );

            number.setPreferredSize(new Dimension(45, 45));
            number.setOpaque(true);
            number.setBackground(Color.LIGHT_GRAY);

            if (i == first || i == second) {
                number.setBackground(Color.YELLOW);
            }

            number.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            numberPanel.add(number);
        }

        numberPanel.revalidate();
        numberPanel.repaint();
    }

    private void showFinalResult() {
        int[] sorted = numbers.clone();

        for (int i = 0; i < sorted.length - 1; i++) {
            int minimumIndex = i;

            for (int j = i + 1; j < sorted.length; j++) {
                if (sorted[j] < sorted[minimumIndex]) {
                    minimumIndex = j;
                }
            }

            int temporary = sorted[i];
            sorted[i] = sorted[minimumIndex];
            sorted[minimumIndex] = temporary;
        }

        displayNumbers(sorted, -1, -1);

        statusLabel.setText(
                "Finished! Your sorted array is "
                + java.util.Arrays.toString(sorted)
        );

        nextButton.setEnabled(false);
        guessInput.setEnabled(false);
        nextButton.setText("Completed");
    }
}
