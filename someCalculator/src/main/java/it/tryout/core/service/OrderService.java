package it.tryout.core.service;

import it.tryout.core.domain.bean.BasketItem;
import it.tryout.core.domain.bean.Receipt;
import it.tryout.core.domain.bean.VendoredUnitItem;
import it.tryout.core.utils.sysvar.InventoryKnowledge;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderService {

	static final Logger logger = LogManager.getLogger(OrderService.class.getName());

	public static BasketItem fromStringToOrder(String orderString) throws Exception
	{
		if (StringUtils.isEmpty(orderString))
		{
			throw new Exception("provided empty string");
		}
		else
		{
			/*TODO 
			 * not the best approach as it will possibly cause inconsistency if the product contains " at " inside name
			 * might switch to matching pattern, but that won't solve it completely either, 
			 * so I'm keeping it simple
			 */
			String[] lev1 = orderString.split(" at ");
			if (lev1 == null || lev1.length == 0 || lev1.length != 2)
			{
				throw new Exception("incorrect general string format");
			}
			else
			{
				// process price
				try
				{
					lev1[1] = lev1[1].replace(" ", "");
					BigDecimal price =  new BigDecimal(lev1[1]);
					try
					{
						String itemName = lev1[0].substring(lev1[0].indexOf(" ")+1);
						String itemqty = lev1[0].substring(0, lev1[0].indexOf(" "));
						try
						{
							BigInteger quantity = new BigInteger(itemqty);
							VendoredUnitItem palatableItem = InventoryKnowledge.getMap().get(itemName);
							if (palatableItem == null)
							{
								throw new Exception("provided item does not exist in the database");
							}
							BasketItem selectedItem = new BasketItem();
							selectedItem.setMatchedItem(palatableItem);
							selectedItem.setPrice(price);
							selectedItem.setQuantity(quantity);
							return selectedItem;

						}
						catch (NumberFormatException e)
						{
							throw new Exception("incorrect quantity format");
						}
					}
					catch (Exception e)
					{
						throw new Exception("incorrect itemname&quantity format");
					}
				}
				catch (NumberFormatException e)
				{
					throw new Exception("incorrect price format");
				}

			}
		}
	}

	/*
	 * TODO
	 * Optimize roundup and  approximation with 0.5 steps
	 */
	public static Receipt generateReceipt(List<BasketItem> basket)
	{
		if (basket != null)
		{
			Receipt receipt = new Receipt();
			receipt.setTotal(new BigDecimal("0.0"));
			receipt.setTaxComponent(new BigDecimal("0.0"));
			receipt.setItems(basket);
			for (BasketItem singleItem : basket)
			{

				if (singleItem.getMatchedItem().getCategory().isTaxableBasic())
				{
					BigDecimal conditionalIncrese = (singleItem.getPrice().multiply(new BigDecimal("10"))).divide(new BigDecimal("100"));
					BigDecimal fixedIncrese = new BigDecimal("0.0");
					if (singleItem.getMatchedItem().isImported())
					{
						fixedIncrese = ((singleItem.getPrice()).multiply(new BigDecimal("5"))).divide(new BigDecimal("100"));
					}
					BigDecimal taxComponent = conditionalIncrese.add(fixedIncrese);
					taxComponent = ensureZeroFiveStep(taxComponent);
					
					singleItem.setPriceWithBaseTax(taxComponent.add(singleItem.getPrice()));
					singleItem.setFinalPrice(singleItem.getPriceWithBaseTax());
					if (singleItem.getQuantity().compareTo(new BigInteger("0")) == 1)
					{
						receipt.setTotal(singleItem.getFinalPrice().add(receipt.getTotal()));
						receipt.setTaxComponent(taxComponent.add(receipt.getTaxComponent()));
					}
				}
				else
				{
					BigDecimal fixedIncrese = new BigDecimal("0.0");
					if (singleItem.getMatchedItem().isImported())
					{
						fixedIncrese = (singleItem.getPrice().multiply(new BigDecimal("5"))).divide(new BigDecimal("100"));
					}
					BigDecimal taxComponent = fixedIncrese;
					
					taxComponent = ensureZeroFiveStep(taxComponent);

					singleItem.setPriceWithBaseTax(taxComponent.add(singleItem.getPrice()));
					singleItem.setFinalPrice(singleItem.getPriceWithBaseTax());
					if (singleItem.getQuantity().compareTo(new BigInteger("0")) == 1)
					{
						receipt.setTotal(singleItem.getFinalPrice().add(receipt.getTotal()));
						receipt.setTaxComponent(taxComponent.add(receipt.getTaxComponent()));
					}
				}
			}
			//receipt.setTaxComponent(roundToFive(receipt.getTaxComponent(), 2));
			receipt.setTaxComponent(round(receipt.getTaxComponent(),2));
			return receipt;
		}
		else
		{
			return null;
		}
	}

	private static BigDecimal round(BigDecimal d, int scale, int roundingMode) {
		return d.setScale(scale, roundingMode);
	}

	private static BigDecimal roundToFiveCeiling(BigDecimal d, int scale) {
		BigDecimal twenty = new BigDecimal(20);
		BigDecimal intermediate = d.multiply(twenty).add(new BigDecimal("0.5")).setScale(scale, BigDecimal.ROUND_CEILING);
		intermediate = intermediate.divide(twenty);
		return intermediate.setScale(scale, BigDecimal.ROUND_HALF_DOWN);
	}

	private static BigDecimal round(BigDecimal d, int scale) {
		return d.setScale(scale, BigDecimal.ROUND_HALF_EVEN);
	}
	
	private static BigDecimal ensureZeroFiveStep(BigDecimal taxComponent)
	{
		try
		{
			taxComponent = round(taxComponent,2, BigDecimal.ROUND_UNNECESSARY);
		}
		catch (ArithmeticException e)
		{
			BigDecimal ceiled = null;

			ceiled = roundToFiveCeiling(taxComponent,2);

			String represent = ceiled.toString();
			if (!represent.endsWith("0") && !represent.endsWith("5"))
			{
				ceiled = round(taxComponent,2);
			}

			taxComponent = ceiled;
			
			represent = taxComponent.toString();
			
			while (!represent.endsWith("0") && !represent.endsWith("5"))
			{
				ceiled = roundToFiveCeiling(ceiled,2);
				represent = ceiled.toString();
			} 
			
			taxComponent = ceiled;
		}
		return taxComponent;
	}

}
