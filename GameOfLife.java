import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class GameOfLife {
	static char Table[][];
	static int width;
	static int height;
	int minorx;
	double cycles;
    int minory;
    int pointerx=0;
    ArrayList<Organism> org;
    JFrame frame;
    int pointery=0;
	String title = "Game Of Life v0.4";
	public int speeder;
    public GameOfLife(int speeder) {
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width =(int) screenSize.getWidth()/12;
   	 	height =(int) ((int) screenSize.getHeight()/12);
   	 	CreateAndPopulate();
   	 	ConfigPointers();
   	 	this.speeder=speeder;
   	 	org = new ArrayList<Organism>();
   	 	org.add(new Organism("Conway's",'1',1000,1,3,4,Color.orange));
   	 org.add(new Organism("Another",'3',1000,2,3,3,Color.cyan));
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    
                }
                frame = new JFrame(title+" - Breeding");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                
                frame.setVisible(true);
            }
        });
    }
	public void newOrganism(String name,char identifier, int mutation_prob, int reproduction, int spread,
			int death_conditions, Color org_color)
	{
		org.add(new Organism(name,identifier,mutation_prob,reproduction,spread,death_conditions,org_color));
	}
	public void CreateAndPopulate()
	{
		Table = new char [width][height];
		
		for(int x = 0;x<width;x++)
		{
			for(int y = 0;y<height;y++)
			{
				Table[x][y]='0';
				
			}
		}
	}
	
	public  void AddCell(int x , int y)
	{
		if(Table[x][y]=='1')
		{
			Table[x][y]='0';
		}
		else
		{
		Table[x][y]='1';
		}
	}
	public  void AddCell(int x , int y,char cell)
	{
		if(Table[x][y]==cell)
		{
			Table[x][y]='0';
		}
		else
		{
		Table[x][y]=cell;
		}
				
	}
	
	public int ChecaVizinhos(int x, int y)
    {
        int QntdDeVizinhos=0;
       
            for(int x1=-1;x1<2;x1++)
            {
                for(int y1=-1;y1<2;y1++)
                {   
                    try
                    {
                        if(Table[x-x1][y-y1]!='0')
                        {
                            QntdDeVizinhos++;
                        }
                    }catch(Exception e)
                    {
                       
                    }
                }
            }
        if(Table[x][y]!='0')
        {
        return QntdDeVizinhos-1;
        }
        else
        {
        return QntdDeVizinhos;
        }
    }
	public int ChecaVizinhos(int x, int y,char org)
	{
		 int QntdDeVizinhos=0;
	       
         for(int x1=-1;x1<2;x1++)
         {
             for(int y1=-1;y1<2;y1++)
             {   
                 try
                 {
                     if(Table[x-x1][y-y1]==org)
                     {
                         QntdDeVizinhos++;
                     }
                     
                 }catch(Exception e)
                 {
                    
                 }
             }
         }
     if(Table[x][y]==org)
     {
     return QntdDeVizinhos-1;
     }
     else
     {
     return QntdDeVizinhos;
     }
		
	}
	public void getMinor()
    {
        for(int x =0;x<width;x++)
        {
            for(int y =0;y<height;y++)
            {
                if(Table[x][y]!='0')
                {
                    minorx = x;
                    minory = y;
                    return;
                }
            }
        }
    }
	public void ConfigPointers()
    {
        getMinor();
        pointerx=minorx;
        pointery=minory;
        //DEFINE A POSIÇÃO PONTEIRO X
        if(minorx==0)
        {
            pointerx=0;
        }
        else
        {
            pointerx=pointerx-1;
        }
        //DEFINE A POSIÇÃO PONTEIRO Y
        if(minory==0)
        {
            pointery=0;
        }
        else
        {
            pointery=pointery-1;
        }
    }
	
	public void StepGameOfLife()
    {
       ArrayList<Point> al = new ArrayList<Point>();
        for(int x =pointerx;x<width;x++)
        {
            for(int y =pointery;y<height;y++)
            {
            	 if((Table[x][y]=='0')&&ChecaVizinhos(x,y)==3)
                 {
                 	al.add(new Point(x,y));
                 }
                 if((Table[x][y]=='1')&&(ChecaVizinhos(x,y)<2)&&(ChecaVizinhos(x,y)>0))
                 {
                	 al.add(new Point(x,y));
                 }
                 if((Table[x][y]=='1')&&(ChecaVizinhos(x,y)==0))
                 {
                	Table[x][y]='0'; 
                 }
                 if((Table[x][y]=='1')&&ChecaVizinhos(x,y)>3)
                 {
                	 al.add(new Point(x,y));
                 }
                
            }
        }
        for(int i =0;i<al.size();i++)
        {
        	if(Table[(int)al.get(i).getX()][(int)al.get(i).getY()]=='1')
        	{
        		Table[(int)al.get(i).getX()][(int)al.get(i).getY()]='0';
        	}
        	else
        	{
        		Table[(int)al.get(i).getX()][(int)al.get(i).getY()]='1';
        	}
        	
        }
		              
    }
	public void Fractal()
    {
       ArrayList<Point> al = new ArrayList<Point>();
        for(int x =pointerx;x<width;x++)
        {
            for(int y =pointery;y<height;y++)
            {
            	 if((Table[x][y]=='0')&&ChecaVizinhos(x,y)==1)
                 {
                 	al.add(new Point(x,y));
                 }
            	 
                
            }
        }
        for(int i =0;i<al.size();i++)
        {
        	if(Table[(int)al.get(i).getX()][(int)al.get(i).getY()]=='1')
        	{
        		Table[(int)al.get(i).getX()][(int)al.get(i).getY()]='0';
        	}
        	else
        	{
        		Table[(int)al.get(i).getX()][(int)al.get(i).getY()]='1';
        	}
        	
        }
		              
    }
	public void Labyrinth(int param, int param2 , int param3 , int param4, int param5)
    {
       ArrayList<Point> al = new ArrayList<Point>();
        for(int x =pointerx;x<width;x++)
        {
            for(int y =pointery;y<height;y++)
            {
            	 if((Table[x][y]=='0')&&ChecaVizinhos(x,y)==param)
                 {
                 	al.add(new Point(x,y));
                 }
                 if((Table[x][y]=='1')&&(ChecaVizinhos(x,y)<param2)&&(ChecaVizinhos(x,y)>param3))
                 {
                	 al.add(new Point(x,y));
                 }
                 if((Table[x][y]=='1')&&(ChecaVizinhos(x,y)==param4))
                 {
                	Table[x][y]='0'; 
                 }
                 if((Table[x][y]=='1')&&ChecaVizinhos(x,y)>param5)
                 {
                	 al.add(new Point(x,y));
                 }
                
            }
        }
        for(int i =0;i<al.size();i++)
        {
        	if(Table[(int)al.get(i).getX()][(int)al.get(i).getY()]=='1')
        	{
        		Table[(int)al.get(i).getX()][(int)al.get(i).getY()]='0';
        	}
        	else
        	{
        		Table[(int)al.get(i).getX()][(int)al.get(i).getY()]='1';
        	}
        	
        }
		              
    }
	public void LittleWorld(Organism g)
    {
		char identifier=g.getIdentifier();
		int mutation_prob=g.getMutation_prob();
		int reproduction=g.getReproduction();
		int spread=g.getSpread();
		int death_conditions=g.getDeath_conditions();
		
	   Random r = new Random();
       ArrayList<Point> al = new ArrayList<Point>();
        for(int x =pointerx;x<width;x++)
        {
            for(int y =pointery;y<height;y++)
            {
            	
            	 if((Table[x][y]!=identifier)&&ChecaVizinhos(x,y,identifier)>reproduction)
                 {
            		 if(x%2==0)
            		 {
            			 al.add(new Point(x+r.nextInt(spread),y+r.nextInt(spread)));
            		 }else
            		 {
            			 al.add(new Point(x-r.nextInt(spread),y-r.nextInt(spread)));
            		 }
            		 
                 }
            	 if((Table[x][y]==identifier)&&ChecaVizinhos(x,y,identifier)<death_conditions)
                 {
            		
            			 Table[x][y]='0';
            			 
                 }
            	 
            	 
                
            }
        }
        for(int i =0;i<al.size();i++)
        {
        	try
        	{
        		Table[(int)al.get(i).getX()][(int)al.get(i).getY()]=identifier;
        		
        	}catch(Exception e)
        	{
        		
        	}
        }
        if(r.nextInt(mutation_prob)==1)
        {
        	//org.add(g.Mutate());
        	g.selfMutate();
        }
        
        
		              
    }
	public GameOfLife getGame()
	{
		return this;
	}
	//TODO Fazer isso
	/*public void LittleWorld(ArrayList<Organism> org)
    {
		
	
	   Random r = new Random();
       ArrayList<Point> al = new ArrayList<Point>();
        for(int x =pointerx;x<width;x++)
        {
            for(int y =pointery;y<height;y++)
            {
            	
            	 if((Table[x][y]!=org)&&ChecaVizinhos(x,y,org)>1)
                 {
            		 if(x%2==0)
            		 {
            			 al.add(new Point(x+r.nextInt(2),y+r.nextInt(2)));
            		 }else
            		 {
            			 al.add(new Point(x-r.nextInt(2),y-r.nextInt(2)));
            		 }
                 }
            	 if((Table[x][y]==org)&&ChecaVizinhos(x,y,org)<2)
                 {
            		
            			 Table[x][y]='0';
            		 
                 }
            	 
            	 
                
            }
        }
        for(int i =0;i<al.size();i++)
        {
        	try
        	{
        		Table[(int)al.get(i).getX()][(int)al.get(i).getY()]=org;   	
        	}catch(Exception e)
        	{
        		
        	}
        }
		              
    }*/
	 public class TestPane extends JPanel {
		 	boolean running = true;
		 	int speed=speeder;
		 	boolean fun = false;
		 	int type=1;
		 	boolean filled = false;
		 	Color back=Color.gray;
		 	Color cell=Color.orange;
		 	
		 	char selectedCell='1';
	        public TestPane() {
	        	
	        	Action Pause = new AbstractAction() {
	        	    public void actionPerformed(ActionEvent e) {
	        	        if(!running)
	        	        {
	        	        	frame.setTitle(title+" - Breeding");
	        	        }
	        	        else
	        	        {
	        	        	frame.setTitle(title+" - Paused");
	        	        }
	        	        running=!running;
	        	    }
	        	};
	        	Action colored = new AbstractAction() {
	        	    public void actionPerformed(ActionEvent e) {
	        	    	Random ran = new Random();
	        	    	float r = ran.nextFloat();
	        	    	float g = ran.nextFloat();
	        	    	float b = ran.nextFloat();
	        	    	
	        	     back = new Color(r,g,b);
	        	      r = ran.nextFloat();
	        	      g = ran.nextFloat();
	        	      b = ran.nextFloat();
	        	     cell = new Color(r,g,b);
	        	    }
	        	};
	        	Action type1 = new AbstractAction() {
	        	    public void actionPerformed(ActionEvent e) {
	        	    	frame.setTitle(title + " - Conway's");
	        	    	type=1;
	        	    }
	        	};
	        	Action type2 = new AbstractAction() {
	        	    public void actionPerformed(ActionEvent e) {
	        	    	frame.setTitle(title + " - Labyrinth");
	        	    	type=2;
	        	    }
	        	};
	        	Action type3 = new AbstractAction() {
	        	    public void actionPerformed(ActionEvent e) {
	        	    	frame.setTitle(title + " - Fractal");
	        	    	type=3;
	        	    }
	        	};
	        	Action type4 = new AbstractAction() {
	        	    public void actionPerformed(ActionEvent e) {
	        	    	frame.setTitle(title + " - Olivato's");
	        	    	type=4;
	        	    }
	        	};
	        	Action help = new AbstractAction() {
	        	    public void actionPerformed(ActionEvent e) {
	        	    	JOptionPane.showMessageDialog(frame,
	        	    		    "C - Change color patterns \nSPACE - Pause\nH - Show this\n1 - Game Of Life \n2 - Fractal\n3 - Labyrinth\n4 - LittleWorld \nP - Fill tool \nL - List organisms\nA - Create new organism ",
	        	    		    "HELP",
	        	    		    JOptionPane.PLAIN_MESSAGE);
	        	    }
	        	};
	        	Action clear = new AbstractAction() {
	        	    public void actionPerformed(ActionEvent e) {
	        	    	CreateAndPopulate();
	        	    }
	        	};
	        	Action newOrg = new AbstractAction() {
	        	    public void actionPerformed(ActionEvent e) {
	        	    	Object[] possibilities = {"Press A To Add"};
	        	    	if(org.size()!=0)
	        	    	{		        	    				        	    
		        	    	possibilities = new Object[org.size()];		        	    	
		        	    	for(int i =0;i<org.size();i++)
		        	    	{
		        	    		possibilities[i]=org.get(i).getName();
		        	    	}
	        	    	}
	        	    	
	        	    	String s = (String)JOptionPane.showInputDialog(
	        	    	                    frame,
	        	    	                    "Select:\n"
	        	    	                    ,
	        	    	                    "Add new cell",
	        	    	                    JOptionPane.PLAIN_MESSAGE,
	        	    	                    null,
	        	    	                    possibilities,
	        	    	                    "Simple");

	        	    	
	        	    	if ((s != null) && (s.length() > 0)) {
	        	    		for(int i =0;i<org.size();i++)
		        	    	{
		        	    		if(org.get(i).getName().equals(s))
		        	    		{
		        	    			selectedCell=org.get(i).getIdentifier();
		        	    		}
		        	    	}
	        	    	    return;
	        	    	}
	        	    }
	        	};
	        	Action fast = new AbstractAction() {
	        	    public void actionPerformed(ActionEvent e) {
	        	    	speed=speed+100;
	        	    	System.out.print("+");
	        	    }
	        	};
	        	Action slow = new AbstractAction() {
	        	    public void actionPerformed(ActionEvent e) {
	        	    	if(speed!=0)
	        	    	speed=speed-100;
	        	    	System.out.print("-");
	        	    }
	        	};
	        	Action add = new AbstractAction() {
	        	    public void actionPerformed(ActionEvent e) {
	        	    	NewOrganism dialog = new NewOrganism();
	        	    	int result = JOptionPane.showConfirmDialog(null,
	                             dialog,
	                             "Create new organism",
	                             JOptionPane.OK_CANCEL_OPTION,
	                             JOptionPane.PLAIN_MESSAGE);
	        	    	  
	        	    	 if (result == JOptionPane.OK_OPTION) {
	        	    		 try
	        	    		 {
		        	    	   String[] myOrg = dialog.getFieldsText().split(":");
		        	    	   String[] colors = myOrg[6].split("#");
		        	    	   Color c = new Color((int)Integer.valueOf(colors[0]),(int)Integer.valueOf(colors[1]),(int)Integer.valueOf(colors[2]));
		        	    	   newOrganism(myOrg[0],myOrg[1].charAt(0),(int)Integer.valueOf(myOrg[2]),(int)Integer.valueOf(myOrg[3]),(int)Integer.valueOf(myOrg[4]),(int)Integer.valueOf(myOrg[5]),c);
		        	    	   JOptionPane.showMessageDialog(frame,
	     	        	    		    "Organism:"+myOrg[0]+" added.",
	     	        	    		    "New Organism:"+myOrg[1],
	     	        	    		    JOptionPane.PLAIN_MESSAGE);
	        	    		 }catch(Exception ex)
	        	    		 {
	        	    			 JOptionPane.showMessageDialog(frame,
	     	        	    		    "Error",
	     	        	    		    "Error error",
	     	        	    		    JOptionPane.PLAIN_MESSAGE);
	        	    			 
	        	    		 }
	        	    	 }
	        	    	 
	        	    }
	        	};
	        	Action fill = new AbstractAction() {
	        	    public void actionPerformed(ActionEvent e) {
	        	    	filled=!filled;
	        	    }
	        	};
	        	this.getInputMap().put(KeyStroke.getKeyStroke(' '), "Pausa");
	        	this.getActionMap().put("Pausa",Pause);
	        	this.getInputMap().put(KeyStroke.getKeyStroke('c'), "color");
	        	this.getActionMap().put("color",colored);
	        	this.getInputMap().put(KeyStroke.getKeyStroke('1'), "type1");
	        	this.getActionMap().put("type1",type1);
	        	this.getInputMap().put(KeyStroke.getKeyStroke('h'), "help");
	        	this.getActionMap().put("help",help);
	        	this.getInputMap().put(KeyStroke.getKeyStroke('2'), "type2");
	        	this.getActionMap().put("type2",type2);
	        	this.getInputMap().put(KeyStroke.getKeyStroke('3'), "type3");
	        	this.getActionMap().put("type3",type3);
	        	this.getInputMap().put(KeyStroke.getKeyStroke('4'), "type4");
	        	this.getActionMap().put("type4",type4);
	        	this.getInputMap().put(KeyStroke.getKeyStroke('q'), "clear");
	        	this.getActionMap().put("clear",clear);
	        	this.getInputMap().put(KeyStroke.getKeyStroke('l'), "new");
	        	this.getActionMap().put("new",newOrg);
	        	this.getInputMap().put(KeyStroke.getKeyStroke('z'), "fast");
	        	this.getActionMap().put("fast",fast);
	        	this.getInputMap().put(KeyStroke.getKeyStroke('x'), "slow");
	        	this.getActionMap().put("slow",slow);
	        	this.getInputMap().put(KeyStroke.getKeyStroke('a'), "add");
	        	this.getActionMap().put("add",add);
	        	this.getInputMap().put(KeyStroke.getKeyStroke('p'), "fill");
	        	this.getActionMap().put("fill",fill);
	        	
	        	
	        		
	        	this.addMouseListener(new MouseListener() {
	        		
	        	    @Override
	        	    public void mouseClicked(MouseEvent e) {
	        	    	  
	        	    }

					@Override
					public void mousePressed(MouseEvent e) {
						int x=e.getX();
	        	    	int y=e.getY();
	        	    	if(!filled)
	        	    	{
	        	    	AddCell(x/12,y/12,selectedCell);
	        	    	}
	        	    	else
	        	    	{
	        	    		
	        	    		for(int x1=-1;x1<3;x1++)
	        	            {
	        	                for(int y1=-1;y1<3;y1++)
	        	                {   
	        	                    try
	        	                    {
	        	                        
	        	                        AddCell((x/12)-x1,(y/12)-y1,selectedCell);
	        	                    }catch(Exception ex)
	        	                    {
	        	                       
	        	                    }
	        	                }
	        	            }
	        	    	}
						
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
	        	});
	            Timer timer = new Timer(speed, new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                	if(running)
	                	{
	                    
	                		if(type==1)
	                		{
	                			StepGameOfLife();
	                		}
	                		if(type==2)
	                		{
	                			Labyrinth(2,2,3,5,5);
	                		}
	                		if(type==3)
	                		{
	                			Fractal();
	                		}
	                		if(type==4)
	                		{
	                			
	                			for(int i =0;i<org.size();i++)
	                			{
	                				LittleWorld(org.get(i));
	                			}
	                			
	                		}
	                		
	                	}
	          
	                	
	                	
	                	Toolkit.getDefaultToolkit().sync();
	                	repaint();
	                	
	                }
	            });
	            timer.start();
	        }

	       
	        @Override
	        public Dimension getPreferredSize() {
	            return new Dimension(width*12,height*12);
	        }
	        
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            Graphics2D g2d = (Graphics2D) g.create();
	            
		            for(int x =pointerx;x<width;x++)
		            {
		                for(int y =pointery;y<height;y++)
		                {
		                   if(Table[x][y]=='0')
		                   {
		                	   g2d.setColor(back);
		                   }
		                   for(int i =0;i<org.size();i++)
		                   {
		                	   if(Table[x][y]==org.get(i).getIdentifier())
			                   {
		                		  
			                	   g2d.setColor(org.get(i).getOrg_color());
			                	   
			                   }
		                   }
		                   g2d.fill3DRect(x*12, y*12, 10, 10,true);
		                }
		            }
	            
	            
	            /*ArrayList<Point> al = StepAnother();
	            g2d.setColor(Color.black);
	            for(int x =pointerx;x<height;x++)
	            {
	                for(int y =pointery;y<width;y++)
	                {	 
	                   
	                   g2d.fillRect(x*12, y*12, 10, 10);
	                }
	            }
	            g2d.setColor(Color.orange);
	            for(int i =0;i<al.size();i++)
	            {
	            	g2d.fillRect((int)al.get(i).getX()*12, (int)al.get(i).getX()*12, 10, 10);
	                
	            }*/
	            
	            
	            g2d.dispose();
	            
	        }

	    }
	
	
	
}
