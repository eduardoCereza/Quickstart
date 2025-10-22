package org.firstinspires.ftc.teamcode.pedroPathing.LimelightTestes;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class LimelightFollow extends OpMode {
    Limelight3A limelight3A;
    CRServo servo;

    // 🔹 PID constants
    final static double Kp = 0.001, Ki = 0.0, Kd = 0.0002;
    final static double minPower = 0.08, maxPower = 0.6;
    final static double tolerance = 0.5; // graus

    double integralSum = 0, lastError = 0;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void init() {
        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(1);
        limelight3A.start();

        servo = hardwareMap.get(CRServo.class, "servoX");
        timer.reset();
    }

    @Override
    public void loop() {
        LLResult result = limelight3A.getLatestResult();


        if (result == null || !result.isValid()) {
            servo.setPower(0);
            telemetry.addLine("Nenhuma AprilTag detectada");
            telemetry.update();
            return;
        }

        double tx = result.getTx();
        double error = -tx;

        double dt = timer.seconds();
        double derivative = (error - lastError) / dt;
        integralSum += error * dt;

        double output = (Kp * error) + (Ki * integralSum) + (Kd * derivative);

        if (Math.abs(error) <= tolerance) {
            output = 0;
            integralSum = 0;
        }

        if (Math.abs(output) < minPower && Math.abs(output) > 0)
            output = Math.copySign(minPower, output);

        output = Range.clip(output, -maxPower, maxPower);

        servo.setPower(output);

        lastError = error;
        timer.reset();

        telemetry.addData("TX (erro)", tx);
        telemetry.addData("Power", output);
        telemetry.addData("Detectou tag", result.isValid());
        telemetry.update();
    }

    @Override
    public void stop() {
        limelight3A.close();
    }
}
