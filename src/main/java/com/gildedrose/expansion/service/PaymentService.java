package com.gildedrose.expansion.service;

import org.springframework.stereotype.Service;

/**
 * Stub payment service, open to extend.
 * 
 * @author Humayun Kabir
 *
 */
@Service
public class PaymentService {

	public boolean isPaid(int price) {
		//TODO: Future development should have real implementation of payment
		return true;
	}
}
