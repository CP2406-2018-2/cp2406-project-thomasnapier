public class House {
    private int temperature;
    private int sunlight;
    private int time;


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
        for (int i = 0; i < rooms.length; i++){
            Fixture[] fixtures = rooms[i].getRoomFixtures();
            Appliance[] appliances = rooms[i].getRoomAppliances();
            System.out.println("The contents of the " + rooms[i].getRoomName() + " are:");
            System.out.println("Appliances: ");
            for (int j = 0; j < appliances.length; j++) {
                System.out.println(appliances[j].getName());
            }
            System.out.println("\nFixtures: ");
            for (int j = 0; j < fixtures.length; j++) {
                System.out.println(fixtures[j].getName());
            }
            System.out.println("-------------------------");
        }
    }
}
