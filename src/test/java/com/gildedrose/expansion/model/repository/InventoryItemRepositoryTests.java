package com.gildedrose.expansion.model.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.gildedrose.expansion.ApplicationTests;
import com.gildedrose.expansion.model.InventoryItem;
import com.gildedrose.expansion.model.Item;

public class InventoryItemRepositoryTests extends ApplicationTests{
	
	
	@Test
	public void testInventoryItemCreateAndDelete() {
		Item testItem = new Item(TEST_ITEM_NAME, TEST_ITEM_DESCRIPTION, TEST_ITEM_PRICE);
		InventoryItem inventoryItem = new InventoryItem(TEST_ITEM_NAME, TEST_ITEM_STOCK);
		itemRepo.saveAndFlush(testItem);
		inventoryRepo.saveAndFlush(inventoryItem);
		Assert.assertTrue(itemRepo.exists(TEST_ITEM_NAME));
		Assert.assertTrue(inventoryRepo.exists(TEST_ITEM_NAME));
		inventoryRepo.delete(TEST_ITEM_NAME);
		itemRepo.delete(TEST_ITEM_NAME);
		Assert.assertFalse(itemRepo.exists(TEST_ITEM_NAME));
		Assert.assertFalse(inventoryRepo.exists(TEST_ITEM_NAME));
	}
	
	@Test
	public void testFindByName() {
		Item testItem = new Item(TEST_ITEM_NAME, TEST_ITEM_DESCRIPTION, TEST_ITEM_PRICE);
		InventoryItem inventoryItem = new InventoryItem(TEST_ITEM_NAME, TEST_ITEM_STOCK);
		itemRepo.saveAndFlush(testItem);
		inventoryRepo.saveAndFlush(inventoryItem);
		InventoryItem repoItem = inventoryRepo.findByItemnameIgnoreCase(TEST_ITEM_NAME);
		Assert.assertNotNull(repoItem);
		Assert.assertTrue(repoItem.getStock() == TEST_ITEM_STOCK);
		inventoryRepo.delete(TEST_ITEM_NAME);
		itemRepo.delete(TEST_ITEM_NAME);

	}
	
	@Test
	public void testFindAll() {
		List<InventoryItem> items = inventoryRepo.findAll();
		Assert.assertTrue(items.size() == 2);
		Item testItem = new Item(TEST_ITEM_NAME, TEST_ITEM_DESCRIPTION, TEST_ITEM_PRICE);
		InventoryItem inventoryItem = new InventoryItem(TEST_ITEM_NAME, TEST_ITEM_STOCK);
		itemRepo.saveAndFlush(testItem);
		inventoryRepo.saveAndFlush(inventoryItem);
		items = inventoryRepo.findAll();
		Assert.assertTrue(items.size() == 3);
		inventoryRepo.delete(TEST_ITEM_NAME);
		itemRepo.delete(TEST_ITEM_NAME);

	}
	
}
