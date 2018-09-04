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
            StringBuilder appString = new StringBuilder();
            for (int j = 0; j < appliances.length; j++) {
                if(j == 0){
                    appString.append(appliances[j].getName());
                }
                else{
                    appString.append(", ").append(appliances[j].getName());
                }
            }
            System.out.println(appString);
            System.out.println("\nFixtures: ");
            StringBuilder fixString = new StringBuilder();
            for (int j = 0; j < fixtures.length; j++) {
                if(j == 0){
                    fixString.append(fixtures[j].getName());
                }
                else{
                    fixString.append(", ").append(fixtures[j].getName());
                }
            }
            System.out.println(fixString);
            System.out.println("-------------------------");
        }
    }

    public static void displayStates(Room[] rooms){
        for (int i = 0; i < rooms.length; i++){
            Fixture[] fixtures = rooms[i].getRoomFixtures();
            Appliance[] appliances = rooms[i].getRoomAppliances();
            for (int j = 0; j < appliances.length; j++) {
                if(appliances[j].getState().equals("")){

                }
                else{
                    System.out.println("=======================");
                    System.out.println("" + appliances[j].getState());
                }
            }
            for (int j = 0; j < fixtures.length; j++) {
                if(fixtures[j].getState().equals("")){

                }
                else{
                    System.out.println("" + fixtures[j].getState());
                    System.out.println("=======================");
                }
            }
        }
    }
}
