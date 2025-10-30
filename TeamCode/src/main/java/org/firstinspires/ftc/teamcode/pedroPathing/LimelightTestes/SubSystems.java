package org.firstinspires.ftc.teamcode.pedroPathing.LimelightTestes;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.IMU;

@Configurable
@TeleOp
public class SubSystems extends OpMode {
    CRServo servo;
    Limelight3A limelight;
    IMU imu;

    public static double k = 0;


    @Override
    public void init()  {
        servo = hardwareMap.get(CRServo.class, "servoX1");
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

    }

    public void loop() {
        LLResult result = limelight.getLatestResult();

        double scale = 181.9994;
        double distance = (scale / Math.sqrt(result.getTa()));

        double cosseno = result.getTx() / distance;


        double power = cosseno * 0.0375 * (74.8 / 100) * k;

        if (Math.toDegrees(Math.acos(cosseno)) != 90) {
            servo.setPower(power);
        } else {
            servo.setPower(0);
        }

        telemetry.addData("Angul0: ", Math.toDegrees(Math.acos(cosseno)));
        telemetry.addData("Power: ", power);

    }
}
