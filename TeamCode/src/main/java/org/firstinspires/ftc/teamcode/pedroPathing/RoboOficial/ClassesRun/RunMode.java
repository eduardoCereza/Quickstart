package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.ClassesRun;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.List;

public class RunMode extends Initialization {

    public void moveChassi(double y, double x, double turn){
        follower.update();
        follower.setTeleOpDrive(y, x, turn, true);
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
