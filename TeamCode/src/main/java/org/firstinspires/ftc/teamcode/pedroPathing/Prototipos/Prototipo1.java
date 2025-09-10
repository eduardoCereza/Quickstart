package org.firstinspires.ftc.teamcode.pedroPathing.Prototipos;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Prototipo 1 Teste", group = "Prototipo")
public class Prototipo1 extends OpMode {
    DcMotor motor, motor2;

    @Override
    public void init(){
        motor = hardwareMap.get(DcMotor.class, "motor");
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2 = hardwareMap.get(DcMotor.class, "motor2");
        motor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
    }
}
