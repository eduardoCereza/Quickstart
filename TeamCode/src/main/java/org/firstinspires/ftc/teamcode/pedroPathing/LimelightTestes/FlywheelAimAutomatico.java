package org.firstinspires.ftc.teamcode.pedroPathing.LimelightTestes;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@Configurable
@TeleOp(name = "Flywheel_Aim_Automatico", group = "Testes")
public class FlywheelAimAutomatico extends OpMode {

    Limelight3A limelight;
    DcMotorEx flywheelA, flywheelB;
    Servo servoY;

    double g = 9.81;
    double r = 0.045;          // flywheel radius (m)
    int ticksAround = 28;
    double efficiency = 0.9;   // Estimated mechanical loss

    double thetaMinDeg = 15.0;
    double thetaMaxDeg = 40.0;
    double posMin = 0.1;
    double posMax = 1;

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(1);
        limelight.start();

        flywheelB = hardwareMap.get(DcMotorEx.class, "flywheelB");
        flywheelA = hardwareMap.get(DcMotorEx.class, "flywheelA");
        servoY = hardwareMap.get(Servo.class, "servoY");

        flywheelB.setDirection(DcMotorEx.Direction.FORWARD);
        flywheelA.setDirection(DcMotorEx.Direction.REVERSE);

        flywheelB.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        flywheelA.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        flywheelB.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        flywheelA.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        telemetry.addLine("Sistema pronto! Aguarde a Limelight detectar a tag...");
    }

    @Override
    public void loop() {
        LLResult result = limelight.getLatestResult();

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

        telemetry.update();
    }
}
