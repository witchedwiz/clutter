package someCalculator;

import static org.junit.Assert.*;

import java.util.List;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import it.tryout.core.domain.bean.BasketItem;
import it.tryout.core.domain.bean.Category;
import it.tryout.core.domain.bean.Receipt;
import it.tryout.core.domain.bean.VendoredUnitItem;
import it.tryout.core.service.OrderService;
import it.tryout.core.utils.sysvar.InventoryKnowledge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class Tester {
	
	static final Logger logger = LogManager.getLogger(Tester.class.getName());

	@BeforeClass
	public static void oneTimeSetUp() {
		// one-time initialization code   
		System.out.println("@BeforeClass - oneTimeSetUp");
		InventoryKnowledge.init();
	}

	@AfterClass
	public static void oneTimeTearDown() {
		// one-time cleanup code
		System.out.println("@AfterClass - oneTimeTearDown");
		InventoryKnowledge.clean();
	}

	@Test
	public void testInput1() {
		//fail("Not yet implemented");
		String row1 = "1 book at 12.49";
		String row2 = "1 music CD at 14.99";
		String row3 = "1 chocolate bar at 0.85";
		try
		{
			BasketItem bI1 = OrderService.fromStringToOrder(row1);
			BasketItem bI2 = OrderService.fromStringToOrder(row2);
			BasketItem bI3 = OrderService.fromStringToOrder(row3);
			
			/*
			 * setup expected response item1
			 */
			Category books = new Category();
			books.setCategoryId("L1");
			books.setCategoryName("books");
			books.setTaxableBasic(false);
			
			VendoredUnitItem vuB = new VendoredUnitItem();
			vuB.setItemId("book");
			vuB.setCategory(books);
			
			BasketItem expBI1 = new BasketItem();
			expBI1.setMatchedItem(vuB);
			expBI1.setPrice(new BigDecimal("12.49"));
			expBI1.setQuantity(new BigInteger("1"));
			
			assertTrue(bI1.equals(expBI1));
			
			/*
			 * setup expected response item2
			 */
			Category music = new Category();
			music.setCategoryId("L2");
			music.setCategoryName("music");
			music.setTaxableBasic(true);
			
			VendoredUnitItem vuM = new VendoredUnitItem();
			vuM.setItemId("music CD");
			vuM.setCategory(music);
			
			BasketItem expBI2 = new BasketItem();
			expBI2.setMatchedItem(vuM);
			expBI2.setPrice(new BigDecimal("14.99"));
			expBI2.setQuantity(new BigInteger("1"));
			
			assertTrue(bI2.equals(expBI2));
			
			/*
			 * setup expected response item3
			 */
			Category food = new Category();
			food.setCategoryId("L3");
			food.setCategoryName("food");
			food.setTaxableBasic(false);
			
			VendoredUnitItem vuFa = new VendoredUnitItem();
			vuFa.setItemId("chocolate bar");
			vuFa.setCategory(food);
			
			BasketItem expBI3 = new BasketItem();
			expBI3.setMatchedItem(vuFa);
			expBI3.setPrice(new BigDecimal("0.85"));
			expBI3.setQuantity(new BigInteger("1"));
			
			assertTrue(bI3.equals(expBI3));
			
			logger.debug("testInput1 passed");
			
		}
		catch (Exception e)
		{
			logger.warn("Exception",e);
			fail("Unexpected Exception");
		}
	}
	
	@Test
	public void testReceipt1() {
		//fail("Not yet implemented");
		String row1 = "1 book at 12.49";
		String row2 = "1 music CD at 14.99";
		String row3 = "1 chocolate bar at 0.85";
		try
		{
			BasketItem bI1 = OrderService.fromStringToOrder(row1);
			BasketItem bI2 = OrderService.fromStringToOrder(row2);
			BasketItem bI3 = OrderService.fromStringToOrder(row3);
			
			List<BasketItem> basket = new ArrayList<BasketItem>();
			basket.add(bI1);
			basket.add(bI2);
			basket.add(bI3);
			Receipt receipt = OrderService.generateReceipt(basket);
			
			System.out.println(receipt.toString());	
			
			assertTrue(receipt.getTotal().equals(new BigDecimal("29.83")));
			assertTrue(receipt.getTaxComponent().equals(new BigDecimal("1.50")));
			logger.debug("testReceipt1 passed");
			
					
		}
		catch (Exception e)
		{
			logger.warn("Exception",e);
			fail("Unexpected Exception");
		}
	}
	
	
	@Test
	public void testInput2() {
		//fail("Not yet implemented");
		String row1 = "1 imported box of chocolates at 10.00";
		String row2 = "1 imported bottle of perfume at 47.50";
		try
		{
			BasketItem bI1 = OrderService.fromStringToOrder(row1);
			BasketItem bI2 = OrderService.fromStringToOrder(row2);
			
			/*
			 * setup expected response item1
			 */
			Category food = new Category();
			food.setCategoryId("L3");
			food.setCategoryName("food");
			food.setTaxableBasic(false);
			
			VendoredUnitItem vuFb = new VendoredUnitItem();
			vuFb.setItemId("imported box of chocolates");
			vuFb.setCategory(food);
			
			BasketItem expBI1 = new BasketItem();
			expBI1.setMatchedItem(vuFb);
			expBI1.setPrice(new BigDecimal("10.00"));
			expBI1.setQuantity(new BigInteger("1"));
			
			assertTrue(bI1.equals(expBI1));
			
			/*
			 * setup expected response item2
			 */
			Category personalCare = new Category();
			personalCare.setCategoryId("L5");
			personalCare.setCategoryName("personalCare");
			personalCare.setTaxableBasic(true);
			
			VendoredUnitItem vuPa = new VendoredUnitItem();
			vuPa.setItemId("imported bottle of perfume");
			vuPa.setCategory(personalCare);
			
			BasketItem expBI2 = new BasketItem();
			expBI2.setMatchedItem(vuPa);
			expBI2.setPrice(new BigDecimal("47.50"));
			expBI2.setQuantity(new BigInteger("1"));
			
			assertTrue(bI2.equals(expBI2));
			
			logger.debug("testInput2 passed");
			
		}
		catch (Exception e)
		{
			logger.warn("Exception",e);
			fail("Unexpected Exception");
		}
	}
	
	@Test
	public void testReceipt2() {
		//fail("Not yet implemented");
		String row1 = "1 imported box of chocolates at 10.00";
		String row2 = "1 imported bottle of perfume at 47.50";
		try
		{
			BasketItem bI1 = OrderService.fromStringToOrder(row1);
			BasketItem bI2 = OrderService.fromStringToOrder(row2);
			
			List<BasketItem> basket = new ArrayList<BasketItem>();
			basket.add(bI1);
			basket.add(bI2);
			Receipt receipt = OrderService.generateReceipt(basket);
			
			System.out.println(receipt.toString());	
			
			assertTrue(receipt.getTotal().equals(new BigDecimal("65.15")));
			assertTrue(receipt.getTaxComponent().equals(new BigDecimal("7.65")));
			logger.debug("testReceipt2 passed");
			
					
		}
		catch (Exception e)
		{
			logger.warn("Exception",e);
			fail("Unexpected Exception");
		}
	}
	
	
	@Test
	public void testInput3() {
		//fail("Not yet implemented");
		String row1 = "1 imported bottle of perfume at 27.99";
		String row2 = "1 bottle of perfume at 18.99";
		String row3 = "1 packet of headache pills at 9.75";
		String row4 = "1 box of imported chocolates at 11.25";
		try
		{
			BasketItem bI1 = OrderService.fromStringToOrder(row1);
			BasketItem bI2 = OrderService.fromStringToOrder(row2);
			BasketItem bI3 = OrderService.fromStringToOrder(row3);
			BasketItem bI4 = OrderService.fromStringToOrder(row4);
			
			/*
			 * setup expected response item1 and item2
			 */
			Category personalCare = new Category();
			personalCare.setCategoryId("L5");
			personalCare.setCategoryName("personalCare");
			personalCare.setTaxableBasic(true);
			
			VendoredUnitItem vuPa = new VendoredUnitItem();
			vuPa.setItemId("imported bottle of perfume");
			vuPa.setCategory(personalCare);
			
			BasketItem expBI1 = new BasketItem();
			expBI1.setMatchedItem(vuPa);
			expBI1.setPrice(new BigDecimal("27.99"));
			expBI1.setQuantity(new BigInteger("1"));
			
			assertTrue(bI1.equals(expBI1));
			
			VendoredUnitItem vuPb = new VendoredUnitItem();
			vuPb.setItemId("bottle of perfume");
			vuPb.setCategory(personalCare);
			
			BasketItem expBI2 = new BasketItem();
			expBI2.setMatchedItem(vuPb);
			expBI2.setPrice(new BigDecimal("18.99"));
			expBI2.setQuantity(new BigInteger("1"));
			
			assertTrue(bI2.equals(expBI2));
			
			/*
			 * setup expected response item3
			 */
			Category medicine = new Category();
			medicine.setCategoryId("L4");
			medicine.setCategoryName("medicine");
			medicine.setTaxableBasic(false);
			
			VendoredUnitItem vuMDa = new VendoredUnitItem();
			vuMDa.setItemId("packet of headache pills");
			vuMDa.setCategory(medicine);
			
			BasketItem expBI3 = new BasketItem();
			expBI3.setMatchedItem(vuMDa);
			expBI3.setPrice(new BigDecimal("9.75"));
			expBI3.setQuantity(new BigInteger("1"));
			
			assertTrue(bI3.equals(expBI3));
			
			
			/*
			 * setup expected response item1
			 */
			Category food = new Category();
			food.setCategoryId("L3");
			food.setCategoryName("food");
			food.setTaxableBasic(false);
			
			VendoredUnitItem vuFc = new VendoredUnitItem();
			vuFc.setItemId("box of imported chocolates");
			vuFc.setCategory(food);
			
			BasketItem expBI4 = new BasketItem();
			expBI4.setMatchedItem(vuFc);
			expBI4.setPrice(new BigDecimal("11.25"));
			expBI4.setQuantity(new BigInteger("1"));
			
			assertTrue(bI4.equals(expBI4));
			
			logger.debug("testInput3 passed");
			
		}
		catch (Exception e)
		{
			logger.warn("Exception",e);
			fail("Unexpected Exception");
		}
	}
	
	@Test
	public void testReceipt3() {
		//fail("Not yet implemented");
		String row1 = "1 imported bottle of perfume at 27.99";
		String row2 = "1 bottle of perfume at 18.99";
		String row3 = "1 packet of headache pills at 9.75";
		String row4 = "1 box of imported chocolates at 11.25";
		try
		{
			BasketItem bI1 = OrderService.fromStringToOrder(row1);
			BasketItem bI2 = OrderService.fromStringToOrder(row2);
			BasketItem bI3 = OrderService.fromStringToOrder(row3);
			BasketItem bI4 = OrderService.fromStringToOrder(row4);
			
			List<BasketItem> basket = new ArrayList<BasketItem>();
			basket.add(bI1);
			basket.add(bI2);
			basket.add(bI3);
			basket.add(bI4);
			Receipt receipt = OrderService.generateReceipt(basket);
			
			System.out.println(receipt.toString());			
			
			assertTrue(receipt.getTotal().equals(new BigDecimal("74.68")));
			assertTrue(receipt.getTaxComponent().equals(new BigDecimal("6.70")));
			logger.debug("testReceipt3 passed");
			
			
		}
		catch (Exception e)
		{
			logger.warn("Exception",e);
			fail("Unexpected Exception");
		}
	}

}
