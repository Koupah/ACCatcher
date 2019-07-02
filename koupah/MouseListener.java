package koupah;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MouseListener extends MouseAdapter{
	
		static int clicks = 0;
		static int releases = 0;
		static boolean mousePressed = false;
		long lastPress = 0;
		long lastRelease = 0;
		
		//When we double click, if it is the 50th click, this become 50
		int doubleClickAtClicks = 0;
		
		//Just in case we get a bunch of doubles
		int doubleClickCount = 0;
		
		ArrayList<Long> clickTimes = new ArrayList<Long>();
		
        public void mousePressed(MouseEvent e){
        
        	if (!(e.getButton() == MouseEvent.BUTTON1))
        		return;
 
        	//CPS Counter
        	if (lastPress == 0) {
        		//TODO Maybe some sort of way to include it?
        	} else if (e.getWhen() - lastPress > 4000) { //Reset CPS Counter after 4 seconds
        		clickTimes.clear();
        		//TODO Maybe some sort of way to include this first click too?
        	} else {
        		clickTimes.add(e.getWhen() - lastPress);
        	}
        	
        	
   
        	//Checks
        	if (mousePressed && getCheck(Category.DoubleClick) != null) {
        		getCheck(Category.DoubleClick).addFlags(1);
        		doubleClickAtClicks = clicks + 1;
        		doubleClickCount++;
        	} else if (getCheck(Category.FastClick) != null) {
        		
        			//Clicked 8ms after previous click		//Clicked <= 5ms after releasing
        		if (e.getWhen() - lastPress <= 8 || e.getWhen() - lastRelease <= 5) 
        		getCheck(Category.FastClick).addFlags(1);        		
        	}
        	
        	
        	
        	//CPS Code
        	if (!clickTimes.isEmpty()) {
        		Long totalTime = 0L;
        		
        		//Keep it at a size of 30 to allow CPS to fluctuate
        		if (clickTimes.size() > 30)
        			clickTimes.remove(1);
        		
        		for (Long a : clickTimes) {
        			
        			totalTime += a;
        		}
        		String a = String.valueOf((double)1000 / (totalTime / clickTimes.size()));
        		if (a.length() > 5)
        			a = a.substring(0, 5);
        		Window.avgcps.setText("Average CPS: " +  a);
        	}
        	
        	
        	
        	//Needed tracking code
        	clicks++;
        	mousePressed = true;
        	lastPress = e.getWhen();
        	Window.cpsText.setText("Clicks: " + clicks);
        }
        public void mouseClicked(MouseEvent e) {
        	
        }
        
        public void mouseEntered(MouseEvent e) {

        }
        public void mouseExited(MouseEvent e) {

        }
       
        public void mouseReleased(MouseEvent e) {
        	if (!(e.getButton() == MouseEvent.BUTTON1))
        		return;
        	
        	
        	//If mouse is released and it wasn't even pressed to begin with
        	if (!mousePressed && getCheck(Category.DoubleRelease) != null) {
        		
        		//If double release basically same time as double click, mouse reporting error
        		if (doubleClickAtClicks + 2 >= clicks) {
        			if (Window.debug)
        			System.out.println("Mouse Reporting Error?");
        			getCheck(Category.DoubleClick).addFlags(-1);
        		} else if (doubleClickCount >= 3)
        			getCheck(Category.Unknown).addFlags(doubleClickCount);
        			
        		else getCheck(Category.DoubleRelease).addFlags(1);
        		
        	} else if (getCheck(Category.FastRelease) != null) {
        			// If time between this release and last release <= 8ms   // If this release - last click time <= 8ms
        			//This first check is fine	    //Some mice mess this 2nd check up
        		if (e.getWhen() - lastRelease <= 8 /*|| e.getWhen() - lastPress <= 8*/)
            		getCheck(Category.FastRelease).addFlags(1);
            	
        	}
        	
        	
        	//Needed tracking code
        	releases++;
        	mousePressed = false;
        	lastRelease = e.getWhen();
        	Window.releasesText.setText("Releases: " + releases);
        	checkAllChecks();
        }
        
        public Check getCheck(Category c) {
        	for (Check check : Window.checks) {
        		if (check.getName().equalsIgnoreCase(c.name()))
        			return check;
        	}
        	return null;
        }
        
        public void checkAllChecks() {
        	for (Check check : Window.checks) {
        	
        		if (check.hasReachedMax()) {
        			Window.foundText.setForeground(new Color(255, 0, 0));
        			Window.foundText.setText("Click Modification found!");
        			Window.typeText.setText("Type: " + check.name);
        		}
        	}
        }
        
    }
