package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "square test leandro")
public class Squared_test extends OpMode {
    private Follower follower;
    private Timer pathTimer;
    private int pathState;
    private final Pose startPose = new Pose(-58, -55, Math.toRadians(0));
    private final Pose leftRear = new Pose(-58, 0, Math.toRadians(0));
    private final Pose leftUp = new Pose(0, 0, Math.toRadians(0));
    private final Pose rightUp = new Pose(0, -55, Math.toRadians(0));

    private PathChain leftBaixo, leftSubir, rightCima, rightRight;

    public void buildPaths() {
        leftBaixo = follower.pathBuilder()
                .addPath(new BezierLine(startPose, leftRear))
                .setLinearHeadingInterpolation(startPose.getHeading(), leftRear.getHeading())
                .setBrakingStrength(1)
                .build();
        leftSubir = follower.pathBuilder()
                .addPath(new BezierLine(leftRear, leftUp))
                .setLinearHeadingInterpolation(leftRear.getHeading(), leftUp.getHeading())
                .setBrakingStrength(1)
                .build();

        rightCima = follower.pathBuilder()
                .addPath(new BezierLine(leftUp, rightUp))
                .setLinearHeadingInterpolation(leftUp.getHeading(), rightUp.getHeading())
                .setBrakingStrength(1)
                .build();

        rightRight = follower.pathBuilder()
                .addPath(new BezierLine(rightUp, startPose))
                .setLinearHeadingInterpolation(rightUp.getHeading(), startPose.getHeading())
                .setBrakingStrength(1)
                .build();

    }
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                if(!follower.isBusy()){
                    follower.followPath(leftBaixo, 1.0, true);
                    setPathState(1);
                }
                break;
            case 1:
                if (!follower.isBusy()) {
                    follower.followPath(leftSubir, 1.0, true);
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    follower.followPath(rightCima, 1.0, true);
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()) {
                    follower.followPath(rightRight, 1.0, true);
                    setPathState(0);
                }
                break;
            }
    }
    @Override
    public void loop() {
        // Loop robot movement and odometry values
        follower.update();
        // Loop the finite-state machine
        autonomousPathUpdate();


        // Feedback to Driver Hub
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    /** This method is called once at the init of the OpMode. **/
    @Override
    public void init() {
        pathTimer = new Timer();
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);
        buildPaths();
    }

    /** This method is called once at the start of the OpMode. It runs all the setup actions, including building paths and starting the path system **/
    @Override
    public void start() {
        setPathState(0);
    }
}