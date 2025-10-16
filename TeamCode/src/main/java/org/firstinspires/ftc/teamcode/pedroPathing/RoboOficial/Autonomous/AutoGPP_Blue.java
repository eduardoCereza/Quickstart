package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class AutoGPP_Blue extends OpMode {

    private Follower follower;

    private SystemLauncher systemLauncher;
    public com.pedropathing.util.Timer pathTimer, actionTimer, opmodeTimer;

    public int pathState;

    public final Pose startPose = new Pose(28.5, 128, Math.toRadians(180)); // Start Pose of our robot.
    public final Pose bolaVerde = new Pose(60, 85, Math.toRadians(135)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    public final Pose bolaRoxa1 = new Pose(37, 121, Math.toRadians(0)); // Highest (First Set) of Artifacts from the Spike Mark.
    public final Pose bolaRoxa2 = new Pose(43, 130, Math.toRadians(0)); // Middle (Second Set) of Artifacts from the Spike Mark.

    public final Pose goal = new Pose();
    public PathChain moveToBallPurple1, moveToBallPurple2, moveToBallGreen, moveToGoal;

    public void buildPaths() {
        moveToBallGreen = follower.pathBuilder()
                .addPath(new BezierLine(startPose, bolaVerde))
                .setLinearHeadingInterpolation(startPose.getHeading(), bolaVerde.getHeading())
                .build();

        moveToBallPurple1 = follower.pathBuilder()
                .addPath(new BezierLine(bolaVerde, bolaRoxa1))
                .setLinearHeadingInterpolation(bolaVerde.getHeading(), bolaRoxa1.getHeading())
                .build();

        moveToBallPurple2 = follower.pathBuilder()
                .addPath(new BezierLine(bolaRoxa1, bolaRoxa2))
                .setLinearHeadingInterpolation(bolaRoxa1.getHeading(), bolaRoxa2.getHeading())
                .build();

        moveToGoal = follower.pathBuilder()
                .addPath(new BezierLine(follower.getPose(), goal))
                .setLinearHeadingInterpolation(follower.getHeading(), goal.getHeading())
                .build();
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                systemLauncher.moveLauncher("blue");
                follower.followPath(moveToBallGreen);
                systemLauncher.suck();
                systemLauncher.launchBall(1);
                setPathState(1);
                break;
            case 1:
                if(!follower.isBusy()) {
                    systemLauncher.moveLauncher("blue");
                    follower.followPath(moveToBallPurple1,true);
                    systemLauncher.suck();
                    systemLauncher.launchBall(1);
                    setPathState(2);
                }
                break;
            case 2:
                if(!follower.isBusy()) {
                    systemLauncher.moveLauncher("blue");
                    follower.followPath(moveToBallPurple2,true);
                    systemLauncher.suck();
                    systemLauncher.launchBall(1);
                    //setPathState(3);
                }
                break;
        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }
    @Override
    public void loop() {

        follower.update();
        autonomousPathUpdate();

        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();
        systemLauncher = new SystemLauncher();


        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);

    }

    @Override
    public void init_loop() {}

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    @Override
    public void stop() {}
}
