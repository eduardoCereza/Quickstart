package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
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


    public Timer pathTimer, actionTimer, opmodeTimer;
    public int pathState;

    public final Pose startPose = new Pose(104, 109, Math.toRadians(0));

    public final Pose scorePose = new Pose(75, 70, Math.toRadians(0));
    public final Pose pgp = new Pose(111 ,42 , Math.toRadians(0));
    public final Pose pgpReference = new Pose(75 ,42 , Math.toRadians(0));

    public final Pose gpp = new Pose(111 , 23, Math.toRadians(0));
    public final Pose gppReference = new Pose(75 ,19 ,Math.toRadians(0));

    public final Pose ppg = new Pose(111, 69 ,Math.toRadians(0));
    public final Pose ppgReference = new Pose(75 ,71 , Math.toRadians(0));


    private PathChain firstRelease, startGetOrder1, launchOrder1, launchOrder2, launchOrder3, getOrder1, getOrder2, getOrder3;

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


    public void buildPaths(){
        //Lançar as 3 primeiras bolas na ordem
        firstRelease = follower.pathBuilder()
                .addPath(new BezierLine(startPose, scorePose))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        //Pegar as 3 bolas na ordem
        getOrder1 = follower.pathBuilder()
                .addPath(new BezierCurve(scorePose, gppReference, gpp))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();


        //Lanças as 3 bolas
        launchOrder1 = follower.pathBuilder()
                .addPath(new BezierLine(gpp, scorePose))
                .setLinearHeadingInterpolation(gpp.getHeading(), scorePose.getHeading())
                .build();

        //Pegar segunda sequencia (3 bolas) fora da ordem
        getOrder2 = follower.pathBuilder()
                .addPath(new BezierCurve(scorePose, pgpReference, pgp))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pgp.getHeading())
                .build();
        //Lançar segunda sequencia
        launchOrder2 = follower.pathBuilder()
                .addPath(new BezierLine(pgp, scorePose))
                .setLinearHeadingInterpolation(pgp.getHeading(), scorePose.getHeading())
                .build();

        //Pegar ultima sequencia
        getOrder3 = follower.pathBuilder()
                .addPath(new BezierCurve(scorePose, ppgReference,ppg))
                .setLinearHeadingInterpolation(scorePose.getHeading(), ppg.getHeading())
                .build();
        //Lançar ultima sequencia
        launchOrder3 = follower.pathBuilder()
                .addPath(new BezierLine(ppg, scorePose))
                .setLinearHeadingInterpolation(ppg.getHeading(), scorePose.getHeading())
                .build();


    }

}