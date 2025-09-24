package org.firstinspires.ftc.teamcode.pedroPathing.AprilTagWebcam;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@TeleOp(name = "Teste April Tag", group = "Prototipos")
public class AprilTag extends LinearOpMode {
    private static final boolean USE_WEBCAM = true;

    private AprilTagProcessor aprilTag;

    private VisionPortal visionPortal;


    @Override
    public void runOpMode(){


        initAprilTag();
        telemetry.addData(">", "Touch START to start OpMode");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()){

            List<AprilTagDetection> currentDetections = aprilTag.getDetections();
            // Step through the list of detections and display info for each one.
            for (AprilTagDetection detection : currentDetections) {
                if (detection.id == 21) {
                    telemetry.addLine("A ordem é: GPP");
                } else if (detection.id == 23){
                    telemetry.addLine("A ordem é: PPG");
                }else if (detection.id == 22){
                    telemetry.addLine("A ordem é: PGP");
                }
            }

            telemetry.update();


        }
        visionPortal.close();
    }

    private void initAprilTag() {

        // Create the AprilTag processor the easy way.
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();

        // Create the vision portal the easy way.
        if (USE_WEBCAM) {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    hardwareMap.get(WebcamName.class, "webcam"), aprilTag);
        } else {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    BuiltinCameraDirection.BACK, aprilTag);
        }

    }
}
