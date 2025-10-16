package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.util.Timer;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class Initialization {

    public DcMotorEx flywheelB, flywheelC, slideL, slideR;
    public CRServo servoX, sugador;
    public Servo servoY;
    public Limelight3A limelight3A;

    //public Timer pathTimer, opmodeTimer;
    public Follower follower;


    //Definição do ID;
    public int[] id = {24, 20};

    //Variáveis para o servo do eixoX
    public double eixoX, seno, ta, anguloX, power, forcaPesoTotal;

    //Variáveis para o servo do eixoY
    public double eixoY, v, hipotenusaMenor, hipotenusaMaior,
            baseMenor, baseMaior,
            positionServoY,
            delta, angulomaior,anguloY, tangente,
            Vborda, rev, rpm;

    //Constantes necessárias
    public final double k = 186.5409338456308,
            g = 980, velocityServoX = 0.0375,
            pesoBola = 74.8, pesoTurret = 0,
            alturaMenor = 74, alturaMaior = 124,
            k_lip = 0.95, r = 4.5;


    public void initialization(HardwareMap hardwareMap, int pipelineIndex) {

        servoX = hardwareMap.get(CRServo.class, "servoX");
        //servoY = hardwareMap.get(Servo.class, "servoY");

        //servoY.setDirection(Servo.Direction.REVERSE);

        //sugador = hardwareMap.get(CRServo.class, "sugador");

        //flywheelB = hardwareMap.get(DcMotorEx.class, "flywheelB");
        //flywheelC = hardwareMap.get(DcMotorEx.class, "flywheelC");

        //slideL = hardwareMap.get(DcMotorEx.class, "slideL");
        //slideR = hardwareMap.get(DcMotorEx.class, "slideR");

        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(pipelineIndex);
        limelight3A.start();

        follower = Constants.createFollower(hardwareMap);
        follower.update();
    }

    public void startTeleop(){
        follower.startTeleopDrive();
    }


}