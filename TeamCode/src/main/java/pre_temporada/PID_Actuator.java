package pre_temporada;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class PID_Actuator extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException{
        pre_temporada.HardwareMapFTC robot = new pre_temporada.HardwareMapFTC();
        robot.init(hardwareMap);


        int encoderDegreesToAttain = 200;
        double minPower = 0.01;
        double maxPower = 0.5;
        PID pid = new PID(0.03);
        pid.setInputRange(50, 500);
        pid.setSetPoint(encoderDegreesToAttain);
        pid.setOutputRange(minPower, maxPower);


        robot.slideH.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.slideH.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while(opModeIsActive()){
            if (robot.slideH.getCurrentPosition() < encoderDegreesToAttain){
                robot.slideH.setPower(minPower +
                        pid.getComputedOutput(robot.slideH.getCurrentPosition()));
            } else {
                robot.slideH.setPower(minPower -
                        pid.getComputedOutput(robot.slideH.getCurrentPosition()));
            }
        }

    }
}
