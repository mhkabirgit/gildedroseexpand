package com.gildedrose.expansion.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.gildedrose.expansion.ApplicationTests;
import com.gildedrose.expansion.model.InventoryItem;
import com.gildedrose.expansion.model.Item;
import com.gildedrose.expansion.model.ListItem;

public class InventoryServiceTests extends ApplicationTests {
	
	@Test
	public void testAddAndDeleteItem() {
		Assert.assertFalse(inventoryService.isItemExist(TEST_ITEM_NAME));
		inventoryService.addItem(TEST_ITEM_NAME, TEST_ITEM_DESCRIPTION, TEST_ITEM_PRICE);
		Assert.assertTrue(inventoryService.isItemExist(TEST_ITEM_NAME));
		Item dbItem = inventoryService.getItem(TEST_ITEM_NAME);
		Assert.assertNotNull(dbItem);
		Assert.assertTrue(dbItem.getName().equals(TEST_ITEM_NAME));
		inventoryService.deleteItem(TEST_ITEM_NAME);
		Assert.assertFalse(inventoryService.isItemExist(TEST_ITEM_NAME));
	}
	
	@Test
	public void testAddAndDeleteInventoryItem() {
		Assert.assertFalse(inventoryService.isInventoryItemExist(TEST_ITEM_NAME));
		inventoryService.addInventoryItem(TEST_ITEM_NAME, TEST_ITEM_DESCRIPTION, TEST_ITEM_PRICE, TEST_ITEM_STOCK);
		Assert.assertTrue(inventoryService.isInventoryItemExist(TEST_ITEM_NAME));
		InventoryItem dbInventoryItem = inventoryService.getInventoryItem(TEST_ITEM_NAME);
		Assert.assertNotNull(dbInventoryItem);
		Assert.assertTrue(dbInventoryItem.getItemname().equals(TEST_ITEM_NAME));
		inventoryService.deleteInventoryItem(TEST_ITEM_NAME);
		inventoryService.deleteItem(TEST_ITEM_NAME);
		Assert.assertFalse(inventoryService.isInventoryItemExist(TEST_ITEM_NAME));
		Assert.assertFalse(inventoryService.isItemExist(TEST_ITEM_NAME));
		
	}
	
	@Test
	public void testGetItemsFromInventory() {
		List<ListItem> listitems = inventoryService.getListItems();
		Assert.assertNotNull(listitems);
		Assert.assertFalse(listitems.isEmpty());
		Assert.assertTrue(listitems.size() == 2);
		for(ListItem listitem: listitems) {
			Assert.assertNotNull(listitem.getName());
			Assert.assertNotNull(listitem.getDescription());
		}
	}
	
	@Test
	public void testUpdateItem() {
		int updatedPrice = 200;
		String updatedDescription = "UPDATED: "+ TEST_ITEM_DESCRIPTION;
		inventoryService.addItem(TEST_ITEM_NAME, TEST_ITEM_DESCRIPTION, TEST_ITEM_PRICE);
		Item dbItem = inventoryService.getItem(TEST_ITEM_NAME);
		Assert.assertFalse(dbItem.getPrice() == updatedPrice);
		Assert.assertFalse(dbItem.getDescription().equals(updatedDescription));
		inventoryService.updateItem(TEST_ITEM_NAME, updatedDescription, updatedPrice);
		dbItem = inventoryService.getItem(TEST_ITEM_NAME);
		Assert.assertTrue(dbItem.getPrice() == updatedPrice);
		Assert.assertTrue(dbItem.getDescription().equals(updatedDescription));
		inventoryService.deleteItem(TEST_ITEM_NAME);
		
	}
	
	@Test
	public void testUpdateInventoryItem() {
		int updatedStock = 400;
		inventoryService.addInventoryItem(TEST_ITEM_NAME, TEST_ITEM_DESCRIPTION, TEST_ITEM_PRICE, TEST_ITEM_STOCK);
		InventoryItem inventoryItem = inventoryService.getInventoryItem(TEST_ITEM_NAME);
		Assert.assertFalse(inventoryItem.getStock() == updatedStock);
		inventoryService.updateInventoryStock(TEST_ITEM_NAME, updatedStock);
		inventoryItem = inventoryService.getInventoryItem(TEST_ITEM_NAME);
		Assert.assertTrue(inventoryItem.getStock() == updatedStock);
		inventoryService.deleteInventoryItem(TEST_ITEM_NAME);
		inventoryService.deleteItem(TEST_ITEM_NAME);
	}
	/**
	 * Ignore this unit test from regular build, since it needs hard coded change in {@PriceService}.
	 * The unit test has been performed during the development. 
	 * @throws InterruptedException
	 */
	@Test
	@Ignore
	public void testSurgedPrice() throws InterruptedException {
		int regularPrice = 100;
		int visitThreshold = 20;
		int surgePercentage = 10;
		int surgedPrice = 110;
		
		inventoryService.addInventoryItem(TEST_ITEM_NAME, TEST_ITEM_DESCRIPTION, regularPrice, TEST_ITEM_STOCK);
	
		Item surgePricedItem =null;
		
		priceService.setVisitThreshold(visitThreshold);
		priceService.setSurgePercentage(surgePercentage);
		
		for(int i=0; i<visitThreshold; i++) {
			surgePricedItem = inventoryService.surgedPricedItem(TEST_ITEM_NAME);
			Assert.assertTrue(surgePricedItem.getPrice() == regularPrice);
		}
		surgePricedItem = inventoryService.surgedPricedItem(TEST_ITEM_NAME);
		Assert.assertTrue(surgePricedItem.getPrice() == surgedPrice);
		Thread.sleep(2000L);
		surgePricedItem = inventoryService.surgedPricedItem(TEST_ITEM_NAME);
		Assert.assertNotNull(surgePricedItem);
		Assert.assertTrue(surgePricedItem.getPrice() == regularPrice);
		inventoryService.deleteInventoryItem(TEST_ITEM_NAME);
		inventoryService.deleteItem(TEST_ITEM_NAME);
	}
	
	@Test
	public void testIsBuySucceed() {
		Assert.assertFalse(inventoryService.isBuySucceed(TEST_ITEM_NAME, -1));
		Assert.assertFalse(inventoryService.isBuySucceed(TEST_ITEM_NAME, 0));
		Assert.assertFalse(inventoryService.isInventoryItemExist(TEST_ITEM_NAME));
		Assert.assertFalse(inventoryService.isBuySucceed(TEST_ITEM_NAME));
		Item item = new Item(TEST_ITEM_NAME, TEST_ITEM_DESCRIPTION, TEST_ITEM_PRICE);
		inventoryService.addItem(item);
		Assert.assertTrue(inventoryService.isItemExist(TEST_ITEM_NAME));
		Assert.assertFalse(inventoryService.isBuySucceed(item));
		inventoryService.deleteItem(item);
		Assert.assertFalse(inventoryService.isItemExist(TEST_ITEM_NAME));
		inventoryService.addInventoryItem(TEST_ITEM_NAME, TEST_ITEM_DESCRIPTION, TEST_ITEM_PRICE, TEST_ITEM_STOCK);
		item = inventoryService.surgedPricedItem(TEST_ITEM_NAME);
		Assert.assertNotNull(item);
		InventoryItem inventoryItem = inventoryService.getInventoryItem(TEST_ITEM_NAME);
		Assert.assertNotNull(inventoryItem);
		Assert.assertFalse(inventoryService.isBuySucceed(item, -1));
		Assert.assertFalse(inventoryService.isBuySucceed(item, 0));
		Assert.assertTrue(inventoryService.isBuySucceed(item, 1));
		Assert.assertTrue(inventoryService.isBuySucceed(item));
		Assert.assertFalse(inventoryService.isBuySucceed(item, TEST_ITEM_STOCK));
		Assert.assertTrue(inventoryService.isBuySucceed(item, TEST_ITEM_STOCK-2));
		Assert.assertFalse(inventoryService.isBuySucceed(item, 1));
		inventoryService.deleteInventoryItem(TEST_ITEM_NAME);
		inventoryService.deleteItem(TEST_ITEM_NAME);
	}

}
