package org.firstinspires.ftc.teamcode.pedroPathing.Prototipo1_Metal_REV;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.pedroPathing.limelight.LimeLightAim;

@TeleOp(name = "Move While Aim")
public class MoveWhileAim extends OpMode {
    private DcMotorEx rightFront, leftFront, rightRear, leftRear;
    LimeLightAim turret;
    public void drive(double power, double strafe, double turn){
        leftFront.setPower((power) + (strafe) + (turn));
        rightFront.setPower((power) - (strafe) - (turn));
        rightRear.setPower((power) + (strafe) - (turn));
        leftRear.setPower((power) - (strafe) + (turn));
    }

    @Override
    public void init(){
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        leftRear = hardwareMap.get(DcMotorEx.class, "leftRear");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightRear");

        rightRear.setDirection(DcMotorEx.Direction.REVERSE);
        turret = new LimeLightAim(hardwareMap);
    }
    @Override
    public void loop(){
        turret.mirar();

        drive(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
    }
}