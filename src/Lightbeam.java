import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Lightbeam {

	private double theta1, n1, n2;

	public Lightbeam(double theta1, double n1, double n2){
		this.theta1 = theta1;
		this.n1 = n1;
		this.n2 = n2;
	}


	public double getT1(){
		return theta1;
	}
	public void setT1(double theta1){
		this.theta1 = theta1;
	}
	public double getN1(){
		return n1;
	}
	public void setN1(double n1){
		this.n1 = n1;
	}
	public double getN2(){
		return n2;
	}
	public void setN2(double n2){
		this.n2 = n2;
	}


	public static Lightbeam getData(){

		Scanner keyboard = new Scanner(System.in);

		double theta1 = 0, n1 = 0, n2 = 0;

		while(true){
			try{
				System.out.println("Please enter a value for the incident angle: ");

				theta1 = keyboard.nextDouble();

				if(theta1 > 0 && theta1 < 90) break;

				System.out.println("The angle must be between 0 & 90.");

			}catch(InputMismatchException e){
				System.out.println("Please input a number.");
				keyboard.nextLine();
			}
		}


		while(true){
			try{
				System.out.println("Please enter a value for n1: ");
				n1 = keyboard.nextDouble();

				if(n1 >= 1) break;

				System.out.println("n1 must be at least 1.");
			}catch(InputMismatchException e){
				System.out.println("Please enter a number.");
				keyboard.nextLine();
			}
		}


		while(true){
			try{
				System.out.println("Please enter a value for n2: ");
				n2 = keyboard.nextDouble();

				if(n2 > 1) break;

				System.out.println("n2 must be at least the same as n1 or reflection will occur.");
			}catch(InputMismatchException e){
				System.out.println("Please enter a number.");
				keyboard.nextLine();
			}
		}


		return new Lightbeam(theta1, n1, n2);
	}


	public double t2(){

		double sin1 = Math.sin(Math.toRadians(getT1()));
		double sin2 = (getN1()*sin1)/getN2();
		double theta2L = Math.asin(sin2);

		return Math.toDegrees(theta2L);
	}


	public double crit(){
		
		double ni = getN1();
		double nr = getN2();
		double thetaR;
		
		
		
		thetaR = Math.asin(nr/ni);
		
		return Math.toDegrees(thetaR);
	}

	
	public static ArrayList<Lightbeam> readFile(String filename) throws IOException{

		ArrayList<Lightbeam> lightbeams = new ArrayList<Lightbeam>();

		BufferedReader infile = new BufferedReader(new FileReader(new File(filename)));
		String line = null;

		while ((line = infile.readLine()) != null) {

			if(line.trim().indexOf('*') == 0)
				continue;
			String[] tokens = line.split(" / ");

			double theta1F = Double.parseDouble(tokens[0]);
			double n1F = Double.parseDouble(tokens[1]);
			double n2F = Double.parseDouble(tokens[2]);

			lightbeams.add(new Lightbeam(theta1F, n1F, n2F));
		}

		infile.close();

		return lightbeams;
	}



	public static void writeFile(ArrayList<Lightbeam> lightbeams)throws IOException{

		String filename = "snellOut"+".txt";

		BufferedWriter outfile = new BufferedWriter(new FileWriter(new File(filename)));

		for(int i = 0; i < lightbeams.size(); i++){

			double t2 = lightbeams.get(i).t2();
			String outs  = String.format("Refraction angle for beam no.%d is: %.2f\n", (i+1), t2);
			outfile.write(outs, 0, outs.length());
		}

		outfile.close();
	}

	

	public static void writeFileTable(ArrayList<Lightbeam> lightbeams)throws IOException{

		String filename = "snellTable"+".txt";

		BufferedWriter outfile = new BufferedWriter(new FileWriter(new File(filename)));

		for(int i = 0; i < lightbeams.size(); i++){

			double t2 = lightbeams.get(i).t2();
			double crit = lightbeams.get(i).crit();
			String outs = null;
			
			if(i == 0)	outs = String.format("Refraction angle for air to air is: %.2f\n"
					+ " and the critical angle is: %.2f\n", t2, crit);
			if(i == 1)	outs = String.format("Refraction angle for air to water is: %.2f\n"
					+ " and the critical angle is: %.2f\n", t2, crit);
			if(i == 2)	outs = String.format("Refraction angle for air to glass is: %.2f\n"
					+ " and the critical angle is: %.2f\n", t2, crit);
			if(i == 3)	outs = String.format("Refraction angle for air to diamond is: %.2f\n"
					+ " and the critical angle is: %.2f\n", t2, crit);
			
			outfile.write(outs, 0, outs.length());
			
		}

		outfile.close();
	}


}
