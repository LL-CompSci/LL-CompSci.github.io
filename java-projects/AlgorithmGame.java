import javax.swing.*;

public class AlgorithmGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sort It Out");

        frame.setLayout(new java.awt.FlowLayout());

        JLabel title = new JLabel("Sort It Out");
        frame.add(title);

        JTextField numberInput = new JTextField(20);
        frame.add(numberInput);

        JButton startButton = new JButton("Start Sorting");
        frame.add(startButton);
        
        startButton.addActionListener(e -> {
            String input = numberInput.getText();

            JOptionPane.showMessageDialog(frame, "You entered: " + input);
        });
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}