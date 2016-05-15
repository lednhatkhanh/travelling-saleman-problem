package code;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import code.Point;

public class TSP {
	
	private Point mainMtr[][];
	private ArrayList<TracePath> pointTravelled;
	private int n;
	private int rowIndex,colIndex; 
	
	//Save the mainArr for calculating cost later!
	private Point savedMtr[][];
	private int savedN;
	
	public TSP(){
		mainMtr = new Point[100][100];
		savedMtr = new Point[100][100];
		n = 0;
		pointTravelled = new ArrayList<TracePath>();
	}
	
	// Read from file + create a savedMatrix for store the original Matrix
	public void readFromFile() throws FileNotFoundException{
		File fi = new File("test.inp");
		Scanner kb = new Scanner(fi);
		if(kb.hasNextLine()){
			n = kb.nextInt()+1;
			mainMtr = new Point[n][n];
			
			savedMtr = new Point[n][n];
			
			for(int i = 0;i < n;i++){
				for(int j=0;j< n;j++){
					
					mainMtr[i][j] = new Point();
					savedMtr[i][j] = new Point();
					
					if(i==0){
						
						mainMtr[i][j].setName(j);
						savedMtr[i][j].setName(j);
						
					}else if(j==0){
						
						mainMtr[i][j].setName(i);
						savedMtr[i][j].setName(i);
						
					}
				}
			}
		}
		while(kb.hasNextLine()){
			for(int i = 1;i < n;i++){
				for(int j = 1;j < n;j++){
					if(i==j){
						
						mainMtr[i][j].setValue(Integer.MAX_VALUE);
						savedMtr[i][j].setValue(Integer.MAX_VALUE);
						
						kb.next();
					}else{
						
						int temp = kb.nextInt();
						
						mainMtr[i][j] = new Point();
						mainMtr[i][j].setValue(temp);
						
						savedMtr[i][j] = new Point();
						savedMtr[i][j].setValue(temp);
						
					}
				}
			}
		}
		
		savedN = n;
		
		kb.close();
	}
	
	// Set infinity value for Path that can't be traveled
	// Eg: A --> A
	private void setValueInfinity(TracePath tp){
		int rowValue = 0,colValue = 0;
		for(int i=0;i<n;i++){
			if(mainMtr[i][0].getName().equals(tp.getEndPoint())){
				rowValue = i;
			}
			if(mainMtr[0][i].getName().equals(tp.getStartPoint())){
				colValue = i;
			}
		}
		mainMtr[rowValue][colValue].setValue(Integer.MAX_VALUE);
	}
	
	// get min value of a row
	private int getMinRow(int row){
		int min = Integer.MAX_VALUE;
		for(int col = 1;col < n;col++){
			if(mainMtr[row][col].getValue() < min) min = mainMtr[row][col].getValue();
		}
		return min;
	}
	
	// Get min value of a row minus the current value(row,col)
	private int getMinInRowByPoint(int row,int col){
		int min = Integer.MAX_VALUE;
		for(int i = 1;i < n;i++){
			if(i != col){
				if(mainMtr[row][i].getValue() < min) min = mainMtr[row][i].getValue();
			}
		}
		return min;
	}
	
	private int getMinCol(int col){
		int min = Integer.MAX_VALUE;
		for(int row = 1;row < n;row++){
			if(mainMtr[row][col].getValue() < min) min = mainMtr[row][col].getValue();
		}
		return min;
	}
	
	private int getMinInColByPoint(int row,int col){
		int min = Integer.MAX_VALUE;
		for(int i = 1;i < n;i ++){
			if(i != row){
				if(mainMtr[i][col].getValue() < min) min = mainMtr[i][col].getValue();
			}
		}
		return min;
	}
	
	// Minimize Matrix, atleast 1 0 for each row and col
	private void minimizeMtr(){
		for(int row = 1;row < n;row++){
			int temp = getMinRow(row);
			for(int col = 1;col < n;col++){
				if(mainMtr[row][col].getValue() != Integer.MAX_VALUE){
					mainMtr[row][col].setValue(mainMtr[row][col].getValue() - temp);
				}
			}
		}
		for(int col = 1;col < n;col++){
			int temp = getMinCol(col);
			for(int  row = 1;row<n;row++){
				if(mainMtr[row][col].getValue() != Integer.MAX_VALUE){
					mainMtr[row][col].setValue(mainMtr[row][col].getValue() - temp);
				}
			}
		}
	}
	
	// Delete row and col that contains the current point(row,col)
	private void deleteRowAndCol(int row,int col){
		for(int i = row;i<n-1;i++){
			for(int j=0;j<n;j++){
				if(j!=0 && j!=0){
					mainMtr[i][j].setValue(mainMtr[i+1][j].getValue());
				}else{
					mainMtr[i][j].setName(mainMtr[i+1][j].getName());
				}
			}
		}
		for(int i = 0;i<n;i++){
			for(int j=col;j<n-1;j++){
				if(i!=0 && j!=0){
					mainMtr[i][j].setValue(mainMtr[i][j+1].getValue());
				}else{
					mainMtr[i][j].setName(mainMtr[i][j+1].getName());
				}
			}
		}
		n--;
	}
	
	// Calculate penalty of all points
	private void calculatePenalty(){
		for(int i=1;i<n;i++){
			for(int j=1;j<n;j++){
				if(mainMtr[i][j].getValue()==0){
					mainMtr[i][j].setPenalty(getMinInColByPoint(i, j)+getMinInRowByPoint(i, j));
				}
			}
		}
	}
	
	// Reset penalty of all points
	private void resetPenalty(){
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				mainMtr[i][j].setPenalty(0);
			}
		}
	}
	
	// Output mainMtr
	//Only for debugging!
	public void outputMtr(){
		if(n>1){
			for(int row = 0;row < n;row++){
				for(int col = 0;col < n;col++){
					if(mainMtr[row][col].getValue() == Integer.MAX_VALUE){
						System.out.print("- ");
					}else if(row==0||col==0){
						System.out.print(mainMtr[row][col].getName()+" ");
					}
					else{
						System.out.print(mainMtr[row][col].getValue()+" ");
					}
				}
				System.out.println();
			}
		}
	}
	
	//Only for debugging
	public void outputSavedMtr(){
		if(savedN>1){
			for(int row = 0;row < savedN;row++){
				for(int col = 0;col < savedN;col++){
					if(savedMtr[row][col].getValue() == Integer.MAX_VALUE){
						System.out.print("- ");
					}else if(row==0||col==0){
						System.out.print(savedMtr[row][col].getName()+" ");
					}
					else{
						System.out.print(savedMtr[row][col].getValue()+" ");
					}
				}
				System.out.println();
			}
		}
	}
	
	// Only for debugging
		public void outputPenalty(){
			for(int i=1;i<n;i++){
				for(int j=1;j<n;j++){
					if(mainMtr[i][j].getPenalty()>=0){
						System.out.print(mainMtr[i][j].getPenalty()+" ");
					}
				}
				System.out.println();
			}
		}
	
	// Only for debugging
	public void outputName(){
		for(int i =0;i<n;i++){
			System.out.println(mainMtr[i][0].getName());
		}
	}
	
	public boolean duplicateTracePath(int row,int col){
		if(mainMtr[row][0].getName().equals(mainMtr[0][col].getName())){
			return true;
		}
		return false;
	}
	
	private void getPointWithHightestPenaty(){
		calculatePenalty();
		int max = Integer.MIN_VALUE;
		for(int i =1;i<n;i++){
			for(int j=1;j<n;j++){
				if(mainMtr[i][j].getPenalty()>max && mainMtr[i][j].getValue()==0){
					max = mainMtr[i][j].getPenalty();
					colIndex = j;
					rowIndex = i;
				}
			}
		}
	}
	
	// If the path was traveled, get next point!
	private void getNextPenaltyPoint(){
		int max = Integer.MIN_VALUE;
		for(int i=rowIndex+1;i<n;i++){
			for(int j=colIndex+1;j<n;j++){
				if(mainMtr[i][j].getPenalty()>max && mainMtr[i][j].getValue()==0){
					max = mainMtr[i][j].getPenalty();
					colIndex = j;
					rowIndex = i;
					break;
				}
			}
		}
	}
	
	// Return false if the matrix already contains the path
	public boolean addToTracePathArr(TracePath tp){
		if(pointTravelled.size()==0){
			pointTravelled.add(tp);
			return true;
		}
		for(int i=0;i<pointTravelled.size();i++){
			if(pointTravelled.get(i).getEndPoint().equals(tp.getEndPoint()) && pointTravelled.get(i).getStartPoint().equals(tp.getStartPoint())){
				return false;
			}
			else if(pointTravelled.get(i).getEndPoint().equals(tp.getStartPoint()) && pointTravelled.get(i).getStartPoint().equals(tp.getEndPoint())){
					return false;
			}
		}
		pointTravelled.add(tp);
		return true;
	}
	
	// Create a TracePath
	public TracePath createTracePath(){
		TracePath tempTracePath = new TracePath();
		tempTracePath.setStartPoint(mainMtr[rowIndex][0].getName());
		tempTracePath.setEndPoint(mainMtr[0][colIndex].getName());
		return tempTracePath;
	}
	
	// This is the main part for solving everything!
	public void solve(){
		int i = 0;
		while(i<savedN-1){
			if(n==2){
				TracePath temp = new TracePath();
				temp.setStartPoint(mainMtr[1][0].getName());
				temp.setEndPoint(mainMtr[0][1].getName());
				addToTracePathArr(temp);
				break;
			}
			minimizeMtr();
			getPointWithHightestPenaty();
			TracePath tempTracePath = createTracePath();
			while(!addToTracePathArr(tempTracePath)){
				getNextPenaltyPoint();
				tempTracePath = createTracePath();
			}
			deleteRowAndCol(rowIndex, colIndex);
			setValueInfinity(tempTracePath);
			resetPenalty();
			i++;
			
			if(n==3){
				mainMtr[0][0].setName("@");
				mainMtr[0][0].setValue(0);
				outputMtr();
				System.out.println();
			}
		}
	}
	
	// Get path
	public String getPath(){
		System.out.println(pointTravelled.toString());
		ArrayList<TracePath> temp = new ArrayList<TracePath>();
		String outputStr = "";
		
		for(int i=0;i<pointTravelled.size();i++){
			if(pointTravelled.get(i).getStartPoint().equals("A")){
				temp.add(pointTravelled.get(i));
				pointTravelled.remove(i);
				break;
			}
		}
		for(int i=0;i<pointTravelled.size();i++){
			if(pointTravelled.get(i).getStartPoint().equals(temp.get(temp.size()-1).getEndPoint())){
				temp.add(pointTravelled.get(i));
				pointTravelled.remove(i);
				i=-1;
			}
		}
		
		pointTravelled = temp;
		for(int i=0;i<pointTravelled.size();i++){
			if(i==0){
				outputStr += pointTravelled.get(i).getStartPoint()+" --> "+pointTravelled.get(i).getEndPoint()+" --> ";
			}else if(i==pointTravelled.size()-1){
				outputStr += pointTravelled.get(i).getEndPoint();
			}else{
				outputStr += pointTravelled.get(i).getEndPoint()+" --> ";
			}
		}
		return outputStr;
	}
	
	// Get cost for a specific path
	public int getValueByTracePath(TracePath tp){
		int tempRow = 0;
		int tempCol = 0;
		for(int i = 0;i<savedN;i++){
			if(savedMtr[i][0].getName().equals(tp.getEndPoint())){
				tempRow = i;
				break;
			}
		}
		for(int i = 0;i<savedN;i++){
			if(savedMtr[0][i].getName().equals(tp.getStartPoint())){
				tempCol = i;
				break;
			}
		}
		return savedMtr[tempRow][tempCol].getValue();
	}
	
	// Calculate the total cost
	public int getCost(){
		int cost = 0;
		for(int i=0;i<pointTravelled.size();i++){
			cost += getValueByTracePath(pointTravelled.get(i));
		}
		return cost;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		TSP tsp = new TSP();
		
		tsp.readFromFile();
		tsp.outputMtr();
		System.out.println();
		
		tsp.solve();
		System.out.println("Path: "+tsp.getPath());
		System.out.println("Cost: "+tsp.getCost());
		
	}
}
