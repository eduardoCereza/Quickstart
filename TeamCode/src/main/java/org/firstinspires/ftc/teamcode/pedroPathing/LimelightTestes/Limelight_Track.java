package org.firstinspires.ftc.teamcode.pedroPathing.LimelightTestes;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.List;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Limelight Follow IMU Fast", group = "Testes")
public class Limelight_Track extends OpMode {

    private CRServo servo;
    private Limelight3A limelight;
    private IMU imu;

    private double anguloInicial = 0;
    private double ganhoServo = 0.01; // <- valor maior = mais rápido
    private double limite = 0.8;      // limite de potência do servo

    @Override
    public void init() {
        servo = hardwareMap.get(CRServo.class, "servoX");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters params = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        ));
        imu.initialize(params);


        anguloInicial = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        limelight.start();
    }

    @Override
    public void loop() {
        LLResult result = limelight.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
        double anguloAtual = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        double deltaAngulo = anguloAtual - anguloInicial;

        // Aplica compensação mais rápida
        double power = -deltaAngulo * ganhoServo;

        // Limita a potência para não forçar o servo
        power = Math.max(-limite, Math.min(limite, power));

            for (LLResultTypes.FiducialResult fr : fiducialResults) {
                if (fr.getFiducialId() == 24) {
                    servo.setPower(-power);
                }
            }


        telemetry.addData("Ângulo Robô", anguloAtual);
        telemetry.addData("Delta Ângulo", deltaAngulo);
        telemetry.addData("Servo Power", power);
        telemetry.update();
    }
}
