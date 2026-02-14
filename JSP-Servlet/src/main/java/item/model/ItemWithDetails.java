package item.model;

/**
 * DTO for displaying Item with optional ItemDetails (for JOIN list).
 */
public class ItemWithDetails {

    private Item item;
    private ItemDetails details; // null if item has no details

    public ItemWithDetails(Item item, ItemDetails details) {
        this.item = item;
        this.details = details;
    }

    public Item getItem() {
        return item;
    }

    public ItemDetails getDetails() {
        return details;
    }

    public boolean hasDetails() {
        return details != null;
    }
}
