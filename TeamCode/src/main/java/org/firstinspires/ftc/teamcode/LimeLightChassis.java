package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "limelight")
public class LimeLightChassis extends OpMode {
    private Limelight3A limelight;

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(1); //apriltag
    }

    @Override
    public void start() {
        limelight.start();
    }

    @Override
    public void loop() {
        LLResult resultado = limelight.getLatestResult();
        if (resultado != null && resultado.isValid()){
            telemetry.addData("Tx", resultado.getTx());
            telemetry.addData("Ta", resultado.getTa());
            telemetry.addData("Ty", resultado.getTy());
        }
    }
}
