import java.util.List;

public class S_I {
	
	public int Signal;
	public int Index;
	
	/**
	 * creates a new S_I object with the signal and the index of that signal
	 * @param signal the signal of the point
	 * @param index index of the point
	 */
	public S_I(int signal, int index)
	{
		this.Signal=signal;
		this.Index=index;
	}
	
	
	@Override
	public String toString() {
		return "S_I [Signal=" + Signal + ", Index=" + Index + "]";
	}


	public int getSignal() {
		return Signal;
	}


	public void setSignal(int signal) {
		Signal = signal;
	}


	public int getIndex() {
		return Index;
	}


	public void setIndex(int index) {
		Index = index;
	}
	/**
	 * sorts the signal in a descending order by the signals
	 * @param arr List of S_I objects
	 */
	public void S_IbubbleSort(List<S_I> arr)
	{  
		int n = arr.size(); 
		S_I temp;
		for(int i=0; i < n; i++)
		{  
			for(int j=1; j < (n-i); j++)
			{ 
				int n1=(arr.get(j-1).Signal);
				int n2=(arr.get(j).Signal);
				if(n1 < n2){  
					//swap elements  
					temp = arr.get(j-1); 
					arr.set(j-1, arr.get(j));  
					arr.set(j, temp);
				}  

			}  
		}  
	}
	
}
