package org.firstinspires.ftc.teamcode.pedroPathing.Prototipo1_Metal_REV.TeleAuto_Testes;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Testes - Protótipos")
public class MainTeleOp_Class extends OpMode {

    Init_Class initClass = new Init_Class();
    RunPeriods run = new RunPeriods();

    Pose startPose = new Pose(90, 80);

    @Override
    public void init(){
        initClass.initComponentsAuto(hardwareMap, startPose,1);
    }

    @Override
    public void loop(){

        double x = gamepad1.left_stick_x;
        double y = gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;

        boolean ativador = gamepad1.a;
        boolean desativador = gamepad1.b;
        float levantar = gamepad1.left_trigger;
        float descer = gamepad1.right_trigger;

        //run.TeleOp(y, x, turn);

        //run.Lançador(telemetry);

        run.Levantar(levantar, descer, ativador, desativador);
    }
}
