

package org.gjt.sp.jedit.options;


import javax.swing.border.*;
import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import org.gjt.sp.jedit.*;
import org.gjt.sp.jedit.bufferset.BufferSet;


public class ViewOptionPane extends AbstractOptionPane
{
    
	public ViewOptionPane()
	{
		super("view");
	} 

	
	@Override
	protected void _init()
	{
		
		layoutIcon1 = GUIUtilities.loadIcon("dock_layout1.png");
		layoutIcon2 = GUIUtilities.loadIcon("dock_layout2.png");
		layoutIcon3 = GUIUtilities.loadIcon("dock_layout3.png");
		layoutIcon4 = GUIUtilities.loadIcon("dock_layout4.png");

		JPanel layoutPanel = new JPanel(new BorderLayout(12,12));

		if(jEdit.getBooleanProperty("view.docking.alternateLayout"))
		{
			layout = new JLabel(jEdit.getBooleanProperty(
				"view.toolbar.alternateLayout")
				? layoutIcon4 : layoutIcon2);
		}
		else
		{
			layout = new JLabel(jEdit.getBooleanProperty(
				"view.toolbar.alternateLayout")
				? layoutIcon3 : layoutIcon1);
		}

		layout.setBorder(new EmptyBorder(12,12,12,12));
		layoutPanel.add(BorderLayout.CENTER,layout);

		JPanel buttons = new JPanel(new GridLayout(2,1,12,12));
		buttons.setBorder(new EmptyBorder(0,12,12,12));
		buttons.add(alternateDockingLayout = new JButton(jEdit.getProperty(
			"options.view.alternateDockingLayout")));
		ActionHandler actionHandler = new ActionHandler();
		alternateDockingLayout.addActionListener(actionHandler);
		buttons.add(alternateToolBarLayout = new JButton(jEdit.getProperty(
			"options.view.alternateToolBarLayout")));
		alternateToolBarLayout.addActionListener(actionHandler);
		layoutPanel.add(BorderLayout.SOUTH,buttons);

		TitledBorder border = new TitledBorder(jEdit.getProperty(
			"options.view.viewLayout"));
		border.setTitleJustification(TitledBorder.CENTER);
		layoutPanel.setBorder(border);

		addComponent(layoutPanel);

		
		showFullPath = new JCheckBox(jEdit.getProperty(
			"options.view.showFullPath"));
		showFullPath.setSelected(jEdit.getBooleanProperty(
			"view.showFullPath"));
		addComponent(showFullPath);

		
		showSearchbar = new JCheckBox(jEdit.getProperty(
			"options.view.showSearchbar"));
		showSearchbar.setSelected(jEdit.getBooleanProperty(
			"view.showSearchbar"));
		addComponent(showSearchbar);

		
		beepOnSearchAutoWrap = new JCheckBox(jEdit.getProperty(
			"options.view.beepOnSearchAutoWrap"));
		beepOnSearchAutoWrap.setSelected(jEdit.getBooleanProperty(
			"search.beepOnSearchAutoWrap"));
		addComponent(beepOnSearchAutoWrap);

		
		showBufferSwitcher = new JCheckBox(jEdit.getProperty(
			"options.view.showBufferSwitcher"));

		showBufferSwitcher.setSelected(jEdit.getBooleanProperty(
			"view.showBufferSwitcher"));
		addComponent(showBufferSwitcher);
		showBufferSwitcher.addActionListener(actionHandler);


		
		bufferSwitcherMaxRowCount = new JTextField(jEdit.getProperty("bufferSwitcher.maxRowCount"));
		addComponent(jEdit.getProperty("options.view.bufferSwitcherMaxRowsCount"),
			bufferSwitcherMaxRowCount);
		bufferSwitcherMaxRowCount.setEditable(showBufferSwitcher.isSelected());

		buffersetScope = new JComboBox();
		buffersetScope.addItem(BufferSet.Scope.global);
		buffersetScope.addItem(BufferSet.Scope.view);
		buffersetScope.addItem(BufferSet.Scope.editpane);
		buffersetScope.setSelectedItem(BufferSet.Scope.fromString(jEdit.getProperty("bufferset.scope")));
		addComponent(jEdit.getProperty("options.bufferset.scope"), buffersetScope);

		
		sortBuffers = new JCheckBox(jEdit.getProperty(
			"options.view.sortBuffers"));
		sortBuffers.setSelected(jEdit.getBooleanProperty("sortBuffers"));
		sortBuffers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				sortByName.setEnabled(sortBuffers.isSelected());
			}
		});

		addComponent(sortBuffers);

		
		sortByName = new JCheckBox(jEdit.getProperty(
			"options.view.sortByName"));
		sortByName.setSelected(jEdit.getBooleanProperty("sortByName"));
		sortByName.setEnabled(sortBuffers.isSelected());
		addComponent(sortByName);

		fullScreenIncludesMenu = new JCheckBox(jEdit.getProperty(
			"options.view.fullScreenIncludesMenu"));
		fullScreenIncludesMenu.setSelected(
			jEdit.getBooleanProperty("fullScreenIncludesMenu"));
		addComponent(fullScreenIncludesMenu);

		fullScreenIncludesToolbar = new JCheckBox(jEdit.getProperty(
			"options.view.fullScreenIncludesToolbar"));
		fullScreenIncludesToolbar.setSelected(
			jEdit.getBooleanProperty("fullScreenIncludesToolbar"));
		addComponent(fullScreenIncludesToolbar);

		fullScreenIncludesStatus = new JCheckBox(jEdit.getProperty(
				"options.view.fullScreenIncludesStatus"));
		fullScreenIncludesStatus.setSelected(
				jEdit.getBooleanProperty("fullScreenIncludesStatus"));
		addComponent(fullScreenIncludesStatus);

	} 

	
	@Override
	protected void _save()
	{
		jEdit.setBooleanProperty("view.docking.alternateLayout",
			layout.getIcon() == layoutIcon2
			|| layout.getIcon() == layoutIcon4);
		jEdit.setBooleanProperty("view.toolbar.alternateLayout",
			layout.getIcon() == layoutIcon3
			|| layout.getIcon() == layoutIcon4);
		jEdit.setBooleanProperty("view.showFullPath",showFullPath
			.isSelected());
		jEdit.setBooleanProperty("view.showSearchbar",showSearchbar
			.isSelected());
		jEdit.setBooleanProperty("search.beepOnSearchAutoWrap",beepOnSearchAutoWrap
			.isSelected());
		jEdit.setBooleanProperty("view.showBufferSwitcher",
			showBufferSwitcher.isSelected());
		jEdit.setProperty("bufferSwitcher.maxRowCount",
			bufferSwitcherMaxRowCount.getText());
		jEdit.setProperty("bufferset.scope", buffersetScope.getSelectedItem().toString());
		jEdit.setBooleanProperty("sortBuffers",sortBuffers.isSelected());
		jEdit.setBooleanProperty("sortByName",sortByName.isSelected());
		jEdit.setBooleanProperty("fullScreenIncludesMenu",fullScreenIncludesMenu.isSelected());
		jEdit.setBooleanProperty("fullScreenIncludesToolbar",fullScreenIncludesToolbar.isSelected());
		jEdit.setBooleanProperty("fullScreenIncludesStatus",fullScreenIncludesStatus.isSelected());

	} 

	
	private JLabel layout;
	private Icon layoutIcon1, layoutIcon2, layoutIcon3, layoutIcon4;
	private JButton alternateDockingLayout, alternateToolBarLayout;
	private JCheckBox showFullPath;
	private JCheckBox showSearchbar;
	private JCheckBox beepOnSearchAutoWrap;
	private JCheckBox showBufferSwitcher;
	private JTextField bufferSwitcherMaxRowCount;
	private JComboBox buffersetScope;
	private JCheckBox sortBuffers;
	private JCheckBox sortByName;
	private JCheckBox fullScreenIncludesMenu;
	private JCheckBox fullScreenIncludesToolbar;
	private JCheckBox fullScreenIncludesStatus;

	

	
	private class ActionHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			if(evt.getSource() == alternateDockingLayout)
			{
				if(layout.getIcon() == layoutIcon1)
					layout.setIcon(layoutIcon2);
				else if(layout.getIcon() == layoutIcon2)
					layout.setIcon(layoutIcon1);
				else if(layout.getIcon() == layoutIcon3)
					layout.setIcon(layoutIcon4);
				else if(layout.getIcon() == layoutIcon4)
					layout.setIcon(layoutIcon3);
			}
			else if(evt.getSource() == alternateToolBarLayout)
			{
				if(layout.getIcon() == layoutIcon1)
					layout.setIcon(layoutIcon3);
				else if(layout.getIcon() == layoutIcon3)
					layout.setIcon(layoutIcon1);
				else if(layout.getIcon() == layoutIcon2)
					layout.setIcon(layoutIcon4);
				else if(layout.getIcon() == layoutIcon4)
					layout.setIcon(layoutIcon2);
			}
			else if (evt.getSource() == showBufferSwitcher)
			{
				bufferSwitcherMaxRowCount.setEditable(showBufferSwitcher.isSelected());
			}
		}
	} 
}