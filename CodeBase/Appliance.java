public class Appliance {
    private String name;
    private boolean isOn;

    public Appliance(){
        name = " ";
        isOn = false;
    }

    public Appliance(String name, boolean on){
        this.name = name;
        isOn = on;
    }

    public String getName() {
        return name;
    }

    public boolean getState(){
        return isOn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setState(boolean on) {
        isOn = on;
    }

}
