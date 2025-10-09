package org.firstinspires.ftc.teamcode.pedroPathing.Prototipo1_Metal_REV.TeleAuto_Testes;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.Timer;

public class MainAuto_Class extends OpMode {

    RunPeriods run = new RunPeriods();
    private Timer pathTimer, actionTimer, opmodeTimer;

    private int pathState;

    private final Pose startPose = new Pose(28.5,128, Math.toRadians(180));

    public void buildPaths(){
    }
    @Override
    public void init(){
        run.initComponents(hardwareMap, 1, startPose);
    }

    @Override
    public void loop(){

    }

}
