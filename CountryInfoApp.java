import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.BufferedWriter;



public class CountryInfoApp {
	private String writeFilePath;
	private CountryInfo[] countriesInfo = new CountryInfo [48];
	
	//constructor
	public CountryInfoApp(String writeFilePath)
	{
		this.writeFilePath=writeFilePath;
	}
	public void run() {process(); write();}
	//processing the app - Nathan Tundrea
	private void process()
	{
		try {
			URL address = new URL("https://operationworld.org/locations/europe/");
			Scanner scan = new Scanner(address.openStream());
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				if(line.contains("list-group small list-group-flush")) 
				{
					line=scan.nextLine();
					line=line.substring(line.indexOf("href=")+5);
					String[] firstSplit=line.split("href=");
					int i=0;
					for (String link : firstSplit)
					{
						//pt test
						System.out.println("https://operationworld.org"+link.substring(1, link.indexOf('>')-1));
						countriesInfo[i] = getInfo("https://operationworld.org"+link.substring(1, link.indexOf('>')-1));
						i++;
					}
					
					break;
				}
			}
		}
		catch(IOException e){
			System.out.println("Link invalid");
		}
		
		//for printing some results
//		System.out.println();
//		System.out.println("Results");
//		
//		for (CountryInfo obj : countriesInfo)
//		{
//			System.out.println(obj.Name+", "+obj.Population+", "+obj.EvangelicalPercent+"%");
//		}
//		System.out.println();
	}
	
	//extracting country's info from their link - Caleb Motiu
	private static CountryInfo getInfo(String link) {
        String country = null;
        long populationResult = 0;
        double evangelicalResult = 0;

        try {
            URL address = new URL(link);
            try (Scanner scan = new Scanner(address.openStream())) {
                while (scan.hasNextLine()) 
                {
                    String line = scan.nextLine();

                    if (line.contains("<th scope=\"row\">Population:</th>")) 
                    {
                        String population = scan.nextLine();
                        int startingIndex = population.indexOf("<td>");
                        int endingIndex = population.indexOf("</td>");
                        String stringResult = population.substring(startingIndex + 4, endingIndex);
                        String newStringResult = stringResult.replace(",", "");
                        populationResult = Long.parseLong(newStringResult);
                    }

                    if (line.contains("Pray for:")) 
                    {
                        int startingIndex = line.indexOf(":") + 2;
                        int endingIndex = line.indexOf("                </h1>");
                        if (line.contains("Germany"))
                        {
                        	startingIndex= line.indexOf("G");
                        	endingIndex= line.indexOf("ny")+2;
                        }
                        country = line.substring(startingIndex, endingIndex);
                    }

                    if (line.contains("<th scope=\"row\">% Evangelical:</th>")) 
                    {
                        String evangelical = scan.nextLine();
                        int startingIndex = evangelical.indexOf("<td>");
                        int endingIndex = evangelical.indexOf("</td>");
                        String stringResult = evangelical.substring(startingIndex + 4, endingIndex - 2);
                        String newStringResult = stringResult.replace(",", "");
                        evangelicalResult = Double.parseDouble(newStringResult);
                    }
                }
            }
        } catch (MalformedURLException e) {
            System.err.println("Malformed URL: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error accessing URL: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + e.getMessage());
            e.printStackTrace();
        }

            return new CountryInfo(country, populationResult, evangelicalResult);
        
    }
	
	//writing the file - Nathan Tundrea
	private void write() {
		// Write the CSV file
		try {
	        StringBuilder output=new StringBuilder("");
            
	        // Build the header
	        output.append("Country, Population, Evangelical Percent\n");

            // Build the data
            for (CountryInfo obj : countriesInfo) {
            	output.append(obj.Name+", "+obj.Population+", "+obj.EvangelicalPercent+"%\n"); 
            } 
            System.out.println(output);
            
            // Open the file for writing
            FileWriter fileWriter = new FileWriter(writeFilePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write the content of the StringBuilder
            bufferedWriter.write(output.toString());

            // Close the writer
            bufferedWriter.close();
            fileWriter.close();
            System.out.println("Information was successfully written to CSV at: " + writeFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
