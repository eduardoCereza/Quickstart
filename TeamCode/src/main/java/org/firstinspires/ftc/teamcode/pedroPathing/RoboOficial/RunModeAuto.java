package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Initialization;

import java.util.List;

public class RunModeAuto extends Initialization {

    public void calculos(){
        LLResult resultado = limelight3A.getLatestResult();

        // ATENÇÃO: em runtime, valide nulidade/targets antes de usar (ver observações).
        List<LLResultTypes.FiducialResult> fr = resultado.getFiducialResults();

        // Deslocamentos da tag em graus (tx = horizontal; ty = vertical) conforme a Limelight.
        //Eixo invertido
        eixoX = resultado.getTx();
        eixoY = resultado.getTy();
        ta = resultado.getTa();

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
    }

    public void followAprilTag(int index){
        calculos();
        LLResult result = limelight3A.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();

        for (LLResultTypes.FiducialResult fr: fiducialResults){
            if (fr.getFiducialId() == id[index]){
                if (anguloX != 0){
                    servoX.setPower(power);
                }else {
                    servoX.setPower(0);
                }
            }

        }

    }

    public void outTake(){
        calculos();
        LLResult result = limelight3A.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();

        for (LLResultTypes.FiducialResult fr : fiducialResults) {
            // Define a velocidade dos dois motores da flywheel (negativo para sentido desejado).
            flywheelB.setVelocity(-rpm);
            flywheelC.setVelocity(-rpm);
        }
    }

    public void moveLauncher(int index) {
        calculos();
        LLResult result = limelight3A.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();

        for (LLResultTypes.FiducialResult fr : fiducialResults) {
            if (fr.getFiducialId() == id[index]) {
                // Ajusta a elevação (Y) do tiro conforme posição calculada.
                servoY.setPosition(posdoservoy);
            }
        }
    }

    public void intake(){
            sugador.setPower(1);
    }


    public void autonomousPathUpdate(){
        switch (pathState){
            case 0:
                follower.followPath(firstRelease, true);
                //run.outTake();
                setPathState(1);
                break;

            case 1:
                if (!follower.isBusy()) {
                    follower.followPath(getOrder1, true);
                    //run.intake();
                    setPathState(2);
                }
                break;


            case 2:
                if (!follower.isBusy()){
                    follower.followPath(launchOrder1, true);
                    //run.outTake();
                    setPathState(3);
                }
                break;
            case 3:
                if (!follower.isBusy()){
                    follower.followPath(getOrder2, true);
                    //run.intake();
                    setPathState(4);
                }
                break;
            case 4:
                if (!follower.isBusy()){
                    follower.followPath(launchOrder2, true);
                    //run.outTake();
                    setPathState(5);
                }
                break;
            case 5:
                if (!follower.isBusy()){
                    follower.followPath(getOrder3, true);
                    //run.intake();
                    setPathState(6);
                }
                break;
            case 6:
                if (!follower.isBusy()){
                    follower.followPath(launchOrder3, true);
                    //run.outTake();
                }
                break;
        }

    }


    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }


}