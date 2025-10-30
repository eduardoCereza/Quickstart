package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.ClassesRun;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.hardware.DcMotor;


import org.firstinspires.ftc.robotcore.external.Telemetry;


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
        servoX1.setPosition(-servoPos);
        servoX2.setPosition(-servoPos);

        // Telemetria
        telemetry.addData("AngleToGoal", angleToGoal);
        telemetry.addData("TurretAngle", turretAngle);
        telemetry.addData("ServoPos", servoPos);
        telemetry.update();
    }
    public void throwBalls(Telemetry telemetry){
        LLResult result = limelight3A.getLatestResult();

        if (result != null && result.isValid()) {
            double ta = result.getTa();
            double d = 181.9994 / Math.sqrt(ta); // cm
            d /= 100.0; // meters

            //Required height - height up to the point where the ball leaves the field.
            double h = 1.20 - 0.285; // m

            double angulo = Math.atan(h / d);
            double anguloDeg = Math.toDegrees(angulo);

            double posServo = posMin +
                    (anguloDeg - thetaMinDeg) * (posMax - posMin) / (thetaMaxDeg - thetaMinDeg);
            posServo = Math.max(0.0, Math.min(1.0, posServo));
            servoY.setPosition(posServo);

            if (d * Math.tan(angulo) > h) {
                double v = Math.sqrt((g * Math.pow(d, 2)) /
                        (2 * Math.pow(Math.cos(angulo), 2) * (d * Math.tan(angulo) - h)));

                double wRoda = v / (efficiency * r);
                double RPM = (60.0 / (2.0 * Math.PI)) * wRoda;
                double ticksPerSecond = (RPM * ticksAround) / 60.0;

                flywheelB.setVelocity(ticksPerSecond);
                flywheelA.setVelocity(ticksPerSecond);

                //flywheelB.setVelocity(servoY.getPosition() * scalar) + constants;
                //flywheelA.setVelocity(servoY.getPosition() * scalar) + constants;

                telemetry.addData("Distance (m)", d);
                telemetry.addData("Degrees", anguloDeg);
                telemetry.addData("Servo pos", posServo);
                telemetry.addData("speed (m/s)", v);
                telemetry.addData("Target (ticks/s)", ticksPerSecond);
            } else {
                flywheelB.setPower(0);
                flywheelA.setPower(0);
            }
        } else {
            flywheelB.setPower(0);
            flywheelA.setPower(0);
        }
    }
    public void liftRobot(boolean holding){
        if (holding){
            slideL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            slideR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            slideL.setTargetPosition(slideL.getCurrentPosition());
            slideR.setTargetPosition(slideR.getCurrentPosition());

            slideL.setPower(1);
            slideR.setPower(1);
        }else {
            slideL.setMotorDisable();
            slideR.setMotorDisable();
        }
    }
}
