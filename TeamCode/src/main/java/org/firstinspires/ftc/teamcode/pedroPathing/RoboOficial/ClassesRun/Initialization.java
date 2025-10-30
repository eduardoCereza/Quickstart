package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.ClassesRun;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class Initialization {

    public DcMotorEx flywheelB, flywheelA, slideL, slideR;
    public Servo servoY, servoX1, servoX2;
    public Limelight3A limelight3A;

    //public Timer pathTimer, opmodeTimer;
    public Follower follower;

    public final Pose goalRed = new Pose(122, 126, Math.toRadians(0));
    public final Pose goalBlue = new Pose(0,0,Math.toRadians(0));

    double g = 9.81;
    double r = 0.045;          // flywheel radius (m)
    int ticksAround = 28;
    double efficiency = 0.9;   // Estimated mechanical loss

    double thetaMinDeg = 15.0;
    double thetaMaxDeg = 40.0;
    double posMin = 0.1;
    double posMax = 1;

    public void initialization(HardwareMap hardwareMap, int pipelineIndex) {

        servoX1 = hardwareMap.get(Servo.class, "servoX1");
        servoX2 = hardwareMap.get(Servo.class, "servoX2");

        servoY = hardwareMap.get(Servo.class, "servoY");

        servoY.setDirection(Servo.Direction.REVERSE);

        flywheelB = hardwareMap.get(DcMotorEx.class, "flywheelB");
        flywheelA = hardwareMap.get(DcMotorEx.class, "flywheelA");

        flywheelB.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        flywheelA.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        flywheelB.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        flywheelA.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        slideL = hardwareMap.get(DcMotorEx.class, "slideL");
        slideR = hardwareMap.get(DcMotorEx.class, "slideR");

        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(pipelineIndex);
        limelight3A.start();

        follower = Constants.createFollower(hardwareMap);
        follower.update();
    }

    public void startTeleop(){
        follower.startTeleopDrive();
    }


}