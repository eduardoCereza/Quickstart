package org.firstinspires.ftc.teamcode.pedroPathing.Prototipo1_Metal_REV.TeleAuto_Testes;


import com.bylazar.gamepad.Gamepad;
import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

public class RunPeriods extends InitClass{

    double anguloX, anguloY, angulomaior, delta, hipmenor, hipmaior, basemenor, basemaior;
    double posdoservoy, eixoX, eixoY, power, servoXPosRad, servoYPosRad, forcaPesoTotal;
    double Vborda, rev, rpm, v;
    final double k = 186.5409338456308, velocity = 0.0375, pesoTurret = 0, pesoBola = 74.8, alturamenor = 74, alturamaior = 124, g = 980, k_lip = 0.95, r = 4.5;

    public void TeleOp(double y, double x, double turn){

        follower.setTeleOpDrive(y, x, turn, true);

    }

    public void lancar(Telemetry telemetry){

        // Obtém o último resultado da Limelight (visão).
        LLResult resultado = limelight3A.getLatestResult();

        // ATENÇÃO: em runtime, valide nulidade/targets antes de usar (ver observações).
        List<LLResultTypes.FiducialResult> fiducialResults = resultado.getFiducialResults();

        // Deslocamentos da tag em graus (tx = horizontal; ty = vertical) conforme a Limelight.
        eixoX = resultado.getTx();
        eixoY = resultado.getTy();

        // Loop sobre cada detecção de fiducial (AprilTag).
        for (LLResultTypes.FiducialResult fr : fiducialResults) {
            telemetry.addData(
                    "Fiducial",
                    "ID: %d, Family: %s, X: %.2f, Y: %.2f",
                    fr.getFiducialId(),
                    fr.getFamily(),
                    fr.getTargetXDegrees(),
                    fr.getTargetYDegrees()
            );

            // --------- Geometria para estimativa de distâncias/ângulos ----------
            // hipmenor: “hipotenusa” (distância) estimada via área da tag (ta). Usa fator empírico k.
            hipmenor = k / Math.sqrt(resultado.getTa());

            // basemenor: base horizontal para alvo de menor altura (pit mais próximo).
            basemenor = Math.sqrt(Math.pow(hipmenor, 2) - Math.pow(alturamenor, 2));

            // basemaior: base para alvo de maior altura (ex.: gol mais alto) -> offset de 20 (unidades iguais às demais).
            basemaior = basemenor + 20;

            // hipmaior: hipotenusa considerando a maior altura (alvo alto).
            hipmaior = Math.sqrt(Math.pow(alturamaior, 2) + Math.pow(basemaior, 2));

            // delta / angulomaior: ângulo de tiro para alcançar a altura maior (cuidado com unidades, ver observações).
            // Aqui delta recebe "radianos" a partir de tan(h/ b), mas usa Math.toRadians em cima de tan() (ver observações).
            delta = Math.toRadians(Math.tan(alturamaior / basemaior));
            angulomaior = Math.toDegrees(delta);

            // --------- Ângulo Y (elevação) ---------
            // servoYPosRad: razão altura/base -> arctan dá o ângulo em radianos.
            servoYPosRad = alturamaior / basemaior;
            anguloY = Math.toDegrees(Math.atan(servoYPosRad));

            // Posição normalizada do servo [0..1] a partir do ângulo (simplificação).
            posdoservoy = anguloY / 180.0;

            // --------- Ângulo X (pan) ---------
            // Usa deslocamento horizontal (eixoX) e “hipmaior” para obter ânguloX (aproximação).
            // Observação: asin(eixoX/hipmaior) supõe eixoX em mesma unidade de comprimento da hipotenusa,
            // mas eixoX está em graus (tx). Mantido conforme o original; ver observações.
            servoXPosRad = eixoX / hipmaior;
            anguloX = Math.toDegrees(Math.asin(servoXPosRad));

            // --------- Controle simples de power no servoX ---------
            // Força “equivalente” considerando massa (pesoBola + pesoTurret) e uma constante de velocidade.
            // Multiplica por 200 como ganho final (tunagem empírica).
            forcaPesoTotal = ((pesoBola / 1000.0) + (pesoTurret / 1000.0)) * 9.8;
            power = (forcaPesoTotal * servoXPosRad * velocity) * 200.0;

            // --------- Cálculos de velocidade para a flywheel ---------
            // v: velocidade de saída necessária para alcançar a base/altura desejada (balística sem arrasto).
            // Usa g, basemaior, angulomaior; importante garantir ângulo em radianos nas funções trigonométricas (ver observações).
            v = Math.sqrt(
                    (g * Math.pow(basemaior, 2)) /
                            (2 * Math.pow(Math.cos(angulomaior), 2) * (basemaior * Math.tan(angulomaior) - alturamaior))
            );

            // Vborda: velocidade linear na borda da flywheel corrigida por perdas (k_lip).
            Vborda = v / k_lip;

            // Conversões para RPM: rev = V / (circunferência); rpm = rev * 60.
            rev = Vborda / (6.28 * r);
            rpm = rev * 60.0;

            // Lado vermelho (ID 24). Para o lado azul, replicar lógica com o ID correspondente.
            if (fr.getFiducialId() == 24) {

                // Mantém a turret tentando centralizar no X. Se há erro angular, aplica potência; senão zera.
                if (anguloX != 0) {
                    servoX.setPower(power);
                } else {
                    servoX.setPower(0);
                }

                // Ajusta a elevação (Y) do tiro conforme posição calculada.
                servoY.setPosition(posdoservoy);

                // Define a velocidade dos dois motores da flywheel (negativo para sentido desejado).
                flywheelB.setVelocity(-rpm);
                flywheelC.setVelocity(-rpm);

                // Telemetria para depuração em campo.
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
            }
        }

    }

    public void Levantar(float subir, float descer, Telemetry telemetry, double power) {

        //int currentPosL = slideL.getCurrentPosition();
        //int currentPosR = slideR.getCurrentPosition();

        if (subir > 0.5) {
            slideL.setPower(power);
            slideR.setPower(power);

        } else if (descer > 0.5) {
            slideL.setPower(-power);
            slideR.setPower(-power);
        }

        /*
        else {
            slideL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            slideR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            slideL.setTargetPosition(currentPosL);
            slideR.setTargetPosition(currentPosR);

            slideL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slideR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            slideR.setPower(1);
            slideL.setPower(1);
        }

         */

    }
}
