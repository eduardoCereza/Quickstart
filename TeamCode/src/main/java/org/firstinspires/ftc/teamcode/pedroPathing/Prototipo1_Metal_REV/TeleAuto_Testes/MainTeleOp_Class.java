package org.firstinspires.ftc.teamcode.pedroPathing.Prototipo1_Metal_REV.TeleAuto_Testes;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Testes - Protótipos")
public class MainTeleOp_Class extends OpMode {
    RunPeriods run;

    Pose startPose = new Pose(90, 80);

    @Override
    public void init(){
        run = new RunPeriods();
        run.initComponents(hardwareMap, 1, startPose);
    }

    @Override
    public void loop(){

        run.Lancar(telemetry);
    }
}
