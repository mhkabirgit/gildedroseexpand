package com.gildedrose.expansion.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gildedrose.expansion.model.Item;
import com.gildedrose.expansion.model.ListItem;
import com.gildedrose.expansion.service.InventoryService;

/**
 * Main controller of the application.
 * It provides rest end points to view the list of the items in the inventory as well as to view a
 * single item from the inventory.
 * It also provides the rest end point to buy an item. 
 * Authentication is required to access buy end point.
 * 
 * @author Humayun Kabir
 *
 */
@Controller
@Transactional
public class InventoryController {
	
	@Autowired
	private InventoryService inventoryService;
	
	/**
	 * Handles the request on the rest end point 'public/items', which is an open access end-point.
	 * It gives the list of items available in the repository. 
	 * @return
	 */
	@RequestMapping(value = "/public/items", method=RequestMethod.GET, produces= {"application/json"})
	public @ResponseBody ResponseEntity<List<ListItem>> getInventoryItems(){
		List<ListItem> listitems = inventoryService.getListItems();
		return new ResponseEntity<>(listitems, HttpStatus.OK);
	}
	
	/**
	 * Handles the request on the rest end point 'public/items/{itemname}', which is an open access end-point.
	 * It gives the details of an individual item with the given itemname.
	 * @param itemname
	 * @return
	 */
	@RequestMapping(value = "/public/items/{itemname}", method=RequestMethod.GET, produces= {"application/json"})
	public @ResponseBody ResponseEntity<Item> getInventoryItems(@PathVariable(value = "itemname") String itemname){
		Item item = inventoryService.surgedPricedItem(itemname);
		if(item != null) {
			return new ResponseEntity<>(item, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}	
	}
	
	/**
	 * This rest end-point is a restricted end-point, only accessible if the user is authenticated.
	 * It provides a form to the user to give an item name to the application to buy an item.
	 * @return
	 */
	@RequestMapping(value="/item/buy", method = RequestMethod.GET)
	public ModelAndView buyItem() {
		return new ModelAndView("itembuy");
	}
	
	/**
	 * This rest end-point is a restricted end-point, only accessible if the user is authenticated.
	 * It handles the buy request posted by the user through buy form.
	 * @param itemName
	 * @return
	 */
	@RequestMapping(value="/item/buy", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> prococessBuy(@RequestParam(value="itemname", required=true) String itemName) {
		if(itemName == null || itemName.isEmpty()) {
			return new ResponseEntity<>("Bad request, must give an item name to buy", HttpStatus.BAD_REQUEST);
		}
		if(inventoryService.isBuySucceed(itemName)) {
			return new ResponseEntity<>("Purchased Successfully", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Purchase Failed", HttpStatus.OK);
		}
	}
	

}
