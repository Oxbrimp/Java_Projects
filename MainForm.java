import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainForm {
    private JPanel mainPanel;
    private JButton addButton;
    private JTextField xField;
    private JTextField yField;
    private JTextArea displayArea;
    private JButton clusterButton;
    private JTextField centroidXField;
    private JTextField centroidYField;
    private JButton addCentroidButton;

    private List<double[]> dataPoints = new ArrayList<>();
    private List<double[]> initialCentroids = new ArrayList<>();

    public MainForm() {
        mainPanel = new JPanel(new GridBagLayout());
        addButton = new JButton("Add Data Point");
        xField = new JTextField(10);
        yField = new JTextField(10);
        centroidXField = new JTextField(10);
        centroidYField = new JTextField(10);
        displayArea = new JTextArea(10, 30);
        clusterButton = new JButton("Cluster");
        addCentroidButton = new JButton("Add Centroid");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        mainPanel.add(new JLabel("X:"), gbc);

        gbc.gridx = 1;
        mainPanel.add(xField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Y:"), gbc);

        gbc.gridx = 1;
        mainPanel.add(yField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(addButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Centroid X:"), gbc);

        gbc.gridx = 1;
        mainPanel.add(centroidXField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("Centroid Y:"), gbc);

        gbc.gridx = 1;
        mainPanel.add(centroidYField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(addCentroidButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(clusterButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(new JScrollPane(displayArea), gbc);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double x = Double.parseDouble(xField.getText());
                double y = Double.parseDouble(yField.getText());
                dataPoints.add(new double[]{x, y});
                displayArea.append("Added: (" + x + ", " + y + ")\n");
                xField.setText("");
                yField.setText("");
            }
        });

        addCentroidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double x = Double.parseDouble(centroidXField.getText());
                double y = Double.parseDouble(centroidYField.getText());
                initialCentroids.add(new double[]{x, y});
                displayArea.append("Added Centroid: (" + x + ", " + y + ")\n");
                centroidXField.setText("");
                centroidYField.setText("");
            }
        });

        clusterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KMeans kMeans = new KMeans(dataPoints, initialCentroids);
                kMeans.cluster();
                displayArea.append("\nClusters:\n");
                List<List<double[]>> clusters = kMeans.getClusters();
                for (int i = 0; i < clusters.size(); i++) {
                    displayArea.append("Cluster " + (i + 1) + ":\n");
                    for (double[] point : clusters.get(i)) {
                        displayArea.append("(" + point[0] + ", " + point[1] + ")\n");
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
