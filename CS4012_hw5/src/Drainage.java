import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.math.*;


/*
 * Rakshith Raghu, rr5de
 * My algorithm works in a mildly similar way to a greedy algorithm
 * First it converts every index into a Cell object containing a length and a value
 * I use 3 for loops in this manner: for( for-, for-,) to iterate through all the indexes twice
 * I then interate through all the indexes again in the opposite direction. So each index is hit at
 * 4 times. When an index is checked, the four directions and whether its value is greater than any
 * of these four directions. If so, it stores 1 + the length given by the index in that direction. If not, it
 * stores 0 for that direction. It then compares these 4 lengths for a max, which is returned and stored in the cell
 * 
 * 
 * I do this 4 times for each index. The first time to initialize a value (which updates for east), 
 * the 2nd time to update for the east. The third and fourth time updates from bottom to top (whereas the initial 2
 * times do so from top to bottom). This makes sure indexes are updated for north, south, east, and west. 
 * 
 * thus, the method is similar to greedy method, but without having each index check every route. It just checks the lengths given
 * by another index in the 4 directions. O(4 x 4 x (rows x columns) )
 * 
 * For dynamic programming, this method would be considered iterative, and uses a memory structure in the index
 * itself. It is, however, not directly recursive.
 * 
 * */

public class Drainage {
	
	//main method
	public static void main(String[] args){
		int size = 0;
		BufferedReader input;
		try {
			//gets the amount of tests
			int tests = 0;
			input = new BufferedReader(new FileReader("map.txt"));
			String line = input.readLine();
			tests = Integer.parseInt(line);
			
			//getting the cities list names and the array for outputting results
			String[] cities = new String[tests];
			int[] results = new int[tests];
			
			int row = 0;
			int[] rows = new int[tests]; 
			int column = 0;
			int[] columns = new int[tests];
			String[] splitter_one = new String[3];
			
			ArrayList<Cell[][]> maps = new ArrayList<Cell[][]>();

			for(int i = 0 ; i < tests; i++){
				line = input.readLine();
				splitter_one = line.split(" ");
				//stores cities
				cities[i] = splitter_one[0];
				//stores the rows and columns
				row = Integer.parseInt(splitter_one[1]);
				rows[i] = row;
				column = Integer.parseInt(splitter_one[2]);
				columns[i] = column;
				maps.add(new Cell[row][column]);
				
				for(int z = 0; z < row; z++ ){
					line = input.readLine();
					String[] row_e = line.split(" ");
					for(int l = 0; l < column; l++ ){
						maps.get(i)[z][l] = new Cell(Integer.parseInt(row_e[l]));
					}
				}
			}
			
			
			//list of maps, list of cities, lists of rows and column numbers gotten
			//actually runs the algorithm here
			Drainage dr = new Drainage();
			for(int b = 0; b < tests; b++){
				results[b] = dr.DrainageAlgorithm(maps.get(b), rows[b], columns[b]);
				System.out.println(cities[b] + ":  " + results[b]);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//method for actual operation
	//Cell object has a value and stored memory of length
	//each index has a length stored at 0. updates iteratively based on the lengths of the indexes to the left
	//and right, north and south. first iterates right, then iterates left. Then iterates bottom to top.
	int DrainageAlgorithm(Cell[][] map, int rows, int columns){
		int max_length = 0;
		Drainage dr = new Drainage();
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				map[i][j].length = dr.check(map, i, j);
				if(map[i][j].length > max_length){
					max_length = map[i][j].length;
				}
			}
			for(int j = columns -1; j >= 0; j--){
				map[i][j].length = dr.check(map, i, j);
				if(map[i][j].length > max_length){
					max_length = map[i][j].length;
				}
			}
		}
		
		for(int i = rows - 1; i >= 0; i--){
			for(int j = 0; j < columns; j++){
				map[i][j].length = dr.check(map, i, j);
				if(map[i][j].length > max_length){
					max_length = map[i][j].length;
				}
			}
			for(int j = columns -1; j >= 0; j--){
				map[i][j].length = dr.check(map, i, j);
				if(map[i][j].length > max_length){
					max_length = map[i][j].length;
				}
			}
		}
		
		return max_length + 1;
	}
	
	
	int check(Cell[][] map, int row, int col){
		int north = 0;
		int south = 0;
		int west = 0;
		int east = 0;
		
		//check edge cases
		if(row == 0){
			north = 0;
		}
		else{
			if(map[row][col].value > map[row-1][col].value){
				north = 1 + map[row-1][col].getLength();
			}
		}
		if(row == map.length - 1){
			south = 0;
		}
		else{
			if(map[row][col].value > map[row+1][col].value){
				south = 1 + map[row+1][col].getLength();
			}
		}
		if(col == 0){
			west = 0;
		}
		else{
			if(map[row][col].value > map[row][col-1].value){
				west = 1 + map[row][col-1].getLength();
			}
		}
		if(col == map[row].length - 1){
			east = 0;
		}
		else{
			if(map[row][col].value > map[row][col+1].value){
				east = 1 + map[row][col+1].getLength();
			}
		}
		
		int updown = Math.max(north, south);
		int rightleft = Math.max(east, west);
		
		return Math.max(updown, rightleft);
	}

}
