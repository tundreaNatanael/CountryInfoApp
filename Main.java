import java.net.URL;
import java.io.IOException;
import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		System.out.print("Dati o adresa de scriere a informatiilor: ");
		CountryInfoApp app=new CountryInfoApp(scan.nextLine());
		app.run();
	}

}
