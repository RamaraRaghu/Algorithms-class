

//using a source https://www.geeksforgeeks.org/merge-sort/ for merge sort function
public class MergeSort {
	
	void mergeSortX(Tomato tomatos[], int left, int right){
		int middle = 0;
		if(left < right){
			middle = (right + left)/2;
		
			mergeSortX(tomatos, left, middle);
			mergeSortX(tomatos, middle + 1, right);
			
			mergeAllX(tomatos, left, middle, right);
		}
	}
	
	void mergeSortY(Tomato tomatos[], int left, int right){
		if(left < right){
			int middle = left + (right - left)/2;
		
			mergeSortY(tomatos, left, middle);
			mergeSortY(tomatos, middle + 1, right);
			
			mergeAllY(tomatos, left, middle, right);
		}
	}
	
	void mergeAllX(Tomato tomatos[], int left, int middle, int right){
		int leftsize = middle - left + 1;
		int rightsize = right - middle;

		Tomato L[] = new Tomato[leftsize];
		Tomato R[] = new Tomato[rightsize];
		
		for(int i= 0; i < leftsize; i++){
			L[i] = tomatos[i+left];
		}
		for(int j= 0; j < rightsize; j++){
			R[j] = tomatos[middle + j+1];
		}
		
		
		int i = 0;
		int j = 0;
		int k = left;
		
		while(i < leftsize && j < rightsize){
			if(L[i].getX() < R[j].getX()){
				tomatos[k] = L[i];
				i = i + 1;
			}
			else{
				tomatos[k] = R[j];
				j = j+1;
			}
			k = k+1;
		}
		
		while(i < leftsize){
			tomatos[k] = L[i];
			i++;
			k++;
		}
		
		while(j < rightsize){
			tomatos[k] = R[j];
			j = j+1;
			k= k+1;
		}
	}
	
	void mergeAllY(Tomato tomatos[], int left, int middle, int right){
		int leftsize = middle - left + 1;
		int rightsize = right - middle;

		Tomato L[] = new Tomato[leftsize];
		Tomato R[] = new Tomato[rightsize];
		
		for(int i= 0; i < leftsize; i++){
			L[i] = tomatos[i+left];
		}
		for(int j= 0; j < rightsize; j++){
			R[j] = tomatos[middle + j+1];
		}
		
		
		int i = 0;
		int j = 0;
		int k = left;
		
		while(i < leftsize && j < rightsize){
			if(L[i].getY() < R[j].getY()){
				tomatos[k] = L[i];
				i = i + 1;
			}
			else{
				tomatos[k] = R[j];
				j = j+1;
			}
			k = k+1;
		}
		
		while(i < leftsize){
			tomatos[k] = L[i];
			i++;
			k++;
		}
		
		while(j < rightsize){
			tomatos[k] = R[j];
			j = j+1;
			k= k+1;
		}
	}

}
