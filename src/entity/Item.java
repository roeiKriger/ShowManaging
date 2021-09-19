package entity;

public class Item 
{
	private int itemId;
	private String name;
	private int price;
	private String pic;
	
	public Item(int itemId, String name, int price, String pic) {
		super();
		this.itemId = itemId;
		this.name = name;
		this.price = price;
		this.pic = pic;
	}

	public int getItemId() {
		return itemId;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public String getPic() {
		return pic;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	@Override
	public String toString() {
		return  name + ", price=" + price+".00" ;
	}
	
	
	

}
