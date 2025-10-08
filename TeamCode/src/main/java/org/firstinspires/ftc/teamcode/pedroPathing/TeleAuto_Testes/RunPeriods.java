package org.firstinspires.ftc.teamcode.pedroPathing.TeleAuto_Testes;


import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

public class RunPeriods extends Init_Class{

    double anguloX, anguloY, angulomaior, delta, hipmenor, hipmaior, basemenor, basemaior;
    double posdoservoy, eixoX, eixoY, power, servoXPosRad, servoYPosRad, forcaPesoTotal;
    double Vborda, rev, rpm, v;
    final double k = 186.5409338456308, velocity = 0.0375, pesoTurret = 0, pesoBola = 74.8, alturamenor = 74, alturamaior = 124, g = 980, k_lip = 0.95, r = 4.5;

    public void TeleOp(double y, double x, double turn){

        if (follower !=null) {
            follower.setTeleOpDrive(y, x, turn, true);
        }

    }

    public void Lançador(Telemetry telemetry){

        LLResult resultado = limelight3A.getLatestResult();

        List<LLResultTypes.FiducialResult> fiducialResults = resultado.getFiducialResults();

        eixoX = resultado.getTx();
        eixoY = resultado.getTy();

        for (LLResultTypes.FiducialResult fr : fiducialResults) {
            telemetry.addData("Fiducial", "ID: %d", fr.getFiducialId());

            hipmenor = k / Math.sqrt(resultado.getTa());

            basemenor = Math.sqrt(Math.pow(hipmenor, 2) - Math.pow(alturamenor, 2));

            basemaior = basemenor + 20;

            hipmaior = Math.sqrt(Math.pow(alturamaior, 2) + Math.pow(basemaior, 2));

            delta = Math.toRadians(Math.tan(alturamaior / basemaior));
            angulomaior = Math.toDegrees(delta);

            servoYPosRad = alturamaior / basemaior;
            anguloY = Math.toDegrees(Math.atan(servoYPosRad));

            posdoservoy = anguloY / 180.0;

            servoXPosRad = eixoX / hipmaior;
            anguloX = Math.toDegrees(Math.asin(servoXPosRad));

            forcaPesoTotal = ((pesoBola / 1000.0) + (pesoTurret / 1000.0)) * 9.8;
            power = (forcaPesoTotal * servoXPosRad * velocity) * 200.0;

            v = Math.sqrt(
                    (g * Math.pow(basemaior, 2)) /
                            (2 * Math.pow(Math.cos(angulomaior), 2) * (basemaior * Math.tan(angulomaior) - alturamaior))
            );

            Vborda = v / k_lip;

            rev = Vborda / (6.28 * r);
            rpm = rev * 60.0;

            if (fr.getFiducialId() == 24) {

                if (anguloX != 0) {
                    servoX.setPower(power);
                } else {
                    servoX.setPower(0);
                }

                servoY.setPosition(posdoservoy);

                flywheelB.setVelocity(-rpm);
                flywheelC.setVelocity(-rpm);

                telemetry.addData("Power: ", power);
                telemetry.addData("Angulo servo X: ", anguloX);
                telemetry.addData("Angulo Maior:", angulomaior);
                telemetry.addData("hip maior:", hipmaior);
                telemetry.addData("hip menor:", hipmenor);
                telemetry.addData("base menor:", basemenor);
                telemetry.addData("base maior:", basemaior);

                telemetry.addData("rpm:", rpm);
                telemetry.addData("velocity B:", flywheelB.getVelocity());
                telemetry.addData("velocity C:", flywheelC.getVelocity());

                telemetry.update();
            }
        }

    }
}
