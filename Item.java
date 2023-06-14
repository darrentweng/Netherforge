public class Item {
    protected char icon;
    protected String desc;

    public Item(char icon, String desc) {
        this.icon = icon;
        this.desc = desc;
    }

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

    public void use() {

    }

    public void use(Board b) {
        
    }
}
