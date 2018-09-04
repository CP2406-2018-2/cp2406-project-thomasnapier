import java.util.Scanner;
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
        final int MIN_TEMPERATURE = 10;
        final int MAX_TEMPERATURE = 42;
        int hours = 5; //starts at 5:00am
        int minutes = 0;
        final int HEATING_RATE = 16;
        final int COOLING_RATE = 44;
        final int SIMULATOR_DELAY = 100;
        int eventTemperature = 0;

        String mainMenu = "Please select an option: " +
                "\n[1] Start Simulator" +
                "\n[2] List House Contents" +
                "\n[3] Quit";

        //create fixture and appliance objects for each room (would come from the configuration file)
        Fixture[] kitchenFixtures = {new Fixture("Room Light", false),
                new Fixture("Room Ceiling Fan", 25),
                new Fixture("Motion Sensor"),
                new Fixture("Room Aircon", 30)};
        Appliance[] kitchenAppliances = {new Appliance("Coffee Machine"),
                new Appliance("Microwave"),
                new Appliance("Hot Water Jug"),
                new Appliance("Oven")};
        Fixture[] livingRoomFixtures = {new Fixture("Room Light", false),
                new Fixture("Room Ceiling Fan", 25),
                new Fixture("Room Aircon", 35),
                new Fixture("Motion Sensor")};
        Appliance[] livingRoomAppliances = {new Appliance("TV")};
        Fixture[] bathroomFixtures = {new Fixture("Room Light", false),
                new Fixture("Motion Sensor")};
        Fixture[] bedroomFixtures = {new Fixture("Room Light", false),
                new Fixture("Motion Sensor")};
        Fixture[] gardenFixtures = {new Fixture("Room Light", false),
                new Fixture("Motion Sensor"),
                new Fixture("Sprinkler", 35)};
        Fixture[] garageFixtures = {new Fixture("Room Light", false),
                new Fixture("Motion Sensor"),
                new Fixture("Garage Door", false)};
        Appliance[] garageAppliances = {new Appliance("Car")};

        //create room objects with the corresponding room fixtures and appliances
        Room[] rooms = {new Room("Kitchen", kitchenFixtures, kitchenAppliances),
                new Room("Living Room", livingRoomFixtures, livingRoomAppliances),
                new Room("Bathroom", bathroomFixtures),
                new Room("Bedroom", bedroomFixtures),
                new Room("Garden", gardenFixtures),
                new Room("Garage", garageFixtures, garageAppliances)};

        //create House object
        House house = new House();

        System.out.println("=============== Start of Simulation Tests ====================");
        System.out.println("Display house's rooms:\n");
        for (int i = 0; i < rooms.length; i++) {
            System.out.println(rooms[i].getRoomName());
        }
        System.out.println("\nDisplay a fixtures values before simulation:\n");
        for (int i = 0; i < 1; i++) {
            Fixture[] testFixtures = rooms[i].getRoomFixtures();
            for (int j = 0; j < 1; j++) {
                System.out.println("Current fixtures name: " + testFixtures[i].getName());
                System.out.println("Current Sunlight of the room the fixture is in: " + testFixtures[i].getSunlight());
                System.out.println("Current Sunlight threshold it has: " + testFixtures[i].getLightCutOff());
                System.out.println("Current Temperature of the room the fixture is in: " + testFixtures[i].getTemperature());
                System.out.println("Current Temperature it has: " + testFixtures[i].getTemperatureCutOff());
                System.out.println("Current Time of the room the fixture is in: " + testFixtures[i].getTime());
                System.out.println("Current Time threshold it has: " + testFixtures[i].getTimeCutOff());
            }
        }
        System.out.println("============ End of Tests =============\n\n");

        //display menu until user enters a valid option
        System.out.println(mainMenu);
        userChoice = userInput.nextInt();
        while (userChoice < 1 || userChoice > 3) {
            System.out.println("Invalid menu option, please try again\n");
            System.out.println(mainMenu);
            userChoice = userInput.nextInt();
        }


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
            while (currentTime <= END_TIME) {
                if(currentTime >= START_TIME && currentTime <= 120){ //reach max sunlight at 7:00am
                    if(currentSunlight < MAX_SUNLIGHT){
                        house.setSunlight(currentSunlight); //send sunlight value to House class
                        for (int i = 0; i < rooms.length; i++) {
                            rooms[i].setSunlight(currentSunlight); //send sunlight value to Room class
                        }
                        currentSunlight++;
                    }
                }
                if(currentTime > 720 && currentTime < 840){ //reach min sunlight at 7pm
                    if(currentSunlight > MIN_SUNLIGHT){
                        house.setSunlight(currentSunlight); //send sunlight value to House class
                        for (int i = 0; i < rooms.length; i++) {
                            rooms[i].setSunlight(currentSunlight); //send sunlight value to Room class
                        }
                        currentSunlight--;
                    }
                }
                if(currentTime >= START_TIME && currentTime <= 420){ //reach max temperature at 12
                    currentTemperature = MIN_TEMPERATURE + (currentTime / HEATING_RATE);
                }
                if(currentTime >= 450 && currentTime < END_TIME){ //reach min temperature at the start of the next day
                    currentTemperature = MAX_TEMPERATURE - (currentTime / COOLING_RATE);
                }
                currentTemperature = generateEvent(rooms, currentTemperature, currentSunlight);
                System.out.println("Current light percentage is: " + currentSunlight + "%");
                System.out.println("Current temperature is: " + currentTemperature);
                if(currentTime % 60 == 0 && currentTime != START_TIME){
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

                if(minutes < 10){
                    System.out.println("Current time: " + hours + ":0" + minutes);
                }
                else{
                    System.out.println("Current time: " + hours + ":" + minutes);
                }
                house.setTime(currentTime); //send time value to House class
                house.setTemperature(currentTemperature); //send temperature value to House class
                for (int i = 0; i < rooms.length; i++) {
                    rooms[i].setTime(currentTime); //send time value to Room class
                    rooms[i].setTemperature(currentTemperature);
                    rooms[i].setRoomDevices();
                }
                currentTime++;
                House.displayStates(rooms);
                System.out.println("\n");
                Thread.sleep(SIMULATOR_DELAY); //1 minute of simulated time = 1 second real time
            }
            System.out.println("=============== End of Simulation Tests ====================");
            System.out.println("\nDisplay a fixtures values after simulation:\n");
            for (int i = 0; i < 1; i++) {
                Fixture[] testFixtures = rooms[i].getRoomFixtures();
                for (int j = 0; j < 1; j++) {
                    System.out.println("Current fixtures name: " + testFixtures[i].getName());
                    System.out.println("Current Sunlight of the room the fixture is in: " + testFixtures[i].getSunlight());
                    System.out.println("Current Sunlight threshold it has: " + testFixtures[i].getLightCutOff());
                    System.out.println("Current Temperature of the room the fixture is in: " + testFixtures[i].getTemperature());
                    System.out.println("Current Temperature it has: " + testFixtures[i].getTemperatureCutOff());
                    System.out.println("Current Time of the room the fixture is in: " + testFixtures[i].getTime());
                    System.out.println("Current Time threshold it has: " + testFixtures[i].getTimeCutOff());
                }
            }
            System.out.println("============ End of Tests =============\n\n");
        }
    }

    public static int generateEvent(Room[] rooms, int temperature, int sunlight){
        int randomNumber;
        int min = 1;
        int max = 1000;
        int range = (max - min) + 1;
        randomNumber = (int)(Math.random() * range) + min;


        if(randomNumber >= 500){ //50% chance
            if(randomNumber == 500){
                System.out.println("******************************");
                System.out.println("It has started to rain");
                System.out.println("******************************");
                temperature -= 10;
                sunlight -= 30;
                return temperature;
            }
            if(randomNumber == 600){
                System.out.println("******************************");
                System.out.println("It has started to hail");
                System.out.println("******************************");
                temperature -= 10;
                sunlight -= 20;
                return temperature;
            }
            if(randomNumber == 700){
                System.out.println("******************************");
                System.out.println("A lightning storm has started");
                System.out.println("******************************");
                temperature -= 5;
                sunlight -= 25;
                return temperature;
                //chance to stop power
            }
            if(randomNumber == 800){
                System.out.println("******************************");
                System.out.println("A person has entered a room");
                System.out.println("******************************");
                //turn motion sensitive devices on & make them stop when the person leaves
            }
            if(randomNumber == 900){
                System.out.println("******************************");
                System.out.println("A heat wave has begun");
                System.out.println("******************************");
                temperature += 15;
                return temperature;
            }
            if(randomNumber == 1000){
                System.out.println("******************************");
                System.out.println("A cold snap has begun");
                System.out.println("******************************");
                temperature -= 15;
                sunlight -= 5;
                return temperature;
            }
        }
        return temperature;
    }
}