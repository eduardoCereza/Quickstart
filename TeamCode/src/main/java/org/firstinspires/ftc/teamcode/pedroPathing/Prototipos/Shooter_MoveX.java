package org.firstinspires.ftc.teamcode.pedroPathing.Prototipos;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.List;

@TeleOp(group = "Prototipo", name = "turret")
public class Shooter_MoveX extends OpMode {
    //servo que move a turret no eixo X, centraliza ele no centro da apriltag
    CRServo servoX;

<<<<<<< Updated upstream
    //Ajustes de variáveis físicas
    double pesoBola = 74.8, pesoTurret = 0, forcaPesoTotal;
    Limelight3A limelight3A;


    public void init() {
        servo = hardwareMap.get(CRServo.class, "servoX");
        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(1);
        limelight3A.start();
=======
    //servo que move a turret no eixo Y, regula o angulo da flywheel
    Servo servoY;

    //motor do flywheel
    DcMotorEx flywheel;

    //limelight, le o valor da apriltag a partida toda
    Limelight3A limelight3A;

    //medidas
    double anguloX, anguloY, angulomaior, delta, hipmenor, hipmaior, basemenor, basemaior;
>>>>>>> Stashed changes

    //tudo relacionado a servo
    double posdoservoy,eixoX, eixoY, power, servoXPosRad, servoYPosRad, forcaPesoTotal;

    //velocidade da flywheel
    double v, V, Vborda, rev, rpm;

    //valores fixos
    final double k = 186.5409338456308, velocity = 0.0375, pesoTurret = 0, pesoBola = 74.8, alturamenor = 74, alturamaior = 124, g = 980, k_slip = 0.95, r = 4.5;

    public void init() {

        // inicializa todos os motores, servos e componentes
        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        servoX = hardwareMap.get(CRServo.class, "servoX");
        servoY = hardwareMap.get(Servo.class, "servoY");
        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(1); //pipeline apriltag 3D todo verificar se tem como fazer algo mais com essa função
        limelight3A.start();
    }

    public void loop() {

        //valores relativos a limelight
        LLResult resultado = limelight3A.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = resultado.getFiducialResults();
        eixoX = resultado.getTx();
        eixoY = resultado.getTy();

        //loop principal
        for (LLResultTypes.FiducialResult fr : fiducialResults) {
            telemetry.addData("Fiducial", "ID: %d, Family: %s, X: %.2f, Y: %.2f", fr.getFiducialId(), fr.getFamily(), fr.getTargetXDegrees(), fr.getTargetYDegrees());

            //calcula todas as medidas dos triangulos que compoem toda a logica para a turret mirar corretamente
            hipmenor = k / Math.sqrt(resultado.getTa());
            basemenor = Math.sqrt(Math.pow(hipmenor, 2) - Math.pow(alturamenor, 2));
            basemaior = basemenor + 20;
            hipmaior = Math.sqrt(Math.pow(alturamaior, 2) + Math.pow(basemaior, 2));
            delta = Math.toRadians(Math.tan(alturamaior / basemaior));
            angulomaior = Math.toDegrees(delta);

            //angulo Y - servo Y / calcula a posição necessaria para que o servo Y se movimente para que a turret fique no angulo desejado
            servoYPosRad = alturamaior / basemaior;
            anguloY = Math.toDegrees(Math.atan(servoYPosRad));
            posdoservoy = anguloY / 180;

            //angulo X - servo X / calcula a posição necessaria para que a turret se centralize no centro da apriltag
            servoXPosRad = eixoX / hipmaior;
            anguloX = Math.toDegrees(Math.asin(servoXPosRad));

            //calculo para o servo X se manter centralizado no centro da apriltag
            forcaPesoTotal = ((pesoBola / 1000) + (pesoTurret / 1000)) * 9.8;
            power = (forcaPesoTotal * servoXPosRad * velocity) * 130;

            //calculo da velocidade da flywheel
            v = Math.sqrt((g * basemaior) / (2 * Math.cos(angulomaior) * (basemaior * Math.tan(angulomaior) * alturamaior)));
            Vborda = v / k_slip;
            rev = Vborda / r * 6.28;
            rpm = rev * 60;

            //caso leia a april tag 24 (lado vermelho) / mesma logica para o lado azul, só muda o id
            if (fr.getFiducialId() == 24) {

                //mantem a turret no centro da apriltag
                if (anguloX != 0) {
                    servoX.setPower(power);
                }

                if(anguloX == 0){
                    servoX.setPower(0);
                }

                //regula a altura da turret em relação ao goal
                servoY.setPosition(posdoservoy);

                //atira as bolas no gol de acordo com a potência calculada
                flywheel.setVelocity(rpm);

                //telemetria para controle dos valores pelo drive hub
                telemetry.addData("Power: ", power);
                telemetry.addData("Angulo servo X: ", anguloX);

                telemetry.addData("Angulo Maior:", angulomaior);
                telemetry.addData("hip maior:", hipmaior);
                telemetry.addData("hip menor:", hipmenor);
                telemetry.addData("base menor:", basemenor);
                telemetry.addData("base maior:", basemaior);

                telemetry.addData("pos servo Y:", servoY.getPosition());
                telemetry.addData("velocidade flywheel:", flywheel.getVelocity());
                telemetry.addData("rpm:", rpm);
                telemetry.addData("V:", v);
                telemetry.addData("rev:", rev);
                telemetry.update();
            }
        }
    }

}