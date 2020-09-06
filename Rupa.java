package zadatak1;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class Rupa extends Canvas implements Runnable  {
	private Color boja=new Color(150,75,0);
	private Basta basta;
	private int brojKoraka;
	private Zivotinja zivotinja;
	private Thread nit;
	private boolean radi=false;
	private boolean slobodna=true;
	
	public Rupa(Basta b) {
		basta=b;
		setBackground(boja);
	}
	//public void postNull() {zivotinja=null;}
	public void postZivotinju(Zivotinja z) {
		zivotinja=z;
	}
	public synchronized Zivotinja zivotinja() {return zivotinja;}
	public void zgaziRupu() {
		if(zivotinja!=null) {
			zivotinja.postUdarena(true);
			zaustavi();
			zivotinja=null;
		}
	}
	public void stvoriNit() {
		nit=new Thread(this);
		System.out.println("Stvara se nit RUPE");
	}
	public synchronized void pokreniNit() {
		nit.start();
		radi=true;
		notifyAll();
	}
	public synchronized boolean pokrenutaNit() {
		return radi;
	}
	public void postBrojKoraka(int a) {brojKoraka=a;}
	public void zaustavi() {System.out.println("Zaustavlja se nit: "+nit.toString());nit.interrupt();}
	@Override
	public void run() {
		try {
			synchronized(this) {while(!radi) wait();}
			double inkrement=getWidth()/brojKoraka;
			//if(zivotinja!=null) {
			zivotinja.postPrecnik(0);
			zivotinja.postCentarx(getWidth()/2+getX());
			zivotinja.postCentary(getHeight()/2+getY());
			while(zivotinja.dohvPrecnik()<getWidth()-5 ) {
				Thread.sleep(100);
				if(zivotinja!=null) {
				zivotinja.crtaj(basta.getGraphics());
				double x=zivotinja.dohvCentarx()-inkrement/2;
				double y=zivotinja.dohvCentary()-inkrement/2;
				double novi=zivotinja.dohvPrecnik()+inkrement;
				zivotinja.postCentarx(x);
				zivotinja.postCentary(y);	
				zivotinja.postPrecnik(novi);
				}
			}
			Thread.sleep(2000);
			if(!zivotinja.udarena()) {
				zivotinja.pobjeglaZivotinja();
				//basta.smanjiPovrce();
			}
			else {
				zivotinja.udarenaZivotinja();
				zivotinja=null;
				repaint();
			}
			
			basta.slobodna(this);
		//	}
			nit.interrupt();
			zivotinja=null;
		}catch(InterruptedException g) {}
		
	}
	public void slobodna(boolean d) {
		slobodna=d;		
	}
	public boolean slobodna() {return slobodna;}
	@Override
	public void paint(Graphics g) {
		g.setColor(boja);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		if(zivotinja!=null) {zivotinja.crtaj(g);}
	}
	public Basta basta() {return basta;}
}
