package com.gildedrose.expansion.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.gildedrose.expansion.ApplicationTests;
import com.gildedrose.expansion.model.Item;

public class PriceServiceTests extends ApplicationTests{
	
	@Test
	public void testSurgedPrice() {
		int regularPrice = 100;
		int visitThreshold = 20;
		int surgePercentage = 10;
		int surgedPrice = 110;
		
		Item item = new Item(TEST_ITEM_NAME, TEST_ITEM_DESCRIPTION, regularPrice);
		Item surgePricedItem =null;
		
		priceService.setVisitThreshold(visitThreshold);
		priceService.setSurgePercentage(surgePercentage);
		
		for(int i=1; i<visitThreshold; i++) {
			surgePricedItem = priceService.surgedPricedItem(item);
			Assert.assertTrue(surgePricedItem.getPrice() == regularPrice);
		}
		surgePricedItem = priceService.surgedPricedItem(item);
		Assert.assertTrue(surgePricedItem.getPrice() == surgedPrice);
	}
	
	/**
	 * Ignore this unit test from regular build, since it needs hard coded change in {@PriceService}.
	 * The unit test has been performed during the development. 
	 * @throws InterruptedException
	 */
	@Test
	@Ignore
	public void testCustomizeSurgeInterval() throws InterruptedException {
		
		int regularPrice = 100;
		int visitThreshold = 20;
		int surgePercentage = 10;
		int surgedPrice = 110;
		
		Item item = new Item(TEST_ITEM_NAME, TEST_ITEM_DESCRIPTION, regularPrice);
		Item surgePricedItem =null;
		
		priceService.setVisitThreshold(visitThreshold);
		priceService.setSurgePercentage(surgePercentage);
		
		for(int i=0; i<visitThreshold; i++) {
			surgePricedItem = priceService.surgedPricedItem(item);
			Assert.assertTrue(surgePricedItem.getPrice() == regularPrice);
		}
		surgePricedItem = priceService.surgedPricedItem(item);
		Assert.assertTrue(surgePricedItem.getPrice() == surgedPrice);
		Thread.sleep(2000L);
		Assert.assertTrue(priceService.visitMap.isEmpty());
		surgePricedItem = priceService.surgedPricedItem(item);
		Assert.assertNotNull(surgePricedItem);
		Assert.assertTrue(surgePricedItem.getPrice() == regularPrice);
		
	}

}
