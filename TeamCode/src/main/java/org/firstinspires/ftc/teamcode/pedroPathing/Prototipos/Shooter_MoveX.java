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

    double eixoX;
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

        if (resultado != null && resultado.isValid()) {
        }

        eixoX = resultado.getTx();


        for (LLResultTypes.FiducialResult fr : fiducialResults){
            if (fr.getFiducialId() == 24){
                if (eixoX > 0.1){
                    servo.setPower(0.5);
                } else if (eixoX < 0) {
                    servo.setPower(-0.5);
                }else {
                    servo.setPower(0);
                }
            }
        }

    }

    void moverServoX(double kp){

    }
}



