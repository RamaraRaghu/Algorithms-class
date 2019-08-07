import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//Rakshith Raghu, rr5de
//RESULTS
/*
* n     tries
*1000     999 (guest635)
*400      399 (ava)
*300      299 (dylan)
*200      199 (gabrielle)
*150      149 (emily)
*100      99  (max)
*10       9   (dominic)
*5        4   (b)
*4        3   (ringo)
 * */

public class Celebrity {


	// Implement this method
	public String findCelebrity(List<String> guests) {
		/*
		 *   Complete this method so that it
		 *   returns the guest who is the celebrity.
		 *
		 *   Input is a list of people (Strings) at the party.
		 *
		 *   You must use the knows method below
		 *   to check who knows whom.
		 */
		System.out.println("ran size :"  + guests.size());
		if(guests.size() == 2){
			if(knows(guests.get(0), guests.get(1)) == true){
				return guests.get(1);
			}
			else{
				return guests.get(0);
			}
		}
		if(knows(guests.get(0), guests.get(1)) == true){
			guests.remove(0);
		}
		else{
			guests.remove(1);
		}
		//if the funnel_down list is between n-1 and 1 size noninclusive
		return findCelebrity(guests);
	}

	/************************************************
	 * Under penalty of the Honor Code  
	 * Do Not Change Anything Below Here 
	 * (In your final submission)        
	 ************************************************/

	// Helper method. Will say if the first parameter knows the second parameter.
	// if a knows b, returns true.  Otherwise, returns false.
	public boolean knows(String a, String b) {
		countOfCallsToKnows++;
		if (knowsWhoFileStorageMap.get(a).contains(b))
			return true;
		return false;
	}


	/************************************************
	 * Under penalty of the Honor Code  
	 * Do not use anything (fields OR methods) 
	 * initialized or defined below
	 * (In your final submission)        
	 ************************************************/


	// Field (DO NOT CHANGE)
	private HashMap<String, List<String>> knowsWhoFileStorageMap;
	private int countOfCallsToKnows = 0;

	public void run() {
		knowsWhoFileStorageMap = new HashMap<>();

		// Read in the input file
		try (BufferedReader br = new BufferedReader(new FileReader("party.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.isEmpty()) continue;
				String[] parts = line.split(" ");
				if (parts.length > 0) {
					String subject = parts[0];
					ArrayList<String> knows = new ArrayList<>();
					for (int i = 1; i < parts.length; i++)
						knows.add(parts[i]);
					knowsWhoFileStorageMap.put(subject, knows);
				}
			}        

			// Call findCelebrity and print the result
			System.out.println(findCelebrity(new ArrayList<String>(knowsWhoFileStorageMap.keySet()))
					+ " " + countOfCallsToKnows);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error occurred when reading file");
		}

	}

	public static void main(String[] args) {
		Celebrity cp = new Celebrity();
		cp.run();
	}

}
