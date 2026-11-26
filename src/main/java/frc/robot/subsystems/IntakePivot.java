package frc.robot.subsystems;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakePivot extends SubsystemBase {
  private final TalonFX intakePivotMotor = new TalonFX(Constants.CanIDs.intakePivotMotorID, new CANBus("Jeff"));
  
  final PositionVoltage p_request = new PositionVoltage(0).withSlot(0);

  public IntakePivot() {
    var configs = new TalonFXConfiguration();
    configs.Slot0.kP = 1;
    configs.Slot0.kI = 0;
    configs.Slot0.kD = 0;
    intakePivotMotor.getConfigurator().apply(configs);
  }

  public void set_Position(double pos){
    intakePivotMotor.setControl(p_request.withPosition(pos));
  }
  
  public double get_Position(){
    return intakePivotMotor.getPosition().getValueAsDouble();
  }

  @Override

  public void periodic() {
  SmartDashboard.getNumber("position", get_Position());
  SmartDashboard.putNumber("Position", get_Position());
  }
}
