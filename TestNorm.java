package badania;

import java.io.FileNotFoundException;

public class TestNorm {

	public static void main(String[] args) {
		GenAlgorythm alg = new GenAlgorythm();
		//try {
		//	alg.run(5, 2, 0.1, "", 500, 1000, null, null);
		//} catch (FileNotFoundException e) {
		//	// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		//alg.genGraph(500, 1000);
		alg.algNormal(500);
		//alg.writeToFile(500);

	}

}
