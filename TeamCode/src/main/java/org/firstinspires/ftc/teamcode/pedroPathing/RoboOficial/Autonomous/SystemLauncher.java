package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Autonomous;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Initialization;
import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.RunMode;

import java.util.List;

public class SystemLauncher extends Initialization {

    RunMode run = new RunMode();

    public void moveLauncher(String color){
        if (color == "blue") {
            run.followAprilTag(1);
        } else if (color == "red") {
            run.followAprilTag(0);
        }
    }

    public void suck(){
        sugador.setPower(1);
    }

    public void launchBall(int index){
        LLResult result = limelight3A.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();

        eixoY = result.getTy();
        ta = result.getTa();

        for (LLResultTypes.FiducialResult fr : fiducialResults) {
            hipotenusaMenor = k / Math.sqrt(ta);

            baseMenor = Math.sqrt(Math.pow(hipotenusaMenor, 2) - Math.pow(alturaMenor, 2));
            baseMaior = baseMenor + 20;

            hipotenusaMaior = Math.sqrt(Math.pow(alturaMaior, 2) + Math.pow(baseMaior, 2));
            delta = Math.toRadians(Math.tan(alturaMaior / baseMaior));
            angulomaior = Math.toDegrees(delta);

            tangente = alturaMaior / baseMaior;
            anguloY = Math.toDegrees(Math.atan(tangente));

            positionServoY = anguloY / 180.0;

            v = Math.sqrt(
                    (g * Math.pow(baseMaior, 2)) /
                            (2 * Math.pow(Math.cos(angulomaior), 2) *
                                    (baseMaior * Math.tan(angulomaior) - alturaMaior))
            );

            Vborda = v / k_lip;

            rev = Vborda / (6.28 * r);
            rpm = rev * 60.0;

            if (fr.getFiducialId() == id[index]){
                servoY.setPosition(positionServoY);

                flywheelB.setVelocity(-rpm);
                flywheelC.setVelocity(-rpm);
            }
        }

    }
}