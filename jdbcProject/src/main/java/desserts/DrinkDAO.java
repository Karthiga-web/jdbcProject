package desserts;

public interface DrinkDAO extends GenericDAO<DrinkDTO> {
	DrinkDTO productInfo(int i);
}
