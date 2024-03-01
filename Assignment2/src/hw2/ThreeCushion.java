package hw2;

import api.BallType;
import api.PlayerPosition;

public class ThreeCushion 
{
	private static final PlayerPosition PLAYER_A = null;
	private static final BallType WHITE = null;
	private static final BallType YELLOW = null;
	private static final PlayerPosition PLAYER_B = null;
	private int inning = 1;
	private int pointsToWin = 0;
	private int foul = 0;
	private int pointsPlayerA = 0;
	private int pointsPlayerB = 0;
	private int cushionImpacts = 0;
	private int bankCheck = 0;
	private int ballHit = 0;
	private boolean shot;
	private boolean lagWinner;
	private PlayerPosition currentInningPlayer;
	private BallType cueBallA;
	private BallType cueBallB;
	private BallType currentCueBall;

public ThreeCushion(PlayerPosition lagWinner, int pointsToWin) 
{
	this.currentInningPlayer = lagWinner;
	this.pointsToWin = pointsToWin;
	
}

public void lagWinnerChooses(boolean selfBreak, BallType cueBall)
{
	if(currentInningPlayer == PLAYER_A)
	{
		if(selfBreak == true)
		{
			cueBallA = cueBall;
			currentCueBall = cueBallA;
			if(cueBallA == WHITE)
			{
				cueBallB = YELLOW;
			}
			else if(cueBallA == YELLOW)
			{
				cueBallB = WHITE;
			}
		}
		else if(selfBreak == false)
		{
			currentInningPlayer = PLAYER_B;
			cueBallA = cueBall;
			cueBallB = YELLOW;
			currentCueBall = cueBallB;
		}
		lagWinner = true;
	}
}

public void cueStickStrike(BallType ball)
{
	if(currentCueBall == ball)
	{
		shot = true;
	}
	else foul();
}

public void cueBallStrike(BallType ball)
{
	if(ball != currentCueBall)
	{
		shot = true;
		ballHit += 1;
		if(ballHit >= 2 && cushionImpacts >= 3)
		{
			if(currentInningPlayer == PLAYER_A)
			{
				pointsPlayerA +=1;
			}
			else if(currentInningPlayer == PLAYER_B)
			{
				pointsPlayerB += 1;
			}
			ballHit = 0;
		}
	}
}

public void cueBallImpactCushion()
{
	cushionImpacts += 1;
	
}

public void endShot()
{
	if(shot == true)
	{

	}
	if(shot == false)
	{
		if(currentInningPlayer == PLAYER_A)
		{
			currentInningPlayer = PLAYER_B;
			if(currentCueBall == WHITE)
			{
				currentCueBall = YELLOW;
			}
			
		}
	}

}

public void foul()
{
	foul = 1;

	if(foul == 1)
	{
		if(currentInningPlayer == PLAYER_A)
		{
			currentInningPlayer = PLAYER_B;
			inning += 1;
			foul = 0;
		}
		else
		{
			currentInningPlayer = PLAYER_A;
			inning += 1;
			foul = 0;
		}
	}
	endShot();
}

public int getPlayerAScore()
{
	return pointsPlayerA;
}

public int getPlayerBScore()
{
	return pointsPlayerB;
}

public int getInning()
{
	return inning;
}

public BallType getCueBall()
{
	return currentCueBall;
}

public PlayerPosition getInningPlayer()
{

	return currentInningPlayer;
}

public boolean isBreakShot()
{
	
	return lagWinner;
}

public boolean isBankShot()
{
	if(bankCheck == 1)
	{
		return true;
	}
	else
		return false;
}

public boolean isShotStarted()
{
	return shot;
}

public boolean isInningStarted()
{
	return shot;
}

public boolean isGameOver()
{
	if(pointsPlayerA == pointsToWin || pointsPlayerB == pointsToWin)
	{
		return true;
	}
	else 
	{
	return false;
	}
}


}