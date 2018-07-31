import java.util.*;
public class Pathfinding {

	
	public static LinkedList<Node> Depthwise (Node start, Node end){
		Stack<Node> work = new Stack<Node> (); 
		List<Node> visited = new LinkedList<Node> ();
		
		work.push(start);
		visited.add (start);
		start.history = new LinkedList<Node> (); // El primer nodo no tiene historia.

		while (work.size() > 0) {
			Node current = work.pop ();
			if (current == end) {
				LinkedList <Node> result = current.history;
				result.add (current);
				return result;
			} else{
				for(int i = 0 ; i< current.neighbors.length; i++){
					Node currentChild = current.neighbors[i];
					if(!visited.contains(currentChild)){
						work.push (currentChild);
						visited.add (currentChild);
						currentChild.history = new LinkedList<Node> (current.history);
						currentChild.history.add (current);
					}
				}
			}
		}
		return null;
	}
	
	public static int getDistance(Node start, Node end){
		LinkedList <Node> result = Depthwise(start, end);
		int distance = 0;
		
		for(int i =0; i < result.size()-1;i++){
			distance += result.get(i).distances.get(result.get(i+1).name);
		}
		
		return distance;
	}
	
	
	public static LinkedList<Node> AStar(Node start, Node end){
		
		if (start.equals(end)){
			return null;
		}
		List<Node> visited = new LinkedList<Node> ();
		List<Node> work = new LinkedList<Node> ();

		start.history = new LinkedList<Node> ();
		start.origen = 0;
		start.destino = getDistance(start, end);
		int total = start.destino;

		visited.add (start);
		work.add (start);

		while(work.size() > 0){

			// get the current one 
			Node current = work.get(0);

			for(int i = 0; i < work.size(); i++){

				// check if answer is here
				if(work.get(i) == end){

					// return path
					LinkedList<Node> result = work.get(i).history;
					result.add (work.get(i));
					return result;
				}

				if (work.get(i).getSuma() < current.getSuma()) {
				
					current = work.get(i);
				}
			}

			work.remove (current);

			// traverse children
			for(int i = 0; i < current.neighbors.length; i++){

				Node currentChild = current.neighbors [i];
				if(!visited.contains(currentChild)){

					visited.add (currentChild);

					currentChild.history = new LinkedList<Node> (current.history);
					currentChild.history.add (current);

					// g - certain
					currentChild.origen = current.origen + current.distances.get(currentChild.name);
					//currentChild.origen = current.origen + Vector3.Distance(current.transform.position, currentChild.transform.position);

					// h - heuristic
					currentChild.destino = total - currentChild.origen;
					
					//currentChild.destino = Vector3.Distance(currentChild.transform.position, end.transform.position);

					work.add (currentChild);
				}
			}
		}

		return null;
	}
	
	public static void main(String[] args) {
		Node[] estados = new Node[8];
		
		Node n1 = new Node("JAL");
		Node n2 = new Node("ZAC");
		Node n3 = new Node("AGS");
		Node n4 = new Node("SON");
		Node n5 = new Node("HER");
		
		Node[] n1_neighbors = {n2};
		Node[] n2_neighbors = {n3,n4};
		Node[] n3_neighbors = {n2,n4};
		Node[] n4_neighbors = {n2,n3,n5};
		Node[] n5_neighbors = {n4};
		
		n1.neighbors = n1_neighbors;
		n2.neighbors = n2_neighbors;
		n3.neighbors = n3_neighbors;
		n4.neighbors = n4_neighbors;
		n5.neighbors = n5_neighbors;
		
		n1.printNeighbors();
		System.out.println("==========");
		n2.printNeighbors();
		System.out.println("==========");
		n3.printNeighbors();
		System.out.println("==========");
		n4.printNeighbors();
		System.out.println("==========");
		n5.printNeighbors();
		System.out.println("=====ROUTE====");

		List<Node> result = Depthwise(n1,n5);
		
		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i).name);
		}
		
		
		
		
		
	}
}
