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

public class AutoPGP_Red extends OpMode{

    RunModeAuto run;
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;

    private final Pose startPose = new Pose(104, 109, Math.toRadians(0));

    private final Pose scorePose = new Pose(75, 70, Math.toRadians(0));
    private final Pose pgp = new Pose(111 ,42 , Math.toRadians(0));
    private final Pose pgpReference = new Pose(75 ,42 , Math.toRadians(0));

    private final Pose gpp = new Pose(111 , 23, Math.toRadians(0));
    private final Pose gppReference = new Pose(75 ,19 ,Math.toRadians(0));

    private final Pose ppg = new Pose(111, 69 ,Math.toRadians(0));
    private final Pose ppgReference = new Pose(75 ,71 , Math.toRadians(0));


    private PathChain firstRelease, startGetOrder1, launchOrder1, launchOrder2, launchOrder3, getOrder1, getOrder2, getOrder3;


    public void buildPaths(){
        //Lançar as 3 primeiras bolas na ordem
        firstRelease = follower.pathBuilder()
                .addPath(new BezierLine(startPose, scorePose))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        //Pegar as 3 bolas na ordem
        getOrder1 = follower.pathBuilder()
                .addPath(new BezierCurve(scorePose, pgpReference, pgp))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();


        //Lanças as 3 bolas
        launchOrder1 = follower.pathBuilder()
                .addPath(new BezierLine(pgp, scorePose))
                .setLinearHeadingInterpolation(pgp.getHeading(), scorePose.getHeading())
                .build();

        //Pegar segunda sequencia (3 bolas) fora da ordem
        getOrder2 = follower.pathBuilder()
                .addPath(new BezierCurve(scorePose, ppgReference, ppg))
                .setLinearHeadingInterpolation(scorePose.getHeading(), ppg.getHeading())
                .build();
        //Lançar segunda sequencia
        launchOrder2 = follower.pathBuilder()
                .addPath(new BezierLine(ppg, scorePose))
                .setLinearHeadingInterpolation(ppg.getHeading(), scorePose.getHeading())
                .build();

        //Pegar ultima sequencia
        getOrder3 = follower.pathBuilder()
                .addPath(new BezierCurve(scorePose, gppReference,gpp))
                .setLinearHeadingInterpolation(scorePose.getHeading(), gpp.getHeading())
                .build();
        //Lançar ultima sequencia
        launchOrder3 = follower.pathBuilder()
                .addPath(new BezierLine(gpp, scorePose))
                .setLinearHeadingInterpolation(gpp.getHeading(), scorePose.getHeading())
                .build();
    }

    public void autonomousPathUpdate(){
        switch (pathState){
            case 0:
                follower.followPath(firstRelease, true);
                //run.outTake();
                setPathState(1);
                break;

            case 1:
                if (!follower.isBusy()) {
                    follower.followPath(getOrder1, true);
                    //run.intake();
                    setPathState(2);
                }
                break;


            case 2:
                if (!follower.isBusy()){
                    follower.followPath(launchOrder1, true);
                    //run.outTake();
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()){
                    follower.followPath(getOrder2, true);
                    //run.intake();
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()){
                    follower.followPath(launchOrder2, true);
                    //run.outTake();
                    setPathState(5);
                }
                break;
            case 5:
                if (!follower.isBusy()){
                    follower.followPath(getOrder3, true);
                    //run.intake();
                    setPathState(6);
                }
                break;
            case 6:
                if (!follower.isBusy()){
                    follower.followPath(launchOrder3, true);
                    //run.outTake();
                }
                break;
        }

    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

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

