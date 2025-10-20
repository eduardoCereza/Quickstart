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

    public double anguloX, anguloY;
    double angulomaior, delta, hipmenor, hipmaior, basemenor, basemaior;

    // Variáveis relacionadas aos servos e controle de força no eixo X.
    double posdoservoy, eixoX, eixoY, power, servoXPosRad, servoYPosRad, forcaPesoTotal;

    // Variáveis da flywheel (cinemática do disparo).
    double Vborda, rev, rpm, v, ta;

    // Constantes de projeto/físicas (unidades precisam ser coerentes — ver observações ao final).
    // k: fator para estimar distância a partir da área (ta) da tag; velocity: ganho simples para servoX;
    // pesos em gramas; alturas em mm (?) ; g em cm/s² (980); k_lip: perda por atrito (lip), r: raio da flywheel.
    final double k = 186.5409338456308, velocity = 0.0375, pesoTurret = 0, pesoBola = 74.8,
            alturamenor = 74, alturamaior = 124, g = 980, k_lip = 0.95, r = 4.5;


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