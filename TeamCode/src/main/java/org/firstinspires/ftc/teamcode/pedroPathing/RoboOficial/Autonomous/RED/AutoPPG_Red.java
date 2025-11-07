package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Autonomous.RED;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.ClassesRun.RobotDecodeOficial;

@Configurable
@Autonomous(name = "ppg")
public class AutoPPG_Red extends OpMode {
    private Follower follower;
    private Timer pathTimer;
    private int pathState;

    public static Pose autoEndPose;
    private final Pose startRed = new Pose(104, 109, Math.toRadians(0));
    private final Pose preloadRed = new Pose(75, 70, Math.toRadians(0));
    private final Pose ppgRed = new Pose(111, 69, Math.toRadians(0));
    private final Pose ppgRedControl = new Pose(75, 71, Math.toRadians(0));
    private final Pose pgpRed = new Pose (111, 42, Math.toRadians(0));
    private final Pose pgpRedControl = new Pose(75, 42, Math.toRadians(0));
    private final Pose gppRed = new Pose(111, 23, Math.toRadians(0));
    private final Pose gppRedControl = new Pose(75, 19, Math.toRadians(0));
    private final Pose shoot = new Pose(75,70,Math.toRadians(0));
    private PathChain preload, ppg, pgp, gpp, shootppg, shootpgp, shootgpp;

    RobotDecodeOficial turret = new RobotDecodeOficial(hardwareMap, 1, telemetry);

    public void buildPaths() {
        preload = follower.pathBuilder()
                .addPath(new BezierLine(startRed, preloadRed))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        ppg = follower.pathBuilder()
                .addPath(new BezierCurve(preloadRed, ppgRedControl, ppgRed))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        pgp = follower.pathBuilder()
                .addPath(new BezierCurve(preloadRed, pgpRedControl, pgpRed))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        gpp = follower.pathBuilder()
                .addPath(new BezierCurve(preloadRed, gppRedControl, gppRed))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        shootppg = follower.pathBuilder()
                .addPath(new BezierLine(ppgRed, shoot))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
        shootpgp = follower.pathBuilder()
                .addPath(new BezierLine(pgpRed, shoot))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        shootgpp = follower.pathBuilder()
                .addPath(new BezierLine(gppRed, shoot))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();


    }
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(preload, 0.7, false);
                setPathState(11);
                break;
            case 1:
                if(!follower.isBusy() && pathState == 1){
                    follower.followPath(ppg, 0.7, false);
                    setPathState(2);

                }
                break;
            case 2:
                if(!follower.isBusy() && pathState == 2){
                    follower.followPath(shootppg, 0.7, true);
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy() && pathState == 3){
                    follower.followPath(pgp, 0.7, false);
                    setPathState(4);

                }
                break;
            case 4:
                if (!follower.isBusy() && pathState == 4){
                    follower.followPath(shootpgp, 0.7, true);
                    setPathState(5);

                }
                break;
            case 5:
                if(!follower.isBusy() && pathState == 5){
                    follower.followPath(gpp, 0.7, false);
                    setPathState(6);

                }
                break;
            case 6:
                if(!follower.isBusy() && pathState == 6){
                    follower.followPath(shootgpp, 0.7, true);
                    setPathState(7);
                }
                break;
            case 7:
                break;
            case 11:
                if (!follower.isBusy()){

                }
        }
    }
    @Override
    public void loop() {
        // Loop robot movement and odometry values
        follower.update();
        // Loop the finite-state machine
        autonomousPathUpdate();

        // Feedback to Driver Hub
        telemetry.update();
    }

    /** This method is called once at the init of the OpMode. **/
    @Override
    public void init() {
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

    @Override
    public void stop(){
        autoEndPose = follower.getPose();
        telemetry.update();
    }
}