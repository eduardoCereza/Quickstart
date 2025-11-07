package org.firstinspires.ftc.teamcode.pedroPathing.LimelightTestes;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configurable
@TeleOp
public class TrackingTag extends OpMode {

    private static final Logger log = LoggerFactory.getLogger(FollowTag.class);
    DcMotorEx coreHex;
    Limelight3A limelight3A;

    public static double kp = 5, kd = 0.000001, ki = 0.00001;
    double TICKS_PER_REVOLUTION = 288.0, integralSum = 0,lastError = 0, derivate, error;


    ElapsedTime timer;
    @Override
    public void init() {
        coreHex = hardwareMap.get(DcMotorEx.class, "corehex");
        coreHex.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        coreHex.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(1);
        limelight3A.start();

        timer = new ElapsedTime();
    }

    @Override
    public void loop() {
        LLResult result = limelight3A.getLatestResult();

        if (result != null && result.isValid()) {
            double tx = result.getTx();

            double distance = 181.9994 / Math.sqrt(result.getTa());

            double tangente = tx / distance;
            double angulo = Math.toDegrees(Math.atan(tangente));
            double target = -tx * TICKS_PER_REVOLUTION;

            double position = coreHex.getCurrentPosition() + target;

            coreHex.setTargetPosition((int) -position);
            coreHex.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            double power = (0.98 * tangente * 0.3) * kp;


            if (angulo != 0){
                coreHex.setPower(Math.abs(power));
            }else {
                coreHex.setPower(0);
            }

            telemetry.addData("Angulo: ", angulo);
            telemetry.addData("Power > ", power);

        } else {
            coreHex.setPower(0);
        }

    }
}
