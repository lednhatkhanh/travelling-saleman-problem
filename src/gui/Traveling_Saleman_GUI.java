package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import code.*;

public class Traveling_Saleman_GUI extends JFrame implements ActionListener{
	private JButton btnThemDuLieu, btnXuly, btnOk, btnReset;
	private JPanel panel1;
	int n;
	private JTable tblTraveling;
	private JTextField txtKm, txtN, txtKetQua;
	private JComboBox cbFrom, cbTo;
	private JLabel lblSKm,lblNhapN;
	private JScrollPane scrollPane;
	
	
	/**
	 * Create the application.
	 */
	public Traveling_Saleman_GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	
		setTitle("Traveling saleman problem");
		setBounds(100, 100, 794, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setResizable(true);
		setVisible(true);
		
		panel1 = new JPanel();
		panel1.setBounds(22, 11, 728, 161);
		getContentPane().add(panel1);
		panel1.setLayout(null);
		
		lblNhapN = new JLabel("Nhập số địa điểm: ");
		lblNhapN.setBounds(291, 19, 161, 21);
		panel1.add(lblNhapN);
		
		txtN = new JTextField();
		txtN.setBounds(462, 19, 86, 20);
		txtN.setText("0");
		panel1.add(txtN);
		txtN.setColumns(10);
		
		btnOk = new JButton("Nhập");
		btnOk.setBounds(579, 18, 89, 23);
		btnOk.addActionListener(this);
		panel1.add(btnOk);
		
		btnXuly = new JButton("Xử lý");
		btnXuly.addActionListener(this);
		btnXuly.setEnabled(false);
		btnXuly.setBounds(435, 127, 134, 23);
		panel1.add(btnXuly);
		
		JLabel lblimn = new JLabel("Đi từ:");
		lblimn.setBounds(28, 15, 73, 28);
		panel1.add(lblimn);
		
		cbFrom = new JComboBox();
		cbFrom.setModel(new DefaultComboBoxModel(new String[] {}));
		cbFrom.setBounds(111, 16, 89, 20);
		panel1.add(cbFrom);
		
		JLabel label = new JLabel("Điểm đến:");
		label.setBounds(28, 54, 73, 28);
		panel1.add(label);
		
		cbTo = new JComboBox();
		cbTo.setModel(new DefaultComboBoxModel(new String[] {}));
		cbTo.setBounds(111, 55, 89, 20);
		panel1.add(cbTo);
		
		JLabel lblSKm = new JLabel("Số km:");
		lblSKm.setBounds(28, 93, 73, 28);
		panel1.add(lblSKm);
		
		txtKm = new JTextField();
		txtKm.setText("0");
		txtKm.setBounds(111, 97, 86, 20);
		panel1.add(txtKm);
		txtKm.setColumns(10);
		
		btnThemDuLieu = new JButton("Thêm dữ liệu");
		btnThemDuLieu.addActionListener(this);
		btnThemDuLieu.setEnabled(true);
		btnThemDuLieu.setBounds(70, 127, 134, 23);
		panel1.add(btnThemDuLieu);
		
		btnReset = new JButton("Thiết lập lại");
		btnReset.addActionListener(this);
		btnReset.setEnabled(false);
		btnReset.setBounds(291, 127, 134, 23);
		panel1.add(btnReset);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 192, 749, 179);
		getContentPane().add(scrollPane);
		
		tblTraveling = new JTable();
		tblTraveling.setModel(new DefaultTableModel(
			new Object[][] {
				
			},
			new String[] {
				"@" 
			}
				
		) {
				boolean[] columnEditables = new boolean[] {
					false, false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
		});
		scrollPane.setViewportView(tblTraveling);
		
		txtKetQua = new JTextField();
		txtKetQua.setEditable(false);
		txtKetQua.setHorizontalAlignment(SwingConstants.LEFT);
		txtKetQua.setBounds(10, 404, 749, 46);
		getContentPane().add(txtKetQua);
		txtKetQua.setColumns(10);
		
		JLabel lblTheResult = new JLabel("Kết quả:");
		lblTheResult.setBounds(10, 382, 121, 14);
		getContentPane().add(lblTheResult);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultTableModel model = (DefaultTableModel) tblTraveling.getModel();
		
		if(e.getSource()==btnThemDuLieu){
			System.out.println(txtKm.getText());
			
			if(cbFrom.getSelectedIndex() == cbTo.getSelectedIndex())
				JOptionPane.showMessageDialog(null, "The area is the same");
			else{
				if(txtKm.getText().equals(null)|| txtKm.getText().equals("0")){
					JOptionPane.showMessageDialog(null, "Invalid value");
				}
				else{
					System.out.println(model.getValueAt(1, 1));
					if(checkAvailble(cbFrom.getSelectedIndex(), cbTo.getSelectedIndex()+1)==false){
						int dialogButton = JOptionPane.YES_NO_OPTION;
						int dialogResult = JOptionPane.showConfirmDialog (null, "The area are already have value, do you want to change it?","Warning",dialogButton);
						if(dialogResult == 0){
							model.setValueAt(txtKm.getText(), cbFrom.getSelectedIndex(), cbTo.getSelectedIndex()+1);
							model.setValueAt(txtKm.getText(), cbTo.getSelectedIndex(), cbFrom.getSelectedIndex()+1);
						}
						else{
							
						}
					}
					else{
						model.setValueAt(txtKm.getText(), cbFrom.getSelectedIndex(), cbTo.getSelectedIndex()+1);
						model.setValueAt(txtKm.getText(), cbTo.getSelectedIndex(), cbFrom.getSelectedIndex()+1);
					}
				}
			}
			btnXuly.setEnabled(true);
			btnReset.setEnabled(true);
		}
		if(e.getSource()==btnXuly){
			if(checkEmpty()==true){
				JOptionPane.showMessageDialog(null, "Please fill all the value");
			}
			else{
				writeTableToFile();
				
				
				Object[][] data = new Object[model.getRowCount()][model.getColumnCount()];
				TSP tsp = new TSP();
				
				try {
					tsp.readFromFile();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				tsp.outputMtr();
				System.out.println();
				
				tsp.solve();
				txtKetQua.setText("Path: "+tsp.getPath() + " with cost: " + tsp.getCost());
			}
		}
		if(e.getSource()==btnOk){
			if(txtN.getText().equals("")) JOptionPane.showMessageDialog(null,"Bạn chưa nhập N!");
			else {
				if(!check(txtN.getText())) JOptionPane.showMessageDialog(null,"N không phải là chữ cái");
				else{
					int temp=Integer.parseInt(txtN.getText());
					if(temp<=0||temp>24) JOptionPane.showMessageDialog(null,"N<=0 hoặc N>24");
					else{
						n=temp;
						txtN.setEditable(false);
					}
				}
			}
			themDiaDiem(n);
		}
		if(e.getSource()==btnReset){
			refreshTable();
			btnReset.setEnabled(false);
			btnXuly.setEnabled(false);
			txtKetQua.setText("");
		}
	}
	
	public void themDiaDiem(int them){
		DefaultTableModel model = (DefaultTableModel) tblTraveling.getModel();
		for(int i=1;i<=n;i++){
			cbFrom.addItem(Character.toString((char) (i + 64)));
			cbTo.addItem(Character.toString((char) (i + 64)));
			model.addColumn(Character.toString((char) (i + 64)));
			model.addRow(new Object[]{Character.toString((char) (i + 64)),null, null, null });
		}
	}
	public void refreshTable(){
		DefaultTableModel model = (DefaultTableModel) tblTraveling.getModel();
		cbFrom.removeAllItems();
		cbTo.removeAllItems();
		txtKm.setText("0");
		txtN.setText("0");
		txtN.setEditable(true);
		model.setColumnCount(1);
		model.setRowCount(0);
	}
	
	public boolean checkFieldName(String sourceName){
		DefaultTableModel model = (DefaultTableModel) tblTraveling.getModel();
		if(model.getColumnCount()>=2){
			for(int i=1;i<model.getColumnCount();i++){
				if(model.getColumnName(i).equals(sourceName))
					return false;
			}
		}
		return true;
	}
	
	public boolean checkAvailble(int i, int j){
		DefaultTableModel model = (DefaultTableModel) tblTraveling.getModel();
		if(model.getValueAt(i, j)==null)
			return true;
		else
			return false;
	}
	
	public boolean checkEmpty(){
		DefaultTableModel model = (DefaultTableModel) tblTraveling.getModel();
		for(int i=0; i<model.getRowCount(); i++){
			for(int j=1;j<model.getColumnCount();j++){
				if((model.getValueAt(i, j)==null) && i+1!=j)
					return true;
			}
		}
		return false;
	}
	
	public boolean check(String source){
		for(int i=0;i<source.length();i++){
			if(source.charAt(i)>57||source.charAt(i)<48) return false;
		}
		return true;
	}
	
	public void writeTableToFile(){
		DefaultTableModel model = (DefaultTableModel) tblTraveling.getModel();
		Object[][] data = new Object[model.getRowCount()][model.getColumnCount()];
		
		for(int i=0;i<model.getRowCount();i++){
			for(int j=1; j<model.getColumnCount();j++){
				System.out.print(model.getValueAt(i,j ) +" ");
				data[i][j-1] = model.getValueAt(i,j );
			}
			System.out.println();
		}
		
		try {
			PrintStream ps = new PrintStream(new File("test.inp"));
			ps.println(model.getRowCount());
			ps.println();
			for(int i=0;i<model.getRowCount();i++){
				for(int j=1; j<model.getColumnCount();j++){
					ps.print(data[i][j-1]);
					if(j!=model.getColumnCount()-1)
						ps.print(" ");
				}
				if(i!=model.getRowCount()-1)
					ps.println();
			}
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
