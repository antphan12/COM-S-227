package hw4;

import api.PositionVector;
import api.Path;
import api.Point;
import api.PointPair;
/**
 * @author antmanphan
 * @version 1.0
 */

public class MultiFixedLink extends AbstractLink {
	
	//creates an array to PointPair
	private PointPair[] connect;
	
	public MultiFixedLink(PointPair[] connected) {
		//based off what is state in the Javadoc
		if(connected.length >= 2 && connected.length <= 6) {
			connect = connected;
		}
	}

	@Override
	public Point getConnectedPoint(Point point) {
		for(PointPair p : connect) {
			if(p.getPointA().equals(point)) {
				return p.getPointB();
			} else if(p.getPointB().equals(point)) {
				return p.getPointA();
			}
		}
		return null;
	}
	//based off javadoc
	@Override
	public int getNumPaths() {
		return connect.length;
	}
}
