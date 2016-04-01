package net.teamio.director.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.teamio.director.act.IMinecraftAdapter;
import net.teamio.director.cut.Keyframe;
import net.teamio.director.cut.Scene;
import net.teamio.director.gui.KeyFramePanel.KeyframeListener;

public class SceneEditor {

	private IMinecraftAdapter adapter;
	public Scene scene;
	
	private JFrame frame;
	private JButton btnRecordKeyframe;
	private JButton btnPlay;
	private KeyFramePanel keyFramePanel;
	private JLabel lblKeyframeX;
	private JSpinner spnX;
	private JSpinner spnY;
	private JSpinner spnZ;
	private JSpinner spnYaw;
	private JSpinner spnPitch;
	private JSpinner spnTime;
	private JSpinner spnInterpolationMovement;
	private JSpinner spnTimingMovement;
	private JSpinner spnInterpolationRotation;
	private JSpinner spnTimingRotation;
	private JButton btnDelete;
	private JButton btnDuplicate;
	private JButton btnClearScene;

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

	private void createDefaultLabel(JPanel panel, Component labelFor,
			String text, GridBagConstraints constraints) {

		JLabel lbl = new JLabel(text);
		lbl.setLabelFor(labelFor);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = constraints.gridx - 1;
		gbc.gridy = constraints.gridy;
		panel.add(lbl, gbc);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 587, 479);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		keyFramePanel = new KeyFramePanel();
		keyFramePanel.setListener(new KeyframeListener() {
			@Override
			public void keyframeSelectionChanged(int id, Keyframe keyframe) {
				redraw();
			}
		});
		panel.add(keyFramePanel, BorderLayout.CENTER);
		

		JScrollBar sbHorizontal = new JScrollBar();
		sbHorizontal.setOrientation(JScrollBar.HORIZONTAL);
		panel.add(sbHorizontal, BorderLayout.SOUTH);
		
		JScrollBar sbVertical = new JScrollBar();
		panel.add(sbVertical, BorderLayout.EAST);
		
		JPanel panelProperties = new JPanel();
		panelProperties.setPreferredSize(new Dimension(300, 250));
		frame.getContentPane().add(panelProperties, BorderLayout.SOUTH);
		GridBagLayout gbl_panelProperties = new GridBagLayout();
		gbl_panelProperties.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelProperties.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panelProperties.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelProperties.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelProperties.setLayout(gbl_panelProperties);
		
		/*
		 * Add Keyframe
		 */
		
		JButton btnAddKeyframe = new JButton("Add Keyframe");
		GridBagConstraints gbc_btnAddKeyframe = new GridBagConstraints();
		gbc_btnAddKeyframe.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAddKeyframe.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddKeyframe.gridx = 0;
		gbc_btnAddKeyframe.gridy = 0;
		panelProperties.add(btnAddKeyframe, gbc_btnAddKeyframe);
		btnAddKeyframe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(scene != null) {
					scene.movement.add(new Keyframe());
				}
				redraw();
			}
		});
		
		/*
		 * Record Keyframe
		 */
		
		btnRecordKeyframe = new JButton("Record Keyframe");
		GridBagConstraints gbc_btnRecordKeyframe = new GridBagConstraints();
		gbc_btnRecordKeyframe.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRecordKeyframe.insets = new Insets(0, 0, 5, 5);
		gbc_btnRecordKeyframe.gridx = 0;
		gbc_btnRecordKeyframe.gridy = 1;
		panelProperties.add(btnRecordKeyframe, gbc_btnRecordKeyframe);
		btnRecordKeyframe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(scene != null && adapter != null) {
					scene.movement.add(adapter.recordKeyframe());
				}
				redraw();
			}
		});
		
		/*
		 * Separator
		 */
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.VERTICAL;
		gbc_separator.gridheight = 6;
		gbc_separator.insets = new Insets(0, 0, 0, 5);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 0;
		panelProperties.add(separator, gbc_separator);
		
		/*
		 * Previous Keyframe
		 */
		
		JButton btnPrevKF = new JButton("<");
		GridBagConstraints gbc_btnPrevKF = new GridBagConstraints();
		gbc_btnPrevKF.anchor = GridBagConstraints.EAST;
		gbc_btnPrevKF.insets = new Insets(0, 0, 5, 5);
		gbc_btnPrevKF.gridx = 2;
		gbc_btnPrevKF.gridy = 0;
		panelProperties.add(btnPrevKF, gbc_btnPrevKF);
		btnPrevKF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				keyFramePanel.setSelectedKeyframe(keyFramePanel.selectedKeyframeID - 1);
			}
		});
		
		/*
		 * Keyframe Label
		 */
		
		lblKeyframeX = new JLabel("Keyframe: X");
		GridBagConstraints gbc_lblKeyframeX = new GridBagConstraints();
		gbc_lblKeyframeX.insets = new Insets(0, 0, 5, 5);
		gbc_lblKeyframeX.gridx = 3;
		gbc_lblKeyframeX.gridy = 0;
		panelProperties.add(lblKeyframeX, gbc_lblKeyframeX);
		
		/*
		 * Next Keyframe
		 */
		
		JButton btnNextKF = new JButton(">");
		GridBagConstraints gbc_btnNextKF = new GridBagConstraints();
		gbc_btnNextKF.anchor = GridBagConstraints.WEST;
		gbc_btnNextKF.insets = new Insets(0, 0, 5, 5);
		gbc_btnNextKF.gridx = 4;
		gbc_btnNextKF.gridy = 0;
		panelProperties.add(btnNextKF, gbc_btnNextKF);
		btnNextKF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				keyFramePanel.setSelectedKeyframe(keyFramePanel.selectedKeyframeID + 1);
			}
		});
		
		/*
		 * Show Keyframe
		 */
		
		JButton btnShow = new JButton("Show");
		GridBagConstraints gbc_btnShow = new GridBagConstraints();
		gbc_btnShow.insets = new Insets(0, 0, 5, 5);
		gbc_btnShow.gridx = 5;
		gbc_btnShow.gridy = 0;
		panelProperties.add(btnShow, gbc_btnShow);
		
		JLabel lblMovement = new JLabel("Movement:");
		lblMovement.setFont(lblMovement.getFont().deriveFont(Font.BOLD));
		GridBagConstraints gbc_lblMovement = new GridBagConstraints();
		gbc_lblMovement.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblMovement.insets = new Insets(0, 0, 5, 5);
		gbc_lblMovement.gridx = 7;
		gbc_lblMovement.gridy = 0;
		panelProperties.add(lblMovement, gbc_lblMovement);
		
		/*
		 * Separator
		 */
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.fill = GridBagConstraints.VERTICAL;
		gbc_separator_1.gridheight = 5;
		gbc_separator_1.insets = new Insets(0, 0, 0, 5);
		gbc_separator_1.gridx = 6;
		gbc_separator_1.gridy = 1;
		panelProperties.add(separator_1, gbc_separator_1);
		
		/*
		 * Play Scene
		 */
		
		btnPlay = new JButton("Play");
		GridBagConstraints gbc_btnPlay = new GridBagConstraints();
		gbc_btnPlay.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPlay.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlay.gridx = 0;
		gbc_btnPlay.gridy = 3;
		panelProperties.add(btnPlay, gbc_btnPlay);
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adapter.replay(scene);
			}
		});
		
		/*
		 * X Coord
		 */
		
		spnX = new JSpinner();
		spnX.setPreferredSize(new Dimension(60, 20));
		spnX.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		GridBagConstraints gbc_spnX = new GridBagConstraints();
		gbc_spnX.anchor = GridBagConstraints.WEST;
		gbc_spnX.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnX.insets = new Insets(0, 0, 5, 5);
		gbc_spnX.gridx = 3;
		gbc_spnX.gridy = 1;
		panelProperties.add(spnX, gbc_spnX);
		createDefaultLabel(panelProperties, spnX, "X", gbc_spnX);
		spnX.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(keyFramePanel.selectedKeyframe != null) {
					double value = (Double)spnX.getValue();
					keyFramePanel.selectedKeyframe.posX = value;
					
				}
				redraw();
			}
		});
		
		/*
		 * Yaw
		 */
		
		spnYaw = new JSpinner();
		spnYaw.setPreferredSize(new Dimension(60, 20));
		spnYaw.setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(1)));
		GridBagConstraints gbc_spnYaw = new GridBagConstraints();
		gbc_spnYaw.anchor = GridBagConstraints.WEST;
		gbc_spnYaw.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnYaw.insets = new Insets(0, 0, 5, 5);
		gbc_spnYaw.gridx = 5;
		gbc_spnYaw.gridy = 1;
		panelProperties.add(spnYaw, gbc_spnYaw);
		createDefaultLabel(panelProperties, spnYaw, "Yaw", gbc_spnYaw);
		spnYaw.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(keyFramePanel.selectedKeyframe != null) {
					float value = (Float)spnYaw.getValue();
					keyFramePanel.selectedKeyframe.yaw = value;
					
				}
				redraw();
			}
		});
		
		/*
		 * Interpolation Movement
		 */
		
		spnInterpolationMovement = new JSpinner();
		spnInterpolationMovement.setModel(new SpinnerListModel(Keyframe.Interpolation.values()));
		GridBagConstraints gbc_spnInterpolationMovement = new GridBagConstraints();
		gbc_spnInterpolationMovement.anchor = GridBagConstraints.WEST;
		gbc_spnInterpolationMovement.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnInterpolationMovement.insets = new Insets(0, 0, 5, 0);
		gbc_spnInterpolationMovement.gridx = 8;
		gbc_spnInterpolationMovement.gridy = 1;
		panelProperties.add(spnInterpolationMovement, gbc_spnInterpolationMovement);
		createDefaultLabel(panelProperties, spnInterpolationMovement, "Interpolation", gbc_spnInterpolationMovement);
		spnInterpolationMovement.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(keyFramePanel.selectedKeyframe != null) {
					Keyframe.Interpolation value = (Keyframe.Interpolation)spnInterpolationMovement.getValue();
					keyFramePanel.selectedKeyframe.interpolationMovement = value;
				}
				redraw();
			}
		});
		
		/*
		 * Y Coord
		 */
		
		spnY = new JSpinner();
		spnY.setPreferredSize(new Dimension(60, 20));
		spnY.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		GridBagConstraints gbc_spnY = new GridBagConstraints();
		gbc_spnY.anchor = GridBagConstraints.WEST;
		gbc_spnY.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnY.insets = new Insets(0, 0, 5, 5);
		gbc_spnY.gridx = 3;
		gbc_spnY.gridy = 2;
		panelProperties.add(spnY, gbc_spnY);
		createDefaultLabel(panelProperties, spnY, "Y", gbc_spnY);
		spnY.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(keyFramePanel.selectedKeyframe != null) {
					double value = (Double)spnY.getValue();
					keyFramePanel.selectedKeyframe.posY = value;
					
				}
				redraw();
			}
		});
		
		/*
		 * Pitch
		 */
		
		spnPitch = new JSpinner();
		spnPitch.setPreferredSize(new Dimension(60, 20));
		spnPitch.setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(1)));
		GridBagConstraints gbc_spnPitch = new GridBagConstraints();
		gbc_spnPitch.anchor = GridBagConstraints.WEST;
		gbc_spnPitch.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnPitch.insets = new Insets(0, 0, 5, 5);
		gbc_spnPitch.gridx = 5;
		gbc_spnPitch.gridy = 2;
		panelProperties.add(spnPitch, gbc_spnPitch);
		createDefaultLabel(panelProperties, spnPitch, "Pitch", gbc_spnPitch);
		spnPitch.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(keyFramePanel.selectedKeyframe != null) {
					float value = (Float)spnPitch.getValue();
					keyFramePanel.selectedKeyframe.pitch = value;
					
				}
				redraw();
			}
		});
		
		/*
		 * Timing Movement
		 */
		
		spnTimingMovement = new JSpinner();
		spnTimingMovement.setModel(new SpinnerListModel(Keyframe.Timing.values()));
		GridBagConstraints gbc_spnTimingMovement = new GridBagConstraints();
		gbc_spnTimingMovement.anchor = GridBagConstraints.WEST;
		gbc_spnTimingMovement.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnTimingMovement.insets = new Insets(0, 0, 5, 0);
		gbc_spnTimingMovement.gridx = 8;
		gbc_spnTimingMovement.gridy = 2;
		panelProperties.add(spnTimingMovement, gbc_spnTimingMovement);
		createDefaultLabel(panelProperties, spnTimingMovement, "Timing", gbc_spnTimingMovement);
		spnTimingMovement.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(keyFramePanel.selectedKeyframe != null) {
					Keyframe.Timing value = (Keyframe.Timing)spnTimingMovement.getValue();
					keyFramePanel.selectedKeyframe.timingMovement = value;
				}
				redraw();
			}
		});
		
		
		/*
		 * Z Coord
		 */
		
		spnZ = new JSpinner();
		spnZ.setPreferredSize(new Dimension(60, 20));
		spnZ.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		GridBagConstraints gbc_spnZ = new GridBagConstraints();
		gbc_spnZ.anchor = GridBagConstraints.WEST;
		gbc_spnZ.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnZ.insets = new Insets(0, 0, 5, 5);
		gbc_spnZ.gridx = 3;
		gbc_spnZ.gridy = 3;
		panelProperties.add(spnZ, gbc_spnZ);
		createDefaultLabel(panelProperties, spnZ, "Z", gbc_spnZ);
		spnZ.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(keyFramePanel.selectedKeyframe != null) {
					double value = (Double)spnZ.getValue();
					keyFramePanel.selectedKeyframe.posZ = value;
					
				}
				redraw();
			}
		});
		
		/*
		 * Rotation
		 */
		
		JLabel lblRotation = new JLabel("Rotation:");
		lblRotation.setFont(lblRotation.getFont().deriveFont(Font.BOLD));
		GridBagConstraints gbc_lblRotation = new GridBagConstraints();
		gbc_lblRotation.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblRotation.insets = new Insets(0, 0, 5, 5);
		gbc_lblRotation.gridx = 7;
		gbc_lblRotation.gridy = 3;
		panelProperties.add(lblRotation, gbc_lblRotation);
		
		/*
		 * Delete Keyframe
		 */
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(keyFramePanel.selectedKeyframeID != -1) {
					scene.movement.remove(keyFramePanel.selectedKeyframeID);
					keyFramePanel.setSelectedKeyframe(-1);
				}
				redraw();
			}
		});
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDelete.gridwidth = 2;
		gbc_btnDelete.insets = new Insets(0, 0, 5, 5);
		gbc_btnDelete.gridx = 2;
		gbc_btnDelete.gridy = 4;
		panelProperties.add(btnDelete, gbc_btnDelete);
		
		/*
		 * Time
		 */
		
		spnTime = new JSpinner();
		spnTime.setPreferredSize(new Dimension(60, 20));
		GridBagConstraints gbc_spnTime = new GridBagConstraints();
		gbc_spnTime.anchor = GridBagConstraints.WEST;
		gbc_spnTime.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnTime.insets = new Insets(0, 0, 5, 5);
		gbc_spnTime.gridx = 5;
		gbc_spnTime.gridy = 4;
		panelProperties.add(spnTime, gbc_spnTime);
		createDefaultLabel(panelProperties, spnTime, "Time", gbc_spnTime);
		spnTime.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(keyFramePanel.selectedKeyframe != null) {
					int value = (Integer)spnTime.getValue();
					if(value < 1) {
						value = 1;
					}
					keyFramePanel.selectedKeyframe.time = value;
					
				}
				redraw();
			}
		});

		/*
		 * Interpolation Rotation
		 */
		
		spnInterpolationRotation = new JSpinner();
		spnInterpolationRotation.setModel(new SpinnerListModel(Keyframe.Interpolation.values()));
		GridBagConstraints gbc_spnInterpolationRotation = new GridBagConstraints();
		gbc_spnInterpolationRotation.anchor = GridBagConstraints.WEST;
		gbc_spnInterpolationRotation.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnInterpolationRotation.insets = new Insets(0, 0, 5, 0);
		gbc_spnInterpolationRotation.gridx = 8;
		gbc_spnInterpolationRotation.gridy = 4;
		panelProperties.add(spnInterpolationRotation, gbc_spnInterpolationRotation);
		createDefaultLabel(panelProperties, spnInterpolationRotation, "Interpolation", gbc_spnInterpolationRotation);
		spnInterpolationRotation.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(keyFramePanel.selectedKeyframe != null) {
					Keyframe.Interpolation value = (Keyframe.Interpolation)spnInterpolationRotation.getValue();
					keyFramePanel.selectedKeyframe.interpolationRotation= value;
				}
				redraw();
			}
		});
		
		/*
		 * Duplicate Keyframe
		 */
		
		btnDuplicate = new JButton("Duplicate");
		GridBagConstraints gbc_btnDuplicate = new GridBagConstraints();
		gbc_btnDuplicate.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDuplicate.gridwidth = 2;
		gbc_btnDuplicate.insets = new Insets(0, 0, 0, 5);
		gbc_btnDuplicate.gridx = 2;
		gbc_btnDuplicate.gridy = 5;
		panelProperties.add(btnDuplicate, gbc_btnDuplicate);
		btnDuplicate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(keyFramePanel.selectedKeyframeID != -1) {
					Keyframe clone = new Keyframe(keyFramePanel.selectedKeyframe);
					scene.movement.add(keyFramePanel.selectedKeyframeID + 1, clone);
				}
				redraw();
			}
		});
		
		/*
		 * Clear Scene
		 */
		
		btnClearScene = new JButton("Clear Scene");
		GridBagConstraints gbc_btnClearScene = new GridBagConstraints();
		gbc_btnClearScene.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClearScene.insets = new Insets(0, 0, 0, 5);
		gbc_btnClearScene.gridx = 0;
		gbc_btnClearScene.gridy = 5;
		panelProperties.add(btnClearScene, gbc_btnClearScene);
		btnClearScene.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showOptionDialog(frame, "This will delete ALL keyframes in this scene!", "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[] { "Confirm Deletion", "No! Don't!!" }, "No! Don't!!");
				if(result == 0) {
					scene.movement.clear();
				}
				redraw();
			}
		});
		
		/*
		 * Timing Rotation
		 */
		
		spnTimingRotation = new JSpinner();
		spnTimingRotation.setModel(new SpinnerListModel(Keyframe.Timing.values()));
		GridBagConstraints gbc_spnTimingRotation = new GridBagConstraints();
		gbc_spnTimingRotation.anchor = GridBagConstraints.WEST;
		gbc_spnTimingRotation.fill = GridBagConstraints.HORIZONTAL;
		gbc_spnTimingRotation.gridx = 8;
		gbc_spnTimingRotation.gridy = 5;
		panelProperties.add(spnTimingRotation, gbc_spnTimingRotation);
		createDefaultLabel(panelProperties, spnTimingRotation, "Timing", gbc_spnTimingRotation);
		spnTimingRotation.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(keyFramePanel.selectedKeyframe != null) {
					Keyframe.Timing value = (Keyframe.Timing)spnTimingRotation.getValue();
					keyFramePanel.selectedKeyframe.timingRotation = value;
				}
				redraw();
			}
		});
	}

	public void dispose() {
		frame.dispose();
	}
	
	public void redraw() {
		keyFramePanel.scene = scene;
		keyFramePanel.revalidate();
		keyFramePanel.repaint();
		System.out.println(keyFramePanel.selectedKeyframeID);
		if(keyFramePanel.selectedKeyframeID == -1) {
			spnInterpolationMovement.setEnabled(false);
			spnInterpolationRotation.setEnabled(false);
			spnTimingMovement.setEnabled(false);
			spnTimingRotation.setEnabled(false);
			spnX.setEnabled(false);
			spnY.setEnabled(false);
			spnZ.setEnabled(false);
			spnPitch.setEnabled(false);
			spnYaw.setEnabled(false);
			spnTime.setEnabled(false);
			btnDelete.setEnabled(false);
			btnDuplicate.setEnabled(false);
			
			lblKeyframeX.setText("No Keyframe Selected");
		} else {
			lblKeyframeX.setText("Keyframe: " + keyFramePanel.selectedKeyframeID);
			
			spnInterpolationMovement.setValue(keyFramePanel.selectedKeyframe.interpolationMovement);
			spnInterpolationRotation.setValue(keyFramePanel.selectedKeyframe.interpolationRotation);
			spnTimingMovement.setValue(keyFramePanel.selectedKeyframe.timingMovement);
			spnTimingRotation.setValue(keyFramePanel.selectedKeyframe.timingRotation);
			spnX.setValue(keyFramePanel.selectedKeyframe.posX);
			spnY.setValue(keyFramePanel.selectedKeyframe.posY);
			spnZ.setValue(keyFramePanel.selectedKeyframe.posZ);
			spnPitch.setValue(keyFramePanel.selectedKeyframe.pitch);
			spnYaw.setValue(keyFramePanel.selectedKeyframe.yaw);
			spnTime.setValue(keyFramePanel.selectedKeyframe.time);
			
			spnInterpolationMovement.setEnabled(true);
			spnInterpolationRotation.setEnabled(true);
			spnTimingMovement.setEnabled(true);
			spnTimingRotation.setEnabled(true);
			spnX.setEnabled(true);
			spnY.setEnabled(true);
			spnZ.setEnabled(true);
			spnPitch.setEnabled(true);
			spnYaw.setEnabled(true);
			spnTime.setEnabled(true);
			btnDelete.setEnabled(true);
			btnDuplicate.setEnabled(true);
		}
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
