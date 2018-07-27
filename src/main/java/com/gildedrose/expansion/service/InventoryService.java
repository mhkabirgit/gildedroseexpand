package com.gildedrose.expansion.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gildedrose.expansion.model.InventoryItem;
import com.gildedrose.expansion.model.Item;
import com.gildedrose.expansion.model.ListItem;
import com.gildedrose.expansion.model.repository.InventoryRepository;
import com.gildedrose.expansion.model.repository.ItemRepository;

/**
 * InventoryService is the core service of the application.
 * It allows the administrator to add, delete, and update the items in the inventory.
 * It provides the list or the single view of the items from the inventory.
 * It also process the buy request from the user.
 * 
 * @author Humayun Kabir
 *
 */
@Service
public class InventoryService {
	
	@Autowired
	private ItemRepository itemRepo;  			//Maintains a list of items
	
	@Autowired
	private InventoryRepository inventoryRepo; 	//Maintains the inventory
	
	@Autowired
	private PriceService priceService; 			//Computes surged price of the items
	
	@Autowired
	private PaymentService paymentService; 		//Settles the payment
	
	
	public List<InventoryItem> getInventoryItems(){
		return inventoryRepo.findAll();
	}
	
	public List<Item> getItems(){
		return itemRepo.findAll();
	}

	/**
	 * Returns the list of inventory items after hiding their original price.
	 * The price is set to INTEGER.MAX_VALUE to hide the original price.
	 * @return
	 */
	public List<ListItem> getListItems(){
		List<ListItem> listitems = new ArrayList<>();
		List<InventoryItem> inventoryItems = getInventoryItems();
		if(inventoryItems != null && inventoryItems.isEmpty() == false) {
			for(InventoryItem inventoryItem: inventoryItems) {
				Item item = inventoryItem.getItem();
				if(item != null) {
					ListItem listitem = item.getListItem();
					listitems.add(listitem);
				}
			}
		}
		return listitems;
	}
	
	public InventoryItem getInventoryItem(String itemname) {
		return inventoryRepo.findByItemnameIgnoreCase(itemname);
	}
	
	public boolean isInventoryItemExist(String name) {
		return inventoryRepo.exists(name);
	}
	
	public Item getItem(String name) {
		return itemRepo.findByNameIgnoreCase(name);
	}
	
	public boolean isItemExist(String name) {
		return itemRepo.exists(name);
	}
	
	public void addItem(Item item) {
		if(getItem(item.getName()) == null) {
			itemRepo.save(item);
		}
	}
	
	public void addItem(String name, String description, int price) {
		Item item = new Item(name, description, price);
		addItem(item);
	}
	
	public void addItems(List<Item> items) {
		for(Item item: items) {
			addItem(item);
		}
	}
	
	public void deleteItem(Item item) {
		itemRepo.delete(item);
	}
	
	public void deleteItems(List<Item> items) {
		itemRepo.delete(items);
	}
	
	public void deleteAllItems() {
		itemRepo.deleteAll();
	}
	
	public void deleteItem(String name) {
		itemRepo.delete(name);
	}
	
	/**
	 * Adds the given InventoryItem into the inventory
	 * @param inventoryItem
	 */
	public void addInventoryItem(InventoryItem inventoryItem) {
		if(getInventoryItem(inventoryItem.getItemname())==null) {
			inventoryRepo.save(inventoryItem);
		}
	}
	
	/**
	 * Adds the given item into both item-list and inventory 
	 * @param item
	 * @param stock
	 */
	public void addInventoryItem(Item item, int stock) {
		InventoryItem inventoryItem = new InventoryItem(item.getName(), stock);
		item.setInventory(inventoryItem);
		inventoryItem.setItem(item);
		addItem(item);
		addInventoryItem(inventoryItem);
	}
	
	
	/**
	 * Adds an item with the given name, description, and price into item-list and an inventory item into
	 * the inventory with the given name and stock
	 * @param name
	 * @param description
	 * @param price
	 * @param stock
	 */
	public void addInventoryItem(String name, String description, int price, int stock) {
		Item item = new Item(name, description, price);
		addInventoryItem(item, stock);
	}
	
	
	public void deleteInventoryItem(InventoryItem inventoryItem) {
		inventoryRepo.delete(inventoryItem);
	}
	
	public void deleteInventoryItem(String itemname) {
		inventoryRepo.delete(itemname);
	}
	
	public void deletAllInventoryItem() {
		inventoryRepo.deleteAll();
	}
	
	/**
	 * Updates item description and price if an item with the given name exists in the item-list
	 * @param name
	 * @param description
	 * @param price
	 */
	public void updateItem(String name, String description, int price) {
		Item item = getItem(name);
		if(name != null ) {
			item.setDescription(description);
			item.setPrice(price);
			itemRepo.save(item);
		}
	}
	
	/**
	 * Updates the description if an item with the given name exists in the item-list
	 * @param name
	 * @param description
	 */
	public void updateItemDescription(String name, String description) {
		Item item = getItem(name);
		if(item != null) {
			item.setDescription(description);
			itemRepo.save(item);
		}
	}
	
	/**
	 * Updates the price if an item with the given name exists in the item-list
	 * @param name
	 * @param price
	 */
	public void updateItemPrice(String name, int price) {
		Item item = getItem(name);
		if(item !=null) {
			item.setPrice(price);
			itemRepo.save(item);
		}
	}
	
	/**
	 * Updates the stock if an inventory item with the given name exists in the inventory
	 * @param itemname
	 * @param stock
	 */
	public void updateInventoryStock(String itemname, int stock) {
		InventoryItem inventoryItem = getInventoryItem(itemname);
		if(inventoryItem !=null) {
			inventoryItem.setStock(stock);
			inventoryRepo.save(inventoryItem);
		}
	}
	
	/**
	 * Returns an Item object with surged price if the item with the give name is available in the inventory.
	 * If the the item is absent in the inventory returns null. 	
	 * @param itemname
	 * @return
	 */
	public Item surgedPricedItem(String itemname) {
		Item itemResult = null;
		InventoryItem inventoryItem = getInventoryItem(itemname);
		if(inventoryItem != null) {
			Item item = inventoryItem.getItem();
			if(item !=null) {
				itemResult = priceService.surgedPricedItem(item);
			}
		}
		return itemResult;
	}
	
	/**
	 * Process the buy request for an item and return the status.
	 * 
	 * @param item that is going to be bought
	 * @param quantity indicates the number of this item to be bought
	 * @return true, if buy has been proceeded successfully
	 */
	public boolean isBuySucceed(Item item, int quantity) {
		//Return false if quantity is less than or equal to zero
		if(quantity<=0) {
			return false;
		}
		
		InventoryItem inventoryItem = item.getInventory();
		inventoryItem= inventoryItem !=null? inventoryItem : getInventoryItem(item.getName());
		
		//Return false if the item is absent in the inventory
		if(inventoryItem == null) {
			return false;
		}
		
		int stock = inventoryItem.getStock();
		//Return false if item does not have enough stock in the inventory
		if(stock < quantity) {
			return false;
		}
		
		int payAmount = item.getPrice()*quantity;
		
		//Proceed with this transaction only if payment has been received
		if(paymentService.isPaid(payAmount)) {
			stock -= quantity;  
			updateInventoryStock(inventoryItem.getItemname(), stock);
			return true;
		}
		
		return false;
	}
	
	public boolean isBuySucceed(String name, int quantity) {
		Item item = surgedPricedItem(name);
		if(item == null) {
			return false;
		}
		return isBuySucceed(item, quantity);
	}
	
	public boolean isBuySucceed(Item item) {
		return isBuySucceed(item, 1);
	}
	
	public boolean isBuySucceed(String name) {
		return isBuySucceed(name, 1);
	}


}
