package it.tryout.core.domain.bean;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;

public class Receipt {
	List<BasketItem> items;
	BigDecimal taxComponent;
	BigDecimal total;
	
	public List<BasketItem> getItems() {
		return items;
	}
	public void setItems(List<BasketItem> items) {
		this.items = items;
	}
	public BigDecimal getTaxComponent() {
		return taxComponent;
	}
	public void setTaxComponent(BigDecimal taxComponent) {
		this.taxComponent = taxComponent;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	@Override
	public String toString() {
		StringWriter sw = new StringWriter();
		// null unsafe, but this should be actually be taken care of by a validator in the flow chain
		for (BasketItem singleItem : items)
		{
			sw.append(singleItem.getQuantity().toString()).append(" ").append(singleItem.getMatchedItem().getItemId()).append(": ").append(singleItem.getPriceWithBaseTax().toString()).append("\n");
		}
		sw.append("Sales Tax: ").append(taxComponent.toString()).append("\n");
		sw.append("Total: ").append(total.toString()).append("\n");
		return sw.toString();
	}
	
}
