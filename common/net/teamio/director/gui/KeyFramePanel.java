package net.teamio.director.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
	
	private int[] xPositions;
	private int[] widths;
	
	private KeyframeListener listener;
	
	int categoryHeight = 21;
	int keyframeInset = 3;
	float zoomFactor = 0.1f;
	
	public static interface KeyframeListener {
		void keyframeSelectionChanged(int id, Keyframe keyframe);
	}
	
	/**
	 * Create the panel.
	 */
	public KeyFramePanel() {
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean match = false;
				int y = e.getY();
				if(y >= 0 && y < categoryHeight) {
					int x = e.getX();
					for(int i = 0; i < xPositions.length; i++) {
						if(x >= xPositions[i] && x < xPositions[i] + widths[i]) {
							setSelectedKeyframe(i);
							match = true;
							break;
						}
					}
				}
				if(!match) {
					setSelectedKeyframe(-1);
				}
			}
		});
	}

	public void setSelectedKeyframe(int id) {
		if(scene.movement.size() > 0 && id >= 0 && id < scene.movement.size()) {
			selectedKeyframeID = id;
			selectedKeyframe = scene.movement.get(id);
		} else {
			selectedKeyframeID = -1;
			selectedKeyframe = null;
		}
		if(getListener() != null) {
			getListener().keyframeSelectionChanged(selectedKeyframeID, selectedKeyframe);
		}
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int categoryWidth = g.getFontMetrics().stringWidth("Thingamabob");
		
		g.setColor(this.getBackground());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(this.getForeground());
		
		paintBox(g, "Player", 0, 0, categoryWidth, categoryHeight);
		paintBox(g, "Thingamabob", 0, categoryHeight, categoryWidth, categoryHeight);
		
		paintBox(g, categoryWidth, 0, this.getWidth() - categoryWidth, categoryHeight);
		paintBox(g, categoryWidth, categoryHeight, this.getWidth() - categoryWidth, categoryHeight);
		
		if(scene != null) {
			
			scene.recalculateTimings();
			
			xPositions = new int[scene.movement.size()];
			widths = new int[scene.movement.size()];
			
			int xOff = categoryWidth;
			
			
			for(int i = 0; i < scene.movement.size(); i++) {
				Keyframe keyframe = scene.movement.get(i);
				int width = (int)(keyframe.time * zoomFactor);
				if(width < 2) {
					width = 2;
				}
				
				xPositions[i] = xOff;
				widths[i] = width + keyframeInset;
				
				if(i == selectedKeyframeID) {
					g.setColor(Color.ORANGE);
				} else {
					g.setColor(Color.RED);
				}
//				g.fillRect();
				g.fillRoundRect(xOff + keyframeInset, keyframeInset, width, categoryHeight - 2*keyframeInset,
						5, 5);
				xOff += width + keyframeInset;
			}
			
			if(selectedKeyframeID > scene.movement.size()) {
				setSelectedKeyframe(-1);
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

	public KeyframeListener getListener() {
		return listener;
	}

	public void setListener(KeyframeListener listener) {
		this.listener = listener;
	}

}
