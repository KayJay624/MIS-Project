package badania;

public class Vertexx {
	public int id;
	Vertexx(int _id) {
		id = _id;
	}
	public int getId() {
		return id;
	}
	public String toString() {
		return String.valueOf(id+1);
	}
	
	public Integer toInteger() {
		return id;
	}
}
