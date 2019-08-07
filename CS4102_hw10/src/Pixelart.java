import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;


/*IDEA: convert the problem to a max-bipartite matching problem. 
 * First step I read line by line
 * The grid is treated like a chessboard, with tiles being considered white or black
 * This is how the dominos are converted. White are to be matched with black
 * I get edges by using pixel_grid as a adjacency matrix. 
 * Then, run on each white tile, find a black tile to augment path with. If it tries to augment
 * the path already taken by another pile, it asks if that path can be changed. If not, then
 * return false. I add to the count of dominos placed each time it returns true. If count is less
 * then pixels/2, then I return impossible. I also return impossible if the amount of black tiles
 * do not match the amount of white tiles. The matching array in the class is used to store the 
 * matches.
*/

//Used geeksforgeeks with help on building the Ford-fulkison algorithm on which
//dominos and augment path are based on.
public class Pixelart {
	public int x = 0;
	public int y = 0;
	boolean[][] pixel_grid;  //adjacency matrix represented here
	int[] matches;
	ArrayList<pixel> white;  //list of white pixels
	ArrayList<pixel> black;  //list of black pixels
	
	
	public static void main(String[] args){
		BufferedReader input;
		Pixelart object = new Pixelart();
		try {
			input = new BufferedReader(new FileReader("art.txt"));
			String line = input.readLine();
			
			String[] splitter = new String[2];
			splitter = line.split(" ");
			
			object.x = Integer.parseInt(splitter[0]);
			object.y = Integer.parseInt(splitter[1]);
			
			object.pixel_grid = new boolean[object.x][object.y];
			object.white = new ArrayList<pixel>();
			object.black = new ArrayList<pixel>();
			
			String[] row = new String[object.x];
			
			for(int i = 0; i < object.y; i++){ 
				//iterates over each row
				line = input.readLine();
				row = line.split("");
				for(int z = 0; z < object.x; z++){  
					//iterates over columns in row
					if(row[z].equals(".")){
					}
					if(row[z].equals("#")){
						pixel temp = new pixel(i,z);
						if(i % 2 == 0){
							if(z % 2 == 0){
								object.white.add(temp);
			
							}
							else{
								object.black.add(temp);
							}
						}
						else{
							if(z % 2 == 0){
								object.black.add(temp);
							}
							else{
								object.white.add(temp);
							}
						}
						//object.pixel_grid[i][z] = true;
					}
				}
			}
			//really messed up chess board produced
			if(object.white.size() != object.black.size()){
				System.out.println("Impossible");
			}
			else{
				int temp_x = 0;
				int temp_y = 0;
				
				int bx = 0;
				int by = 0;
				
				boolean impossible = false;
				object.pixel_grid = new boolean[object.white.size()][object.black.size()];
				
				for(int i = 0; i < object.white.size(); i++) {
					ArrayList<pixel> temp = new ArrayList<pixel>();
					temp_x = object.white.get(i).x;
					temp_y = object.white.get(i).y;
					for(int z = 0; z < object.black.size(); z++){
						object.pixel_grid[i][z] = false;
						bx = object.black.get(z).x;
						by = object.black.get(z).y;
						
						//four potential borders
						if(bx == temp_x && by == temp_y - 1){
							temp.add(object.black.get(z));
							object.pixel_grid[i][z] = true;
						}
						
						if(bx == temp_x && by == temp_y + 1){
							temp.add(object.black.get(z));
							object.pixel_grid[i][z] = true;
						}
						
						if(bx == temp_x -1 && by == temp_y){
							temp.add(object.black.get(z));
							object.pixel_grid[i][z] = true;
						}
						
						if(bx == temp_x + 1 && by == temp_y){
							temp.add(object.black.get(z));
							object.pixel_grid[i][z] = true;
						}
					}
				}
				
				object.matches = new int[object.black.size()];
				//index of matches is the black tile, value is the index of white tile in match
				for(int i = 0; i < object.matches.length; i++){
					//given value -1 to show that there are no matches to any tiles currently
					object.matches[i] = -1;
				}
				
				//run the algorithm
				object.dominoCover();
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//algorithm attempts to match white tiles to black tiles
	//bipartiate graph trying to match white to black
	//given: arraylist of white pixels
	//arraylist of black pixels
	//arraylist<arraylist<pixels>) which is adjacency list
	//grid of pixel location
	
	private void dominoCover() {
		boolean impossible = false;
		boolean found = false;
		
		
		int max_number = 0;
		for(int i = 0; i < this.white.size(); i++){
			//for each new white node, set all black nodes as unvisited
			boolean checked[] = new boolean[this.black.size()];
			for(int z = 0; z < this.black.size(); z++){
				checked[z] = false;
			}
			found = this.augment(i, checked);
			if(found == true){
				max_number = max_number + 1;
			}
		}
		
		if(max_number != this.matches.length){
			System.out.println("Impossible");
		}
		else{
			int bx = 0;
			int by = 0;
			
			int wx = 0;
			int wy = 0;
			
			for(int i = 0; i < this.matches.length; i++){
				bx = this.black.get(i).x;
				by = this.black.get(i).y;
				
				wx = this.white.get(this.matches[i]).x;
				wy = this.white.get(this.matches[i]).y;
				
				System.out.println(by + " " + bx + " " + wy + " " + wx);
			}
		}
		
		
	}
	
	//path is augmented by finding a black node that hasnt been filled, and filling it
	//also searches for another black node for another if thats the only possible one.
	private boolean augment(int white_node, boolean[] checked){
		for(int i = 0; i < this.black.size(); i++){
			if(this.pixel_grid[white_node][i] == true && checked[i] == false){
				checked[i] = true;
				if(this.matches[i] == -1 || this.augment(this.matches[i], checked) == true){
					this.matches[i] = white_node;
					return true;
				}
				
			}
		}
		return false;
	}
	
}
