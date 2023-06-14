public class Torch extends Item{

    private static String desc = "a TORCH to guide you. increases visibility.";

    public Torch() {
        super('i', desc);
        
    }

    public void use(Board b) {
        b.setVisibility(3);
    }

}
