package zadatak1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Basta extends Panel implements Runnable {
	private Rupa[][] matrica;
	private int povrce = 100;
	private int dt, brojKoraka;
	private int vrsta, kolona;
	private Krtica krtica;
	private Label labelaPoena;
	private Thread nit ;
	private boolean radi = false;

	public Basta(int brojVrsta, int brojKolona) {
		vrsta = brojVrsta;
		kolona = brojKolona;
		matrica = new Rupa[vrsta][kolona];
		setSize(350, 500);
	}
	public void postLabelu(Label l) {labelaPoena=l;}
	public synchronized void provjeriUdarac(MouseEvent e) {
		if(radi) {
		int x=e.getX();
		int y=e.getY();
		System.out.println("X= "+x+","+"Y= "+y);
		for(int i=0;i<vrsta;i++) {
			for(int j=0;j<kolona;j++) {
				if (matrica[i][j].getX()+matrica[i][j].getHeight()>=x && matrica[i][j].getY()+matrica[i][j].getWidth()>=y && matrica[i][j].getX()<x && matrica[i][j].getY()<y ) {
					System.out.println("UPAD:"+i+","+j);
					if(matrica[i][j].zivotinja()!=null)
					matrica[i][j].zgaziRupu();
					repaint();
				}
			}
		}
		}
	}

	public void inicijalizujMatricu() {
		setBackground(Color.GREEN);
		int sirinaRupe = getWidth() / kolona;
		int visinaRupe = getHeight() / vrsta;
		sirinaRupe -= sirinaRupe * 0.1;
		visinaRupe -= visinaRupe * 0.1;
		for (int i = 0; i < kolona; i++) {
			for (int j = 0; j < vrsta; j++) {
				matrica[i][j] = new Rupa(this);
				matrica[i][j].setSize(sirinaRupe, visinaRupe);
				matrica[i][j].setLocation(i * (int) (sirinaRupe * 1.1), j * (int) (visinaRupe * 1.1));
			}
		}
	}

	public synchronized int brKoraka() {
		return brojKoraka;
	}

	public void postBrKoraka(int a) {
		brojKoraka = a;
		for (int i = 0; i < vrsta; i++) {
			for (int j = 0; j < kolona; j++) {
				matrica[i][j].postBrojKoraka(a);
			}
		}
	}
	
	public synchronized void smanjiPovrce() {
		System.out.println("SMANJUJE POVRCE: "+nit.toString());
		povrce--;
	}

	public synchronized void pokreni() {
		System.out.println("KREIRANA NIT BASTE");
		nit=new Thread(this);
		nit.setName("BASTA");
		nit.start();
		radi = true;
		for (int i = 0; i < vrsta; i++) {
			for (int j = 0; j < kolona; j++)
				matrica[i][j].slobodna(true);
		}
		notifyAll();
	}
	public void zaustavi() {
		nit.interrupt();
		for(int i=0;i<kolona;i++)
			for(int j=0;j<vrsta;j++) {
				if(matrica[i][j].pokrenutaNit())
					matrica[i][j].zaustavi();
			}
		System.out.println("Umrla nit BASTA");
	}

	public void postInterval(int d) {
		dt = d;
	}

	public void slobodna(Rupa r) {
		for (int i = 0; i < vrsta; i++) {
			for (int j = 0; j < kolona; j++) {
				if (matrica[i][j].equals(r))
					matrica[i][j].slobodna(true);
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		for (int i = 0; i < vrsta; i++) {
			for (int j = 0; j < kolona; j++) {
				matrica[i][j].paint(g);
			}
		}
	}

	@Override
	public void run() {
		try {
			synchronized (this) {
				while (!radi)
					wait();
			}
			while (true) {
				nit.sleep(dt);
				int x = (int) (Math.random() * kolona);
				int y = (int) (Math.random() * vrsta);
				repaint();
				if (matrica[x][y].slobodna()) {
					krtica = new Krtica(matrica[x][y]);
					matrica[x][y].slobodna(false);
					matrica[x][y].postZivotinju(krtica);
					matrica[x][y].postBrojKoraka(15);
					matrica[x][y].stvoriNit();
					matrica[x][y].pokreniNit();
					dt -= 0.01 * dt;
				}
				azurirajLabelu();
				repaint();
				if(povrce==0) nit.interrupt();
			}
		} catch (InterruptedException g) {
		}
	}

	private void azurirajLabelu() {
		labelaPoena.setText("Povrce: "+povrce);
		
	}
	public int interval() {
		return dt;
	}
	public boolean running() {
			return radi;
	}
}
