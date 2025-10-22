package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp
public class testes extends OpMode {

    CRServo servo,servo2, servo3;

    @Override
    public void init() {
        servo = hardwareMap.get(CRServo.class, "servo");
        servo2 = hardwareMap.get(CRServo.class, "servo2");
        servo3 = hardwareMap.get(CRServo.class, "servo3");


    }

    @Override
    public void loop() {
        servo.setPower(-1);
        servo2.setPower(-1);
        servo3.setPower(-1);
    }
}
