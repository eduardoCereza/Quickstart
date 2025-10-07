package org.firstinspires.ftc.teamcode.pedroPathing.TeleAuto_Testes;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RunPeriods extends Init_Class{

    Init_Class initClass = new Init_Class();
    Follower follower;
    public void runTeleOp(Double x, Double y, Double turn){
        initClass.follower = follower;
        follower.setTeleOpDrive(x, y, turn,true);
    }
}
