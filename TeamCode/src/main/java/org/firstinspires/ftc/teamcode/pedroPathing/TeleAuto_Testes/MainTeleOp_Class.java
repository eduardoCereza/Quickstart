package org.firstinspires.ftc.teamcode.pedroPathing.TeleAuto_Testes;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Init_Class;

public class MainTeleOp_Class extends OpMode {

    Init_Class initClass = new Init_Class();
    Servo servoY;
    CRServo servoX;

    Limelight3A limelight;

    @Override
    public void init(){
        initClass.setNameServo("servoX", "servoY");
        initClass.initServo(servoX, servoY, hardwareMap);

        initClass.initLimelight(limelight, hardwareMap, 1);
    }

    @Override
    public void loop(){

    }
}
