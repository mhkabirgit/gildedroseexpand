package com.gildedrose.expansion.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gildedrose.expansion.model.InventoryItem;


public interface InventoryRepository extends JpaRepository<InventoryItem, String> {
	
	public InventoryItem findByItemnameIgnoreCase(String itemname);
	public List<InventoryItem> findAll();

}
