package hw4;

import api.Crossable;
import api.Path;
import api.Traversable;
import api.Point;
import api.PositionVector;
/**
 * @author antmanphan
 * @version 1.0
 */

public class CouplingLink extends AbstractLink {
	private Point ePoint1; //ePoint(end point on the Point). So end point 1
	private Point ePoint2; //end point 2
	
	public CouplingLink(Point endpoint1, Point endpoint2) {
		
		ePoint1 = endpoint1;
		ePoint2 = endpoint2;
	}
@Override
public Point getConnectedPoint(Point point) {
	Path path = point.getPath();
	if(path == null) {
		if(point == ePoint2) {
			return ePoint1;
		} else if (point == ePoint1) {
			return ePoint2;
		} else {
			return null;
		}
	} else { 
		//iterates to find the endPoints
		for(int i = 0; i < path.getNumPoints(); i++) {
			if(path.getPointByIndex(i).equals(ePoint1)) {
				return ePoint2;
			} else if(path.getPointByIndex(i).equals(ePoint2)) {
				return ePoint1;
			}
		}
	}
	return null;
}
@Override
public int getNumPaths() {
	return 2;
}
}
