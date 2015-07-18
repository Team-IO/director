package net.teamio.director.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import sun.rmi.server.ActivatableServerRef;
import net.teamio.director.act.IMinecraftAdapter;
import net.teamio.director.cut.Keyframe;
import net.teamio.director.cut.Scene;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SceneEditor {

	private IMinecraftAdapter adapter;
	public Scene scene;
	
	private JFrame frame;
	private JButton btnRecordKeyframe;
	private JButton btnPlay;
	private KeyFramePanel keyFramePanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SceneEditor window = new SceneEditor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SceneEditor() {
		initialize();
	}
	
	public void invokeSetVisible() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		keyFramePanel = new KeyFramePanel();
		panel.add(keyFramePanel, BorderLayout.CENTER);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setOrientation(JScrollBar.HORIZONTAL);
		panel.add(scrollBar, BorderLayout.SOUTH);
		
		JScrollBar scrollBar_1 = new JScrollBar();
		panel.add(scrollBar_1, BorderLayout.EAST);
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(300, 150));
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JButton btnAddKeyframe = new JButton("Add Keyframe");
		btnAddKeyframe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(scene != null) {
					scene.movement.add(new Keyframe());
				}
			}
		});
		GridBagConstraints gbc_btnAddKeyframe = new GridBagConstraints();
		gbc_btnAddKeyframe.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddKeyframe.gridx = 0;
		gbc_btnAddKeyframe.gridy = 0;
		panel_1.add(btnAddKeyframe, gbc_btnAddKeyframe);
		
		btnRecordKeyframe = new JButton("Record Keyframe");
		btnRecordKeyframe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(scene != null && adapter != null) {
					scene.movement.add(adapter.recordKeyframe());
				}
			}
		});
		GridBagConstraints gbc_btnRecordKeyframe = new GridBagConstraints();
		gbc_btnRecordKeyframe.insets = new Insets(0, 0, 5, 0);
		gbc_btnRecordKeyframe.gridx = 0;
		gbc_btnRecordKeyframe.gridy = 1;
		panel_1.add(btnRecordKeyframe, gbc_btnRecordKeyframe);
		
		btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adapter.replay(scene);
			}
		});
		GridBagConstraints gbc_btnPlay = new GridBagConstraints();
		gbc_btnPlay.gridx = 0;
		gbc_btnPlay.gridy = 3;
		panel_1.add(btnPlay, gbc_btnPlay);
	}

	public void dispose() {
		frame.dispose();
	}
	
	public void redraw() {
		keyFramePanel.scene = scene;
		keyFramePanel.repaint();
	}

	public boolean isDisposed() {
		return !frame.isVisible();
	}

	public IMinecraftAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(IMinecraftAdapter adapter) {
		this.adapter = adapter;
		btnPlay.setEnabled(adapter != null);
		btnRecordKeyframe.setEnabled(adapter != null);
	}
}
