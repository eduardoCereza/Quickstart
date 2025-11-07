package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.ClassesRun;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.ClassesRun.Interfaces.IInitialization;

import java.util.ArrayList;
import java.util.List;

public class Initialization implements IInitialization {

    public DcMotorEx flywheel, slideL, slideR;

    List<DcMotorEx> motors = new ArrayList<>();

    public Servo servoX1, servoX2;
    public Limelight3A limelight3A;

    //public Timer pathTimer, opmodeTimer;
    public Follower follower;

    public final Pose goalRed = new Pose(122, 126, Math.toRadians(0));
    public final Pose goalBlue = new Pose(0,0,Math.toRadians(0));
     // flywheel radius (m)

    public void initialization(HardwareMap hardwareMap, int pipelineIndex) {

        //servoX1 = hardwareMap.get(Servo.class, "servoX1");
        //servoX2 = hardwareMap.get(Servo.class, "servoX2");

        //servoX1.setPosition(0.5);
        //servoX2.setPosition(0.5);

        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");

        flywheel.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        flywheel.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);


        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(pipelineIndex);
        limelight3A.start();

        //follower = Constants.createFollower(hardwareMap);
        //follower.update();
    }

    public void startTeleop(){
        //follower.startTeleopDrive();
    }


}