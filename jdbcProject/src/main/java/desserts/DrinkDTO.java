package desserts;

public class DrinkDTO {

	Long id;
	String name;
	int cost;

	public DrinkDTO(Long id, String name, int cost2) {
		this.id = id;
		this.name = name;
		this.setCost(cost2);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static int compareByName(DrinkDTO a, DrinkDTO b) {
		return a.name.compareTo(b.name);
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost2) {
		this.cost = cost2;
	}

	@Override
	public String toString() {
		return "DrinkDTO{" + "id='" + id + "'," + "name='" + name + "'," + "cost=" + cost + "}";
	}

}
