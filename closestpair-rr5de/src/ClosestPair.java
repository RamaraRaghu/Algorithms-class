import java.io.BufferedReader;
import java.util.Arrays;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//importing line below
import java.math.*;

public class ClosestPair {
	
	private Tomato[] toms;
	private float smallest;
	
	public static void main(String[] args){
		int size = 0;
		BufferedReader input;
		try {
			//THIS SECTION IS THE READING IN OF THE STRING
			//
			//
			input = new BufferedReader(new FileReader("garden.txt"));
			//input = new BufferedReader(new FileReader("C:/Users/ramara/workspace/“closestpair-rr5de.java/src/garden.txt"));
			String line = input.readLine();
			size = Integer.parseInt(line);
			
			Tomato[] point_inputs = new Tomato[size];
			int i = 0;
			String[][] splitter = new String[size][2];
			line = input.readLine();

			
			while(line != null){
				splitter[i] = line.split(" ");
				point_inputs[i] = new Tomato(Float.parseFloat(line.substring(0, line.indexOf(' '))), Float.parseFloat(line.substring(line.indexOf(' ')+1)));
				i = i+1;
				line = input.readLine();
			}
			//
			//
			//WE NOW HAVE AN ARRAY OF TOMATO OBJECTS
			//find median of x cartesian points
			
			MergeSort xsort = new MergeSort();
			xsort.mergeSortX(point_inputs, 0, size-1);
			
			for(int i1 = 0; i1 < point_inputs.length; i1++){
			}
			
			ClosestPair finder = new ClosestPair(point_inputs,Float.MAX_VALUE);
			finder = finder.tomatoAlgorithm(finder.toms, size);
			System.out.println("the smallest distance is: " + finder.smallest);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ClosestPair tomatoAlgorithm(Tomato tomatos[], int size){
		//dividing into 3 rather than 2. Dont want a situation with only one tomato being checked. 3 or 2 is fine.
		if(size <= 3){
			return checkAll(tomatos, size);
		}
		
		//get the median? or the point in the middle of the set
		int middle = size/2;
		float middle_x = tomatos[middle].getX();
		ClosestPair leftside = tomatoAlgorithm(Arrays.copyOfRange(tomatos, 0, middle), middle);
		ClosestPair rightside = tomatoAlgorithm(Arrays.copyOfRange(tomatos,middle, size), size - middle);
		//so this should give the shortest of each side;
		
		float shorter = 0;
		//getting the smaller of the two sides
		if(leftside.smallest <= rightside.smallest){
			shorter = leftside.smallest;
		}
		else{
			shorter = rightside.smallest;
		}
		
		
		ClosestPair final_one = new ClosestPair(tomatos, shorter);
		final_one.mergeAllY(leftside.toms, rightside.toms);
		//running mergesort algorithm here
		int runway_size = 0;
		for(int i = 0; i < final_one.toms.length; i++){
			//added in abs here
			if (Math.abs(final_one.toms[i].getX() - middle_x) > final_one.smallest){
				runway_size = runway_size + 1;
			}
		}
		int m = 0;
		
		Tomato[] runway = new Tomato[runway_size];
		for(int i = 0; i < final_one.toms.length; i++){
			//added in abs here
			if (Math.abs(final_one.toms[i].getX() - middle_x) > final_one.smallest){
				runway[m] = final_one.toms[i];
				m = m+ 1;
			}
		}
		//NOW WE HAVE A LIST OF POINTS IN THE RUNWAY ORGANIZED BY Y IN SIZE
		//I only need compare to the next 15 higher?
		
		for(int i = 0; i < runway_size - 15; i++){
			for(int z = 1; z < 16; z++){
				//change here
				if(runway[i].distance_f(runway[i], runway[i+z]) < final_one.smallest){
					//change here
					final_one.smallest = runway[i].distance_f(runway[i], runway[i+z]);
				}
			}
		}
		
		
		return final_one;
	}
	
	//This is where 3 or 2 tomatos are left in the sublist. These are then brute force checked
	public ClosestPair checkAll(Tomato[] tomatos, int size){
		float dist_return = Float.MAX_VALUE;
		if(size == 3){
			for(int i = 0; i < 3; i++){
				for(int z = i+1; z < 3; z++){
					if(tomatos[i].distance_f(tomatos[i],tomatos[z]) < dist_return){
						dist_return = tomatos[i].distance_f(tomatos[i],tomatos[z]);
					}
				}
			}
		}
		else{
			dist_return = tomatos[0].distance_f(tomatos[0], tomatos[1]);
		}
		
		MergeSort sorter = new MergeSort();
		sorter.mergeSortY(tomatos, 0, size-1);
		return new ClosestPair(tomatos, dist_return);
	}
	
	//object declaration used to both report the smallest value and service the mergesort of Y coordinates
	public ClosestPair(Tomato toms[], float smallest){
		this.toms = toms.clone();
		this.smallest = smallest;
	}
	
	
	
	
	//method to finish combining all of the Y values
	void mergeAllY(Tomato tomatos[], Tomato tomatos1[]){
		int leftsize = tomatos.length;
		int rightsize = tomatos1.length;

		Tomato L[] = new Tomato[leftsize];
		Tomato R[] = new Tomato[rightsize];
		
		for(int i= 0; i < leftsize; i++){
			L[i] = tomatos[i];
		}
		for(int j= 0; j < rightsize; j++){
			R[j] = tomatos1[j];
		}
		
		
		int i = 0;
		int j = 0;
		int k = 0;
		
		while(i < leftsize && j < rightsize){
			if(L[i].getY() < R[j].getY()){
				this.toms[k] = L[i];
				i = i + 1;
			}
			else{
				this.toms[k] = R[j];
				j = j+1;
			}
			k = k+1;
		}
		
		while(i < leftsize){
			this.toms[k] = L[i];
			i++;
			k++;
		}
		
		while(j < rightsize){
			this.toms[k] = R[j];
			j = j+1;
			k= k+1;
		}
	}
}
