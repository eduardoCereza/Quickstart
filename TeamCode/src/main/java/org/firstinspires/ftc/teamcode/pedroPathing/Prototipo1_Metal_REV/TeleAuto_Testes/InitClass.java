package org.firstinspires.ftc.teamcode.pedroPathing.Prototipo1_Metal_REV.TeleAuto_Testes;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class InitClass {


    Servo servoY;
    CRServo servoX;

    DcMotorEx flywheelB,flywheelC, slideL,slideR;

    Follower follower;
    Limelight3A limelight3A;

    public void initComponents(HardwareMap hardwareMap, int pipelineIndex){
        servoX = hardwareMap.get(CRServo.class, "servoX");
        servoY = hardwareMap.get(Servo.class, "servoY");

        servoY.setDirection(Servo.Direction.REVERSE);

        flywheelB = hardwareMap.get(DcMotorEx.class, "flywheelB");
        flywheelC = hardwareMap.get(DcMotorEx.class, "flywheelC");

        slideL = hardwareMap.get(DcMotorEx.class, "slideL");
        slideR = hardwareMap.get(DcMotorEx.class, "slideR");

        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(pipelineIndex);

        /*
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);
        follower.update();
        follower.startTeleopDrive();

         */
    }
}
