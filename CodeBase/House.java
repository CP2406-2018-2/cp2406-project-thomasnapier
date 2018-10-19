public class House {
    private int temperature;
    private int sunlight;
    private int time;
    private double total;
    private double electricityTotal;
    private double waterTotal;
    final double COST_PER_KWH = 0.025; //0.25c per minute
    final double COST_PER_LITRE = 0.0294;


    public void setTotal(double total) {
        this.total = total;
    }

    public void setElectricityTotal(double electricityTotal) {
        this.electricityTotal = electricityTotal;
    }

    public void setWaterTotal(double waterTotal) {
        this.waterTotal = waterTotal;
    }

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

    public void calculateTotal(){
        this.total = this.electricityTotal + this.waterTotal;
    }

    public void calculateWaterTotal(){
        this.waterTotal *= COST_PER_LITRE;
    }

    public void calculateElectricityTotal(){
        this.electricityTotal *= COST_PER_KWH;
    }

    public double getWaterTotal(){
        return waterTotal;
    }

    public double getElectricityTotal(){
        return electricityTotal;
    }

    public double getTotal(){
        return total;
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

    public void displayStates(Room[] rooms){
        for (Room room : rooms) {
            Fixture[] fixtures = room.getRoomFixtures();
            Appliance[] appliances = room.getRoomAppliances();
            for (Appliance appliance : appliances) {
                if (!appliance.getState()) {
                } else {
                    this.electricityTotal += appliance.getEnergyUsage() * COST_PER_KWH;
                    this.waterTotal += appliance.getWaterUsage() * COST_PER_LITRE;
                    this.total += this.electricityTotal + this.waterTotal;
                }
            }
            for (Fixture fixture : fixtures) {
                if (!fixture.getState()) {
                } else {
                    this.electricityTotal += fixture.getEnergyUsage() * COST_PER_KWH;
                    this.waterTotal += fixture.getWaterUsage() * COST_PER_LITRE;
                    this.total = this.electricityTotal + this.waterTotal;
                }
            }
        }
    }
}
