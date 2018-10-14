import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class JSimulatorFrame extends JFrame{
    JMenuBar mainMenuBar = new JMenuBar();
    JMenu file = new JMenu("File");
    JMenuItem loadConfig = new JMenuItem("Load Configuration");
    JMenuItem exit = new JMenuItem("Exit");
    JMenu simulation = new JMenu("Simulation");
    JMenuItem showHideInformation = new JMenuItem("Show/Hide Information");
    JMenuItem run = new JMenuItem("Run");
    JMenuItem pause = new JMenuItem("Pause");
    JMenuItem stop = new JMenuItem("Stop");
    JMenu help = new JMenu("Help");
    JMenuItem about = new JMenuItem("About");
    JMenuItem userGuide = new JMenuItem("User Guide");

    JPanel statusPanel = new JPanel();
    JLabel statusLabel = new JLabel();

    JPanel simulationPanel = new JPanel();

    JPanel informationPanel = new JPanel();
    JTextField informationField = new JTextField();

    Container con;

    public JSimulatorFrame(){
        setTitle("Smart Home Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setJMenuBar(mainMenuBar);
        mainMenuBar.add(file);
        mainMenuBar.add(simulation);
        mainMenuBar.add(help);
        file.add(loadConfig);
        file.add(exit);
        simulation.add(showHideInformation);
        simulation.add(run);
        simulation.add(pause);
        simulation.add(stop);
        help.add(about);
        help.add(userGuide);

        add(statusPanel, BorderLayout.SOUTH);
        statusPanel.add(statusLabel);
        statusPanel.setBackground(Color.LIGHT_GRAY);
        statusPanel.setPreferredSize(new Dimension(1000, 30));

        add(simulationPanel, BorderLayout.CENTER);

        add(informationPanel, BorderLayout.WEST);
        informationPanel.setLayout(new GridLayout(1, 1));
        informationPanel.add(informationField);
        informationField.setEditable(false);
        informationPanel.setPreferredSize(new Dimension(300, 600));
    }

    public static void main(String[] args) {
        JSimulatorFrame simulatorFrame = new JSimulatorFrame();
        simulatorFrame.setVisible(true);
        simulatorFrame.setSize(1000, 600);
        simulatorFrame.setResizable(false); //fixed window size
    }
}
