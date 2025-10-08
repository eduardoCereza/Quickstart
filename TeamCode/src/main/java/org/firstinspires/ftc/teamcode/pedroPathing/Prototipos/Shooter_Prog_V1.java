package org.firstinspires.ftc.teamcode.pedroPathing.Prototipos;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.opencv.core.Mat;

@TeleOp(group = "Prototipo")

public class Shooter_Prog_V1 extends OpMode {

    Limelight3A limelight3A;

    DcMotorEx left, right;

    CRServo servoX;
    Servo servoY;
    double hip, altura = 120, position, power, velocity, angulo, cos;

    public void init(){
        limelight3A = hardwareMap.get(Limelight3A.class, "limelight3A");
        limelight3A.pipelineSwitch(1);
        limelight3A.start();

        servoX = hardwareMap.get(CRServo.class, "servoX");
        servoY = hardwareMap.get(Servo.class, "servoY");

        left = hardwareMap.get(DcMotorEx.class, "left");
        right = hardwareMap.get(DcMotorEx.class, "flywheelC");
    }

    public void loop(){
        LLResult resultado = limelight3A.getLatestResult();

        // Pega o valor de área (ta) do Limelight para calcular a distância
        double ta = limelight3A.getLatestResult().getTa();
        final double k = 130.384048104052974;
        hip = k / Math.sqrt(ta);

        if (resultado != null && resultado.isValid()) {
            // Exibe dados do Limelight
            telemetry.addData("Tx", resultado.getTx());
            telemetry.addData("Ta", resultado.getTa());
            telemetry.addData("Ty", resultado.getTy());

            cos = altura / hip;
            angulo = Math.toDegrees(Math.acos(cos));
            position = angulo / 180;
            servoY.setPosition(position);
            telemetry.addData("POSITION SERVO: ", position);

            // Exibe dados da odometria (posição X, Y)
            telemetry.update();
        }

    }
}
