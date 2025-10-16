package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Teleoperado;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class TeleOp_Class extends OpMode {
    RunMode run = new RunMode();
    int index;

    double x, y, turn;

    String mensagem, avisoChassi;
    boolean invertido;

    @Override
    public void init(){
        run.initialization(hardwareMap, 1);

    }

    @Override
    public void loop(){
        //run.moveLauncher(gamepad2.left_trigger);
        //run.moveSucker(gamepad2.right_trigger);

        x = gamepad1.left_stick_x;
        y = gamepad1.left_stick_y;
        turn = gamepad1.right_stick_x;

        if (gamepad1.dpad_left){
            index = 1;
            mensagem = "Aliança AZUL!";
        } else if (gamepad1.dpad_right) {
            index = 0;
            mensagem = "Aliança VERMELHA!";
        }

        telemetry.addLine(mensagem);
        telemetry.addData("INDEX: ", index);


        if (gamepad1.cross){
            invertido = false;
        } else if (gamepad1.circle) {
            invertido = true;
        }

        if (!invertido){
            run.moveChassi(-y, -x, -turn);
            avisoChassi = "CHASSI NORMAL";

        }else {
            run.moveChassi(y, x, turn);
            avisoChassi = "CHASSI INVERTIDO!";
        }

        telemetry.addLine(avisoChassi);
        telemetry.addLine("Rodando!");
        run.followAprilTag(index);


        telemetry.update();
    }

    @Override
    public void start(){
        run.startTeleop();
    }
}
