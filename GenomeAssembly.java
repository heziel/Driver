import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Algorithm:
 * A greedy algorithm based on the following idea, 
 * Choose a String to be your starting superString and begin finding Second DNA string from DNAStrings array which have the longest
 * Mutual overlap amongst all possible pairs in DNAStrings. 
 * When such found form the overlapped string from the two strings and connect it to our superString,
 * Remove the DNA string from DNSStrings Array.
 * Use the new superString to Match  more overlaps. 
 * Repeat this until DNSStrings array will be empty.
 * 
 * Assumption:
 * there exists a unique way to reconstruct the entire chromosome from these reads by gluing together pairs of reads that overlap by more than half their length.
 * 
 * Complexity: 
 * The algorithm runs in O(m^2 * k), where m is the number of strings in DNAStrings array, and k is one string length.
 * In the worse case, k = O(n) where n is the total length of the input, so the complexity will be O(m^2 * n)
 * In the average case, k = n/m, so the complexity will be O(m^2 * n/m) = O(mn)
*/

public class GenomeAssembly {
	
	private static final String ENCODING = "UTF-8";
	private static final String OUTPUT_FILE_NAME = "superstring.txt";
    private static final String STRINGS_DELIMITER= ">Rosalind_";
    
	/*
	 * This Method uses a greedy Algorithm approach.  
	 * The Method is running while we have strings in our String Array, 
	 * In each loop we search for overlap on our SuperString.
	 * Overlap means there is another string that have a Prefix matching our SuperString Suffix,
	 * or string that have a Suffix matching our SuperString Prefix ( we know there is such pair, because of the assumption above )
	 * Once we found such String we attach the remaining String to the superString , and remove our string from the DNSStrings array.
	 */
	public static void  generateSuperString( ArrayList<String> dnaStrings )
	{
		// open File For Writing	
		PrintWriter output = getOutputPrintWriter();
		
		int suffixStartIndex = -1;
		boolean foundOverlap = false;
		
		String superString = dnaStrings.remove(0);
	
		while ( dnaStrings.size() > 0 ){				

			suffixStartIndex++;
	
		    for ( String dna : dnaStrings ) {
		    	// starting from the middle because of the assumption.
		    	int middleDNALength = dna.length() / 2;
		    	
		    	// prefix/suffix will shrink/grow by 1 each loop until we will find an overlap.
		    	String suffix = dna.substring( middleDNALength - suffixStartIndex, dna.length());
		    	String prefix = dna.substring(0, middleDNALength + suffixStartIndex);
		    	
		    	// once we found an overlap we can write the remaining into the superString and continue our search for overlaps.
				if  ( superString.endsWith(prefix)  ){
					superString = superString + dna.substring( middleDNALength + suffixStartIndex , dna.length());
					foundOverlap = true;
				}
				else if ( superString.startsWith(suffix)  ){
					superString =  dna.substring(0, middleDNALength - suffixStartIndex) + superString;
					foundOverlap = true;
				}
				
				// once we found overlap, remove this dna string from the array.
				if ( foundOverlap ){
					dnaStrings.remove(dna);
					
					suffixStartIndex = -1;
					foundOverlap = false;
					
					break;	
				}
			}		    
		}		
	
		output.print(superString);
		output.close();	
	}	
	
	/*
	 * This Method parse the DNA Strings file into Array of Strings.
	 */
	public static ArrayList<String> parseDNAStringsFile( String dnaTextFile ){
	
		Scanner scanner = getInputScanner( dnaTextFile );
		ArrayList<String> DNAStrings = new ArrayList<String>();
		
		if ( scanner == null) return null;
        
		// skip the first line (<Rosalind..)
		scanner.nextLine();
		
		String currentString = "";
		String currentLine = "";		

		while ( scanner.hasNext() ) {
			currentLine = scanner.nextLine();
			
			if ( currentLine.startsWith(STRINGS_DELIMITER)){
				DNAStrings.add(currentString);	
				currentString = "";				
			}
			else 
				currentString = currentString.concat(currentLine);		    
		}		
	
		// at EOF insert the last string.
		DNAStrings.add(currentString);
		
		scanner.close();
	
		return DNAStrings;
	}

	/*
	 *  This Method creates a new output file, which will be used for writing the superString output file.
	 */
	private static PrintWriter getOutputPrintWriter() {
		PrintWriter output = null;
		try {
			output = new PrintWriter(OUTPUT_FILE_NAME, ENCODING);
		} catch (FileNotFoundException e) {
            System.out.println("ERROR: Output file was not Found");
        } catch (UnsupportedEncodingException e) {
            System.out.println("ERROR: Unsupport encoding");
        }
		return output;
	}
	
	/*
	 * This Method creates a Scanner that will be used for reading the DNA Strings.
	 */
	private static Scanner getInputScanner( String dnaTextFile) {		
		Scanner scanner =  null;
		try {		
			scanner = new Scanner(new FileInputStream(dnaTextFile));
		} catch (FileNotFoundException e) {		
			System.out.println("ERROR: DNA text File was not Found");
			
		}
		return scanner;	
	}
}