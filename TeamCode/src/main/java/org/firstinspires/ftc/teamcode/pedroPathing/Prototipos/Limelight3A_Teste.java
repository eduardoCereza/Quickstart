package org.firstinspires.ftc.teamcode.pedroPathing.Prototipos;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "Teste Limelight", group = "Prototipos")
public class Limelight3A_Teste extends OpMode {
    Limelight3A limelight;

    @Override
    public void init(){

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(11);
        limelight.pipelineSwitch(0);
        limelight.start();

        telemetry.addData(">", "Robot Ready.  Press Play.");
        telemetry.update();

    }

    @Override
    public void loop() {

    }
}
