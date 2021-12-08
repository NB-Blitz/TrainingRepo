package org.team5148.training;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

	CANSparkMax frontLeft = new CANSparkMax(1, MotorType.kBrushless);
	CANSparkMax frontRight = new CANSparkMax(3, MotorType.kBrushless);
	CANSparkMax backLeft = new CANSparkMax(2, MotorType.kBrushless);
	CANSparkMax backRight = new CANSparkMax(4, MotorType.kBrushless);
	Joystick bill = new Joystick(3);
	Timer timer = new Timer();
	AHRS navx = new AHRS();

	@Override
	public void autonomousInit() {
		timer.reset();
		timer.start();
		navx.reset();
	}

	@Override
	public void autonomousPeriodic() {
		double angle = (navx.getAngle() + 180) % 360;
		double time = timer.get();

		SmartDashboard.putNumber("Angle", angle);

		if (time <= 1) {
			frontLeft.set(-0.2);
			frontRight.set(0.2);
			backLeft.set(-0.2);
			backRight.set(0.2);
		} else if (time >= 1 && time <= 6) {
			if (angle <= 270) {
				frontLeft.set(-0.2);
				frontRight.set(-0.2);
				backLeft.set(-0.2);
				backRight.set(-0.2);
			} else {
				frontLeft.set(0);
				frontRight.set(0);
				backLeft.set(0);
				backRight.set(0);
			}
		} else {
			frontLeft.set(0);
			frontRight.set(0);
			backLeft.set(0);
			backRight.set(0);
		}
	}

	@Override
	public void teleopInit() {
	}

	@Override
	public void teleopPeriodic() {
		double y = -bill.getY();
		double x = bill.getX();
		double z = -bill.getZ();
		double slider = bill.getThrottle();
		double speed = 0.2 * (-slider + 1) / 2;

		frontLeft.set(-speed * (x + y - z));
		frontRight.set(speed * (-x + y - z));
		backLeft.set(-speed * (-x + y + z));
		backRight.set(speed * (x + y + z));

	}
}
