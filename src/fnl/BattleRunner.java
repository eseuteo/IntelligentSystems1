package fnl;

import robocode.control.*;
import robocode.control.events.*;

public class BattleRunner {
	static int MAP_SIZE = 19;
	static int NUM_ROBOTS = 100;
	static double INIT_X = 32.0;
	static double INIT_Y = 32.0;

	public static void main(String[] args) {
		RobocodeEngine.setLogMessagesEnabled(false);
		RobocodeEngine engine = new RobocodeEngine(new java.io.File("C:/Robocode"));
		engine.addBattleListener(new BattleObserver());
		engine.setVisible(true);

		long inactivityTime = 10000000;
		double gunCoolingRate = 1.0;
		int sentryBorderSize = 50;
		boolean hideEnemyNames = false;
		int numRounds = 5;

		BattlefieldSpecification battlefield = new BattlefieldSpecification(1216, 1216);
		RobotSpecification[] selectedRobots = new RobotSpecification[NUM_ROBOTS];
		RobotSetup[] initialSetups = new RobotSetup[NUM_ROBOTS];
		RobotSpecification[] modelsRobot = new RobotSpecification[2];
		modelsRobot = engine.getLocalRepository("fnl.RouteBot*,sample.SittingDuck");
		selectedRobots[0] = modelsRobot[0];
		initialSetups[0] = new RobotSetup(INIT_X, INIT_Y, 0.0);
		boolean[][] map= MapGenerator.gen(MAP_SIZE, NUM_ROBOTS);
		int k = 1;

		for (int i = 0; i < MAP_SIZE; i++) {
			for (int j = 0; j < MAP_SIZE; j++) {
				if (map[i][j]&&!(i==0&&j==0)) {
					selectedRobots[k] = modelsRobot[1];
					initialSetups[k] = new RobotSetup((double) i * 64 + 32, (double) j * 64 + 32, 0.0);
					k++;
				}
			}
		}

		BattleSpecification battleSpec = new BattleSpecification(battlefield, numRounds, inactivityTime, gunCoolingRate,
				sentryBorderSize, hideEnemyNames, selectedRobots, initialSetups);
		engine.runBattle(battleSpec, true);
		engine.close();

		System.exit(0);
	}
}

class BattleObserver extends BattleAdaptor {

	public void onBattleCompleted(BattleCompletedEvent e) {
		System.out.println("-- Battle has completed --");

		System.out.println("Battle results:");
		for (robocode.BattleResults result : e.getSortedResults()) {
			System.out.println("  " + result.getTeamLeaderName() + ": " + result.getScore());
		}
	}

	public void onBattleMessage(BattleMessageEvent e) {
		System.out.println("Msg> " + e.getMessage());
	}

	public void onBattleError(BattleErrorEvent e) {
		System.out.println("Err> " + e.getError());
	}
}
