package hw4;

import api.PositionVector;
import api.Point;
import api.Path;
/**
 * @author antmanphan
 * @version 1.0
 */

public class DeadEndLink extends AbstractLink{
	
	@Override
	public Point getConnectedPoint(Point point) {
		return null; //does nothing
	}
	//reuturns to be a deadend so only 1 path total.
	@Override
	public int getNumPaths() {
		return 1;
	}
	@Override 
	public void shiftPoints(PositionVector positionVector) {
}
}
