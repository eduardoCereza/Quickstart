package org.firstinspires.ftc.teamcode.pedroPathing.Prototipo1_Metal_REV.TeleAuto_Testes;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.Timer;

public class MainAuto_Class extends OpMode {
    RunPeriods run;
    private final Pose startPose = new Pose(28.5,128, Math.toRadians(180));


    @Override
    public void init(){
        run = new RunPeriods();
        
    }

    @Override
    public void loop(){

    }
    @Override
    public void start() {
        run.opmodeTimer.resetTimer();
        run.setPathState(0);
    }

}
