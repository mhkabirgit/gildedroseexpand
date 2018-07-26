package com.gildedrose.expansion.service;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.gildedrose.expansion.model.Item;

/**
 * PriceService implements the business logic to compute the surge price.
 * It maintains visit count for individual items and surge the price based on the visit count.
 * It refreshes the visit count every surge interval, default surge interval is one hour.
 * Surge interval is also configurable.
 * 
 * @author Humayun Kabir
 *
 */
@Service
public class PriceService {
	
	public static final long DEFAULT_SURGE_INTERVAL= 60*60*1000L; //500ms for unit test, One hour or 60*60*1000 ms for production
	public static final int DEFAULT_VISIT_THRESHOLD = 10;
	public static final int DEFAULT_SURGE_PERCENTGE = 10;
	public static double HUNDRED_IN_DOUBLE = 100.0;
	
	Map<Item, Integer> visitMap = new ConcurrentHashMap<>(); 
	
	private int visitThreshold = DEFAULT_VISIT_THRESHOLD;;
	private int surgePercentage = DEFAULT_SURGE_PERCENTGE;
	private long surgeInterval = DEFAULT_SURGE_INTERVAL;
	private Timer customSurgPriceScheduleTimer = null; 
	
	/**
	 * Task to run in every surge interval, if the surge interval is not the default interval
	 */
	private class VisitMapClearingTask extends TimerTask {

		@Override
		public void run() {
			visitMap.clear();
		}
	}

	/**
	 * Method to configure the surge interval, the use of this method is not required.
	 * This method is deprecated. It is useful for unit test.
	 * @param interval
	 */
	@Deprecated
	public void startSurgePriceTimer(long interval) {
		setSurgeInterval(interval);
		if(customSurgPriceScheduleTimer !=null) {
			customSurgPriceScheduleTimer.cancel();
			customSurgPriceScheduleTimer =null;
		}
		customSurgPriceScheduleTimer = new Timer("SurgePriceTimer", true);
		customSurgPriceScheduleTimer.scheduleAtFixedRate(new VisitMapClearingTask(), getSurgeInterval(), getSurgeInterval());
	}
	
	
	/**
	 * Returns the copy of the given item with price surged.
	 * @param item
	 * @return
	 */
	public Item surgedPricedItem(final Item item) {
		Item itemCopy = item.getCopy();
		int visits = getVisits(item);
		visits++;
		setVisits(item, visits);
		if(visits>visitThreshold) {
			itemCopy.setPrice((int)(item.getPrice()*(HUNDRED_IN_DOUBLE + surgePercentage)/HUNDRED_IN_DOUBLE));
		}
		return itemCopy;
	}
	
	/**
	 * Sets the visit count of the item to the given visits
	 * @param item
	 * @param visits
	 */
	private void setVisits(Item item, int visits) {
		visitMap.put(item, visits);
	}
	
	/**
	 * Gets the visit count of a given item.
	 * @param item
	 * @return
	 */
	private int getVisits(Item item) {
		if(visitMap.isEmpty()== false && visitMap.containsKey(item)) {
			return visitMap.get(item);
		}
		return 0;
	}
	
	/**
	 * Schedules to refresh the visit count every default surge interval 
	 */
	@Scheduled(fixedRate=DEFAULT_SURGE_INTERVAL)
	public void clearVisitMap() {
		if(customSurgPriceScheduleTimer == null) {
			visitMap.clear();
		}	
	}

	public int getVisitThreshold() {
		return visitThreshold;
	}


	public void setVisitThreshold(int surgeThreshold) {
		this.visitThreshold = surgeThreshold;
	}


	public int getSurgePercentage() {
		return surgePercentage;
	}


	public void setSurgePercentage(int surgePercentage) {
		this.surgePercentage = surgePercentage;
	}


	public long getSurgeInterval() {
		return surgeInterval;
	}


	public  void setSurgeInterval(long surgeInterval) {
		this.surgeInterval = surgeInterval;
	}

}
