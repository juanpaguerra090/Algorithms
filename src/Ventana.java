import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

public class Ventana extends JPanel implements ActionListener {

	//GUI Components
	JComboBox<String> selOrigen;
	JComboBox<String> selDest;
	
	JButton goTime;
	JButton goDistance;

	JLabel origin, dest; 


	//
	String a, b; // A = Origen , B = Destino

	public Ventana() {
		super(new BorderLayout());

		String[] origen  = { "PUE",	"OAX",	"CHIAP", "TAB",	"CAMP",	"YUC",	"VER",	"QROO",	"MICH",	"GUERR", "MORELOS",	"EDOMEX", "HID", "QUER", "TLAX", "CDMX", "JAL", "NAY", "COL", "AGS", "SLP",	"TMLP",	"NL", "GUA", "BCS",	"BC", "CHI", "COAH", "DUR",	"SIN", "SON", "ZAC" };
		String[] destino = { "PUE",	"OAX",	"CHIAP", "TAB",	"CAMP",	"YUC",	"VER",	"QROO",	"MICH",	"GUERR", "MORELOS",	"EDOMEX", "HID", "QUER", "TLAX", "CDMX", "JAL", "NAY", "COL", "AGS", "SLP",	"TMLP",	"NL", "GUA", "BCS",	"BC", "CHI", "COAH", "DUR",	"SIN", "SON", "ZAC" };

		//Create the combo box, select the item at index 4.
		//Indices start at 0, so 4 specifies the pig.
		FlowLayout experimentLayout = new FlowLayout();
		this.setLayout(experimentLayout);

		//ComboBoxes
		this.selOrigen = new JComboBox<String>(origen);
		this.selOrigen.setSelectedIndex(4);
		this.selOrigen.addActionListener(this);
		
		this.selDest = new JComboBox<String>(destino);
		this.selDest.setSelectedIndex(4);
		this.selDest.addActionListener(this);

		//Labels
		this.origin = new JLabel("Origin: ");
		this.dest = new JLabel("Destiny: ");
		
		// Go Buttons
		this.goDistance = new JButton("Shortest Route ");
		this.goDistance.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
		        int returnValue = fileChooser.showOpenDialog(null);
		        if (returnValue == JFileChooser.APPROVE_OPTION) {
		          File selectedFile = fileChooser.getSelectedFile();
		          String fileName = selectedFile.getPath();
		          System.out.println("======= ROUTE BY DISTANCE =======");
		          ReadFile x = new ReadFile(Ventana.this.a, Ventana.this.b, fileName);
		          System.out.println(x.recFinal + " Kms");
		        }
			}
			
		});
		this.goTime = new JButton("Fastest Route");
		this.goTime.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
		        int returnValue = fileChooser.showOpenDialog(null);
		        if (returnValue == JFileChooser.APPROVE_OPTION) {
		          File selectedFile = fileChooser.getSelectedFile();
		          String fileName = selectedFile.getPath();
		          System.out.println("======= ROUTE BY TIME =======");
		          ReadFile x = new ReadFile(Ventana.this.a, Ventana.this.b, fileName);
		          System.out.println(x.recFinal/60 + ":" + x.recFinal%60);

		        }
				
			}
			
		});

		//Add to Window
		this.add(origin);
		this.add(selOrigen);

		this.add(dest);
		this.add(selDest);
		
		this.add(goTime);
		this.add(goDistance);
		setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	}

	/** Listens to the combo box. */
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> cb = (JComboBox)e.getSource();
		if (e.getSource().equals(selOrigen)){
			//Origin ComboBox
			this.a = (String) cb.getSelectedItem();
		}
		else if (e.getSource().equals(selDest)){
			//Destination ComboBox
			this.b = (String) cb.getSelectedItem();
		}
		
		
	}



	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		//Create and set up the window.
		JFrame frame = new JFrame("MEX-86");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create and set up the content pane.
		JComponent newContentPane = new Ventana();
		newContentPane.setOpaque(true); //content panes must be opaque
		frame.setContentPane(newContentPane);

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}



// [QROO][CAMP][TAB][CHIAP][OAX][CDMX][MORE][GUE][GUA][ZAC][DUR][SIN][SON][BC][BCS]
// [QROO][CAMP][TAB][CHIAP][OAX][CDMX][MORELOS][GUE][GUA][TAM][SLP][NAY][ZAC][BC][BCS]
