import java.util.ArrayList;
import java.util.List;

//written entirely by chatGPT using item.java, genericTBox.java and ListTBox.java as input
public class Inventory {
    private List<Item> items;
    private ListTBox inventoryList;

    public Inventory(GenericTBox listTBox) {
        items = new ArrayList<>(); // Initialize the items list
        inventoryList = (ListTBox)listTBox; // Store the reference to the inventory list box
    }

    /**
     * Add an item to the inventory.
     *
     * @param item The item to be added.
     */
    public void addItem(Item item) {
        items.add(item); // Add the item to the items list
        updateInventoryList(); // Update the inventory list box
    }

    /**
     * Remove an item from the inventory.
     *
     * @param item The item to be removed.
     */
    public void removeItem(Item item) {
        items.remove(item); // Remove the item from the items list
        updateInventoryList(); // Update the inventory list box
    }

    /**
     * Get the list of items in the inventory.
     *
     * @return The list of items.
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Update the inventory list box with the current items in the inventory.
     */
    private void updateInventoryList() {
        ArrayList<String> itemList = new ArrayList<>();
        for (Item item : items) {
            itemList.add("- " + item.getDescription()); // Format each item as a bullet point
        }
        inventoryList.replaceList(itemList); // Replace the content of the inventory list box
    }
}
