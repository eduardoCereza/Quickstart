package org.firstinspires.ftc.teamcode.pedroPathing.TeleAuto_Testes;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class Init_Class {

    public DcMotorEx left, right;
    public CRServo x;
    public Servo y;
    public Limelight3A limelight;

    Follower follower;
    public void initComponents(HardwareMap hardwareMap, Pose startPose, int pipelineIndex) {
        x = hardwareMap.get(CRServo.class, "servoX");
        y = hardwareMap.get(Servo.class, "servoY");

        left = hardwareMap.get(DcMotorEx.class, "left");
        right = hardwareMap.get(DcMotorEx.class, "right");


        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(pipelineIndex);

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);
        follower.update();
        follower.startTeleopDrive();
    }

}
