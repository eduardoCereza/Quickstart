package org.firstinspires.ftc.teamcode.pedroPathing.RoboOficial.ClassesRun.Interfaces;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public interface IRunMode extends IInitialization {

    public void moveChassi(double y, double x, double turn);

    public void followTag(double x, double y, double heading, boolean blue, Telemetry telemetry);

    public void throwBalls(Telemetry telemetry);

    public void liftRobot(boolean holding);
}
