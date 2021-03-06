package edu.utdallas.sharedfiles.Search;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import edu.utdallas.gamePlayEngine.menuFrame;
import edu.utdallas.gamePlayEngine.controller.GameController;
import edu.utdallas.gamePlayEngine.model.GameModel;
import edu.utdallas.gamePlayEngine.model.GameModelBoundary;
import edu.utdallas.gamePlayEngine.view.GameView;
import edu.utdallas.gamegenerator.RepoUpdate.Updates;
import edu.utdallas.sharedfiles.Shared.*;
import edu.utdallas.gamegeneratorcollection.gamegenerator.Structure.*;
import edu.utdallas.gamespecification.Act;
import edu.utdallas.gamespecification.Challenge;
import edu.utdallas.gamespecification.Character;
import edu.utdallas.gamespecification.Game;
import edu.utdallas.gamespecification.Introduction;
import edu.utdallas.gamespecification.Item;
import edu.utdallas.gamespecification.Layout;
import edu.utdallas.gamespecification.MultipleChoiceItem;
import edu.utdallas.gamespecification.QuizChallenge;
import edu.utdallas.gamespecification.Scene;
import edu.utdallas.gamespecification.Screen;
import edu.utdallas.gamespecification.Summary;
import edu.utdallas.previewtool.View.BackgroundSelectWindow;
import edu.utdallas.previewtool.View.CharacterProfileWindow;
import edu.utdallas.previewtool.View.CharacterSelectWindow;
import edu.utdallas.previewtool.View.PropSelectWindow;
import edu.utdallas.previewtool.View.ScenePanel;
import edu.utdallas.previewtool.View.SoundSelectWindow;
import edu.utdallas.previewtool.Error.PreviewError;
import edu.utdallas.previewtool.Error.GameErrorChecker;
import edu.utdallas.previewtool.Error.GameErrorList;
import Jama.Matrix;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class InputWizard implements ActionListener {
/**
 * @Authors Kaleb Breault, Alex Hunsberger, Zayed Alfalasi, Abdulla Alfalasi, Jacob Dahleen
 * This class makes a GUI interface for entering input and previewing XML games
 * implements ActionListener so a subclass for it is not needed. 
 */
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 481;
	private Matrix[] componentInputs;
	private boolean submitClicked = false;
 	private JFrame window = new JFrame();
 	private JPanel mainPannel;
 	private JMenuBar menuBar;
 	private JMenu menu;
 	private JMenu fileMenu;
 	private JMenu gameengine;
 	private JMenu gameEngineMenu;
 	private JMenuItem openFileItem;
 	private JMenuItem opengame;
 	private JMenuItem addToRepo;
 	private JMenuItem remakeRepo;
 	private JMenuItem saveToRepo;
 	private JMenuItem saveToRepoAs;
 	private JMenuItem checkErrorList;
 	private JMenuItem openEngine;
 	private static String label1 = "Preview after generating: ";
 	private JTree gameTree;
 	private ScenePanel scenePanel;
 	//JD character selection class parameters
 	private CharacterSelectWindow characterSelectWindow;
 	private CharacterAsset characterSelectAsset;
 	private PropSelectWindow propSelectWindow;
 	private ImageAsset propSelectAsset;
 	private BackgroundSelectWindow backgroundSelectWindow;
 	private String backgroundSelectPath;
 	private SoundSelectWindow soundSelectWindow;
 	private String soundSelectPath;
 	public enum gameLevel{GAME, ACT, SCENE, SCREEN, CHALLENGE};
 	private gameLevel selectedLevel = null;
 	private JButton characterButton;
 	private JButton propButton;
 	private JButton backgroundAndHiddenButton;
 	private JButton soundButton;
 	private JButton textButton;
 	private JButton buttonButton;
 	private Scene lastSelectedScene = null;
 	private Screen lastSelectedScreen = null;
 	private File Currentfile = null;
 	private boolean hasCriticalGameErrors = false;
 	private String toggleHiddenText = "Show/Hide";
 	//JD end
 	
 	private Game game;
 	
 	private String playerGender= "none"; 
  	private String playerAge= "none";	
  	private String playerDress= "none";  	
   	private String gameTheme= "none";	
 	private String gameSubject= "none";  	
 	private String gameSetting= "none";   	
 	private String gameDifficulty= "none"; 
 	private JFileChooser saveFileChooser;
 	private String gameSavePath = "C:\\";
 	private static final int wizardRowSize = 10; //row size for wizard
 	private String gameGradeLevel = "none";
	private Updates updater;
	private String charBaseDir = "Office, Classroom\\Characters\\";
	public static final String soundFolder = "AudioAssetRepository\\";
	private int selectedValue = 1;
	CharacterAsset c;
	final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	
	public InputWizard(final Matrix[] input)
	{
		componentInputs = input;
		initializeComponentInputs();
        window.setSize(WIDTH, HEIGHT);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int nextOpenRow = 0;
        // next available row slot
        final String none = "no";
        mainPannel = new JPanel(new GridLayout(wizardRowSize, 1));
// making menu
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        openFileItem = new JMenuItem("Open Game File");
        openFileItem.setActionCommand("openFile");
        openFileItem.setMnemonic(KeyEvent.VK_O);
        openFileItem.addActionListener(this);
        fileMenu.add(openFileItem);
        menuBar.add(fileMenu);
        menu = new JMenu("Repository Tools");
        menu.setMnemonic(KeyEvent.VK_R);
        menuBar.add(menu);

        // Change ends
        addToRepo = new JMenuItem("Add game to repository", KeyEvent.VK_D);
        addToRepo.setActionCommand("addToRepo");
        addToRepo.addActionListener(this);
        remakeRepo = new JMenuItem("Remake the repository", KeyEvent.VK_R);
        remakeRepo.addActionListener(this);
        remakeRepo.setActionCommand("remakeRepo");
        menu.add(addToRepo);
        menu.add(remakeRepo);
        saveToRepo = new JMenuItem("Save Game File", KeyEvent.VK_S);
        saveToRepo.addActionListener(this);
        saveToRepo.setActionCommand("saveToRepo");
        fileMenu.add(saveToRepo);
        saveToRepo.setEnabled(false);
        saveToRepoAs = new JMenuItem("Save Game File As", KeyEvent.VK_A);
        saveToRepoAs.addActionListener(this);
        saveToRepoAs.setActionCommand("saveToRepoAs");
        fileMenu.add(saveToRepoAs);
        saveToRepoAs.setEnabled(false);
        checkErrorList = new JMenuItem("Check XML Errors", KeyEvent.VK_C);
        checkErrorList.addActionListener(this);
        checkErrorList.setActionCommand("viewErrorList");
        checkErrorList.setEnabled(false);
        fileMenu.add(checkErrorList);

        //Create Character Select Window
        characterSelectWindow = new CharacterSelectWindow(window);
        characterSelectWindow.addWindowListener(new WindowListener(){
			public void windowActivated(final WindowEvent arg0) { }
			public void windowClosed(final WindowEvent e) { }
			public void windowClosing(final WindowEvent e) { }
			public void windowDeactivated(final WindowEvent e) {
				/*
				if(characterSelectWindow.getNewCharacterAsset() == null)
				{
					return;
				}
				else
				{
					List<Asset> currentAssets = lastSelectedScreen.getAssets();
					currentAssets.add(characterSelectWindow.getNewCharacterAsset());
					lastSelectedScreen.setAssets(currentAssets);
					displayScreen(lastSelectedScene, lastSelectedScreen);
				}*/
			}
			public void windowDeiconified(final WindowEvent e) { }
			public void windowIconified(final WindowEvent e) { }
			public void windowOpened(final WindowEvent e) { }
        });

        //Create Prop Select Window
        propSelectWindow = new PropSelectWindow(window);
        propSelectWindow.addWindowListener(new WindowListener(){
			public void windowActivated(final WindowEvent e) { }
			public void windowClosed(final WindowEvent e) { }
			public void windowClosing(final WindowEvent e) { }
			public void windowDeactivated(final WindowEvent e) {
				/*
				if(propSelectWindow.getNewImageAsset() == null)
				{
					return;
				}
				else
				{
					List<Asset> currentAssets = lastSelectedScreen.getAssets();
					currentAssets.add(propSelectWindow.getNewImageAsset());
					lastSelectedScreen.setAssets(currentAssets);
					displayScreen(lastSelectedScene, lastSelectedScreen);
				}*/
			}
			public void windowDeiconified(final WindowEvent e) { }
			public void windowIconified(final WindowEvent e) { }
			public void windowOpened(final WindowEvent e) { }
        });

        //Create Background Select Window
        backgroundSelectWindow = new BackgroundSelectWindow(window);
        backgroundSelectWindow.addWindowListener(new WindowListener() {
			public void windowActivated(final WindowEvent e) { }
			public void windowClosed(final WindowEvent e) { }
			public void windowClosing(final WindowEvent e) { }
			public void windowDeactivated(final WindowEvent e) {
		   if (backgroundSelectWindow.getNewBackgroundPath() == null) {
					return;
					}
				else {
					//lastSelectedScreen.setBackground(backgroundSelectWindow.getNewBackgroundPath());
					//scenePanel.loadBackground(lastSelectedScene.getBackground());
				}
			}
			public void windowDeiconified(final WindowEvent e) { }
			public void windowIconified(final WindowEvent e) { }
			public void windowOpened(final WindowEvent e) { }
        });

        //Create Sound Select Window
        soundSelectWindow = new SoundSelectWindow(window);
        soundSelectWindow.addWindowListener(new WindowListener() {
			public void windowActivated(final WindowEvent e) { }
			public void windowClosed(final WindowEvent e) { }
			public void windowClosing(final WindowEvent e) { }
			public void windowDeactivated(final WindowEvent e) {
			if (soundSelectWindow.getNewSoundPath() == null) {
				System.out.println("got here, but null");
				return;
				}
			else {
		    soundSelectPath = soundSelectWindow.getNewSoundPath();
			System.out.println(soundSelectPath);
			}
			}
			public void windowDeiconified(final WindowEvent e) { }
			public void windowIconified(final WindowEvent e) { }
			public void windowOpened(final WindowEvent e) { }
        });

        // create tree-structure for viewing Acts/Scenes/Screens/Challenges
        gameTree = new JTree();
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("No game file selected");
        DefaultTreeModel model = new DefaultTreeModel(rootNode);
        gameTree.setModel(model);
        gameTree.addTreeSelectionListener(new TreeSelectionListener() 
        {
            public void valueChanged(final TreeSelectionEvent e) 
            {
            	if(game == null || hasCriticalGameErrors) { return; } // don't try to display an empty game
            	DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) gameTree.getLastSelectedPathComponent();
            	AudioPlayer.stopAudio();
            	if (isQuestionNode(selectedNode))
            	{
            		Item item = (Item)selectedNode.getUserObject();
            		Challenge challenge = ((Screen)((DefaultMutableTreeNode)selectedNode.getParent()).getUserObject()).getChallenge().get(0); //Temporary addition of .get(0) to make it play nice with a list
            		Scene scene = (Scene)((DefaultMutableTreeNode)selectedNode.getParent().getParent()).getUserObject();
            		selectedLevel = gameLevel.CHALLENGE;
            		characterButton.setEnabled(false);
            		propButton.setEnabled(false);
                    backgroundAndHiddenButton.setText(toggleHiddenText);
                    backgroundAndHiddenButton.setActionCommand("toggleHiddenElements");
            		backgroundAndHiddenButton.setEnabled(true);
            		soundButton.setEnabled(false);
            		textButton.setEnabled(false);
                    buttonButton.setEnabled(false);
            		displayChallenge(scene, challenge, item);
            	}
            	//TODO 1. When going to a Question node in game the buttons do not refresh Assuming this method ^^^^^ is why.
            	//TODO 2. The only difference between the two below is a cast and variable type, and we have a unused isChallange method
            	//TODO that encompasses Intro, Question, Summary. Feels like we could tackle todo #1 and reduce the code
            	else if (isIntroNode(selectedNode))
            	{
            		Introduction intro = (Introduction)selectedNode.getUserObject();
            		Scene scene = (Scene)((DefaultMutableTreeNode)selectedNode.getParent().getParent()).getUserObject();
            		selectedLevel = gameLevel.CHALLENGE;
            		characterButton.setEnabled(false);
            		propButton.setEnabled(false);
                    backgroundAndHiddenButton.setText(toggleHiddenText);
                    backgroundAndHiddenButton.setActionCommand("toggleHiddenElements");
            		backgroundAndHiddenButton.setEnabled(true);
            		soundButton.setEnabled(false);
            		textButton.setEnabled(false);
                    buttonButton.setEnabled(false);
            		
            		//scenePanel.loadAssets(intro.getAssets(), true);
            		//scenePanel.loadBackground(scene.getBackground());
            	}
            	else if (isSummaryNode(selectedNode))
            	{
            		Summary summary = (Summary)selectedNode.getUserObject();
            		Scene scene = (Scene)((DefaultMutableTreeNode)selectedNode.getParent().getParent()).getUserObject();
            		selectedLevel = gameLevel.CHALLENGE;
            		characterButton.setEnabled(false);
            		propButton.setEnabled(false);
                    backgroundAndHiddenButton.setText(toggleHiddenText);
                    backgroundAndHiddenButton.setActionCommand("toggleHiddenElements");
            		backgroundAndHiddenButton.setEnabled(true);
            		soundButton.setEnabled(false);
            		textButton.setEnabled(false);
                    buttonButton.setEnabled(false);
            		
            		//scenePanel.loadAssets(summary.getAssets(), true);
            		//scenePanel.loadBackground(scene.getBackground());
            	}
            	else if (isScreenNode(selectedNode))
            	{
            		characterButton.setEnabled(true);
            		propButton.setEnabled(true);
                    backgroundAndHiddenButton.setText(toggleHiddenText);
                    backgroundAndHiddenButton.setActionCommand("toggleHiddenElements");
            		backgroundAndHiddenButton.setEnabled(true);
            		soundButton.setEnabled(true);
            		textButton.setEnabled(true);
                    buttonButton.setEnabled(true);
            		selectedLevel = gameLevel.SCREEN;
            		lastSelectedScene = (Scene)((DefaultMutableTreeNode)selectedNode.getParent()).getUserObject();
            		lastSelectedScreen = (Screen)selectedNode.getUserObject();
            		displayScreen(lastSelectedScene, lastSelectedScreen);
            	}
            	else if (isSceneNode(selectedNode))
            	{
          			Scene s = (Scene)selectedNode.getUserObject();
          			characterButton.setEnabled(false);
            		propButton.setEnabled(false);
                    backgroundAndHiddenButton.setText("Background");
                    backgroundAndHiddenButton.setActionCommand("backgroundToolbar");
            		backgroundAndHiddenButton.setEnabled(true);
            		soundButton.setEnabled(true);
            		textButton.setEnabled(false);
                    buttonButton.setEnabled(false);
          			selectedLevel = gameLevel.SCENE;
          			lastSelectedScene = s;
          			scenePanel.clear();
          			System.out.println("calling clear scene node\n");
          			//scenePanel.loadBackground(s.getBackground());
          			scenePanel.backgroundMusicPreview(lastSelectedScene.getMusic() != null);
            	}
            	else if(isGameNode(selectedNode))
            	{
            		List<Character> chars = game.getCharacter();
            		
            		scenePanel.clear();
            		System.out.println("calling clear rootnode\n");
            		characterButton.setEnabled(false);
            		propButton.setEnabled(false);
                    backgroundAndHiddenButton.setText("Background");
                    backgroundAndHiddenButton.setActionCommand("backgroundToolbar");
            		backgroundAndHiddenButton.setEnabled(false);
            		soundButton.setEnabled(false);
            		textButton.setEnabled(false);
                    buttonButton.setEnabled(false);
            		selectedLevel = gameLevel.GAME;
            		
            		int xSpacing = 180;
            		int charCounter = 0;
            		int defaultHeight = 300;
            		int defaultWidth = 180;
            		for (final Character ca : chars){
            			
            			//create a copy as not to modify the original coordinates
            			CharacterAsset c = new CharacterAsset();

            			//set image path for character
            			//c.setDisplayImage(ca.getProfile().getProfilePhoto());
            			//make all images the standard character width and height
            			c.setWidth(defaultWidth);
            			c.setHeight(defaultHeight);
            			//set image location
            			c.setX(xSpacing*charCounter);
            			c.setY(0);
        				scenePanel.loadAsset(c, charBaseDir, true);
        				//button for viewing profile
        				JButton charButton = new JButton("View Profile");
        				charButton.setBounds(xSpacing*charCounter+defaultWidth/8, defaultHeight, defaultWidth*3/4, 30);
        				charButton.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        				charButton.addActionListener(new ActionListener()
        				{
							public void actionPerformed(final ActionEvent e) 
							{
								CharacterProfileWindow cpw = new CharacterProfileWindow(window, ca);
								cpw.setVisible(true);
							}
        				});
        				scenePanel.add(charButton);
        				charCounter++;
            		}
            	}
            	else if(isActNode(selectedNode)) { //Act node
            		characterButton.setEnabled(false);
            		propButton.setEnabled(false);
                    backgroundAndHiddenButton.setText("Background");
                    backgroundAndHiddenButton.setActionCommand("backgroundToolbar");
            		backgroundAndHiddenButton.setEnabled(false);
            		soundButton.setEnabled(false);
            		textButton.setEnabled(false);
                    buttonButton.setEnabled(false);
            		selectedLevel = gameLevel.ACT;
            		scenePanel.clear();
            		scenePanel.displayAct((Act)selectedNode.getUserObject());
            	}
            	else { //Just in case... Possibly error out
            		characterButton.setEnabled(false);
            		propButton.setEnabled(false);
                    backgroundAndHiddenButton.setText("Background");
                    backgroundAndHiddenButton.setActionCommand("backgroundToolbar");
            		backgroundAndHiddenButton.setEnabled(false);
            		soundButton.setEnabled(false);
            		textButton.setEnabled(false);
                    buttonButton.setEnabled(false);
                    //gameLevel is unknown?
            		selectedLevel = gameLevel.ACT;
            	}
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(gameTree);
        
        // create tabbed layout
        
        JPanel generateTab = new JPanel(new BorderLayout());
        JPanel previewTab = new JPanel(new BorderLayout());
        JPanel gamePlayEngine = new JPanel(new BorderLayout());
        tabbedPane.addTab("Generate", null, generateTab);
        tabbedPane.addTab("Preview", null, previewTab);
        tabbedPane.addTab("Game Play Engine", null, gamePlayEngine);
        
        //Adding game play engine menu in main panel
        JMenu games = new JMenu("Games");
	    JMenuItem openGame = new JMenuItem("Open Game");
	    JMenuItem quit = new JMenuItem("Quit");
	    
	    games.add(openGame);
	    games.addSeparator();
	    games.add(quit);
	    
	    menuBar.add(games);
	    
	    final GameView gameView = new GameView();
				
		final JPanel jPanel = new JPanel(new BorderLayout());
		//jPanel.setLocationRelativeTo(null);
		//jPanel.pack();
		jPanel.setVisible(true);
		jPanel.setSize(600, 600);
		jPanel.setLayout(new BorderLayout());
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		jPanel.setLocation(dim.width / 2 - jPanel.getSize().width / 2,
				dim.height / 2 - jPanel.getSize().height / 2);
        
		 openGame.addActionListener(new ActionListener() {
		        public void actionPerformed(final ActionEvent arg0) {
		        	JFileChooser myFileChooser = new JFileChooser();
		        	int retval = myFileChooser.showOpenDialog(null);
			        if (retval == JFileChooser.APPROVE_OPTION) {
			            File myFile = myFileChooser.getSelectedFile();
			            System.out.println("Opening File: " + myFile.toString());
			            try
						{
			            	gameView.resetView();
			            	GameController gameController = new GameController(new GameModel(), gameView);
			            	final GameModelBoundary gameModelBoundary = gameController.getModelBoundary();
			            	gameModelBoundary.setView(gameView);
			            	gameModelBoundary.gmbEnd();
			            	
			        		gameModelBoundary.startGame(myFile.toString(), jPanel);
						} catch (Exception e)
						{
							System.out.println("Exception in GameViewFrame.java, startGame: " + e.toString());
						}
			        }
		        }
		    });
		    quit.addActionListener(new ActionListener() {
		        public void actionPerformed(final ActionEvent arg0) {
		        	System.exit(0);
		        }
		    });
		    gamePlayEngine.add(jPanel);
		    
        JPanel browsePanel = new JPanel(new BorderLayout()); // browse/click on Acts/Scenes
        browsePanel.add(scrollPane);
        
        JPanel toolbarPanel = new JPanel(new GridLayout(3,2,0,0));
        characterButton = new JButton("Character");
        characterButton.addActionListener(this);
        characterButton.setEnabled(false);
        characterButton.setActionCommand("charactersToolbar");
        toolbarPanel.add(characterButton);
        propButton = new JButton("Prop");
        propButton.addActionListener(this);
        propButton.setEnabled(false);
        propButton.setActionCommand("propToolbar");
        toolbarPanel.add(propButton);
        //TODO Stood-up though unused Text Button
        textButton = new JButton("Text");
        textButton.setEnabled(false);
        textButton.setToolTipText("Not yet implemented");
        toolbarPanel.add(textButton);
        //TODO Stood-up though unused Button Button
        buttonButton = new JButton("Button");
        buttonButton.setEnabled(false);
        buttonButton.setToolTipText("Not yet implemented");
        toolbarPanel.add(buttonButton);
        //TODO "How much do any of these other buttons do?" -Ryan 2/27/14
        soundButton = new JButton("Sound");
        soundButton.addActionListener(this);
        soundButton.setEnabled(false);
        soundButton.setActionCommand("soundToolbar");
        toolbarPanel.add(soundButton);
        backgroundAndHiddenButton = new JButton("Background");
        backgroundAndHiddenButton.addActionListener(this);
        backgroundAndHiddenButton.setEnabled(false);
        backgroundAndHiddenButton.setActionCommand("backgroundToolbar");
        toolbarPanel.add(backgroundAndHiddenButton);
        browsePanel.add(toolbarPanel, BorderLayout.SOUTH);
        toolbarPanel.setPreferredSize(new Dimension(0, 80));
        
        scenePanel = new ScenePanel(this); // view/edit the Scene selected in the browse panel
        scenePanel.setLayout(null);
        JSplitPane splitPreviewPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, browsePanel, scenePanel);
        splitPreviewPane.setEnabled(false);
        previewTab.add(splitPreviewPane);
        
        
        
        //Jcheckbox
        JCheckBox tickBox = new JCheckBox(label1);
        tickBox.setHorizontalTextPosition(SwingConstants.LEFT);
        ActionListener actionListener = new ActionListener() {

        	@Override
        public void actionPerformed(final ActionEvent actionEvent) {

            AbstractButton absB = (AbstractButton) actionEvent.getSource();

            boolean slct = absB.getModel().isSelected();

             selectedValue = (slct ? 0 : 1);
            
            System.out.println(selectedValue);


        }

          };
        	  tickBox.addActionListener(actionListener);
        	 // tickBox.setMnemonic(KeyEvent.VK_S);


    //gradeButtons    
    ButtonGroup gradeGroup = new ButtonGroup();
 		JRadioButton primaryButton = new JRadioButton("Primary School");
 		primaryButton.setActionCommand("primary");
 		primaryButton.addActionListener(this);
 		JRadioButton secondaryButton = new JRadioButton("Secondary School");
 		secondaryButton.setActionCommand("secondary");
 		secondaryButton.addActionListener(this);
 		JRadioButton highButton = new JRadioButton("High School");
 		highButton.setActionCommand("high");
 		highButton.addActionListener(this);
 		JRadioButton collegeButton= new JRadioButton("College");
 		collegeButton.setActionCommand("college");
 		collegeButton.addActionListener(this);
 		JRadioButton jobTrainingButton= new JRadioButton("Job Training");
 		jobTrainingButton.setActionCommand("jobTraining");
 		jobTrainingButton.addActionListener(this);
 		JRadioButton noGradePreference = new JRadioButton("No Preference",true);
 		noGradePreference.setActionCommand(none+" grade");
 		noGradePreference.addActionListener(this);
 		gradeGroup.add(primaryButton);
 		gradeGroup.add(secondaryButton);
 		gradeGroup.add(highButton);
 		gradeGroup.add(collegeButton);
 		gradeGroup.add(jobTrainingButton);
 		gradeGroup.add(noGradePreference);
 		
 		JPanel gradePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel gradeLabel = new JLabel("Indended grade level:");
    	gradePanel.add(gradeLabel);
 		gradePanel.add(primaryButton);
 		gradePanel.add(secondaryButton);
 		gradePanel.add(highButton);
 		gradePanel.add(collegeButton);
 		gradePanel.add(jobTrainingButton);
 		gradePanel.add(noGradePreference);
 	mainPannel.add(gradePanel,nextOpenRow++);
    //GenderButtons    
        ButtonGroup genderGroup = new ButtonGroup();
        	JRadioButton maleButton = new JRadioButton("Male");
            maleButton.setActionCommand("Male");
            maleButton.addActionListener(this);
        	JRadioButton femaleButton = new JRadioButton("Female");
            femaleButton.setActionCommand("Female");
            femaleButton.addActionListener(this);
        	JRadioButton noGenderPreference = new JRadioButton("No Preference",true);
            noGenderPreference.setActionCommand(none+" gender");
            noGenderPreference.addActionListener(this);
        	genderGroup.add(maleButton);
        	genderGroup.add(femaleButton);
        	genderGroup.add(noGenderPreference);
        	JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        	JLabel genderLabel = new JLabel("Player Gender:");
        	genderPanel.add(genderLabel);
        	genderPanel.add(maleButton);
        	genderPanel.add(femaleButton);
        	genderPanel.add(noGenderPreference);
        mainPannel.add(genderPanel,nextOpenRow++);
        //ageButtons    
        ButtonGroup ageGroup = new ButtonGroup();
        	JRadioButton youngButton = new JRadioButton("Young");
            youngButton.setActionCommand("Young");
            youngButton.addActionListener(this);
        	JRadioButton oldButton = new JRadioButton("Old");
        	oldButton.setActionCommand("Old");
        	oldButton.addActionListener(this);
        	JRadioButton noAgePreference = new JRadioButton("No Preference",true);
            noAgePreference.setActionCommand(none+" age");
            noAgePreference.addActionListener(this);
        	ageGroup.add(youngButton);
        	ageGroup.add(oldButton);
        	ageGroup.add(noAgePreference);
        	JPanel agePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        	JLabel ageLabel = new JLabel("Character age:");
        	agePanel.add(ageLabel);
        	agePanel.add(youngButton);
        	agePanel.add(oldButton);
        	agePanel.add(noAgePreference);
        mainPannel.add(agePanel,nextOpenRow++);
        
        //dressButtons    
        ButtonGroup dressGroup = new ButtonGroup();
        	JRadioButton casualButton = new JRadioButton("Casual Dress");
        	casualButton.setActionCommand("Casual");
        	casualButton.addActionListener(this);
        	JRadioButton fancyButton = new JRadioButton("Fancy Dress");
        	fancyButton.setActionCommand("Fancy");
        	fancyButton.addActionListener(this);
        	JRadioButton noDressPreference = new JRadioButton("No Preference",true);
        	noDressPreference.setActionCommand(none+" dress");
        	noDressPreference.addActionListener(this);
        	dressGroup.add(casualButton);
        	dressGroup.add(fancyButton );
        	dressGroup.add(noDressPreference);
        	JPanel dressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        	JLabel dressLabel = new JLabel("Character dress:");
        	dressPanel.add(dressLabel);
        	dressPanel.add(casualButton);
        	dressPanel.add(fancyButton );
        	dressPanel.add(noDressPreference);
        mainPannel.add(dressPanel,nextOpenRow++);
        //Theme Buttons
     	ButtonGroup themeGroup = new ButtonGroup();
     		JRadioButton goobleButton = new JRadioButton("Gooble");
     		goobleButton.setActionCommand("Gooble");
     		goobleButton.addActionListener(this);
     		JRadioButton dreamButton = new JRadioButton("Dream");
     		dreamButton.setActionCommand("Dream");
     		dreamButton.addActionListener(this);
     		JRadioButton virtualTourButton = new JRadioButton("Virtual Tour");
     		virtualTourButton.setActionCommand("VirtualTour");
     		virtualTourButton.addActionListener(this);
     		JRadioButton workplaceButton = new JRadioButton("Workplace");
     		workplaceButton.setActionCommand("Workplace");
     		workplaceButton.addActionListener(this);
     		JRadioButton otherButton = new JRadioButton("Other");
     		otherButton.setActionCommand("Other");
     		otherButton.addActionListener(this);
     		JRadioButton noThemePreference = new JRadioButton("No Preference",true);
     		noThemePreference.setActionCommand(none+" theme");
     		noThemePreference.addActionListener(this);
     		themeGroup.add(goobleButton);
     		themeGroup.add(dreamButton);
     		themeGroup.add(virtualTourButton);
     		themeGroup.add(workplaceButton);
     		themeGroup.add(otherButton);
     		themeGroup.add(noThemePreference);
     		JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel themeLabel = new JLabel("Game Theme:");
        	themePanel.add(themeLabel);
     		themePanel.add(goobleButton);
     		themePanel.add(dreamButton);
     		themePanel.add(virtualTourButton);
     		themePanel.add(workplaceButton);
     		themePanel.add(otherButton);
     		themePanel.add(noThemePreference);
     	mainPannel.add(themePanel,nextOpenRow++);
      //SubjectButtons
        ButtonGroup subjectGroup = new ButtonGroup();
    		JRadioButton englishButton = new JRadioButton("English");
    		englishButton.setActionCommand("English");
    		englishButton.addActionListener(this);
    		JRadioButton mathButton = new JRadioButton("Math");
    		mathButton.setActionCommand("Math");
    		mathButton.addActionListener(this);
    		JRadioButton scienceButton = new JRadioButton("Science");
    		scienceButton.setActionCommand("Science");
    		scienceButton.addActionListener(this);
    		JRadioButton socialstudiesButton = new JRadioButton("Social Studies");
    		socialstudiesButton.setActionCommand("Social Studies");
    		socialstudiesButton.addActionListener(this);
    		JRadioButton literatureButton = new JRadioButton("Literature");
    		literatureButton.setActionCommand("Literature");
    		literatureButton.addActionListener(this);
    		JRadioButton professionalButton = new JRadioButton("Professional");
    		professionalButton.setActionCommand("Professional");
    		professionalButton.addActionListener(this);
    		JRadioButton noSubjectPreference = new JRadioButton("No Preference",true);
    		noSubjectPreference.setActionCommand(none+" subject");
    		noSubjectPreference.addActionListener(this);
    		subjectGroup.add(englishButton);
    		subjectGroup.add(mathButton);
    		subjectGroup.add(scienceButton);
    		subjectGroup.add(socialstudiesButton);
    		subjectGroup.add(literatureButton);
    		subjectGroup.add(professionalButton);
    		subjectGroup.add(noSubjectPreference);
    		JPanel subjectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel subjectLabel = new JLabel("Game subject:");
           	subjectPanel.add(subjectLabel);
    		subjectPanel.add(englishButton);
    		subjectPanel.add(mathButton);
    		subjectPanel.add(scienceButton);
    		subjectPanel.add(socialstudiesButton);
    		subjectPanel.add(literatureButton);
    		subjectPanel.add(professionalButton);
    		subjectPanel.add(noSubjectPreference);
    	mainPannel.add(subjectPanel,nextOpenRow++);
    //Setting Buttons	
     	ButtonGroup settingGroup = new ButtonGroup();
     		JRadioButton professionalSettingButton = new JRadioButton("Professional");
     		professionalSettingButton.setActionCommand("ProfessionalSetting");
     		professionalSettingButton.addActionListener(this);
     		JRadioButton casualSettingButton = new JRadioButton("Casual");
     		casualSettingButton.setActionCommand("CasualSetting");
     		casualSettingButton.addActionListener(this);
     		JRadioButton naturalSettingButton = new JRadioButton("Natural");
     		naturalSettingButton.setActionCommand("NaturalSetting");
     		naturalSettingButton.addActionListener(this);
     		JRadioButton educationalSettingButton= new JRadioButton("Educational");
     		educationalSettingButton.setActionCommand("EducationalSetting");
     		educationalSettingButton.addActionListener(this);
     		JRadioButton nonterrestrialSettingButton= new JRadioButton("Non-terrestrial");
     		nonterrestrialSettingButton.setActionCommand("Non-terrestrialSetting");
     		nonterrestrialSettingButton.addActionListener(this);
     		JRadioButton noSettingPreference = new JRadioButton("No Preference",true);
     		noSettingPreference.setActionCommand(none+" setting");
     		noSettingPreference.addActionListener(this);
     		settingGroup.add(professionalSettingButton);
     		settingGroup.add(casualSettingButton);
     		settingGroup.add(naturalSettingButton);
     		settingGroup.add(educationalSettingButton);
     		settingGroup.add(nonterrestrialSettingButton);
 			settingGroup.add(noSettingPreference);
 			JPanel settingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
 			JLabel settingLabel = new JLabel("Game Setting:");
 			settingPanel.add(settingLabel);
 			settingPanel.add(professionalSettingButton);
 			settingPanel.add(casualSettingButton);
 			settingPanel.add(naturalSettingButton);
 			settingPanel.add(educationalSettingButton);
 			settingPanel.add(nonterrestrialSettingButton);
 			settingPanel.add(noSettingPreference);
 		mainPannel.add(settingPanel,nextOpenRow++);
        //difficulty Buttons
     	ButtonGroup difficultyGroup = new ButtonGroup();
     		JRadioButton easyButton = new JRadioButton("Easy");
     		easyButton.setActionCommand("Easy");
     		easyButton.addActionListener(this);
     		JRadioButton mediumButton = new JRadioButton("Medium");
     		mediumButton.setActionCommand("Medium");
     		mediumButton.addActionListener(this);
     		JRadioButton hardButton = new JRadioButton("Hard");
     		hardButton.setActionCommand("Hard");
     		hardButton.addActionListener(this);
     		JRadioButton noDifficultyPreference = new JRadioButton("No Preference",true);
     		noDifficultyPreference.setActionCommand(none+" difficulty");
     		noDifficultyPreference.addActionListener(this);
     		difficultyGroup.add(easyButton);
     		difficultyGroup.add(mediumButton);
     		difficultyGroup.add(hardButton);
     		difficultyGroup.add(noDifficultyPreference);
     		JPanel difficultyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel difficultyLabel = new JLabel("Challenge difficulty:");
        	difficultyPanel.add(difficultyLabel);
     		difficultyPanel.add(easyButton);
     		difficultyPanel.add(mediumButton);
     		difficultyPanel.add(hardButton);
     		difficultyPanel.add(noDifficultyPreference);
     	mainPannel.add(difficultyPanel,nextOpenRow++);
     	JPanel previewCheckPanel = new JPanel(new GridLayout(1,1));
     	previewCheckPanel.add(tickBox);
        mainPannel.add(previewCheckPanel, nextOpenRow++);
        //ADD MORE BUTTON SETS HERE IN FUTURE IF DESIRED
     	
     	
     	//Submit Button on bottom
     	JPanel submitPanel = new JPanel(new GridLayout(1,3));
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        submitButton.setActionCommand("Submit");
        submitPanel.add(submitButton);
        mainPannel.add(submitPanel, nextOpenRow++);
        generateTab.add(mainPannel);
        window.add(tabbedPane,BorderLayout.CENTER);
        window.setJMenuBar(menuBar);
        window.setVisible(true); // this needs to happen last to avoid blank window on start-up
	}
	
	private boolean isGameNode(final DefaultMutableTreeNode node)
	{
		return node != null && node.isRoot();
	}
	private boolean isActNode(final DefaultMutableTreeNode node)
	{
		return node != null && node.getUserObject() != null && node.getUserObject() instanceof Act;
	}
	private boolean isSceneNode(final DefaultMutableTreeNode node)
	{
		return node != null && node.getUserObject() != null && node.getUserObject() instanceof Scene;
	}
	private boolean isScreenNode(final DefaultMutableTreeNode node)
	{
		return node != null && node.getUserObject() != null && node.getUserObject() instanceof Screen;
	}
	private boolean isQuestionNode(final DefaultMutableTreeNode node)
	{
		return node != null && node.getUserObject() != null && node.getUserObject() instanceof Item;
	}
	private boolean isSummaryNode(final DefaultMutableTreeNode node)
	{
		return node != null && node.getUserObject() != null && node.getUserObject() instanceof Summary;
	}
	private boolean isIntroNode(final DefaultMutableTreeNode node)
	{
		return node != null && node.getUserObject() != null && node.getUserObject() instanceof Introduction;
	}
	
	private ArrayList<String> getScreenCharacterNames(final ArrayList<CharacterAsset> chars)
	{
		ArrayList<String> charStrings = new ArrayList<String>();
		for(CharacterAsset charAsset : chars)
		{
			String filePath = charAsset.getDisplayImage();
			String charName = filePath.substring(0, filePath.indexOf('\\'));
			
			if(!charStrings.contains(charName))
			{
				charStrings.add(charName);
			}
		}
		
		return charStrings;
	}
	
	private ArrayList<String> getGameGenericCharacterNames()
	{
		ArrayList<String> charStrings = new ArrayList<String>();
		for(Character ch : game.getCharacter())
		{
			//String filePath = ch.getProfile().getProfilePhoto();
			//String charName = filePath.substring(0, filePath.indexOf('\\'));
			String charName = ch.getName();
			
			if(!charStrings.contains(charName))
			{
				charStrings.add(charName);
			}
		}
		
		return charStrings;
	}
	
	//sets all the component Inputs to 0
	private void initializeComponentInputs()
	{
		for(int x=0; x<componentInputs.length;x++)
		{
			componentInputs[x]= initializeMatrix(componentInputs[x], 1);
		}
		
	}
	// read in and return an XML game file given the path to the file
	private Game readGameFile(final File gameFile)
	{
		Game game1 = null;
		
		try {
			game1 = (Game)JAXBContext.newInstance(Game.class).createUnmarshaller().unmarshal(gameFile);
		}
		catch (JAXBException e) {
			//TODO this was printing errors before, do we want it in?
			e.printStackTrace();
			System.out.println(e.toString());
			System.out.println("Unable to open " + gameFile);
		}
		
		return game1;
	}
	private Game saveGameFile(final File gameFile)
    {
            try {
            		JAXBContext jaxbContext = JAXBContext.newInstance(Game.class);
            		Marshaller marshaller = jaxbContext.createMarshaller();
            		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            		marshaller.marshal(game, gameFile);
            }
            catch (JAXBException e) {
                    e.printStackTrace();
                    System.out.println("Unable to open " + gameFile);
            }
            return game;
    }
	
	private void saveGameFileAs()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Enter a name for the file");
		chooser.setFileFilter(new FileNameExtensionFilter("Game XML", "XML"));
		chooser.setAcceptAllFileFilterUsed(false);
		int retval = chooser.showSaveDialog(null);
		
		if(retval == JFileChooser.APPROVE_OPTION)
		{
            File file = chooser.getSelectedFile();
            // check for .xml (of any case variation) at the end ($) of the filename
            if(!file.getName().matches(".*[.][Xx][Mm][Ll]$"))
            {
            	System.out.println("didn't match");
            	file = new File(file.getPath() + ".XML");
            }
            game = saveGameFile(file);
            Currentfile = file;
    		System.out.println("saved as " + file.getPath());
         }
		else {
            System.out.println("Save command cancelled by user.");
        }
	}

	// C40 handle loading an XML game into the preview window
	private void loadGame()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Select a game XML file");
		chooser.setFileFilter(new FileNameExtensionFilter("Game XML", "xml","xmL","xMl","xML","Xml","XmL","XMl","XML"));
		chooser.setAcceptAllFileFilterUsed(false);
		int retval = chooser.showOpenDialog(null);
		
		if(retval == JFileChooser.APPROVE_OPTION)
		{
            File file = chooser.getSelectedFile();
            game = readGameFile(file);
            hasCriticalGameErrors = false;
            loadAndDisplayErrors(game);
            if(!hasCriticalGameErrors) 
            { 
            	displayGame(game, file.getName()); 
            }
            else {
        		((DefaultMutableTreeNode) gameTree.getModel().getRoot()).removeAllChildren();
                DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
                rootNode.setUserObject("No game file selected");
                ((DefaultTreeModel) gameTree.getModel()).setRoot(rootNode);
            }
            checkErrorList.setEnabled(true);
            saveToRepo.setEnabled(true);
            saveToRepoAs.setEnabled(true);
            Currentfile = file;
    		System.out.println("calling clear loadgame\n");
        } 
		else {
            System.out.println("Open command cancelled by user.");
        }
	}
	
	// return true if there are critical errors
	private void loadAndDisplayErrors(final Game game)
	{
        GameErrorList errorList = GameErrorChecker.checkErrors(game, scenePanel.getWidth(), scenePanel.getHeight());
        scenePanel.clear();
        scenePanel.loadErrors(errorList);
        hasCriticalGameErrors = errorList.hasCriticalErrors();
        
        //Debug
        for (PreviewError e : errorList) {
        	System.out.println(e);
        }
	}

	// divide game into Acts and Scenes translating to java swing TreeNodes
	// file name is required because it will be the name of the root node
	private void displayGame(final Game game1, final String name)
	{
		((DefaultMutableTreeNode) gameTree.getModel().getRoot()).removeAllChildren();
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(name);
        rootNode.setUserObject(game1);
        ((DefaultTreeModel) gameTree.getModel()).setRoot(rootNode);

		List<Act> acts = game1.getAct();
		for (int i = 0; i < acts.size(); i++) {
			DefaultMutableTreeNode actNode = new DefaultMutableTreeNode("Act " + (i + 1));
			actNode.setUserObject(acts.get(i));

			List<Scene> scenes = acts.get(i).getScene();
			for (int j = 0; j < scenes.size(); j++) {
				DefaultMutableTreeNode sceneNode = new DefaultMutableTreeNode("Scene " + (j + 1));
				sceneNode.setUserObject(scenes.get(j));
				actNode.add(sceneNode);
				List<Screen> screens = scenes.get(j).getScreen();
				for (int k = 0; k < screens.size(); k++) {
					DefaultMutableTreeNode screenNode = new DefaultMutableTreeNode("Screen " + (k + 1));
					screenNode.setUserObject(screens.get(k));
					

					if (screens.get(k).getChallenge() != null) {
						QuizChallenge challenge = (QuizChallenge)(screens.get(k).getChallenge());


						Introduction intro = challenge.getIntroduction();
						if (intro != null) {
							DefaultMutableTreeNode introNode = new DefaultMutableTreeNode("Introduction");
							introNode.setUserObject(challenge.getIntroduction());
							screenNode.add(introNode);
						}
						
						//
						Item item = challenge.getItem();
						if (item != null) {
							//for(int m = 0; m < items.size(); m++)
							//{
								DefaultMutableTreeNode questionNode = new DefaultMutableTreeNode("Challenge Question ");
								//questionNode.setUserObject(items.get(m));
								questionNode.setUserObject(item);
								screenNode.add(questionNode);
							//}
						}

						//List<Summary> summaries = challenge.getSummary();
						Summary summary = challenge.getSummary();
						if (summary != null) {
							//for(int m = 0; m < summaries.size(); m++)
							//{
								DefaultMutableTreeNode summaryNode = new DefaultMutableTreeNode("Summary ");
								summaryNode.setUserObject(summary);
								screenNode.add(summaryNode);
							//}
						}
					}

					sceneNode.add(screenNode);
				}
			}

			rootNode.add(actNode);
		}
        gameTree.expandRow(0);
	}

	//paint the scene in all of its glory
	private void displayScreen(final Scene scene, final Screen screen) 
	{
		//List<Asset> assets = screen.getAssets();
		List<Asset> assets = null; //Temporary to get this working
		if (assets != null){
			scenePanel.loadAssets(assets, false);
		} else {
			System.out.println("assets null");
		}

		//scenePanel.loadBackground(scene.getBackground());
	}

	//paint the Challenge Multiple Choice question
	private void displayChallenge(final Scene scene, final Challenge challenge, final Item item)
	{
		System.out.println("in displayChallenge()");

		if (challenge instanceof QuizChallenge) {
			Layout layout = null;
			if (((QuizChallenge) challenge).getLayout() == null) {
				return;
			}

			switch (((QuizChallenge) challenge).getLayout().getLayoutName()) {
				//case MULTIPLE_CHOICE_LAYOUT:
				case "Multiple Choice Layout": //Temporary until the above constant is defined again
				if (item instanceof MultipleChoiceItem)	{
					//layout = new Layout((MultipleChoiceItem)item);
					layout = new Layout();
					layout.setLayoutName("MultipleChoiceItem");
				}
				break;
			default:
				break;
			}

			//scenePanel.loadAssets(layout.getAssets(), true);
			//scenePanel.loadBackground(scene.getBackground());
		}
	}

	// sets all the values of the matrix to the given value. 
	private Matrix initializeMatrix(final Matrix inputMatrix, final double initValue)
	{
		double[][] inputMatrixArray = inputMatrix.getArray();
		for (int y = 0; y < inputMatrixArray.length; y++) {
			for (int z = 0; z < inputMatrixArray[y].length; z++) {
				inputMatrixArray[y][z] = initValue;
			}
		}
     return inputMatrix;
	}
	//same as above, only with an array instead of a matrix 
	private double[][] initializeArray(final double[][] inputMatrixArray, final double initValue)
	{
		for (int y = 0; y < inputMatrixArray.length; y++) {
			for (int z = 0; z < inputMatrixArray[y].length; z++) {
				inputMatrixArray[y][z] = initValue;
			}
		}
     return inputMatrixArray;		
	}
	//prints the given matrix
	private void printMatrix(final Matrix  inputMatrix)
	{
		double[][] inputArray = inputMatrix.getArray();
		for (int x = 0; x < inputArray.length; x++)	{
			for (int y = 0; y <  inputArray[x].length; y++) {
				System.out.printf("%.2f ", inputArray[x][y]);
			}
			System.out.println("");
		}
	}
	public final Matrix[] getWizardInputs() {
		while (!submitClicked) {
			//WAIT!!!! waits for the user to hit submit once called
			// Sleep to avoid running at full CPU usage
			try { Thread.sleep(200); } 
			catch (Exception e) { }
		}
		return componentInputs;
	}
	public final void previewGame(final File filename){
		System.out.println(selectedValue);
    	  if (selectedValue == 0) {
  		  tabbedPane.setSelectedIndex(1);

  		  //We are literally guessing how long it will take to write to disk.
  		  //TODO: sync filesystem write
  		  //note: 2 seconds was a sufficient amount of time for my computer
  		  try {Thread.sleep(3000);}catch(Exception e){}
          game = readGameFile(filename);
          displayGame(game, filename.getName());

  	  }
	}
	// a way to weight the options for each component against each other (ie age v gender) on which is more important
	// add a third input to this method called weight and replace the optionTotal in the assignment statement with it. 
	private Matrix optionMatrix(final int optionNumber, final int optionTotal)
	{
		double[][] outputArray = new double[optionTotal][optionTotal];
		outputArray = initializeArray(outputArray,1);
		for (int x = 0; x < optionTotal; x++) {
			if (x != optionNumber - 1) {
				outputArray[optionNumber-1][x]=optionTotal; //replace optionTotal here with weight
				outputArray[x][optionNumber-1]=(1.0 / optionTotal); //replace optionTotal here with weight
			}
		}
		return new Matrix(outputArray);
	}
	//called once after the submit button was clicked and a valid location is given.
	private void distributeInputs() {
		//start with character component
		int socialRating = 0;
		int professionalRating = 0;
		int educationalRating = 0;
		System.out.println("distributing Inputs");
		switch(gameGradeLevel){
		//There is probably a better way to do this, with lists or whatnot but this was the quick
		//implementation that I(Kaleb) decided to go with
		case "primary":
			componentInputs[4].setMatrix(6,9,6,9,optionMatrix(1,4));
			educationalRating++;
			break;
		case "secondary":
			componentInputs[4].setMatrix(6,9,6,9,optionMatrix(2,4));
			educationalRating++;
			break;
		case "high":
			componentInputs[4].setMatrix(6,9,6,9,optionMatrix(3,4));
			educationalRating++;
			break;
		case "college":
			componentInputs[4].setMatrix(6,9,6,9,optionMatrix(4,4));
			educationalRating++;
			break;
		case "jobTraining":
			professionalRating++;
			break;
		case "no grade":
			socialRating++;
		break;
		default:
			break;
		}
		switch(playerAge){
		case "Young":
			componentInputs[0].setMatrix(0,1,0,1,optionMatrix(1,2));
			break;
		case "Old":
			componentInputs[0].setMatrix(0,1,0,1,optionMatrix(2,2));
			break;
		case "none":
			break;
		default:
			break;
		}
		switch(playerGender){
		case "Male":
			componentInputs[0].setMatrix(2,3,2,3,optionMatrix(1,2));
			break;
		case "Female":
			componentInputs[0].setMatrix(2,3,2,3,optionMatrix(2,2));
			break;
		case "none":
			break;
		default:
			break;
		}
		switch(playerDress){
		case "Casual":
			componentInputs[0].setMatrix(4,5,4,5,optionMatrix(1,2));
			//			socialRating++;
			break;
		case "Fancy":
			componentInputs[0].setMatrix(4,5,4,5,optionMatrix(2,2));
			break;
		case "none":
			break;
		default:
			break;
		}
		switch(gameTheme){
		case "Gooble":
			componentInputs[5].setMatrix(0,4,0,4,optionMatrix(1,5));
			educationalRating++;
			break;
		case "Dream":
			componentInputs[5].setMatrix(0,4,0,4,optionMatrix(2,5));
			socialRating++;
			break;
		case "VirtualTour":
			componentInputs[5].setMatrix(0,4,0,4,optionMatrix(3,5));
			professionalRating++;
			break;
		case "Workplace":
			componentInputs[5].setMatrix(0,4,0,4,optionMatrix(4,5));
			socialRating++;
			break;
		case "Other":
			componentInputs[5].setMatrix(0,4,0,4,optionMatrix(5,5));
			break;
		case "none":
			break;
		default:
			System.out.println("Unanticipated Input for gameTheme " + gameTheme);
			break;
		}
		switch(gameSubject)
		{
		//Subject
		case "English":
			componentInputs[4].setMatrix(0,5,0,5,optionMatrix(3,6));
			educationalRating++;
			break;
		case "Math":
			componentInputs[4].setMatrix(0,5,0,5,optionMatrix(1,6));
			educationalRating++;
			break;
		case "Science":
			componentInputs[4].setMatrix(0,5,0,5,optionMatrix(5,6));
			educationalRating++;
			break;
		case "Social Studies":
			componentInputs[4].setMatrix(0,5,0,5,optionMatrix(4,6));
			educationalRating++;
			break;
		case "Literature":
			componentInputs[4].setMatrix(0,5,0,5,optionMatrix(2,6));
			educationalRating++;
			break;
		case "Professional":
			componentInputs[4].setMatrix(0,5,0,5,optionMatrix(6,6));
			professionalRating++;
			break;
		case "none":
			break;
		default:
			System.out.println("Unanticipated Input for gameSubject " + gameSubject);
			break;
		}
		switch(gameSetting){
		//Setting
		case "Professional":
			professionalRating+=2;
			componentInputs[3].setMatrix(3,7,3,7,optionMatrix(1,5));
			break;
		case "Casual":
			socialRating+=2;
			componentInputs[3].setMatrix(3,7,3,7,optionMatrix(2,5));
			break;
		case "Natural":
			componentInputs[3].setMatrix(3,7,3,7,optionMatrix(3,5));
			break;
		case "Educational":
			componentInputs[3].setMatrix(3,7,3,7,optionMatrix(4,5));
			educationalRating+=2;
			break;
		case "Non-terrestrial":
			componentInputs[3].setMatrix(3,7,3,7,optionMatrix(5,5));
			break;
		case "none":
			break;
		default:
			System.out.println("Unanticipated Input for gameSetting " + gameSetting);
			break;
		}
		switch(gameDifficulty){
		//Difficulty
		case "Easy":
			componentInputs[2].setMatrix(0,2,0,2,optionMatrix(1,3));
			componentInputs[2].setMatrix(3,5,3,5,optionMatrix(1,3));
			break;
		case "Medium":
			componentInputs[2].setMatrix(0,2,0,2,optionMatrix(2,3));
			componentInputs[2].setMatrix(3,5,3,5,optionMatrix(2,3));
			break;
		case "Hard":
			componentInputs[2].setMatrix(0,2,0,2,optionMatrix(3,3));
			componentInputs[2].setMatrix(3,5,3,5,optionMatrix(3,3));
			break;
		case "none":
			break;
		default:
			System.out.println("Unanticipated Input for gameDifficulity " + gameDifficulty);
			break;
		}
		System.out.println("social: "+ socialRating + " Professional: "+ professionalRating + " Educational: " + educationalRating);

		if(!(socialRating == professionalRating && professionalRating == educationalRating))
		{
			if(socialRating>professionalRating && socialRating>educationalRating)
			{
				componentInputs[2].setMatrix(6,8,6,8,optionMatrix(1,3));
				componentInputs[1].setMatrix(0,2,0,2,optionMatrix(1,3));

			}
			if(professionalRating>=socialRating && professionalRating>educationalRating)
			{
				componentInputs[3].setMatrix(0,2,0,2,optionMatrix(2,3));
				componentInputs[2].setMatrix(6,8,6,8,optionMatrix(2,3));
				componentInputs[1].setMatrix(0,2,0,2,optionMatrix(3,3));

			}
			if(educationalRating>=socialRating && educationalRating>=professionalRating)
			{
				componentInputs[2].setMatrix(6,8,6,8,optionMatrix(3,3));
				componentInputs[1].setMatrix(0,2,0,2,optionMatrix(2,3));
			}
		}
	}	
	public final void printStrings()
	{
		System.out.println(gameGradeLevel);
		System.out.println(playerGender);
		System.out.println(playerAge);
		System.out.println(playerDress);
		System.out.println(gameTheme);
		System.out.println(gameSetting);
		System.out.println(gameDifficulty);
		System.out.println(gameSubject);
	}
	public final String getFileLocation()
	{
		return gameSavePath;
	}
	public final void checkForXML(final String input)
	{			
		if (!input.contains(".")) {
			gameSavePath = input+".xml";
		}
        else { 
			String extension = input.substring(input.lastIndexOf(".") + 1, input.length());
			if (!extension.equalsIgnoreCase("XML")) {
				gameSavePath= input.substring(0,input.lastIndexOf("."))+".xml";
			}
		}
	}
	public final void actionPerformed(final ActionEvent e) 
	{
		switch (e.getActionCommand()) {
		case "openEngine": //---Game Engine code added by Sreeram---
			System.out.println("Invoking Game Engine..");
			GameView gameView = new GameView();
    		menuFrame myMenuFrame = new menuFrame(gameView);
			break;
		case "Submit":
			printStrings();
			distributeInputs();
			System.out.println("Submit Clicked");
			saveFileChooser = new JFileChooser("OutputGames//");
			int returnValue = saveFileChooser.showSaveDialog(saveFileChooser);
			if (returnValue==JFileChooser.APPROVE_OPTION) {
				File file = saveFileChooser.getSelectedFile();
				gameSavePath=file.getAbsolutePath(); 
				checkForXML(gameSavePath);
				System.out.println("Game Save Path: "+gameSavePath);
				submitClicked = true;
				Currentfile = file;
				previewGame(file);
			}
			else if (returnValue == JFileChooser.CANCEL_OPTION) {
				System.out.println("Save cancelled by user. \n Returning.");
			}
			break;
		case "openFile":
			loadGame();

			break;
		case "charactersToolbar":
			//JD
			characterSelectAsset = null;
			characterSelectWindow.setCharacterAsset(characterSelectAsset);
			ArrayList<CharacterAsset> chars = new ArrayList<CharacterAsset>();
			//List<Asset> assets = lastSelectedScreen.getAssets(); 
			List<Asset> assets = null; //Temporary fix to stop the code from breaking for now.
			for (Asset as : assets) {
				if (as instanceof CharacterAsset) {
					chars.add((CharacterAsset) as);
				}
			}
			ArrayList<String> charNamesInScreen = getScreenCharacterNames(chars);
			ArrayList<String> charNamesInGame = getGameGenericCharacterNames();
			ArrayList<String> availableChars = new ArrayList<String>();
			for (String charName : charNamesInGame) {
				if (!charNamesInScreen.contains(charName)) {
					availableChars.add(charName);
				}
			}
			if (availableChars.isEmpty()) {
				JOptionPane.showMessageDialog(null, "All characters are currently in this Screen, none to add");
			}
			else {
				characterSelectWindow.setCharacterChoices(availableChars);
				characterSelectWindow.setVisible(true);
			}
			break;
		case "propToolbar":
			propSelectAsset = null;
			propSelectWindow.setImageAsset(propSelectAsset);
			propSelectWindow.setVisible(true);
			break;
		case "backgroundToolbar":
			backgroundSelectPath = null;
			String currentBackgroundPath = lastSelectedScene.getBackground().getBackground();
			currentBackgroundPath = currentBackgroundPath.substring(0, currentBackgroundPath.lastIndexOf('\\'));
			backgroundSelectWindow.setBackgroundPathString(backgroundSelectPath);
			backgroundSelectWindow.setBackgroundFolderPath(currentBackgroundPath);
			backgroundSelectWindow.setVisible(true);
			break;
		case "soundToolbar":
			soundSelectPath = null;
			if (selectedLevel.equals(gameLevel.SCENE)) {
				soundSelectWindow.setSoundFolderPath(SoundSelectWindow.MUSICFOLDER);
			}
			else if (selectedLevel.equals(gameLevel.SCREEN)) {
				soundSelectWindow.setSoundFolderPath(SoundSelectWindow.EFFECTSFOLDER);
			}
			else {
				break;
			}
			soundSelectWindow.setVisible(true);
			break;
			//JD end
		case "deleteElement":

			//Comments below are to temporarily remove code until we know what to do with Assets.
			/*
			Asset toDelete = scenePanel.getTargetedAsset();
			if(lastSelectedScreen.getAssets().contains(toDelete))
			{
				lastSelectedScreen.getAssets().remove(toDelete);
				displayScreen(lastSelectedScene, lastSelectedScreen);
			}
			else
			{
				System.out.println("Error: Attempt to delete asset not in screen!");
			}*/
			break;
		case "previewSound":
			/*
			Asset toPreviewSound = scenePanel.getTargetedAsset();
			if(lastSelectedScreen.getAssets().contains(toPreviewSound))
			{
				String insideSoundFolderPath = toPreviewSound.getSoundEffect();
				AudioPlayer.playAudio(soundFolder + insideSoundFolderPath);
			}
			else
			{
				System.out.println("Error: Attempt to preview asset not in screen!");
			}*/
			break;
		case "backgroundMusicPreviewPlay":
			//TODO finish
			if (lastSelectedScene.getMusic() != null) {
				String insideSoundFolderPath = lastSelectedScene.getMusic().getMusic();
				AudioPlayer.playAudio(soundFolder + insideSoundFolderPath);
			} else {
				System.out.println("Error: No background music found.");
			}
			break;
		case "backgroundMusicPreviewStop":
			AudioPlayer.stopAudio();
			break;
		case "toggleHiddenElements":
			scenePanel.toggleHiddenElements();
			break;
		case "resizeAsset":
			displayScreen(lastSelectedScene, lastSelectedScreen);
			break;
		case "saveToRepo":
			if (Currentfile != null && !Currentfile.equals(""))
			{
				int retval = JOptionPane.showConfirmDialog(null, "Overwrite " + Currentfile + " and save changes?", "Warning", JOptionPane.YES_NO_OPTION);
				if (retval == JOptionPane.YES_OPTION)
				{
					System.out.println("overwriting " + Currentfile);
					saveGameFile(Currentfile);
				}
			}
			break;
		case "saveToRepoAs":
			if (game != null) {
				saveGameFileAs();
			}
			break;
		case "viewErrorList":
			loadAndDisplayErrors(game);
			break;
		case "addToRepo":
			File parent = new File("New Games\\");
			saveFileChooser = new JFileChooser(parent);
			saveFileChooser.setDialogTitle("Choose the game folder");
			saveFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			updater= new Updates();
			int returnValue2 = saveFileChooser.showOpenDialog(saveFileChooser);
			String gameName;
			if (returnValue2 == JFileChooser.APPROVE_OPTION)
			{
				File file = saveFileChooser.getSelectedFile();
				gameName = file.getAbsolutePath();
				
//				if(gameName.contains("New Games"))
//				{ 
//				System.out.println("Game name: "+ gameName.substring(gameName.lastIndexOf('\\')+1,gameName.length()));				
//				updater.addGame(gameName.substring(gameName.lastIndexOf('\\')+1,gameName.length()));
//				}
				System.out.println("Game name: "+ gameName.substring(gameName.lastIndexOf('\\')+1,gameName.length()));
				updater.addGame(gameName.substring(gameName.lastIndexOf('\\')+1,gameName.length()));
			}
			else if(returnValue2 == JFileChooser.CANCEL_OPTION)
			{
				System.out.println("Open cancelled by user. /n Returning.");
			}
			
			break;
		case "remakeRepo":
			updater = new Updates();
			updater.remakeRepo();
			break;
			// Grade 
		case "primary":
			gameGradeLevel = "primary";
			break;
		case "secondary":
			gameGradeLevel = "secondary";
			break;
		case "high":
			gameGradeLevel = "high";
			break;
		case "college":
			gameGradeLevel = "college";
			break;
		case "jobTraining":
			gameGradeLevel = "jobTraining";
			break;
		case "no grade":
			gameGradeLevel = "none";
		break;
//Gender			
		case "Male": 
			playerGender = "Male";
			break;
		case "Female":
			playerGender = "Female";
			break;
		case "no gender":
			playerGender = "none";
//Age					
		case "Young":
			playerAge = "Young";
			break;
		case "Old":
			playerAge = "Old";
			break;
		case "no age":
			playerAge = "none";
			break;
//Dress
		case "Casual":
			playerDress = "Casual";
			break;
		case "Fancy":
			playerDress = "Fancy";
			break;
		case "no dress":
			playerDress = "none";
			break;
//Theme
		case "Gooble":
			gameTheme = "Gooble";
			break;
		case "Dream":
			gameTheme = "Dream";
			break;
		case "VirtualTour":
			gameTheme = "VirtualTour";
			break;
		case "Workplace":
			gameTheme = "Workplace";
			break;
		case "Other":
			gameTheme = "Other";
			break;
		case "no theme":
			gameTheme = "none";
			break;
//Subject
		case "English":
			gameSubject = "English";
			break;
		case "Math":
			gameSubject = "Math";
			break;
		case "Science":
			gameSubject = "Science";
			break;
		case "Social Studies":
			gameSubject = "Social Studies";
			break;
		case "Literature":
			gameSubject = "Literature";
			break;
		case "Professional":
			gameSubject = "Professional";
			break;
		case "no subject":
			gameSubject = "none";
			break;
//Setting
		case "ProfessionalSetting":
			gameSetting = "Professional";
			break;
		case "CasualSetting":
			gameSetting = "Casual";
			break;
		case "NaturalSetting":
			gameSetting = "Natural";
			break;
		case "EducationalSetting":
			gameSetting = "Educational";
			break;
		case "Non-terrestrialSetting":
			gameSetting = "Non-terrestrial";
			break;
		case "no setting":
			gameSetting = "none";
			break;
//Difficulty
		case "Easy":
			gameDifficulty = "Easy";
			break;
		case "Medium":
			gameDifficulty = "Medium";
			break;
		case "Hard":
			gameDifficulty = "Hard";
			break;
		case "no difficulty":
			gameDifficulty = "none";
			break;
		default:
		System.out.println("Unanticipated Input in ActionPerformed:" + e.getActionCommand());
		break;
		}
	}
}
