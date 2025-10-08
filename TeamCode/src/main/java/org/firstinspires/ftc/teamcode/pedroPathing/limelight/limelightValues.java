package org.firstinspires.ftc.teamcode.pedroPathing.limelight;


import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorGoBildaPinpoint;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

import java.util.Locale;

@Autonomous(name = "limelight3A")
public class limelightValues extends OpMode {
    private Limelight3A limelight;

    GoBildaPinpointDriver odo; // Declare OpMode member for the Odometry Computer

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight3A");
        odo = hardwareMap.get(GoBildaPinpointDriver.class,"pinpoint");

        // Configurações do Pinpoint
        odo.setOffsets(65, 0, DistanceUnit.MM); //these are tuned for 3110-0002-0001 Product Insight #1
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.REVERSED);
        odo.resetPosAndIMU();

        // Configuração do Limelight
        limelight.pipelineSwitch(3); // Pipeline neural
    }

    @Override
    public void start() {
        limelight.start();
    }

    @Override
    public void loop() {
        // Obtém a posição atual do Pinpoint
        Pose2D pos = odo.getPosition();
        String data = String.format(Locale.US, "{X: %.3f, Y: %.3f, H: %.3f}", pos.getX(DistanceUnit.MM), pos.getY(DistanceUnit.MM), pos.getHeading(AngleUnit.DEGREES));
        telemetry.addData("Position", data);

        // Pega o valor de área (ta) do Limelight para calcular a distância
        double ta = limelight.getLatestResult().getTa();
        double k = 130.384048104052974;
        double distanciareal = k / Math.sqrt(ta);

        LLResult resultado = limelight.getLatestResult();

        if (resultado != null && resultado.isValid()) {
            // Exibe dados do Limelight
            telemetry.addData("Tx", resultado.getTx());
            telemetry.addData("Ta", resultado.getTa());
            telemetry.addData("Ty", resultado.getTy());
            telemetry.addData("Distância:", distanciareal);

            // Exibe dados da odometria (posição X, Y)
            telemetry.update();
        }
        odo.update();
    }
}
