import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DigitalClockWork extends JFrame {
    private JLabel timeLabel;
    private DateTimeFormatter timeFormat;

    public DigitalClockWork() {
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.BOLD, 50));

        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

        add(timeLabel);
        setTitle("Digital Clock");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setVisible(true);

        Timer timer = new Timer(1000, e -> updateClock());
        timer.start();
    }

    private void updateClock() {

        timeLabel.setText(LocalTime.now().format(timeFormat));

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(DigitalClock::new);
    }
}
