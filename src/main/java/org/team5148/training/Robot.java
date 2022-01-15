package org.team5148.training;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class Robot extends TimedRobot {

	// Constants
	private final double ROBOT_SPEED = 1;
	private final double LAUNCHER_SPEED = 0.8;
	private final double STORAGE_SPEED = 0.8;
	private final double INTAKE_SPEED = 0.25;

	// Drivetrain
	CANSparkMax frontLeft = new CANSparkMax(1, MotorType.kBrushless);
	CANSparkMax frontRight = new CANSparkMax(3, MotorType.kBrushless);
	CANSparkMax backLeft = new CANSparkMax(2, MotorType.kBrushless);
	CANSparkMax backRight = new CANSparkMax(4, MotorType.kBrushless);

	// Launcher
	CANSparkMax topLauncher = new CANSparkMax(5, MotorType.kBrushless);
	CANSparkMax bottomLauncher = new CANSparkMax(6, MotorType.kBrushless);

	// Storage
	TalonSRX launcherFeeder = new TalonSRX(8);
	CANSparkMax storageFeeder1 = new CANSparkMax(9, MotorType.kBrushless);
	CANSparkMax storageFeeder2 = new CANSparkMax(10, MotorType.kBrushless);
	CANSparkMax intake = new CANSparkMax(11, MotorType.kBrushless);

	// Controls
	XboxController xboxController = new XboxController(0);
	Joystick joystick = new Joystick(1);

	// Shuffleboard
	ShuffleboardTab tab = Shuffleboard.getTab("Practice");
	NetworkTableEntry useXbox = tab.add("Use Xbox Controller", true).getEntry();

	@Override
	public void teleopInit() {

	}

	@Override
	public void teleopPeriodic() {

		// Input Variables
		boolean isXbox = useXbox.getBoolean(true);
		double xInput = 0;
		double yInput = 0;
		double zInput = 0;
		boolean launcherInput = false;
		boolean intakeInput = false;

		// Input Setting
		if (isXbox) {
			xInput = xboxController.getX(Hand.kLeft);
			yInput = xboxController.getY(Hand.kLeft);
			zInput = xboxController.getX(Hand.kRight);

			launcherInput = xboxController.getBButton();
			intakeInput = xboxController.getAButton();
		} else {
			xInput = joystick.getX();
			yInput = joystick.getY();
			zInput = joystick.getZ();

			launcherInput = joystick.getRawButton(1);
			intakeInput = joystick.getRawButton(2);
		}

		// Drive Train
		frontLeft.set(-ROBOT_SPEED * (xInput + yInput - zInput));
		frontRight.set(ROBOT_SPEED * (-xInput + yInput - zInput));
		backLeft.set(-ROBOT_SPEED * (-xInput + yInput + zInput));
		backRight.set(ROBOT_SPEED * (xInput + yInput + zInput));

		// Launcher
		topLauncher.set(launcherInput ? LAUNCHER_SPEED : 0);
		bottomLauncher.set(launcherInput ? LAUNCHER_SPEED : 0);

		// Storage
		launcherFeeder.set(ControlMode.PercentOutput, intakeInput ? STORAGE_SPEED : 0);
		storageFeeder1.set(intakeInput ? STORAGE_SPEED : 0);
		storageFeeder2.set(intakeInput ? STORAGE_SPEED : 0);

		// Intake
		intake.set(intakeInput ? INTAKE_SPEED : 0);
	}
}
