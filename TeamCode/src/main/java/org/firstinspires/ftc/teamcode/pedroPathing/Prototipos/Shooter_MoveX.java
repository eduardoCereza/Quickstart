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
    private final int frameWidth = 1280;

    CRServo servo;

    double eixoX, power = 0.3, error;
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


            if (fr.getFiducialId() == 24) {
                if (eixoX > 0.1) {
                    servo.setPower(power);
                } else if (eixoX < -0.1) {
                    servo.setPower(-power);
                }else{
                    servo.setPower(0);
                }

            }
        }
    }

}



