package net.teamio.director.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JScrollBar;

import net.teamio.director.cut.Keyframe;
import net.teamio.director.cut.Scene;

public class KeyFramePanel extends JPanel {

	private static final long serialVersionUID = -6393897786797869553L;

	public Scene scene;
	public JScrollBar horizontalScrollbar;
	public int selectedKeyframeID = -1;
	public Keyframe selectedKeyframe = null;
	
	/**
	 * Create the panel.
	 */
	public KeyFramePanel() {
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int categoryWidth = g.getFontMetrics().stringWidth("Thingamabob");
		int categoryHeight = 21;
		int keyframeInset = 3;
		
		
		
		paintBox(g, "Player", 0, 0, categoryWidth, categoryHeight);
		paintBox(g, "Thingamabob", 0, categoryHeight, categoryWidth, categoryHeight);
		
		paintBox(g, categoryWidth, 0, this.getWidth() - categoryWidth, categoryHeight);
		paintBox(g, categoryWidth, categoryHeight, this.getWidth() - categoryWidth, categoryHeight);
		
		if(scene != null) {
			
			for(int i = 0; i < scene.movement.size(); i++) {
				System.out.println(i);
				g.setColor(Color.RED);
				g.fillRect(categoryWidth + keyframeInset + i * (20 + keyframeInset), keyframeInset, 20, categoryHeight - 2*keyframeInset);
			}
			
		}
	}
	
	private void paintBox(Graphics g, String text, int x, int y, int width, int height) {
		g.drawRect(x, y, width, height);
		g.drawString(text, x, y + height - 3);
	}
	
	private void paintBox(Graphics g, int x, int y, int width, int height) {
		g.drawRect(x, y, width, height);
	}

}
