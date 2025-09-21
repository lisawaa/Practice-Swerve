package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.servohub.ServoHub.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class SwerveModule 
{
    private final SparkMax driveMotor;
    private final SparkMax turnMotor;
    private final RelativeEncoder driveEncoder;
    private final AbsoluteEncoder turnEncoder;
    private final double driveEncoderInverted
    private final SparkClosedLoopController driveClosedLoopController;
    private final SparkClosedLoopController turnClosedLoopController;
    private final MotorLocation motorLocation;
    private double angularOffset = 0;
    private SwerveModuleState desiredState = new SwerveModuleState(0.0, new Rotation2d());
    
    public SwerveModule(int driveMotorId, int turnMotorId, boolean driveEncoderInverted, double angularOffset, int turnEncoderids, SparkMaxConfig drivingConfig, MotorLocation motorLocation)
    {
        driveMotor = new SparkMax(driveMotorId, MotorType.kBrushless);
        turnMotor = new SparkMax(turnMotorId, MotorType.kBrushless);

        driveEncoder = driveMotor.getEncoder();
        turnEncoder = turnMotor.getAbsoluteEncoder();

        driveClosedLoopController = driveMotor.getClosedLoopController();
        turnClosedLoopController = turnMotor.getClosedLoopController();

        motorLocation = motorLocation;

        driveMotor.configure(drivingConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        turnMotor.configure(turningConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        this.angularOffset = angularOffset; 
        desiredState.angle = new Rotation2d(turnEncoder.getPosition());
        if(driveEncoderInverted)
        {
            this.driveEncoderInverted = -1.0;
        }
        else
        {
            this.driveEncoderInverted = 1.0;
        }
        driveEncoder.setPosition(0);
    }

    public SwerveModuleState getState()
    {
        return new SwerveModuleState(getDriveVelocity(), new Rotation2d(turnEncoder.getPosition() - angularOffset));
    }

    public double getDrivePosition()
    {
        return driveEncoderInverted * driveEncoder.getPosition();
    }








    }
 
}
