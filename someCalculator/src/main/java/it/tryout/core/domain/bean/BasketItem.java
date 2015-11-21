package it.tryout.core.domain.bean;

import java.math.BigDecimal;
import java.math.BigInteger;


public class BasketItem{

	public BigDecimal price = null;
	public BigDecimal priceWithBaseTax = null;
	/*
	 * it would make sense that the price is in the vendoredunititem, but since multiple items share the same name, in order to
	 * avoid bringing too much meat on the oven, I switched the price as a "on-the-fly" concept based on the receipt input
	 * this way the "name" of the item is a conceptually key, but become a real key when tied with the price
	 */
	public BigDecimal finalPrice = null;
	private VendoredUnitItem matchedItem;
	private BigInteger quantity;
	
	
	public VendoredUnitItem getMatchedItem() {
		return matchedItem;
	}
	public void setMatchedItem(VendoredUnitItem matchedItem) {
		this.matchedItem = matchedItem;
	}
	public BigInteger getQuantity() {
		return quantity;
	}
	public void setQuantity(BigInteger quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}
	
	public BigDecimal getPriceWithBaseTax() {
		return priceWithBaseTax;
	}
	public void setPriceWithBaseTax(BigDecimal priceWithBaseTax) {
		this.priceWithBaseTax = priceWithBaseTax;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((finalPrice == null) ? 0 : finalPrice.hashCode());
		result = prime * result
				+ ((matchedItem == null) ? 0 : matchedItem.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result
				+ ((quantity == null) ? 0 : quantity.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasketItem other = (BasketItem) obj;
		if (finalPrice == null) {
			if (other.finalPrice != null)
				return false;
		} else if (!finalPrice.equals(other.finalPrice))
			return false;
		if (matchedItem == null) {
			if (other.matchedItem != null)
				return false;
		} else if (!matchedItem.equals(other.matchedItem))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}
	
	
}
