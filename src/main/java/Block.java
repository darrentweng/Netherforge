public class Block {
    
    private char icon;

    public Block(char icon) {
        this.icon = icon;
    }

    public Block() {
        this.icon = '#';
    }

    private String desc = "A block";
    public String getDescription() {
        return desc;
    }

    @Override
    public String toString() {
        return String.valueOf(icon);
    }
}
