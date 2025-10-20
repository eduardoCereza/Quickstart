package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Autonomous.RED;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.RunModeAuto;

public class AutoGPP_Red extends OpMode{

    RunModeAuto run;


    @Override
    public void start(){
        opmodeTimer.resetTimer();
        setPathState(0);
    }
    @Override
    public void loop() {
        //run.calculos();
        //run.followAprilTag(1);
        follower.update();
        autonomousPathUpdate();

        telemetry.addData("Path: ", pathState);
    }

    @Override
    public void init() {
        run = new RunModeAuto();

        pathTimer = new Timer();
        opmodeTimer =new Timer();
        opmodeTimer.resetTimer();

        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);



    }

}

