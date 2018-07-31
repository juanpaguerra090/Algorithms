import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReadFile {

	private int zones;
	private int numberofvertices;

	Node[][] estados;
	private String[][] nombres;
	private int[][][] zoneMatrix;

	private int initialZone, finalZone;
	private String initialState, finalState; 
	private int initialPos, finalPos;

	public LinkedList<String> Path;
	public int recFinal;
	
	private int diferencial; 
	@SuppressWarnings("rawtypes")
	private LinkedList<Node>[] paths; 


	/**
	 * Constructor parametrizado para lectura de archivos. 
	 * @param initialState
	 * @param finalState
	 */
	public ReadFile(String initialState, String finalState, String fileName){
		this.initialPos = -1;
		this.finalPos = -1;
		this.initialState = initialState;
		this.finalState = finalState;

		this.readFile(fileName);
		this.getInitialStatus();
		this.addNodes();
		this.routeAStar();
		System.out.println(this.printPath());
	}

	/**
	 * Lectura de archivos por medio de Buffered Reader y StringTokenizer. Obtiene como archivo de texto los parametros del mapa
	 * tal como el número de zonas, la matriz de distancia entre las ciudades de las diferentes zonas.
	 */
	public void readFile(String fileName){
		try{

			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String linea = br.readLine();
			StringTokenizer st;
			zones = Integer.parseInt(linea);
			nombres = new String[zones][9];
			zoneMatrix = new int[zones][9][9];
			linea = br.readLine();
			while(linea!= null){
				st = new StringTokenizer(linea);

				for (int i = 0; i < zones; i++) {
					numberofvertices = Integer.parseInt(st.nextToken());
					linea = br.readLine();
					st = new StringTokenizer(linea);

					for (int j = 0; j < numberofvertices; j++){
						nombres[i][j] = st.nextToken();

					}

					linea = br.readLine();
					st = new StringTokenizer(linea);

					for(int j = 0; j < numberofvertices; j++ ){					
						for(int k = 0; k < numberofvertices; k++){
							zoneMatrix[i][j][k] = Integer.parseInt(st.nextToken());

						}
						linea = br.readLine();
						if(linea != null){
							st = new StringTokenizer(linea);
						}
					}	
				}
				linea = br.readLine();
				if(linea != null){
					st = new StringTokenizer(linea);
				}
			}	
			br.close(); 
		}
		catch(IOException e){
			System.out.println("Error de I/O " + e);
		}
	}

	public void addNodes(){
		this.estados =  new Node[zones][numberofvertices];
		for (int h = 0; h < this.zones; h++) {
			for (int i = 0; i < numberofvertices; i++) {
				estados[h][i] = new Node(this.nombres[h][i]);
			}
			for (int i = 0; i < numberofvertices; i++){
				int size = 0;
				int pos = 0;
				for (int j = 0; j < numberofvertices; j++) {
					if (zoneMatrix[h][i][j] !=0){
						size++;
					}
				}
				Node[] tmpNeighbors = new Node[size];
				for (int j = 0; j < numberofvertices; j++) {
					if (zoneMatrix[h][i][j] != 0){
						estados[h][i].distances.put( nombres[h][j], zoneMatrix[h][i][j]);
						tmpNeighbors[pos] = estados[h][j];
						pos++;
					}
				}
				estados[h][i].neighbors = tmpNeighbors;
			}
		}
	}

	/**
	 * Obtener posiciones, estados, zonas iniciales y finales del mapa para poder establecer la ruta por medio de zonas. 
	 * Se evalúa la ruta en base a la zona inicial (<i>origen</i>), y después en base a la zona final (<i>destino</i>). 
	 */
	public void getInitialStatus(){
		for (int i = 0; i < this.zones; i++) {
			for (int j = 0; j < nombres[1].length; j++) {
				if (nombres[i][j].equals(initialState)){
					this.initialPos = j; 
					this.initialZone = i;
				}
				if (nombres[i][j].equals(finalState)){
					this.finalPos = j; 
					this.finalZone = i;
				}
				if (initialPos >= 0 && finalPos >= 0 ){
					return; 
				}
			}
		}
	}

	/**
	 * Cálculo de las rutas entre zonas, establece el <i>PathFinding</i> en base a la transición entre zonas. 
	 */
	public void routeDepthwise(){

		this.diferencial = Math.abs(this.initialZone-this.finalZone);
		this.paths = new LinkedList[diferencial+1];
	
		if(this.diferencial == 0){


			paths[0]=Pathfinding.Depthwise(estados[initialZone][initialPos], estados[initialZone][finalPos]);
		}
		// Ir de ida
		else if(initialZone < finalZone){
			int x = 0; 
			paths[x]=(Pathfinding.Depthwise(estados[initialZone][initialPos], estados[initialZone][numberofvertices-1]));
			x++;
			System.out.println("initialzone" + this.initialZone);
			System.out.println("finalzone" + this.finalZone);

			for (int i = this.initialZone+1 ; i <= this.finalZone; i++) {
				if(finalZone != i){
					paths[x]=(Pathfinding.Depthwise(estados[i][0], estados[i][numberofvertices-1]));
					x++;
				}
				else{
					paths[x]=(Pathfinding.Depthwise(estados[i][0], estados[i][finalPos]));
					x++;
				}
			}
		}
		// Ir de regreso
		else if (initialZone > finalZone ){
			int x = 0; 
			paths[x]=(Pathfinding.Depthwise(estados[initialZone][initialPos], estados[initialZone][0]));
			x++;
			for (int i = this.initialZone-1 ; i >= this.finalZone; i--) {
				if(finalZone != i){
					paths[x]=(Pathfinding.Depthwise(estados[i][numberofvertices-1], estados[i][0]));
					x++;
				}
				else{
					paths[x]=(Pathfinding.Depthwise(estados[i][numberofvertices-1], estados[i][finalPos]));
					x++;
				}
			}
		}

		for(int i =0; i < paths.length;i++){
			System.out.println("Printing Path...");
			for (int j = 0; j < paths[i].size()-1; j++) {
				String x = paths[i].get(j).name;
				System.out.println("Key[" + x + "], Value[" + paths[i].get(j).distances.get(paths[i].get(j+1).name)+"]");
			}
		}
	}
	
	public void routeAStar(){

		this.diferencial = Math.abs(this.initialZone-this.finalZone);
		this.paths = new LinkedList[diferencial+1];
	
		if(this.diferencial == 0){
			try{
				paths[0]=Pathfinding.AStar(estados[initialZone][initialPos], estados[initialZone][finalPos]);
			}
			catch(Exception e){
				System.out.println("Can't find a path to the same place");
			}
		}
		// Ir de ida
		else if(initialZone < finalZone){
			int x = 0; 
			paths[x]=(Pathfinding.AStar(estados[initialZone][initialPos], estados[initialZone][numberofvertices-1]));
			x++;
			System.out.println("initialzone" + this.initialZone);
			System.out.println("finalzone" + this.finalZone);

			for (int i = this.initialZone+1 ; i <= this.finalZone; i++) {
				if(finalZone != i){
					paths[x]=(Pathfinding.AStar(estados[i][0], estados[i][numberofvertices-1]));
					x++;
				}
				else{
					paths[x]=(Pathfinding.AStar(estados[i][0], estados[i][finalPos]));
					x++;
				}
			}
		}
		// Ir de regreso
		else if (initialZone > finalZone ){
			int x = 0; 
			paths[x]=(Pathfinding.AStar(estados[initialZone][initialPos], estados[initialZone][0]));
			x++;
			for (int i = this.initialZone-1 ; i >= this.finalZone; i--) {
				if(finalZone != i){
					paths[x]=(Pathfinding.AStar(estados[i][numberofvertices-1], estados[i][0]));
					x++;
				}
				else{
					paths[x]=(Pathfinding.AStar(estados[i][numberofvertices-1], estados[i][finalPos]));
					x++;
				}
			}
		}

		this.Path = new LinkedList<String>();
		this.recFinal = 0;
		for(int i =0; i < paths.length;i++){
			System.out.println("Printing Path...");
			for (int j = 0; j < paths[i].size()-1; j++) {
				String x = paths[i].get(j).name;
				System.out.println("Key[" + x + "], Value[" + paths[i].get(j).distances.get(paths[i].get(j+1).name)+"]");
				this.Path.add(x);
				this.recFinal += paths[i].get(j).distances.get(paths[i].get(j+1).name);
			}
		}
		this.Path.add(estados[finalZone][finalPos].name);
	}
	
	private String printPath(){
		String list= "";
		for (int i = 0; i < Path.size(); i++) {
			list += "[" + Path.get(i) + "]";
		}
		return list;
	}

	public static void main(String[] args){
		ReadFile x = new ReadFile("QROO","BCS", "File.txt");
	}
}