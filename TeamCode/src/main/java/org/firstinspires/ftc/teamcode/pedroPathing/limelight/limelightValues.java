package org.firstinspires.ftc.teamcode.pedroPathing.limelight;


import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorGoBildaPinpoint;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

@Autonomous(name = "limelight")
public class limelightValues extends OpMode {
    private Limelight3A limelight;
    private GoBildaPinpointDriver pinpoint;
    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setOffsets(90, 10, DistanceUnit.MM); //these are tuned for 3110-0002-0001 Product Insight #1
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpoint.resetPosAndIMU();
        pinpoint.setPosition(new Pose2D(DistanceUnit.CM, 0, 0, AngleUnit.DEGREES, 0));
        limelight.pipelineSwitch(3); //apriltag
    }

    @Override
    public void start() {
        limelight.start();
    }

    @Override
    public void loop() {

        Pose2D pose2D = pinpoint.getPosition();

        double ta = limelight.getLatestResult().getTa();
        double k = 130.384048104052974;
        double distanciareal = k/Math.sqrt(ta);

        LLResult resultado = limelight.getLatestResult();
        if (resultado != null && resultado.isValid()){
            telemetry.addData("Tx", resultado.getTx());
            telemetry.addData("Ta", resultado.getTa());
            telemetry.addData("Ty", resultado.getTy());
            telemetry.addData("distance:", distanciareal);

            telemetry.addData("y", pinpoint.getPosY(DistanceUnit.CM));
            telemetry.addData("x", pinpoint.getPosX(DistanceUnit.CM));
            telemetry.update();
        }
    }
}

