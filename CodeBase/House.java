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

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setSunlight(int sunlight) {
        this.sunlight = sunlight;
    }

    public void setTime(int time){
        this.time = time;
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

    public void resetRoomValues(Room[] rooms){
        for (Room room : rooms) {
            room.setSunlight(0);
            room.setTime(0);
            room.setTemperature(0);
            room.setRoomDevices();
            Fixture[] fixtures = room.getRoomFixtures();
            Appliance[] appliances = room.getRoomAppliances();
            for (int i = 0; i < fixtures.length; i++) {
                fixtures[i].setTotalWaterUsage(0);
                fixtures[i].setTotalEnergyUsage(0);
            }
            for (int i = 0; i < appliances.length; i++) {
                appliances[i].setTotalEnergyUsage(0);
                appliances[i].setTotalWaterUsage(0);
            }
        }
    }
}
