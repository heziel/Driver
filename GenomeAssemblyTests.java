import java.util.ArrayList;

public class GenomeAssemblyTests {

	/*
	 * Testing the algorithm using a sample DNA file.
	 * Input:  Text File with At most 50 DNA strings
	 * Output: A shortest superstring containing all the given strings. 
	 * 
	 * Running the program e.g : GenomeAssemblyTests rosalind_long.txt
	 * Result output - superstring.txt
	 */
	
	public static void main(String[] args) {

		ArrayList<String> myArr = null;
		
		// get the strings array from a file
		myArr = GenomeAssembly.parseDNAStringsFile(args[0]);
		
		if ( myArr == null )
			System.out.println("Format: GenomeAssemblyTests rosalind_long.txt");	
		else
			GenomeAssembly.generateSuperString(myArr) ;
			
	}	
}