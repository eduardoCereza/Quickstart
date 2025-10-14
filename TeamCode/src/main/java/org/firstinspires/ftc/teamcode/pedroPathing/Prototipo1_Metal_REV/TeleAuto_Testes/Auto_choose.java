package org.firstinspires.ftc.teamcode.pedroPathing.Prototipo1_Metal_REV.TeleAuto_Testes;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.Prototipo1_Metal_REV.TeleAuto_Testes.Turret;

@Autonomous(name = "AUTONOMOUS DECODE")
public class Auto_choose extends OpMode {
    private Follower follower;
    private Timer pathTimer;
    private int pathState;
    private final Pose startRed = new Pose(120, 121, Math.toRadians(0));
    private final Pose startBlue = new Pose(22, 121, Math.toRadians(0));
    private final Pose preloadRed = new Pose(105, 107, Math.toRadians(0));
    private final Pose preloadBlue = new Pose(35, 107, Math.toRadians(0));
    private final Pose ppgRed = new Pose(125, 83, Math.toRadians(0));
    private final Pose ppgRedControl = new Pose(87, 79, Math.toRadians(0));
    private final Pose ppgBlue = new Pose(0, -55, Math.toRadians(0));
    private final Pose ppgBlueControl = new Pose(53, 83, Math.toRadians(0));
    private final Pose pgpRed = new Pose (124, 60, Math.toRadians(0));
    private final Pose pgpRedControl = new Pose(124, 60, Math.toRadians(0));
    private final Pose pgpBlue = new Pose(16, 60, Math.toRadians(0));
    private final Pose pgpBlueControl = new Pose(53, 53, Math.toRadians(0));
    private final Pose gppRed = new Pose(125, 35, Math.toRadians(0));
    private final Pose gppRedControl = new Pose(95, 24, Math.toRadians(0));
    private final Pose gppBlue = new Pose(15, 35, Math.toRadians(0));
    private final Pose gppBlueControl = new Pose(45, 24, Math.toRadians(0));
    private final Pose shoot = new Pose(70, 80, Math.toRadians(0));
    private PathChain a;
    Turret xoter = new Turret(hardwareMap);

    RunPeriods run = new RunPeriods();
    Init_Class init = new Init_Class();

    public void buildPaths() {

    }
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:


                break;
        }
    }
    @Override
    public void loop() {
        // Loop robot movement and odometry values
        follower.update();
        // Loop the finite-state machine
        autonomousPathUpdate();

        xoter.mirar();
        // Feedback to Driver Hub
        telemetry.addData("path state", pathState);
        telemetry.addData("servoX", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    /** This method is called once at the init of the OpMode. **/
    @Override
    public void init() {
        init.initComponentsAuto(hardwareMap, startRed, 1);
        pathTimer = new Timer();
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startRed);
        setPathState(0);
        buildPaths();
    }

    /** This method is called once at the start of the OpMode. It runs all the setup actions, including building paths and starting the path system **/
    @Override
    public void start() {
        setPathState(0);
    }
}