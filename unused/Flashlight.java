public class Flashlight extends Item {
    private int batteryLevel;
    

    public String getDescription() {
        return desc;
    }
    public void setDescription(String d) {
        desc = d;
    }
    @Override
    public String toString() {
        return String.valueOf(icon);
    }
}
