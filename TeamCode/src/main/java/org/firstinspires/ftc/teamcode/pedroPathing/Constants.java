package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(8)
            .forwardZeroPowerAcceleration(-38.79947566325643)
            .lateralZeroPowerAcceleration(-65.88498904226495)

            .translationalPIDFCoefficients(new PIDFCoefficients(0.051, 0, 0.005, 0.02))
            .secondaryTranslationalPIDFCoefficients(new PIDFCoefficients(0.01,0,0.0001,0.02))

            .headingPIDFCoefficients(new PIDFCoefficients(0.3, 0, 0.00001, 0.02))
            .secondaryHeadingPIDFCoefficients(new PIDFCoefficients(0.09, 0, 0.000005, 0.02))

            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.005, 0, 0.00005, 0.6, 0.03))
            .secondaryDrivePIDFCoefficients(new FilteredPIDFCoefficients(0.005, 0, 0.00005, 0.6, 0.035))

            .centripetalScaling(0.000001)
            ;
    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("rightFront")
            .rightRearMotorName("rightRear")
            .leftRearMotorName("leftRear")
            .leftFrontMotorName("leftFront")
            .leftFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .leftRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .xVelocity(91.25780384934795)
            .yVelocity(70.07506122739296)
            ;
    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(8)
            .strafePodX(0)
            .distanceUnit(DistanceUnit.CM)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED);
    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 0.45, 1);
    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .pinpointLocalizer(localizerConstants)
                .build();
    }
}