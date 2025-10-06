package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Init_Class {
    String nameServoX, nameServoY, nameMotorLeft, nameMotorRight;

    public void setNameServo(String nameServoX, String nameServoY){
        this.nameServoX = nameServoX;
        this.nameServoY = nameServoY;
    }

    public void setNameMotor(String nameMotorLeft, String nameMotorRight){
        this.nameMotorLeft = nameMotorLeft;
        this.nameMotorRight = nameMotorRight;
    }
    public void initServo(CRServo x, Servo y, HardwareMap hardwareMap){
        x = hardwareMap.get(CRServo.class, nameServoX);
        y = hardwareMap.get(Servo.class, nameServoY);
    }

    public void initMotor(DcMotorEx left, DcMotorEx right, HardwareMap hardwareMap){
        left = hardwareMap.get(DcMotorEx.class, nameMotorLeft);
        right = hardwareMap.get(DcMotorEx.class, nameMotorRight);
    }

    public void initLimelight(Limelight3A limelight, HardwareMap hardwareMap, Integer indice){
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(indice);
    }
}
