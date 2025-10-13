package org.firstinspires.ftc.teamcode.pedroPathing.Prototipo1_Metal_REV.TeleAuto_Testes;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Testes - Protótipos")
public class MainTeleOp_Class extends OpMode {
    RunPeriods run = new RunPeriods();
    
    public void start(){
        run.follower.startTeleopDrive();
    }

    @Override
    public void init(){
        run.initComponents(hardwareMap,1);

    }

    @Override
    public void loop(){

        double x = gamepad1.left_stick_x;
        double y = gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;

        run.TeleOp(y, x, turn);
        run.acompanharAprilTag();

        //run.Lançador(telemetry);

        //run.Levantar(levantar, descer, ativador, desativador);
    }
}
