package frc.robot.subsystems;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Spindexer extends SubsystemBase {
    final VelocityVoltage v_request = new VelocityVoltage(0).withSlot(0);
    private final TalonFX spindexMotor = new TalonFX(Constants.CanIDs.spindexerMotorID, new CANBus("Jeff"));

    public Spindexer(){
        var configs = new TalonFXConfiguration();
        configs.Slot0.kP = 0.05;
        configs.Slot0.kI = 0;
        configs.Slot0.kD = 0;
        spindexMotor.getConfigurator().apply(configs);
    }

    public double get_Velocity(){
    return spindexMotor.getVelocity().getValueAsDouble();
    }

    public void set_Velocity(double vel){
        spindexMotor.setControl(v_request.withVelocity(vel));
    }
    public void stop_spin(){
        spindexMotor.setVoltage(0);
    }
}