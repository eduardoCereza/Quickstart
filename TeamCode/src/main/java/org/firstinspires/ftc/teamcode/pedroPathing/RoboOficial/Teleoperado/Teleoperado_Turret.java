package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Teleoperado;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import static org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Autonomous.RED.AutoPPG_Red.autoEndPose;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.RobotDecodeOficial;

//TODO:                                          ATENÇÃO!!!
//TODO:                     ESSA PROGRAMAÇÃO SÓ FUNCIONA DEPOIS DO UM AUTÔNOMO
//TODO:                     SE TIVER DUVIDAS CHAMA O HENRIQUE QUE ELE EXPLICA DE BOA

//Teleoperado para teste da turret
@TeleOp(name = "Teleoperado_Turret")
public class Teleoperado_Turret extends OpMode {

    // Follower do Pedro Pathing
    private Follower follower;

    // Subsistema da turret
    RobotDecodeOficial turret;

    // Loop principal
    @Override
    public void loop() {
        // Atualiza o Pedro Pathing
        follower.update();

        // Começa o teleoperado com um robot centric
        follower.setTeleOpDrive(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);

        // Começa a mirar a turret no eixo X
        turret.mirarX_turret(follower.getPose().getX(), follower.getPose().getY(), follower.getPose().getHeading(), false, telemetry);

        // Começa a mirar a turret no eixo Y
        turret.mirarY_turret();

        // Se apertar o bumper direito ele começa a atirar as bolas
        if(gamepad1.right_bumper){
            turret.shoot();
        }

        // Telemetria
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading:", Math.toDegrees(follower.getHeading()));
        telemetry.update();
    }

    // Init
    @Override
    public void init() {

        // Init do Subsistema da turret
        turret = new RobotDecodeOficial(hardwareMap, 1, telemetry);

        // Init o Pedro Pathing
        follower = Constants.createFollower(hardwareMap);

        // Seta a posição inicial do robô como a final do ultimo autônomo
        follower.setStartingPose(autoEndPose == null ? new Pose() : autoEndPose);

        // Atualiza o Pedro Pathing
        follower.update();
    }

    // Start
    @Override
    public void start() {
        follower.startTeleopDrive();
    }

    // Stop
    public void stop() {
        autoEndPose = follower.getPose();
    }

}