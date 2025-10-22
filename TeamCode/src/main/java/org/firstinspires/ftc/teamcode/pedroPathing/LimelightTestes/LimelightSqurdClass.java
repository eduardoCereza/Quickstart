package org.firstinspires.ftc.teamcode.pedroPathing.LimelightTestes;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp
public class LimelightSqurdClass extends OpMode {
    Limelight3A limelight3A;
    CRServo servo;



    double tx, ta, alturatrianguloX, hiptrianguloX, angleX;

    final double k = 180.9994;

    @Override
    public void init() {
        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(1);
        limelight3A.start();

        servo = hardwareMap.get(CRServo.class, "servo");


    }

    @Override
    public void loop() {
        moveX();

    }

    private void moveX(){

    }
}
