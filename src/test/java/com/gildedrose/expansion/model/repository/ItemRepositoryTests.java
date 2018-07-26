package com.gildedrose.expansion.model.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.gildedrose.expansion.ApplicationTests;
import com.gildedrose.expansion.model.Item;

public class ItemRepositoryTests extends ApplicationTests{
	
	
	@Test
	public void testItemCreateAndDelete() {
		Item testItem = new Item(TEST_ITEM_NAME, TEST_ITEM_DESCRIPTION, TEST_ITEM_PRICE);
		itemRepo.saveAndFlush(testItem);
		Assert.assertTrue(itemRepo.exists(TEST_ITEM_NAME));
		itemRepo.delete(TEST_ITEM_NAME);
		Assert.assertFalse(itemRepo.exists(TEST_ITEM_NAME));
	}
	
	@Test
	public void testFindByName() {
		Item testItem = new Item(TEST_ITEM_NAME, TEST_ITEM_DESCRIPTION, TEST_ITEM_PRICE);
		itemRepo.saveAndFlush(testItem);
		Item repoItem = itemRepo.findByNameIgnoreCase(TEST_ITEM_NAME);
		Assert.assertNotNull(repoItem);
		Assert.assertTrue(repoItem.getPrice() == TEST_ITEM_PRICE);
		itemRepo.delete(TEST_ITEM_NAME);
	}
	
	@Test
	public void testFindAll() {
		List<Item> items = itemRepo.findAll();
		Assert.assertTrue(items.size() == 2);
		Item testItem = new Item(TEST_ITEM_NAME, TEST_ITEM_DESCRIPTION, TEST_ITEM_PRICE);
		itemRepo.saveAndFlush(testItem);
		items = itemRepo.findAll();
		Assert.assertTrue(items.size() == 3);
		Item dbLastItem = items.get(2);
		Assert.assertTrue(dbLastItem.getName().equals(TEST_ITEM_NAME));
		itemRepo.delete(TEST_ITEM_NAME);
	}
	
}
