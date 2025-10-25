package org.firstinspires.ftc.teamcode.pedroPathing.LimelightTestes;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.IMU;
import com.seattlesolvers.solverslib.hardware.motors.CRServoEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.util.Direction;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

@Configurable
@TeleOp(name = "LimelightProgramTurret")
public class FollowTag extends LinearOpMode {

    double Heading;
    CRServo servo;
    Limelight3A limelight;
    IMU imu;

    public static double kH = 22, divisor = 3;

    @Override
    public void runOpMode() throws InterruptedException {
        servo = hardwareMap.get(CRServo.class, "servoX");
        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters params = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        ));
        imu.initialize(params);

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(11);
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(0);
        limelight.start();
        //FtcDashboard.getInstance().startCameraStream(limelight, 90);

        waitForStart();

        while (opModeIsActive()) {
            LLResult result = limelight.getLatestResult();
            Pose3D botpose = result.getBotpose();

            if (result.isValid()) {
                List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
                for (LLResultTypes.FiducialResult fr : fiducialResults) {
                    Heading = (-fr.getTargetXDegrees() / kH);

                    telemetry.addData("TX Degrees", fr.getTargetXDegrees());

                }
            } else {
                telemetry.addData("Limelight", "No data available");
            }

            servo.setPower(-Heading / divisor);


            telemetry.addData("Heading LL", Heading);
            telemetry.addData("Botpose", botpose.toString());
            telemetry.update();

        }
    }
}
