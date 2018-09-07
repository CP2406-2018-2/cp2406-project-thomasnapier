public class Fixture {
    private String name;
    private boolean isOn;
    private int temperature;
    private int sunlight;
    private int time;
    private int temperatureCutOff;
    private int timeCutOff;
    private int lightCutOff;
    private double energyUsage;
    private double waterUsage;
    private double totalEnergyUsage = 0;
    private double totalWaterUsage = 0;

    public Fixture(String name, int temperatureCutOff, int timeCutOff, int lightCutOff, double water, double energy){
        this.name = name;
        isOn = false;
        this.temperatureCutOff = temperatureCutOff;
        this.timeCutOff = timeCutOff;
        this.lightCutOff = lightCutOff;
        this.waterUsage = water;
        this.energyUsage = energy;
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
        if(this.lightCutOff < 100){
            if(this.sunlight <= this.lightCutOff){
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

    public void setSunlight(int sunlight){
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

    public void setEnergyUsage(double energy){
        this.energyUsage = energy;
    }

    public void setWaterUsage(double water){
        this.waterUsage = water;
    }

    public double getEnergyUsage(){
        return this.energyUsage;
    }

    public double getWaterUsage(){
        return this.waterUsage;
    }

    public double calculateEnergyUsage(){
        this.totalEnergyUsage += this.energyUsage;
        return this.totalEnergyUsage;
    }

    public double calculateWaterUsage(){
        this.totalWaterUsage += this.waterUsage;
        return this.totalWaterUsage;
    }

}