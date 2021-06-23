package simulation.vue;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import simulation.reseau.*;

/**
 * panel graphique principal
 * @version 3.0
 * @author Ahmed CHEGGAF, Pierre-Louis de Villers
 */

public class PanelDrawing extends JPanel implements MouseListener, MouseMotionListener{

    private static final long serialVersionUID = 1L;

    /**
     * Liste des routes affichées
     */
    private List<Route> listRoute = new ArrayList<Route>();
    
    /**
     * Liste des ronds points affichés
     */
    private List<RondPoint> listRondPoint = new ArrayList<RondPoint>();
    
    /**
     * Liste des sources affichées
     */
    private List<Source> listSource = new ArrayList<Source>();
    
    /**
     * Liste des intersections affichées
     */
    private List<Intersection> listIntersection = new ArrayList<Intersection>();
    
    /**
     * Liste des points utilisés
     */
    private MapPoint[] listPoint = new MapPoint[2];
    
    /**
     * Compteur de clics
     */
    private int clicks = 0;
    
    /**
     * Indicateur de l'élement à dessiner
     */
    private int indexToDraw = 1;
    
    /**
     * composant selectionné
     */
    private JComponent selected;
    
    /**
     * dernier composant selectionné
     */
    private JComponent lastSelected;
    
    /**
     * Vitesse des routes courante (par défaut à 50)
     */
    private int currentRoadSpeed = 50;
    
    /**
     * Fréquence des sources courante
     */
    private double currentSourceFreq = NoeudEnSo.FREQUENCE_PAR_DEFAUT;
    
    /**
     * Réseau utilisé pour la simulation
     */
    private Reseau reseau = new Reseau();
    
    /**
     * Orientation de sources, true pour horizontal, false pour vertical
     */
    private boolean sourceOrientation = false;
    
    /**
     * Directions disponible sur l'intersection
     */
    private boolean[] intersectionDirection = {true, true, true, true};
    
    /**
     * Compteur d'intersections
     */
    private int irCounter = 0;
    
    /**
     * coordonnées du curseur
     */
    private int currentX = 0, currentY = 0;
    
    /**
     * Opacité
     */
    private int currentOpacity = 255;
    
    /**
     * Nombre de voies des routes (par défaut à 2)
     */
    private int nbVoies = 2;

    /**
     * Constructeur du Panel, initialise les listeners, et rend le panel visible
     */
    PanelDrawing() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setVisible(true);
    }
    
    @Override
	public void paint(Graphics g){
    	
    	// On peint l'arrière plan du panel manuellement
    	g.setColor(getBackground());
    	g.fillRect(0, 0, getWidth(), getHeight());
    	g.setColor(getForeground());

        for (Route i : listRoute){
            i.paint(g);
        }
        for (RondPoint i : listRondPoint){
            i.paint(g);
        }
        for (Source i : listSource){
            i.paint(g);
        }
        for (Intersection i : listIntersection){
            i.paint(g);
        }
        Color myColor = new Color(241, 68, 68, currentOpacity);
        g.setColor(myColor);

        g.drawLine(0,currentY,this.getWidth(),currentY);
        g.drawLine(currentX,0,currentX,this.getHeight());
    }


    /**
	 * Modifie l'indicateur de l'élément à dessiner
	 * @param changeTo nouvelle valeur de l'indicateur
	 */
    private void setIndex(int changeTo){
	    indexToDraw = changeTo;
	}

	/**
	 * Vide le contenu du panel
	 * @param thePanel
	 */
    private void clearAll(JPanel thePanel){
	    selected = null;
	    lastSelected = null;
	    irCounter = 0;
	    Iterator<RondPoint> iterator1 = listRondPoint.iterator();
	    Iterator<Route> iterator = listRoute.iterator();
	    Iterator<Source> iterator2 = listSource.iterator();
	    Iterator<Intersection> iterator3 = listIntersection.iterator();
	    while (iterator.hasNext()){
	        Route i = iterator.next();
	        iterator.remove();
	        this.remove(i);
	
	    }
	    while (iterator1.hasNext()){
	        RondPoint i = iterator1.next();
	        iterator1.remove();
	        this.remove(i);
	    }
	    while (iterator2.hasNext()){
	        Source i = iterator2.next();
	        iterator2.remove();
	        this.remove(i);
	    }
	    while (iterator3.hasNext()){
	        Intersection i = iterator3.next();
	        iterator3.remove();
	        this.remove(i);
	    }
	    this.validate();
	    this.repaint();
	    thePanel.validate();
	    thePanel.repaint();
	}

	@Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton()==MouseEvent.BUTTON1){

            if (indexToDraw == 1) {
                if (selected != null) drawRoute(e);
            }
            else if (indexToDraw == 2) {
                if( !(selected instanceof RondPoint)) drawRondPoint(e);
            }
            else if (indexToDraw == 3){
                drawSource(e);
            }
            else if (indexToDraw == 4){
                drawIntersection(e);
            }
        }
        else {
            deleteRoute(e);
        }
    }

    @Override
	public void mouseMoved(MouseEvent e){
	    int x = e.getX();
	    int y = e.getY();
	    currentX = x;
	    currentY = y;
	    this.validate();
	    this.repaint();
	    int j = 0;
	    for (RondPoint i : listRondPoint){
	        if (isIN(i,x,y)){
	            i.whichSideToHighlight(x, y);
	            j++;
	            selected = i;
	        }
	        else{
	            i.resetHighlight();
	        }
	        this.validate();
	        this.repaint();
	    }
	    if (j==0) selected = null;
	    for (Route i : listRoute){
	        if (isIN(i,x,y)){
	            i.changeColor(true);
	            selected = i;
	        }
	        else i.changeColor(false);
	        this.validate();
	        this.repaint();
	    }
	    for (Source i : listSource){
	        if (isIN(i,x,y)){
	            i.Highlight();
	            selected = i;
	        }
	        else i.unHighlight();
	        this.validate();
	        this.repaint();
	    }
	    for (Intersection i : listIntersection){
	        if (isIN(i,x,y)){
	            i.Highlight();
	            selected = i;
	        }
	        else i.unHighlight();
	        this.validate();
	        this.repaint();
	    }
	}

	@Override
	public void mouseDragged(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	/**
	 * Vérifie si un point se trouve sur une route
	 * @param routeToCheck route à vérifier
	 * @param x abscisse du point
	 * @param y ordonnée du point
	 * @return true si le point se trouve sur la route
	 */
	private boolean isIN(Route routeToCheck, int x, int y){
	    Rectangle r = new Rectangle(routeToCheck.x1-routeToCheck.centreX,
	            routeToCheck.y1-routeToCheck.centreY,routeToCheck.w,routeToCheck.h);
	
	    return r.contains(x,y);
	}

	/**
	 * Vérifie si un point se trouve sur une source
	 * @param sourceToCheck source à vérifier
	 * @param x abscisse du point
	 * @param y ordonnée du point
	 * @return true si le point se trouve sur la source
	 */
	private boolean isIN(Source sourceToCheck, int x, int y){
	    Rectangle r;
	    if (!sourceOrientation)
	    r = new Rectangle(sourceToCheck.x1-5,
	            sourceToCheck.y1-(sourceToCheck.rayon/2),10,(sourceToCheck.rayon));
	    else r = new Rectangle(sourceToCheck.x1-(sourceToCheck.rayon/2),
	                sourceToCheck.y1-5,(sourceToCheck.rayon),10);
	    return r.contains(x,y);
	}

	/**
	 * Vérifie si un point se trouve sur un rond-point
	 * @param rpToCheck rond-point à vérifier
	 * @param x abscisse du point
	 * @param y ordonnée du point
	 * @return true si le point se trouve sur le rond-point
	 */
	private boolean isIN(RondPoint rpToCheck, int x, int y){
	    Rectangle r = new Rectangle(rpToCheck.x1-((int)(rpToCheck.rayon/2)),
	            rpToCheck.y1-((int)(rpToCheck.rayon/2)),rpToCheck.rayon,rpToCheck.rayon);
	    return r.contains(x,y);
	}

	/**
	 * Vérifie si un point se trouve sur une intersection
	 * @param intersectionToCheck intersection à vérifier
	 * @param x abscisse du point
	 * @param y ordonnée du point
	 * @return true si le point se trouve sur l'intersection
	 */
	private boolean isIN(Intersection intersectionToCheck, int x, int y){
	    Rectangle r = new Rectangle(intersectionToCheck.x1-(intersectionToCheck.rayon/2),
	            intersectionToCheck.y1-(intersectionToCheck.rayon/2),(intersectionToCheck.rayon),(intersectionToCheck.rayon));
	
	    return r.contains(x,y);
	}

	/**
     * Créé et dessine une route
     * @param e
     */
	private void drawRoute(MouseEvent e){
        clicks++;
        if (clicks == 2 ){
            int x = e.getX();
            int y = e.getY();
            MapPoint deuxiemePoint = new MapPoint(x,y);
            listPoint[1] = deuxiemePoint;
            listRoute.add(new Route(listPoint[0].getX(),listPoint[0].getY(),
                    listPoint[1].getX(),listPoint[1].getY(),2,20,currentRoadSpeed));
            this.add(listRoute.get(listRoute.size()-1));
            this.validate();
            this.repaint();
            clicks = 0;

            Noeud n1;
            Noeud n2;
            if (!(lastSelected instanceof Route) && !(selected instanceof Route)){
                n1 = reseau.getNoeud(getName(lastSelected));
                n2 = reseau.getNoeud(getName(selected));
                reseau.creerAxe(n1,n2,"A"+listRoute.size(),currentRoadSpeed,nbVoies);
            }
            else if ((lastSelected instanceof Route) && !(selected instanceof Route)){
                irCounter++;
                n1 = reseau.creerNoeud(listPoint[0].x, listPoint[0].y, "IR" + irCounter, TypeNoeud.DEFAULT);
                n2 = reseau.getNoeud(getName(selected));
                reseau.creerAxe(n1,n2,"A"+listRoute.size(),currentRoadSpeed,nbVoies);
            }
            else if (!(lastSelected instanceof Route) && (selected instanceof Route)){
                irCounter++;
                n2 = reseau.creerNoeud(listPoint[1].x, listPoint[1].y, "IR" + irCounter, TypeNoeud.DEFAULT);
                n1 = reseau.getNoeud(getName(lastSelected));
                reseau.creerAxe(n1,n2,"A"+listRoute.size(),currentRoadSpeed,nbVoies);
            }
            else if ((lastSelected instanceof Route) && (selected instanceof Route)){
                irCounter++;
                n2 = reseau.creerNoeud(listPoint[1].x, listPoint[1].y, "IR" + irCounter, TypeNoeud.DEFAULT);
                irCounter++;
                n1 = reseau.creerNoeud(listPoint[0].x, listPoint[0].y, "IR" + irCounter, TypeNoeud.DEFAULT);
                reseau.creerAxe(n1,n2,"A"+listRoute.size(),currentRoadSpeed,nbVoies);
            }

        } else {
            int x = e.getX();
            int y = e.getY();
            lastSelected = selected;
            MapPoint premierPoint = new MapPoint(x,y);
            listPoint[0] = premierPoint;
        }
    }

    /**
     * Supprime une route
     * @param e
     */
	private void deleteRoute(MouseEvent e){
        Iterator<Route> iterator = listRoute.iterator();
        while (iterator.hasNext()){
            Route i = iterator.next();
            if (isIN(i,e.getX(),e.getY())){
                iterator.remove();
                this.remove(i);
                this.validate();
                this.repaint();
            }
        }
        Iterator<RondPoint> iterator1 = listRondPoint.iterator();
        while (iterator1.hasNext()){
            RondPoint i = iterator1.next();
            if (isIN(i,e.getX(),e.getY())){
                iterator1.remove();
                this.remove(i);
                this.validate();
                this.repaint();
            }
        }

        Iterator<Source> iterator2 = listSource.iterator();
        while (iterator2.hasNext()){
            Source i = iterator2.next();
            if (isIN(i,e.getX(),e.getY())){
                iterator2.remove();
                this.remove(i);
                this.validate();
                this.repaint();
            }
        }
        Iterator<Intersection> iterator3 = listIntersection.iterator();
        while (iterator3.hasNext()){
            Intersection i = iterator3.next();
            if (isIN(i,e.getX(),e.getY())){
                iterator3.remove();
                this.remove(i);
                this.validate();
                this.repaint();
            }
        }
    }

    /**
     * Créé et dessine un rond-point
     * @param e
     */
	private void drawRondPoint(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        listRondPoint.add(new RondPoint(x,y));
        this.add(listRondPoint.get(listRondPoint.size()-1));
        reseau.creerNoeud(x,y,"R"+String.valueOf(listRondPoint.size()),TypeNoeud.DEFAULT);
        this.validate();
        this.repaint();
    }

    /**
     * Créé et dessine une source
     * @param e
     */
	private void drawSource(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        listSource.add(new Source(x, y, currentSourceFreq, sourceOrientation));
        this.add(listSource.get(listSource.size()-1));
        reseau.creerNoeud(x,y,"S"+String.valueOf(listSource.size()),TypeNoeud.ENTREE_SORTIE, currentSourceFreq);
        this.validate();
        this.repaint();
    }
    
	/**
	 * Créé et dessine une intersection
	 * @param e
	 */
	private void drawIntersection(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        listIntersection.add(new Intersection(x, y, intersectionDirection[0],
                intersectionDirection[1],intersectionDirection[2],intersectionDirection[3]));
        this.add(listIntersection.get(listIntersection.size()-1));
        reseau.creerNoeud(x,y,"I"+String.valueOf(listIntersection.size()),TypeNoeud.DEFAULT);
        this.validate();
        this.repaint();
    }
    
    /**
     * Change la fréquence des sources à créer
     * @param freq nouvelle valeur pour la fréquence
     */
	private void changeSourceFrequency(double freq){
        currentSourceFreq = freq;
    }
    
    /**
     * Change la vitesse des routes à créer
     * @param speed nouvelle valeur pour la vitesse
     */
	private void changeRoadSpeed(int speed){
        currentRoadSpeed = speed;
    }
    
    /**
     * Change l'orientation des sources à créer
     * @param changeTo nouvelle orientation des sources
     */
	private void changeSourceOrientation(boolean changeTo){
        sourceOrientation = changeTo;
    }

    /**
     * Change l'opacité des élements à créer
     * @param newOpacity nouvelle valeur pour l'opacité
     */
	private void changeOpacity(int newOpacity){
        currentOpacity = newOpacity;
    }

    /**
     * Change le nombre de voies des routes à créer
     * @param nbV nouvelle valeur pour le nombre de voies
     */
	private void changeNbVoies(int nbV){
        nbVoies = nbV;
    }

    /**
     * Créé le JPanel à afficher
     * @param x abscisse du JPanel
     * @param y ordonnée du JPanel
     * @param width largeur du JPanel
     * @param height hauteur du JPanel
     * @return le JPanel à afficher
     */
    static JPanel getPanel(int x, int y, int width, int height){

        //Panel principale
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        //Panel graphique ou on dessine

        PanelDrawing pp = new PanelDrawing();
        pp.setBounds(x,40,width,400);

        //Panel parametrique

        JPanel[] panelParametrique = {new JPanel()};

        int heightPanel = 60;
        panelParametrique[0].setBounds(0,440,width,heightPanel);
        panelParametrique[0].setBackground(Color.orange);
        JPanel routeParams = routePanel( 0, 440, width, heightPanel, pp);
        JPanel rondParams = rondPanel( 0, 440, width, heightPanel, pp);
        JPanel sourceParams = sourcePanel( 0, 440, width, heightPanel, pp);
        JPanel interParams = intersectionPanel( 0, 440, width, heightPanel, pp);
        panelParametrique[0] = routeParams;

        //Panel contenant les buttons
        JPanel buttonsPanel = new JPanel();

        buttonsPanel.setBounds(0,0,width,40);
        buttonsPanel.setBackground(Color.orange);

        //les buttons
        JButton buttonRoute = new JButton("Route");
        JButton buttonSource = new JButton("Source");
        JButton buttonRondPoint = new JButton("Rond Point");
        JButton buttonIntersection = new JButton("Intersection");
        JButton buttonClearAll = new JButton("Clear");
        JSlider opacitySlider = new JSlider();
        opacitySlider.setValue(200);
        opacitySlider.setSize(100,10);
        opacitySlider.setMinimum(0);
        opacitySlider.setMaximum(255);

        opacitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                pp.changeOpacity(opacitySlider.getValue());
            }
        });

        // selection entre rond point et route
        buttonRoute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pp.setIndex(1);
                mainPanel.remove(panelParametrique[0]);
                panelParametrique[0] = routeParams;
                panelParametrique[0].setVisible(true);
                mainPanel.add(panelParametrique[0]);
                mainPanel.setVisible(true);
                mainPanel.validate();
                mainPanel.repaint();
            }
        });

        buttonRondPoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pp.setIndex(2);
                mainPanel.remove(panelParametrique[0]);
                panelParametrique[0] = rondParams;
                panelParametrique[0].setVisible(true);
                mainPanel.add(panelParametrique[0]);
                mainPanel.setVisible(true);
                mainPanel.validate();
                mainPanel.repaint();
            }
        });
        
        buttonSource.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pp.setIndex(3);
                mainPanel.remove(panelParametrique[0]);
                panelParametrique[0] = sourceParams;
                panelParametrique[0].setVisible(true);
                mainPanel.add(panelParametrique[0]);
                mainPanel.setVisible(true);
                mainPanel.validate();
                mainPanel.repaint();
            }
        });
        
        buttonClearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pp.clearAll(mainPanel);
                mainPanel.remove(panelParametrique[0]);
                panelParametrique[0].setVisible(true);
                mainPanel.add(panelParametrique[0]);
                mainPanel.setVisible(true);
                mainPanel.validate();
                mainPanel.repaint();
            }
        });
        
        buttonIntersection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pp.setIndex(4);
                mainPanel.remove(panelParametrique[0]);
                panelParametrique[0] = interParams;
                panelParametrique[0].setVisible(true);
                mainPanel.add(panelParametrique[0]);
                mainPanel.setVisible(true);
                mainPanel.validate();
                mainPanel.repaint();
            }
        });

        buttonsPanel.add(buttonRoute);
        buttonsPanel.add(buttonRondPoint);
        buttonsPanel.add(buttonSource);
        buttonsPanel.add(buttonIntersection);
        buttonsPanel.add(buttonClearAll);
        buttonsPanel.add(opacitySlider);
        opacitySlider.setVisible(true);

        buttonClearAll.setVisible(true);
        buttonRondPoint.setVisible(true);
        buttonRoute.setVisible(true);
        buttonSource.setVisible(true);
        buttonsPanel.setVisible(true);


        mainPanel.add(buttonsPanel);
        mainPanel.add(panelParametrique[0]);
        mainPanel.add(pp);

        mainPanel.setBounds(x,y,width,height);
        return mainPanel;
    }
    
    /**
     * Créé le JPanel de paramétrage des routes
     * @param x abscisse du JPanel
     * @param y ordonnée du JPanel
     * @param width largeur du JPanel
     * @param height hauteur du JPanel
     * @param panelD référence vers l'objet contenant
     * @return le JPanel de paramétrage des routes
     */
    private static JPanel routePanel(int x, int y, int width, int height, PanelDrawing panelD){
        JPanel route = new JPanel();
        route.setBounds(x,y,width,height);
        Color maCouleur = new Color(255, 180, 0);
        route.setBackground(maCouleur);
        route.setLayout(null);
        JLabel textVitesse = new JLabel("Vitesse");
        JTextField  textFieldVitesse = new JTextField();
        JButton confirm = new JButton("OK");
        JCheckBox feuRouge = new JCheckBox(" Feu Rouge");
        
        JSeparator freqSeparator = new JSeparator(SwingConstants.VERTICAL);
        
        JLabel textnbVoies = new JLabel(" | Nombre de voies");
        JTextField  textFieldnbVoies = new JTextField();
        JCheckBox doubleSens = new JCheckBox(" Double sens");
        doubleSens.setSelected(true);
        
        textFieldVitesse.addActionListener(new ActionListener() {
        	
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            	
	                String vitesse = textFieldVitesse.getText();
	                int speed = Integer.parseInt(vitesse);
	                panelD.changeRoadSpeed(speed);

	        	} catch (NumberFormatException nfo) {
	        		JOptionPane.showInternalMessageDialog(null, "Erreur, la valeur de la vitesse doit être un nombre !", "Erreur", JOptionPane.ERROR_MESSAGE);
	        	}
            }
        });
        
        confirm.addActionListener(new ActionListener() {
        	
        	@Override
            public void actionPerformed(ActionEvent e) {
            	try {
            	
	                String vitesse = textFieldVitesse.getText();
	                int speed = Integer.parseInt(vitesse);
	                panelD.changeRoadSpeed(speed);

	        	} catch (NumberFormatException nfo) {
	        		JOptionPane.showInternalMessageDialog(null, "Erreur, la valeur de la vitesse doit être un nombre !", "Erreur", JOptionPane.ERROR_MESSAGE);
	        	}
            }
        });
        
        textFieldnbVoies.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String nbVoie = textFieldnbVoies.getText();
                    int nbV = Integer.parseInt(nbVoie);
                    panelD.changeNbVoies(nbV);

                } catch (NumberFormatException nfo) {
                    JOptionPane.showInternalMessageDialog(null, "Erreur, la valeur de nbVoies doit être un nombre !", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        

        textVitesse.setBounds(10,0,50,30);
        textVitesse.setVisible(true);
        textFieldVitesse.setBounds(60,0,100,30);
        textFieldVitesse.setVisible(true);
        confirm.setBounds(170,0,60,30);
        confirm.setVisible(true);
        freqSeparator.setBounds(240, 0, 1, 30);
        freqSeparator.setVisible(true);
        feuRouge.setBounds(250,0,140,30);
        feuRouge.setVisible(true);
        textnbVoies.setBounds(400,0,120,30);
        textnbVoies.setVisible(true);
        textFieldnbVoies.setBounds(530,0,80,30);
        textFieldnbVoies.setVisible(true);
        doubleSens.setBounds(620,0,140,30);
        doubleSens.setVisible(true);

        route.add(textVitesse);
        route.add(textFieldVitesse);
        route.add(confirm);
        route.add(freqSeparator);
        route.add(textnbVoies);
        route.add(textFieldnbVoies);
        route.add(feuRouge);
        route.add(doubleSens);
        route.setVisible(true);
        return route;
    }
    
    /**
     * Créé le JPanel de paramétrage des sources
     * @param x abscisse du JPanel
     * @param y ordonnée du JPanel
     * @param width largeur du JPanel
     * @param height hauteur du JPanel
     * @param panelD référence vers l'objet contenant
     * @return le JPanel de paramétrage des sources
     */
    private static JPanel sourcePanel(int x, int y, int width, int height, PanelDrawing panelD){
        JPanel source = new JPanel();
        source.setBounds(x,y,width,height);
        Color maCouleur = new Color(189, 158, 81);
        source.setBackground(maCouleur);
        source.setLayout(null);
        JLabel textVitesse = new JLabel("Frequence");
        JTextField  textFieldFreq = new JTextField();

        JButton vertical = new JButton("Vertical");
        JButton horizontal = new JButton("Horizontal");
        JButton confirm = new JButton("OK");
        
        JSeparator freqSeparator = new JSeparator(SwingConstants.VERTICAL);


        textFieldFreq.addActionListener(new ActionListener() {
        	
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
	                String freq = textFieldFreq.getText();
	                double frequin = Double.parseDouble(freq);
	                panelD.changeSourceFrequency(frequin);
	        	} catch (NumberFormatException nfo) {
	        		JOptionPane.showInternalMessageDialog(null, "Erreur, la valeur de la fréquence doit être un nombre !", "Erreur", JOptionPane.ERROR_MESSAGE);
	        	}
            }
        });

        horizontal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelD.changeSourceOrientation(true);
            }
        });

        vertical.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelD.changeSourceOrientation(false);
            }
        });
        
        confirm.addActionListener(new ActionListener() {
        	
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
	                String freq = textFieldFreq.getText();
	                double frequin = Double.parseDouble(freq);
	                panelD.changeSourceFrequency(frequin);
	        	} catch (NumberFormatException nfo) {
	        		JOptionPane.showInternalMessageDialog(null, "Erreur, la valeur de la fréquence doit être un nombre !", "Erreur", JOptionPane.ERROR_MESSAGE);
	        	}
            }
        });


        textVitesse.setBounds(10,0,70,30);
        textVitesse.setVisible(true);
        textFieldFreq.setBounds(80,0,100,30);
        textFieldFreq.setVisible(true);
        confirm.setBounds(190,0,60,30);
        freqSeparator.setBounds(260, 0, 1, 30);
        freqSeparator.setVisible(true);
        vertical.setBounds(270,0,80,30);
        horizontal.setBounds(360,0,100,30);
        vertical.setVisible(true);
        horizontal.setVisible(true);
        confirm.setVisible(true);

        source.add(textVitesse);
        source.add(textFieldFreq);
        source.add(freqSeparator);
        source.add(vertical);
        source.add(horizontal);
        source.add(confirm);
        source.setVisible(true);
        return source;
    }
    
    /**
     * Créé le JPanel de paramétrage des ronds-points
     * @param x abscisse du JPanel
     * @param y ordonnée du JPanel
     * @param width largeur du JPanel
     * @param height hauteur du JPanel
     * @param panelD référence vers l'objet contenant
     * @return le JPanel de paramétrage des ronds-points
     */
    private static JPanel rondPanel(int x, int y, int width, int height, PanelDrawing panelD){
        JPanel source = new JPanel();
        source.setBounds(x,y,width,height);
        Color maCouleur = new Color(231, 171, 97);
        JLabel textRond = new JLabel("Rond point");
        textRond.setBounds(10,0,100,30);
        textRond.setVisible(true);

        source.setBackground(maCouleur);
        source.setLayout(null);
        source.add(textRond);
        source.setVisible(true);
        return source;
    }

    /**
     * Créé le JPanel de paramétrage des intersections
     * @param x abscisse du JPanel
     * @param y ordonnée du JPanel
     * @param width largeur du JPanel
     * @param height hauteur du JPanel
     * @param panelD référence vers l'objet contenant
     * @return le JPanel de paramétrage des intersections
     */
    private static JPanel intersectionPanel(int x, int y, int width, int height, PanelDrawing panelD){
        JPanel source = new JPanel();
        source.setBounds(x,y,width,height);
        Color maCouleur = new Color(210, 217, 19);
        JLabel textRond = new JLabel("Intersection");
        textRond.setBounds(10,0,100,30);
        textRond.setVisible(true);
        JCheckBox upBox = new JCheckBox("Route en Haut");
        JCheckBox downBox = new JCheckBox("Route en Bas");
        JCheckBox leftBox = new JCheckBox("Route à Gauche");
        JCheckBox rightBox = new JCheckBox("Route à Droite");
        upBox.setVisible(true);
        downBox.setVisible(true);
        rightBox.setVisible(true);
        leftBox.setVisible(true);
        int xb = 100;
        int widthb = 140;
        int heightb = 30;
        upBox.setBounds(xb,0,widthb,30);
        downBox.setBounds(xb +(1* widthb),0,widthb,heightb);
        rightBox.setBounds(xb +(2* widthb),0,widthb,heightb);
        leftBox.setBounds(xb +(3* widthb),0,widthb,heightb);

        upBox.setSelected(true);
        downBox.setSelected(true);
        rightBox.setSelected(true);
        leftBox.setSelected(true);

        upBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (upBox.isSelected()) panelD.intersectionDirection[0] = true;
                else panelD.intersectionDirection[0] = false;
            }
        });
        downBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (downBox.isSelected()) panelD.intersectionDirection[1] = true;
                else panelD.intersectionDirection[1] = false;
            }
        });
        leftBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (leftBox.isSelected()) panelD.intersectionDirection[2] = true;
                else panelD.intersectionDirection[2] = false;
            }
        });
        rightBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rightBox.isSelected()) panelD.intersectionDirection[3] = true;
                else panelD.intersectionDirection[3] = false;
            }
        });

        source.setBackground(maCouleur);
        source.setLayout(null);
        source.add(textRond);
        source.add(upBox);
        source.add(downBox);
        source.add(rightBox);
        source.add(leftBox);
        source.setVisible(true);
        return source;
    }

    /**
     * Obtiens le nom d'un composant
     * @param comp le composant dont on veut le nom
     * @return le nom du composant
     */
    private String getName(JComponent comp){
        String type1 = "I";
        int t1 = 0;
        if (comp instanceof Source){
            type1 = "S";
            t1 = listSource.indexOf(comp)+1;
        }
        else if (comp instanceof RondPoint){
            type1 = "R";
            t1 = listRondPoint.indexOf(comp)+1;
        }
        else if (comp instanceof Intersection){
            type1 = "I";
            t1 = listIntersection.indexOf(comp)+1;
        }
        return type1+t1;
    }
}