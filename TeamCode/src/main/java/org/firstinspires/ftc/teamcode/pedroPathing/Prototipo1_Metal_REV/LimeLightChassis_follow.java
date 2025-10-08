package org.firstinspires.ftc.teamcode.pedroPathing.Prototipo1_Metal_REV;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class LimeLightChassis_follow extends OpMode {
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
        limelight = hardwareMap.get(Limelight3A.class, "limelight3A");
        //pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pintpoint");
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftRear = hardwareMap.get(DcMotorEx.class, "leftRear");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightRear");
        //pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        //pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        limelight.pipelineSwitch(0); //apriltag 3d
        limelight.setPollRateHz(1800);
        rightRear.setDirection(DcMotorSimple.Direction.REVERSE);
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
        if(resultado.getTx() > 2){
            strafePower = -0.1; // positive strafes flywheelC
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
        if(resultado.getTx() < -2){
            strafePower = 0.1; // negative strafes left
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
        if (resultado.getTx() < 2 && resultado.getTx() > -2){

            if (resultado.getTa() < 20){
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

                leftFront.setPower(0.1);
                rightFront.setPower(0.1);
                leftRear.setPower(0.1);
                rightRear.setPower(0.1);
            }
            if (resultado.getTa() > 15){
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

                //leftFront.setPower();
                rightFront.setPower(-0.1);
                leftRear.setPower(-0.1);
                rightRear.setPower(-0.1);
            }
            if (resultado.getTa() > 15 && resultado.getTa() < 20) {
                leftFront.setPower(0);
                rightFront.setPower(0);
                leftRear.setPower(0);
                rightRear.setPower(0);
            }


            telemetry.addLine("centralizado");
            telemetry.update();
        }
        /*else{
            if(resultado.getTa() < 10){
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

                leftFront.setPower(-0.4);
                rightFront.setPower(-0.4);
                leftRear.setPower(-0.4);
                rightRear.setPower(-0.4);
            }
            if (resultado.getTa() > 10){
                leftFront.setPower(0);
                rightFront.setPower(0);
                leftRear.setPower(0);
                rightRear.setPower(0);
            }
        }

         */
        telemetry.addData("Right Front", rightFront.getPower());
        telemetry.addData("Left Front", leftFront.getPower());
        telemetry.addData("Right Rear", rightRear.getPower());
        telemetry.addData("Left Rear", leftRear.getPower());
        telemetry.update();

    }
}