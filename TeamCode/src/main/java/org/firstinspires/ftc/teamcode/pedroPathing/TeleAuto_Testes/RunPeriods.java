package org.firstinspires.ftc.teamcode.pedroPathing.TeleAuto_Testes;


public class RunPeriods extends Init_Class{
    public void TeleOp(double y, double x, double turn){


        if (follower !=null) {
            follower.setTeleOpDrive(y, x, turn, true);
        }

    }
}
