package org.firstinspires.ftc.teamcode.pedroPathing.Prototipo1_Metal_REV.TeleAuto_Testes;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class Init_Class {

    public DcMotorEx flywheelB, flywheelC, slideL, slideR;
    public CRServo servoX;
    public Servo servoY;
    public Limelight3A limelight3A;

    Follower follower;
    public void initComponentsAuto(HardwareMap hardwareMap, Pose startPose, int pipelineIndex) {
        servoX = hardwareMap.get(CRServo.class, "servoX");
        servoY = hardwareMap.get(Servo.class, "servoY");

        flywheelB = hardwareMap.get(DcMotorEx.class, "left");
        flywheelC = hardwareMap.get(DcMotorEx.class, "flywheelC");

        slideL = hardwareMap.get(DcMotorEx.class, "slideL");
        slideR = hardwareMap.get(DcMotorEx.class, "slideR");

        limelight3A = hardwareMap.get(Limelight3A.class, "limelight3A");
        limelight3A.pipelineSwitch(pipelineIndex);

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);
        follower.update();
    }

}
