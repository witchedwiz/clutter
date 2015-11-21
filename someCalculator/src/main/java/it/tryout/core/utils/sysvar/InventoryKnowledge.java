package it.tryout.core.utils.sysvar;

import it.tryout.core.domain.bean.Category;
import it.tryout.core.domain.bean.VendoredUnitItem;

import java.util.HashMap;



/*
 * Act as a db in reference to item description, category, etc.. pre-tax price will be fed via simulted input
 */
public class InventoryKnowledge {

	private static HashMap<String, VendoredUnitItem> map = new HashMap<String, VendoredUnitItem>();
	
	public static void init()
	{
		/*
		 * define categories
		 */
		makeKnowledgeBooks();
		makeKnowledgeFood();
		makeKnowledgeMedicine();
		makeKnowledgeMusic();
		makeKnowledgePersonalCare();
		
	}
	
	public static void clean()
	{
		map.clear();
	}
	
	private static void makeKnowledgeMusic()
	{
		Category music = new Category();
		music.setCategoryId("L2");
		music.setCategoryName("music");
		music.setTaxableBasic(true);
		
		VendoredUnitItem vuM = new VendoredUnitItem();
		vuM.setItemId("music CD");
		vuM.setCategory(music);
		
		map.put("music CD", vuM);
	}
	
	private static void makeKnowledgeBooks()
	{
		Category books = new Category();
		books.setCategoryId("L1");
		books.setCategoryName("books");
		books.setTaxableBasic(false);
		
		VendoredUnitItem vuB = new VendoredUnitItem();
		vuB.setItemId("book");
		vuB.setCategory(books);
		
		map.put("book", vuB);
	}
	
	private static void makeKnowledgeFood()
	{
		Category food = new Category();
		food.setCategoryId("L3");
		food.setCategoryName("food");
		food.setTaxableBasic(false);
		
		VendoredUnitItem vuFa = new VendoredUnitItem();
		vuFa.setItemId("chocolate bar");
		vuFa.setCategory(food);
		
		VendoredUnitItem vuFb = new VendoredUnitItem();
		vuFb.setItemId("imported box of chocolates");
		vuFb.setImported(true);
		vuFb.setCategory(food);
		
		VendoredUnitItem vuFc = new VendoredUnitItem();
		vuFc.setItemId("box of imported chocolates");
		vuFc.setImported(true);
		vuFc.setCategory(food);
		
		
		map.put("chocolate bar", vuFa);
		map.put("imported box of chocolates", vuFb);
		map.put("box of imported chocolates", vuFc);
	}
	
	private static void makeKnowledgeMedicine()
	{
		Category medicine = new Category();
		medicine.setCategoryId("L4");
		medicine.setCategoryName("medicine");
		medicine.setTaxableBasic(false);
		
		VendoredUnitItem vuMDa = new VendoredUnitItem();
		vuMDa.setItemId("packet of headache pills");
		vuMDa.setCategory(medicine);
		
		map.put("packet of headache pills", vuMDa);
	}
	
	private static void makeKnowledgePersonalCare()
	{
		Category personalCare = new Category();
		personalCare.setCategoryId("L5");
		personalCare.setCategoryName("personalCare");
		personalCare.setTaxableBasic(true);
		
		VendoredUnitItem vuPa = new VendoredUnitItem();
		vuPa.setItemId("imported bottle of perfume");
		vuPa.setImported(true);
		vuPa.setCategory(personalCare);
		
		VendoredUnitItem vuPb = new VendoredUnitItem();
		vuPb.setItemId("bottle of perfume");
		vuPb.setCategory(personalCare);
		
		map.put("imported bottle of perfume", vuPa);
		map.put("bottle of perfume", vuPb);
	}

	public static HashMap<String, VendoredUnitItem> getMap() {
		return map;
	}

}
