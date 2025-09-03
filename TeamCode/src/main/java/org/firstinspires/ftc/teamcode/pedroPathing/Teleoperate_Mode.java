package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Teleoperate_Mode extends OpMode {

    //TODO: Criação das variável
    private Follower follower;

    //String ip = "192.168.43.1:8001";
    Servo garra, ponta;
    DcMotor slide;
    private Hw_Others hw;
    double x, y, turn;
    private final Pose startPose = new Pose(60, 96, Math.toRadians(180));

    //TODO: Método de inicialização do período Teleoperado
    @Override
    public void init(){
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startPose);
        hw = new Hw_Others(garra, ponta, slide);
        hw.init(hardwareMap);
        //System.out.println("Acesse o ip: "+ ip);
    }

    //TODO: Método para começar o período Teleoperado
    @Override
    public void start(){
        follower.startTeleopDrive();
    }

    //TODO: Método de loop infinito para o teleoperado
    @Override
    public void loop(){
        x = gamepad1.left_stick_y;
        y = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;

        follower.setTeleOpDrive(x, y, turn, false);
        follower.update();
    }

    //TODO: Método para mover o servo motor

    /*
    public void moverServo(String aberto){
        if (gamepad2.left_bumper)
            aberto = "aberto";

        if (aberto == "aberto")
            garra.setPosition(1);
        else
            garra.setPosition(0);
    }

     */
}
