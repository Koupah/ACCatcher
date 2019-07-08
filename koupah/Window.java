package koupah;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;

public class Window extends JFrame {
	
	private JPanel contentPane;
	static JLabel foundText = new JLabel("Nothing Found");
	static JLabel typeText = new JLabel("");
	static JLabel cpsText = new JLabel("Clicks: 0");
	static JLabel releasesText = new JLabel("Releases: 0");
	static boolean debug = true;
	static ArrayList<Check> checks = new ArrayList<Check>();
	static JLabel avgcps = new JLabel("Average CPS: 0");
	
	
	public static void main(String[] args) throws IOException, InterruptedException {

		for (Category c : Category.values()) {
			if (c.equals(Category.DoubleClick))
				checks.add(new Check(c, 1, c.name()));
			else if (c.equals(Category.DoubleRelease))
				checks.add(new Check(c,1,c.name()));
			else if (c.equals(Category.FastClick))
				checks.add(new Check(c,10,c.name()));
			else
			checks.add(new Check(c, 3, c.name()));
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Window() {
		setResizable(false);
		addMouseListener(new MouseListener());
		setTitle("CLICK IN THIS WINDOW");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		avgcps.setHorizontalAlignment(SwingConstants.CENTER);
		avgcps.setFont(new Font("Tahoma", Font.PLAIN, 16));
		avgcps.setBounds(0, 104, 444, 32);
		contentPane.add(avgcps);
		foundText.setForeground(new Color(0, 255, 0));
		
		
		foundText.setHorizontalAlignment(SwingConstants.CENTER);
		foundText.setFont(new Font("Tahoma", Font.BOLD, 20));
		foundText.setBounds(0, 9, 444, 32);
		contentPane.add(foundText);
		
		cpsText = new JLabel("Clicks: 0");
		cpsText.setHorizontalAlignment(SwingConstants.CENTER);
		cpsText.setBounds(0, 147, 444, 45);
		contentPane.add(cpsText);
		
		releasesText = new JLabel("Releases: 0");
		releasesText.setHorizontalAlignment(SwingConstants.CENTER);
		releasesText.setBounds(0, 192, 444, 45);
		contentPane.add(releasesText);
		typeText.setForeground(Color.RED);
		
		typeText.setHorizontalAlignment(SwingConstants.CENTER);
		typeText.setFont(new Font("Tahoma", Font.PLAIN, 20));
		typeText.setBounds(0, 38, 444, 32);
		contentPane.add(typeText);
		
	}

}

