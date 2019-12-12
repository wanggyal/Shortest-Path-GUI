import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.swing.*;

@SuppressWarnings("serial")
public class GGSherpa extends JFrame {
	
// ***** classes *****
 Panel panel=new Panel();
 JFrame frame=new JFrame();
 Help help;
 Edge edge=new Edge();
 
// ***** lists *****
 EdgeList<Edge> edgeList=new EdgeList();
 DotList<Dot> dotList=new DotList();
 
 // ***** mouse listeners *****
 ClickListner cl=new ClickListner();
 ClickListner2 cl2=new ClickListner2();
 ClickListner3 cl3=new ClickListner3();
 ClickListner4 cl4=new ClickListner4();
 ClickListner5 cl5=new ClickListner5();
 
 // ***** buttons *****
 JRadioButton rbtnAddVertex =new JRadioButton("Add Vertex");
 JRadioButton rbtnAddEdge=new JRadioButton("Add Edges");
 JRadioButton rbtnMoveVertex =new JRadioButton("Move Vertex");
 JRadioButton rbtnShortestPath=new JRadioButton("Shortest Path");
 JRadioButton rbtnweight =new JRadioButton("Change a Weight to:");
 JButton btnaddAllEdge=new JButton("Add All Edges");
 JButton btnRandomWeight=new JButton("Random Weight");
 JButton btnHelp=new JButton("Help");
 JButton btnMinimalTree=new JButton("Minimal Spanning Tree");
 
 //***JTextField***//
 JTextArea jWeight=new JTextArea(5,20);
 
 //*****jlabel*****
 JLabel label=new JLabel();
 
 //**** two nodes to get the shortest path ****
 Dot node1;//they are used to store the starting and destination vertex while getting the shortest path
 Dot node2;
 
 //**** constructor ****
 public GGSherpa() {
	 super("Grap Gui");
	 setFrame();
	 
 }
 public void setFrame() {
	 this.preferredSize();
	 this.setSize(700, 700);
	 this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	 this.getContentPane().setLayout(null); 
	 this.setBackground(Color.WHITE);
	 this.addPanel(); 
	 this.setButtons();
	 this.setLabe();
	 this.setVisible(true); 
 }
 public void paint(Graphics g) {
	 super.paint(g);
	 //	**draws the vertex**
	 if(dotList.size()>0) {
		for(int i=0;i<dotList.size();i++) {
			dotList.get(i).draw(g);
		}
		}
	 
	 //	**draws the edges**
	 if(edgeList.size()>0) {
		 for(int i=0;i<edgeList.size();i++) {
			 edgeList.get(i).draw(g);
		 }
	 }
 }
 public void addPanel() {
	 panel.setBackground(Color.WHITE);
	 panel.setBounds(230, 10, 440, 600);
	 panel.setVisible(true);
	 this.add(panel);
 }
 
 //*************************************** Add buttons to the frame ****************************************************
 public void setButtons() {
	 
	 //*************************************** REGULAR BUTTONS *************************************
	 
	 //**************************** Add all the Edges ***************************
	 btnaddAllEdge.setBounds(10, 400, 200, 50);
	 this.add(btnaddAllEdge);
	 btnaddAllEdge.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
			 edgeDefaultColor();
			 addAllEdges();
			 removeMouseAdapters();
			 setLabel(Color.GREEN,"Edges Added successfully.");
		 }
	 });
	 
	 //************* Get Random Weight for the edge in edgeList *****************
	 btnRandomWeight.setBounds(10, 450, 200, 50);
	 this.add(btnRandomWeight);
	 btnRandomWeight.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
			 removeMouseAdapters();
			 edgeDefaultColor();
			 for(int i=0;i<edgeList.size();i++) {
				 setLabel(Color.GREEN,"Random Weight added to the Edges.");
				 Random r=new Random();
				 int j=r.nextInt(200)+1;
				 edgeList.get(i).setWeight(j);
				 repaint();
			 }
		 }
	 });
	 
	 //******** Display instruction to use the app ( activates the help class) ***********
	 btnHelp.setBounds(10, 500, 200, 50);
	 this.add(btnHelp);
	 btnHelp.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			 removeMouseAdapters();
			 help=new Help();
		}
	 });
	 
	 //************* Get Minimal spannig tree *****************
	 btnMinimalTree.setBounds(10, 550, 200, 50);
	 this.add(btnMinimalTree);
	 btnMinimalTree.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
			  removeMouseAdapters();
			 
			 dotDefaultColor();
			 Dijkstra dj=new Dijkstra(); 
				dj.execute(dotList.get(1));
				dj.getMinimalTree();
			
			setLabel(Color.GREEN,"Minimal path found.");
		 }
	 });
	 
    //***change wight to text field***
		 jWeight.setBounds(170, 318, 40, 16);
	     this.add(jWeight);
	     
	 //************************************ RADIO BUTTONS ************************************************     
	 //	**radio button to add vertex**
	 rbtnAddVertex.setBounds(10, 100, 200, 50);
	 this.add(rbtnAddVertex);
	 
	 //	**radio button to add edge**
	 rbtnAddEdge.setBounds(10,150,200,50);
	 this.add(rbtnAddEdge);
	 
	 //	**radio button to move the vertex**
	 rbtnMoveVertex.setBounds(10, 200, 200, 50);
	 this.add(rbtnMoveVertex);
	 
	 rbtnShortestPath.setBounds(10, 250, 200, 50);
	 this.add(rbtnShortestPath);
	 
	 rbtnweight.setBounds(10, 300, 160, 50);
	 this.add(rbtnweight);
	 
	 //****************** Grouping radio buttons ******************
	 ButtonGroup bg=new ButtonGroup();
	 bg.add(rbtnAddEdge);
	 bg.add(rbtnAddVertex);
	 bg.add(rbtnShortestPath);
	 bg.add(rbtnMoveVertex);
	 bg.add(rbtnweight);
	 
	 //**************** selecting radio to button move vertex ***************
	 rbtnMoveVertex.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			setLabel(Color.RED,"Press on a vertex and drag mouse to any location");
			panel.removeMouseListener(cl);
			panel.removeMouseListener(cl2);
			panel.removeMouseListener(cl4);
			panel.removeMouseListener(cl5);
			panel.addMouseListener(cl3);
		}
	 });
	 
	 //**************** selecting radio to add vertex *********************
	 rbtnAddVertex.addActionListener(new ActionListener() {
		 @Override	
		 public void actionPerformed(ActionEvent e) {
			setLabel(Color.RED,"click inside the white box to create vertex.");
			panel.removeMouseListener(cl2);
			panel.removeMouseListener(cl3);
			panel.removeMouseListener(cl4);
			panel.removeMouseListener(cl5);
			panel.addMouseListener(cl);
			}
		 });
	 
	 //**************** selecting radio button to add edges ***************
	 rbtnAddEdge.addActionListener(new ActionListener() {
		@Override
		 public void actionPerformed(ActionEvent e) {
			setLabel(Color.RED,"click on a vertex.");
			
			panel.removeMouseListener(cl);
			panel.removeMouseListener(cl3);
			panel.removeMouseListener(cl4);
			panel.removeMouseListener(cl5);
			panel.addMouseListener(cl2);
		 }
	 });
	 
	 //***** Selecting this button will change the weight of the edge *****
	 rbtnweight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setLabel(Color.RED,"Enter weight you want and select a edge that you wish to change");
				panel.removeMouseListener(cl);
				panel.removeMouseListener(cl2);
				panel.removeMouseListener(cl3);
				panel.removeMouseListener(cl5);
				panel.addMouseListener(cl4);
			}
		 });
	 
	 //******* selection this buttion will give the shortest path *************
	 rbtnShortestPath.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
			 edgeDefaultColor();
			 setLabel(Color.RED,"Click on a starting vertex and the click on the destination.");
			 
			 node1=new Dot();
			 node2=new Dot();
			 
			 panel.removeMouseListener(cl);
			 panel.removeMouseListener(cl2);
			 panel.removeMouseListener(cl3);
			 panel.removeMouseListener(cl4);
			 panel.addMouseListener(cl5);
 
		 }
	 });
    }
 
 	//*****seting up the JLabel*****
 	public void setLabe() {
 		label.setBounds(350,670,300,30);
 		label.setForeground(Color.RED);
 		panel.add(label);
 }
 	public void setLabel(Color c,String message)
 	{ 
 		label.setForeground(c);
 		label.setText(message);
 	}
 	//******************************* change back the color of the dots to red *****************************************
 	public void dotDefaultColor() {
		for(int i=0;i<dotList.size();i++) {
			dotList.get(i).setColor(Color.RED);
		}
 	}
 	
 	//******************************* change back the color of the edges to blue *****************************************
 	public void edgeDefaultColor() {
 		for(int i=0;i<edgeList.size();i++) {
 			edgeList.get(i).setColor(Color.BLUE);
 		}
 	}
 	
 
 	
 	//******************************************* method to add all Edges ***********************************************
 	//i am using the combination technique to find the number of edges that can be produced by the given number of vertices
 	//example if there is 4 vertices, c(4,2) which is 6, so we can generate 6 maximum  edges from 4 vertices
 	public void addAllEdges() {
 		edgeList=new EdgeList();
 		int size=dotList.size();
 		for(int i=0;i<size;i++) {
 			for(int j=i+1;j<size;j++) {
 				edgeList.add(new Edge(dotList.get(i),dotList.get(j)));
 				edgeList.get(i).calcWeight();
 				repaint();
 			}
 		}
 	}
 	
 	//************************************* this method will remove the mouse adapters from the panel ********************
 	public void removeMouseAdapters() {
 		 panel.removeMouseListener(cl);
		 panel.removeMouseListener(cl2);
		 panel.removeMouseListener(cl3);
		 panel.removeMouseListener(cl4);
		 panel.removeMouseListener(cl5);
 	}
 	//************************************************ main function ****************************************************
	public static void main(String[] args) {
		// TODO Auto-generated method stub
GGSherpa g=new GGSherpa();
	}

	
	//************************************************ mouse adapters classes for dot************************************
	protected class ClickListner extends MouseAdapter{
		
		ClickListner(){
			super();
		}
		
		public void mouseClicked(MouseEvent e) {
			Dot dot=new Dot();
			int x=e.getX()+225;
			int y=e.getY()+25;
			dot.setColor(Color.RED);
			dot.setX(x);;
			dot.setY(y);
			dotList.add(dot);
			repaint();
		}
	 }
	
	//******************************************** mouse adapter for edgelist *******************************************
	protected class ClickListner2 extends MouseAdapter{
		 int i=0;
		ClickListner2(){
			super();
		}
		public void mouseClicked(MouseEvent e) {
			Dot dots=new Dot();
			int x=e.getX()+225;
			int y=e.getY()+25;
			
			//***Check if user clicked on specific dots***
			for(i=0;i<dotList.size();i++) {
				int z1=dotList.get(i).getX(),z2=dotList.get(i).getY();
				
				if(((x<=z1+10)&&(x>=z1-10))&&((y<=z2+10)&&(y>=z2-10))) {
					dotList.get(i).setColor(Color.GREEN);
					setLabel(Color.RED,"Click on another vertex to add edge.");
					repaint();
					if(edge.hasA()) {
						dots.setX(z1);//change back to z1,z2
						dots.setY(z2);
						edge.setB(dots);	
						break;
					}
					else {
						dots.setX(z1);
						dots.setY(z2);
						edge.setA(dots);	
						break;
					}
					}
				}
			
			//***Check if there is two point and if there is draw line form point A to point B***
		if(edge.hasA()&&edge.hasB()) {
			edge.calcWeight();
			edgeList.add(edge);	
			setLabel(Color.GREEN,"Edge Added successfully!!!");
			edge=new Edge();
			dotDefaultColor();//change color back to red
			repaint();
		}
		}
	 }
	
	//******************************************* mouse Adapter to move the vertex **************************************
	protected class ClickListner3 extends MouseAdapter{
		
		int x,y,i=0;
		int a,b,j;// this is the point from the vertex
		boolean pressed;
		
		 ClickListner3() {
			super();
		}
		 
		 public void mousePressed(MouseEvent e) {
			x=e.getX()+225;
			y=e.getY()+25;
			for(this.i=0;this.i<dotList.size();this.i++) {
				 a=dotList.get(this.i).getX();
				 b=dotList.get(this.i).getY();
				if(((x<=a+10)&&(x>=a-10))&&((y<=b+10)&&(y>=b-10))) {
					pressed=true;
					dotList.get(i).setColor(Color.GREEN);
					
					setLabel(Color.RED,"Drag your mouse to any location and release to move the vertex");
					repaint();
					break;
				}
			}
		 }
		 
		 public void mouseReleased(MouseEvent e) {
			if(pressed) {
				x=e.getX()+225;
				y=e.getY()+25;
				dotList.get(this.i).setX(x);
				dotList.get(this.i).setY(y);
				
				setLabel(Color.GREEN,"Vertex moved successfully.");
				this.moveEdge();
				dotDefaultColor();//change color to red
				repaint();
			}
			else {
				setLabel(Color.RED,"Moving vertex Unsuccessful!!!");
			}
			 
		 }
		 //************* change the point on the edge after moving the vertex ******************
		 public void moveEdge() {
			 if(edgeList.size()>=i) {
				for(j=0;j<edgeList.size();j++) {
					if(edgeList.get(j).equalsEdge(a+5, b+5)) {

						if(edgeList.get(j).equalsA(a+5, b+5)) {edgeList.get(j).setA(new Dot(x,y));
						}
						else {edgeList.get(j).setB(new Dot(x,y));
						}
						}
					}
				}
			 }
		 }
	
	//************************************ Mouse Adapter to change the weight of the edge *******************************
	protected class ClickListner4 extends MouseAdapter{
		int x,y,i=0;
		public ClickListner4(){
			super();
		}
		
		public void mouseClicked(MouseEvent e) {
			x=e.getX()+225;
			y=e.getY()+25;
			int newWeight=0;
			if(edgeList.size()>0) {
				
				//check if the number entered is valid or not
				try {
					newWeight=Integer.parseInt(jWeight.getText());
				}
				catch(NumberFormatException nfe) {
					setLabel(Color.RED,nfe.getMessage());
				}
				
			for(i=0;i<edgeList.size();i++) {

					if(edgeList.get(i).checkEdge(x, y)&&newWeight!=0){
					edgeList.get(i).setWeight(newWeight);
					setLabel(Color.GREEN,"New Weight added successfully.");
					repaint();
					break;
					}
					else {
						setLabel(Color.RED,"Error adding weight, please check the instructions.");
					}
				}
				
				}
			
			
			repaint();
		}	
	}
	
	//*********************** mouse Adapter to Get the shortest path from one node to another ***************************
	protected class ClickListner5 extends MouseAdapter
	{
		public ClickListner5() {
			super();
		}
		
		public void mouseClicked(MouseEvent e) {
			edgeDefaultColor();
			int x=e.getX()+225;
			int y=e.getY()+25;
			int i;
			node2=new Dot();
			for(i=0;i<dotList.size();i++) {
				int z1=dotList.get(i).getX(),z2=dotList.get(i).getY();
				
				if(((x<=z1+10)&&(x>=z1-10))&&((y<=z2+10)&&(y>=z2-10))) {
					
					if(node1.isEmpty()) {
						node1=dotList.get(i);
						dotList.get(i).setColor(Color.GREEN);
						setLabel(Color.RED,"Click on destination vertex to find the shortest path.");
					}
					else if(node2.isEmpty()) 
						{node2=dotList.get(i);
						dotList.get(i).setColor(Color.GREEN);
						}
					repaint();
					break;
				}
				
			}
			
			//add the methods from dijkstras algorithm
			if(!node1.isEmpty()&&!node2.isEmpty()){
				Dijkstra dj=new Dijkstra();
				dj.execute(node1);
				//dj.getMinimalTree();
				dj.getPath(node1,node2);
			}
		}
	}
	
	//************************************* Implementation of Dijkstra's Algorithm **************************************
	protected class Dijkstra{
		private  ArrayList<Dot> nodes;
		private EdgeList<Edge> edges;
		private Set<Dot> visitedNodes;
		private Set<Dot> unvisitedNodes;
		private Map<Dot, Integer> totalWeight;
		private Map<Dot,Dot> prevNodes;
		
		public Dijkstra() {
			nodes=new ArrayList<>();
			for(int i=0;i<dotList.size();i++) {
				Dot dot=new Dot(dotList.get(i).getX(),dotList.get(i).getY());
				this.nodes.add(dot);
			}
			this.edges=edgeList;
		}
		
		public void execute(Dot start) {
			visitedNodes=new HashSet<>();
			unvisitedNodes=new HashSet<>();
			totalWeight=new HashMap<>();
			prevNodes=new HashMap<>();
			this.totalWeight.put(start, 0);
			unvisitedNodes.add(start);
			while(unvisitedNodes.size()>0) {
				Dot node=getMinimum(unvisitedNodes);
				visitedNodes.add(node);
				unvisitedNodes.remove(node);
				findMinimalWeights(node);
			}
		}
		
		private Dot getMinimum(Set<Dot> dots) {
			Dot minimum=null;
			for(Dot dot:dots) {
				if(minimum==null) {
					minimum=dot;
				}else{
					if(this.getShortestDistance(dot)<this.getShortestDistance(minimum)) {
						minimum=dot;
					}
				}
			}
			return minimum;
		}

		public void findMinimalWeights(Dot node) {
			DotList<Dot> adjNodes=getNeighbours(node);
			for(int i=0;i<adjNodes.size();i++) {
				Dot target=adjNodes.get(i);
				if(getShortestDistance(target)>getShortestDistance(node)+getDistance(node,target)) {
					totalWeight.put(target, getShortestDistance(node)+getDistance(node,target));
					prevNodes.put(target, node);
					unvisitedNodes.add(target);
				}
			}
		}

		private int getDistance(Dot node, Dot target)  {
			int weight=0;
			Edge edge=new Edge(node,target);
			int index=this.getIndexOf(edge);
			if(index!=-1)         
			weight=edges.get(index).getWeight();
			else {            	           
				throw new RuntimeException("no such edge");
			}
			return weight;
		}

		private int getShortestDistance(Dot node) {
			Integer d=totalWeight.get(node);
			if(d==null) {
				return Integer.MAX_VALUE;
			}
			else
			return d;
		}

		private DotList<Dot> getNeighbours(Dot node) {
			DotList<Dot> temp=new DotList<>();
			for(int i=0;i<nodes.size();i++) {
				if(!visitedNodes.contains(nodes.get(i))) {
					if(this.checkNeighbour(new Edge(node,nodes.get(i)))==true){
					temp.add(nodes.get(i));
				}
				}
				
			}
	
			return temp;
		}
		
		public Map<Dot, Dot> getPrev(){
			return this.prevNodes;
		}
		
		public boolean checkNeighbour(Edge e) {
			int x1,x2,y1,y2,a,b,c,d;//a=x1 b=x2 c=y1 d=y2 where abcd are point of edge from edgelist
			boolean check=false;
			x1=e.getPointA().getX();
			x2=e.getPointB().getX();
			y1=e.getPointA().getY();
			y2=e.getPointB().getY();
			for(int i=0;i<edges.size();i++) {
				a=edges.get(i).getPointA().getX();
				b=edges.get(i).getPointB().getX();
				c=edges.get(i).getPointA().getY();
				d=edges.get(i).getPointB().getY();
				if(a==x1&&b==x2&&c==y1&&d==y2) {
					check= true;
					break;
				}
				if(a==x2&&b==x1&&c==y2&&d==y1) {
					check= true;
					break;
				}
			}
			return check;
			}
		public int getIndexOf(Edge e) {
			int x1,x2,y1,y2,a,b,c,d,index=-1;//a=x1 b=x2 c=y1 d=y2 where abcd are point of edge from edgelist
			x1=e.getPointA().getX();
			x2=e.getPointB().getX();
			y1=e.getPointA().getY();
			y2=e.getPointB().getY();
			for(int i=0;i<edges.size();i++) {
				a=edges.get(i).getPointA().getX();
				b=edges.get(i).getPointB().getX();
				c=edges.get(i).getPointA().getY();
				d=edges.get(i).getPointB().getY();
				if(a==x1&&b==x2&&c==y1&&d==y2) {
					index=i;
					break;
				}
				if(a==x2&&b==x1&&c==y2&&d==y1) {
					index= i;
					break;
				}
			}
			return index;
		}
		
		//this method will find the index of the edges which we have in the prevNodes and the change the color in the original Edge list 
		public void getMinimalTree() {
			int i=0;
			ArrayList<Dot> target=new ArrayList<>(prevNodes.keySet());;
			ArrayList<Dot> previous=new ArrayList<>(prevNodes.values()) ;
			if(target.size()==previous.size()) {
			for(int j=0;j<prevNodes.size();j++) {
				i=this.getIndexOf(new Edge(target.get(j),previous.get(j)));
				edgeList.get(i).setColor(Color.GREEN);
			}
			
			}
			else throw new RuntimeException("Error while getting minumun, targe.size!=prerious.size");
			repaint();
			dotDefaultColor();
			
		}
		
		//get the shortest path from one node to another
		public void getPath( Dot a,Dot b) {
			dotDefaultColor();
			if(!b.isEmpty()) {
				int x,y;
				ArrayList<Dot> keys=new ArrayList<>(prevNodes.keySet());
				LinkedList<Dot> path=new LinkedList<>();
				Dot start=b;//was b
				for(int i=0;i<keys.size();i++) {
					x=keys.get(i).getX();
					y=keys.get(i).getY();
					if(x==start.getX()&&y==start.getY()) {
						start=keys.get(i);
						break;
				}
				}
				if(prevNodes.get(start)==null) {
					return;
				}
				path.add(start);
			while(prevNodes.get(start)!=null) {
				path.add(prevNodes.get(start));
				start=prevNodes.get(start);
			}
			//reverse the linklist
			Collections.reverse(path);

			int i;
			for(i=0;i<path.size();i++) {
				try {
				int index=this.getIndexOf(new Edge(path.get(i),path.get(i+1)));
				edgeList.get(index).setColor(Color.RED);
				
				}
				catch(IndexOutOfBoundsException e){
					setLabel(Color.RED,"Error!!!"+e.getMessage());
				}
			}
				repaint();
				setLabel(Color.GREEN,"Shortest path found ");
			}
		}
	}
	
	//****************************************** DotList Class contains the list of Dot (the vertex) *************************************
	protected class DotList<Dot> {
		private ArrayList<Dot> dotList;
		public DotList() {
			dotList=new ArrayList<>();
		}
		public void add(Dot d) {
			dotList.add(d);
		}
		public int size() {
			return dotList.size();
		}
		public Dot get(int i){
			return dotList.get(i);
		}
		public ArrayList<Dot> getList(){
			return dotList;
		}
		public Iterator<Dot> iterator() {
			ArrayList<Dot> list=new ArrayList<>();
			for(int i=0;i<dotList.size();i++) {
				list.add(dotList.get(i));
			}
			return list.iterator();
		}
		}

	//********************************************** this class contains the vertex *********************************************
	protected class Dot {
		private int x;
		private int y;
		private Color color;

		public Dot() {
			this.color=color.RED;
			x=y=0;
		}
		public Dot(int a, int b) {
			this.color=color.RED;
			x=a;
			y=b;
		}
		public void setX(int a) {
			x=a;
		}
		public void setY(int b) {
			y=b;
		}
		public void setColor(Color c) {
			this.color=c;
		}
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		public boolean isEmpty() {
			if(this.x==0&&this.y==0)
				return true;
			else return false;
		}
		public String getColor() {
			return color.toString();
		}
		public void draw(Graphics g) {
			g.setColor(color);
			g.fillOval(x, y, 10, 10);
			
		}
	}
	
	//********************************************* this class contains the edge ******************************************
	protected class Edge {
		private int x1,y1,x2,y2;
		private int weight;
		private double slope;
		Color color=Color.BLUE;
		Dot dot;

		public Edge() {
			x1=x2=y1=y2=0;
			weight=0;
			dot=new Dot();
		}
		public Edge(Dot a,Dot b) {
			x1=a.getX()+5;
			x2=b.getX()+5;
			y1=a.getY()+5;
			y2=b.getY()+5;
			this.calcWeight();
		}

		public void setEdge(Dot a,Dot b) {
			x1=a.getX();
			y1=a.getY();
			x2=b.getX();
			y2=b.getY();
		}

		public void setA(Dot a) {
			x1=a.getX()+5;
			y1=a.getY()+5;
		}

		public void setB(Dot b) {
			x2=b.getX()+5;
			y2=b.getY()+5;
		}

		public void setColor(Color c) {
			this.color=c;
		}
		public int calcWeight() {	weight=(int)Math.sqrt(Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2));
			return weight;
		}
		public int getWeight() {
		return weight;	
		}

		public void setWeight(int weight) {
			this.weight=weight;
		}

		public void draw(Graphics g) {
			/***midpoint(x,y) is the position where we draw weight of the edge***/
			int midPointX=(int)((x2+x1)/2);
			int midPointY=(int)((y2+y1)/2);
			g.setColor(Color.BLACK);
			g.drawString(Integer.toString(weight), midPointX, midPointY);
			g.setColor(color);
			g.drawLine(x1, y1, x2, y2);
		}

		public Dot getPointA() {
			Dot A=new Dot(x1,y1);
			return A;
		}

		public Dot getPointB() {
		Dot B=new Dot(x2,y2);
		return B;
		}

		public boolean hasA() {
			return (x1>0&&y1>0);	
		}

		public boolean hasB() {
			return (x2>0&&y2>0);	
		}

		public boolean equalsA(int x,int y) {
			return (x==x1&&y==y1);
		}

		public boolean equalsB(int x,int y) {
			return (x==x2&&y==y2);
		}

		public boolean equalsEdge(int x,int y) {
			return(this.equalsA(x, y)||this.equalsB(x, y));
		}

		public double calcSlope() {
			return slope=(double)(y2-y1)/(x2-x1);
		}
		//************ check if the user clicked on this edge or not *****************
		public boolean checkEdge(int x, int y) {
			//this.calcSlope();
			int  i,a,b;
			if(this.equal(x,y)) return true;
			//returns true if the (x,y) from the mouse click is on the line (x1,y1)&(x2,y2)
			for(i=1;i<=20;i++) {
				a=x;b=y+i;
				if(this.equal(a,b)) return true;
				a=x;b=y-i;
				if(this.equal(a,b)) return true;
				a=x-i;b=y;
				if(this.equal(a,b)) return true;
				a=x+i;b=y;
				if(this.equal(a,b)) return true;
				a=x+i;b=y+i;
				if(this.equal(a,b)) return true;
				a=x-i;b=y-i;
				if(this.equal(a,b)) return true;
				a=x+i;b=y-i;
				if(this.equal(a,b)) return true;
				a=x-i;b=y+i;
				if(this.equal(a,b)) return true;
			}
			if(i>=10) {
				return false;
			}
			else return true;	
		}

		public boolean equal(int x,int y) {
			int c=((y)-y1)*(x2-x1);
			int d=(y2-y1)*((x)-x1);
			if(c>d-20&&c<d+20) return true;
			else return false;
		}
		}


	//*********************************************** this class contains the list of the Edge ******************************************
	public class EdgeList<Edge> {
		private ArrayList<Edge> edgeList;

		public EdgeList() {
			edgeList=new ArrayList<>();
		}

		public void add(Edge e) {
			edgeList.add(e);
		}

		public Edge get(int i) {
			return edgeList.get(i);
		}

		public ArrayList<Edge> getList(){
			return edgeList;
		}

		public int getIndex(Edge e) {
			return edgeList.indexOf(e);
		}


		public int size() {
			return edgeList.size();
		}
		}

	//************************************** this is the help class that will guide how to use this program *******************************
	protected class Help {
		JFrame frame;
		JTextArea jLabel ;
		JButton btn;
		JPanel panel;

		public Help() {
			this.gui();
		}
		public void gui() {
			frame=new JFrame("HELP");
			frame.setVisible(true);
			frame.setSize(600,600);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			panel=new JPanel();
			panel.setSize(frame.getSize());
			panel.setBackground(Color.WHITE);
			
			jLabel=new JTextArea();
			jLabel.setBounds(20,20,56,360);
			jLabel.setEditable(false);
			
			btn=new JButton("OK");
			btn.setBounds(290, 500, 50, 20);
			
			panel.add(jLabel);
			frame.add(btn);
			
			jLabel.setText("\n\n"
					+ "    - Select Add Vertex and left click on the right empty space to\n"
					+ "      create a dot or Vertex.--> Create more than one vertex\n\n"
					+ "    - Select Add Edge and Connect the vertex to create a link between\n"
					+ "       two vertex.\n\n "
					+ "    - Select Move Vertex to move vertex to another position.\n\n"
					+ "    - Select Shortest Path to find the shortest path from one vertex to another.\n\n"
					+ "    - Select on Change weight to, and then enter a weight then Click on a Edge to\n"
					+ "       change the weight of specific edge.\n\n"
					+ "    - Click on Add All Edge to connect all the edges.\n\n"
					+ "    - Click on Add Random Weight to provide random weight to the edges.\n\n"
					+ "    - Click on Minimal Spanning Tree to get the minimal spanning tree\n\n"
					+ "    - There might be some issue if you clicked on Minimal Spanning Tree and then Shorted Path.");
			
			frame.add(panel);
			//*********************function of the "OK" button**************************
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
				}
			});
		}

		}
	
	protected class Panel extends JPanel{
		public Panel() {
			this.setBounds(230, 10, 440, 600);
			this.setBackground(Color.GRAY);
			this.setVisible(true);

		 }
	}

	}//end
//i have the link of this GUI, that i tried on my computer because i was not sure if it will run on other computer.
//https://youtu.be/j8TFYk7yYlA