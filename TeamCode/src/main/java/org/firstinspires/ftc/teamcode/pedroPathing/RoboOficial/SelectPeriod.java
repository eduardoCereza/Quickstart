package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial;

import com.pedropathing.telemetry.SelectableOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Autonomous.AutoGPP_Blue;
import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Autonomous.AutoGPP_Red;
import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Autonomous.AutoPGP_Blue;
import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Autonomous.AutoPGP_Red;
import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Autonomous.AutoPPG_Blue;
import org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.Autonomous.AutoPPG_Red;


@TeleOp(name="Selecionar Periodos")

public class SelectPeriod extends SelectableOpMode {
    Initialization initialization;


    public static class TeleOp_Class extends OpMode{
        public void init(){
        }

        public void loop(){
            telemetry.addLine("TELEOPERADO RODANDO!");
            telemetry.update();
        }
    }


    public SelectPeriod() {
        super("Select OpMode", s -> {
            s.folder("TeleOp", t ->{
                t.add("Modo teleoperado", TeleOp_Class::new);
            });
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

        initialization = new Initialization();
        initialization.initialization(hardwareMap, 1);
    }
}
