package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Flywheels extends SubsystemBase {
    final VelocityVoltage v_request = new VelocityVoltage(0).withSlot(0);
    private final TalonFX rightFlywheelMotor = new TalonFX(Constants.CanIDs.rightFlywheelMotorID);
    private final TalonFX leftFlywheelMotor = new TalonFX(Constants.CanIDs.leftFlywheelMotorID);
    public Flywheels(){
        var configs = new TalonFXConfiguration();
        configs.Slot0.kP = 0.8;
        configs.Slot0.kI = 0;
        configs.Slot0.kD = 0;
        leftFlywheelMotor.getConfigurator().apply(configs);
        rightFlywheelMotor.getConfigurator().apply(configs);
    }   
    public double get_VelocityL(){
        return leftFlywheelMotor.getVelocity().getValueAsDouble();
    }
    public double get_VelocityR(){
        return rightFlywheelMotor.getVelocity().getValueAsDouble();
    }
    public void set_VelocityL(double vel){
        leftFlywheelMotor.setControl(v_request.withVelocity(vel));
    }
    public void set_VelocityR(double vel){
        rightFlywheelMotor.setControl(v_request.withVelocity(vel));
    }
    public void stop_fly(){
        rightFlywheelMotor.setVoltage(0);
        leftFlywheelMotor.setVoltage(0);
    }
}