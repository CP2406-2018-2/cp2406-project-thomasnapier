public class Fixture {
    private String name;
    private boolean isOn;
    private int temperature;
    private int sunlight;
    private int time;
    private int temperatureCutOff;
    private int timeCutOff;
    private int lightCutOff;
    private boolean motionActive;

    public Fixture(){
        name = " ";
        isOn = false;
    }

    public Fixture(String name){
        this.name = name;
        isOn = false;
    }

    public Fixture(String name, int temperatureCutOff){
        this.name = name;
        this.temperatureCutOff = temperatureCutOff;
    }

    public Fixture(String name, boolean motionActive){
        this.name = name;
        this.motionActive = motionActive;
    }

    public Fixture(String name, int temperatureCutOff, int timeCutOff){
        this.name = name;
        this.temperatureCutOff = temperatureCutOff;
        this.timeCutOff = timeCutOff;
    }

    public Fixture(String name, int temperatureCutOff, int timeCutOff, int lightCutOff){
        this.name = name;
        isOn = false;
        this.temperatureCutOff = temperatureCutOff;
        this.timeCutOff = timeCutOff;
        this.lightCutOff = lightCutOff;
    }

    public String getName() {
        return name;
    }

    public String getState(){
        String status;
        if(isOn){
            status = "on";
        }
        else{
            status = "off";
        }
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(boolean on) {
        isOn = on;
    }

}