public class Fixture {
    private String name;
    private boolean isOn;
    private int temperature;
    private int sunlight;
    private int time;
    private int temperatureCutOff;
    private int lowerTimeCutOff;
    private int upperTimeCutOff;
    private int lightCutOff;
    private double energyUsage;
    private double waterUsage;
    private double totalEnergyUsage = 0;
    private double totalWaterUsage = 0;


    public void setTotalEnergyUsage(double totalEnergyUsage) {
        this.totalEnergyUsage = totalEnergyUsage;
    }

    public void setTotalWaterUsage(double totalWaterUsage) {
        this.totalWaterUsage = totalWaterUsage;
    }


    public Fixture(String name, int temperatureCutOff, int lowerTimeCutOff, int upperTimeCutOff, int lightCutOff, double water, double energy){
        this.name = name;
        isOn = false;
        this.temperatureCutOff = temperatureCutOff;
        this.lowerTimeCutOff = lowerTimeCutOff;
        this.upperTimeCutOff = upperTimeCutOff;
        this.lightCutOff = lightCutOff;
        this.waterUsage = water;
        this.energyUsage = energy;
    }

    public String getName() {
        return name;
    }

    public boolean getState(){
        if(this.temperatureCutOff > 0){
            if(this.temperature >= this.temperatureCutOff){
                isOn = true;
            }
            if(this.temperature <= this.temperatureCutOff){
                isOn = false;
            }
        }
        else if(this.lowerTimeCutOff > 0){
            if(this.time >= this.lowerTimeCutOff && this.time <= this.upperTimeCutOff){
                isOn = true;
            }
            else{
                isOn = false;
            }
        }
        else if(this.lightCutOff > 0){
            if(this.sunlight <= this.lightCutOff){
                isOn = true;
            }
            if(this.sunlight >= this.lightCutOff){
                isOn = false;
            }
        }
        else{
            isOn = false;
        }
        return isOn;
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