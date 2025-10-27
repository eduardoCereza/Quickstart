package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

public class RunMode extends Initialization {
    public void calculosY(){
        LLResult resultado = limelight3A.getLatestResult();

        // Deslocamentos da tag em graus (tx = horizontal; ty = vertical) conforme a Limelight.
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

    public void moveChassi(double y, double x, double turn){
        follower.update();
        follower.setTeleOpDrive(y, x, turn, true);
    }

    public void outTake(float ativador){
        calculosY();
        LLResult result = limelight3A.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();

        for (LLResultTypes.FiducialResult fr : fiducialResults) {
            if (ativador > 0.5) {
                // Define a velocidade dos dois motores da flywheel (negativo para sentido desejado).
                flywheelB.setVelocity(-rpm);
                flywheelC.setVelocity(-rpm);
            }
        }
    }
    public void ajustePosition(int index){
        calculosY();
        LLResult result = limelight3A.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();

        eixoY = result.getTy();
        ta = result.getTa();

        for (LLResultTypes.FiducialResult fr : fiducialResults) {
            if (fr.getFiducialId() == id[index]){
                // Ajusta a elevação (Y) do tiro conforme posição calculada.
                servoY.setPosition(posdoservoy);
            }
        }
    }
    public void moveSucker(float ativador){
        if (ativador > 0.5){
            sugador.setPower(1);
        }
    }

    public void followTag(double x, double y, double heading, boolean blue, Telemetry telemetry){

        // Heading do robô
        double headingDeg = Math.toDegrees(heading);

        // Localização do robô no field
        x = x;
        y = y;

        // Localização X e Y dos goals
        double goalX = blue ? goalBlue.getX() : goalRed.getX();
        double goalY = blue ? goalBlue.getY() : goalRed.getY();

        // Ângulo pro goal
        double angleToGoal = Math.toDegrees(Math.atan2(goalX - x, goalY - y));

        // Ângulo da turret com base no angulo pro goal
        double turretAngle = angleToGoal + headingDeg;

        // Máximo e minimo do servo
        // TODO: VERIFICAR O MINIMO E MÁXIMO DO SERVO X
        turretAngle = Math.max(-150, Math.min(150, turretAngle));

        // Transforma em posição do servo mesmo
        double servoPos = (turretAngle + 90) / 180.0;

        // Minimo e máximo do servo, nao muda muita coisa isso
        servoPos = Math.max(0.0, Math.min(1.0, servoPos));

        // Seta a posição do servo X
        servoX.setPosition(-servoPos);

        // Telemetria
        telemetry.addData("AngleToGoal", angleToGoal);
        telemetry.addData("TurretAngle", turretAngle);
        telemetry.addData("ServoPos", servoPos);
        telemetry.update();
    }

}
