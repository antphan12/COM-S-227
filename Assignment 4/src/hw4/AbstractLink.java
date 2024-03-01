package hw4;

import api.Crossable;
import api.Path;
import api.Point;
import api.PositionVector;

//true if only the train is crossing
public abstract class AbstractLink implements Crossable{
private boolean IsCross;

public void trainEnteredCrossing(){
	IsCross = true;
	}
public void trainExitedCrossing() {
	IsCross = false;
}
protected boolean getCrossing() {
	return IsCross;
}

public void shiftPoints(PositionVector positionVector) {
	
	Point oneEndPoint = getConnectedPoint(positionVector.getPointB());
	//oneEndPoint is equal and connected to point B.
	Path otherPath = oneEndPoint.getPath();
	//otherPath is equal to oneEndPoint.
	positionVector.setPointA(oneEndPoint);
	//sets point A equal to otherEndPoint.
	if(oneEndPoint.getPointIndex() == 0) {
		positionVector.setPointB(otherPath.getPointByIndex(1));
	} else {
		positionVector.setPointB(otherPath.getPointByIndex(otherPath.getNumPoints() - 2));
	}
}
}	