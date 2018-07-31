import java.util.*; 
public class Node {
	
	
	public LinkedList<Node> history;
	public Node[] neighbors;
	public String name; 
	public Hashtable<String, Integer> distances;
	
	public int origen;
	public int destino;
	public int suma;
		
	public int getSuma(){
		return origen + destino;
	}
	
	public Node(String name) {
		super();
		this.name = name;
		this.history =new LinkedList<Node>();
		this.distances = new Hashtable<>();
		
	}
	
	public void printNeighbors(){
		for (int i = 0; i < neighbors.length; i++) {
			System.out.println(neighbors[i].name);
		}
	}
	
	
	

/*	public static void main(String[] args) {
		int[] testNeighbors = {1,2,3,4,5,6};
		Node testNode = new Node(testNeighbors);
		
		
	}*/
}