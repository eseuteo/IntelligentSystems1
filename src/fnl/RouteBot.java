import robocode.control.*;
import robocode.control.events.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

//
// Application that demonstrates how to run two sample robots in Robocode using the
// RobocodeEngine from the robocode.control package.
//
// @author Flemming N. Larsen
//
public class BattleRunner {
	static int MAP_SIZE = 13;

	public static void main(String[] args) {
		// Disable log messages from Robocode
		RobocodeEngine.setLogMessagesEnabled(false);
		// Create the RobocodeEngine
		// RobocodeEngine engine = new RobocodeEngine(); // Run from current
		// working directory
		RobocodeEngine engine = new RobocodeEngine(new java.io.File("C:/Robocode")); // Run
																						// from
																						// C:/Robocode

		// Add our own battle listener to the RobocodeEngine
		engine.addBattleListener(new BattleObserver());

		// Show the Robocode battle view
		engine.setVisible(true);

		// Setup the battle specification
		long inactivityTime = 10000000;
		double gunCoolingRate = 1.0;
		int sentryBorderSize = 50;
		boolean hideEnemyNames = false;
		int numRounds = 5;
		BattlefieldSpecification battlefield = new BattlefieldSpecification(832, 832); // 800x600
		RobotSpecification[] selectedRobots = new RobotSpecification[51];
		RobotSetup[] initialSetups = new RobotSetup[51];
		RobotSpecification[] modelsRobot = new RobotSpecification[2];
		modelsRobot = engine.getLocalRepository("fnl.RouteBot*,sample.SittingDuck");
		long int seed=Syste.currentTimeMillis();
		selectedRobots[0] = modelsRobot[0];
		initialSetups[0] = new RobotSetup(96.0, 352.0, (double)int);
		boolean[][] map = new boolean[MAP_SIZE][MAP_SIZE];


		Random rand = new Random(seed);
		for (int i = 1; i < 51; i++) {

			int x = rand.nextInt();
			x = Math.abs(x);
			int y = (x/MAP_SIZE)%MAP_SIZE;
			x%=MAP_SIZE;
			boolean done = false;
			while (!done) {
				if(map[x][y]){
					 x = rand.nextInt();
					 x = Math.abs(x);
					 y = (x/MAP_SIZE)%MAP_SIZE;
					 x%=MAP_SIZE;
				}else{
					map[x][y]=true;
					done=true;
					selectedRobots[i] = modelsRobot[1];
					initialSetups[i] = new RobotSetup((double)x * 64 + 32, (double)y * 64 + 32, 0.0);
				}

			}

		}

/*		for (int i=0; i<MAP_SIZE; i++){
			for(int j=0; j<MAP_SIZE; j++){
				aux = Math.abs(rand.nextInt())%1000;
				System.out.println(Math.abs(aux));
				condition = aux < 70;
				if (condition && robotCounter < 21){
					map[i][j] = true;
					selectedRobots[robotCounter] = modelsRobot[1];
					initialSetups[robotCounter] = new RobotSetup((double)i * 64 + 32, (double)j * 64 + 32, 0.0);
					robotCounter++;
				}
			}
		}


*/
/*		for (int i = 1; i < 21; i++) {
			selectedRobots[i] = modelsRobot[1];
			double randX = ThreadLocalRandom.current().nextInt(1, 25);
			double randY = ThreadLocalRandom.current().nextInt(1, 25);
			initialSetups[i] = new RobotSetup(randX * 32, randY * 32, 0.0);
		}
*/
		BattleSpecification battleSpec = new BattleSpecification(battlefield, numRounds, inactivityTime, gunCoolingRate,
				sentryBorderSize, hideEnemyNames, selectedRobots, initialSetups);

		// Run our specified battle and let it run till it is over
		engine.runBattle(battleSpec, true); // waits till the battle finishes

		// Cleanup our RobocodeEngine
		engine.close();

		// Make sure that the Java VM is shut down properly
		System.exit(0);
	}
}

//
// Our private battle listener for handling the battle event we are interested
// in.
//
class BattleObserver extends BattleAdaptor {

	// Called when the battle is completed successfully with battle results
	public void onBattleCompleted(BattleCompletedEvent e) {
		System.out.println("-- Battle has completed --");

		// Print out the sorted results with the robot names
		System.out.println("Battle results:");
		for (robocode.BattleResults result : e.getSortedResults()) {
			System.out.println("  " + result.getTeamLeaderName() + ": " + result.getScore());
		}
	}

	// Called when the game sends out an information message during the battle
	public void onBattleMessage(BattleMessageEvent e) {
		System.out.println("Msg> " + e.getMessage());
	}

	// Called when the game sends out an error message during the battle
	public void onBattleError(BattleErrorEvent e) {
		System.out.println("Err> " + e.getError());
	}
}
