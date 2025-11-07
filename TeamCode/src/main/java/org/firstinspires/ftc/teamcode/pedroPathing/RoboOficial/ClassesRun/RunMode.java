package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.ClassesRun;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.hardware.DcMotor;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.ClassesRun.Interfaces.IRunMode;
import org.opencv.core.Mat;


public class RunMode extends Initialization implements IRunMode {

    public void moveChassi(double y, double x, double turn) {
        follower.update();
        follower.setTeleOpDrive(y, x, turn, true);
    }

    public void followTag(double x, double y, double heading, boolean blue, Telemetry telemetry) {

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
        servoX1.setPosition(-servoPos);
        servoX2.setPosition(-servoPos);

        // Telemetria
        telemetry.addData("AngleToGoal", angleToGoal);
        telemetry.addData("TurretAngle", turretAngle);
        telemetry.addData("ServoPos", servoPos);
        telemetry.update();
    }

    public void throwBalls(Telemetry telemetry) {
        LLResult result = limelight3A.getLatestResult();

        double a = 20.0;
        double b = 1500.0;
        double MAX_RPM = 6000;

        double distance = 181.9994 / Math.sqrt(result.getTa());

        double targetRPM = a * distance + b;
        targetRPM = Math.min(targetRPM, MAX_RPM);

        double TICKS_PER_REV = 28;
        double targetVelocity = (targetRPM * TICKS_PER_REV) / 60.00;

        if (result.isValid()) {
            flywheel.setVelocity(targetVelocity);
        }

        telemetry.addData("Distância (cm)", distance);
        telemetry.addData("Target RPM", targetRPM);
        telemetry.addData("Ticks/s", targetVelocity);
    }


    public void liftRobot(boolean holding) {
        if (holding) {
            slideL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            slideR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            slideL.setTargetPosition(slideL.getCurrentPosition());
            slideR.setTargetPosition(slideR.getCurrentPosition());

            slideL.setPower(1);
            slideR.setPower(1);
        } else {
            slideL.setMotorDisable();
            slideR.setMotorDisable();
        }
    }
}
