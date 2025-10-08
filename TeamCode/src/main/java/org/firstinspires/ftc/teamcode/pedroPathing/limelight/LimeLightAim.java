package org.firstinspires.ftc.teamcode.pedroPathing.limelight;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.List;

public class LimeLightAim {
    CRServo servo;
    double eixoX, power, angulo, velocity = 0.0375, cosseno;
    Limelight3A limelight3A;

    public LimeLightAim (HardwareMap hwMap){
        servo = hwMap.get(CRServo.class, "servoX");
        limelight3A = hwMap.get(Limelight3A.class, "limelight3A");
        limelight3A.pipelineSwitch(1);
    }

    public void mirar(){
        limelight3A.start();
        LLResult resultado = limelight3A.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = resultado.getFiducialResults();
        eixoX = resultado.getTx();

        for (LLResultTypes.FiducialResult fr : fiducialResults) {
            double k = 130.384048104052974;
            double hip = k / Math.sqrt(resultado.getTa());

            cosseno = eixoX / hip;

            angulo = Math.toDegrees(Math.acos(cosseno));
            power = (0.73304 * cosseno * velocity) * 200;

            if (fr.getFiducialId() == 24) {
                if (angulo != 90) {
                    servo.setPower(power);
                }

                if(angulo == 90){
                    servo.setPower(0);
                }
            }
        }
    }
}
