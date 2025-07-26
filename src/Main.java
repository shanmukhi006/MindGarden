import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Main {
    static int streak = 0;
    static JLabel streakLabel;
    static JLabel imageLabel;

    public static void main(String[] args) {
        loadStreak(); // Load saved streak count

        JFrame frame = new JFrame("🌱 Mind Garden");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(230, 255, 230)); // Light green

        JLabel habitLabel = new JLabel("Habit: Read 30 mins");
        habitLabel.setFont(new Font("Arial", Font.BOLD, 22));
        habitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(habitLabel);

        imageLabel = new JLabel();
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateImage(); // Show correct image
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(imageLabel);

        streakLabel = new JLabel("Streak: " + streak + " days");
        streakLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        streakLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(streakLabel);

        JButton doneButton = new JButton("✅ I did it today!");
        doneButton.setFont(new Font("Arial", Font.BOLD, 14));
        doneButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(doneButton);

        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                streak++;
                streakLabel.setText("Streak: " + streak + " days");
                updateImage();
                saveStreak();
            }
        });

        JButton resetButton = new JButton("🔁 Reset Streak");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 13));
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(resetButton);

        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    streak = 0;
                    streakLabel.setText("Streak: 0 days");
                    updateImage();
                    saveStreak();
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    static void updateImage() {
        String imagePath = "images/seed.png";  // Default

        if (streak >= 3 && streak < 5) {
            imagePath = "images/sprout.png";
        } else if (streak >= 5 && streak < 7) {
            imagePath = "images/plant.png"; // <-- renamed from leaf.png
        } else if (streak >= 7) {
            imagePath = "images/flower.png";
        }

        System.out.println("Loading image: " + imagePath); // Debug

        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaled = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            System.out.println("Image not found: " + imagePath);
        }
    }

    static void saveStreak() {
        try {
            FileWriter fw = new FileWriter("streak.txt");
            fw.write(String.valueOf(streak));
            fw.close();
        } catch (IOException e) {
            System.out.println("Failed to save streak.");
        }
    }

    static void loadStreak() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("streak.txt"));
            streak = Integer.parseInt(br.readLine());
            br.close();
        } catch (Exception e) {
            streak = 0;
        }
    }
}


