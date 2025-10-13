package org.firstinspires.ftc.teamcode.pedroPathing.limelight;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.List;

public class Turret {
    // Servo contínuo que gira a torre (turret) no eixo X (pan) para centralizar na AprilTag.
    CRServo servoX;

    // Servo posicional que ajusta a elevação da turret (eixo Y / tilt) para definir o ângulo de arremesso.
    Servo servoY;

    // Motores da flywheel (lançamento da bola).
    DcMotorEx flywheelB, flywheelC;

    // Câmera Limelight 3A (detecção de AprilTag / visão).
    Limelight3A limelight3A;

    // Variáveis geométricas/angulares usadas nos cálculos de mira/alcance.
    double anguloX, anguloY, angulomaior, delta, hipmenor, hipmaior, basemenor, basemaior;

    // Variáveis relacionadas aos servos e controle de força no eixo X.
    double posdoservoy, eixoX, eixoY, power, servoXPosRad, servoYPosRad, forcaPesoTotal;

    // Variáveis da flywheel (cinemática do disparo).
    double Vborda, rev, rpm, v;

    // Constantes de projeto/físicas (unidades precisam ser coerentes — ver observações ao final).
    // k: fator para estimar distância a partir da área (ta) da tag; velocity: ganho simples para servoX;
    // pesos em gramas; alturas em mm (?) ; g em cm/s² (980); k_lip: perda por atrito (lip), r: raio da flywheel.
    final double k = 186.5409338456308, velocity = 0.0375, pesoTurret = 0, pesoBola = 74.8,
            alturamenor = 74, alturamaior = 124, g = 980, k_lip = 0.95, r = 4.5;

    public Turret(HardwareMap hwmap) {
        servoX = hwmap.get(CRServo.class, "servoX");
        servoY = hwmap.get(Servo.class, "servoY");

        flywheelB = hwmap.get(DcMotorEx.class, "left");
        flywheelC = hwmap.get(DcMotorEx.class, "flywheelC");

        limelight3A = hwmap.get(Limelight3A.class, "limelight3A");
        limelight3A.pipelineSwitch(1);
    }

    public void mirar() {
        LLResult resultado = limelight3A.getLatestResult();

        // ATENÇÃO: em runtime, valide nulidade/targets antes de usar (ver observações).
        List<LLResultTypes.FiducialResult> fiducialResults = resultado.getFiducialResults();

        // Deslocamentos da tag em graus (tx = horizontal; ty = vertical) conforme a Limelight.
        eixoX = resultado.getTx();
        eixoY = resultado.getTy();

        // Loop sobre cada detecção de fiducial (AprilTag).
        for (LLResultTypes.FiducialResult fr : fiducialResults) {


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
            }


        }

    }
}

