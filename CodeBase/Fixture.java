public class Fixture {
    private String name;
    private boolean isOn;

    public Fixture(){
        name = " ";
        isOn = false;
    }

    public Fixture(String name){
        this.name = name;
        isOn = false;
    }

    public Fixture(String name, boolean on){
        this.name = name;
        this.isOn = on;
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