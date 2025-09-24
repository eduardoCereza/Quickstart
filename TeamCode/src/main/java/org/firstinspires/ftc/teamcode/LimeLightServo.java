package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;

@Autonomous
public class LimeLightServo extends OpMode {
    private Limelight3A limelight;
    private CRServo girador;

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        girador = hardwareMap.get(CRServo.class, "girador");
        limelight.pipelineSwitch(0); //apriltag
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
        if (resultado.getTx() > 0){
            girador.setPower(1);
        }
        if(resultado.getTx() < 0){
            girador.setPower(-1);
        }
        if (resultado.getTx() == 0){
            girador.setPower(0);
        }
    }
}
