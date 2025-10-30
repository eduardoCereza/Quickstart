package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.ClassesRun;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class Initialization {

    public DcMotorEx flywheelB, flywheelC, slideL, slideR;
    public Servo servoY, servoX;
    public Limelight3A limelight3A;

    //public Timer pathTimer, opmodeTimer;
    public Follower follower;

    public final Pose goalRed = new Pose(122, 126, Math.toRadians(0));
    public final Pose goalBlue = new Pose(0,0,Math.toRadians(0));

    //Definição do ID;
    public int[] id = {24, 20};

    public double anguloX, power;

    public void initialization(HardwareMap hardwareMap, int pipelineIndex) {

        servoX = hardwareMap.get(Servo.class, "servoX");
        servoY = hardwareMap.get(Servo.class, "servoY");

        servoY.setDirection(Servo.Direction.REVERSE);
        
        flywheelB = hardwareMap.get(DcMotorEx.class, "flywheelB");
        flywheelC = hardwareMap.get(DcMotorEx.class, "flywheelC");

        //slideL = hardwareMap.get(DcMotorEx.class, "slideL");
        //slideR = hardwareMap.get(DcMotorEx.class, "slideR");

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