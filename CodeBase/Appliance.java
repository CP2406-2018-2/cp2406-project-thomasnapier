public class Appliance {
    private String name;
    private boolean isOn;
    private int temperature;
    private int sunlight;
    private int time;
    private int temperatureCutOff;
    private int timeCutOff;
    private int lightCutOff;
    private boolean motionActive;

    public Appliance(){
        name = " ";
        isOn = false;
    }

    public Appliance(String name){
        this.name = name;
        isOn = false;
    }

    public Appliance(String name, int temperatureCutOff){
        this.name = name;
        this.temperatureCutOff = temperatureCutOff;
    }

    public Appliance(String name, boolean motionActive){
        this.name = name;
        this.motionActive = motionActive;
    }

    public Appliance(String name, int temperatureCutOff, int timeCutOff){
        this.name = name;
        this.temperatureCutOff = temperatureCutOff;
        this.timeCutOff = timeCutOff;
    }

    public Appliance(String name, int temperatureCutOff, int timeCutOff, int lightCutOff){
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
        String status = "";
        if(this.temperatureCutOff > 0){
            if(this.temperature >= this.temperatureCutOff){
                isOn = true;
                status = this.name + " is on";
            }
        }
        if(this.timeCutOff > 0){
            if(this.time >= this.timeCutOff){
                isOn = true;
                status = this.name + " is on";
            }
        }
        if(this.lightCutOff > 0){
            if(this.sunlight >= this.lightCutOff){
                isOn = true;
                status = this.name + " is on";
            }
        }
        else{
            isOn = false;
        }
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(boolean on) {
        isOn = on;
    }

    public void setTime(int time){
        this.time = time;
    }

    public void setTemperature(int temperature){
        this.temperature = temperature;
    }

    public void setSunlight(int sunlight) {
        this.sunlight = sunlight;
    }

    public int getSunlight(){
        return sunlight;
    }

    public int getTime(){
        return time;
    }

    public int getTemperature(){
        return temperature;
    }

    public int getTemperatureCutOff(){
        return temperatureCutOff;
    }

    public int getTimeCutOff(){
        return timeCutOff;
    }

    public int getLightCutOff(){
        return lightCutOff;
    }
}
