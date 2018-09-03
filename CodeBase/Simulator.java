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
        int hours = 5;
        int minutes = 0;
        final int HEATING_RATE = 16;
        final int COOLING_RATE = 44;
        final int SIMULATOR_DELAY = 10;

        String mainMenu = "Please select an option: " +
                "\n[1] Start Simulator" +
                "\n[2] List House Contents" +
                "\n[3] Quit";

        System.out.println("=============== Tests ====================");

        System.out.println("============ End of Tests =============");

        //display menu until user enters a valid option
        System.out.println(mainMenu);
        userChoice = userInput.nextInt();
        while (userChoice < 1 || userChoice > 3) {
            System.out.println("Invalid menu option, please try again\n");
            System.out.println(mainMenu);
            userChoice = userInput.nextInt();
        }

        //create fixture and appliance objects for each room
        Fixture[] kitchenFixtures = {new Fixture("Room Lights", false),
                new Fixture("Room Ceiling Fan", 25),
                new Fixture("Motion Sensor"),
                new Fixture("Room Aircon", 30)};
        Appliance[] kitchenAppliances = {new Appliance("Coffee Machine"),
                new Appliance("Microwave"),
                new Appliance("Hot Water Jug"),
                new Appliance("Oven")};
        Fixture[] livingRoomFixtures = {new Fixture("Room Lights", false),
                new Fixture("Room Ceiling Fan", 25),
                new Fixture("Room Aircon", 35),
                new Fixture("Motion Sensor")};
        Appliance[] livingRoomAppliances = {new Appliance("TV")};
        Fixture[] bathroomFixtures = {new Fixture("Room Lights", false),
                new Fixture("Motion Sensor")};
        Fixture[] bedroomFixtures = {new Fixture("Room Lights", false),
                new Fixture("Motion Sensor")};
        Fixture[] gardenFixtures = {new Fixture("Room Lights", false),
                new Fixture("Motion Sensor"),
                new Fixture("Sprinklers", 35, 720)};
        Fixture[] garageFixtures = {new Fixture("Room Lights", false),
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
                System.out.println("\n");
                house.setTime(currentTime); //send time value to House class
                house.setTemperature(currentTemperature); //send temperature value to House class
                for (int i = 0; i < rooms.length; i++) {
                    rooms[i].setTime(currentTime); //send time value to Room class
                    rooms[i].setTemperature(currentTemperature);
                }
                currentTime++;
                Thread.sleep(SIMULATOR_DELAY); //1 minute of simulated time = 1 second real time
            }
        }
    }
}