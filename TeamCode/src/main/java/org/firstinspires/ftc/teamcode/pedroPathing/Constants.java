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
            .mass(5)
            .lateralZeroPowerAcceleration(83.36905075433687)
            .forwardZeroPowerAcceleration(99.80732979361467)
            .translationalPIDFCoefficients(new PIDFCoefficients(0.06,0,0.00001,0.03))
            .secondaryTranslationalPIDFCoefficients(new PIDFCoefficients(0.07, 0, 0.001,0))
            .headingPIDFCoefficients(new PIDFCoefficients(0.55,0,0.1,0.03))
            .secondaryHeadingPIDFCoefficients(new PIDFCoefficients(0.7,0,0.01,0))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.001,0,0.0001,0.6,0.000001))
            .secondaryDrivePIDFCoefficients(new FilteredPIDFCoefficients(0.0002,0,0.0000005, 0.6,0.01))
            .centripetalScaling(0.005)
            .useSecondaryTranslationalPIDF(true)
            .useSecondaryHeadingPIDF(true)
            .useSecondaryDrivePIDF(true)
            ;
    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("rightFront")
            .rightRearMotorName("rightRear")
            .leftRearMotorName("leftRear")
            .leftFrontMotorName("leftFront")
            .leftFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .leftRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            ;
    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(-2)
            .strafePodX(-5.5)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD);
    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);
    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .pinpointLocalizer(localizerConstants)
                .build();
    }
}
