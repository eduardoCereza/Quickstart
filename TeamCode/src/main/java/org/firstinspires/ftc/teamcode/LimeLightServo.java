package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous
public class LimeLightServo extends OpMode {
    private Limelight3A limelight;
    private DcMotorEx leftRear, leftFront, rightRear, rightFront;
    //private GoBildaPinpointDriver pinpoint;
    double strafePower;
    double movePower;
    double vX = 0.0;
    double vY = 0.0;
    double vRot = 0.0;
    double fl = vY + vX + vRot; // +0.4
    double fr = vY - vX - vRot; // -0.4
    double bl = vY - vX + vRot; // -0.4
    double br = vY + vX - vRot; // +0.4

    double max;


    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        //pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pintpoint");
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftRear = hardwareMap.get(DcMotorEx.class, "leftRear");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightRear");
        //pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        //pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        limelight.pipelineSwitch(1); //apriltag 3d
        //pinpoint.resetPosAndIMU();
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
        if(resultado.getTx() > 0.5){
            strafePower = 0.4; // positive strafes right
            vX = strafePower;
            vY = 0.0;
            vRot = 0.0;

            fl = vY + vX + vRot; // +0.4
            fr = vY - vX - vRot; // -0.4
            bl = vY - vX + vRot; // -0.4
            br = vY + vX - vRot; // +0.4

            max = Math.max(1.0, Math.max(Math.abs(fl), Math.max(Math.abs(fr),
                    Math.max(Math.abs(bl), Math.abs(br)))));
            fl /= max; fr /= max; bl /= max; br /= max;

            leftFront.setPower(fl);
            rightFront.setPower(fr);
            leftRear.setPower(bl);
            rightRear.setPower(br);
        }
        if(resultado.getTx() < -0.5){
            strafePower = -0.4; // negative strafes left
            vX = strafePower;
            vY = 0.0;
            vRot = 0.0;

            fl = vY + vX + vRot; // -0.4
            fr = vY - vX - vRot; // +0.4
            bl = vY - vX + vRot; // +0.4
            br = vY + vX - vRot; // -0.4

            max = Math.max(1.0, Math.max(Math.abs(fl), Math.max(Math.abs(fr),
                    Math.max(Math.abs(bl), Math.abs(br)))));
            fl /= max; fr /= max; bl /= max; br /= max;

            leftFront.setPower(fl);
            rightFront.setPower(fr);
            leftRear.setPower(bl);
            rightRear.setPower(br);
        }
        if (resultado.getTx() < 0.5 && resultado.getTx() > -0.5){
            /*if(resultado.getTa() < 10){
                movePower = 0.4; // positive moves foward
                vX = 0.0;
                vY = movePower;
                vRot = 0.0;

                fl = vY + vX + vRot; // +0.4
                fr = vY - vX - vRot; // +0.4
                bl = vY - vX + vRot; // +0.4
                br = vY + vX - vRot; // +0.4

                max = Math.max(1.0, Math.max(Math.abs(fl), Math.max(Math.abs(fr),
                        Math.max(Math.abs(bl), Math.abs(br)))));
                fl /= max; fr /= max; bl /= max; br /= max;

                leftFront.setPower(fl);
                rightFront.setPower(fr);
                leftRear.setPower(bl);
                rightRear.setPower(br);
            }

             */
            /*if (resultado.getTa() > 10){
                movePower = -0.4; // negative moves back
                vX = 0.0;
                vY = movePower;
                vRot = 0.0;

                fl = vY + vX + vRot; // -0.4
                fr = vY - vX - vRot; // -0.4
                bl = vY - vX + vRot; // -0.4
                br = vY + vX - vRot; // -0.4

                max = Math.max(1.0, Math.max(Math.abs(fl), Math.max(Math.abs(fr),
                        Math.max(Math.abs(bl), Math.abs(br)))));
                fl /= max; fr /= max; bl /= max; br /= max;

                leftFront.setPower(fl);
                rightFront.setPower(fr);
                leftRear.setPower(bl);
                rightRear.setPower(br);
            }

             */
            telemetry.addLine("centralizado");
            telemetry.update();
        }
        if (resultado == null && resultado.isValid()){
            leftFront.setPower(-0.4);
            rightFront.setPower(0.4);
            leftRear.setPower(-0.4);
            rightRear.setPower(0.4);
            telemetry.addLine("procurando");
            telemetry.update();
        }
        else {
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftRear.setPower(0);
            rightRear.setPower(0);
        }
    }
}
