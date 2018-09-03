public class Room {
    private String roomName;
    private Fixture[] fixtures = {};
    private Appliance[] appliances = {};
    private int temperature;
    private int sunlight;
    private int time;

    public Room(String roomName){
        this.roomName = roomName;
    }

    public Room(String roomName, Fixture[] fixtures, Appliance[] appliances){ //for rooms with both appliances and fixtures
        this.roomName = roomName;
        this.fixtures = fixtures;
        this.appliances = appliances;
    }

    public Room(String roomName, Fixture[] fixtures){ //for rooms with only fixtures
        this.roomName = roomName;
        this.fixtures = fixtures;
    }

    public Room(String roomName, Appliance[] appliances){ //for rooms with only appliances
        this.roomName = roomName;
        this.appliances = appliances;
    }

    public Fixture[] getRoomFixtures(){
        return fixtures;
    }

    public Appliance[] getRoomAppliances(){
        return appliances;
    }

    public String getRoomName(){
        return roomName;
    }

    public void setRoomName(String name){
        roomName = name;
    }

    public void setSunlight(int sunlight) {
        this.sunlight = sunlight;
    }

    public int getSunlight() {
        return sunlight;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }


}

