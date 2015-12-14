package net.teamio.director.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.awt.Dimension;

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
		
		Color c_keyframe_normalBody = new Color(79, 148, 205);
		Color c_keyframe_normalBorder = new Color(69, 129, 179);

		Color c_keyframe_selectedBody = new Color(49, 92, 128);
		Color c_keyframe_selectedBorder = new Color(39, 73, 102);
		
		Color c_category_titleBody = new Color(102, 95, 71);
		Color c_category_titleBorder = new Color(153, 143, 107);
		Color c_category_titleText = Color.WHITE;
		
		Color c_categoty_framesBody = new Color(51, 51, 51);
		Color c_categoty_framesBorder = new Color(102, 102, 102);
		
		int categoryTitlePadding = 5;
		
		int categoryWidth = g.getFontMetrics().stringWidth("Thingamabob") + categoryTitlePadding*2;
		
		g.setColor(c_category_titleBody);
		g.fillRect(0, 0, categoryWidth-1, this.getHeight());

		g.setColor(c_category_titleText);
		paintBox(g, "Player", categoryTitlePadding, 0, categoryWidth, categoryHeight);
		paintBox(g, "Thingamabob", categoryTitlePadding, categoryHeight, categoryWidth, categoryHeight);

		g.setColor(c_category_titleBorder);
		g.drawLine(categoryWidth-1, 0, categoryWidth-1, this.getHeight());
		paintBox(g, 0, 0, categoryWidth, categoryHeight);
		paintBox(g, 0, categoryHeight, categoryWidth, categoryHeight);
		

		g.setColor(c_categoty_framesBody);
		g.fillRect(categoryWidth, 0, this.getWidth()-categoryWidth, this.getHeight());
		
		g.setColor(c_categoty_framesBorder);
		paintBox(g, categoryWidth, 0, this.getWidth() - categoryWidth, categoryHeight);
		paintBox(g, categoryWidth, categoryHeight, this.getWidth() - categoryWidth, categoryHeight);
		keyframeInset = 5;
		if(scene == null) {
			this.setPreferredSize(new Dimension(categoryWidth, categoryHeight*2));
//			Container parent = this.getParent();
//			if(parent instanceof JScrollPane) {
//				JScrollPane scrollParent = (JScrollPane)parent;
//				scrollParent.
//			}
		} else {
			
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
					g.setColor(c_keyframe_selectedBody);
				} else {
					g.setColor(c_keyframe_normalBody);
				}
//				g.fillRect();
				g.fillRoundRect(xOff + keyframeInset, keyframeInset, width, categoryHeight - 2*keyframeInset,
						2, 2);
				if(i == selectedKeyframeID) {
					g.setColor(c_keyframe_selectedBorder);
				} else {
					g.setColor(c_keyframe_normalBorder);
				}
				g.drawRoundRect(xOff + keyframeInset, keyframeInset, width, categoryHeight - 2*keyframeInset,
						2, 2);
				xOff += width + keyframeInset;
			}
			
			if(selectedKeyframeID > scene.movement.size()) {
				setSelectedKeyframe(-1);
			}
			this.setPreferredSize(new Dimension(categoryWidth + xOff, categoryHeight*2));
		}
	}
	
	private void paintBox(Graphics g, String text, int x, int y, int width, int height) {
//		g.drawLine(x, y + height, x + width, y + height);
//		g.drawRect(x, y, width, height);
		g.drawString(text, x, y + height - 3);
	}
	
	private void paintBox(Graphics g, int x, int y, int width, int height) {
		g.drawLine(x, y + height, x + width, y + height);
//		g.drawRect(x, y, width, height);
	}

	public KeyframeListener getListener() {
		return listener;
	}

	public void setListener(KeyframeListener listener) {
		this.listener = listener;
	}

}
