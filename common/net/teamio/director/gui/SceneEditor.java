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
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import net.teamio.director.act.IMinecraftAdapter;
import net.teamio.director.cut.Keyframe;
import net.teamio.director.cut.Scene;

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
		gbc.insets = constraints.insets;
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
		
		JButton btnAddKeyframe = new JButton("Add Keyframe");
		btnAddKeyframe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(scene != null) {
					scene.movement.add(new Keyframe());
				}
			}
		});
		GridBagConstraints gbc_btnAddKeyframe = new GridBagConstraints();
		gbc_btnAddKeyframe.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAddKeyframe.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddKeyframe.gridx = 0;
		gbc_btnAddKeyframe.gridy = 0;
		panelProperties.add(btnAddKeyframe, gbc_btnAddKeyframe);
		
		btnRecordKeyframe = new JButton("Record Keyframe");
		btnRecordKeyframe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(scene != null && adapter != null) {
					scene.movement.add(adapter.recordKeyframe());
				}
			}
		});
		GridBagConstraints gbc_btnRecordKeyframe = new GridBagConstraints();
		gbc_btnRecordKeyframe.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRecordKeyframe.insets = new Insets(0, 0, 5, 5);
		gbc_btnRecordKeyframe.gridx = 0;
		gbc_btnRecordKeyframe.gridy = 1;
		panelProperties.add(btnRecordKeyframe, gbc_btnRecordKeyframe);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.fill = GridBagConstraints.VERTICAL;
		gbc_separator.gridheight = 4;
		gbc_separator.insets = new Insets(0, 0, 5, 5);
		gbc_separator.gridx = 1;
		gbc_separator.gridy = 0;
		panelProperties.add(separator, gbc_separator);
		
		JButton btnPrevKF = new JButton("<");
		GridBagConstraints gbc_btnPrevKF = new GridBagConstraints();
		gbc_btnPrevKF.anchor = GridBagConstraints.EAST;
		gbc_btnPrevKF.insets = new Insets(0, 0, 5, 5);
		gbc_btnPrevKF.gridx = 2;
		gbc_btnPrevKF.gridy = 0;
		panelProperties.add(btnPrevKF, gbc_btnPrevKF);
		
		lblKeyframeX = new JLabel("Keyframe: X");
		GridBagConstraints gbc_lblKeyframeX = new GridBagConstraints();
		gbc_lblKeyframeX.insets = new Insets(0, 0, 5, 5);
		gbc_lblKeyframeX.gridx = 3;
		gbc_lblKeyframeX.gridy = 0;
		panelProperties.add(lblKeyframeX, gbc_lblKeyframeX);
		
		JButton btnNextKF = new JButton(">");
		GridBagConstraints gbc_btnNextKF = new GridBagConstraints();
		gbc_btnNextKF.anchor = GridBagConstraints.WEST;
		gbc_btnNextKF.insets = new Insets(0, 0, 5, 5);
		gbc_btnNextKF.gridx = 4;
		gbc_btnNextKF.gridy = 0;
		panelProperties.add(btnNextKF, gbc_btnNextKF);
		
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
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.fill = GridBagConstraints.VERTICAL;
		gbc_separator_1.gridheight = 5;
		gbc_separator_1.insets = new Insets(0, 0, 0, 5);
		gbc_separator_1.gridx = 6;
		gbc_separator_1.gridy = 1;
		panelProperties.add(separator_1, gbc_separator_1);
		
		btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adapter.replay(scene);
			}
		});
		GridBagConstraints gbc_btnPlay = new GridBagConstraints();
		gbc_btnPlay.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPlay.insets = new Insets(0, 0, 5, 5);
		gbc_btnPlay.gridx = 0;
		gbc_btnPlay.gridy = 3;
		panelProperties.add(btnPlay, gbc_btnPlay);
		
		spnX = new JSpinner();
		spnX.setPreferredSize(new Dimension(60, 20));
		spnX.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		GridBagConstraints gbc_spnX = new GridBagConstraints();
		gbc_spnX.anchor = GridBagConstraints.WEST;
		gbc_spnX.insets = new Insets(0, 0, 5, 5);
		gbc_spnX.gridx = 3;
		gbc_spnX.gridy = 1;
		panelProperties.add(spnX, gbc_spnX);
		
		createDefaultLabel(panelProperties, spnX, "X", gbc_spnX);
		
		spnYaw = new JSpinner();
		spnYaw.setPreferredSize(new Dimension(60, 20));
		spnYaw.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		GridBagConstraints gbc_spnYaw = new GridBagConstraints();
		gbc_spnYaw.anchor = GridBagConstraints.WEST;
		gbc_spnYaw.insets = new Insets(0, 0, 5, 5);
		gbc_spnYaw.gridx = 5;
		gbc_spnYaw.gridy = 1;
		panelProperties.add(spnYaw, gbc_spnYaw);
		
		createDefaultLabel(panelProperties, spnYaw, "Yaw", gbc_spnYaw);
		
		spnInterpolationMovement = new JSpinner();
		spnInterpolationMovement.setModel(new SpinnerListModel(Keyframe.Interpolation.values()));
		GridBagConstraints gbc_spnInterpolationMovement = new GridBagConstraints();
		gbc_spnInterpolationMovement.anchor = GridBagConstraints.WEST;
		gbc_spnInterpolationMovement.insets = new Insets(0, 0, 5, 0);
		gbc_spnInterpolationMovement.gridx = 8;
		gbc_spnInterpolationMovement.gridy = 1;
		panelProperties.add(spnInterpolationMovement, gbc_spnInterpolationMovement);
		
		createDefaultLabel(panelProperties, spnInterpolationMovement, "Interpolation", gbc_spnInterpolationMovement);
		
		spnY = new JSpinner();
		spnY.setPreferredSize(new Dimension(60, 20));
		spnY.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		GridBagConstraints gbc_spnY = new GridBagConstraints();
		gbc_spnY.anchor = GridBagConstraints.WEST;
		gbc_spnY.insets = new Insets(0, 0, 5, 5);
		gbc_spnY.gridx = 3;
		gbc_spnY.gridy = 2;
		panelProperties.add(spnY, gbc_spnY);
		
		createDefaultLabel(panelProperties, spnY, "Y", gbc_spnY);
		
		spnPitch = new JSpinner();
		spnPitch.setPreferredSize(new Dimension(60, 20));
		spnPitch.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		GridBagConstraints gbc_spnPitch = new GridBagConstraints();
		gbc_spnPitch.anchor = GridBagConstraints.WEST;
		gbc_spnPitch.insets = new Insets(0, 0, 5, 5);
		gbc_spnPitch.gridx = 5;
		gbc_spnPitch.gridy = 2;
		panelProperties.add(spnPitch, gbc_spnPitch);
		
		createDefaultLabel(panelProperties, spnPitch, "Pitch", gbc_spnPitch);
		
		spnTimingMovement = new JSpinner();
		spnTimingMovement.setModel(new SpinnerListModel(Keyframe.Timing.values()));
		GridBagConstraints gbc_spnTimingMovement = new GridBagConstraints();
		gbc_spnTimingMovement.anchor = GridBagConstraints.WEST;
		gbc_spnTimingMovement.insets = new Insets(0, 0, 5, 0);
		gbc_spnTimingMovement.gridx = 8;
		gbc_spnTimingMovement.gridy = 2;
		panelProperties.add(spnTimingMovement, gbc_spnTimingMovement);
		
		createDefaultLabel(panelProperties, spnTimingMovement, "Timing", gbc_spnTimingMovement);
		
		
		spnZ = new JSpinner();
		spnZ.setPreferredSize(new Dimension(60, 20));
		spnZ.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		GridBagConstraints gbc_spnZ = new GridBagConstraints();
		gbc_spnZ.anchor = GridBagConstraints.WEST;
		gbc_spnZ.insets = new Insets(0, 0, 5, 5);
		gbc_spnZ.gridx = 3;
		gbc_spnZ.gridy = 3;
		panelProperties.add(spnZ, gbc_spnZ);
		createDefaultLabel(panelProperties, spnZ, "Z", gbc_spnZ);
		
		JLabel lblRotation = new JLabel("Rotation:");
		lblRotation.setFont(lblRotation.getFont().deriveFont(Font.BOLD));
		GridBagConstraints gbc_lblRotation = new GridBagConstraints();
		gbc_lblRotation.anchor = GridBagConstraints.SOUTHEAST;
		gbc_lblRotation.insets = new Insets(0, 0, 5, 5);
		gbc_lblRotation.gridx = 7;
		gbc_lblRotation.gridy = 3;
		panelProperties.add(lblRotation, gbc_lblRotation);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(keyFramePanel.selectedKeyframeID != -1) {
					scene.movement.remove(keyFramePanel.selectedKeyframeID);
					keyFramePanel.selectedKeyframeID = -1;
					keyFramePanel.selectedKeyframe = null;
				}
				redraw();
			}
		});
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.gridwidth = 2;
		gbc_btnDelete.anchor = GridBagConstraints.WEST;
		gbc_btnDelete.insets = new Insets(0, 0, 5, 5);
		gbc_btnDelete.gridx = 2;
		gbc_btnDelete.gridy = 4;
		panelProperties.add(btnDelete, gbc_btnDelete);
		
		
		spnTime = new JSpinner();
		spnTime.setPreferredSize(new Dimension(60, 20));
		GridBagConstraints gbc_spnTime = new GridBagConstraints();
		gbc_spnTime.anchor = GridBagConstraints.WEST;
		gbc_spnTime.insets = new Insets(0, 0, 5, 5);
		gbc_spnTime.gridx = 5;
		gbc_spnTime.gridy = 4;
		panelProperties.add(spnTime, gbc_spnTime);
		createDefaultLabel(panelProperties, spnTime, "Time", gbc_spnTime);

		spnInterpolationRotation = new JSpinner();
		spnInterpolationRotation.setModel(new SpinnerListModel(Keyframe.Interpolation.values()));
		GridBagConstraints gbc_spnInterpolationRotation = new GridBagConstraints();
		gbc_spnInterpolationRotation.anchor = GridBagConstraints.WEST;
		gbc_spnInterpolationRotation.insets = new Insets(0, 0, 5, 0);
		gbc_spnInterpolationRotation.gridx = 8;
		gbc_spnInterpolationRotation.gridy = 4;
		panelProperties.add(spnInterpolationRotation, gbc_spnInterpolationRotation);
		createDefaultLabel(panelProperties, spnInterpolationRotation, "Time", gbc_spnInterpolationRotation);
		
		btnDuplicate = new JButton("Duplicate");
		btnDuplicate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(keyFramePanel.selectedKeyframeID != -1) {
					Keyframe clone = new Keyframe(keyFramePanel.selectedKeyframe);
					scene.movement.add(keyFramePanel.selectedKeyframeID + 1, clone);
				}
				redraw();
			}
		});
		GridBagConstraints gbc_btnDuplicate = new GridBagConstraints();
		gbc_btnDuplicate.anchor = GridBagConstraints.WEST;
		gbc_btnDuplicate.gridwidth = 2;
		gbc_btnDuplicate.insets = new Insets(0, 0, 0, 5);
		gbc_btnDuplicate.gridx = 2;
		gbc_btnDuplicate.gridy = 5;
		panelProperties.add(btnDuplicate, gbc_btnDuplicate);
		
		spnTimingRotation = new JSpinner();
		spnTimingRotation.setModel(new SpinnerListModel(Keyframe.Timing.values()));
		GridBagConstraints gbc_spnTimingRotation = new GridBagConstraints();
		gbc_spnTimingRotation.anchor = GridBagConstraints.WEST;
		gbc_spnTimingRotation.gridx = 8;
		gbc_spnTimingRotation.gridy = 5;
		panelProperties.add(spnTimingRotation, gbc_spnTimingRotation);
		createDefaultLabel(panelProperties, spnTimingRotation, "Timing", gbc_spnTimingRotation);
	}

	public void dispose() {
		frame.dispose();
	}
	
	public void redraw() {
		keyFramePanel.scene = scene;
		keyFramePanel.repaint();
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
