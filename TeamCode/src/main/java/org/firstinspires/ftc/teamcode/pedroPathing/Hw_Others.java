package org.firstinspires.ftc.teamcode.pedroPathing;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hw_Others{
    Servo servo1, servo2;
    DcMotor slide;
    public Hw_Others(Servo garra, Servo ponta, DcMotor slide) {
        this.servo1 = garra;
        this.servo2 = ponta;
        this.slide = slide;
    }

    public void init(HardwareMap map){
        servo1 = map.get(Servo.class, "servo1");
        servo2 = map.get(Servo.class, "servo2");

        slide = map.get(DcMotor.class,"slide");
    }
}
