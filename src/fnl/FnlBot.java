package fnl;

import robocode.Robot;
import robocode.ScannedRobotEvent;
import java.util.concurrent.ThreadLocalRandom;

public class FnlBot extends Robot {
	public void run() {
		turnLeft(getHeading() % 90);
		turnGunRight(90);
		while(true) {
			int randomNum = ThreadLocalRandom.current().nextInt(0, 100);
			ahead(1000);
			turnRight(randomNum);
		}
	}
	
}
