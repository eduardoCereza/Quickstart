package org.firstinspires.ftc.teamcode.pedroPathing.Prototipos;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;


@TeleOp(name = "Teste Limelight", group = "Prototipos")
public class Limelight3A_Teste extends OpMode {

    int[] id = {21, 22, 23};
    Limelight3A limelight;

    @Override
    public void init(){

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        telemetry.setMsTransmissionInterval(11);
        limelight.pipelineSwitch(0);
        limelight.start();

        telemetry.addData(">", "Robot Ready.  Press Play.");



    }

    @Override
    public void loop() {
        LLResult result = limelight.getLatestResult();
        // Access fiducial results
        List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
        for (LLResultTypes.FiducialResult fr : fiducialResults) {

            if(fr.getFiducialId() == id[0]){
                telemetry.addLine("Ordem GPP");
            } else if (fr.getFiducialId() == id[1]) {
                telemetry.addLine("Ordem PGP");
            } else if (fr.getFiducialId() == id[2]) {
                telemetry.addLine("Ordem PPG");
            }
        }
        telemetry.update();


    }
}
