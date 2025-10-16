package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Teleoperado;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class TeleOp_Class extends OpMode {
    RunMode run = new RunMode();
    int index;

    @Override
    public void init(){
        run.initialization(hardwareMap, 1);

    }

    @Override
    public void loop(){
        //run.moveLauncher(gamepad2.left_trigger);
        //run.moveSucker(gamepad2.right_trigger);

        if (gamepad1.dpad_left){
            index = 1;
            telemetry.addLine("Aliança AZUL!");
        } else if (gamepad1.dpad_right) {
            index = 0;
            telemetry.addLine("Aliança VERMELHA!");
        }

        run.followAprilTag(index);
        run.moveChassi(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

        telemetry.update();
    }

    @Override
    public void start(){
        run.follower.startTeleopDrive();
    }
}
