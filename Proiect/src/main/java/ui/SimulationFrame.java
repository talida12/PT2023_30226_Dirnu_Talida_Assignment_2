package ui;
import models.SelectionPolicy;
import controller.SimulationManager;

import java.awt.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
public class SimulationFrame {
    //input data panel
    private static JFrame frame = new JFrame("Assignment 2");
    private static JLabel title = new JLabel("Queue Management");
    private static JLabel timeLimitLabel = new JLabel("Time limit: ");
    private static JLabel minArrivalTimeLabel = new JLabel("Minimum arrival time:");
    private static JLabel maxArrivalTimeLabel = new JLabel("Maximum arrival time:");
    private static JLabel minProcessingTimeLabel = new JLabel("Minimum processing time:");
    private static JLabel maxProcessingTimeLabel = new JLabel("Maximum processing time:");
    private static JLabel numberOfServersLabel = new JLabel("Number of servers:");
    private static JLabel numberOfClientsLabel = new JLabel("Number of clients:");
    private static JTextField timeLimit = new JTextField(10);
    private static JTextField minArrivalTime = new JTextField(10);
    private static JTextField maxArrivalTime = new JTextField(10);
    private static JTextField minProcessingTime = new JTextField(10);
    private static JTextField maxProcessingTime = new JTextField(10);
    private static JTextField serversNumber = new JTextField(10);
    private static JTextField clientsNumber = new JTextField(10);
    private static JComboBox<SelectionPolicy> selectStrategy = new JComboBox<>();
    private static JLabel startegyLabel = new JLabel("Select a strategy:");
    private static JButton validate = new JButton("Validate Input Data");
    private static JButton start = new JButton("Start Simulation");
    private static JSplitPane mainPanel = new JSplitPane();
    private static JPanel inputDataPanel = new JPanel(){
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File("C:\\Users\\40756\\IdeaProjects\\tema2tp\\src\\main\\java\\background5.png"));
            } catch (IOException e) {System.out.println("error");}
            g.drawImage(image, 0, 0, null);
        }
    };
    private static JPanel simulationPanel = new JPanel();
    private static JPanel panel1 = new JPanel();
    private static JPanel panel2 = new JPanel();
    private static JPanel panel3 = new JPanel();
    private static JPanel panel4 = new JPanel();
    private static JPanel panel5 = new JPanel();
    private static JPanel panel6 = new JPanel();
    private static JPanel panel7 = new JPanel();
    private static JPanel panel8 = new JPanel();
    private static JPanel arrivalPanel1 = new JPanel();
    private static JPanel arrivalPanel2 = new JPanel();
    //simulation panel
    private static JLabel timeLabel = new JLabel("TIME:");
    private static JLabel timeValue = new JLabel();
    private static JLabel avgWaitLabel = new JLabel("Average waiting time:");
    private static JLabel avgWaitValue = new JLabel();
    private static JLabel avgServiceLabel = new JLabel("Average service time:");
    private static JLabel avgServiceValue = new JLabel();
    private static JLabel peakHourLabel = new JLabel("Peak hour:");
    private static JLabel peakValue = new JLabel();
    private static JPanel panel9 = new JPanel();
    private static JPanel panel10 = new JPanel();
    private static JPanel panel11 = new JPanel();
    private static JPanel panel12 = new JPanel();
    private static JPanel upperPanel = new JPanel();
    private static JPanel lowerPanel = new JPanel(new BorderLayout());
    private static JPanel lowerPanel1 = new JPanel();
    private static JPanel lowerPanel2 = new JPanel();
    private static JTextArea queuesDisplay = new JTextArea(22, 47);
    private static JButton downloadButton = new JButton("Download Results");
    private static JButton stopButton = new JButton("Stop Simulation");
    private static Thread simulationThread;

    public static void makeButtonStyle(JButton btn, Color color1, Color color2) {
        btn.setBackground(color1);
        btn.setForeground(color2);
        btn.setFont(new Font(Font.SERIF, Font.BOLD, 12));
    }
    public static void addPanelDesign(JPanel panel, Color color){
        panel.setForeground(color);
        panel.setBackground(color);
    }
    public static void makeTextStyle(JLabel text){
        text.setFont(new Font("Times New Roman", Font.BOLD, 13));
        text.setForeground(Color.WHITE);
    }
    public static void styleComponents() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 470);
        mainPanel.setLeftComponent(inputDataPanel);
        mainPanel.setRightComponent(simulationPanel);
        mainPanel.setResizeWeight(0.60);
        inputDataPanel.setMinimumSize(new Dimension(0, 0));
        queuesDisplay.setEditable(false);
        queuesDisplay.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        queuesDisplay.setLineWrap(false);
        queuesDisplay.setWrapStyleWord(false);
        title.setFont(new Font("Times New Roman", Font.BOLD, 18));
        title.setForeground(Color.BLACK);
        SimulationFrame.makeTextStyle(timeValue);
        SimulationFrame.makeTextStyle(avgWaitValue);
        SimulationFrame.makeTextStyle(avgServiceValue);
        SimulationFrame.makeTextStyle(peakValue);
        panel1.setOpaque(false);
        panel2.setOpaque(false);
        panel3.setOpaque(false);
        panel4.setOpaque(false);
        panel5.setOpaque(false);
        panel6.setOpaque(false);
        panel7.setOpaque(false);
        arrivalPanel1.setOpaque(false);
        arrivalPanel2.setOpaque(false);
        panel8.setOpaque(false);
        SimulationFrame.addPanelDesign(panel8, new Color(11, 11, 69));
        SimulationFrame.addPanelDesign(panel9, new Color(41, 41, 41));
        SimulationFrame.addPanelDesign(panel10, new Color(41, 41, 41));
        SimulationFrame.addPanelDesign(panel11, new Color(41, 41, 41));
        SimulationFrame.addPanelDesign(panel12, new Color(41, 41, 41));
        SimulationFrame.addPanelDesign(inputDataPanel, new Color(41, 41, 41));
        SimulationFrame.addPanelDesign(upperPanel, new Color(41, 41, 41));
        SimulationFrame.addPanelDesign(lowerPanel1, new Color(41, 41, 41));
        SimulationFrame.addPanelDesign(lowerPanel2, new Color(41, 41, 41));
        SimulationFrame.makeTextStyle(timeLimitLabel);
        SimulationFrame.makeTextStyle(minArrivalTimeLabel);
        SimulationFrame.makeTextStyle(maxArrivalTimeLabel);
        SimulationFrame.makeTextStyle(minProcessingTimeLabel);
        SimulationFrame.makeTextStyle(maxProcessingTimeLabel);
        SimulationFrame.makeTextStyle(numberOfClientsLabel);
        SimulationFrame.makeTextStyle(numberOfServersLabel);
        SimulationFrame.makeTextStyle(startegyLabel);
        SimulationFrame.makeTextStyle(timeLabel);
        SimulationFrame.makeTextStyle(avgServiceLabel);
        SimulationFrame.makeTextStyle(avgWaitLabel);
        SimulationFrame.makeTextStyle(peakHourLabel);
        SimulationFrame.makeButtonStyle(validate, new Color(0, 100, 0), new Color(255, 255, 255));
        SimulationFrame.makeButtonStyle(start, new Color(0, 100, 0), new Color(255, 255, 255));
        SimulationFrame.makeButtonStyle(downloadButton, new Color(0, 100, 0), new Color(255, 255, 255));
        SimulationFrame.makeButtonStyle(stopButton, new Color(0, 100, 0), new Color(255, 255, 255));
        selectStrategy.addItem(SelectionPolicy.SHORTEST_QUEUE);
        selectStrategy.addItem(SelectionPolicy.SHORTEST_TIME);
    }
    public static void layoutComponents() {
        //input data panel
        panel1.add(title);
        panel2.add(timeLimitLabel);
        panel2.add(timeLimit);
        arrivalPanel1.add(minArrivalTimeLabel);
        arrivalPanel1.add(minArrivalTime);
        arrivalPanel2.add(maxArrivalTimeLabel);
        arrivalPanel2.add(maxArrivalTime);
        panel3.add(minProcessingTimeLabel);
        panel3.add(minProcessingTime);
        panel4.add(maxProcessingTimeLabel);
        panel4.add(maxProcessingTime);
        panel5.add(numberOfServersLabel);
        panel5.add(serversNumber);
        panel6.add(numberOfClientsLabel);
        panel6.add(clientsNumber);
        panel7.add(startegyLabel);
        panel7.add(selectStrategy);
        panel8.add(validate);
        panel8.add(start);
        inputDataPanel.add(panel1);
        inputDataPanel.add(panel2);
        inputDataPanel.add(arrivalPanel1);
        inputDataPanel.add(arrivalPanel2);
        inputDataPanel.add(panel3);
        inputDataPanel.add(panel4);
        inputDataPanel.add(panel5);
        inputDataPanel.add(panel6);
        inputDataPanel.add(panel7);
        inputDataPanel.add(panel8);
        //simulation panel
        simulationPanel.setLayout(new BoxLayout(simulationPanel, BoxLayout.Y_AXIS));
        panel9.add(timeLabel);
        panel9.add(timeValue);
        panel10.add(avgWaitLabel);
        panel10.add(avgWaitValue);
        panel11.add(avgServiceLabel);
        panel11.add(avgServiceValue);
        panel12.add(peakHourLabel);
        panel12.add(peakValue);
        upperPanel.add(panel9);
        upperPanel.add(panel10);
        upperPanel.add(panel11);
        upperPanel.add(panel12);
        JScrollPane scrollPane = new JScrollPane(queuesDisplay);
        scrollPane.setPreferredSize(new Dimension(560, 310));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        lowerPanel1.add(scrollPane);
        lowerPanel2.add(downloadButton);
        lowerPanel2.add(stopButton);

        JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, upperPanel, lowerPanel);
        splitPane1.setDividerLocation(0.3);
        splitPane1.setBackground(Color.GRAY);
        simulationPanel.add(splitPane1);

        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane2.setResizeWeight(0.99);
        splitPane2.setTopComponent(lowerPanel1);
        splitPane2.setBottomComponent(lowerPanel2);
        lowerPanel.add(splitPane2, BorderLayout.CENTER);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }
    public static void addButtonListeners() {
        SimulationFrame.validateButtonListener();
        SimulationFrame.startButtonListener();
        SimulationFrame.stopButtonListener();
        SimulationFrame.downloadButtonListener();
    }

    public static boolean validateData() {
        try {
            if ( timeLimit.getText().isEmpty()|| minArrivalTime.getText().isEmpty() ||
                    maxArrivalTime.getText().isEmpty() || minProcessingTime.getText().isEmpty() ||
                    maxProcessingTime.getText().isEmpty() || serversNumber.getText().isEmpty() ||
                    clientsNumber.getText().isEmpty() )
                JOptionPane.showMessageDialog(null, "You must insert all the values required!");
            else if ( Integer.parseInt(minProcessingTime.getText()) > Integer.parseInt(maxProcessingTime.getText()))
                JOptionPane.showMessageDialog(null, "The maximum processing time value " +
                        "must be greater or equal to the minimum processing time value!");
            else if ( Integer.parseInt(minArrivalTime.getText()) > Integer.parseInt(maxArrivalTime.getText()))
                JOptionPane.showMessageDialog(null, "The maximum arrival time value " +
                        "must be greater or equal to the minimum arrival time value!");
            else {
                Integer.parseInt(timeLimit.getText());
                Integer.parseInt(serversNumber.getText());
                Integer.parseInt(clientsNumber.getText());
                return true;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "The input values must be integer values!");
        }
        return false;
    }

    public static void validateButtonListener() {
        validate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateData())
                    JOptionPane.showMessageDialog(null, "All the data is valid!");
            }
        });
    }

    public static void startButtonListener() {
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateData()) {
                    SimulationManager gen = new SimulationManager(getTimeLimit(), getMinimumArrivalTime(), getMaximumArrivalTime(), getMaximumProcessingTime(),
                            getMinimumProcessingTime(), getNumberOfServers(), getNumberOfClients(),
                            getStrategy());
                    simulationThread = new Thread(gen);
                    simulationThread.start();
                }
            }
        });
    }
    public static void stopButtonListener() {
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulationThread.interrupt();
            }
        });
    }

    public static void downloadButtonListener() {
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = "snapshots/snapshot" + LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy.hh.mm.ss")) + ".txt";
                File file = new File(filename);
                try (PrintWriter pw = new PrintWriter(file)) {
                    pw.println("Time: " + getTime().getText());
                    pw.println("Average waiting time: " + getAvgWaitValue().getText());
                    pw.println("Average service time: " + getAvgServiceValue().getText());
                    pw.println("Peak hour: " + getPeakValue().getText());
                    pw.println();
                    pw.println(queuesDisplay.getText());
                } catch (IOException ex) {
                    System.err.println("Error writing to file: " + ex.getMessage());
                }
            }
        });
    }

    public static int getTimeLimit() {
        return Integer.parseInt(timeLimit.getText());
    }
    public static JLabel getAvgServiceValue() { return avgServiceValue; }
    public static JLabel getAvgWaitValue() { return avgWaitValue; }
    public static  int getMinimumArrivalTime() { return Integer.parseInt(minArrivalTime.getText());}
    public static int getMaximumArrivalTime() { return Integer.parseInt(maxArrivalTime.getText());}
    public static int getMinimumProcessingTime() {
        return Integer.parseInt(minProcessingTime.getText());
    }
    public static int getMaximumProcessingTime() {
        return Integer.parseInt(maxProcessingTime.getText());
    }
    public static int getNumberOfServers() {
        return Integer.parseInt(serversNumber.getText());
    }
    public static  int getNumberOfClients() {
        return Integer.parseInt(clientsNumber.getText());
    }
    public static SelectionPolicy getStrategy() {
        return (SelectionPolicy) selectStrategy.getSelectedItem();
    }
    public static JLabel getTime() {
        return timeValue;
    }
    public static JLabel getPeakValue() { return peakValue; }
    public static JTextArea getQueuesDisplay() { return queuesDisplay; }
    public static void main(String[] args) {
        SimulationFrame.styleComponents();
        SimulationFrame.layoutComponents();
        SimulationFrame.addButtonListeners();
    }
}
