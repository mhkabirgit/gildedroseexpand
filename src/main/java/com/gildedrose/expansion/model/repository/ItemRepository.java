package com.gildedrose.expansion.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gildedrose.expansion.model.Item;

public interface ItemRepository extends JpaRepository<Item, String> {
	public Item findByNameIgnoreCase(String name);
	public List<Item> findAll();
}
