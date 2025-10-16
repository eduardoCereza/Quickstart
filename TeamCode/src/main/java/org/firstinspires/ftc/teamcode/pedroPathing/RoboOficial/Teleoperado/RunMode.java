package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Teleoperado;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Initialization;

import java.util.List;

public class RunMode extends Initialization {

    //Definição do ID;
    public int[] id = {24, 20};

    //Variáveis para o servo do eixoX
    private double eixoX, seno, ta, anguloX, power, forcaPesoTotal;

    //Variáveis para o servo do eixoY
    private double eixoY, v, hipotenusaMenor, hipotenusaMaior,
            baseMenor, baseMaior,
            positionServoY,
            delta, angulomaior,anguloY, tangente,
            Vborda, rev, rpm;

    //Constantes necessárias
    private final double k = 186.5409338456308,
            g = 980, velocityServoX = 0.0375,
            pesoBola = 74.8, pesoTurret = 0,
            alturaMenor = 74, alturaMaior = 124,
            k_lip = 0.95, r = 4.5;


    public void moveChassi(double y, double x, double turn){
        follower.setTeleOpDrive(y, x, turn, true);
        follower.update();
    }

    public void followAprilTag(int index){
        LLResult result = limelight3A.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();

        eixoX = result.getTx();
        ta = result.getTa();

        for (LLResultTypes.FiducialResult fr: fiducialResults){

            //Cateto oposto / hipotenusa
            double hipotenusa = k / Math.sqrt(ta);
            seno = eixoX / (hipotenusa);
            //Transformando para angulo
            anguloX = Math.toDegrees(Math.asin(seno));

            //Calculo de força peso em newtons
            forcaPesoTotal = ( (pesoBola / 1000) + (pesoTurret / 1000) ) * 10;

            //Calculo de potência
            power = (velocityServoX * seno * forcaPesoTotal) * 100;

            if (fr.getFiducialId() == id[index]){
                if (anguloX != 0){
                    servoX.setPower(power);
                }else {
                    servoX.setPower(0);
                }
            }

        }

    }

    public void moveLauncher(float ativador, int index){
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
                // Ajusta a elevação (Y) do tiro conforme posição calculada.
                servoY.setPosition(positionServoY);

                if (ativador > 0.5) {
                    // Define a velocidade dos dois motores da flywheel (negativo para sentido desejado).
                    flywheelB.setVelocity(-rpm);
                    flywheelC.setVelocity(-rpm);
                }
            }
        }

    }

    public void moveSucker(float ativador){
        if (ativador > 0.5){
            sugador.setPower(1);
        }
    }
}
