package org.firstinspires.ftc.teamcode.pedroPathing.Prototipos;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;

import org.opencv.core.Mat;

import java.util.List;

@Autonomous
public class xoter extends OpMode {
    final double h_tag = 74, h_cam = 40, Offset = 45, g = 980, kslip = 0.95, raio = 5.6, k = 130.384048104052974, velocity_servoX = 0.0375, pesoBola = 74.8, pesoTurret = 0;
    double ca, cg, x, d, dr, ta,tx, v, angulo, Vborda, rev, rpm, seno, power, forcaPesoTotal;
    private Limelight3A limelight;
    private CRServo servoX;

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight3A");
        limelight.pipelineSwitch(0); //apriltag 3d

        servoX = hardwareMap.get(CRServo.class, "servoX");
    }
    public void loop(){
        LLResult resultado = limelight.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = resultado.getFiducialResults();
        tx = resultado.getTx();
        ta = resultado.getTa();
        //calculo da distancia do xoter até a apriltag
        d = k / Math.sqrt(ta);

        //calculo distancia real do xoter até o goal

        //calculo do angulo
        seno = tx / dr;
        angulo = Math.toDegrees(Math.asin(seno));

        //calculo da velocidade do servo X
        power = (forcaPesoTotal * seno * velocity_servoX) * 100;

        //calculo da velocidade da flywheel
        v = Math.sqrt(g * Math.pow(dr , 2) / 2 * Math.cos(angulo) * (dr * Math.tan(angulo) - cg));

        //transformação da velocidade pra rpm
        Vborda = v / kslip;
        rev = Vborda / 2 * 6.28 * raio;
        rpm = rev * 60;

        if(resultado != null && resultado.isValid()){

            for (LLResultTypes.FiducialResult fr : fiducialResults) {
            telemetry.addData("fiducial", "ID: %d, family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());

            if (fr.getFiducialId() == 24) {
                if (angulo != 0) {
                    servoX.setPower(power);
                }
                if (angulo == 0) {
                    servoX.setPower(0);
                }
                telemetry.addData("Power Servo X:", power);
                telemetry.addData("angulo:", angulo);
            }
            }
        }
        telemetry.addData("Velocidade flywheel:", rpm);
        telemetry.addData("Distancia limelight3A goal:", dr);
        telemetry.addData("Distancia limelight3A apriltag:", d);
        telemetry.addData("Ta:", ta);
        telemetry.addData("Tx:", tx);
        telemetry.update();
    }
    public void start(){
        limelight.start();
    }
}
