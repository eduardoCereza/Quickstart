package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Teleoperado;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.ClassesRun.RunMode;

@TeleOp(name = "Modo Teleoperado")
public class TeleOp_Class extends OpMode {
    RunMode run = new RunMode();

    double x, y, turn;

    String avisoChassi;

    @Override
    public void init(){
        run.initialization(hardwareMap, 1);


    }

    @Override
    public void loop(){

        if (gamepad1.cross){
            reverse(false);
        } else if (gamepad1.circle) {
            reverse(true);
        }

        run.followTag(run.follower.getPose().getX(), run.follower.getPose().getY(), run.follower.getPose().getHeading(), false, telemetry);

        telemetry.update();
        telemetry.addLine(avisoChassi);

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
