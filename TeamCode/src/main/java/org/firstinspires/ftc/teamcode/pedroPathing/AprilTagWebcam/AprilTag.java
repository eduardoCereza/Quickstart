package org.firstinspires.ftc.teamcode.pedroPathing.AprilTagWebcam;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@TeleOp(name = "Teste April Tag", group = "Prototipos")
public class AprilTag extends OpMode {

    AprilTagProcessor.Builder aprilTagBuilder;
    AprilTagProcessor aprilTagProcessor;

    double tagPoseX, tagPoseY, tagPoseZ;

    AprilTagDetection aprilTagDetection;

    VisionPortal visionPortal;
    VisionPortal.Builder visionPortalBuilder;

    public void init(){
        //Inicializar identificador de April Tag
        aprilTagBuilder = new AprilTagProcessor.Builder();
        aprilTagBuilder.setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11);
        aprilTagBuilder.setTagLibrary(AprilTagGameDatabase.getCurrentGameTagLibrary());
        aprilTagBuilder.setDrawTagID(true); //mostra o ID
        aprilTagBuilder.setDrawTagOutline(true); //mostra o contorno
        aprilTagBuilder.setDrawAxes(true); //Mostra as linhas de marcação X, Y e Z
        aprilTagBuilder.setDrawCubeProjection(true); //mostra o cubo projetado pela leitura

        aprilTagProcessor = aprilTagBuilder.build();


        //Inicializar visionPortal
        visionPortalBuilder = new VisionPortal.Builder();

        visionPortalBuilder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        visionPortalBuilder.setCameraResolution(new Size(640, 480));
        visionPortalBuilder.addProcessor(aprilTagProcessor);
        visionPortalBuilder.setStreamFormat(VisionPortal.StreamFormat.YUY2);
        visionPortalBuilder.enableLiveView(true);
        visionPortalBuilder.setAutoStopLiveView(true);

        visionPortal = visionPortalBuilder.build();

        //init process
        visionPortal.setProcessorEnabled(aprilTagProcessor, true);

    }

    public void loop(){

        /*
        tagPoseX = aprilTagDetection.ftcPose.servoX;
        tagPoseY = aprilTagDetection.ftcPose.y;
        tagPoseZ = aprilTagDetection.ftcPose.z;

         */

        tagPoseY = aprilTagDetection.ftcPose.elevation; //angulo que a camera deve inclinar (cima/baixo) para apontar diretamente para o centro da aprilTag
        tagPoseX = aprilTagDetection.ftcPose.bearing; //angulo que a camera deve girar (esquerda/direita) para apontar diretamente para o centro da aprilTag
        tagPoseZ = aprilTagDetection.ftcPose.range; //distancia direta ao centro da April Tag

        telemetry.addData("ELEVATION: ", tagPoseY);
        telemetry.addData("BEARING: ", tagPoseX);
        telemetry.addData("RANGE: ", tagPoseZ);
        telemetry.update();
    }

}
