import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JSimulatorFrame extends JFrame implements ActionListener{
    JMenuBar mainMenuBar = new JMenuBar();
    JMenu file = new JMenu("File");
    JMenuItem loadConfig = new JMenuItem("Load Configuration");
    JMenuItem exit = new JMenuItem("Exit");
    JMenu simulation = new JMenu("Simulation");
    JMenuItem showHideInformation = new JMenuItem("Show/Hide Information");
    JMenuItem setSpeed = new JMenuItem("Set Speed");
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
    JLabel informationTitle = new JLabel("Information Panel");
    JTextField lightField = new JTextField();
    JTextField temperatureField = new JTextField();
    JTextField timeField = new JTextField();
    JTextField electricityTotalField = new JTextField();
    JTextField waterTotalField = new JTextField();
    JTextField totalField = new JTextField();
    JTextField eventField = new JTextField();

    Font titleFont = new Font("Arial", Font.BOLD, 22);
    Font informationFont = new Font("Arial", Font.PLAIN, 16);
    Font deviceFont = new Font("Arial", Font.BOLD, 16);

    final Color LIGHT_YELLOW = new Color(255, 255, 102);
    final Color SUNLIGHT = new Color(255, 255, 255);
    int sunlightCount = 255;

    int delay = 100;
    Timer timer = new Timer(delay, this::timerOneMethod); //create new timer and assign it a new action event handler
    boolean isPaused = false;

    int currentTime = 0;
    final int START_TIME = 0; //5:00am
    final int END_TIME = 1439; //minutes in 23 hours and 59 minutes (4:59am the next day)
    final int MIN_SUNLIGHT = 0;
    final int MAX_SUNLIGHT = 100;
    int currentSunlight = 0;
    int currentTemperature = 0;
    int hours = 5; //starts at 5:00am
    int minutes = 0;
    int halfDays = 0;
    final int HEATING_RATE = 16;
    final int COOLING_RATE = 64;
    int eventTemperature = 0;
    final int MAX_TEMPERATURE = 35;
    final int MIN_TEMPERATURE = 10;

    Scanner scanner = null;
    java.util.List<Fixture> fixList = new ArrayList<>();
    java.util.List<Appliance> appList = new ArrayList<>();
    java.util.List<Room> roomList = new ArrayList<>();
    String roomName = "";
    Room[] rooms = new Room[roomList.size()];

    //create House object
    House house = new House();

    boolean isCorrectFile = false;
    boolean hasSelectedFile = false;

    public JSimulatorFrame(){
        setTitle("Smart Home Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setJMenuBar(mainMenuBar);
        mainMenuBar.add(file);
        mainMenuBar.add(simulation);
        mainMenuBar.add(help);
        file.add(loadConfig);
        loadConfig.addActionListener(this);
        file.add(exit);
        exit.addActionListener(this);
        simulation.add(showHideInformation);
        showHideInformation.addActionListener(this);
        simulation.add(setSpeed);
        setSpeed.addActionListener(this);
        simulation.add(run);
        run.addActionListener(this);
        simulation.add(pause);
        pause.addActionListener(this);
        simulation.add(stop);
        stop.addActionListener(this);
        help.add(about);
        about.addActionListener(this);
        help.add(userGuide);
        userGuide.addActionListener(this);

        add(statusPanel, BorderLayout.SOUTH);
        statusPanel.add(statusLabel);
        statusPanel.setBackground(Color.LIGHT_GRAY);
        statusPanel.setPreferredSize(new Dimension(1000, 30));

        add(simulationPanel, BorderLayout.CENTER);

        add(informationPanel, BorderLayout.WEST);
        informationPanel.setLayout(new GridLayout(8, 1));
        informationPanel.add(informationTitle);
        informationPanel.add(eventField);
        informationPanel.add(timeField);
        informationPanel.add(temperatureField);
        informationPanel.add(lightField);
        informationPanel.add(electricityTotalField);
        informationPanel.add(waterTotalField);
        informationPanel.add(totalField);
        informationPanel.setBackground(Color.WHITE);
        informationPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        informationPanel.setPreferredSize(new Dimension(300, 600));
        informationPanel.setVisible(true);
        informationTitle.setFont(titleFont);
        informationTitle.setHorizontalAlignment(JLabel.CENTER);

        timeField.setFont(informationFont);
        timeField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        timeField.setHorizontalAlignment(JTextField.CENTER);
        timeField.setEditable(false);
        timeField.setBackground(Color.WHITE);

        temperatureField.setFont(informationFont);
        temperatureField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        temperatureField.setHorizontalAlignment(JTextField.CENTER);
        temperatureField.setEditable(false);
        temperatureField.setBackground(Color.WHITE);

        lightField.setFont(informationFont);
        lightField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        lightField.setHorizontalAlignment(JTextField.CENTER);
        lightField.setEditable(false);
        lightField.setBackground(Color.WHITE);

        electricityTotalField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        electricityTotalField.setFont(informationFont);
        electricityTotalField.setHorizontalAlignment(JTextField.CENTER);
        electricityTotalField.setEditable(false);
        electricityTotalField.setBackground(Color.WHITE);

        waterTotalField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        waterTotalField.setFont(informationFont);
        waterTotalField.setHorizontalAlignment(JTextField.CENTER);
        waterTotalField.setEditable(false);
        waterTotalField.setBackground(Color.WHITE);

        eventField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        eventField.setFont(informationFont);
        eventField.setHorizontalAlignment(JTextField.CENTER);
        eventField.setEditable(false);
        eventField.setBackground(Color.WHITE);

        totalField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        totalField.setFont(informationFont);
        totalField.setHorizontalAlignment(JTextField.CENTER);
        totalField.setEditable(false);
        totalField.setBackground(Color.WHITE);
    }

    public void actionPerformed(ActionEvent event){
        Object source = event.getSource();
        if(source == exit){
            System.out.println("Exiting program"); //testing exit button
            System.exit(0);
        }
        else if(source == loadConfig){
            System.out.println("Loading file"); //testing load file button
            if(!hasSelectedFile){
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(this);
                if(result == JFileChooser.APPROVE_OPTION){
                    File selectedFile = fileChooser.getSelectedFile();
                    statusLabel.setText(selectedFile + " loaded successfully");
                    statusPanel.setBackground(Color.GREEN);
                    //load all appliance, fixtures, rooms and properties from a configuration file
                    try {
                        if(selectedFile.getName().equals("configurations.txt")){
                            isCorrectFile = true;
                            hasSelectedFile = true;
                            System.out.println("File Loaded"); //testing file loading
                            scanner = new Scanner(selectedFile);
                            while(scanner.hasNextLine()){
                                String total[] = scanner.nextLine().split("/");
                                for (int i = 0; i < total.length; i++) {
                                    if(i == 0){
                                        String data[] = total[i].split(";"); //separate individual fixtures
                                        for (String aData : data) {
                                            String individual[] = aData.split(","); //separate individual fixture values
                                            fixList.add(new Fixture(individual[0], Integer.parseInt(individual[1]),
                                                    Integer.parseInt(individual[2]), Integer.parseInt(individual[3]),
                                                    Integer.parseInt(individual[4]), Double.parseDouble(individual[5]),
                                                    Double.parseDouble(individual[6])));
                                        }
                                    }
                                    else{
                                        if(i == 1){
                                            String data[] = total[i].split(";"); //separate individual appliances
                                            for (String aData : data) {
                                                String individual[] = aData.split(","); //separate individual appliance values
                                                appList.add(new Appliance(individual[0], Integer.valueOf(individual[1]),
                                                        Integer.valueOf(individual[2]), Integer.valueOf(individual[3]),
                                                        Integer.valueOf(individual[4]), Double.valueOf(individual[5]),
                                                        Double.valueOf(individual[6])));
                                            }
                                        }
                                        else{
                                            roomName = total[2];
                                        }
                                    }
                                }
                                Fixture[] tempFixtures = new Fixture[fixList.size()];
                                tempFixtures = fixList.toArray(tempFixtures);
                                Appliance[] tempAppliances = new Appliance[appList.size()];
                                tempAppliances = appList.toArray(tempAppliances);
                                roomList.add(new Room(roomName, tempFixtures, tempAppliances));
                                fixList = new ArrayList<>();
                                appList = new ArrayList<>();
                            }
                            scanner.close();
                            rooms = roomList.toArray(rooms);
                            //create simulation window components dynamically
                            simulationPanel.setLayout(new GridLayout(2, 3));
                            JPanel[] roomPanels = new JPanel[rooms.length];
                            JLabel[] roomLabels = new JLabel[rooms.length];
                            for (int i = 0; i < rooms.length; i++) {
                                System.out.println("Creating Room Panels"); //testing room panel creation
                                roomPanels[i] = new JPanel();
                                simulationPanel.add(roomPanels[i]);
                                roomPanels[i].setLayout(new GridLayout(7, 1));
                                roomLabels[i] = new JLabel();
                                System.out.println("Creating Room Labels"); //testing room label creation
                                roomLabels[i].setFont(titleFont);
                                roomPanels[i].add(roomLabels[i]);
                                roomLabels[i].setHorizontalAlignment(JLabel.CENTER);
                                roomLabels[i].setText(rooms[i].getRoomName());
                                roomPanels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                                Fixture[] fixtures = rooms[i].getRoomFixtures();
                                Appliance[] appliances = rooms[i].getRoomAppliances();
                                JLabel[] fixtureLabels = new JLabel[fixtures.length];
                                JLabel[] applianceLabels = new JLabel[appliances.length];
                                for (int j = 0; j < fixtures.length; j++) {
                                    fixtureLabels[j] = new JLabel("" + fixtures[j].getName());
                                    fixtureLabels[j].setHorizontalAlignment(JLabel.CENTER);
                                    fixtureLabels[j].setFont(deviceFont);
                                    roomPanels[i].add(fixtureLabels[j]);
                                }
                                for (int j = 0; j < appliances.length; j++) {
                                    applianceLabels[j] = new JLabel("" + appliances[j].getName());
                                    applianceLabels[j].setHorizontalAlignment(JLabel.CENTER);
                                    applianceLabels[j].setFont(deviceFont);
                                    roomPanels[i].add(applianceLabels[j]);
                                }
                            }
                        }
                        else{
                            isCorrectFile = false;
                            statusLabel.setText("You have not selected a valid configuration file");
                            statusPanel.setBackground(Color.RED);
                            System.out.println("Wrong file selected"); //testing file selection
                        }
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            else{
                statusLabel.setText("Unable to load file because you have already loaded one");
                statusPanel.setBackground(Color.RED);
                System.out.println("File already loaded"); //testing file already loaded
            }
        }
        else if(source == showHideInformation){
            if(informationPanel.isVisible()){
                informationPanel.setVisible(false);
                System.out.println("Hide Panel"); //testing hiding
            }
            else{
                informationPanel.setVisible(true);
                System.out.println("Show Panel"); //testing showing
            }
        }
        else if(source == run){
            System.out.println("Running"); //testing running button
            isPaused = false;
            if (isCorrectFile){
                statusLabel.setText("");
                statusPanel.setBackground(Color.LIGHT_GRAY);
                timer.start();
            }
        }
        else if(source == pause){
            System.out.println("Paused"); //testing pause button
            isPaused = true;
            statusLabel.setText("Simulation is Paused");
        }
        else if(source == stop){
            System.out.println("Stopped"); //testing stop button
            timer.stop();
            statusLabel.setText("Simulation has Stopped");
            eventField.setBackground(UIManager.getColor("Panel.background"));
            //reset simulation values to initial
            currentTime = 0;
            currentSunlight = 0;
            currentTemperature = 0;
            hours = 5;
            minutes = 0;
            halfDays = 0;
            eventTemperature = 0;
            house.setSunlight(currentSunlight);
            house.setTime(currentTime);
            house.setTemperature(currentTemperature);
            house.setElectricityTotal(0);
            house.setWaterTotal(0);
            house.setTotal(0);
            house.resetRoomValues(rooms);
        }
        else if (source == about){
            System.out.println("About"); //testing about
            JOptionPane.showMessageDialog(null, "Smart Home Simulator v1.0.0 by Thomas Napier");
        }
        else if (source == userGuide){
            System.out.println("User guide"); //testing user guide
            JOptionPane.showMessageDialog(null, "----- User Guide -----" +
                    "\n\n\b LOADING CONFIGURATIONS: " +
                    "\n To start the simulation, please go to File > Load Configuration and browse for configurations.txt that came with the software" +
                    "\n Once loaded, it should state that it has been successful, and a visual of the home's rooms should appear." +
                    "\n\n\b STARTING THE SIMULATION:" +
                    "\n To begin the simulation, please go to Simulation > Run" +
                    "\n Optionally you may select Show/Hide Information under Simulation to display real time details about the home" +
                    "\n\n\b DURING THE SIMULATION:" +
                    "\n During the simulation, you may Pause or Stop the simulation using the corresponding buttons underneath Simulation" +
                    "\n\n\b RESTART THE SIMULATION:" +
                    "\n To restart the simulation, under Simulation, where Stop was previously should now be Restart Simulation. Pressing this will not do anything until you " +
                    "also press Run to begin the new simulation" +
                    "\n\n\b EXIT THE SOFTWARE: " +
                    "\n To exit the program, simply hit the x in the top right of the window or go to File > Exit. This will not alter the house's configuration settings");
        }
        else if (source == setSpeed){
            String userInput = JOptionPane.showInputDialog(null, "Set Simulator Speed" +
                    "\n Fast - 10" +
                    "\n Medium - 100" +
                    "\n Slow - 1000");
            if(userInput == null){ //if user hits cancel
                System.out.println("Cancel was hit - Speed remains the same"); //testing cancel button on speed setter
            }
            else{
                delay = Integer.parseInt(userInput);
                System.out.println("Speed set to " + delay);
                timer.setDelay(delay);
            }
        }
        invalidate();
        validate();
    }

    public void timerOneMethod(ActionEvent e) {
        if (timer.isRunning()) {
            if (!isPaused) {
                if (currentTime < END_TIME) {
                    if (currentTime >= START_TIME && currentTime <= 120) { //reach max sunlight at 7:00am
                        if (currentSunlight < MAX_SUNLIGHT) {
                            sunlightCount-=2;
                            house.setSunlight(currentSunlight);
                            for (Room room : rooms) {
                                room.setSunlight(currentSunlight);
                            }
                            currentSunlight++;
                        }
                    }
                    if (currentTime > 720 && currentTime < 840) { //reach min sunlight at 7pm
                        if (currentSunlight > MIN_SUNLIGHT) {
                            sunlightCount+=2;
                            house.setSunlight(currentSunlight);
                            for (Room room : rooms) {
                                room.setSunlight(currentSunlight);
                            }
                            currentSunlight--;
                        }
                    }
                    if (eventTemperature == 0) { //if no event has triggered
                        generateEvent();
                    }
                    if (currentTime >= START_TIME && currentTime <= 420) { //reach max temperature at 12
                        currentTemperature = (MIN_TEMPERATURE + (currentTime / HEATING_RATE));
                        currentTemperature += eventTemperature;
                    }
                    if (currentTime >= 450 && currentTime < END_TIME) { //reach min temperature at the start of the next day
                        currentTemperature = (MAX_TEMPERATURE - (currentTime / COOLING_RATE));
                        currentTemperature += eventTemperature;
                    }
                    String lightOutput = "Light: " + currentSunlight + "%";
                    lightField.setBackground(new Color(255, 255, sunlightCount));
                    String temperatureOutput = "Temp: " + currentTemperature + "°C";
                    if (currentTime % 60 == 0 && currentTime != START_TIME) {
                        if (hours == 11) {
                            halfDays++;
                        }
                        if (hours == 12) {
                            hours = 1;
                            minutes = 0;
                        } else {
                            hours++;
                            minutes = 0;
                        }
                    } else {
                        minutes++;
                    }
                    String timeOutput = "";
                    if (halfDays == 0 || halfDays == 2) {
                        if (minutes < 10) {
                            timeOutput = "Time: " + hours + ":0" + minutes + "am";
                        } else {
                            timeOutput = "Time: " + hours + ":" + minutes + "am\n";
                        }
                    } else {
                        if (minutes < 10) {
                            timeOutput += "Time: " + hours + ":0" + minutes + "pm\n";
                        } else {
                            timeOutput += "Time: " + hours + ":" + minutes + "pm\n";
                        }
                    }
                    lightField.setText("" + lightOutput);
                    temperatureField.setText("" + temperatureOutput);
                    timeField.setText("" + timeOutput);
                    house.setTime(currentTime);
                    house.setTemperature(currentTemperature);
                    for (Room room : rooms) {
                        room.setTime(currentTime);
                        room.setTemperature(currentTemperature);
                        room.setRoomDevices();
                    }
                    currentTime++;
                    JPanel[] roomPanels = new JPanel[rooms.length];
                    JLabel[] roomLabels = new JLabel[rooms.length];
                    simulationPanel.removeAll();
                    for (int i = 0; i < rooms.length; i++) {
                        roomPanels[i] = new JPanel();
                        simulationPanel.add(roomPanels[i]);
                        roomPanels[i].setLayout(new GridLayout(7, 1));
                        roomLabels[i] = new JLabel();
                        roomLabels[i].setFont(titleFont);
                        roomPanels[i].add(roomLabels[i]);
                        roomLabels[i].setHorizontalAlignment(JLabel.CENTER);
                        roomLabels[i].setText(rooms[i].getRoomName());
                        roomPanels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        Fixture[] fixtures = rooms[i].getRoomFixtures();
                        Appliance[] appliances = rooms[i].getRoomAppliances();
                        JLabel[] fixtureLabels = new JLabel[fixtures.length];
                        JLabel[] applianceLabels = new JLabel[appliances.length];
                        for (int j = 0; j < fixtures.length; j++) {
                            fixtureLabels[j] = new JLabel("" + fixtures[j].getName());
                            fixtureLabels[j].setHorizontalAlignment(JLabel.CENTER);
                            fixtureLabels[j].setFont(deviceFont);
                            roomPanels[i].add(fixtureLabels[j]);
                            if(fixtures[j].getState()){
                                if(fixtures[j].getName().equals("Room Light")){
                                    roomPanels[i].setBackground(LIGHT_YELLOW);
                                }
                                fixtureLabels[j].setForeground(Color.GREEN);
                            }
                            else{
                                fixtureLabels[j].setForeground(Color.RED);
                            }
                        }
                        for (int j = 0; j < appliances.length; j++) {
                            applianceLabels[j] = new JLabel("" + appliances[j].getName());
                            applianceLabels[j].setHorizontalAlignment(JLabel.CENTER);
                            applianceLabels[j].setFont(deviceFont);
                            roomPanels[i].add(applianceLabels[j]);
                            if(appliances[j].getState()){
                                applianceLabels[j].setForeground(Color.GREEN);
                            }
                            else{
                                applianceLabels[j].setForeground(Color.RED);
                            }
                        }
                    }
                    house.displayStates(rooms);
                    waterTotalField.setText("Water total: $" + (float) house.getWaterTotal());
                    electricityTotalField.setText("Electricity total: $" + (float) house.getElectricityTotal());
                    totalField.setText("Total cost: $" + (float) house.getTotal());
                }
                else{
                    statusLabel.setText("Simulation has ended - Press Restart Simulation and then Run");
                    stop.setText("Restart Simulation");
                    timer.stop();
                    timer.restart();
                }
            }
        }
    }

    private void generateEvent() {
        int randomNumber;
        randomNumber = (int) (Math.random() * ((1000 - 1)) + 1) + 1;
        int temperature;
        String eventString = "";
        if (randomNumber >= 500) { //50% chance
            if(randomNumber == 500){
                eventString = "It has started to rain";
                temperature = -3;
                eventTemperature = temperature;
                eventField.setBackground(Color.LIGHT_GRAY);
            }
            else if(randomNumber == 600){
                eventString = "It has started to hail";
                temperature = -7;
                eventTemperature = temperature;
                eventField.setBackground(Color.GRAY);
            }
            else if(randomNumber == 700){
                eventString = "A lightning storm has begun";
                temperature = -5;
                eventTemperature = temperature;
                eventField.setBackground(Color.GRAY);
            }
            else if (randomNumber == 800){
                eventString = "Strong winds have begun";
                temperature = -3;
                eventTemperature = temperature;
                eventField.setBackground(Color.LIGHT_GRAY);
            }
            else if (randomNumber == 900){
                eventString = "A heat wave has begun";
                temperature = 10;
                eventTemperature = temperature;
                eventField.setBackground(Color.ORANGE);
            }
            else if (randomNumber == 1000){
                eventString = "A cold snap has begun";
                temperature = -10;
                eventTemperature = temperature;
                eventField.setBackground(Color.cyan);
            }
            eventField.setText("Event Status: " + eventString);
            System.out.println("Event has started: " + eventString);
        }
    }

    public static void main(String[] args) {
        JSimulatorFrame simulatorFrame = new JSimulatorFrame();
        simulatorFrame.setVisible(true);
        simulatorFrame.setSize(1200, 800);
        simulatorFrame.setResizable(false); //fixed window size

    }
}
