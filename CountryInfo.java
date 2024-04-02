
public class CountryInfo {
	public final String Name;
	public final long Population;
	public final double EvangelicalPercent;
	public CountryInfo(String Name, long Population, double EvangelicalPercent)
	{
		this.Name=Name;
		this.Population=Population;
		this.EvangelicalPercent=EvangelicalPercent;
		
	}
	
	//temporary method
	public void print() {System.out.println(Name+" has "+Population+" people and "+EvangelicalPercent+"% of them are Evangelical.");}
}
