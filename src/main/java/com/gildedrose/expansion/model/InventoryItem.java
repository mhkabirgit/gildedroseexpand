package com.gildedrose.expansion.model;

import java.util.concurrent.locks.ReentrantLock;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * InventoryItem models the items in the inventory.
 * It is thread safe, multiple users will be able to get the consistent view of the data.
 * When modified, it will also be consistent to multiple users.
 * 
 * @author Humayun Kabir
 *
 */
@Entity(name="inventoryitem")
public class InventoryItem {
	
	@Transient
	ReentrantLock lock = new ReentrantLock(); // Lock to control stock update and view
	
	@Id
	@Column(name="itemname", unique=true)
	@NotNull(message = "Item name cannot be null")
	private String itemname;
	
	@OneToOne(fetch=FetchType.LAZY, optional=false)
	@JoinColumn(name="itemname", nullable=false)
	private Item item;
	
	@Column(name="stock")
	@NotNull(message="Item Inventory stock cannot be null.")
	private int stock;
	
	//Required for Hibernate
	public InventoryItem() {}
	
	public InventoryItem(String itemname, int stock) {
		setItemname(itemname);
		setStock(stock);
	}

	public  String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getStock() {
		try {
			lock.lock();
			return this.stock;
		}
		finally {
			lock.unlock();
		}
	}

	public void setStock(int stock) {
		try {
			lock.lock();
			this.stock = stock;
		}
		finally {
			lock.unlock();
		}
		
	}
}
