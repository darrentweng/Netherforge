public class Item {
    private char icon;
    private String desc;

    public Item(char icon, String desc) {
        this.icon = icon;
        this.desc = desc;
    }

    public String getDescription() {
        return desc;
    }

    @Override
    public String toString() {
        return String.valueOf(icon);
    }
}
