package org.firstinspires.ftc.teamcode.pedroPathing.Prototipo1_Metal_REV;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.pedroPathing.Prototipo1_Metal_REV.TeleAuto_Testes.RunPeriods;

//@TeleOp(name = "Prototipo 1 Teste", group = "Prototipo")
public class Prototipo1 extends OpMode {
    DcMotor motor, motor2, corerex, slide;


    @Override
    public void init(){
        motor = hardwareMap.get(DcMotor.class, "motor");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2 = hardwareMap.get(DcMotor.class, "motor2");
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //corerex = hardwareMap.get(DcMotor.class, "corerex");
        //corerex.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //slide = hardwareMap.get(DcMotor.class, "slide");
        //slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop(){
        if (gamepad1.right_trigger > 0.5){
            motor.setPower(1);
            motor2.setPower(1);
        }else if (gamepad1.left_trigger > 0.5){
            motor.setPower(-1);
            motor2.setPower(-1);
        }
        else {
            motor.setPower(0);
            motor2.setPower(0);

        }

        /*
        if (gamepad1.left_bumper){
            corerex.setPower(1);
        } else if (gamepad1.right_bumper) {
            corerex.setPower(-1);
        }else {
            corerex.setPower(0);
        }

        if (gamepad1.left_stick_y > 0.5){
            slide.setPower(1);
        } else if (gamepad1.left_stick_y < 0) {
            slide.setPower(-1);
        }else {
            slide.setPower(0);
        }

         */
    }
}
