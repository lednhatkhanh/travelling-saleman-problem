package code;

/*
 * This class will trace the path we traveled
 */


public class TracePath {
	private String startPoint,endPoint;
	
	public TracePath(){
		startPoint = endPoint = "";
	}

	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	
	public String toString(){
		return startPoint + " --> " + endPoint;
	}
}
