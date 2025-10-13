package org.firstinspires.ftc.teamcode.pedroPathing.Prototipo1_Metal_REV.TeleAuto_Testes;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class InitClass {


    Servo servoY;
    CRServo servoX;

    DcMotorEx flywheelB,flywheelC, slideL,slideR;

    Follower follower;
    Limelight3A limelight3A;

    Timer pathTimer, opmodeTimer;


    private void buildPaths(){

    }

    public void initComponents(HardwareMap hardwareMap, int pipelineIndex, Pose startPose, boolean teleop){
        servoX = hardwareMap.get(CRServo.class, "servoX");
        servoY = hardwareMap.get(Servo.class, "servoY");

        servoY.setDirection(Servo.Direction.REVERSE);

        flywheelB = hardwareMap.get(DcMotorEx.class, "flywheelB");
        flywheelC = hardwareMap.get(DcMotorEx.class, "flywheelC");

        slideL = hardwareMap.get(DcMotorEx.class, "slideL");
        slideR = hardwareMap.get(DcMotorEx.class, "slideR");

        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(pipelineIndex);
        limelight3A.start();

        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);


        if (teleop){
            follower.startTeleopDrive();
        }else {
            buildPaths();
        }

    }



}
