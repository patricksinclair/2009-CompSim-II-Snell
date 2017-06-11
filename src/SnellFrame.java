import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;





class SnellPanel extends JPanel{

	private double n1, n2, crit;
	private Lightbeam beam;
	private int X, Y;
	boolean inputted = false;
	
	public SnellPanel(){

		setBackground(Color.BLACK);

	}

	
	@Override
	public void paintComponent(Graphics g){
		
		X = getWidth();
		Y = getHeight();
		
		beam = new Lightbeam(0, getN1(), getN2());
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(0, -50);
		g2d.setStroke(new BasicStroke(5));
		
		double critRad = Math.toRadians(beam.crit());
		double O = -1*(Y)*Math.tan(critRad);
		int oh = (int)O + X/2;
		
		
		g.setColor(Color.CYAN);
		g.fillRect(0, Y, X, 50);


		g2d.setColor(Color.YELLOW);
		if(!inputted) g2d.drawLine(0, Y, X/2, Y); else
		g2d.drawLine(oh, 0, X/2, Y);
		g2d.drawLine(X/2, Y, X, Y);
		
		g2d.setColor(Color.PINK);
		String critS = String.format("The critical angle is: %.2f\u00b0 ", beam.crit());
		g2d.drawString(critS, X/2, Y/2);
	}


	public void setN1(double n1){
		this.n1 = n1;
	}
	public void setN2(double n2){
		this.n2 = n2;
	}
	public double getN1(){
		return n1;
	}
	public double getN2(){
		return n2;
	}
}




class SnellFrame extends JFrame{

	private int X = 700;
	private int Y = 600;
	JLabel n1Label, n2Label;
	JTextField n1text, n2text;
	JPanel control;
	SnellPanel snellpan;


	public SnellFrame(){

		setBackground(Color.GRAY);

		n1Label = new JLabel("Enter the value of n1: ");
		n1text = new JTextField(10);
		n2Label = new JLabel("Enter the value of n2: ");
		n2text = new JTextField(10);

		n1text.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				n1text.setText("");
			}
		});
		n2text.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				n2text.setText("");
			}
		});

		control = new JPanel();
		snellpan = new SnellPanel();

		control.add(n1Label);
		control.add(n1text);
		control.add(n2Label);
		control.add(n2text);

		setLayout(new BorderLayout());
		getContentPane().add(snellpan, BorderLayout.CENTER);
		getContentPane().add(control, BorderLayout.SOUTH);
		pack();


		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});


		setN1();
		setN2();

		setSize(X, Y);
		setLocation(200, 0);
		setTitle("Critical angles.");
		setVisible(true);




	}


	public void setN1(){	
		n1text.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				double n1 = Double.parseDouble(n1text.getText());
				snellpan.setN1(n1);
				repaint();
			}
		});
	}

	public void setN2(){
		n2text.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				double n2 = Double.parseDouble(n2text.getText());
				snellpan.setN2(n2);
				repaint();
				snellpan.inputted = true;
			}
		});
	}



}
