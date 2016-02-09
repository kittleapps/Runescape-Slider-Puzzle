import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Slider extends JFrame implements ActionListener {

	private static final long serialVersionUID = 3820807912519753989L;

	public static final int rows = 5;
	public static final int cols = 5;
	public static final int chunks = rows * cols;

	public static String[][] position;
	/* position's Data storage Usages:
	 *
	 * [#][0] = Exact Button X Position
	 * [#][1] = Exact Button Y Position
	 * [#][2] = Exact Button Width
	 * [#][3] = Exact Button Height
	 * [#][4] = Permanent Button Position ID, Leave Unchanged When Created.
	 * [#][5] = Current/Temporary Button Position ID.
	 * 
	 */
	private static JPanel Tiles;
	public static Timer timer;
	private static JButton[] listOfBtn = new JButton[chunks];

	public static String PATH = Paths.get(".").toAbsolutePath().normalize().toString() + "/img_src/";
	public static File TEMP_DIR = new File(Slider.PATH + ".TEMP_DIR/");

	public static String[] LET = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z".split(" ");
	public final static int INTERVAL = 550;
	public static int Offset = 56;
	public static boolean RANDOMIZED = true;
	
	public static int[][] C2 = { 
			{ 115 + Offset * 0, 39 + Offset * 0 },
			{ 115 + Offset * 1, 39 + Offset * 0 },
			{ 115 + Offset * 2, 39 + Offset * 0 },
			{ 115 + Offset * 3, 39 + Offset * 0 },
			{ 115 + Offset * 4, 39 + Offset * 0 }, 
			{ 115 + Offset * 0, 39 + Offset * 1 },
			{ 115 + Offset * 1, 39 + Offset * 1 },
			{ 115 + Offset * 2, 39 + Offset * 1 },
			{ 115 + Offset * 3, 39 + Offset * 1 },
			{ 115 + Offset * 4, 39 + Offset * 1 },
			{ 115 + Offset * 0, 39 + Offset * 2 }, 
			{ 115 + Offset * 1, 39 + Offset * 2 },
			{ 115 + Offset * 2, 39 + Offset * 2 },
			{ 115 + Offset * 3, 39 + Offset * 2 },
			{ 115 + Offset * 4, 39 + Offset * 2 },
			{ 115 + Offset * 0, 39 + Offset * 3 },
			{ 115 + Offset * 1, 39 + Offset * 3 },
			{ 115 + Offset * 2, 39 + Offset * 3 },
			{ 115 + Offset * 3, 39 + Offset * 3 },
			{ 115 + Offset * 4, 39 + Offset * 3 },
			{ 115 + Offset * 0, 39 + Offset * 4 },
			{ 115 + Offset * 1, 39 + Offset * 4 },
			{ 115 + Offset * 2, 39 + Offset * 4 },
			{ 115 + Offset * 3, 39 + Offset * 4 }, 
			{ 115 + Offset * 4, 39 + Offset * 4 },
	};

	public static int TILED = 1;
	public static int lastButtonID = (chunks-1);
	public static String temp_1, temp_2, temp_3, temp_4, temp_5, temp_6, temp_7, temp_8, temp_9;
	public JButton exit, hint;

	public Slider(String Title, String Source, String UI) throws IOException {
		final File fileToRead = new File(UI);
		final File ImagePath = new File(Source);
		if (fileToRead.exists()) {
			if (ImagePath.exists()) {

				setLayout(null);
				exit = new JButton();
				hint = new JButton();
				Slider.Tiles = new JPanel();

				Slider.timer = new Timer(Slider.INTERVAL, evt -> {
					Slider.Tiles.repaint();
				});

				this.add(Slider.Tiles);
				this.setSize(new Dimension(ImageIO.read(fileToRead).getWidth() + 10, ImageIO.read(fileToRead).getHeight() + 10));
				Slider.Tiles.setBounds(0, 0, getWidth(), getHeight());
				Slider.Tiles.setLayout(null);
				Slider.Tiles.add(exit);
				exit.setOpaque(false);
				exit.setContentAreaFilled(false);
				exit.setBorderPainted(false);
				exit.setBounds(474, 15, 38, 38);
				exit.setBackground(Color.BLACK);
				exit.setName("Exit!");

				Slider.Tiles.add(hint);
				hint.setOpaque(false);
				hint.setContentAreaFilled(false);
				hint.setBorderPainted(false);
				hint.setBounds(10, 284, 49, 49);
				hint.setName("Hint?");
				hint.setBackground(Color.BLACK);
				Slider.position = new String[chunks][6];


				try {
					final JLabel UI_img = new JLabel(new ImageIcon(ImageIO.read(fileToRead)));
					Slider.Tiles.add(UI_img);
					UI_img.setBounds(0, 0, getWidth(), getHeight());
				} catch (final IOException e1) {
					e1.printStackTrace();
					System.out.println("ERROR! ERROR! I Blame Mod Boko! #BlameBoko");
					System.exit(0);
				}


				BufferedImage image = null;

				try {
					image = ImageIO.read(ImagePath);
				} catch (final IOException e) {
					System.out.println("The File: "+ImagePath.getAbsolutePath()+" Triggered an error.");
					System.exit(0);
				}

				BufferedImage[] imgs = new BufferedImage[chunks];
				final int chunkWidth = image.getWidth() / cols;
				final int chunkHeight = image.getHeight() / rows;
				int count = 0;

				for (int x = 0; x < rows; x++) {
					for (int y = 0; y < cols; y++) {
						imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
						final Graphics2D gr = imgs[count++].createGraphics();
						gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x,
								chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
						gr.dispose();
					}
				}

				System.out.println("Sudo Bash: NootNoot!");
				System.out.println("Sudo Bash has Recieved an Image Drop!");
				System.out.println("Sudo Bash: Brilliant!");
				System.out.println("You Receive "+chunks+" Image Shard(s).");

				Randomizer myRandom = new Randomizer(chunks-1);
				for (int j = 0; j < (chunks-1); ++j){
					if (RANDOMIZED){
						int value = myRandom.next();
						Slider.position[j][4] = "" + value;
						Slider.position[j][5] = "" + value;
					}
					else{
						Slider.position[j][4] = "" + j;
						Slider.position[j][5] = "" + j;
					}
				}
				for (int i = 0; i < chunks; i++) {
					int val = i;
					if (i == (chunks-1)) {
						Slider.listOfBtn[chunks-1] = new JButton();
						Slider.listOfBtn[chunks-1].setName(""+(chunks-1));
						Slider.listOfBtn[chunks-1].setOpaque(true);
						Slider.listOfBtn[chunks-1].setContentAreaFilled(true);
						Slider.listOfBtn[chunks-1].setBorderPainted(false);
						Slider.listOfBtn[chunks-1].setEnabled(false);
						Slider.listOfBtn[chunks-1].setBackground(new Color(30,30,30,255));
						Slider.listOfBtn[chunks-1].setBounds(Slider.C2[chunks-1][0], Slider.C2[chunks-1][1], 49, 49);
						Slider.position[chunks-1][0] = "" + Slider.listOfBtn[chunks-1].getX();
						Slider.position[chunks-1][1] = "" + Slider.listOfBtn[chunks-1].getY();
						Slider.position[chunks-1][2] = "" + Slider.listOfBtn[chunks-1].getWidth();
						Slider.position[chunks-1][3] = "" + Slider.listOfBtn[chunks-1].getHeight();
						Slider.position[chunks-1][4] = "" + (chunks-1);
						Slider.position[chunks-1][5] = "" + (chunks-1);
						Slider.Tiles.add(Slider.listOfBtn[chunks-1]);
					} else {
						Slider.listOfBtn[i] = new JButton();
						Slider.listOfBtn[i].addActionListener(new ActionListener()
						{
							@Override
							public void actionPerformed(ActionEvent e)
							{
								Slider.CheckMove(Slider.listOfBtn[val], e);
							}
						});
						Slider.listOfBtn[i].setIcon(new ImageIcon(imgs[i]));
						Slider.listOfBtn[i].setName("" + i);
						Slider.listOfBtn[i].setBorderPainted(false);
						Slider.listOfBtn[i].setOpaque(true);
						Slider.listOfBtn[i].setBackground(new Color(88, 73, 56));
						Slider.listOfBtn[i].setBounds(Slider.C2[Integer.parseInt(Slider.position[i][4])][0], Slider.C2[Integer.parseInt(Slider.position[i][4])][1], 49, 49);
						Slider.position[i][0] = "" + Slider.listOfBtn[i].getX();
						Slider.position[i][1] = "" + Slider.listOfBtn[i].getY();
						Slider.position[i][2] = "" + Slider.listOfBtn[i].getWidth();
						Slider.position[i][3] = "" + Slider.listOfBtn[i].getHeight();
						Slider.listOfBtn[i].setVisible(true);
						Slider.Tiles.add(Slider.listOfBtn[i]);
					}
				}
				validate();
				setTitle(Title);
				setResizable(false);
				setBackground(Color.BLACK);
				Slider.Tiles.setBackground(Color.BLACK);
				setLocationRelativeTo(null);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
				setVisible(true);
				Slider.timer.start();
				System.out.println("You Combine the Image Shards into a Clue Puzzle.");
				hint.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						System.out.println("Nothing Interesting Happens.");	
						JOptionPane.showMessageDialog(null,
								"Sorry... There is No Hints in This Program.\n\nI May be a Lazy Programmer....\n\nBUT! This Program is to Help You Practise Without Hints ;)",
								"Sudo Bash Says...",
								JOptionPane.PLAIN_MESSAGE);
					}
				});

				exit.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						int dialogResult = JOptionPane.showConfirmDialog (null,
								"Do You Wish to Exit This Program?\n\nALL Progress will be LOST!",
								"Sudo Bash Says...",
								JOptionPane.ERROR_MESSAGE);
						if(dialogResult == JOptionPane.YES_OPTION){
							System.out.println("You Selected Yes to Close.. Terminating Program.. I'll Be Back...");
							System.exit(0);
						}
						else if(dialogResult == JOptionPane.OK_OPTION){
							System.out.println("You Selected OK to Close.. Terminating Program.. I'll Be Back...");
							System.exit(0);
						}
						else if(dialogResult == JOptionPane.NO_OPTION){
							System.out.println("You Selected NO to Close.. Program Will Resume As If You Never Clicked X ;)");
						}
						else{
							System.out.println("You Selected an Unknown Method.. Program Will Resume As If You Never Did That..");
						}
					}
				});


			}
			else{
				System.out.println("The File: "+ImagePath.getAbsolutePath()+" Was Not Found.");
				JOptionPane.showMessageDialog(null,
						"The File: "+ImagePath.getAbsolutePath()+" Was Not Found.\n\nThis Program Replies on "+Source+" To Work Properly.",
						"Sudo Bash Says...",
						JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
		else{
			System.out.println("The File: "+fileToRead.getAbsolutePath()+" Was Not Found.");
			JOptionPane.showMessageDialog(null,
					"The File: "+fileToRead.getAbsolutePath()+" Was Not Found.\n\nThis Program Replies on "+UI+" To Work Properly.",
					"Sudo Bash Says...",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {}

	public static void Move(JButton b) {
		final int BUTTONID = Integer.parseInt(b.getName());
		final int Missing_TILE = Slider.findMissingTile();

		int TILESA = 1;
		Slider.temp_1 = Slider.position[Missing_TILE][0] + ":" + Slider.position[Missing_TILE][1] + ":"
				+ Slider.position[Missing_TILE][2] + ":" + Slider.position[Missing_TILE][3];
		Slider.temp_2 = Slider.position[BUTTONID][0] + ":" + Slider.position[BUTTONID][1] + ":"
				+ Slider.position[BUTTONID][2] + ":" + Slider.position[BUTTONID][3];
		if (Slider.TILED == 1 && TILESA == 1) {
			Slider.listOfBtn[BUTTONID].setBounds(Integer.parseInt(Slider.position[Missing_TILE][0]),
					Integer.parseInt(Slider.position[Missing_TILE][1]),
					Integer.parseInt(Slider.position[Missing_TILE][2]),
					Integer.parseInt(Slider.position[Missing_TILE][3]));
			Slider.listOfBtn[24].setBounds(Integer.parseInt(Slider.position[BUTTONID][0]),
					Integer.parseInt(Slider.position[BUTTONID][1]), Integer.parseInt(Slider.position[BUTTONID][2]),
					Integer.parseInt(Slider.position[BUTTONID][3]));
			Slider.TILED = 0;
			TILESA = 0;
		} else if (Slider.TILED == 0 && TILESA == 1) {
			Slider.listOfBtn[BUTTONID].setBounds(Integer.parseInt(Slider.position[Missing_TILE][0]),
					Integer.parseInt(Slider.position[Missing_TILE][1]),
					Integer.parseInt(Slider.position[Missing_TILE][2]),
					Integer.parseInt(Slider.position[Missing_TILE][3]));
			Slider.listOfBtn[Missing_TILE].setBounds(Integer.parseInt(Slider.position[BUTTONID][0]),
					Integer.parseInt(Slider.position[BUTTONID][1]), Integer.parseInt(Slider.position[BUTTONID][2]),
					Integer.parseInt(Slider.position[BUTTONID][3]));
			Slider.TILED = 1;
			TILESA = 0;
		}
		Slider.temp_5 = Slider.position[Missing_TILE][0];
		Slider.temp_6 = Slider.position[Missing_TILE][1];
		Slider.temp_7 = Slider.position[Missing_TILE][2];
		Slider.temp_8 = Slider.position[Missing_TILE][3];
		Slider.temp_9 = Slider.position[Missing_TILE][5];

		Slider.position[Missing_TILE][0] = Slider.position[BUTTONID][0];
		Slider.position[Missing_TILE][1] = Slider.position[BUTTONID][1];
		Slider.position[Missing_TILE][2] = Slider.position[BUTTONID][2];
		Slider.position[Missing_TILE][3] = Slider.position[BUTTONID][3];
		Slider.position[Missing_TILE][5] = Slider.position[BUTTONID][5];
		Slider.position[BUTTONID][0] = Slider.temp_5;
		Slider.position[BUTTONID][1] = Slider.temp_6;
		Slider.position[BUTTONID][2] = Slider.temp_7;
		Slider.position[BUTTONID][3] = Slider.temp_8;
		Slider.position[BUTTONID][5] = Slider.temp_9;
		Slider.lastButtonID = Missing_TILE;
		if (CheckResults()){
			System.out.println("You Completed a Treasure Trail!");
			JOptionPane.showMessageDialog(null,
					"Congratulations! You Finished This Slider Puzzle!\n\nUnfortunately You Don't Win GP/Items as a Result...\n\nIt Was Good Practise Don't You Think. =)",
					"Sudo Bash Says...",
					JOptionPane.PLAIN_MESSAGE);
			for (int i = 0; i < chunks; i++){
				Slider.listOfBtn[i].setEnabled(false);
			}
		}
	}
	public static boolean CheckResults(){
		int correctPlacements = 0;
		for (int i = 0; i < chunks; i++){
			if(Slider.position[i][4] ==  Slider.position[i][5]){
				correctPlacements++;
			}
		}
		if (correctPlacements >=25){
			return true;
		}else{
			return false;
		}
	}
	public static void CheckMove(JButton b, ActionEvent e) {

		if ((b.getName().equalsIgnoreCase("Exit!")) || (b.getName().equalsIgnoreCase("Hint?"))
				|| (b.getName() == null) || (b.getName().equalsIgnoreCase(""))){

		}
		else{
			final int BUTTONID = Integer.parseInt(b.getName());

			if (Integer.parseInt(Slider.position[BUTTONID][5]) - 1 == Slider.getLastButtonID()
					|| Integer.parseInt(Slider.position[BUTTONID][5]) + 1 == Slider.getLastButtonID()
					|| Integer.parseInt(Slider.position[BUTTONID][5]) - 5 == Slider.getLastButtonID()
					|| Integer.parseInt(Slider.position[BUTTONID][5]) + 5 == Slider.getLastButtonID()) {
				Slider.Move(b);
			}
			else{
				System.out.println("Nothing Interesting Happens.");
			}
		}

	}

	public static int findMissingTile() {
		int Counter = 0;

		for (int i = 0; i < chunks; i++) {
			if (Slider.position[i][4].equals("" + (chunks-1))) {
				return Counter;
			} else {
				Counter++;
			}
		}
		return Counter;

	}

	public static int getLastButtonID() {
		return Integer.parseInt(Slider.position[Slider.lastButtonID][5]);
	}

	public static void main(String[] args) {
		final String PATH = Paths.get(".").toAbsolutePath().normalize().toString() + "/img_src/";
		try {
			System.out.println("Welcome to Runescape!");
			new Slider("puzzle", PATH + "PIC.png", PATH + "UI.png");

		} catch (final IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}


}
