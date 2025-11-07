package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Teleoperado;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.ClassesRun.Interfaces.IRunMode;
import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.ClassesRun.RunMode;

@TeleOp(name = "Modo Teleoperado")
public class TeleOp_Class extends OpMode {
    RunMode run = new RunMode();

    double x, y, turn;

    String avisoChassi;

    boolean holdPosition;

    @Override
    public void init(){
        run.initialization(hardwareMap, 1);
    }

    @Override
    public void loop(){

        run.throwBalls(telemetry);

        telemetry.addLine(avisoChassi);
        telemetry.update();
    }

    @Override
    public void start(){
        run.startTeleop();
    }

    public void reverse(boolean reverse){

        x = gamepad1.left_stick_x;
        y = gamepad1.left_stick_y;
        turn = gamepad1.right_stick_x;

        if (!reverse){
            run.moveChassi(-y, -x, -turn);
            avisoChassi = "CHASSI NORMAL";

        }else {
            run.moveChassi(y, x, turn);
            avisoChassi = "CHASSI INVERTIDO!";
        }
    }
}
