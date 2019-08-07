
public class Cell {
	public int value;
	public int length;
	
	public Cell(int val){
		this.value = val;
		this.length = 0;
	}
	
	public int getVal(){
		return this.value;
	}
	
	public int getLength(){
		return this.length;
	}
	
	public void setLength(int newlength){
		this.length = newlength;
	}

}
