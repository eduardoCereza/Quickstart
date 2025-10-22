package org.firstinspires.ftc.teamcode.pedroPathing.LimelightTestes;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class MeasureDistancia  extends OpMode {
    Limelight3A limelight3A;

    private double distance;

    @Override
    public void init() {
        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(1);
        limelight3A.start();
    }

    @Override
    public void loop() {

        LLResult llResult = limelight3A.getLatestResult();
        if (llResult != null && llResult.isValid()){
            distance = getDistance(llResult.getTa());
            telemetry.addData("Distance: ", distance);
            telemetry.addData("Target X: ", llResult.getTx());
            telemetry.addData("Target Area: ", llResult.getTa());
        }

    }

    public double getDistance(double ta){

        double scale = 181.9994;
        double distance = (scale / Math.sqrt(ta));
        return distance;
    }
}
