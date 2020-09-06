package zadatak1;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Igra extends Frame {
	private Basta basta;
	private Label poeni;
	private Button dugme = new Button("Kreni");

	// private Checkbox
	public Igra() {
		super("Igra");
		setBounds(750, 200, 475, 400);
		basta = new Basta(4, 4);
		basta.setSize(350, 350);
		basta.inicijalizujMatricu();
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				if(basta.running()) {
				basta.zaustavi();
				}
				dispose();

			}

		});
		add(basta, BorderLayout.CENTER);
		add(eastern(), BorderLayout.EAST);
		basta.postInterval(1000);
		basta.postBrKoraka(10);
		// basta.pokreni();
		basta.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent arg0) {
				System.out.println("UDARAC");
				basta.provjeriUdarac(arg0);
			}

		});
		setResizable(false);
		setVisible(true);
	}

	private Panel eastern() {
		Panel istok = new Panel(new GridLayout(8, 1, 10, 10));
		Label nivoi = new Label("Tezina: ");
		nivoi.setFont(new Font(null, Font.BOLD, 20));
		nivoi.setAlignment(Label.CENTER);
		istok.add(nivoi);

		CheckboxGroup tezina = new CheckboxGroup();
		Checkbox easy = new Checkbox("Lako", tezina, true);
		Checkbox middle = new Checkbox("Srednje", tezina, false);
		Checkbox hard = new Checkbox("Tesko", tezina, false);
		istok.add(easy);
		istok.add(middle);
		istok.add(hard);
		easy.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				basta.postBrKoraka(10);
				basta.postInterval(1000);
			}

		});
		middle.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				basta.postBrKoraka(8);
				basta.postInterval(750);
			}

		});
		hard.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				basta.postBrKoraka(6);
				basta.postInterval(500);
			}

		});

		istok.setSize(200, 500);
		dugme.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (dugme.getLabel().equals("Kreni")) {
					System.out.println("INTERVAL: " + basta.interval());
					basta.pokreni();
					dugme.setLabel("Stani");
				} else {
					basta.zaustavi();
					dugme.setLabel("Kreni");
				}

			}

		});
		istok.add(dugme, BorderLayout.CENTER);

		poeni = new Label("Povrce: 100");
		basta.postLabelu(poeni);
		poeni.setFont(new Font(null, Font.BOLD, 20));
		istok.add(poeni);
		return istok;

	}

	public static void main(String[] args) {
		new Igra();
	}
}
