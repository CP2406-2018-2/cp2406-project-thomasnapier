public class House {
    private int temperature;
    private int sunlight;
    private int time;
    private double total;
    private double electricityTotal;
    private double waterTotal;


    public House(){
        sunlight = 0;
        temperature = 0;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getSunlight() {
        return sunlight;
    }

    public void setSunlight(int sunlight) {
        this.sunlight = sunlight;
    }

    public void setTime(int time){
        this.time = time;
    }

    //display contents for each room in the house
    public static void displayContents(Room[] rooms){
        for (Room room : rooms) {
            Fixture[] fixtures = room.getRoomFixtures();
            Appliance[] appliances = room.getRoomAppliances();
            System.out.println("The contents of the " + room.getRoomName() + " are:");
            System.out.println("Appliances: ");
            StringBuilder appString = new StringBuilder();
            for (int j = 0; j < appliances.length; j++) {
                if (j == 0) {
                    appString.append(appliances[j].getName());
                } else {
                    appString.append(", ").append(appliances[j].getName());
                }
            }
            System.out.println(appString);
            System.out.println("\nFixtures: ");
            StringBuilder fixString = new StringBuilder();
            for (int j = 0; j < fixtures.length; j++) {
                if (j == 0) {
                    fixString.append(fixtures[j].getName());
                } else {
                    fixString.append(", ").append(fixtures[j].getName());
                }
            }
            System.out.println(fixString);
            System.out.println("-------------------------");
        }
    }

    public static void displayStates(Room[] rooms){
        for (Room room : rooms) {
            Fixture[] fixtures = room.getRoomFixtures();
            Appliance[] appliances = room.getRoomAppliances();
            for (Appliance appliance : appliances) {
                if (appliance.getState().equals("")) {
                } else {
                    System.out.print(" **" + appliance.getState() + "** ");
                    appliance.calculateEnergyUsage();
                    appliance.calculateWaterUsage();
                }
            }
            for (Fixture fixture : fixtures) {
                if (fixture.getState().equals("")) {
                } else {
                    System.out.print(" **" + fixture.getState() + "** ");
                    fixture.calculateEnergyUsage();
                    fixture.calculateWaterUsage();
                }
            }
        }
    }

    public void calculateCosts(Room[] rooms){
        for (Room room : rooms) {
            Fixture[] fixtures = room.getRoomFixtures();
            Appliance[] appliances = room.getRoomAppliances();
            for (Appliance appliance : appliances) {
                this.electricityTotal = appliance.calculateEnergyUsage();
                this.waterTotal = appliance.calculateWaterUsage();
            }
            for (Fixture fixture : fixtures) {
                this.electricityTotal = fixture.calculateEnergyUsage();
                this.waterTotal = fixture.calculateWaterUsage();
            }
        }
    }

    public void displayTotals(double electricityCost, double waterCost){
        this.electricityTotal *= electricityCost;
        this.waterTotal *= waterCost;
        this.total = this.electricityTotal + this.waterTotal;
        System.out.println("\n\nThe total cost for all appliances and fixtures throughout the day was $" + (int) this.total + ", consisting of $"
                + (int) this.electricityTotal + " from electricity and $" + (int) this.waterTotal + " from water");
    }
}
