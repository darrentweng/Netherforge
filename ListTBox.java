import java.util.ArrayList;

//written in large part by chatGPT. using chatGPT to create child classes with slight variations seems viable
public class ListTBox extends GenericTBox {
    private ArrayList<String> textList;

    public ListTBox(String t, int f, int gx, int gy, double wx, double wy, int gw, int gh) {
        super(t, f, gx, gy, wx, wy, gw, gh);

        //set some additional properties
        getTextArea().setEditable(false); // Make the text area non-editable

        textList = new ArrayList<>();
    }

    public ArrayList<String> getTextList() {
        return textList;
    }
    
    public void appendToList(String input) {
        textList.add(input);
        updateTextFromList();
    }

    public void replaceList(ArrayList<String> newList) {
        textList = newList;
        updateTextFromList();
    }

    private void updateTextFromList() {
        StringBuilder sb = new StringBuilder();
        for (String text : textList) {
            sb.append(text).append("\n");
        }
        replaceText(sb.toString());
    }
}
