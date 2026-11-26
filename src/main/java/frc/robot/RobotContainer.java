// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.controller.BangBangController;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.*;

public class RobotContainer {
    private final Intake inta = new Intake();
    private final Spindexer spin = new Spindexer();
    private final IntakePivot piv = new IntakePivot();
    private final Feeder feed = new Feeder();
    private final Flywheels fly = new Flywheels();
    private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(1).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final Telemetry logger = new Telemetry(MaxSpeed);
    public final CommandXboxController driverController = new CommandXboxController(0);
    BangBangController controller = new BangBangController();
    public final CommandXboxController operatorController = new CommandXboxController(1);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public final DataLog log = DataLogManager.getLog();

    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {
        ;
        driverController.leftBumper().whileTrue(new InstantCommand(() -> inta.set_Velocity(4500)));
        driverController.rightBumper().whileTrue(new InstantCommand(() -> inta.stop_intake()));
        driverController.a().whileTrue(new InstantCommand(() -> spin.stop_spin()));
        driverController.a().whileTrue(new InstantCommand(() -> feed.stop_feeder()));
        driverController.a().whileTrue(new InstantCommand(() -> fly.stop_fly()));
        driverController.b().whileTrue(new InstantCommand(() -> spin.set_Velocity(4500)));
        driverController.b().whileTrue(new InstantCommand(() -> feed.set_Velocity(4500)));
        driverController.b().whileTrue(new InstantCommand(() -> fly.set_VelocityL(4500)));
        driverController.b().whileTrue(new InstantCommand(() -> fly.set_VelocityR(4500)));
        driverController.leftTrigger().whileTrue(new InstantCommand(() -> piv.set_Position(15.5)));
        driverController.rightTrigger().whileTrue(new InstantCommand(() -> piv.set_Position(0)));
        
        drivetrain.setDefaultCommand(
            drivetrain.applyRequest(() -> {
                double cappedMaxSpeed = MaxSpeed;
                double cappedMaxAngularVelocity = MaxAngularRate;

                if (driverController.rightTrigger(0.3).getAsBoolean()) {
                    cappedMaxSpeed = 0.7; //meters per second
                    cappedMaxAngularVelocity *= 0.5; //radiants per seconds
                }
                
                double xVel = Math.max(-cappedMaxSpeed, Math.min(cappedMaxSpeed,-driverController.getLeftY() * MaxSpeed));
                double yVel = Math.max(-cappedMaxSpeed, Math.min(cappedMaxSpeed,-driverController.getLeftX() * MaxSpeed));
                double rotVel = Math.max(-cappedMaxAngularVelocity, Math.min(cappedMaxAngularVelocity, -driverController.getRightX() * MaxAngularRate));

                return drive.withVelocityX(xVel)
                    .withVelocityY(yVel)
                    .withRotationalRate(rotVel);
            })
        );

        drivetrain.registerTelemetry(logger::telemeterize);

        driverController.start().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric)); // zero gyro
    }

    public Command getAutonomousCommand() {
        // return ppAutoChooser.getSelected();
        return new InstantCommand();
    }
}
