package hw4;

import api.PositionVector;
import api.Path;
import api.Point;
/**
 * @author antmanphan
 * @version 1.0
 */

public class StraightLink extends AbstractLink{
	
	private Point ePointA;
	private Point ePointB;
	private Point ePointC;

	public StraightLink(Point endPointA, Point endPointB, Point endPointC) {
		ePointA = endPointA;
		ePointB = endPointB;
		ePointC = endPointC;
	}
	
	@Override
	public Point getConnectedPoint(Point point) {
		if(point.equals(ePointA)) {
			return ePointB;
		} else if(point.equals(ePointB) || point.equals(ePointC)) {
			return ePointA;
		} else {
			return null;
		}
	}
	@Override
	public int getNumPaths() {
		return 3;
	}
}
