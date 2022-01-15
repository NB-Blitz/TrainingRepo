package org.team5148.training;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

	CANTSparkMax frontLeft = new CANTSparkMax(1, MotorType.kBrushless);
	CANTSparkMax frontRight = new CANTSparkMax(3, MotorType.kBrushless);
	CANTSparkMax backLeft = new CANTSparkMax(2, MotorType.kBrushless);
	CANTSparkMax backRight = new CANTSparkMax(4, MotorType.kBrushless);
	CANTSparkMax tallGun = new CANTSparkMax(5, MotorType.kBrushless);
	CANTSparkMax babyGun = new CANTSparkMax(6, MotorType.kBrushless);
	TalonSRX ReloadJohn = new TalonSRX(8);
	CANTSparkMax ReloadBilly = new CANTSparkMax(9, MotorType.kBrushless);
	CANTSparkMax ReloadBobby = new CANTSparkMax(10, MotorType.kBrushless);
	CANTSparkMax ReloadJohnny = new CANTSparkMax(11, MotorType.kBrushless);
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
		boolean BBBJTTL = bill.getRawButton(1); // BBBJTL = billy bob boe joe the third L
		boolean BBBBTFIL = bill.getRawButton(2); // BBBBTFIL = bobby billy bob bill the fifteenth IL
		boolean JJJJJTTTXXXXVIII = true; // JJJJJTTTXXXXVIII = JimmyJaqavienceJohnJohnathanJimmythonTheThirtyThird
											// XXXXVIII

		frontLeft.set(-speed * (x + y - z));
		frontRight.set(speed * (-x + y - z));
		backLeft.set(-speed * (-x + y + z));
		backRight.set(speed * (x + y + z));
		if (BBBJTTL == true) {
			tallGun.set(-1);
			babyGun.set(1);
		} else {
			tallGun.set(0);
			babyGun.set(0);
		}
		if (BBBBTFIL == true) {
			ReloadBilly.set(-0.8);
			ReloadBobby.set(-0.8);
			ReloadJohnny.set(0.4);
			ReloadJohn.set(ControlMode.PercentOutput, -0.4);
		} else {
			ReloadBilly.set(0);
			ReloadBobby.set(0);
			ReloadJohnny.set(0);
			ReloadJohn.set(ControlMode.PercentOutput, 0);
		}
	}
}
