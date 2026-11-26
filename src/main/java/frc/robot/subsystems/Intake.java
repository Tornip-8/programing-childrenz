package frc.robot.subsystems;
//imports
import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import frc.robot.Constants;
//code goes in subsystem
public class Intake extends SubsystemBase{
    final VelocityVoltage v_request = new VelocityVoltage(0).withSlot(0);
    private static TalonFX intakeMotor = new TalonFX(Constants.CanIDs.intakeMotorID, new CANBus("Jeff"));

    public Intake(){
        var configs = new TalonFXConfiguration();
        configs.Slot0.kP = 0.8;
        configs.Slot0.kI = 0;
        configs.Slot0.kD = 0;
        intakeMotor.getConfigurator().apply(configs);
    }

    public double get_Velocity(){
    return intakeMotor.getVelocity().getValueAsDouble();
    }

    public void set_Velocity(double vel){
        intakeMotor.setControl(v_request.withVelocity(vel/60));
    }
    public void stop_intake(){
        intakeMotor.setVoltage(0);
    }
}