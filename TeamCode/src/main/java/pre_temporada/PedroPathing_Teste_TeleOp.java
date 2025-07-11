package pre_temporada;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@TeleOp(name = "Teleoperado")
public class PedroPathing_Teste_TeleOp extends OpMode {

    /*
    1. Quando o sensor de cor for igual a false, ele está pressionado
     */

    private Pose startPose = new Pose(0, 0, 0);

    pre_temporada.HardwareMapFTC robot = new pre_temporada.HardwareMapFTC();

    private Follower follower;

    @Override
    public void init(){

        robot.init(hardwareMap);

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);

    }

    public void loop(){

        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
        follower.update();

    }

    public void start(){
        follower.startTeleopDrive();
    }

    public void slideV(){

    }

    public void slideH(){

        if (!robot.sensorTouch.getState()){
            robot.slideH.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

    }
}
