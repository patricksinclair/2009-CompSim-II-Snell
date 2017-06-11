import java.io.IOException;



public class SnellMain {
	public static void main(String[] args) throws IOException{

		SnellFrame sf = new SnellFrame();
		
		Lightbeam s1 = Lightbeam.getData();

		System.out.printf("The diffracted angle is: %.2f", s1.t2());

		Lightbeam.writeFile(Lightbeam.readFile("snellin"));
		Lightbeam.writeFileTable(Lightbeam.readFile("snellTable"));

	}	
}
