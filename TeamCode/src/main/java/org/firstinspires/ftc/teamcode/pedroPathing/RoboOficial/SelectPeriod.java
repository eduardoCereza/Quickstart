package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial;

import com.pedropathing.telemetry.SelectableOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Autonomous.BLUE.AutoGPP_Blue;
import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Autonomous.RED.AutoGPP_Red;
import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Autonomous.BLUE.AutoPGP_Blue;
import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Autonomous.RED.AutoPGP_Red;
import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Autonomous.BLUE.AutoPPG_Blue;
import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Autonomous.RED.AutoPPG_Red;

@TeleOp(name="Selecionar Periodos")

public class SelectPeriod extends SelectableOpMode {

    public SelectPeriod() {
        super("Select Autonomous", s -> {
            s.folder("Autonomous", a ->{
                a.folder("RED", r ->{
                    r.add("PPG", AutoPPG_Red::new);
                    r.add("PGP", AutoPGP_Red::new);
                    r.add("GPP", AutoGPP_Red::new);
                });
                a.folder("BLUE", b ->{
                    b.add("PPG", AutoPPG_Blue::new);
                    b.add("PGP", AutoPGP_Blue::new);
                    b.add("GPP", AutoGPP_Blue::new);
                });
            });
        });

    }
}
