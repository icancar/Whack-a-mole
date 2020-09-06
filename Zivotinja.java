package zadatak1;

import java.awt.Graphics;

public abstract class Zivotinja {
	protected Rupa rupa;
	protected boolean udarena=false;
	public abstract double dohvPrecnik();
	public Zivotinja(Rupa r) {
		rupa=r;
	}
	public abstract void crtaj(Graphics g);
	public abstract void pobjeglaZivotinja();
	public abstract void udarenaZivotinja();
	public abstract void postPrecnik(double d);
	public abstract void postCentarx(double a);
	public abstract void postCentary(double b);
	public abstract double dohvCentarx();
	public abstract double dohvCentary();
	public boolean udarena() {return udarena;}
	public void postUdarena(boolean da) {udarena=da;}
}
