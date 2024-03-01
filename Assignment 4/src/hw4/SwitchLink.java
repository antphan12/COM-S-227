package hw4;

import api.PositionVector;
import api.Path;
import api.Point;
/**
 * @author antmanphan
 * @version 1.0
 */

public class SwitchLink extends AbstractLink{
	
	private Point ePointA;
	private Point ePointB;
	private Point ePointC;
	private boolean isTurn;
	
	public SwitchLink(Point endPointA, Point endPointB, Point endPointC){
		ePointA = endPointA;
		ePointB = endPointB;
		ePointC = endPointC;
	}

	public void setTurn(boolean isTurn) {
		if(!getCrossing()) {
			this.isTurn = isTurn;
		}
	}
	
	@Override
	public Point getConnectedPoint(Point point) {
		if(isTurn && point.equals(ePointA)) {
			return ePointC;
		} else if(!isTurn && point.equals(ePointA)) {
			return ePointB;
		} else if(point.equals(ePointB) || point.equals(ePointA)) {
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


