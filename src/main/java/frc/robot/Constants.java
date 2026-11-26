package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;

public class Constants {
    public static class CanIDs {
        // Intake
        public static final int intakePivotMotorID = 20;
        public static final int intakeMotorID = 21;

        // Shooter
        public static final int rightFlywheelMotorID = 60; // Kraken x60 that powers the shooter.
        public static final int leftFlywheelMotorID = 61;

        public static final int shooterHoodMotorID = 62;

        public static final int turretMotorID = 50;
        public static final int turretEncoderID = 51;

        public static final int feederMotorID = 40;

        public static final int spindexerMotorID = 30;
    }
}
