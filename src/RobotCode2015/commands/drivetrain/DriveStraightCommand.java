package RobotCode2015.commands.drivetrain;

import RobotCode2015.subsystems.Drivetrain;
import RobotCode2015.Constants;

/**
 * Drive straight until interrupted
 * @author Manan
 * @author Arcterus
 */
public class DriveStraightCommand extends AutomaticDriveCommand {
	private double speed;

	private double leftStart, rightStart;

	private double left, right;
        
        /**
         * Initialize a new DriveStraightCommand with speed .5 (default)
         */
	public DriveStraightCommand() {
            super ("Drive Straight Command");
            requires(drivetrain);
            speed = .5;
	}
        
        /**
         * Initialize a new DriveStraightCommand with a custom speed.
         * @param speed The speed at which to drive.
         */
        public DriveStraightCommand(double speed) {
            super ("Drive Straight Command");
            requires(drivetrain);
            this.speed = speed;
        }

	protected void initialize() { }

	public void start() {
		super.start();
		left = right = speed;
		//These don't seem to be used anywhere else:
                //leftStart = drivetrain.getLeftDistance();
		//rightStart = drivetrain.getRightDistance();

		drivetrain.driveRaw(speed, speed); //Why are we using drive raw as opposed to regular drive?
	}

	// Called repeatedly when this Command is scheduled to run
	// XXX: this may or may not work.  We need to test it.
	protected void execute() {
		double ratio = drivetrain.getLeftRate() / drivetrain.getRightRate();

		if(Double.isNaN(ratio)) ratio = 1.0;
		// 	drivetrain.driveRaw(speed / ratio, speed * ratio);

		//double difference = leftEnc.getDistance()-leftStart - rightEnc.getDistance()+rightStart; // d = l-r
		// double difference = (leftEnc.getDistance()-leftStart) - (.5*rightEnc.getDistance()-rightStart); // d = l-r

		right *= ratio;
		//left -= difference * Constants.DrivetrainConst.driveStraightScaling;
		//right += difference * Constants.DrivetrainConst.driveStraightScaling;

		// SmartDashboard.putNumber("diff",difference);
		// SmartDashboard.putNumber("scaled diff",difference*Constants.DrivetrainConst.driveStraightScaling);
		//
		// SmartDashboard.putNumber("Left Rate", leftEnc.getRate());
		// SmartDashboard.putNumber("Right Rate", rightEnc.getRate());
		drivetrain.driveRaw(left, right);
	}

	// Do we really want this to run forever until interrupted?
	protected boolean isFinished() {
		return false;
	}
}