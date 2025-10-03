package org.firstinspires.ftc.teamcode.pedroPathing.Prototipos;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

import java.util.List;

@TeleOp(group = "Prototipo")
public class Shooter_MoveX extends OpMode {
    CRServo servo;
    double eixoX, power, angulo, velocity = 0.0375, seno;
    Limelight3A limelight3A;

    public void init() {
        servo = hardwareMap.get(CRServo.class, "servoX");
        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(1);
        limelight3A.start();

    }

    public void loop() {

        LLResult resultado = limelight3A.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = resultado.getFiducialResults();
        eixoX = resultado.getTx();

        for (LLResultTypes.FiducialResult fr : fiducialResults) {
            telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());

            double k = 130.384048104052974;
            double hip = k / Math.sqrt(resultado.getTa());

            seno = eixoX / hip;
            angulo = Math.toDegrees(Math.asin(seno));
            power = (0.73304 * seno * velocity) * 100;

            if (fr.getFiducialId() == 24) {
                if (angulo != 90) {
                    servo.setPower(power);
                }

                if(angulo == 90){
                    servo.setPower(0);
                }
                telemetry.addData("Power: ", power);
                telemetry.addData("Angulo: ", angulo);
            }
        }
    }

}



