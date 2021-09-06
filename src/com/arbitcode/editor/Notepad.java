package com.arbitcode.editor;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class Notepad extends JFrame implements ActionListener, WindowListener {

	JTextArea jta = new JTextArea();
	File fnameContainer;

	public Notepad() {
		System.out.println("Cunstructor called");
		Font fnt = new Font("Arial", Font.PLAIN, 15);
		Container con = getContentPane();
		JMenuBar jmb = new JMenuBar();
		JMenu jmfile = new JMenu("File");
		JMenu jmedit = new JMenu("Edit");
		JMenu jmhelp = new JMenu("Help");

		con.setLayout(new BorderLayout());

		JScrollPane sbrText = new JScrollPane(jta);
		sbrText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sbrText.setVisible(true);

		jta.setFont(fnt);
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);

		con.add(sbrText);

		createMenuItem(jmfile, "New");
		createMenuItem(jmfile, "Open");
		createMenuItem(jmfile, "Save");
		createMenuItem(jmfile, "Save as");
		jmfile.addSeparator();
		createMenuItem(jmfile, "Exit");

		createMenuItem(jmedit, "Cut");
		createMenuItem(jmedit, "Copy");
		createMenuItem(jmedit, "Paste");

		createMenuItem(jmhelp, "About Notepad");

		jmb.add(jmfile);
		jmb.add(jmedit);
		jmb.add(jmhelp);

		setJMenuBar(jmb);

		setIconImage(Toolkit.getDefaultToolkit().getImage("notepad.gif"));
		addWindowListener(this);
		setSize(500, 500);
		setTitle("Untitled.txt - NotePad");
		System.out.println("App launched");
		setVisible(true);
	}

	private void createMenuItem(JMenu jm, String txt) {

		JMenuItem jmi = new JMenuItem(txt);
		jmi.addActionListener(this);
		jm.add(jmi);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JFileChooser jfc = new JFileChooser();
		if (e.getActionCommand().equals("New")) {
			this.setTitle("Untitled.txt - Notepad");
			jta.setText("");
			fnameContainer = null;

		} else if (e.getActionCommand().equals("Open")) {
			int ret = jfc.showDialog(null, "Open");
			if (ret == JFileChooser.APPROVE_OPTION) {
				File fyl = jfc.getSelectedFile();
				OpenFile(fyl.getAbsolutePath());
				this.setTitle(fyl.getName() + " - Notepad");
				fnameContainer = fyl;
			}

		} else if (e.getActionCommand().equals("Save")) {
			if (fnameContainer != null) {
				jfc.setCurrentDirectory(fnameContainer);
				jfc.setSelectedFile(fnameContainer);
			} else {
				jfc.setSelectedFile(new File("Untitled.txt"));
			}
			int ret = jfc.showSaveDialog(null);
			if (ret == JFileChooser.APPROVE_OPTION) {
				File fyl = jfc.getSelectedFile();
				try {
					SaveFile(fyl.getAbsolutePath());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				this.setTitle(fyl.getName() + " - Notepad");
				fnameContainer = fyl;

			}
		} else if (e.getActionCommand().equals("Exit")) {
			Exiting();
		} else if (e.getActionCommand().equals("Copy")) {
			jta.copy();
		} else if (e.getActionCommand().equals("Paste")) {
			jta.paste();
		} else if (e.getActionCommand().equals("About Notepad")) {
			JOptionPane.showMessageDialog(this, "Created by : ArbitCode", "Notepad", JOptionPane.INFORMATION_MESSAGE);
		} else if (e.getActionCommand().equals("Cut")) {
			jta.cut();
		}

	}

	private void Exiting() {
		// TODO Auto-generated method stub
		System.exit(0);

	}

	private void SaveFile(String fname) throws IOException {
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		DataOutputStream o = new DataOutputStream(new FileOutputStream(fname));
		o.writeBytes(jta.getText());
		o.close();
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

	}

	private void OpenFile(String fname) {

		try {
			BufferedReader d = new BufferedReader(new InputStreamReader(new FileInputStream(fname)));
			String l;
			jta.setText("");
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			while ((l = d.readLine()) != null) {
				jta.setText(jta.getText() + l + "\r\n");
			}
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			d.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void windowActivated(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(final WindowEvent e) {
		// TODO Auto-generated method stub
		Exiting();

	}

	@Override
	public void windowDeactivated(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
