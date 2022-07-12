import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.Random;

public class Window extends JFrame implements KeyListener{
	public JLabel[][] MAP=new JLabel[20][20];
	public CRDS A=new CRDS();
	boolean APT=true;
	public int sp=200;
	public Random rand=new Random();
	int len=1;
	public int scor=0;
	public CRDS[] SN=new CRDS[80];
	int dx=1,dy=0;
	int hx=0,hy=0;
	public void DEFMAP() {
		int x=40,y=30;
		for(int i=0;i<20;i++) {
			for(int j=0;j<20;j++) {
				MAP[i][j]=new JLabel();
				MAP[i][j].setOpaque(true);
				MAP[i][j].setBackground(new Color(19, 125, 21));
				if(x==440) {
					x=40;
					y+=20;
				}
				MAP[i][j].setBounds(x,y,20,20);
				x+=20;
			}
		}
	}
	public void snekDraw() {
		for(int i=0;i<len;i++) {
			MAP[SN[i].y][SN[i].x].setBackground(new Color(0, 0, 255));
		}
		MAP[A.y][A.x].setBackground(Color.RED);
	}
	public void add() {
		for(int i=0;i<20;i++) {
			for(int j=0;j<20;j++) {
				this.add(MAP[i][j]);
			}
		}
	}
	public void resetCan() {
		for(int i=0;i<20;i++) {
			for(int j=0;j<20;j++) {
				MAP[i][j].setBackground(new Color(19, 125, 21));
			}
		}
	}
	public void bound() {
		/*<--- bounds ---->*/
		boolean t=false;
		for(int i=1;i<len;i++) {
			if(SN[0].x==SN[i].x && SN[0].y==SN[i].y) {
				t=true;
			}
		}
		if(hy>=20 || hy<0 || hx>=20 || hx<0 || t) {
			JOptionPane.showMessageDialog(
					null,
					"GAME OVER",
					"<MESAJ>",
					JOptionPane.OK_OPTION);
			this.dispose();
			System.exit(0);
		}
		/*<----- MARUL FERMECAT ------>*/
		if(SN[0].x==A.x && SN[0].y==A.y) {
			len++;
			scor++;
			calc();
			APT=true;
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		if(key==KeyEvent.VK_UP && dy!=1) {
			dy=-1;
			dx=0;
		}
		if(key==KeyEvent.VK_DOWN && dy!=-1) {
			dy=1;
			dx=0;
		}
		if(key==KeyEvent.VK_LEFT && dx!=1) {
			dx=-1;
			dy=0;
		}
		if(key==KeyEvent.VK_RIGHT && dx!=-1) {
			dx=1;
			dy=0;
		}
	}
	public void initCORD() {
		for(int i=0;i<80;i++) {
			SN[i]=new CRDS(0,0);
		}
	}
	public void calc() {
		for(int i=len-1;i>=1;i--) {
			SN[i].x=SN[i-1].x;
			SN[i].y=SN[i-1].y;		
		}
		hx+=dx;
		hy+=dy;
		SN[0].x=hx;
		SN[0].y=hy;
		if(APT==true) {
			sp-=5;
			A.x=rand.nextInt(20);
			A.y=rand.nextInt(20);
			APT=false;
		}
	}
	Window(){
		JLabel sc=new JLabel();
		sc.setBounds(40,450,100,50);
		sc.setFont(new Font("Courier new",Font.BOLD,20));
		this.setSize(500, 600);
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(null);
		DEFMAP();
		initCORD();
		add();
		this.add(sc);
		this.addKeyListener(this);
		this.setVisible(true);
		while(true) {//game loop
			try {
				resetCan();
				calc();
				bound();
				snekDraw();
				sc.setText("SCOR "+scor);
				SwingUtilities.updateComponentTreeUI(this);
				Thread.sleep(sp);			
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}