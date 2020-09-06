package zadatak1;

import java.awt.Color;
import java.awt.Graphics;

public class Krtica extends Zivotinja{
	private double centarx,centary;
	private double precnik;
	public Krtica(Rupa r) {
		super(r);
	}
	@Override
	public void crtaj(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillOval((int)centarx,(int)centary,(int) precnik, (int)precnik);
		
	}
	public void postPrecnik(double r) {precnik=r;}
	public double dohvPrecnik() {return precnik;}
	@Override
	public void pobjeglaZivotinja() {
		rupa.basta().smanjiPovrce();
		
	}

	@Override
	public void udarenaZivotinja() {
		rupa.zaustavi();
		
	}
	@Override
	public void postCentarx(double a) {
		centarx=a;
	}
	@Override
	public void postCentary(double b) {
		centary=b;
	}
	@Override
	public double dohvCentarx() {
		return centarx;
	}
	@Override
	public double dohvCentary() {
		return centary;
	}

}
