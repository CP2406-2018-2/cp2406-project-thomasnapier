import java.util.Scanner;
import java.util.*;
import java.io.*;
public class Simulator {

    public static void main(String[] args) throws InterruptedException {

        int currentTime = 0;
        final int START_TIME = 0; //5:00am
        final int END_TIME = 1439; //minutes in 23 hours and 59 minutes (4:59am the next day)
        Scanner userInput = new Scanner(System.in);
        int userChoice;
        final int MIN_SUNLIGHT = 0;
        final int MAX_SUNLIGHT = 100;
        int currentSunlight = 0;
        int currentTemperature = 0;
        int hours = 5; //starts at 5:00am
        int minutes = 0;
        int halfDays = 0;
        final int HEATING_RATE = 16;
        final int COOLING_RATE = 64;
        int simulatorSpeed = 100; //1000 = 1 second real = 1 min simulated, 100 = 0.1 second real = 1 min simulated0
        int eventTemperature = 0;
        final int MAX_TEMPERATURE = 35;
        final int MIN_TEMPERATURE = 10;
        final double COST_PER_KWH = 0.25; //0.25c per minute
        final double COST_PER_LITRE = 0.294;

        String mainMenu = "Please select an option: " +
                "\n[1] Start Simulator" +
                "\n[2] List House Contents" +
                "\n[3] Quit";

        //load all appliance, fixtures, rooms and properties from a configuration file
        String workingDir = System.getProperty("user.dir");
        String file = workingDir + "\\CodeBase\\configurations.txt";
        File inputFile = new File(file);
        Scanner scanner = null;
        List<Fixture> fixList = new ArrayList<>();
        List<Appliance> appList = new ArrayList<>();
        List<Room> roomList = new ArrayList<>();
        String roomName = "";
        Room[] rooms = new Room[roomList.size()];
        try {
            scanner = new Scanner(inputFile);
            while(scanner.hasNextLine()){
                String total[] = scanner.nextLine().split("/");
                for (int i = 0; i < total.length; i++) {
                    if(i == 0){
                        String data[] = total[i].split(";"); //separate individual fixtures
                        for (int j = 0; j < data.length - 1; j++) {
                            String individual[] = data[j].split(","); //separate individual fixture values
                            fixList.add(new Fixture(individual[0], Integer.parseInt(individual[1]),
                                    Integer.parseInt(individual[2]), Integer.parseInt(individual[3]),
                                    Double.parseDouble(individual[4]), Double.parseDouble(individual[5])));
                        }
                    }
                    else{
                        if(i == 1){
                            String data[] = total[i].split(";"); //separate individual appliances
                            for (int j = 0; j < data.length; j++) {
                                String individual[] = data[j].split(","); //separate individual appliance values
                                appList.add(new Appliance(individual[0], Integer.valueOf(individual[1]),
                                        Integer.valueOf(individual[2]), Integer.valueOf(individual[3]),
                                        Double.valueOf(individual[4]), Double.valueOf(individual[5])));
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //create House object
        House house = new House();

        displayStartTests(rooms);

        //display menu until user enters a valid option
        System.out.println(mainMenu);
        userChoice = userInput.nextInt();
        while(userChoice == 2 || userChoice < 1 || userChoice > 3) {
            if(userChoice == 2){
                House.displayContents(rooms);
            }
            else{
                System.out.println("Invalid menu option, please try again\n");
            }
            System.out.println("\n" + mainMenu);
            userChoice = userInput.nextInt();
        }
        if(userChoice == 1) {
            for (int i = 0; i < 50; ++i) System.out.println(); //clear console
            while (currentTime <= END_TIME) {
                if(currentTime >= START_TIME && currentTime <= 120){ //reach max sunlight at 7:00am
                    if(currentSunlight < MAX_SUNLIGHT){
                        house.setSunlight(currentSunlight);
                        for (Room room : rooms) {
                            room.setSunlight(currentSunlight);
                        }
                        currentSunlight++;
                    }
                }
                if(currentTime > 720 && currentTime < 840){ //reach min sunlight at 7pm
                    if(currentSunlight > MIN_SUNLIGHT){
                        house.setSunlight(currentSunlight);
                        for (Room room : rooms) {
                            room.setSunlight(currentSunlight);
                        }
                        currentSunlight--;
                    }
                }
                if(eventTemperature == 0){ //if no event has triggered
                    eventTemperature = generateEvent();
                }
                if(currentTime >= START_TIME && currentTime <= 420){ //reach max temperature at 12
                    currentTemperature = (MIN_TEMPERATURE + (currentTime / HEATING_RATE));
                    currentTemperature += eventTemperature;
                }
                if(currentTime >= 450 && currentTime < END_TIME){ //reach min temperature at the start of the next day
                    currentTemperature = (MAX_TEMPERATURE - (currentTime / COOLING_RATE));
                    currentTemperature += eventTemperature;
                }
                String output = "";
                output += "Current light percentage is: " + currentSunlight + "% |";
                output += "| Current temperature is: " + currentTemperature + "|";
                if(currentTime % 60 == 0 && currentTime != START_TIME){
                    if(hours == 11){
                        halfDays++;
                    }
                    if(hours == 12){
                        hours = 1;
                        minutes = 0;
                    }
                    else{
                        hours++;
                        minutes = 0;
                    }
                }
                else{
                    minutes++;
                }
                if(halfDays == 0 || halfDays == 2){
                    if(minutes < 10){
                        output += "| Current time: " + hours + ":0" + minutes + "am";
                    }
                    else{
                        output += "| Current time: " + hours + ":" + minutes + "am";
                    }
                }
                else{
                    if(minutes < 10){
                        output += "| Current time: " + hours + ":0" + minutes + "pm";
                    }
                    else{
                        output += "| Current time: " + hours + ":" + minutes + "pm";
                    }
                }
                System.out.print("\r" + output);
                house.setTime(currentTime);
                house.setTemperature(currentTemperature);
                for (Room room : rooms) {
                    room.setTime(currentTime);
                    room.setTemperature(currentTemperature);
                    room.setRoomDevices();
                }
                currentTime++;
                house.displayStates(rooms);
                Thread.sleep(simulatorSpeed); //1 minute of simulated time = 1 second real time
//                house.calculateCosts(rooms);
            }
            house.displayTotals(COST_PER_KWH, COST_PER_LITRE);
        }
    }

    private static void displayStartTests(Room[] rooms) {
        System.out.println("=============== Start of Simulation Tests ====================");
        System.out.println("Display house's rooms + attributes:\n");
        House.displayContents(rooms);
        for (Room room : rooms) {
            System.out.println(room.getRoomName());
            System.out.println("Sunlight Before: " + room.getSunlight());
            room.setSunlight(50);
            System.out.println("Sunlight After: " + room.getSunlight());
            room.setSunlight(0);
            System.out.println("Temperature Before: " + room.getTemperature());
            room.setTemperature(25);
            System.out.println("Temperature After: " + room.getTemperature());
            room.setTemperature(0);
            System.out.println("\n");
        }
        System.out.println("\nDisplay a fixtures values:\n");
        for (int i = 0; i < 1; i++) { //for 1 fixture in 1 room
            rooms[i].setTemperature(25);
            rooms[i].setSunlight(50);
            rooms[i].setTime(720);
            rooms[i].setRoomDevices();
            Fixture[] testFixtures = rooms[i].getRoomFixtures();
            for (int j = 0; j < 1; j++) {
                System.out.println("Current fixtures name: " + testFixtures[i].getName());
                System.out.println("Current Sunlight of the room the fixture is in: " + testFixtures[i].getSunlight());
                System.out.println("Current Sunlight threshold it has: " + testFixtures[i].getLightCutOff());
                System.out.println("Current Temperature of the room the fixture is in: " + testFixtures[i].getTemperature());
                System.out.println("Current Temperature threshold: " + testFixtures[i].getTemperatureCutOff());
                System.out.println("Current Time of the room the fixture is in: " + testFixtures[i].getTime());
                System.out.println("Current Time threshold it has: " + testFixtures[i].getTimeCutOff());
                System.out.println("Current KW/min usage: " + testFixtures[i].getEnergyUsage());
                System.out.println("Current water litre/min usage: " + testFixtures[i].getWaterUsage());
                System.out.println("State should display nothing: " + testFixtures[i].getState());
                testFixtures[i].setSunlight(20);
                System.out.println("State should display: " + testFixtures[i].getState());
                testFixtures[i].setSunlight(0);
                System.out.println("Calculate 5 minutes of electricity usage");
                for (int k = 0; k < 6; k++) {
                    System.out.println("Minute " + k + ": " + testFixtures[i].calculateEnergyUsage());
                }
                testFixtures[i].setEnergyUsage(0.1);
                testFixtures[i].setWaterUsage(1);
                System.out.println("Calculate 5 minutes of water usage");
                for (int k = 0; k < 6; k++) {
                    System.out.println("Minute " + k + ": " + testFixtures[i].calculateWaterUsage());
                }
                testFixtures[i].setWaterUsage(0);
            }
            rooms[i].setTemperature(0);
            rooms[i].setSunlight(0);
            rooms[i].setTime(0);
            rooms[i].setRoomDevices();
        }
        System.out.println("\nDisplay an Appliance's values:\n");
        for (int i = 0; i < 1; i++) { //for 1 appliance in 1 room
            rooms[i].setTemperature(25);
            rooms[i].setSunlight(50);
            rooms[i].setTime(720);
            rooms[i].setRoomDevices();
            Appliance[] testAppliances = rooms[i].getRoomAppliances();
            for (int j = 0; j < 1; j++) {
                System.out.println("Current appliance's name: " + testAppliances[i].getName());
                System.out.println("Current Sunlight of the room the appliance is in: " + testAppliances[i].getSunlight());
                System.out.println("Current Sunlight threshold it has: " + testAppliances[i].getLightCutOff());
                System.out.println("Current Temperature of the room the appliance is in: " + testAppliances[i].getTemperature());
                System.out.println("Current Temperature threshold: " + testAppliances[i].getTemperatureCutOff());
                System.out.println("Current Time of the room the appliance is in: " + testAppliances[i].getTime());
                System.out.println("Current Time threshold it has: " + testAppliances[i].getTimeCutOff());
                System.out.println("Current KW/min usage: " + testAppliances[i].getEnergyUsage());
                System.out.println("Current water litre/min usage: " + testAppliances[i].getWaterUsage());
                System.out.println("State should display nothing: " + testAppliances[i].getState());
                testAppliances[i].setSunlight(20);
                System.out.println("State should display: " + testAppliances[i].getState());
                testAppliances[i].setSunlight(0);
                System.out.println("Calculate 5 minutes of electricity usage");
                for (int k = 0; k < 6; k++) {
                    System.out.println("Minute " + k + ": " + testAppliances[i].calculateEnergyUsage());
                }
                testAppliances[i].setEnergyUsage(0.03);
                System.out.println("Calculate 5 minutes of water usage");
                for (int k = 0; k < 6; k++) {
                    System.out.println("Minute " + k + ": " + testAppliances[i].calculateWaterUsage());
                }
                testAppliances[i].setWaterUsage(0.5);
            }
            rooms[i].setTemperature(0);
            rooms[i].setSunlight(0);
            rooms[i].setTime(0);
            rooms[i].setRoomDevices();
        }
        System.out.println("============ End of Tests =============\n\n");
    }

    private static int generateEvent(){
        int randomNumber = getRandomNumber(1, 1000);
        int temperature = 0;
        if(randomNumber >= 500){ //50% chance
            switch(randomNumber){
                case 500:{
                    for (int i = 0; i < 50; ++i) System.out.println();
                    System.out.println(
                            "\n******************************" +
                                    "\n It has started to rain" +
                                    "\n******************************");
                    temperature = -3;
                    return temperature;
                }
                case 600:{
                    for (int i = 0; i < 50; ++i) System.out.println();
                    System.out.println(
                            "\n******************************" +
                                    "\n It has started to hail" +
                                    "\n******************************");
                    temperature = -7;
                    return temperature;
                }
                case 700:{
                    for (int i = 0; i < 50; ++i) System.out.println();
                    System.out.println(
                            "\n******************************" +
                                    "\n A lightning storm has begun" +
                                    "\n******************************");
                    temperature = -5;
                    return temperature;
                }
                case 800:{
                    for (int i = 0; i < 50; ++i) System.out.println();
                    System.out.println(
                            "\n******************************" +
                                    "\n Strong winds have started" +
                                    "\n******************************");
                    temperature = -3;
                    return temperature;
                }
                case 900:{
                    for (int i = 0; i < 50; ++i) System.out.println();
                    System.out.println(
                            "\n******************************" +
                                    "\n A heat wave has begun" +
                                    "\n******************************");
                    temperature = 10;
                    return temperature;
                }
                case 1000:{
                    for (int i = 0; i < 50; ++i) System.out.println();
                    System.out.println(
                            "\n******************************" +
                                    "\n A cold snap has begun" +
                                    "\n******************************");
                    temperature = -10;
                    return temperature;
                }
            }
        }
        return temperature;
    }

    private static int getRandomNumber(int min, int max) {
        int randomNumber;
        int range = (max - min) + 1;
        randomNumber = (int)(Math.random() * range) + min;
        return randomNumber;
    }

}