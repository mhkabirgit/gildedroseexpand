package com.gildedrose.expansion.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Item is used to model the item of the application.
 * 
 * @author Humayun Kabir
 *
 */
@Entity(name="item")
@JsonPropertyOrder({"name", "description", "price"})
public class Item {
	
	@Id
	@Column(name="name", unique=true)
	@NotNull(message="Item must have a name.")
	@JsonProperty
	private String name;
	
	@Column(name="description")
	@JsonProperty
	@NotNull(message="Item must have a description.")
	private String description;
	
	@Column(name="price")
	@NotNull(message="Item must have a price.")
	@JsonProperty
	private int price;
	
	@OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="item")
	@JsonIgnore
	private InventoryItem inventory;
	
	//Required for Hibernate 
	public Item() {	}
	
	@JsonCreator
	public Item(String name, String description, int price) {
		this.setName(name);
		this.setDescription(description);
		this.setPrice(price);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@JsonIgnore
	public InventoryItem getInventory() {
		return inventory;
	}

	public void setInventory(InventoryItem inventory) {
		this.inventory = inventory;
	}
	
	@JsonIgnore
	public Item getCopy() {
		return new Item(this.name, this.description, this.price);
	}
	
	@JsonIgnore
	public ListItem getListItem() {
		return new ListItem(this.name, this.description);
	}
	

}
