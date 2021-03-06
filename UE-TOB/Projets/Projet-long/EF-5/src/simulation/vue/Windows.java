/**
 * @author Guohao
 * This GUI is made by WindowsBuilder.
 */
package simulation.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class Windows extends JFrame {

	private JPanel contentPane;
	private JTextField txtFiledEx1;
	private JTextField txtFiledEx2;
	private JTextField textFieldZoneDepart;
	private JTextField textFieldZoneArrive;
	private JPanel map;
	boolean switchState = false;


	/**
	 * Create the frame.
	 */
	public Windows() {
		setTitle("Simulation Syst\u00E8me Routier");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 885, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		JTabbedPane tabbedPaneMain = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneMain.setFont(new Font("Arial", Font.PLAIN, 15));
		tabbedPaneMain.setBackground(new Color(255, 255, 255));
		JButton switcher = new JButton("Switch Interfaces");

		switcher.setVisible(true);
		contentPane.add(switcher,BorderLayout.NORTH);
		contentPane.add(tabbedPaneMain, BorderLayout.CENTER);

		final JPanel[] creation = {new JPanel()};

		JPanel tempCreation = new JPanel();
		creation[0].setBackground(SystemColor.text);

		tabbedPaneMain.addTab(" Cr\u00E9ation", null, creation[0], null);

		JPanel panelCreationLeft = new JPanel();
		panelCreationLeft.setBackground(SystemColor.textInactiveText);

		JPanel panelCreationCenter = new JPanel();
		panelCreationCenter.setBackground(SystemColor.text);



		JPanel panelCreationRight = new JPanel();
		panelCreationRight.setBackground(SystemColor.textInactiveText);
		GroupLayout gl_creation = new GroupLayout(creation[0]);
		gl_creation.setHorizontalGroup(
				gl_creation.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_creation.createSequentialGroup()
								.addComponent(panelCreationLeft, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelCreationCenter, GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
								.addGap(18)
								.addComponent(panelCreationRight, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE))
		);
		gl_creation.setVerticalGroup(
				gl_creation.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_creation.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_creation.createParallelGroup(Alignment.LEADING)
										.addComponent(panelCreationRight, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(panelCreationCenter, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(panelCreationLeft, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
		);

		JButton btnButton1 = new JButton("Lancer la simulation");

		JButton btnButton2 = new JButton("En arri??re-plan");
		GroupLayout gl_panelCreationRight = new GroupLayout(panelCreationRight);
		gl_panelCreationRight.setHorizontalGroup(
				gl_panelCreationRight.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelCreationRight.createSequentialGroup()
								.addGap(29)
								.addGroup(gl_panelCreationRight.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(btnButton2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnButton1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addContainerGap(29, Short.MAX_VALUE))
		);
		gl_panelCreationRight.setVerticalGroup(
				gl_panelCreationRight.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelCreationRight.createSequentialGroup()
								.addContainerGap()
								.addComponent(btnButton1, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
								.addGap(18)
								.addComponent(btnButton2, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
								.addContainerGap(382, Short.MAX_VALUE))
		);
		panelCreationRight.setLayout(gl_panelCreationRight);


		map = new JPanel ();
		map.setBackground(SystemColor.window);
		map.setVisible(true);
		map.setBounds(0, 0, 400, 400);

		GroupLayout gl_panelCreationCenter = new GroupLayout(panelCreationCenter);
		gl_panelCreationCenter.setHorizontalGroup(
				gl_panelCreationCenter.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelCreationCenter.createSequentialGroup()
								.addContainerGap()
								.addComponent(map, GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
								.addContainerGap())
		);
		gl_panelCreationCenter.setVerticalGroup(
				gl_panelCreationCenter.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelCreationCenter.createSequentialGroup()
								.addGap(5)
								.addComponent(map, GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
								.addContainerGap())
		);
		map.setLayout(null);
		panelCreationCenter.setLayout(gl_panelCreationCenter);


		panelCreationCenter.setBounds(0,60,885,500);
		JButton btnPassagePieton = new JButton("Passage Pi\u00E9ton ");

		JButton btnEntreeSortie = new JButton("Entr??e / Sortie ");
		btnEntreeSortie.addActionListener(this::creerEntreeSortie);

		JButton btnRondPoint = new JButton("Rond Point");
		btnRondPoint.addActionListener(this::creerRondP);

		JButton btnCarrefour = new JButton("Route");
		btnCarrefour.addActionListener(this::creerRoute);

		JButton btnIntersections = new JButton("Intersections");
		btnIntersections.addActionListener(this::creerIntersection);

		JRadioButton rdbtnModeManuel = new JRadioButton("Mode Manuel");
		GroupLayout gl_panelCreationLeft = new GroupLayout(panelCreationLeft);
		gl_panelCreationLeft.setHorizontalGroup(
				gl_panelCreationLeft.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panelCreationLeft.createSequentialGroup()
								.addGap(25)
								.addGroup(gl_panelCreationLeft.createParallelGroup(Alignment.TRAILING)
										.addComponent(rdbtnModeManuel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
										.addComponent(btnPassagePieton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
										.addComponent(btnRondPoint, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnIntersections, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
										.addComponent(btnCarrefour, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
										.addComponent(btnEntreeSortie, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
								.addGap(26))
		);
		gl_panelCreationLeft.setVerticalGroup(
				gl_panelCreationLeft.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelCreationLeft.createSequentialGroup()
								.addContainerGap()
								.addComponent(btnEntreeSortie, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGap(18)
								.addComponent(btnCarrefour, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGap(18)
								.addComponent(btnIntersections, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGap(18)
								.addComponent(btnRondPoint, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGap(18)
								.addComponent(btnPassagePieton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGap(220)
								.addComponent(rdbtnModeManuel)
								.addGap(16))
		);
		panelCreationLeft.setLayout(gl_panelCreationLeft);
		creation[0].setLayout(gl_creation);

		tabbedPaneMain.setEnabledAt(0, true);

		JPanel simulation = new JPanel();
		simulation.setBackground(SystemColor.text);
		tabbedPaneMain.addTab(" Simulation", null, simulation, null);
		simulation.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPaneSimulation = new JTabbedPane(JTabbedPane.TOP);
		simulation.add(tabbedPaneSimulation, BorderLayout.CENTER);

		JPanel panelSimulationRapide = new JPanel();
		panelSimulationRapide.setBackground(SystemColor.text);
		tabbedPaneSimulation.addTab("Rapide", null, panelSimulationRapide, null);
		tabbedPaneSimulation.setEnabledAt(0, true);

		JPanel panelSimulationRapideLeft = new JPanel();
		panelSimulationRapideLeft.setBackground(SystemColor.textInactiveText);

		JPanel panelSimulationRapideRight = new JPanel();
		panelSimulationRapideRight.setBackground(SystemColor.textInactiveText);

		JPanel panelSimulationRapideCenter = new JPanel();
		panelSimulationRapideCenter.setBackground(SystemColor.text);
		GroupLayout gl_panelSimulationRapide = new GroupLayout(panelSimulationRapide);
		gl_panelSimulationRapide.setHorizontalGroup(
				gl_panelSimulationRapide.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelSimulationRapide.createSequentialGroup()
								.addComponent(panelSimulationRapideLeft, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelSimulationRapideCenter, GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelSimulationRapideRight, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE))
		);
		gl_panelSimulationRapide.setVerticalGroup(
				gl_panelSimulationRapide.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panelSimulationRapide.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_panelSimulationRapide.createParallelGroup(Alignment.TRAILING)
										.addComponent(panelSimulationRapideCenter, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
										.addComponent(panelSimulationRapideRight, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
										.addComponent(panelSimulationRapideLeft, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE))
								.addContainerGap())
		);

		JLabel lblZoneDepart = new JLabel("Zone de d\u00E9part ");
		lblZoneDepart.setFont(new Font("Consolas", Font.BOLD, 15));
		lblZoneDepart.setForeground(SystemColor.text);

		JLabel lblZoneArrive = new JLabel("Zone d'arriv\u00E9 ");
		lblZoneArrive.setForeground(Color.WHITE);
		lblZoneArrive.setFont(new Font("Consolas", Font.BOLD, 15));

		textFieldZoneDepart = new JTextField();
		textFieldZoneDepart.setColumns(10);

		textFieldZoneArrive = new JTextField();
		textFieldZoneArrive.setColumns(10);
		GroupLayout gl_panelSimulationRapideLeft = new GroupLayout(panelSimulationRapideLeft);
		gl_panelSimulationRapideLeft.setHorizontalGroup(
				gl_panelSimulationRapideLeft.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelSimulationRapideLeft.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_panelSimulationRapideLeft.createParallelGroup(Alignment.LEADING)
										.addComponent(textFieldZoneDepart, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
										.addComponent(lblZoneDepart, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
										.addComponent(lblZoneArrive, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
										.addComponent(textFieldZoneArrive, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE))
								.addContainerGap())
		);
		gl_panelSimulationRapideLeft.setVerticalGroup(
				gl_panelSimulationRapideLeft.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelSimulationRapideLeft.createSequentialGroup()
								.addGap(37)
								.addComponent(lblZoneDepart)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(textFieldZoneDepart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(37)
								.addComponent(lblZoneArrive, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(textFieldZoneArrive, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(247, Short.MAX_VALUE))
		);
		panelSimulationRapideLeft.setLayout(gl_panelSimulationRapideLeft);

		txtFiledEx2 = new JTextField();
		txtFiledEx2.setText("   Here is Map area!");
		txtFiledEx2.setColumns(10);
		GroupLayout gl_panelSimulationRapideCenter = new GroupLayout(panelSimulationRapideCenter);
		gl_panelSimulationRapideCenter.setHorizontalGroup(
				gl_panelSimulationRapideCenter.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panelSimulationRapideCenter.createSequentialGroup()
								.addContainerGap(167, Short.MAX_VALUE)
								.addComponent(txtFiledEx2, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
								.addGap(160))
		);
		gl_panelSimulationRapideCenter.setVerticalGroup(
				gl_panelSimulationRapideCenter.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelSimulationRapideCenter.createSequentialGroup()
								.addGap(158)
								.addComponent(txtFiledEx2, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(178, Short.MAX_VALUE))
		);
		panelSimulationRapideCenter.setLayout(gl_panelSimulationRapideCenter);
		panelSimulationRapide.setLayout(gl_panelSimulationRapide);

		JPanel panelSimulationDetaille = new JPanel();
		tabbedPaneSimulation.addTab("D\u00E9taill\u00E9 ", null, panelSimulationDetaille, null);
		GroupLayout gl_panelSimulationDetaille = new GroupLayout(panelSimulationDetaille);
		gl_panelSimulationDetaille.setHorizontalGroup(
				gl_panelSimulationDetaille.createParallelGroup(Alignment.LEADING)
						.addGap(0, 851, Short.MAX_VALUE)
		);
		gl_panelSimulationDetaille.setVerticalGroup(
				gl_panelSimulationDetaille.createParallelGroup(Alignment.LEADING)
						.addGap(0, 437, Short.MAX_VALUE)
		);
		panelSimulationDetaille.setLayout(gl_panelSimulationDetaille);
		tabbedPaneMain.setEnabledAt(1, true);

		JPanel donnees = new JPanel();
		tabbedPaneMain.addTab("Donn\u00E9es", null, donnees, null);
		tabbedPaneMain.setEnabledAt(2, true);
		tempCreation = creation[0];
		JPanel finalTempCreation = tempCreation;
		switcher.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switchState = !switchState;
				if (switchState){
					contentPane.remove(tabbedPaneMain);
					tabbedPaneMain.remove(creation[0]);
					tabbedPaneMain.remove(simulation);
					tabbedPaneMain.remove(donnees);
					creation[0] = PanelDrawing.getPanel(0,0,885,500);
					map.setVisible(false);
					tabbedPaneMain.addTab(" Cr\u00E9ation", null, creation[0], null);
					tabbedPaneMain.addTab(" Simulation", null, simulation, null);
					tabbedPaneMain.addTab("Donn\u00E9es", null, donnees, null);
					contentPane.add(tabbedPaneMain);
					contentPane.validate();
					contentPane.repaint();
				}
				else {
					contentPane.remove(tabbedPaneMain);
					tabbedPaneMain.remove(creation[0]);
					tabbedPaneMain.remove(simulation);
					tabbedPaneMain.remove(donnees);
					creation[0] = finalTempCreation;
					map.setVisible(true);
					tabbedPaneMain.addTab(" Cr\u00E9ation", null, creation[0], null);
					tabbedPaneMain.addTab(" Simulation", null, simulation, null);
					tabbedPaneMain.addTab("Donn\u00E9es", null, donnees, null);
					contentPane.add(tabbedPaneMain);
					contentPane.validate();
					contentPane.repaint();
				}
			}
		});
		this.setResizable(false);
	}

	/**
	 * Renvoyer le JPanel de la map.
	 */
	public JPanel getMap() {
		return this.map;
	}

	/**
	 * Action Listeners.
	 */
	private void creerRoute(ActionEvent e) {
		CreerRoute window = new CreerRoute(this);
	}

	private void creerRondP(ActionEvent e) {
		CreerRondP window = new CreerRondP(this);
	}

	private void creerEntreeSortie(ActionEvent e) {
		CreerEntreeSortie window = new CreerEntreeSortie(this);
	}

	private void creerIntersection(ActionEvent e) {
		CreerInter window = new CreerInter(this);
	}
}
