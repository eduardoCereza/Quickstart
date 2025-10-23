package org.firstinspires.ftc.teamcode.pedroPathing.LimelightTestes;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp
public class LimelightFollow extends OpMode {

    private CRServo servo;
    private Limelight3A limelight;
    private IMU imu;

    // --- Constantes de controle ---
    private static final double KP_HEADING = 0.015;  // compensação de heading
    private static final double KP_VISION = 0.010;   // correção pela visão
    private static final double DEAD_BAND = 1.0;     // tolerância central (graus)
    private static final double MAX_POWER = 0.8;     // limite de velocidade
    private static final long VISION_TIMEOUT_MS = 400; // tempo pra perder visão
    private static final double SEARCH_SPEED = 1;   // velocidade de busca contínua

    // --- Variáveis ---
    private double lastHeadingDeg = 0;
    private long lastVisionSeen = 0;
    private double visionCorrection = 0;
    private double lastTx = 0;
    private double tx = 0;
    private double power = 0;
    private boolean searching = false;
    private int searchDir = 1; // 1 = direita, -1 = esquerda

    @Override
    public void init() {
        servo = hardwareMap.get(CRServo.class, "servoX");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(1);
        limelight.start();

        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters params = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        ));
        imu.initialize(params);

        telemetry.addLine("Limelight servo tracking ready ✅");
    }

    @Override
    public void loop() {
        double currentHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        double headingDelta = wrapAngle(currentHeading - lastHeadingDeg);
        double headingCorrection = -KP_HEADING * headingDelta;

        LLResult result = limelight.getLatestResult();

        if (result != null && result.isValid()) {
            // 🔹 TAG VISÍVEL
            tx = result.getTx();

            if (Math.abs(tx) > DEAD_BAND) {
                visionCorrection = KP_VISION * tx;
            } else {
                visionCorrection = 0;
            }

            // Atualiza direção de busca futura
            lastTx = tx;
            searchDir = (lastTx > 0) ? 1 : -1;

            // Combina heading + visão
            power = headingCorrection + visionCorrection;
            power = Math.max(-MAX_POWER, Math.min(MAX_POWER, power));
            servo.setPower(power);

            // Marca tempo de última visão e reseta modo busca
            lastVisionSeen = System.currentTimeMillis();
            searching = false;

        }
        if (!result.isValid()){
            // 🔸 TAG PERDIDA
            do{
                if (lastTx > 0){
                    servo.setPower(1);
                } else if (lastTx < 0) {
                    servo.setPower(-1);
                }
            }while (result.isValid());
        }

        lastHeadingDeg = currentHeading;

        // Telemetria
        telemetry.addData("Heading (°)", currentHeading);
        telemetry.addData("Heading Δ (°)", headingDelta);
        telemetry.addData("TX (last)", lastTx);
        telemetry.addData("Servo Power", servo.getPower());
        telemetry.addData("Vision Corr", visionCorrection);
        telemetry.addData("Searching", searching);
        telemetry.addData("Search Dir", searchDir > 0 ? "➡️ Right" : "⬅️ Left");
        telemetry.addData("Vision Age (ms)", System.currentTimeMillis() - lastVisionSeen);
        telemetry.update();
    }

    private double wrapAngle(double angle) {
        while (angle > 180) angle -= 360;
        while (angle < -180) angle += 360;
        return angle;
    }
}
