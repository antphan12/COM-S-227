import static api.CardinalDirection.*;
import api.Crossable;
import api.Path;
import api.Point;
import api.PositionVector;
import hw4.CouplingLink;
import hw4.DeadEndLink;
import simulation.PathTypes;
import simulation.Track;

public class SimpleTests {
	public static void main(String args[]) {
		// set up a simple track with a single path and a dead end link
		Track track = new Track();
		Path path1 = track.addPathType(PathTypes.pathType5, 5, 5, WEST, EAST);
		Crossable link1 = (Crossable) new DeadEndLink();
		path1.setHighEndpointLink(link1);
		
		// set up the position vector (train) to be at the end of the path
		Point highPoint = path1.getHighpoint();
		Point beforeHighPoint =
				path1.getPointByIndex(highPoint.getPointIndex() - 1);
		PositionVector position = new PositionVector();
		position.setPointA(beforeHighPoint);
		position.setPointB(highPoint);
		
		// test methods of DeadEndLink
		link1.trainEnteredCrossing(); // does nothing
		link1.trainExitedCrossing(); // does nothing
		System.out.println("DeadEndLink has " + link1.getNumPaths() + " paths.");
		System.out.println("Expected 1 path.");
		Point connectedPoint = link1.getConnectedPoint(highPoint);
		System.out.println("Connected point is " + connectedPoint);
		System.out.println("Expected null point.");
		link1.shiftPoints(position); // does nothing
	}
}
