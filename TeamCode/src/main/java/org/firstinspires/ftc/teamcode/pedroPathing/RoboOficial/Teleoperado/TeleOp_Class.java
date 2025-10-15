package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Teleoperado;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class TeleOp_Class extends OpMode {
    RunMode run = new RunMode();

    @Override
    public void init(){
        run.initialization(hardwareMap, 1);

    }

    @Override
    public void loop(){
        //run.moveLauncher(gamepad2.left_trigger);
        //run.moveSucker(gamepad2.right_trigger);
        run.followAprilTag();
        run.moveChassi(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
    }

    @Override
    public void start(){
        run.follower.startTeleopDrive();
    }
}
