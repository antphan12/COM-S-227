package hw4;

import api.PositionVector;
import api.Path;
import api.Point;
import api.PointPair;
/**
 * @author antmanphan
 * @version 1.0
 */


public class MultiSwitchLink extends AbstractLink{
	//same as in MultiFixed Link where it creates a array for connect from PointPair
	private PointPair[] connect;
	
	public MultiSwitchLink(PointPair[] connected) {
		if(connected.length >= 2 && connected.length <= 6) {
			connect = connected;
		}
	}
	
	public void switchConnection(PointPair pointPair, int index) {
		if(!getCrossing()) {
			connect[index] = pointPair;
		}
	}
	
	@Override
	public Point getConnectedPoint(Point point) {
		for(PointPair p: connect) {
			if(p.getPointA().equals(point)) {
				return p.getPointB();
			} else if (p.getPointB().equals(point)) {
				return p.getPointA();
			}
		}
		return null;
	}
	//provided from javadoc
	@Override
	public int getNumPaths() {
		return connect.length;
}
}
