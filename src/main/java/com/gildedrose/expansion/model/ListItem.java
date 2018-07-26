package com.gildedrose.expansion.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * ListItem models the item in the list view, which does not have the price information.
 * 
 * @author Humayun Kabir
 *
 */
public class ListItem {
	private final String name;
	private final String description;
	
	@JsonCreator
	@JsonPropertyOrder({"name", "description"})
	public ListItem(String name, String description) {
		this.name= name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

}
