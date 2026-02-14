package item.service;

import java.util.List;

import item.model.Item;
import item.model.ItemDetails;
import item.model.ItemWithDetails;

public interface ItemService {
	List<Item> getItems();
	List<ItemWithDetails> getItemsWithDetails();
	Item getItem(Long id);
	ItemWithDetails getItemWithDetails(Long itemId);
	Item getItemByName(String name);
	Boolean createItem(Item item);
	Boolean updateItem(Item item);
	Boolean removeItem(Long id);
	boolean hasItemDetails(Long itemId);
	Boolean createItemDetails(ItemDetails details);
	Boolean deleteItemDetailsByItemId(Long itemId);
	Boolean updateItemDetails(ItemDetails details);
}