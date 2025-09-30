package org.firstinspires.ftc.teamcode.pedroPathing.Prototipos;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.opencv.core.Mat;

public class Shooter_Prog_V1 extends OpMode {

    Limelight3A limelight3A;

    DcMotorEx left, right;

    CRServo servoX;
    Servo servoY;
    double distancia, altura, position, power, velocity, tang_alfa, angulo, cos_alfa;

    public void init(){
        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(1);
        limelight3A.start();

        servoX = hardwareMap.get(CRServo.class, "servoX");
        servoY = hardwareMap.get(Servo.class, "servoY");

        left = hardwareMap.get(DcMotorEx.class, "left");
        right = hardwareMap.get(DcMotorEx.class, "right");

        altura = 120;
        velocity = 100;
    }

    public void loop(){
        LLResult resultado = limelight3A.getLatestResult();

        // Pega o valor de área (ta) do Limelight para calcular a distância
        double ta = limelight3A.getLatestResult().getTa();
        final double k = 130.384048104052974;
        distancia = k / Math.sqrt(ta);

        if (resultado != null && resultado.isValid()) {
            // Exibe dados do Limelight
            telemetry.addData("Tx", resultado.getTx());
            telemetry.addData("Ta", resultado.getTa());
            telemetry.addData("Ty", resultado.getTy());

            /**
             * Acha a tangente do angulo alfa, com base no calculo:
             * CATETO OPOSTO / CATETO ADJACENTE
             * ALTURA / DISTANCIA que o robo esta do GOAL
             **/
            tang_alfa = altura / distancia;

            //Com base na tangente, ele acha o angulo, transformando o valor da tangente em angulação.
            angulo = Math.toDegrees(Math.atan(tang_alfa));
            //divindo o angulo alfa por 180 graus, achamos o valor da posição do servo
            position = angulo / 180;
            //REVISAR
            power = (0.73304 * Math.cos(angulo) * velocity) / 100;

            telemetry.addData("POSITION SERVO: ", position);

            left.setPower(power);
            right.setPower(power);

            servoY.setPosition(position);


            // Exibe dados da odometria (posição X, Y)
            telemetry.update();
        }

    }
}
