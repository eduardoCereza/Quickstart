package org.firstinspires.ftc.teamcode.pedroPathing.Prototipos;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;

import java.util.List;

@Autonomous
public class Shooter_Movi_Obl extends OpMode {
    private Limelight3A limelight;
    private CRServo servoX;

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0); //apriltag 3d

        servoX = hardwareMap.get(CRServo.class, "servoX");
    }
    public void loop(){
        LLResult resultado = limelight.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = resultado.getFiducialResults();

        if(resultado != null && resultado.isValid()){

            for (LLResultTypes.FiducialResult fr : fiducialResults) {
            telemetry.addData("fiducial", "ID: %d, family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());

            }
        }
    }
    public void start(){
        limelight.start();
    }
}
